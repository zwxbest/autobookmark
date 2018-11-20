package com.nizouba.utils;

import com.google.common.collect.Maps;
import com.nizouba.dto.BookmarkWithLevel;
import com.nizouba.itext.LineTextPros;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangweixiao
 */
public class LevelConverterUtil {

    public static BookmarkWithLevel convertFontSize2Level(LineTextPros lineTextPros) {
        BookmarkWithLevel bookmarkWithLevel = new BookmarkWithLevel();
        bookmarkWithLevel.setTitle(lineTextPros.getLineText());
        bookmarkWithLevel.setYOffset(lineTextPros.getYTop());
        bookmarkWithLevel.setFontSize(lineTextPros.getMaxFontSize());
        bookmarkWithLevel.setPageNum(lineTextPros.getPageNo());
        String numberTitle = convertChsToDig(bookmarkWithLevel.getTitle());
        bookmarkWithLevel.setTitle(numberTitle);
        return bookmarkWithLevel;
    }

    private static String convertChsToDig(String chs) {
        if (!chs.startsWith("第")) {
            return chs;
        }
        Map<String, Integer> digs = Maps.newLinkedHashMap();
        digs.put("一", 1);
        digs.put("二", 2);
        digs.put("三", 3);
        digs.put("四", 4);
        digs.put("五", 5);
        digs.put("六", 6);
        digs.put("七", 7);
        digs.put("八", 8);
        digs.put("九", 9);
        digs.put("十", 10);
        digs.put("十一", 11);
        digs.put("十二", 12);
        digs.put("十三", 13);
        digs.put("十四", 14);
        digs.put("十五", 15);
        digs.put("十六", 16);
        digs.put("十七", 17);
        digs.put("十八", 18);
        digs.put("十九", 19);
        digs.put("二十", 20);
        digs.put("二十一", 21);
        digs.put("二十二", 22);
        digs.put("二十三", 23);
        digs.put("二十四", 24);
        digs.put("二十五", 25);
        digs.put("二十六", 26);
        digs.put("二十七", 27);
        digs.put("二十八", 28);
        digs.put("二十九", 29);
        digs.put("三十", 30);
        digs.put("三十一", 31);
        digs.put("三十二", 32);
        digs.put("三十三", 33);
        digs.put("三十四", 34);
        digs.put("三十五", 35);
        digs.put("三十六", 36);
        digs.put("三十七", 37);
        digs.put("三十八", 38);
        digs.put("三十九", 39);
        List<String> collect = digs.keySet().stream().collect(Collectors.toList());
        for (int i = collect.size() - 1; i >= 0; i--) {
            int index = chs.indexOf(collect.get(i));
            if (index != -1) {
                chs = chs.replace(collect.get(i), digs.get(collect.get(i)).toString());
                return chs;
            }
        }
        return chs;
    }

}
