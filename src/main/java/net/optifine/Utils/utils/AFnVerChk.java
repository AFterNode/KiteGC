package net.optifine.Utils.utils;

import cn.afternode.api.VersionChecker;
import net.optifine.Vapu.Client;

public class AFnVerChk extends Thread{
    public void run() {
        boolean result = VersionChecker.CheckVersion("21923781892", Client.version);
        if (!result) {
            Helper.sendMessage("当前版本不是最新版或版本检查失败");
            Helper.sendMessage("前往Github了解更多信息：https://github.com/AFterNode/KiteGC");
        }
    }

    public static void go() {
        AFnVerChk thread = new AFnVerChk();
        thread.start();
    }
}
