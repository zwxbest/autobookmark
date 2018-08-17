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

    //页码，
    int pageNo=-1;




}
