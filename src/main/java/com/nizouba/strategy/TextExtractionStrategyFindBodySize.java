package com.nizouba.strategy;

import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import java.util.Map;
import com.nizouba.utils.PdfUtils;

/**
 * @author zhangweixiao 随机找5页，字体大小出现的频率由大到小排序，遍历，第一个占比超过一定比例的认定为body字体
 */
public class TextExtractionStrategyFindBodySize implements TextExtractionStrategy {

    private Map<Float, Integer> fontSizeCountMap;

    public TextExtractionStrategyFindBodySize(Map<Float, Integer> fontSizeCountMap) {
        this.fontSizeCountMap = fontSizeCountMap;
    }


    @Override
    public String getResultantText() {
        return "";
    }

    @Override
    public void beginTextBlock() {

    }

    @Override
    public void renderText(TextRenderInfo renderInfo) {
        float fontSize = PdfUtils.getFontSize(renderInfo);
        //fontSized的Key不在就设置values为1，在就加上1
        fontSizeCountMap.merge(fontSize, 1, Integer::sum);
    }

    @Override
    public void endTextBlock() {

    }

    @Override
    public void renderImage(ImageRenderInfo renderInfo) {

    }
}
