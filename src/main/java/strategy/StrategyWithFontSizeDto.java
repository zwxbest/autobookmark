package strategy;

import dto.BookmarkWithFontSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangweixiao
 * 计算书签条目的dto，传入TextExteract策略。
 */
@Getter
@Setter
public class StrategyWithFontSizeDto {

    /**
     * 要提取出来的bookmarkList
     */
    List<BookmarkWithFontSize> bookmarkWithFontSizes=new ArrayList<BookmarkWithFontSize>();
    /**
     * 正文最大的字体，因为代码的字体大小可能更小。
     */
    Float mainBodySize=-1f;

    /**
     * 此页高度，用来计算偏移比例
     */
    Float pageHeight=-1f;

//       /**
     //     * 页码，用来处理最后一个书签的下一行在另一页的情况。
     //     * 如果不传页码，找到大于正文字体的内容，在下一行，下一行的lastYBottom为-1，会new一个stringbuilder
     //     * 如果传上一页的lastYBottom，这个buttom是这页的位置，逻辑不对。
     //     * 所以计算是不是同一行，除了lastYBottom,还要同一页
     //     */
//    int pageNo=-1;

    StringBuilder sb=new StringBuilder();



}
