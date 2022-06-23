package cn.afternode.api;

import jline.internal.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class VersionChecker {
    static final String ApiBase = "https://api.afternode.cn/";

    public static String httpGet(String url) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL reqUrl = new URL(url);
            // 建立连接
            URLConnection conn = reqUrl.openConnection();

            //设置请求头
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            //          conn.setRequestProperty("Connection", "Keep-Alive");//保持长连接
            conn.setDoOutput(true); //设置为true才可以使用conn.getOutputStream().write()
            conn.setDoInput(true); //才可以使用conn.getInputStream().read();

            //写入参数
            out = new PrintWriter(conn.getOutputStream());
            //设置参数，可以直接写&参数，也可以直接传入拼接好的
            out.flush();

            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {// 使用finally块来关闭输出流、输入流
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    public static @Nullable boolean CheckVersion(String key, String version) {
        try {
            String resp = httpGet(ApiBase + "/verchk/" + key);
            HashMap<String, String> map = Json.parse(resp);

            if (map.get("version") != version) {
                return false;
            } else if (map.get("version") == null) {
                System.out.println("[AFnAPI] Unable to check version: null");
                return false;
            }
        } catch (Exception e) {
            System.out.println("[AFnAPI] Unable to check version: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
