package net.optifine.API;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.optifine.Manager.FontManager;
import net.optifine.Vapu.Client;

import java.awt.*;

public interface Theme {
    Minecraft mc = Minecraft.getMinecraft();

    String getName();
    void render(float newWidth, float newHeight);

    void renderWatermark();

    static float renderBackground(float newWidth, float yIndex, String renderName, int stringWidth, Entity renderViewEntity) {
        float margin = 3.0f;
        float x;

        if (Client.isMinecraftFont)
            x = newWidth - stringWidth - (4.5f);
        else
            x = newWidth - FontManager.F16.getStringWidth(renderName) - (4.5f);

        if (Client.isMinecraftFont) {
            WorldRenderUtils.drawRects((int) (x - margin + 1), (int) yIndex + (10 - 11), x + stringWidth + margin + 5, yIndex + 10, new Color(0, 0, 0, (int) 170));
        } else {
            WorldRenderUtils.drawRects((int) (x - margin + 1), (int) yIndex  + (10 - 11), x + FontManager.F16.getStringWidth(renderName) + margin + 5, yIndex + 10, new Color(0, 0, 0, (int) 170));
        }

        return x;
    }
}
