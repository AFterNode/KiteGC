package net.optifine.Modules.world;

import net.optifine.Modules.ModuleType;
import net.optifine.Modules.Module;
import net.optifine.Utils.utils.TimerUtil;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class FastPlace extends Module {
    private final TimerUtil timer = new TimerUtil();
    public FastPlace() {
        super("FastPlace", Keyboard.KEY_NONE, ModuleType.World,"Make you place the blocks faster");
        Chinese="快速放置";
    }

    @SubscribeEvent
    public void onTick(TickEvent event) {
        int key = mc.gameSettings.keyBindUseItem.getKeyCode();

        if (mc.gameSettings.keyBindUseItem.isKeyDown()) {
                if (timer.delay(20)) {
                    mc.thePlayer.swingItem();
                    KeyBinding.onTick(key);
                    mc.thePlayer.getHeldItem().useItemRightClick(mc.theWorld, mc.thePlayer);
                    timer.reset();
                }
            }
        }
    }
