package net.optifine;

import net.optifine.Utils.Helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class OptifineRenderer {
    public static void inject(Thread[] threads) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        for (Thread thread : threads) {
            if(thread != null) {
                if(thread.getName().equals("Client thread")) {
                    URLClassLoader classLoader = (URLClassLoader) thread.getContextClassLoader();
                    Thread.currentThread().setContextClassLoader(classLoader);
                    try {
                        Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                        if (!method.isAccessible()) method.setAccessible(true);
                        method.invoke(classLoader, OptifineRenderer.class.getProtectionDomain().getCodeSource().getLocation());
                    } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Class<?> clazz = classLoader.loadClass("net.optifine.Vapu.Client");
                    clazz.newInstance();
                    break;
                }
            }
        }
    }
}
