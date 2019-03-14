import java.util.*;

public class Result {
    private int char_count = 0;
    private int word_count = 0;
    private int line_count = 0;

    public void char_count_plus() {
        ++char_count;
    }
    public void char_count_plus(int i) {
        if(i>0)
        char_count+=i;
    }
    public int getChar_count() {
        return char_count;
    }

    public void word_count_plus() {
        ++word_count;
    }

    public int getWord_count() {
        return word_count;
    }
    public void line_count_plus() {
        ++line_count;
    }

    public int getLine_count() {
        return line_count;
    }
    public HashMap<String, Integer> resultMap;

    public Result() {
        resultMap = new HashMap<String, Integer>();
    }

    public void addWord(String word) {
        String tempWord = word.toLowerCase();
        if (resultMap.containsKey(tempWord)) {
            resultMap.put(tempWord, resultMap.get(tempWord) +  1);
            word_count_plus();
        } else {
            resultMap.put(tempWord, 1);
            word_count_plus();
        }
    }

    public List<Map.Entry<String, Integer>> sort() {
        //从HashMap恢复entry集合
        //从resultMap.entrySet()创建LinkedList。我们将排序这个链表来解决顺序问题。我们之所以要使用链表来实现这个目的，是因为在链表中插入元素比数组列表更快。
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(resultMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {//通过传递链表和自定义比较器来使用Collections.sort()方法排序链表。
            //降序排序
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if(o2.getValue()==o1.getValue()) return o1.getKey().compareTo(o2.getKey());
                return o2.getValue().compareTo(o1.getValue());//使用自定义比较器，基于entry的值（Entry.getValue()），来排序链表。
            }
        });

//        for (Map.Entry<String, Integer> e: list) {//输出全部
//            System.out.println(e.getKey()+":"+e.getValue());
//        }
        return list;
    }
}

