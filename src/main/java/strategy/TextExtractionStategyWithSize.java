package strategy;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
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


    private Float lastyBottom = -1f;

    private Float lastFontSize = -1f;

    private Float lastyTop = -1f;

    private StringBuilder sb=new StringBuilder();

    /**
     * lastyBottom保存的是上一行文本书签，lastPlainBottom保存的是文本的bottom，
     * 用来判断是否换行，换行了就重置startOfLine，当找到第一个大字号字体的字时，如果并非一行的开头，就不加入。
     */
    private Float lastPlainBottom = -1f;


    private StrategyWithFontSizeDto dto;

    //不大于正文字体的文字在这一行的位置
    private int startMainBodySizeOfLine;


    public TextExtractionStategyWithSize(StrategyWithFontSizeDto dto) {
        this.dto = dto;
    }

    public String getResultantText() {

        if(!sb.toString().equals(""))
        {
            dto.getBookmarkWithFontSizes().add(new BookmarkWithFontSize(sb.toString(), lastFontSize, lastyTop,dto.getPageNo()));
        }
        return "";
    }

    public void beginTextBlock() {
    }

    /**
     * 这里是一个一个字进来的，会被调用多次，最后返给getResultantText
     *
     * @param renderInfo
     */
    public void renderText(TextRenderInfo renderInfo) {
        //这一行的底线位置
        float yBottom = renderInfo.getBaseline().getStartPoint().get(Vector.I2);
        //计算fontSize的位置
        float yDesc = renderInfo.getDescentLine().getStartPoint().get(Vector.I2);
        //这一行的顶线位置
        float yTop = renderInfo.getAscentLine().getEndPoint().get(Vector.I2);
        String text = renderInfo.getText();
        Float fontSize = (float) Math.round(yTop - yDesc);
        //比正文字体大，作为书签
//        System.out.println(renderInfo.getFont().getFontDescriptor(BaseFont.FONT_WEIGHT,1000));

        if (!lastPlainBottom.equals(-1f) && !lastPlainBottom.equals(yBottom)) {
            startMainBodySizeOfLine = 0;
        }

        if (fontSize.compareTo(dto.getMainBodySize()) > 0) {

            if (!lastyBottom.equals(-1f) && !lastyBottom.equals(yBottom)) {
                if(!sb.toString().equals(""))
                {
                    dto.getBookmarkWithFontSizes().add(new BookmarkWithFontSize(sb.toString(), lastFontSize, lastyTop,dto.getPageNo()));
                }
                sb=new StringBuilder();
            }

            if (startMainBodySizeOfLine == 0) {
                lastFontSize = fontSize;
                lastyTop = yTop;
                lastyBottom = yBottom;
                sb.append(text);
            }

        } else {
//            if (!lastPlainBottom.equals(-1f) && !lastPlainBottom.equals(yBottom)) {
//                startMainBodySizeOfLine = 0;
//            }
            startMainBodySizeOfLine++;//这一行的第几个字，只有从第一个开始才算取章节
            lastPlainBottom = yBottom;
        }

    }

    public void endTextBlock() {
    }

    public void renderImage(ImageRenderInfo renderInfo) {

    }
}
