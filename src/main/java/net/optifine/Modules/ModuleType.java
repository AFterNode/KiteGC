package net.optifine.Modules;

import net.optifine.Gui.LiquidBaseClickGui.components.SubWindow;
import net.optifine.Vapu.Client;

public enum ModuleType{
    Combat("Combat","战斗类"),
    Blatant("Blatant","暴力类"),
    Render("Render", "视觉类"),
    Movement("Movement", "移动类"),
    Player("Player", "玩家类"),
    World("World", "世界类"),
    Other("Other", "其他类"),
    Message("Message", "消息发送器"),
    Config("Global", "全局设置");

    private final String name;
    private final String ChineseName;
    private final SubWindow window;

    ModuleType(String name, String chineseName) {
        this.name = name;
        this.ChineseName = chineseName;
        this.window = new SubWindow(name);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        if(Client.CHINESE){
            return ChineseName;
        } else {
            return name;
        }
    }
    
    public SubWindow getWindow() {
    	return window;
    }
}
