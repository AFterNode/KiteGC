package net.optifine.Modules.player;

import net.optifine.Modules.ModuleType;
import net.optifine.Modules.Module;
import net.optifine.Utils.TimerUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class AntiAFK extends Module {
    public TimerUtil timer = new TimerUtil();
    public AntiAFK() {
        super("AntiAFK", Keyboard.KEY_NONE, ModuleType.Player,"Prevent you kicked of AFK");
        Chinese="挂机防踢";
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.timer.delay((long) 10.0)) {
            if(mc.thePlayer.onGround){
                mc.thePlayer.jump();
            }
            this.timer.reset();
        }
    }
}
