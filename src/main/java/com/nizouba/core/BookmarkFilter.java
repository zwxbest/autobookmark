package com.nizouba.core;

import com.nizouba.itext.LineTextPros;

/**
 * 书签过滤器，过滤不符合要求的书签
 * Created by zwxbest on 2018/11/18.
 */
public interface BookmarkFilter {
    boolean filter(LineTextPros lineText);
}
