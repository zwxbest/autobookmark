package strategy;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;
import com.sun.org.apache.bcel.internal.generic.LSTORE;
import dto.BookmarkWithFontSize;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangweixiao
 * 带有size的提取策略
 */
public class TextExtractionStategyWithSize implements TextExtractionStrategy {

    List<BookmarkWithFontSize> bookmarkWithFontSizes=new ArrayList<BookmarkWithFontSize>();

    private float fontSize;

    public TextExtractionStategyWithSize(List<BookmarkWithFontSize> bookmarkWithFontSizes)
    {
        this.bookmarkWithFontSizes=bookmarkWithFontSizes;
    }

    public String getResultantText() {
        return "";
    }

    public void beginTextBlock() {

    }

    public void renderText(TextRenderInfo renderInfo) {

        //This code assumes that if the baseline changes then we're on a newline
        Vector curBaseline = renderInfo.getDescentLine().getStartPoint();
        Vector topRight = renderInfo.getAscentLine().getEndPoint();
        Rectangle rect = new Rectangle(curBaseline.get(Vector.I1), curBaseline.get(Vector.I2), topRight.get(Vector.I1), topRight.get(Vector.I2));
        String text=renderInfo.getText();
        float fontSize = rect.getHeight();
        bookmarkWithFontSizes.add(new BookmarkWithFontSize(text,fontSize));
    }

    public void endTextBlock() {

    }

    public void renderImage(ImageRenderInfo renderInfo) {

    }
}
