import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import strategy.TextExtractionStategyWithSize;
import utils.PdfUtils;

/**
 * @author zhangweixiao
 */
public class Application {

    public static void main(String[] args) throws Exception {
        PdfReader reader = new PdfReader("F:\\坚果云\\PDF\\Nginx\\[实战Nginx_取代Apache的高性能Web服务器].张宴.扫描版.pdf");

        PdfUtils.getMainBodyFontSize(reader);


    }
}
