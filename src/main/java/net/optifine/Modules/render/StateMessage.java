package net.optifine.Modules.render;

import net.optifine.Vapu.Client;
import net.optifine.Modules.ModuleType;
import net.optifine.Modules.Module;
import org.lwjgl.input.Keyboard;

public class StateMessage extends Module {
    public StateMessage() {
        super("NoStateMessage", Keyboard.KEY_NONE, ModuleType.Render,"Hide Modules State info");
        Chinese="无开关信息";
    }

    public void enable() {
        Client.MessageON = true;
    }

    public void disable(){
        Client.MessageON = false;
    }
}
