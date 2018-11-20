package com.nizouba.level;

import static com.nizouba.level.FontSizeConverter.findParentByFontSize;
import static com.nizouba.level.LevelRegexConveter.findParentByRegex;

import com.nizouba.dto.BookmarkWithLevel;
import com.nizouba.itext.LineTextPros;
import com.nizouba.utils.LevelConverterUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangweixiao
 */
public class RegexFontLevelConverter implements LevelConverter {

    @Override
    public List<BookmarkWithLevel> convertFontSize2Leve(List<LineTextPros> lineTextProsList) {
        List<BookmarkWithLevel> bookmarkWithLevels = new ArrayList<>();
        for (LineTextPros lineTextPros : lineTextProsList) {
            BookmarkWithLevel bookmarkWithLevel = LevelConverterUtil
                .convertFontSize2Level(lineTextPros);
            bookmarkWithLevels.add(bookmarkWithLevel);

            for (int j = bookmarkWithLevels.size() - 1; j >= 0; j--) {
                //按照书签层级找
                boolean findParent = findParentByRegexAndFontSize(bookmarkWithLevel,
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
     * 混合模式，当child解析的正则为空或者按照正则没找到时，就按照字体找爹妈
     */
    private static boolean findParentByRegexAndFontSize(BookmarkWithLevel child,
        BookmarkWithLevel maybeParent) {
        if (child.getNumberInTitles().isEmpty()) {
            return findParentByFontSize(child, maybeParent);
        } else {
            return findParentByRegex(child, maybeParent) | findParentByFontSize(child, maybeParent);
        }
    }
}
