package net.optifine.Modules.other;

import net.optifine.Modules.Module;
import net.optifine.Modules.ModuleType;
import net.optifine.Utils.Helper;
import net.optifine.Values.Mode;
import net.optifine.Vapu.Client;
import net.optifine.Vapu.Debug;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

public class Hitokoto extends Module {
    public Hitokoto() {
        super("Hitokoto", Keyboard.KEY_NONE, ModuleType.Other, "Get sentence from HitokotoAPI and send it");
        Chinese = "一言";
    }

    @Override
    public void enable(){
        net.optifine.Utils.Hitokoto.go();
        state = false;
    }
}
