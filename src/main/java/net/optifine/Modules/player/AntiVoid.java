package net.optifine.Modules.player;

import net.optifine.Modules.Module;
import net.optifine.Modules.ModuleType;
import org.lwjgl.input.Keyboard;

// 未完成
public class AntiVoid extends Module {
    public AntiVoid() {
        super("AntiVoid", Keyboard.KEY_NONE, ModuleType.Player, "Prevents you from falling into the void");
        Chinese = "防掉虚空";
    }
}
