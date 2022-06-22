package net.optifine.Modules.blatant;

import net.optifine.Modules.ModuleType;
import net.optifine.Modules.Module;
import net.optifine.Values.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Speed extends Module {
    private static Mode<Enum> mode = new Mode("Mode", "mode", (Enum[]) Speed.speedmode.values(), (Enum) speedmode.FastHop);
    public Speed() {
        super("Speed", Keyboard.KEY_NONE, ModuleType.Blatant,"Make you move quickly");
        this.addValues(this.mode);
        Chinese="加速";
    }
    public static double movementSpeed;

    static enum speedmode {
        FastHop,
        BunnyHop,
        SlowHop
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {
        if(this.mode.getValue() == speedmode.FastHop){
            if(!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.moveForward > 0 && mc.thePlayer.onGround){
                mc.thePlayer.setSprinting(true);
                mc.thePlayer.jump();
            }
        } else if(this.mode.getValue() == speedmode.SlowHop){
            if (!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.moveForward > 0 && mc.thePlayer.onGround) {
                mc.thePlayer.jump();
                setSpeed(0.26 + (getNormalSpeedEffect() * 0.05));
            } else {
                setSpeed(0);
            }
            movementSpeed = 0.26;
        } else if(this.mode.getValue() == speedmode.BunnyHop){
            if (isToJump() && Minecraft.getMinecraft().thePlayer.moveForward != 0 && (Minecraft.getMinecraft().thePlayer.posY % 1 == 0)) Minecraft.getMinecraft().thePlayer.jump();
        }
    }

    public boolean isToJump() {
        if (mc.thePlayer != null && !mc.thePlayer.isInWater() && !mc.thePlayer.isOnLadder()) return true;
        return false;
    }

    public static double getNormalSpeedEffect() {
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            return mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        }

        return 0;
    }

    public static void setSpeed(double speed) {
        mc.thePlayer.motionX = -Math.sin(getDirection()) * speed;
        mc.thePlayer.motionZ = Math.cos(getDirection()) * speed;
    }

    public static float getDirection() {
        float yaw = mc.thePlayer.rotationYaw;
        if (mc.thePlayer.movementInput.moveForward < 0.0f) {
            yaw += 180.0f;
        }
        float forward = 1.0f;
        if (mc.thePlayer.movementInput.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (mc.thePlayer.movementInput.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (mc.thePlayer.movementInput.moveStrafe > 0.0f) {
            yaw -= 90.0f * forward;
        }
        if (mc.thePlayer.movementInput.moveStrafe < 0.0f) {
            yaw += 90.0f * forward;
        }
        return yaw * ((float) Math.PI / 180);
    }

    public static double getMotionX(double speed) {
        return -Math.sin(getDirection()) * speed;
    }

    public static double getMotionZ(double speed) {
        return Math.cos(getDirection()) * speed;
    }
}
