package core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 划分层级的模式
 *
 * @author zhangweixiao
 */
@Getter
public class LevelMode {


    private String levelRegex;

    @Setter
    private LevelModeEnum levelModeEnum;

    public void setLevelRegex(String levelRegex) throws Exception {
        if (levelModeEnum.equals(LevelModeEnum.FontSizeMode)) {
            throw new Exception("非正则层级模式，无需设置正则规则");
        }
        this.levelRegex = levelRegex;
    }

    /**
     * 提取书签的模式
     *
     * @author zhangweixiao
     */
    @Getter
    @AllArgsConstructor
    enum LevelModeEnum {

        /**
         * 模式
         */
        FontSizeMode(1, "只根据字体大小来提取书签以及划分层级"),
        ChapterMode(2, "根据规则划分层级，例如第一章，1.1等"),
        MixMode(3, "混合模式，先根据规则，如果当前书签不符合规则或者找不到父书签，就按照字体");

        /**
         * 代码
         */
        private int code;
        /**
         * 描述
         */
        private String desc;

    }
}
