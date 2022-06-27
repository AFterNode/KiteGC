package net.optifine.Modules.combat;

import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraftforge.common.MinecraftForge;
import net.optifine.Modules.ModuleType;
import net.optifine.Modules.Module;
import net.optifine.Utils.TimerUtil;
import net.optifine.Values.Mode;
import net.optifine.Values.Numbers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.optifine.Values.Option;
import org.lwjgl.input.Keyboard;

import java.util.Random;

public class AutoClicker extends Module {
    private final TimerUtil timer = new TimerUtil();
    private Numbers<Double> cps = new Numbers<Double>("CPS", "Cps",5.0, 1.0, 20.0,1.0);
    private Option<Boolean> autoblock = new Option<Boolean>("AutoBlock","AutoBlock", false);
    private Mode<Enum> mode = new Mode("Mode", "Mode", modes.values(), modes.AFnKite);
    public boolean doBlock = true;

    enum modes{
        AFnKite,
        VapuLite
    }

    public AutoClicker() {
        super("AutoClicker", Keyboard.KEY_K, ModuleType.Combat,"auto Attack when you hold the attack button");
        this.addValues(this.cps,this.autoblock,this.mode);
        Chinese="连点器";
        MinecraftForge.EVENT_BUS.register(this);
    }

    // VapuLite版
    @SubscribeEvent
    public void onTick(TickEvent event) {
        if (mode.getValue() != modes.VapuLite) return;
        int key = mc.gameSettings.keyBindAttack.getKeyCode();
        if (mc.gameSettings.keyBindAttack.isKeyDown()) {
            float delays = new Random().nextInt(this.cps.getValue().intValue()) + 2;
            if (timer.delay(delays * 10)) {
                mc.thePlayer.swingItem();
                KeyBinding.onTick(key);
                try {
                    if(mc.objectMouseOver.entityHit != null) {
                        mc.playerController.attackEntity(mc.thePlayer, Minecraft.getMinecraft().objectMouseOver.entityHit);
                        if(this.doBlock){
                            this.doBlock = !this.doBlock;
                        } else {
                            this.doBlock = true;
                        }
                        if (this.doBlock) {
                            if (mc.thePlayer.getCurrentEquippedItem() == null) {
                                return;
                            }
                            if (!(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) {
                                return;
                            }
                            mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                timer.reset();
            }
        }
    }

    // AFnKite版
    @SubscribeEvent
    public void AFnKiteAC(TickEvent event) {
        if (mode.getValue() != modes.AFnKite) return;
        ACThread thread = new ACThread(cps.getValue(), this);
        thread.start();
    }
}

class ACThread extends Thread {
    final int cps;
    final double delay_s;
    final AutoClicker ac;

    ACThread(double c, AutoClicker a){
        cps = new Double(c).intValue();
        delay_s = new Double(1 / cps).intValue();
        ac = a;
    }

    public void run() {
        int tmp = 0;
        while (tmp <= cps) {
            AutoClicker.mc.thePlayer.swingItem();
            try {
                if(AutoClicker.mc.objectMouseOver.entityHit != null) {
                    AutoClicker.mc.playerController.attackEntity(AutoClicker.mc.thePlayer, Minecraft.getMinecraft().objectMouseOver.entityHit);
                    if(ac.doBlock){
                        ac.doBlock = !ac.doBlock;
                    } else {
                        ac.doBlock = true;
                    }
                    if (ac.doBlock) {
                        if (AutoClicker.mc.thePlayer.getCurrentEquippedItem() == null) {
                            return;
                        }
                        if (!(AutoClicker.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) {
                            return;
                        }
                        AutoClicker.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(AutoClicker.mc.thePlayer.inventory.getCurrentItem()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
