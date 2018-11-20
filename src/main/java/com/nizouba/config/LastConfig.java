package com.nizouba.config;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author zhangweixiao
 */

public class LastConfig {

    static Properties properties;

    static {
        try {
            properties = new Properties();
            // 使用ClassLoader加载properties配置文件生成对应的输入流
            InputStream in = LastConfig.class.getClassLoader()
                .getResourceAsStream("config.properties");
            // 使用properties对象加载输入流
            properties.load(in);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static String getProperties(String key) {
        return properties.getProperty(key);
    }

    public static void updateProperties(String key, String value) {
        try {
            OutputStream outputStream = new FileOutputStream(
                LastConfig.class.getClassLoader().getResource(".").getPath() + "config.properties");
            properties.setProperty(key, value);
            properties.store(outputStream, "update");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
