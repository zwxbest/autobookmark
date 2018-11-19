package com.nizouba.itext;

import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;

/**
 * Created by zwxbest on 2018/11/18.
 */
public class Utils {
    /**
     * 获取字体大小
     * * @param renderInfo x
     *
     * @return
     */
    public static float getFontSize(TextRenderInfo renderInfo) {
        //底线位置
        float yDesc = renderInfo.getDescentLine().getStartPoint().get(Vector.I2);
        //顶线位置
        float yTop = renderInfo.getAscentLine().getEndPoint().get(Vector.I2);
        return yTop - yDesc;
    }

    /**
     * 保留几位小数
     * @param num
     * @param save 保留的位数
     * @return
     */
    public static float floatRound(float num, int save) {
        int base = save * 10;
        return Math.round(num * base) / base;
    }
}
