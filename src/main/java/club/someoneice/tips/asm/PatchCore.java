package club.someoneice.tips.asm;

import net.tclproject.mysteriumlib.PlaceholderCoremod;

public class PatchCore extends PlaceholderCoremod {
    @Override
    public void registerFixes() {
        registerClassWithFixes(ASMLoadingScreenRenderer.class.getName());
    }
    // LoadingScreenRenderer.setLoadingProgress
}
