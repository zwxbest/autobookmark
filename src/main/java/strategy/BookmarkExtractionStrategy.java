package strategy;

import com.google.common.collect.Lists;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;
import com.sun.org.apache.xerces.internal.parsers.DTDParser;
import command.CommandLineHelper;
import consts.RegexConsts;
import dto.BookmarkWithFontSize;
import dto.StrategyWithFontSizeDto;
import java.util.List;
import javafx.application.Application;
import utils.PdfUtils;

/**
 * @author zhangweixiao 提取书签的策略
 */
public class BookmarkExtractionStrategy implements TextExtractionStrategy {

    private float lastYBottom = -1f;

    private Float lastFontSize = -1f;

    private float lastYTop = -1f;

    private StringBuilder sb = new StringBuilder();

    private boolean firstIsBookMark = false;

    /**
     * 一行书签中最大的字体
     */
    private float maxFontSize = -1f;

    private StrategyWithFontSizeDto dto;

    public BookmarkExtractionStrategy(StrategyWithFontSizeDto dto) {
        this.dto = dto;
    }

    @Override
    public String getResultantText() {
        boolean isBookmark = !"".equals(sb.toString());
        if (CommandLineHelper.arg.getMode() == 1) {
            isBookmark &= sb.toString().matches(RegexConsts.BOOKMARK_START_REGEX);
        }
        if (isBookmark) {
            dto.getBookmarkWithFontSizes().add(
                new BookmarkWithFontSize(sb.toString(), maxFontSize, lastYTop, dto.getPageNo()));
        }
        return "";
    }

    @Override
    public void beginTextBlock() {
    }

    /**
     * 这里是一个一个字进来的，会被调用多次，最后返给getResultantText
     */
    @Override
    public void renderText(TextRenderInfo renderInfo) {
        //是否是新的一行
        boolean isNewLine;
        //这一行的底线位置
        float yBottom = renderInfo.getBaseline().getStartPoint().get(Vector.I2);
        //这一行的顶线位置
        float yTop = renderInfo.getAscentLine().getEndPoint().get(Vector.I2);
        String text = renderInfo.getText();

        float fontSize = PdfUtils.getFontSize(renderInfo);
//        if(dto.getPageNo()==28){
//            System.out.println(fontSize+" "+text+" "+lastYBottom+"");
//        }
        //是否是新的一行
        if (lastYBottom != yBottom) {
            isNewLine = true;
            //这行的第一个是不是书签？
            firstIsBookMark =
                fontSize > dto.getBodySize() && (!"".equals(text.trim()));
        } else {
            isNewLine = false;
        }
        if (firstIsBookMark) {
            if (isNewLine) {
                if (!"".equals(sb.toString())&& sb.toString().matches(RegexConsts.BOOKMARK_START_REGEX)) {
                    dto.getBookmarkWithFontSizes().add(
                        new BookmarkWithFontSize(sb.toString(), maxFontSize, lastYTop,
                            dto.getPageNo()));
                    sb = new StringBuilder();
                }
                maxFontSize = -1f;
            }
            sb.append(text);
            if (fontSize > maxFontSize) {
                maxFontSize = fontSize;
            }
            lastYTop = yTop;
            lastFontSize = fontSize;
            lastYBottom = yBottom;
        }

    }

    @Override
    public void endTextBlock() {
    }

    @Override
    public void renderImage(ImageRenderInfo renderInfo) {

    }
}
