package net.optifine.Modules.render;

import net.minecraft.client.Minecraft;
import net.optifine.Modules.ModuleType;
import net.optifine.Modules.Module;
import org.lwjgl.input.Keyboard;

public class FullBright extends Module {
    private float old;
    public FullBright() {
        super("FullBright", Keyboard.KEY_NONE, ModuleType.Render,"Make the bright for night and dark");
        Chinese="夜视";
    }

    @Override
    public void enable() {
        this.old = mc.gameSettings.gammaSetting;
        Minecraft.getMinecraft().gameSettings.gammaSetting = 300;
    }

    @Override
    public void disable() {
        Minecraft.getMinecraft().gameSettings.gammaSetting = this.old;
    }
}
