import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import dto.BookmarkWithFontSize;
import po.BookmarkWithLevel;
import strategy.TextExtractionStategyWithSize;
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

        File srcFile=new File("E:\\PDF\\Java\\Spring\\精通Spring+4.x++企业应用开发实战.pdf");
        String dest=srcFile.getParent()+"\\"+srcFile.getName().replaceAll("\\.pdf","").concat("-提取书签").concat(".pdf");
                PdfReader reader = new PdfReader(srcFile.getPath());

        List<BookmarkWithFontSize> bookmarkWithFontSize = PdfUtils.getBookmarkWithFontSize(reader);

        //转换为带层级的
        List<BookmarkWithLevel> bookmarkWithLevels = BookmarkConveter.convertDtoToPo(bookmarkWithFontSize);

        //写入书签
        PdfUtils.createBookmarks(bookmarkWithLevels,reader,dest);

    }

}
