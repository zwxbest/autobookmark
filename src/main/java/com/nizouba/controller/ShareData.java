package com.nizouba.controller;

import com.google.common.collect.Maps;
import java.util.Map;
import lombok.Getter;

/**
 * @author zhangweixiao
 */
@Getter
public class ShareData {

    public static String PDF_FILE_NAME = "pdfFileName";

    public static Map<String, Object> shareData = Maps.newHashMap();

}
