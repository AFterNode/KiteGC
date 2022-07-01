package net.optifine.Gui.MusicPlayerGUI;

import javax.swing.*;
import java.awt.*;

public class FileSelectorUI extends FileDialog {
    public FileSelectorUI(JFrame parent) {
        super(parent, "选择文件");
        setVisible(true);
        setMultipleMode(true);
        setAlwaysOnTop(true);
        setDirectory("%HOMEPATH%\\Music");
    }
}
