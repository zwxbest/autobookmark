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

        String srcParent="E:\\PDF2\\微服务";
        String fileName="分布式服务框架原理与实践_李林锋著.pdf";
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
