package net.optifine.Modules.Config;

import net.optifine.Manager.ConfigManager;
import net.optifine.Manager.ModuleManager;
import net.optifine.Vapu.Client;
import net.optifine.Modules.ModuleType;
import net.optifine.Modules.Module;
import net.optifine.Utils.utils.Helper;
import net.optifine.Values.Mode;
import net.optifine.Values.Numbers;
import net.optifine.Values.Option;
import net.optifine.Values.Value;
import org.lwjgl.input.Keyboard;

import java.util.List;

import static org.lwjgl.input.Keyboard.KEY_NONE;

public class LoadConfig extends Module {
    public LoadConfig() {
        super("LoadConfig", KEY_NONE, ModuleType.Config,"Load your configs");
        Chinese="加载配置";
        NoToggle=true;
    }

    public void enable() {
        List<String> binds = ConfigManager.read(Client.config+"-binds.cfg");
        for (String v : binds) {
            String name = v.split(":")[0];
            String bind = v.split(":")[1];
            Module m = ModuleManager.getModule(name);
            if (m == null) continue;
            m.setKey(Keyboard.getKeyIndex((String)bind.toUpperCase()));
        }
        List<String> enabled = ConfigManager.read(Client.config+"-modules.cfg");
        for (String v : enabled) {
            Module m = ModuleManager.getModule(v);
            if (m == null) continue;
            m.setState(true);
        }
        List<String> vals = ConfigManager.read(Client.config+"-values.cfg");
        for (String v : vals) {
            String name = v.split(":")[0];
            String values = v.split(":")[1];
            Module m = ModuleManager.getModule(name);
            if (m == null) continue;
            for (Value value : m.getValues()) {
                if (!value.getName().equalsIgnoreCase(values)) continue;
                if (value instanceof Option) {
                    value.setValue(Boolean.parseBoolean(v.split(":")[2]));
                    continue;
                }
                if (value instanceof Numbers) {
                    value.setValue(Double.parseDouble(v.split(":")[2]));
                    continue;
                }
                ((Mode)value).setMode(v.split(":")[2]);
            }
        }
        Client.name = ConfigManager.read(Client.config+"-client.cfg").get(0);
        Helper.sendMessage("Configs Loaded.");
        this.state = false;
    }
}
