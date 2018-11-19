package com.nizouba.tools;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.SimpleBookmark;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import com.nizouba.utils.PdfUtils;

/**
 * @author zhangweixiao
 */
public class Tools {

    /**
     * 通过正则表达式删除掉生成的错误书签，比如页码，还有代码文字，之类的
     * @param srcName x
     * @param regex x
     * @param dest  x
     * @throws Exception x
     */
    public static void removeByRegex(String srcName,String regex,String dest) throws Exception{
        File srcFile=new File(srcName);
        PdfReader reader = new PdfReader(srcFile.getPath());
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        List<HashMap<String, Object>> list = SimpleBookmark.getBookmark(reader);
        PdfUtils.converterBookmarks(list,regex);
        stamper.setOutlines(list);
        stamper.close();
        reader.close();
        System.out.println("清理书签成功");
    }

    /**
     * 拷贝书签
     * @param copyFrom x
     * @param srcName x
     * @param dest x
     * @throws Exception x
     */
    public static void copyBookmark(String copyFrom,String srcName, String dest) throws Exception{
        PdfReader copyFromReader = new PdfReader(copyFrom);
        List<HashMap<String, Object>> copyFromBookmarkList = SimpleBookmark.getBookmark(copyFromReader);
        PdfReader srcNameReader = new PdfReader(srcName);
        PdfStamper stamper = new PdfStamper(srcNameReader, new FileOutputStream(dest));
        stamper.setOutlines(copyFromBookmarkList);
        stamper.close();
        copyFromReader.close();
        srcNameReader.close();
        System.out.println("复制书签成功");
    }
}
