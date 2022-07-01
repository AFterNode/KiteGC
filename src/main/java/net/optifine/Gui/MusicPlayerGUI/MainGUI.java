package net.optifine.Gui.MusicPlayerGUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainGUI extends JFrame {
    JPanel mainPanel;
    File current;
    public MPUtils player;

    public MainGUI() {
        setTitle("Kite MP");

        loadMainPanel();
        getContentPane().add(mainPanel);
    }

    public void loadMainPanel() {
        mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        JButton selectFile = new JButton("选择文件");
        selectFile.addActionListener(e -> {
            FileSelectorUI fileSelectorUI = new FileSelectorUI(this);
            current = new File(fileSelectorUI.getDirectory() + fileSelectorUI.getFile());
        });
        mainPanel.add(selectFile);

        JButton play = new JButton("开始/停止");
        play.addActionListener(e -> {
            if (current == null) return;
            if (player != null) {
                player.stop();
                player = null;
                return;
            }

            player = new MPUtils(current);
            player.play();
        });
        mainPanel.add(play);
    }
}
