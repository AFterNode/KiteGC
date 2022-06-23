package net.optifine.Modules.player;

import net.optifine.Modules.Module;
import net.optifine.Modules.ModuleType;
import net.optifine.Values.Mode;
import net.optifine.Values.Numbers;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AutoCNM extends Module {
    private AutoCNMThread th;
    public static final HashMap<Enum, String[]> SentenceMap = new HashMap<>();
    public static boolean status;

    private Mode<Enum> mode = new Mode<>("Mode", "mode", Sentences.values(), Sentences.Normal);
    private Numbers<Double> delay = new Numbers<>("Delay", "Delay", 5.0, 3.0, 20.0, 5.0);

    enum Sentences{
        Normal
    }

    public AutoCNM() {
        super("AutoCNM", Keyboard.KEY_NONE, ModuleType.Player, "CNMB");
        Chinese = "自动骂人";

        SentenceMap.put(Sentences.Normal, LTap.LMessages);
        this.addValues(mode);
        this.addValues(delay);
    }

    @Override
    public void enable(){
        th = AutoCNMThread.go(delay.getValue(),
                SentenceMap.get(mode.getValue()));
        status = this.state;
    }

    @Override
    public void disable() {
        status = this.state;
    }
}

class AutoCNMThread extends Thread {
    private final double delay;
    private final String[] sentences;

    AutoCNMThread(double d, String[] s) {
        delay = d;
        sentences = s;
    }

    public void run() {
        while (true) {
            int tmp_delay = new Double(delay).intValue() * 1000;
            try {
                if (!AutoCNM.status) break;
                Random r = new Random();
                AutoCNM.mc.thePlayer.sendChatMessage(sentences[r.nextInt(sentences.length)]);
                TimeUnit.MILLISECONDS.sleep(tmp_delay);
            } catch (Exception e) {
                System.out.println("[Kite] Exception occurred at AutoCNM: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static AutoCNMThread go(double delay, String[] sentences) {
        AutoCNMThread thread = new AutoCNMThread(delay, sentences);
        thread.start();
        return thread;
    }
}
