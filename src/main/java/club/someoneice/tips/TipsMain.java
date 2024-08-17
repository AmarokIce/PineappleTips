package club.someoneice.tips;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;

@Mod(modid = TipsMain.MODID, name = TipsMain.NAME, useMetadata = true)
public class TipsMain {
    public static final String MODID = "tips";
    public static final String NAME = "PineappleTips";
    public static final String VERSION = "@VERSION@";

    public static final Logger LOG = LogManager.getLogger(NAME);

    @Mod.Instance(MODID)
    public static TipsMain INSTANCE;

    public boolean obfuscated;

    public static final List<String> TIPS = Lists.newArrayList();

    /* Init Event */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        INSTANCE = this;

        readConfig();
        List<String> tips = Lists.newArrayList(TIPS);
        TIPS.clear();
        tips.stream().map(I18n::format).forEach(TIPS::add);

        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        this.obfuscated = !(Boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment");
    }

    /* Common Method */
    private static void readConfig() throws IOException {
        File configFile = new File(Loader.instance().getConfigDir(), NAME + ".json");
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

        if (configFile.isFile()) {
            TIPS.addAll(gson.fromJson(new String(Files.readAllBytes(configFile.toPath())), new TypeToken<List<String>>() {
            }.getType()));
            return;
        }

        configFile.createNewFile();
        TIPS.add("凤梨级测试！");
        TIPS.add("咩狼的尾巴有多长？");
        TIPS.add("何不试试 ManaMetalMod ！");
        TIPS.add("你是否做过一个 PasterDream ？");
        Files.write(configFile.toPath(), gson.toJson(TIPS).getBytes());
    }

    static int tickOfClientInRender = 0;
    static String tipHolder = "";
    static Random random = new Random();

    public static void getAndDrawTip(FontRenderer font, int x, int y) {
        if (tipHolder.isEmpty() || ++tickOfClientInRender >= 100) {
            tickOfClientInRender = 0;
            tipHolder = TIPS.get(random.nextInt(TIPS.size()));
        }

        font.drawString("§l§eTips:", x, y, Color.WHITE.getRGB());
        y += 10;

        if (tipHolder.length() < 20) font.drawString(tipHolder, x , y, Color.WHITE.getRGB());
        else {
            String tip0 = tipHolder.substring(0, 20);
            String tip1 = tipHolder.substring(20);
            font.drawString(tip0, x, y, Color.WHITE.getRGB());
            font.drawString(tip1, x, y + 10, Color.WHITE.getRGB());
        }
    }

    /* Common Event */
    @SuppressWarnings("unchecked")
    List<Class<? extends GuiScreen>> screens = Lists.newArrayList(
            GuiGameOver.class,
            GuiConnecting.class,
            GuiDisconnected.class,
            GuiIngameMenu.class
    );

    @SubscribeEvent
    public void onScreenLoading(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (!screens.contains(event.gui.getClass())) return;
        int x = 10;
        int y = event.gui.height - event.gui.height / 10 - 10;
        FontRenderer font = event.gui.mc.fontRenderer;
        getAndDrawTip(font, x , y);
    }

    @SubscribeEvent
    public void onScreenOpen(GuiOpenEvent event) {
        if (!(event.gui instanceof GuiDownloadTerrain)) return;
        try {
            NetHandlerPlayClient npc = ReflectionHelper.getPrivateValue(GuiDownloadTerrain.class, (GuiDownloadTerrain) event.gui, "field_146594_a");
            event.gui = new GuiLoadingWorldWithTips(npc);
        } catch (Exception ignored) {}
    }
}
