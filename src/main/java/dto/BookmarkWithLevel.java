package dto;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author zhangweixiao
 * 有层级的bookmark，可以写入PDF书签中
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    private List<String> numberInTitles = Lists.newArrayList();

    private List<BookmarkWithLevel> childs;
}
