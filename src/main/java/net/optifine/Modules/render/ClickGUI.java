package net.optifine.Modules.render;

import net.optifine.Gui.LuneClickGui.Lune;
import net.optifine.Gui.MatrixClickGui.ClickUi;
import net.optifine.Gui.VapeClickGui.VapeClickGui;
import net.optifine.Values.Mode;
import net.optifine.Modules.ModuleType;
import net.optifine.Modules.Module;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Module {
	private Mode<Enum> mode = new Mode("Mode", "mode", (Enum[]) ClickGUI.GuiMode.values(), (Enum) ClickGUI.GuiMode.Vape);

	static enum GuiMode {
		Vape,
		Lune,
		Matrix
	}
	public ClickGUI() {
		super("ClickGUI", Keyboard.KEY_RSHIFT, ModuleType.Render,"Open ClickGui");
		this.addValues(this.mode);
		Chinese="设置GUI";
		// TODO Auto-generated constructor stub
	}

	@Override
	public void enable() {
		this.setState(false);
		mc.thePlayer.closeScreen();
		if(this.mode.getValue() == GuiMode.Vape) {
			mc.displayGuiScreen(new VapeClickGui());
		} else if(this.mode.getValue() == GuiMode.Lune){
			mc.displayGuiScreen(new Lune());
		} else if(this.mode.getValue() == GuiMode.Matrix){
			mc.displayGuiScreen(new ClickUi());
		}
	}
}
