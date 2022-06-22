package net.optifine.Modules.combat;

import net.minecraft.item.Item;
import net.optifine.Utils.math.ReflectionUtil;
import net.optifine.Vapu.Client;
import net.optifine.Vapu.Debug;
import net.optifine.Modules.ModuleType;
import net.optifine.Modules.Module;
import net.optifine.Utils.TimerUtil;
import net.optifine.Values.Mode;
import net.optifine.Values.Numbers;
import net.optifine.Values.Option;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class AutoPot extends Module {
    private int stage = 0;
    private TimerUtil timer = new TimerUtil();
    private int oldSlot;
    private int potSlot;
    public Numbers<Double> health = new Numbers<Double>("Health", "health", 3.0, 0.0, 10.0, 0.5);
    static boolean currentlyPotting = false;
    public boolean isUsing = true;
    public int slot;
    public AutoPot() {
        super("AutoPot", Keyboard.KEY_NONE, ModuleType.World,"Auto use pot when you low health");
        this.addValues(this.health);
    }


    @Override
    public void enable() {
        super.enable();
        this.timer.reset();
        if (mc.thePlayer == null) {
            this.setState(false);
        } else {
            this.oldSlot = mc.thePlayer.inventory.currentItem;
            this.potSlot = this.getPotionSlot();
        }
    }


    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent e) {
        if(Client.nullCheck())
            return;
        this.oldSlot = mc.thePlayer.inventory.currentItem;
        this.potSlot = this.getPotionSlot();
        if (this.potSlot == -1) {
            this.setState(false);
        }

        float f = this.health.getValue().floatValue();


        if (mc.thePlayer.getHealth() <= f) {
            mc.thePlayer.inventory.currentItem = this.potSlot;
            ReflectionUtil.rightClickMouse();
            mc.thePlayer.inventory.currentItem = this.oldSlot;
        }
    }

    public int getPotionSlot() {
        for (int i = 0; i < 8; ++i) {
            ItemStack itemstack = mc.thePlayer.inventory.getStackInSlot(i);

            if (itemstack != null && !itemstack.isStackable()) {
                Item item = itemstack.getItem();

                if (item instanceof ItemPotion) {
                    ItemPotion itempotion = (ItemPotion) item;

                    if (ItemPotion.isSplash(itemstack.getMetadata())) {
                        return i;
                    }
                }
            }
        }

        return -1;
    }

}
