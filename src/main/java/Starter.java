import com.itextpdf.text.pdf.PdfReader;
import command.CommandArg;
import command.CommandLineHelper;
import dto.BookmarkWithFontSize;
import po.BookmarkWithLevel;
import utils.Conveter;
import utils.PdfUtils;

import java.io.File;
import java.util.List;

/**
 * @author zhangweixiao
 */
public class Starter {

    public static CommandArg arg;

    public static void main(String[] args)  {
        try {
            arg = CommandLineHelper.runCommand(args);
            File srcFile=new File(arg.getFileName());
            String dest=srcFile.getParent()+"\\"+srcFile.getName().replaceAll("\\.pdf","").concat("-nizouba.com(你走吧)").concat(".pdf");

            PdfReader reader = new PdfReader(srcFile.getPath());
            List<BookmarkWithFontSize> bookmarkWithFontSize = PdfUtils.getBookmarkWithFontSize(reader);

            List<BookmarkWithLevel> bookmarkWithLevels = Conveter.convertDtoToPo(bookmarkWithFontSize);

            //写入书签
            PdfUtils.createBookmarks(bookmarkWithLevels,reader,dest);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

}
