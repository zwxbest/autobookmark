package utils;

import dto.BookmarkWithFontSize;
import po.BookmarkWithLevel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwxbest on 2018/8/17.
 */
public class Conveter {


    public static List<BookmarkWithLevel> convertDtoToPo(List<BookmarkWithFontSize> bookmarkWithFontSizeList)
    {
        List<BookmarkWithLevel> bookmarkWithLevels=new ArrayList<>();
        for(int i=0;i<bookmarkWithFontSizeList.size();i++)
        {
            BookmarkWithFontSize bookmarkWithFontSize=bookmarkWithFontSizeList.get(i);
            BookmarkWithLevel bookmarkWithLevel=new BookmarkWithLevel();
            bookmarkWithLevel.setTitle(bookmarkWithFontSize.getBookmark());
            bookmarkWithLevel.setYOffset(bookmarkWithFontSize.getYOffset());
            bookmarkWithLevel.setFontSize(bookmarkWithFontSize.getFontSize());
            bookmarkWithLevel.setPageNum(bookmarkWithFontSize.getPageNum());
            for(int j=bookmarkWithLevels.size()-1;j>=0;j--)
            {
                //倒顺找到第一个比当前fontSize大的，认定为parent
                if(bookmarkWithFontSize.getFontSize().compareTo(bookmarkWithLevels.get(j).getFontSize())<0)
                {
                    bookmarkWithLevel.setParent(bookmarkWithLevels.get(j));
                    break;
                }
            }
            bookmarkWithLevels.add(bookmarkWithLevel);
        }
        return bookmarkWithLevels;
    }

}
