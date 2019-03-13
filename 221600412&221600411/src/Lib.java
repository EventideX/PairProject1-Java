
import java.util.*;

/**
 * @program: java8
 * @description:
 * @author: ChenYu
 * @create: 2019-03-11 16:09
 **/
public class Lib {

    private Map<String, Integer> map = new HashMap<String, Integer>();  // For the word

    public String countChar(String content) {
        return "characters: " + content.replaceAll("\r\n", " ").toCharArray().length;
    }


    public String countWord(String content) {
        int wordNum = 0;
        char[] ch = content.toCharArray();
        int begin = 0, end = 0, len = content.toCharArray().length;
        String str = null;
        for (int i = 0; i < len; i++) {
            boolean flag = !((ch[i] >= 65 && ch[i] <= 90) || (ch[i] >= 97 && ch[i] <= 122) || (ch[i] >= 48 && ch[i] <= 57));
            if (flag || i == 0) {  // If it is a delimiter or the beginning of the calculation
                if (flag) {
                    begin = end = i + 1;
                } else {
                    begin = end = i;
                }
                // Find two delimiters
                while (end < len && ((ch[end] >= 65 && ch[end] <= 90) ||
                        (ch[end] >= 97 && ch[end] <= 122) || (ch[end] >= 48 && ch[end] <= 57))) {
                    end++;
                }
                if (begin != end) {
                    i = end - 1;
                    if (end - begin >= 4) {
                        str = content.substring(begin, end).toLowerCase();
                        boolean isWord = true;
                        for (int j = 0; j < 4; j++) { // If the first four are letters
                            if (str.charAt(j) >= 48 && str.charAt(j) <= 57) {
                                isWord = false;
                                break;
                            }
                        }
                        if (isWord) {
                            wordNum++;
                            if (map.containsKey(str)) {
                                map.put(str, map.get(str) + 1);
                            } else {
                                map.put(str, 1);
                            }
                        }
                    }
                }
            } else {
                continue;
            }
        }
        return "words: " + wordNum;
    }


    // Calculate number of lines
    public String countLine(String content) {
        int len = content.toCharArray().length;
        char[] ch = content.toCharArray();
        int line = 0;
        boolean flag = false;
        for (int i = 0; i < len; i++) {
            while (i + 1 < len) {   // /r/n Text wrap skips calculations
                if ((ch[i] == 13 && ch[i + 1] == 10)) {
                    break;
                }
                if ((ch[i] >= 0 && ch[i] <= 32) || ch[i] == 127) {   // Is a blank character
                    i++;
                    continue;
                } else {
                    i++;
                    flag = true;
                }
            }
            if( i + 1  == len &&  ! ((ch[i] >= 0 && ch[i] <= 32) || ch[i] == 127)){
                flag = true;
            }
            i = i + 1;
            if (flag) {
                line++;
                flag = false;
            }
        }
        return "lines: " + line;
    }


    // Computing word frequency
    public String countMostWord() {
        // Sort the keys in the HashMap and display the sorted results
        List<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        // Sort the keys in the HashMap
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                if (o1.getValue() == o2.getValue()) {
                    return o1.getKey().compareTo(o2.getKey());
                }
                return -(o1.getValue() - o2.getValue());
            }
        });
        StringBuilder sb = new StringBuilder();
        // The output frequency
        for (int i = 0; i < infoIds.size() && i < 10; i++) {
            sb.append("<" + infoIds.get(i).getKey() + ">: " + infoIds.get(i).getValue() + "\r\n");
        }
        return sb.toString();
    }

}
