package strategy;

import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;
import dto.BookmarkWithFontSize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangweixiao
 *         随机找3页，找3页出现的FontSize最多的FontSize，作为正文文本，比这个大的就作为书签。
 */
public class TextExtractionStategyFindMainSize implements TextExtractionStrategy {

    private Map<Float, Integer> fontSizeCountMap;

    public TextExtractionStategyFindMainSize(Map<Float, Integer> fontSizeCountMap) {
        this.fontSizeCountMap = fontSizeCountMap;
    }


    public String getResultantText() {
        return "";
    }

    public void beginTextBlock() {

    }

    public void renderText(TextRenderInfo renderInfo) {

        float yDesc=renderInfo.getDescentLine().getStartPoint().get(Vector.I2);
        //这一行的顶线位置
        float yTop = renderInfo.getAscentLine().getEndPoint().get(Vector.I2);
//        float fontSize = Math.round(yTop - yDesc);
        float fontSize = yTop - yDesc;
        Integer fontSizeCount = fontSizeCountMap.get(fontSize);
        if (fontSizeCount == null) {
            fontSizeCountMap.put(fontSize, 1);
        } else {
            fontSizeCountMap.put(fontSize, fontSizeCount + 1);
        }
    }

    public void endTextBlock() {

    }

    public void renderImage(ImageRenderInfo renderInfo) {

    }
}
