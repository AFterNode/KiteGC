package net.optifine.Modules.world;

import net.minecraft.init.Items;
import net.minecraft.util.WeightedRandom;
import net.optifine.Modules.ModuleType;
import net.optifine.Modules.Module;
import net.optifine.Modules.combat.AntiBot;
import net.optifine.Utils.utils.Helper;
import net.optifine.Values.Numbers;
import net.optifine.Values.Option;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import scala.Array;
import scala.tools.cmd.gen.AnyValReps;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MurderMystery extends Module {

    public static boolean a1;
    public static int a2;
    private static List<EntityPlayer> m;
    private static List<EntityPlayer> bw;

    public static Option<Boolean> Murder = new Option<Boolean>("Tell Everyone Murder","Tell Everyone Murder", false);
    public static Option<Boolean> Bow= new Option<Boolean>("Tell Everyone Bow","Tell Everyone Bow", false);
    public static Option<Boolean> Hyt = new Option<>("HytExtension", "hytExtension", false);
    public static Numbers<Integer> delay = new Numbers<>("AutoDetectDelay", "AutoDetectDelay", 5, 10, 1, 1);
    public static boolean status = false;

    public MurderMystery() {
        super("MurderMystery", Keyboard.KEY_NONE, ModuleType.World,"Detection Murders in Murder game");
        this.addValues(this.Murder,this.Bow,Hyt);
        Chinese="杀手检查";
    }

    @Override
    public void enable() {
        status = true;

        MMThread thread = new MMThread();
        thread.start();
    }

    @Override
    public void disable() {
        MurderMystery.a1 = false;
        MurderMystery.a2 = 0;
        status = false;
    }

    @SubscribeEvent
    public void o(final RenderWorldLastEvent ev) {
        if (MurderMystery.mc.thePlayer.getWorldScoreboard() != null && MurderMystery.mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1) != null) {
            final String d = MurderMystery.mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1).getDisplayName();
            if (d.contains("MURDER") || d.contains("MYSTERY")) {
                for (final EntityPlayer en : MurderMystery.mc.theWorld.playerEntities) {
                    if (en != MurderMystery.mc.thePlayer && !en.isInvisible() && !AntiBot.isServerBot(en)) {
                        if (en.getHeldItem() != null && en.getHeldItem().hasDisplayName()) {
                            final Item i = en.getHeldItem().getItem();
                            if (i instanceof ItemSword || i instanceof ItemAxe || en.getHeldItem().getDisplayName().replaceAll("§", "").equals("aKnife")) {
                                if (!MurderMystery.m.contains(en)) {
                                    MurderMystery.m.add(en);
                                        MurderMystery.mc.thePlayer.playSound("note.pling", 1.0f, 1.0f);
                                    Helper.sendMessage(en.getName() + " 手中检测到武器");
                                    if(this.Murder.getValue()){
                                        mc.thePlayer.sendChatMessage(en.getName() + " 是狼");
                                    }
                                }
                            }
                            else if (i instanceof ItemBow && !MurderMystery.bw.contains(en)) {
                                MurderMystery.bw.add(en);
                                Helper.sendMessage("[WARNING]" + en.getName() + " have bow! he maybe will kill you.");
                                if(this.Bow.getValue()){
                                    mc.thePlayer.sendChatMessage(en.getName() + " have bow.");
                                }
                            } else if (i == Items.blaze_rod && !MurderMystery.bw.contains(en)) {
                                Helper.sendMessage("[WARNING] " + en.getName() + " 是预言家");
                            }
                        }
                        int rgb = Color.green.getRGB();
                        if ((MurderMystery.m.contains(en) && !MurderMystery.bw.contains(en)) || (MurderMystery.m.contains(en) && MurderMystery.bw.contains(en))) {
                            rgb = Color.red.getRGB();
                        }
                        if (!MurderMystery.m.contains(en) && MurderMystery.bw.contains(en)) {
                            rgb = Color.orange.getRGB();
                        }
                    }
                }
            }
            else {
                this.c();
            }
        }
        else {
            this.c();
        }
    }

    public static void c() {
        if (MurderMystery.m.size() > 0) {
            MurderMystery.m.clear();
        }
        if (MurderMystery.bw.size() > 0) {
            MurderMystery.bw.clear();
        }
    }

    static {
        MurderMystery.a1 = false;
        MurderMystery.a2 = 0;
        MurderMystery.m = new ArrayList<EntityPlayer>();
        MurderMystery.bw = new ArrayList<EntityPlayer>();
    }

    public static List<EntityPlayer> getM() { return MurderMystery.m; }
    public static List<EntityPlayer> getBw() { return bw; }

}

class MMThread extends Thread {
    public void detect() {
        if (MurderMystery.mc.thePlayer.getWorldScoreboard() != null && MurderMystery.mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1) != null) {
            final String d = MurderMystery.mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1).getDisplayName();
            if (d.contains("MURDER") || d.contains("MYSTERY")) {
                for (final EntityPlayer en : MurderMystery.mc.theWorld.playerEntities) {
                    if (en != MurderMystery.mc.thePlayer && !en.isInvisible() && !AntiBot.isServerBot(en)) {
                        if (en.getHeldItem() != null && en.getHeldItem().hasDisplayName()) {
                            final Item i = en.getHeldItem().getItem();
                            if (i instanceof ItemSword || i instanceof ItemAxe || en.getHeldItem().getDisplayName().replaceAll("§", "").equals("aKnife")) {
                                if (!MurderMystery.getM().contains(en)) {
                                    MurderMystery.getM().add(en);
                                    MurderMystery.mc.thePlayer.playSound("note.pling", 1.0f, 1.0f);
                                    Helper.sendMessage(en.getName() + " 手中检测到武器");
                                    if(MurderMystery.Murder.getValue()){
                                        MurderMystery.mc.thePlayer.sendChatMessage(en.getName() + " 是狼");
                                    }
                                }
                            }
                            else if (i instanceof ItemBow && !MurderMystery.getBw().contains(en)) {
                                MurderMystery.getBw().add(en);
                                Helper.sendMessage("[WARNING]" + en.getName() + " have bow! he maybe will kill you.");
                                if(MurderMystery.Bow.getValue()){
                                    MurderMystery.mc.thePlayer.sendChatMessage(en.getName() + " have bow.");
                                }
                            } else if (i == Items.blaze_rod && !MurderMystery.getBw().contains(en)) {
                                Helper.sendMessage("[WARNING] " + en.getName() + " 是预言家");
                            }
                        }
                        int rgb = Color.green.getRGB();
                        if ((MurderMystery.getM().contains(en) && !MurderMystery.getBw().contains(en)) || (MurderMystery.getM().contains(en) && MurderMystery.getBw().contains(en))) {
                            rgb = Color.red.getRGB();
                        }
                        if (!MurderMystery.getM().contains(en) && MurderMystery.getBw().contains(en)) {
                            rgb = Color.orange.getRGB();
                        }
                    }
                }
            }
            else {
                MurderMystery.c();
            }
        }
        else {
            MurderMystery.c();
        }
    }

    public void run() {
        while (MurderMystery.status) {
            try {
                detect();
                TimeUnit.SECONDS.sleep(MurderMystery.delay.getValue());
            } catch (Exception e) {
                System.out.println("[Kite] Exception occurred at MurderMystery: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
