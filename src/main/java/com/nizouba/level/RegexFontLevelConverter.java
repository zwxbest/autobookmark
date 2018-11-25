package com.nizouba.level;

import static com.nizouba.level.FontSizeConverter.findParentByFontSize;
import static com.nizouba.level.LevelRegexConveter.findParentByRegex;

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
public class RegexFontLevelConverter implements LevelConverter {

    private Pattern p = Pattern.compile(Config.configProperties.getLevelMode().getLevelRegex());

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

            boolean findParent = false;
            for (int j = bookmarkWithLevels.size() - 1; j >= 0; j--) {
                //先按照书签层级找
                findParent = findParentByRegex(bookmarkWithLevel,
                    bookmarkWithLevels.get(j));
                if (findParent) {
                    bookmarkWithLevel.setParent(bookmarkWithLevels.get(j));
                    break;
                }
            }
            if (!findParent) {
                for (int j = bookmarkWithLevels.size() - 1; j >= 0; j--) {
                    //先按照书签层级找
                    findParent = findParentByFontSize(bookmarkWithLevel,
                        bookmarkWithLevels.get(j));
                    if (findParent) {
                        bookmarkWithLevel.setParent(bookmarkWithLevels.get(j));
                        break;
                    }
                }
            }
            bookmarkWithLevels.add(bookmarkWithLevel);
        }
        return bookmarkWithLevels;
    }
}
