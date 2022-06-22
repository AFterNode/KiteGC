package net.optifine.Modules.render;

import net.optifine.Vapu.Client;
import net.optifine.Modules.ModuleType;
import net.optifine.Modules.Module;
import org.lwjgl.input.Keyboard;

public class ChineseMode extends Module {
    public ChineseMode() {
        super("中文", Keyboard.KEY_NONE, ModuleType.Combat,"");
        Chinese="中文模式";
    }


    @Override
    public void enable(){
        Client.CHINESE = true;
        this.mc.thePlayer.closeScreen();
    }

    @Override
    public void disable(){
        Client.CHINESE = false;
        this.mc.thePlayer.closeScreen();
    }

}
