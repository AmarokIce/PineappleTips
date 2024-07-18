package club.someoneice.tips.asm;

// import club.someoneice.tips.TipsMain;
// import cpw.mods.fml.client.FMLClientHandler;
// import cpw.mods.fml.relauncher.ReflectionHelper;
// import net.minecraft.client.LoadingScreenRenderer;
// import net.minecraft.client.Minecraft;
// import net.minecraft.client.gui.Gui;
// import net.minecraft.client.gui.ScaledResolution;
// import net.minecraft.client.renderer.OpenGlHelper;
// import net.minecraft.client.renderer.Tessellator;
// import net.minecraft.client.shader.Framebuffer;
// import net.minecraft.util.MinecraftError;
// import net.tclproject.mysteriumlib.asm.annotations.EnumReturnSetting;
// import net.tclproject.mysteriumlib.asm.annotations.Fix;
// import net.tclproject.mysteriumlib.asm.annotations.FixOrder;
// import org.lwjgl.opengl.GL11;
//
// public class ASMLoadingScreenRenderer {
//     @Fix(order = FixOrder.FIRST, returnSetting = EnumReturnSetting.ALWAYS)
//     public static void setLoadingProgress(LoadingScreenRenderer instance, int loadProgress) {
//         /* Reflection Field Start */
//         Minecraft mc = Minecraft.getMinecraft();
//         boolean progress = reflection(instance, "field_73724_e");
//         boolean running = ReflectionHelper.getPrivateValue(Minecraft.class, mc, "running");
//
//         String currentlyDisplayedText = reflection(instance, "currentlyDisplayedText");
//         String field_73727_a = reflection(instance, "field_73727_a");
//
//         Framebuffer fb = reflection(instance, "field_146588_g");
//         /* Reflection Field End */
//
//         if (!running) if (!progress) throw new MinecraftError();
//
//         long j = Minecraft.getSystemTime();
//         long time = reflection(instance, "field_73723_d");
//         if (j - time < 100L) return;
//
//         ReflectionHelper.setPrivateValue(LoadingScreenRenderer.class, instance, j, "field_73723_d");
//
//         ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
//         int k = scaledresolution.getScaleFactor();
//         int x = scaledresolution.getScaledWidth();
//         int y = scaledresolution.getScaledHeight();
//
//         if (OpenGlHelper.isFramebufferEnabled()) fb.framebufferClear();
//         else GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
//
//         fb.bindFramebuffer(false);
//         GL11.glMatrixMode(GL11.GL_PROJECTION);
//         GL11.glLoadIdentity();
//         GL11.glOrtho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
//         GL11.glMatrixMode(GL11.GL_MODELVIEW);
//         GL11.glLoadIdentity();
//         GL11.glTranslatef(0.0F, 0.0F, -200.0F);
//
//         if (!OpenGlHelper.isFramebufferEnabled())
//             GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
//
//
//         if (!FMLClientHandler.instance().handleLoadingScreen(scaledresolution)) {
//             Tessellator tessellator = Tessellator.instance;
//             mc.getTextureManager().bindTexture(Gui.optionsBackground);
//             float f = 32.0F;
//             tessellator.startDrawingQuads();
//             tessellator.setColorOpaque_I(4210752);
//             tessellator.addVertexWithUV(0.0D, y, 0.0D, 0.0D, ((float)y / f));
//             tessellator.addVertexWithUV(x, y, 0.0D, ((float)x / f), ((float)y / f));
//             tessellator.addVertexWithUV(x, 0.0D, 0.0D, ((float)x / f), 0.0D);
//             tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
//             tessellator.draw();
//
//             if (loadProgress >= 0) {
//                 byte b0 = 100;
//                 byte b1 = 2;
//                 int j1 = x / 2 - b0 / 2;
//                 int k1 = y / 2 + 16;
//                 GL11.glDisable(GL11.GL_TEXTURE_2D);
//                 tessellator.startDrawingQuads();
//                 tessellator.setColorOpaque_I(8421504);
//                 tessellator.addVertex(j1, k1, 0.0D);
//                 tessellator.addVertex(j1, k1 + b1, 0.0D);
//                 tessellator.addVertex(j1 + b0, (k1 + b1), 0.0D);
//                 tessellator.addVertex(j1 + b0, k1, 0.0D);
//                 tessellator.setColorOpaque_I(8454016);
//                 tessellator.addVertex(j1, k1, 0.0D);
//                 tessellator.addVertex(j1, k1 + b1, 0.0D);
//                 tessellator.addVertex((j1 + loadProgress), k1 + b1, 0.0D);
//                 tessellator.addVertex((j1 + loadProgress), k1, 0.0D);
//                 tessellator.draw();
//                 GL11.glEnable(GL11.GL_TEXTURE_2D);
//             }
//
//             GL11.glEnable(GL11.GL_BLEND);
//             OpenGlHelper.glBlendFunc(770, 771, 1, 0);
//             mc.fontRenderer.drawStringWithShadow(currentlyDisplayedText, (x - mc.fontRenderer.getStringWidth(currentlyDisplayedText)) / 2, y / 2 - 4 - 16, 16777215);
//             mc.fontRenderer.drawStringWithShadow(field_73727_a, (x - mc.fontRenderer.getStringWidth(field_73727_a)) / 2, y / 2 - 4 + 8, 16777215);
//
//             int tipX = 10;
//             int tipY = y - y / 10 - 10;
//             TipsMain.getAndDrawTip(mc.fontRenderer, tipX, tipY);
//         }
//
//         fb.unbindFramebuffer();
//
//         if (OpenGlHelper.isFramebufferEnabled()) fb.framebufferRender(x * k, y * k);
//
//         mc.func_147120_f();
//
//         try {
//             Thread.yield();
//         }
//         catch (Exception ignored) {}
//     }
//
//
//
//     /* === Static Method Area === */
//
//     private static <T> T reflection(LoadingScreenRenderer instance, String name) {
//         return ReflectionHelper.getPrivateValue(LoadingScreenRenderer.class, instance, name);
//     }
// }