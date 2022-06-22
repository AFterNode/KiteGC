package net.optifine.Modules.render;

import net.minecraft.client.gui.Gui;
import net.optifine.Modules.ModuleType;
import net.optifine.Utils.utils.ColorUtils;
import net.optifine.Vapu.Client;
import net.optifine.Modules.Module;
import net.optifine.Manager.FontManager;
import net.optifine.Values.Option;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Comparator;

public class HUD extends Module {
    private Option<Boolean> Health = new Option<Boolean>("Health","Health", false);
    private Option<Boolean> WaterMark = new Option<Boolean>("WaterMark","WaterMark", true);
    private Option<Boolean> ArrayList = new Option<Boolean>("ArrayList","ArrayList", true);
    private int width;
    public HUD() {
        super("HUD", Keyboard.KEY_H, ModuleType.Render,"Show " + Client.name + " HUD Screen");
        this.addValues(this.Health,this.WaterMark,this.ArrayList);
        Chinese="HUD界面";
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        ScaledResolution s = new ScaledResolution(mc);
        int width = new ScaledResolution(mc).getScaledWidth();
        int height = new ScaledResolution(mc).getScaledHeight();
        int y = 1;
        if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiMainMenu)) return;
        if(this.WaterMark.getValue()){
            FontManager.C22.drawStringWithShadow(Client.name,2,2, ColorUtils.rainbow(1));
        }
        ArrayList<Module> modules = new ArrayList<>();
        for (Module m : Client.instance.moduleManager.getModules()) {
            modules.add(m);
        }
        int i = 0;
        if(this.Health.getValue()){
            if (mc.thePlayer.getHealth() >= 0.0f && mc.thePlayer.getHealth() < 10.0f) {
                this.width = 3;
            }
            if (mc.thePlayer.getHealth() >= 10.0f && mc.thePlayer.getHealth() < 100.0f) {
                this.width = 5;
            }
            mc.fontRendererObj.drawStringWithShadow("♥" + MathHelper.ceiling_float_int(mc.thePlayer.getHealth()), (float) (new ScaledResolution(mc).getScaledWidth() / 2 - this.width), (float) (new ScaledResolution(mc).getScaledHeight() / 2 - 15), -1);
        }
        for (Module m : modules) {
            if (m != null && this.ArrayList.getValue()) {
                if (m != null && m.getState()) {
                    int moduleWidth = FontManager.C18.getStringWidth(m.name);
                    FontManager.C18.drawStringWithShadow(m.name, width - moduleWidth - 1, y, ColorUtils.rainbow(2)+i);
                    y += FontManager.C18.getHeight();
                }
            }
        }
    }

}