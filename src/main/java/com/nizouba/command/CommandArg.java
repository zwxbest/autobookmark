package com.nizouba.command;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangweixiao
 */
@Getter
@Setter
public class CommandArg {
    private int mode;
    private String fileName;
    private Float size;
    /**
     * 清理书签的正则表达式
     */
    private String cleanRegex;
    /**
     * 从哪本书拷贝
     */
    private String copyFrom;

    /**
     * 划分层级的正则表达式，比如1.1.1这种
     */
    private String levelRegex = "^第*(([0-9]+\\.*)+)";
    private String leveRegexSep="\\.";


}
