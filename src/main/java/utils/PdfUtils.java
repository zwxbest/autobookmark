package utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import dto.BookmarkWithFontSize;
import po.BookmarkWithLevel;
import strategy.StrategyWithFontSizeDto;
import strategy.TextExtractionStategyFindMainSize;
import strategy.TextExtractionStategyWithSize;

import java.io.FileOutputStream;
import java.util.*;
import java.util.concurrent.Executors;

/**
 * @author zhangweixiao
 */
public class PdfUtils {




    /**
     * 获取书签的List，包含FontSize,后续用来分层
     */
   public static List<BookmarkWithFontSize> getBookmarkWithFontSize(PdfReader reader) throws Exception
   {
       List<BookmarkWithFontSize> bookmarkWithFontSizes=new ArrayList<BookmarkWithFontSize>();
       float mainBodySize= getMainBodyFontSize(reader);
       //总页数
       int numberOfPages = reader.getNumberOfPages();

       StringBuilder sb=new StringBuilder();

       StrategyWithFontSizeDto dto=new StrategyWithFontSizeDto();
       for(int i=1;i<=numberOfPages;i++)
       {

           dto.setMainBodySize(mainBodySize);
           dto.setPageHeight(reader.getPageSize(i).getHeight());
           dto.setPageNo(i);

           TextExtractionStategyWithSize stategyWithSize = new TextExtractionStategyWithSize(dto);
            PdfTextExtractor.getTextFromPage(reader, i, stategyWithSize);
       }
       return dto.getBookmarkWithFontSizes();
   }
    /**
     *  获取正文的文字大小，超过5行并且都是同样大小字体
     * @param reader
     * @return
     */
    public  static float  getMainBodyFontSize(PdfReader reader) throws Exception
    {
        //总页数
        int numberOfPages = reader.getNumberOfPages();

        Map<Float,Integer> fontSizeCountMap=new TreeMap<>(new Comparator<Float>() {
            @Override
            public int compare(Float o1, Float o2) {
                return -o1.compareTo(o2);
            }
        });
        //按照key倒序
        Map<Float,Integer> fontSizeCountMap2=new TreeMap<>((o1,o2)->-o1.compareTo(o2));
        //获取文字大小的策略
        TextExtractionStategyFindMainSize stategyWithSize = new TextExtractionStategyFindMainSize(fontSizeCountMap);
        //取3张作为样本
        for(int i=0;i<5;i++)
        {
            //跳过第一页
            int randomPage=new Random().nextInt(numberOfPages-1)+1;
            PdfTextExtractor.getTextFromPage(reader, randomPage, stategyWithSize);
        }
        Integer sumCount = fontSizeCountMap.values().stream().reduce(0, (a, b) -> a + b);
        //正文字体大小
        Float mainFontSize=-1f;
        for(Float key:fontSizeCountMap.keySet())
        {
           float count= fontSizeCountMap.get(key);
            if(count/sumCount>0.01)
            {
                mainFontSize=key;
                break;
            }
        }
      return mainFontSize;
    }

    public static void createBookmarks(List<BookmarkWithLevel> booksmarks, PdfReader reader, String dest) throws Exception {
        Document document = new Document();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream(dest));
        document.open();

        PdfOutline root = copy.getRootOutline();

        copy.addDocument(reader);

        PdfAction action;
        copy.freeReader(reader);
        Map<BookmarkWithLevel, PdfOutline> maps = new HashMap<>();
        PdfOutline outline=null;
        PdfOutline parent=root;
        for (BookmarkWithLevel bookMark : booksmarks) {
            //*1.1f是想上面露一点，要不太贴着文字了。
            action = PdfAction.gotoLocalPage(bookMark.getPageNum(), new PdfDestination(
                    PdfDestination.XYZ, -1,bookMark.getYOffset()*1.05f, 0), copy);
            if(bookMark.getParent()!=null)
                parent=maps.get(bookMark.getParent());
            else
                parent=root;
            outline = new PdfOutline(parent, action, bookMark.getTitle(), false);
            maps.put(bookMark, outline);
        }
        reader.close();
        copy.flush();
        copy.close();
        document.close();

    }

}
