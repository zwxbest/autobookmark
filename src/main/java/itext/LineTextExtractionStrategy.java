package itext;

import com.google.common.collect.Lists;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;

import javax.rmi.CORBA.Util;
import java.util.List;

/**
 * 按照行来解析
 * Created by zwxbest on 2018/11/18.
 */
public class LineTextExtractionStrategy implements TextExtractionStrategy {

    private List<LineTextPros> lineTextProsList = Lists.newArrayList();

    private float lastYBottom = -1f;

    private StringBuilder lineText = new StringBuilder();

    private LineTextPros lineTextPros = new LineTextPros();

    private float lastYTop = -1f;

    private float maxFontSize = -1f;

    private float firstBlockFontSize = -1f;

    private int pageNo;

    public LineTextExtractionStrategy(List<LineTextPros> lineTextProsList, int pageNo) {
        this.lineTextProsList = lineTextProsList;
        this.pageNo = pageNo;
    }

    @Override
    public String getResultantText() {
        addLineToList();
        return null;
    }

    private void addLineToList() {
        if (lineText.toString().trim().isEmpty()) {
            return;
        }
        lineTextPros.setMaxFontSize(maxFontSize);
        lineTextPros.setLineText(lineText.toString());
        lineTextPros.setFirstBlockFontSize(firstBlockFontSize);
        lineTextPros.setYBottom(lastYBottom);
        lineTextPros.setYTop(lastYTop);
        lineTextProsList.add(lineTextPros);
    }

    @Override
    public void beginTextBlock() {

    }

    @Override
    public void renderText(TextRenderInfo renderInfo) {

        String blockText = renderInfo.getText();
        float yBottom = Utils.floatRound(renderInfo.getBaseline().getStartPoint().get(Vector.I2), 1);
        float yTop = renderInfo.getAscentLine().getEndPoint().get(Vector.I2);
        float fontSize = Utils.floatRound(Utils.getFontSize(renderInfo), 1);

        if (lastYBottom != yBottom) {
            //保存上一行的数据
            addLineToList();
            //准备新的一行
            lineText = new StringBuilder();
            lineTextPros = new LineTextPros();
            lineTextPros.setPageNo(pageNo);
            maxFontSize = -1f;
            firstBlockFontSize = fontSize;
        }
        if (fontSize > maxFontSize) {
            maxFontSize = fontSize;
        }
        lineText.append(blockText);
        lastYBottom = yBottom;
        lastYTop = yTop;
    }

    @Override
    public void endTextBlock() {

    }

    @Override
    public void renderImage(ImageRenderInfo renderInfo) {

    }
}
