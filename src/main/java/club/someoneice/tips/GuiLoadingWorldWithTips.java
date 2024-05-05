package club.someoneice.tips;

import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;

import java.awt.*;

public class GuiLoadingWorldWithTips extends GuiDownloadTerrain {
    public GuiLoadingWorldWithTips(NetHandlerPlayClient client) {
        super(client);
    }

    @Override
    public void drawScreen(int bX, int bY, float step) {
        this.drawBackground(0);
        this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingTerrain"), this.width / 2, this.height / 2 - 50, 16777215);

        int x = 10;
        int y = height - height / 10 - 10;
        TipsMain.getAndDrawTip(mc.fontRenderer, x , y);
    }
}
