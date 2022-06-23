package net.optifine.Modules.player;

import net.optifine.Modules.Module;
import net.optifine.Modules.ModuleType;
import net.optifine.Values.Mode;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AutoCNM extends Module {
    private AutoCNMThread th;
    public static final HashMap<Enum, Integer> DelayMap = new HashMap<>();
    public static final HashMap<Enum, String[]> SentenceMap = new HashMap<>();


    private Mode<Enum> mode = new Mode<>("Mode", "mode", (Enum[]) Sentences.values(), (Enum) Sentences.Normal);
    private Mode<Enum> delay = new Mode<>("Delay", "delay", Delay.values(), Delay.Normal);

    enum Sentences{
        Normal
    }

    enum Delay {
        Slow,
        Normal,
        Fast,
    }

    public AutoCNM() {
        super("AutoCNM", Keyboard.KEY_NONE, ModuleType.Player, "CNMB");
        Chinese = "自动骂人";

        SentenceMap.put(Sentences.Normal, LTap.LMessages);
        this.addValues(mode);

        DelayMap.put(Delay.Slow, 7);
        DelayMap.put(Delay.Normal, 4);
        DelayMap.put(Delay.Fast, 1);
        this.addValues(delay);
    }

    @Override
    public void enable(){
        th = AutoCNMThread.go(DelayMap.get(delay.getValue()),
                SentenceMap.get(mode.getValue()));
    }

    @Override
    public void disable() {
        th.interrupt();
        th = null;
    }
}

class AutoCNMThread extends Thread {
    private final int delay;
    private final String[] sentences;

    AutoCNMThread(int d, String[] s) {
        delay = d;
        sentences = s;
    }

    public void run() {
        while (true) {
            try {
                Random r = new Random();
                AutoCNM.mc.thePlayer.sendChatMessage(sentences[r.nextInt(sentences.length)]);
                TimeUnit.SECONDS.sleep(delay);
            } catch (Exception e) {
                System.out.println("[Kite] Exception occurred at AutoCNM: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static AutoCNMThread go(int delay, String[] sentences) {
        AutoCNMThread thread = new AutoCNMThread(delay, sentences);
        thread.start();
        return thread;
    }
}
