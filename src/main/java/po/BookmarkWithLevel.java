package po;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zhangweixiao
 * 有层级的bookmark，可以写入PDF书签中
 */
@Getter
@Setter
public class BookmarkWithLevel {

    /**
     * 书签
     */
    String title;
    /**
     * parent
     */
    BookmarkWithLevel parent;
    /**
     * 偏移
     */
    float yOffset;

    Float fontSize;

    private int pageNum;
}
