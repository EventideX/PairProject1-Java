import java.io.*;
import java.util.*;
public class Main {
    public static void main(String args[]) throws IOException {
        //读取文件成n行
        List<StringBuilder> stringBuilders=Lib.readFile(args[args.length-1]);

        //输出字符数abcdefg
        System.out.println("characters: "+Lib.countChars(stringBuilders));

        //统计单词数
        stringBuilders=Lib.removeSpace(stringBuilders);
        Map<String,Integer> hashTable= Lib.countWords(stringBuilders);

        //输出单词数
        Set<String> wordsList=hashTable.keySet();
        int sum=0;
        for(String key:wordsList) {
            sum+=hashTable.get(key);;
        }
        System.out.println("words: " + sum);
        System.out.println("lines: " + stringBuilders.size());
        int i=0;
        for(String key:wordsList){
            System.out.println("<"+key+">:"+hashTable.get(key));
            if(++i>=10)break;
        }
    }


}
