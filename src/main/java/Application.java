import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import dto.BookmarkWithFontSize;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Locale;
import po.BookmarkWithLevel;
import strategy.TextExtractionStategyWithSize;
import sun.util.resources.LocaleData;
import utils.BookmarkConveter;
import utils.PdfUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zhangweixiao
 */
public class Application {

    public static void main(String[] args) throws Exception {

//        LocalDate date1=LocalDate.of(2018,1,1).minusMonths(1).withDayOfMonth(1);
//        System.out.println(date1);

         String srcParent="F:\\坚果云\\PDF\\Java\\Spring";
        String fileName="Beginning.Spring-Spring入门经典.pdf";
        File srcFile=new File(srcParent,fileName);
        String dest=srcFile.getParent()+"\\"+srcFile.getName().replaceAll("\\.pdf","").concat("-提取书签").concat(".pdf");
                PdfReader reader = new PdfReader(srcFile.getPath());

        List<BookmarkWithFontSize> bookmarkWithFontSize = PdfUtils.getBookmarkWithFontSize(reader);

        //转换为带层级的
        List<BookmarkWithLevel> bookmarkWithLevels = BookmarkConveter.convertDtoToPo(bookmarkWithFontSize);

        //写入书签
        PdfUtils.createBookmarks(bookmarkWithLevels,reader,dest);

    }

}
