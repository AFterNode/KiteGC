package net.optifine.Modules.other;


import net.minecraft.entity.player.EntityPlayer;
import net.optifine.Utils.utils.Helper;
import net.optifine.Values.Mode;
import net.optifine.Modules.Module;
import net.optifine.Modules.ModuleType;
import net.optifine.Modules.combat.AntiBot;
import net.optifine.Values.Numbers;
import org.lwjgl.input.Keyboard;
import scala.Int;

import java.util.concurrent.TimeUnit;

/**
 * 此功能没有完成
 */
public class AntiVanish extends Module {
    public static boolean status = false;
    public static Mode<Enum> server = new Mode<>("Server", "Server", Servers.values(), Servers.Normal);
    public static Numbers<Integer> delay = new Numbers<>("Delay", "Delay", 10, 5, 60, 1);

    enum Servers {
        Normal,
        DoMCer
    }

    public AntiVanish() {
        super("AntiVanish", Keyboard.KEY_NONE, ModuleType.Other, "[BETA]Check if there is an invisible player");

        addValues(server);
    }

    @Override
    public void enable() {
        status = true;
        AVThread thread = new AVThread();
        thread.start();
    }
}

class AVThread extends Thread {
    public void run() {
        while (AntiVanish.status) {
            try {
                int value = 0;
                for (final EntityPlayer ep: AntiVanish.mc.theWorld.playerEntities) {
                    if (AntiBot.isServerBot(ep)) continue;

                    // 航服拓展
                    if (AntiVanish.server.getValue() == AntiVanish.Servers.DoMCer){
                        if (ep.getInventory().length >= 4) continue;
                    }

                    if (ep.isInvisible()) {
                        value ++;
                    }
                }
                if (value == 0) return;
                Helper.sendMessage("There are " + value + " invisible players.");
                TimeUnit.SECONDS.sleep(AntiVanish.delay.getValue());
            } catch (Exception e) {
                System.out.println("[Kite] Exception occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
