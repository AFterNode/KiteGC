package net.optifine.Modules.other;

import net.optifine.Gui.MusicPlayerGUI.MPThread;
import net.optifine.Gui.MusicPlayerGUI.MainGUI;
import net.optifine.Modules.Module;
import net.optifine.Modules.ModuleType;
import org.lwjgl.input.Keyboard;

public class MusicPlayer extends Module {
    MPThread MPThread;
    MainGUI MPGui;

    public MusicPlayer() {
        super("MusicPlayer", Keyboard.KEY_N, ModuleType.Other, "MP3 Music Player");

        MPThread = new MPThread();

        MPThread.start();
    }

    @Override
    public void enable() {
        MPThread.ui.setVisible(true);
    }

    @Override
    public void disable() {
        MPThread.ui.setVisible(false);
        MPThread.ui.player.stop();
        MPThread.stop();
    }
}
