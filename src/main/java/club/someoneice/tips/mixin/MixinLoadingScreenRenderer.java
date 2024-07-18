package club.someoneice.tips.mixin;

import club.someoneice.tips.TipsMain;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LoadingScreenRenderer.class)
public class MixinLoadingScreenRenderer {
    @Inject(method = "setLoadingProgress", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/renderer/OpenGlHelper;glBlendFunc(IIII)V"))
    public void onSetLoadingProgress(int tick, CallbackInfo ci) {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        int y = scaledresolution.getScaledHeight();
        int tipX = 10;
        int tipY = y - y / 10 - 10;
        TipsMain.getAndDrawTip(mc.fontRenderer, tipX, tipY);
    }
}
