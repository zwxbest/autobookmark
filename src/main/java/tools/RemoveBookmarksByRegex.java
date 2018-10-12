package tools;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.SimpleBookmark;
import po.BookmarkWithLevel;
import utils.PdfUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zwxbest on 2018/8/21
 * 通过正则表达式删除掉生成的错误书签，比如页码，还有代码文字，之类的
 */
public class RemoveBookmarksByRegex {

    public static void main(String[] args) throws Exception{
        String parent="F:\\坚果云\\PDF\\Java\\基础";
        String filename="《Java核心技术 卷1 基础知识（原书第9版）》（完整中文版）.pdf";
        String regex="";
        File srcFile=new File(parent,filename);
        String dest=srcFile.getParent()+"\\"+srcFile.getName().replaceAll("\\.pdf","").concat("-清理书签").concat(".pdf");
        PdfReader reader = new PdfReader(srcFile.getPath());
        List<HashMap<String, Object>> list = SimpleBookmark.getBookmark(reader);
        List<BookmarkWithLevel> levels=new ArrayList<>();
        PdfUtils.converterBookmarks(list,levels,null);
        List<BookmarkWithLevel> bookmarkWithLevels = PdfUtils.filterBookmarks(levels, "<");
        System.out.println(1);
    }
}
