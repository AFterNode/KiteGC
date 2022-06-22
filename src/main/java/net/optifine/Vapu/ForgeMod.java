package net.optifine.Vapu;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

@Mod(modid="Optifine", name="Optifine", version="2.02", acceptedMinecraftVersions="[1.8.9]")
public class ForgeMod {
	@SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event) throws IOException {
        new Client();
    }
	
	@SideOnly(Side.SERVER)
    @Mod.EventHandler
    public void initServer(FMLPreInitializationEvent event) {
        System.out.println("666主播居然要给服务器安装Vapu暴打玩家是吧");
    }
}
