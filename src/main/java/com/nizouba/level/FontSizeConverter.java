package com.nizouba.level;

import com.nizouba.dto.BookmarkWithLevel;
import com.nizouba.itext.LineTextPros;
import com.nizouba.utils.LevelConverterUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangweixiao
 */
public class FontSizeConverter implements LevelConverter {


    @Override
    public List<BookmarkWithLevel> convertFontSize2Leve(List<LineTextPros> lineTextProsList) {
        List<BookmarkWithLevel> bookmarkWithLevels = new ArrayList<>();
        for (LineTextPros lineTextPros : lineTextProsList) {
            BookmarkWithLevel bookmarkWithLevel = LevelConverterUtil
                .convertFontSize2Level(lineTextPros);
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
