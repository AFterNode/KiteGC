package net.optifine.Modules.blatant;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.optifine.Modules.Module;
import net.optifine.Modules.ModuleType;
import net.optifine.Utils.Helper;
import org.lwjgl.input.Keyboard;

public class Spammer extends Module {
    private static String content = "KiteGC >> https://afternode.cn/ << VapuLite based";

    public Spammer() {
        super("Spammer", Keyboard.KEY_NONE, ModuleType.Blatant, "Auto send message");
        Chinese = "刷屏";
    }

    public static void setContent(String c) {
        content = c;
    }

    @Override
    public void enable(){
        Helper.sendMessage("按TAB发送设置的消息");
    }

    @SubscribeEvent
    public void keyInput(InputEvent.KeyInputEvent event) {
        if (!this.state) return;
        mc.thePlayer.sendChatMessage(content);
    }
}
