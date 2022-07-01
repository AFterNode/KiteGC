package net.optifine.Gui.MusicPlayerGUI;

import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;

public class MPUtils {
    Player player;

    public MPUtils(File file) {
        try {
            player = new Player(new FileInputStream(file));
        } catch (Exception ignored) {}
    }

    public void play() {
        try {
            player.play();
        } catch (Exception ignored) {}
    }

    public void stop() {
        player.close();
    }
}
