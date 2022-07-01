package net.optifine.Gui.MusicPlayerGUI;

public class MPThread extends Thread{
    public MainGUI ui;

    public void run() {
        ui = new MainGUI();
    }
}
