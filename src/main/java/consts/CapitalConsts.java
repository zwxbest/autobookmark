package consts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangweixiao 大小写转换
 */

public class CapitalConsts {

    public static final Map<String, Integer> numbers =
        new HashMap<String, Integer>() {
            {
                put("一", 1);
                put("二", 2);
                put("三", 3);
                put("四", 4);
                put("五", 5);
                put("六", 6);
                put("七", 7);
                put("八", 8);
                put("九", 9);
                put("十", 10);
                put("十一", 11);
                put("十二", 12);
                put("十三", 13);
                put("十四", 14);
                put("十五", 15);
                put("十六", 16);
                put("十七", 17);
                put("十八", 18);
                put("十九", 19);
                put("二十", 20);
                put("二十一", 21);
                put("二十二", 22);
                put("二十三", 23);
                put("二十四", 24);
                put("二十五", 25);
                put("二十六", 26);
            }
        };


    public static String convertToInt(String name) {
        Set<String> strings1 = numbers.keySet();
        List<String> stringlist = new ArrayList<>(strings1);
        for (int i = stringlist.size() - 1; i >= 0; i--) {
            if (name.contains(stringlist.get(i))) {
                name = name.replace(stringlist.get(i), numbers.get(stringlist.get(i)).toString());
                break;
            }
        }
        return name;
    }

    public static void main(String[] args) {
        System.out.println(convertToInt("第十一章"));
        System.out.println(convertToInt("第二章"));
        System.out.println(convertToInt("第十三章"));
    }

    @AllArgsConstructor
    static class CpaitialPair {

        String capital;
        int number;
    }
}
