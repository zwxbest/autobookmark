package dto;

import itext.LineTextPros;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by zwxbest on 2018/11/18.
 */
@Getter
@Setter
public class BookmarkPage {
    private int pageNum;
    private List<LineTextPros> lineTextProsList;
}
