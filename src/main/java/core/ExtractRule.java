package core;

import javafx.beans.binding.SetExpression;
import lombok.Getter;
import lombok.Setter;

/**
 * 提取书签的规则 必须根据字体大小提取
 *
 * @author zhangweixiao
 */
@Getter
public class ExtractRule {

    /**
     * 除了根据字体，再增加正则表达式提取
     */
    @Setter
    public boolean addMarkRegex = false;

    /**
     * 提取要用的正则表达式
     */
    private String extractRegex = null;

    public void setExtractRegex(String extractRegex) throws Exception {
        if (!addMarkRegex) {
            throw new Exception("非正则提取模式，无需设置正则规则");
        }
        this.extractRegex = extractRegex;
    }


}
