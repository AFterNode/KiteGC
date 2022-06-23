package net.optifine.Utils;

import net.optifine.Modules.blatant.Spammer;

import java.util.concurrent.TimeUnit;

public class SpammerThread extends Thread{
    public void run() {
        while (true) {
            try {
                if (!Spammer.status) break;
                Spammer.mc.thePlayer.sendChatMessage(Spammer.getContent());
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                System.out.println("[Kite] Exception occurred at SpammerThread: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static SpammerThread go() {
        SpammerThread spammerThread = new SpammerThread();
        spammerThread.start();
        return spammerThread;
    }
}
