import com.itextpdf.text.pdf.PdfReader;
import dto.BookmarkWithFontSize;
import po.BookmarkWithLevel;
import utils.Conveter;
import utils.PdfUtils;

import java.io.File;
import java.util.List;

/**
 * @author zhangweixiao
 */
public class Application {

    public static void main(String[] args) throws Exception {

//        LocalDate date1=LocalDate.of(2018,1,1).minusMonths(1).withDayOfMonth(1);
//        System.out.println(date1);

         String srcParent="F:\\PDF";
        String fileName="[SQL基础教程.第3版].(冯宇晖&贾文峰).扫描版.pdf";
        File srcFile=new File(srcParent,fileName);
        String dest=srcFile.getParent()+"\\"+srcFile.getName().replaceAll("\\.pdf","").concat("-提取书签").concat(".pdf");
                PdfReader reader = new PdfReader(srcFile.getPath());

        List<BookmarkWithFontSize> bookmarkWithFontSize = PdfUtils.getBookmarkWithFontSize(reader);

        //转换为带层级的
        List<BookmarkWithLevel> bookmarkWithLevels = Conveter.convertDtoToPo(bookmarkWithFontSize);

        //写入书签
        PdfUtils.createBookmarks(bookmarkWithLevels,reader,dest);

    }

}
