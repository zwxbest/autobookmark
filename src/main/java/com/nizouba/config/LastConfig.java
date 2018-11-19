package com.nizouba.config;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author zhangweixiao
 */

public class LastConfig {

    public static String lastPath;

    static {
        try {
            Properties properties = new Properties();
            // 使用ClassLoader加载properties配置文件生成对应的输入流
            InputStream in = LastConfig.class.getClassLoader()
                .getResourceAsStream("config.properties");
            // 使用properties对象加载输入流
            properties.load(in);
            //获取key对应的value值
            lastPath = properties.getProperty("lastPath");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
