package net.optifine;

import net.optifine.Vapu.Client;
import net.minecraft.launchwrapper.LaunchClassLoader;

import java.lang.instrument.Instrumentation;

public class TextureFilter {
    public static void agentmain(String args, Instrumentation instrumentation) throws Exception {
        for (Class<?> classes : instrumentation.getAllLoadedClasses()) {
            if (classes.getName().startsWith("net.minecraft.client.Minecraft")) {
                LaunchClassLoader classLoader = (LaunchClassLoader)classes.getClassLoader();
                classLoader.addURL(TextureFilter.class.getProtectionDomain().getCodeSource().getLocation());
                Class<?> client = classLoader.loadClass(Client.class.getName());
                client.newInstance();
            }
        }
    }
}