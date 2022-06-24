package net.optifine.Modules.combat;

import net.optifine.Modules.ModuleType;
import net.optifine.Modules.Module;
import net.optifine.Manager.ModuleManager;
import net.optifine.Values.Mode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;

import java.util.Objects;

public class AntiBot extends Module {
    private static Mode<Enum> mode = new Mode("Mode", "mode", (Enum[]) AntiBot.antibotmode.values(), (Enum) AntiBot.antibotmode.Hypixel);
        public AntiBot() {
            super("AntiBot", Keyboard.KEY_NONE, ModuleType.Combat,"Make cheats exclude the bots");
            this.addValues(this.mode);
            Chinese="反机器人";
        }


    static enum antibotmode {
        Hypixel,
        Mineplex,
        Syuu,
        Vanilla,
        DoMCer
    }
    public static double getEntitySpeed(Entity entity) {
        double xDif = entity.posX - entity.prevPosX;
        double zDif = entity.posZ - entity.prevPosZ;
        return (Math.sqrt(xDif * xDif + zDif * zDif) * 20.0);
    }

        public static boolean isServerBot(Entity entity) {
            if (Objects.requireNonNull(ModuleManager.getModule("AntiBot")).getState()) {
                if(AntiBot.mode.getValue() == antibotmode.Hypixel){
                    return !entity.getDisplayName().getFormattedText().startsWith("\u00a7") || entity.isInvisible() || entity.getDisplayName().getFormattedText().toLowerCase().contains("npc");
                } else if(AntiBot.mode.getValue() == antibotmode.Mineplex){
                    for (Object object : mc.theWorld.playerEntities) {
                        EntityPlayer entityPlayer = (EntityPlayer)object;
                        if (entityPlayer == null || entityPlayer == mc.thePlayer || !entityPlayer.getName().startsWith("Body #") && entityPlayer.getMaxHealth() != 20.0f) continue;
                        return true;
                    }
                } else if(AntiBot.mode.getValue() == antibotmode.Syuu){
                    for (Entity entitys : mc.theWorld.loadedEntityList) {
                        if (entity == mc.thePlayer) continue;
                        if (entity instanceof EntityPlayer) {
                            final EntityPlayer entityPlayer = (EntityPlayer)entity;
                            if (entityPlayer.isInvisible() && entityPlayer.getHealth() > 1000.0f && getEntitySpeed(entityPlayer) > 20) {
                                return true;
                            }
                        }
                    }
                } else if(AntiBot.mode.getValue() == antibotmode.Vanilla){
                    if(!entity.getDisplayName().getFormattedText().startsWith("\u00a7") ||
                            entity.isInvisible() ||
                            entity.getDisplayName().getFormattedText().toLowerCase().contains("npc")){
                        return true;
                    }
                } else if (mode.getValue() == antibotmode.DoMCer) {
                    if (entity.getDisplayName().getFormattedText().startsWith("§") ||
                            entity.getDisplayName().getFormattedText().contains("\n") ||
                    entity.getDisplayName().getFormattedText().contains("点击开始进行游戏") ||
                    entity.getDisplayName().getFormattedText().contains("在线人数: ")) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

