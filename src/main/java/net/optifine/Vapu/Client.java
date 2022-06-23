package net.optifine.Vapu;

import net.optifine.Manager.ConfigManager;
import net.optifine.Manager.FileManager;
import net.optifine.Command.*;
import net.optifine.Manager.FriendManager;
import net.optifine.Modules.Module;
import net.optifine.Manager.ModuleManager;
import net.optifine.Utils.utils.Helper;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static net.optifine.Utils.utils.Helper.mc;

public class Client {
    public static boolean DebugMode = false;
    // 调试时可以启用，注入成功会修改标题

    public static String name = "Kite";
    public static String real_name = "GiteGC";
    public static String version = "1.0.0";
    public static String config = "default";

    public static Color VapeGuiTheme = new Color(0, 156, 161, 255);
    public static int Theme = VapeGuiTheme.getRGB();
    public static int ThemeR = VapeGuiTheme.getRed();
    public static int ThemeG = VapeGuiTheme.getGreen();
    public static int ThemeB = VapeGuiTheme.getBlue();

    public static boolean MessageON = true;
    public static boolean StringBigSnakeDetection = false;
    public static boolean AutoBlock = false;
    public static boolean ChatBypass = false;
    public static boolean FluxTheme = false;
    public static boolean CHINESE = false;
    public static boolean isMinecraftFont = false;
    public static Client instance;
    public static boolean state = false;
    public static Random rand=new Random();
    public final FileManager fileManager = new FileManager();
    public static ModuleManager moduleManager = new ModuleManager();
    public List<String> faList = new ArrayList<String>();
    public void updateFA() {
        if(Module.mc.theWorld==null || Module.mc.thePlayer==null) {
            faList.clear();
            return;
        }
        if (faList.size()>0) {
            String msg = faList.remove(0);
            Module.mc.thePlayer.sendChatMessage(msg);
        }
    }
    public Client() throws IOException {
        if (state) return;
        state = true;
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        instance = this;
        CommandInit();
        ConfigManager.init();
        FriendManager.init();
    }

    public static boolean nullCheck() {
        return mc.thePlayer == null || mc.theWorld == null;
    }



    private void CommandInit() {
        ClientCommandHandler.instance.registerCommand(new manbox(Client.instance));
        ClientCommandHandler.instance.registerCommand(new womanbox(Client.instance));
        ClientCommandHandler.instance.registerCommand(new Bind(Client.instance));
        ClientCommandHandler.instance.registerCommand(new WaterMark(Client.instance));
        ClientCommandHandler.instance.registerCommand(new saylocal(Client.instance));
        ClientCommandHandler.instance.registerCommand(new phone(Client.instance));
        ClientCommandHandler.instance.registerCommand(new friend(Client.instance));
        ClientCommandHandler.instance.registerCommand(new Toggle(Client.instance));
        ClientCommandHandler.instance.registerCommand(new t(Client.instance));
        ClientCommandHandler.instance.registerCommand(new SpammerContent());
    }

    @SubscribeEvent
    public void keyInput(InputEvent.KeyInputEvent event) {
        for(Module m : moduleManager.getModules()) {
            if(Keyboard.isKeyDown(m.key) && m.getKey() != Keyboard.KEY_NONE) {
                m.toggle();
            }
//            if(Keyboard.isKeyDown(m.key) && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
//                mc.displayGuiScreen(new BindScreen(m));
//            }
        }
    }

    public static void SaveConfig() throws IOException {
        Client.instance.fileManager.saveModules();
    }

    public static void LoadConfig() throws IOException {
        Client.instance.fileManager.loadModules();
    }

    public static void unInject() {
        state = false;
        if (instance != null) {
            MinecraftForge.EVENT_BUS.unregister(instance);
            instance=null;
        }
    }
}
