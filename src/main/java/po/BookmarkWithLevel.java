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
     * 孩子
     */
    List<BookmarkWithLevel> children;

    float yOffsetPercent;
}
