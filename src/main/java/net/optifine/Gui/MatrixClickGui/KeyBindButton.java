package net.optifine.Gui.MatrixClickGui;

import net.optifine.Gui.font.CFontRenderer;
import net.optifine.Manager.FontManager;
import net.optifine.Modules.Module;
import net.minecraft.client.gui.Gui;
import net.optifine.Utils.utils.RenderUtil;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class KeyBindButton extends ValueButton {
    public Module cheat;
    public double opacity = 0.0;
    public boolean bind;

    public KeyBindButton(Module cheat, int x, int y) {
        super(null, x, y);
        this.custom = true;
        this.bind = false;
        this.cheat = cheat;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        CFontRenderer font = FontManager.F18;
        CFontRenderer mfont = FontManager.F16;
        CFontRenderer bigfont = FontManager.F24;
        Gui.drawRect((int) 0.0, (int) 0.0, (int) 0.0, (int) 0.0, 0);
        Gui.drawRect(this.x - 10, this.y - 4, this.x + 80, this.y + 11, RenderUtil.reAlpha(-1, 0.6f));
        mfont.drawString("Bind", this.x - 5, this.y + 2, new Color(24, 23, 23).getRGB());
        mfont.drawString("" + Keyboard.getKeyName(this.cheat.getKey()),
                this.x + 76 - mfont.getStringWidth(Keyboard.getKeyName(this.cheat.getKey())), this.y + 2,
                new Color(24, 23, 23).getRGB());
    }

    @Override
    public void key(char typedChar, int keyCode) {
        if (this.bind) {
            this.cheat.setKey(keyCode);
            if (keyCode == 1) {
                this.cheat.setKey(0);
            }
            ClickUi.binding = false;
            this.bind = false;
        }
        super.key(typedChar, keyCode);
    }

    @Override
    public void click(int mouseX, int mouseY, int button) {
        if (mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6
                && mouseY < this.y + FontManager.F18.getStringHeight(this.cheat.getName()) + 5
                && button == 0) {
            ClickUi.binding = this.bind = !this.bind;
        }
        super.click(mouseX, mouseY, button);
    }
}
