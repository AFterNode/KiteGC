package net.optifine.Gui.MusicPlayerGUI;

import javazoom.jl.player.Player;
import net.optifine.Utils.utils.Helper;

import java.io.File;
import java.io.FileInputStream;

public class MPUtils extends Thread{
    Player player;
    File[] playList;
    public MPStatus status;

    public MPUtils(File[] files) {
        playList = files;
    }

    public void run() {
        try {
            for (File file: playList) {
                Helper.sendMessage("Current playing: " + file.getName());
                player = new Player(new FileInputStream(file));
                status = MPStatus.Playing;
                player.play();
            }
        } catch (Exception ignored) {}
    }

    public void play() {
        try {
            start();
        } catch (Exception ignored) {}
    }

    public void stopPlay() {
        player.close();
        status = MPStatus.Stopped;
    }
}
