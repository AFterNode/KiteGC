package net.optifine.Modules.Config;

import net.optifine.Modules.Module;
import net.optifine.Modules.ModuleType;
import net.optifine.Utils.Hitokoto;
import net.optifine.Vapu.Client;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.util.Map;

public class Title extends Module {
    String original;

    public Title() {
        super("Title Changer", Keyboard.KEY_NONE, ModuleType.Config, "更改游戏窗口标题");
        original = Display.getTitle();
    }

    @Override
    public void enable() {
        Thread hitokoto = new ChangeTitle();
        hitokoto.start();
    }

    @Override
    public void disable() {
        Display.setTitle(original);
    }
}

class ChangeTitle extends Thread {
    public void run() {
        String get = Hitokoto.get("a");
        Map map = Hitokoto.parse(get);
        String content = (String) map.get("hitokoto");
        Display.setTitle(Client.name + " " + Client.version + " | " + content);
    }
}
