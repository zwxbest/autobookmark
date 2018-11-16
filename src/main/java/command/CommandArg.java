package command;

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
    private String regex;
    /**
     * 从哪本书拷贝
     */
    private String copyFrom;
}
