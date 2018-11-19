package com.nizouba.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangweixiao
 * 带文字大小的书签，每行代表一个书签，按照文字的Size分层级
 * 首先随机统计3页文字，取出占比超过50%的文字大小，比如10pt，作为正文文字大小。
 * 为什么要统计3页呢，统计1页防止统计出单独占1页的内容，比如第四章，之类的。2页页感觉有点少，那就暂定3页了。
 * 超过10pt的都是书签，先出父级，再出子级，子级作为父级的子书签。如果再出父级，就上一层。
 * 或者取某一个超过200字的页面
 */
@Getter
@Setter
@AllArgsConstructor
public class BookmarkWithFontSize {

    private String bookmark;

    private Float fontSize;

    private float yOffset;

    private int pageNum;
}
