package net.optifine.Modules.blatant;

import net.optifine.Modules.ModuleType;
import net.optifine.Modules.Module;
import net.optifine.Values.Mode;
import net.optifine.Values.Numbers;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Criticals extends Module {
    public int noBlockTimer = 0;
    private Mode<Enum> mode = new Mode("Mode", "mode", (Enum[]) Criticals.CriticalsMode.values(), (Enum) Criticals.CriticalsMode.Legit);

    static enum CriticalsMode {
        Legit
    }

    public static Numbers<Double> delay = new Numbers<Double>("Delay", "delay", 50.0, 0.0, 100.0, 10.0);
    public Criticals() {
        super("Criticals", Keyboard.KEY_NONE, ModuleType.Blatant,"Make you Criticals on Attack");
        this.addValues(this.mode);
        Chinese="刀刀暴击";
    }



    public static boolean canJump() {
        if (mc.thePlayer.isOnLadder()) {
            return false;
        }
        if (mc.thePlayer.isInWater()) {
            return false;
        }
        if (mc.thePlayer.isInLava()) {
            return false;
        }
        if (mc.thePlayer.isSneaking()) {
            return false;
        }
        if (mc.thePlayer.isRiding()) {
            return false;
        }
        return true;
    }


    @SubscribeEvent
    public void onTick(TickEvent event){
        if(this.mode.getValue() == CriticalsMode.Legit){
            if (mc.gameSettings.keyBindAttack.isKeyDown()) {
                if(mc.objectMouseOver.entityHit != null && canJump() && mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                    mc.playerController.attackEntity(mc.thePlayer, Minecraft.getMinecraft().objectMouseOver.entityHit);
                }
            }
        }
    }
}
