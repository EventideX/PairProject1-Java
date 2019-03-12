package soft.src;

import java.util.*;

/**
 * @program: java8
 * @description:
 * @author: ChenYu
 * @create: 2019-03-11 16:09
 **/
public class Lib {

    private static Map<String, Integer> map = new HashMap<String, Integer>();  // 存放单词

    // 计算Ascii
    public static void countChar(String content) {
        System.out.println("characters:" + content.replaceAll("\r\n"," ").toCharArray().length);
    }

    // 计算单词总数
    public static void countWord(String content) {
        int wordNum = 0;
        char[] ch = content.toCharArray();
        int begin = 0, end = 0, len = content.toCharArray().length;
        String str = null;
        for (int i = 0; i < len; i++) {
            boolean flag = !( (ch[i] >= 65 && ch[i] <= 90) || (ch[i] >= 97 && ch[i] <= 122) || (ch[i] >= 48 && ch[i] <= 57) ); // 判断是否是分隔符
            if (flag || i == 0) {  // 如果是分隔符  或者是开头 开始计算
                if (flag) {
                    begin = end = i + 1;
                } else {
                    begin = end = i;
                }
                // 找到俩个 分隔符
                while (end < len && ((ch[end] >= 65 && ch[end] <= 90) ||
                        (ch[end] >= 97 && ch[end] <= 122) || (ch[end] >= 48 && ch[end] <= 57)) ) {
                    end++;
                }
             //   System.out.println(begin + " " + end);

                if (begin != end) {
                    i = end - 1;
                    if (end - begin >= 4) {
                        str = content.substring(begin, end).toLowerCase();
                        // System.out.println(str);
                        boolean isWord = true;
                        for (int j = 0; j < 4 ; j++ ){ // 如果前四个是字母
                            if(str.charAt(j) >= 48 && str.charAt(j) <= 57){
                                isWord = false;
                                break;
                            }
                        }
                        if(isWord){
                            wordNum++;
                            if(map.containsKey(str)){
                                map.put(str,map.get(str)+1);
                            }else {
                                map.put(str,1);
                            }
                        }
                    }
                }
            } else {
                continue;
            }
        }
        System.out.println("words:" + wordNum);   // 输出单词数
    }


    // 计算行数
    public static void countLine(String content) {
        int len = content.toCharArray().length;
        char[] ch = content.toCharArray();
        int line = 0;
        boolean flag = false;
        for (int i = 0 ; i < len; i++){
            while (i+1 < len ){   // /r/n 文本自动换行跳过计算
                if((ch[i] == 13 && ch[i+1] == 10)){
                    i++;
                    break;
                }
                if((ch[i]>=0 && ch[i]<= 32 ) || ch[i] == 127){   // 为空白字符
                    i++;
                    continue;
                }else {
                    i++;
                    flag = true;
                }
            }
            i = i+1;
            if(flag){
                line++;
                flag = false;
            }
        }
        System.out.println("lines:" + line);   // 输出单词数
    }


    // 计算词频
    public static void countMostWord() {
        // 对HashMap中的key 进行排序后  显示排序结果
        List<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        // 对HashMap中的key 进行排序
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                if(o1.getValue() == o2.getValue()){
                    return o1.getKey().compareTo(o2.getKey());
                }
                return - (o1.getValue() - o2.getValue());
            }
        });
        // 输出词频
        for (int i = 0; i < infoIds.size() && i < 10;  i++) {
            System.out.println("<" + infoIds.get(i).getKey() + ">:" + infoIds.get(i).getValue());
        }
    }

}
