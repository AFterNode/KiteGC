package net.optifine.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public final class Misc {
	public static ScaledResolution getScaledResolution() {
		return new ScaledResolution(Minecraft.getMinecraft());
	}
}
