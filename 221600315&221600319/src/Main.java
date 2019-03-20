import java.io.*;
import java.util.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
public class Main {
    public static void main(String args[]) throws IOException {
        //读取文件成n行
        List<StringBuilder> stringBuilders=Lib.readFile(args[args.length-1]);
        BufferedWriter bf=new BufferedWriter(new FileWriter(new File("result.txt")));
        //输出字符数abcdefg
        bf.write(("characters: "+Lib.countChars(stringBuilders)+"\r\n"));
        //System.out.println();

        //统计单词数
        stringBuilders=Lib.removeSpace(stringBuilders);
        Map<String,Integer> hashTable= Lib.countWords(stringBuilders);

        //输出单词数
        Set<String> wordsList=hashTable.keySet();
        int sum=0;
        for(String key:wordsList) {
            sum+=hashTable.get(key);;
        }
        bf.write("words: " + sum+"\r\n");
        bf.write("lines: " + stringBuilders.size()+"\r\n");
        int i=0;
        for(String key:wordsList){
            //System.out.println("<"+key+">:"+hashTable.get(key));
			bf.write("<"+key+">:"+hashTable.get(key)+"\r\n");
            if(++i>=10)break;
        }
        bf.flush();
        bf.close();
    }


}
