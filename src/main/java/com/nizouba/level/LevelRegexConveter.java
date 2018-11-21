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
public class LevelRegexConveter  implements LevelConverter{

    private Pattern p = Pattern.compile(Config.levelMode.getLevelRegex());

    @Override
    public List<BookmarkWithLevel> convertFontSize2Leve(List<LineTextPros> lineTextProsList) {
        List<BookmarkWithLevel> bookmarkWithLevels = new ArrayList<>();
        for (LineTextPros lineTextPros : lineTextProsList) {
            BookmarkWithLevel bookmarkWithLevel = LevelConverterUtil
                .convertFontSize2Level(lineTextPros);
            bookmarkWithLevels.add(bookmarkWithLevel);

            Matcher m = p.matcher(bookmarkWithLevel.getTitle());
            if (m.find()) {
                String number = m.group(1);
                bookmarkWithLevel.setNumberInTitles(Lists.newArrayList(number.split("\\.")));
            }
            for (int j = bookmarkWithLevels.size() - 1; j >= 0; j--) {
                //按照书签层级找
                boolean findParent = findParentByRegex(bookmarkWithLevel,
                    bookmarkWithLevels.get(j));
                if (findParent) {
                    bookmarkWithLevel.setParent(bookmarkWithLevels.get(j));
                    break;
                }
            }
        }
        return bookmarkWithLevels;
    }


    /**
     * 根据正则表达式找parent
     *
     * @param child x
     * @param maybeParent x
     * @return x
     */
     static boolean findParentByRegex(BookmarkWithLevel child,
        BookmarkWithLevel maybeParent) {
        List<String> childNumberInTitles = child.getNumberInTitles();
        List<String> maybeParentNumberInTitles = maybeParent.getNumberInTitles();
        if (childNumberInTitles.isEmpty()) {
            return false;
        }
        int childSize = childNumberInTitles.size();
        int maybeParnetSize = maybeParentNumberInTitles.size();
        if (childSize <= maybeParnetSize) {
            return false;
        }
        for (int i = 0; i < maybeParnetSize; i++) {
            if (!childNumberInTitles.get(i).equals(maybeParentNumberInTitles.get(i))) {
                return false;
            }
        }
        return true;
    }

}
