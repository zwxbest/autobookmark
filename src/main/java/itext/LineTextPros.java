package itext;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 行的属性
 * Created by zwxbest on 2018/11/18.
 */
@Getter
@Setter
@NoArgsConstructor
public class LineTextPros {
    /**
     * 每一行顶部的位置
     */
    private float yTop;
    /**
     * 每行第一块字体大小
     */
    private float firstBlockFontSize;
    /**
     * 每行最大的字体大小
     */
    private float maxFontSize;
    /**
     * 每一行底部的位置
     */
    private float yBottom;
    /**
     * 每行的字体内容
     */
    private String lineText;
    /**
     *  该行所在页码
     */
    private int pageNo;

}
