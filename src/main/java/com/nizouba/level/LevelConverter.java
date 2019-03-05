package com.nizouba.level;

import com.nizouba.dto.BookmarkWithLevel;
import com.nizouba.itext.LineTextPros;

import java.util.List;

/**
 * @author zhangweixiao
 */
public interface LevelConverter {

    List<BookmarkWithLevel> convertFontSize2Leve(List<LineTextPros> lineTextProsList);
}
