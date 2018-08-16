package utils;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import dto.BookmarkWithFontSize;
import strategy.TextExtractionStategyWithSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;

/**
 * @author zhangweixiao
 */
public class PdfUtils {

   public static List<BookmarkWithFontSize> bookmarkWithFontSizes=new ArrayList<BookmarkWithFontSize>();

    /**
     *  获取正文的文字大小，超过5行并且都是同样大小字体
     * @param reader
     * @return
     */
    public  static float  getMainBodyFontSize(PdfReader reader) throws Exception
    {
        //总页数
        int numberOfPages = reader.getNumberOfPages();

        //获取文字大小的策略
        TextExtractionStategyWithSize stategyWithSize = new TextExtractionStategyWithSize(bookmarkWithFontSizes);

        //跳过第一页
         int randomPage=new Random().nextInt(numberOfPages)+1;
         PdfTextExtractor.getTextFromPage(reader, randomPage, stategyWithSize);
         return 0;
    }

}
