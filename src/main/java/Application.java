import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import strategy.TextExtractionStategyWithSize;
import utils.PdfUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zhangweixiao
 */
public class Application {

    public static void main(String[] args) throws Exception {

        PdfReader reader = new PdfReader("F:\\坚果云\\PDF\\Java\\ElasticSearch\\深入理解ElasticSearch .pdf");

//        PdfUtils.getBookmarkWithFontSize(reader);
        List<Object> o = Arrays.asList(1, "o", new Date());
            aa(o.toArray());


    }

    public static void  aa(Object ...a)
    {
        for (Object o : a) {
            System.out.println(o);
        }
    }
}
