import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Lib {
    private static final int HAN_ZI=0x80;

    /**
     * 统计字符数
     * @param stringList
     * @return
     */
    public static int countChars(List<StringBuilder> stringList) {
        int sum=0;
        for(StringBuilder line :stringList){
            sum+=line.length();
        }
        return sum;
    }

    /**
     * 读取文件到ListStringBuilder中
     * @param arg
     * @return
     * @throws IOException
     */
    public static List<StringBuilder> readFile(String arg) throws IOException {
        BufferedInputStream file=new BufferedInputStream(new FileInputStream(arg));
        List<StringBuilder> stringBuilders=new ArrayList<>();
        int pre=0,b;
        StringBuilder builder=new StringBuilder();
        while((b=file.read())!=-1){
            if(pre=='\r'&&b=='\n'){
                //将\r\n改为\n
                builder.append((char)b);
                stringBuilders.add(builder);
                builder=new StringBuilder();
            }else if(b<HAN_ZI){
                if(b<='Z'&&b>='A')b+=32;
                builder.append((char)b);
            }
            pre=b;
        }
        stringBuilders.add(builder);
        for(StringBuilder line:stringBuilders){
            if(line.lastIndexOf("\r\n")!=-1){
                line.deleteCharAt(line.length()-2);
            }
        }
        return stringBuilders;
    }

    /**
     * 去除List<StringBuilder>中的空白
     * @param stringBuilders
     * @return
     */
    public static List<StringBuilder> removeSpace(List<StringBuilder> stringBuilders){
        List<StringBuilder> res=new ArrayList<>();
        int i;
        for (StringBuilder stringBuilder : stringBuilders) {
            i = 0;
            while (i < stringBuilder.length()) {
                if (stringBuilder.charAt(i) > 32 && stringBuilder.charAt(i) < 127) {
                    break;
                }
                i++;
            }
            if (i != stringBuilder.length()) {
                res.add(stringBuilder);
            }
        }
        return res;
    }

    /**
     * 统计单词数
     * @param stringList
     * @return
     */
    public static Map<String,Integer> countWords(List<StringBuilder> stringList){
        Map<String,Integer> hashTable=new LinkedHashMap<>();
        int count;
        boolean flag;
        for(StringBuilder line:stringList){
            count=0;
            flag=true;
            for(int i=0;i<line.length();++i){
                if(line.charAt(i)<='z'&&line.charAt(i)>='a'){
                    ++count;
                }else if(line.charAt(i)<='9'&&line.charAt(i)>='0'){
                    if(count<4){
                        flag=false;
                    }
                    ++count;
                }else if(count>3&&flag){
                    String key=line.substring(i-count,i);
                    hashTable.merge(key, 1, (a, b) -> a + b);
                    count=0;
                }else{
                    count=0;
                    flag=true;
                }
            }
        }
        List<Map.Entry<String, Integer>> keyList = new LinkedList<Map.Entry<String, Integer>>(hashTable.entrySet());
        keyList.sort( new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return Integer.compare(o2.getValue().compareTo(o1.getValue()), 0);
            }

        });
        hashTable.clear();
        for(Map.Entry<String, Integer> entry:keyList){
            hashTable.put(entry.getKey(),entry.getValue());
        }
        return hashTable;
    }
}
