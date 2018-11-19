package com.nizouba.core;

import com.nizouba.itext.LineTextPros;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zwxbest on 2018/11/18.
 */
public class BookmarkFilterHandler {

    private List<LineTextPros> lineTextProsList;

    public BookmarkFilterHandler(List<LineTextPros> lineTextProsList) {
        this.lineTextProsList = lineTextProsList;

    }

    /**
     * 根据传入的规则过滤书签
     * @param filter x
     * @return x
     */
    public List<LineTextPros> filter(BookmarkFilter filter) {
        return lineTextProsList.stream().filter(filter::filter).collect(Collectors.toList());

    }

}
