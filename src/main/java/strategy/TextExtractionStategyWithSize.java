package strategy;

import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;
import command.CommandLineHelper;
import consts.RegexConsts;
import dto.BookmarkWithFontSize;
import javafx.application.Application;

/**
 * @author zhangweixiao
 *         带有size的提取策略
 */
public class TextExtractionStategyWithSize implements TextExtractionStrategy {

    private Float lastyBottom = -1f;

    private Float lastFontSize = -1f;

    private Float lastyTop = -1f;

    private StringBuilder sb = new StringBuilder();

    private boolean startOfNewLine = false;

    /**
     * lastyBottom保存的是上一行文本书签，lastPlainBottom保存的是文本的bottom，
     * 用来判断是否换行，换行了就重置startOfLine，当找到第一个大字号字体的字时，如果并非一行的开头，就不加入。
     */
//    private Float lastPlainBottom = -1f;


    private StrategyWithFontSizeDto dto;

    //不大于正文字体的文字在这一行的位置
    private int startMainBodySizeOfLine;


    public TextExtractionStategyWithSize(StrategyWithFontSizeDto dto) {
        this.dto = dto;
    }

    public String getResultantText() {

        boolean isBookmark = !sb.toString().equals("");
        if (CommandLineHelper.arg.getMode() == 1) {
            isBookmark &= sb.toString().matches(RegexConsts.bookmarkStartRegex);
        }
        if (isBookmark) {
            dto.getBookmarkWithFontSizes().add(new BookmarkWithFontSize(sb.toString(), lastFontSize, lastyTop, dto.getPageNo()));
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
        Float fontSize = yTop - yDesc;
        //避免一行中非第一个的加大的关键字，这样的不做提取
        if (fontSize.compareTo(dto.getMainBodySize()) <= 0) {
            return;
        }
        if (!lastyBottom.equals(-1f) && !lastyBottom.equals(yBottom)) {
            if (!sb.toString().equals("") && sb.toString().matches(RegexConsts.bookmarkStartRegex)) {
                dto.getBookmarkWithFontSizes().add(new BookmarkWithFontSize(sb.toString(), lastFontSize, lastyTop, dto.getPageNo()));
            }
            sb = new StringBuilder();
            sb.append(text);
            startOfNewLine = false;
        } else {
            startOfNewLine = true;
        }
        lastFontSize = fontSize;
        lastyTop = yTop;
        lastyBottom = yBottom;

    }

    public void endTextBlock() {
    }

    public void renderImage(ImageRenderInfo renderInfo) {

    }
}
