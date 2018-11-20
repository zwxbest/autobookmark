package com.nizouba.level;

import com.google.common.collect.Lists;
import com.nizouba.core.config.Config;
import com.nizouba.dto.BookmarkWithLevel;
import com.nizouba.itext.LineTextPros;
import com.nizouba.utils.LevelConverterUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangweixiao
 */
public class FontSizeConverter implements LevelConverter {

    private static Pattern p = Pattern.compile(Config.levelMode.getLevelRegex());

    @Override
    public List<BookmarkWithLevel> convertFontSize2Leve(List<LineTextPros> lineTextProsList) {
        List<BookmarkWithLevel> bookmarkWithLevels = new ArrayList<>();
        for (LineTextPros lineTextPros : lineTextProsList) {
            BookmarkWithLevel bookmarkWithLevel = LevelConverterUtil
                .convertFontSize2Level(lineTextPros);
            Matcher m = p.matcher(bookmarkWithLevel.getTitle());
            if (m.find()) {
                String number = m.group(1);
                bookmarkWithLevel.setNumberInTitles(Lists.newArrayList(number.split("\\.")));
            }
            bookmarkWithLevels.add(bookmarkWithLevel);
            for (int j = bookmarkWithLevels.size() - 1; j >= 0; j--) {
                //按照书签层级找
                boolean findParent = findParentByFontSize(bookmarkWithLevel,
                    bookmarkWithLevels.get(j));
                if (findParent) {
                    bookmarkWithLevel.setParent(bookmarkWithLevels.get(j));
                    break;
                }
            }
        }
        return bookmarkWithLevels;
    }

     static boolean findParentByFontSize(BookmarkWithLevel child,
        BookmarkWithLevel maybeParent) {
        return child.getFontSize() < maybeParent.getFontSize();
    }


}
