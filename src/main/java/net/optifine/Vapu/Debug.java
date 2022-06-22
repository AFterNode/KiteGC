package net.optifine.Vapu;

import net.optifine.Utils.Helper;

public class Debug {
    public static void sendMessage(String Message){
        if(Client.DebugMode){
            Helper.sendMessage(Message);
        }
    }
}
