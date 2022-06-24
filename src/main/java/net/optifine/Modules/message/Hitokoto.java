package net.optifine.Modules.message;

import net.optifine.Modules.Module;
import net.optifine.Modules.ModuleType;
import net.optifine.Utils.utils.Helper;
import net.optifine.Values.Mode;
import net.optifine.Values.Option;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Hitokoto extends Module {
    private Mode<Enum> from = new Mode<>("Mode", "mode", modes.values(), modes.HitokotoAPI);
    private Option<Boolean> HytPublic = new Option<>("HytPublic", "HytPublic", false);
    private final List<HashMap<String, String >> maps = new ArrayList<>();

    enum modes {
        HitokotoAPI,
        Internal_Beta
    }

    public Hitokoto() {
        super("Hitokoto", Keyboard.KEY_NONE, ModuleType.Message, "Get sentence from HitokotoAPI and send it");
        Chinese = "一言";
        addValues(from, HytPublic);

        {
            HashMap<String, String> tmp =  new HashMap<>();
            tmp.put("hitokoto", "大切な人といつかまた巡り会えますように.");
            tmp.put("from", "プラスティック・メモリーズ");
            maps.add(tmp);
        }
    }

    @Override
    public void enable(){
        try {
            Enum m = from.getValue();
            if (m == modes.HitokotoAPI) {
                net.optifine.Utils.Hitokoto.go();
            } else if (m == modes.Internal_Beta) {
                Random random = new Random();
                HashMap<String, String> hashMap = maps.get(random.nextInt(maps.toArray().length));
                StringBuilder builder = new StringBuilder();
                if (HytPublic.getValue()) builder.append("@");
                builder.append(hashMap.get("hitokoto")).append(" | 来自：").append(hashMap.get("from")).append(" >>KITE HITOKOTO<<");

                mc.thePlayer.sendChatMessage(builder.toString());
            }
        } catch (Exception e) {
            Helper.sendMessage("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }

        state = false;
    }
}
