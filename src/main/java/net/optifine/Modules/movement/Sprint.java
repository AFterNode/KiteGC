package net.optifine.Modules.movement;

import net.optifine.Modules.ModuleType;
import net.optifine.Modules.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.optifine.Values.Option;
import net.optifine.Vapu.Client;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {
    private Option<Boolean> Sprint = new Option<Boolean>("KeepSprint","KeepSprint", false);
    public Sprint() {
        super("Sprint", Keyboard.KEY_NONE, ModuleType.Movement,"Force sprint when you moving");
        this.addValues(this.Sprint);
        Chinese="强制疾跑";
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {
        if(!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.moveForward > 0) {
            mc.thePlayer.setSprinting(true);
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if(Client.nullCheck())
            return;
        if(this.Sprint.getValue()) {
            if(!mc.thePlayer.isSprinting()) mc.thePlayer.setSprinting(true);
        }
    }
}
