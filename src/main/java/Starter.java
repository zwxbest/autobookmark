import com.itextpdf.text.pdf.PdfReader;
import com.sun.org.apache.regexp.internal.RE;
import command.CommandArg;
import command.CommandLineHelper;
import dto.BookmarkWithFontSize;
import po.BookmarkWithLevel;
import tools.Tools;
import utils.Conveter;
import utils.PdfUtils;

import java.io.File;
import java.util.List;

/**
 * @author zhangweixiao
 */
public class Starter {

    public static CommandArg arg;

    public static void main(String[] args) throws Exception  {
        arg = CommandLineHelper.runCommand(args);
        File srcFile=new File(arg.getFileName());
        String temp=srcFile.getParent()+"\\"+srcFile.getName().replaceAll("\\.pdf","").concat("temp").concat(".pdf");
        String dest=srcFile.getParent()+"\\"+srcFile.getName().replaceAll("\\.pdf","").concat("-nizouba.com(你走吧)").concat(".pdf");

        if(arg.getRegex()!=null){
             dest=srcFile.getParent()+"\\"+srcFile.getName().replaceAll("\\.pdf","").concat("-nizouba.com(你走吧)-清理书签").concat(".pdf");
            Tools.removeByRegex(arg.getFileName(),arg.getRegex(),dest);
            return;
        }
        if(arg.getCopyFrom()!=null){
            dest=srcFile.getParent()+"\\"+srcFile.getName().replaceAll("\\.pdf","").concat("-nizouba.com(你走吧)-复制书签").concat(".pdf");
            Tools.copyBookmark(arg.getCopyFrom(),arg.getFileName(),dest);
            return;
        }
        PdfReader reader = new PdfReader(srcFile.getPath());
        List<BookmarkWithFontSize> bookmarkWithFontSize = PdfUtils.getBookmarkWithFontSize(reader);

        List<BookmarkWithLevel> bookmarkWithLevels = Conveter.convertDtoToPo(bookmarkWithFontSize);

        //写入书签
        PdfUtils.createBookmarks(bookmarkWithLevels,reader,temp);
        PdfUtils.genWaterMark(temp,dest,"nizouba.png");
        File tempFile = new File(temp);
        if(tempFile.exists()){
            tempFile.delete();
        }
        System.out.println("添加书签成功");
    }

}