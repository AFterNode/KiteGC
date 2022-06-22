package net.optifine.Modules.other;

import net.optifine.Modules.Module;
import net.optifine.Modules.ModuleType;
import net.optifine.Utils.Helper;
import net.optifine.Values.Mode;
import net.optifine.Vapu.Client;
import net.optifine.Vapu.Debug;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

public class Hitokoto extends Module {
    public Hitokoto() {
        super("Hitokoto", Keyboard.KEY_NONE, ModuleType.Other, "Get sentence from HitokotoAPI and send it");
        Chinese = "一言";
    }

    @Override
    public void enable(){
        String h = net.optifine.Utils.Hitokoto.get("a");
        if (Client.DebugMode) {
            Helper.sendMessage(h);
        }
        if (h.equals("Failed")) return;
        Map hashMap = net.optifine.Utils.Hitokoto.parse(h);

        mc.thePlayer.sendChatMessage((String) hashMap.get("hitokoto") + ">>Kite Hitokoto<<");
        mc.thePlayer.sendChatMessage("来自：" + (String) hashMap.get("from") + ">>Kite Hitokoto<<");
        state = false;
    }
}
