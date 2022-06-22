package net.optifine.Modules.Config;

import net.optifine.Vapu.Client;
import net.optifine.Modules.ModuleType;
import net.optifine.Modules.Module;
import net.optifine.Manager.ModuleManager;

import java.util.ArrayList;

import static org.lwjgl.input.Keyboard.KEY_NONE;

public class Uninject extends Module {
    public Uninject() {
        super("Uninject", KEY_NONE, ModuleType.Config,"Uninject "+ Client.name);
        Chinese="解体";
        NoToggle=true;
    }

    public void enable() {
        this.mc.thePlayer.closeScreen();
        ArrayList<Module> modules = new ArrayList<>(ModuleManager.getModules());
        for (Module m : modules) {
            if (m != null) {
                m.setState(false);
            }
        }
        Client.unInject();
        state=false;
    }
}
