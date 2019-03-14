import java.io.*;
import java.util.*;

public class TextLib {
    public static int countLines,countChars,countWords;
    public static String fileContent = new String();
    public static String path = "C:\\Users\\win\\IdeaProjects\\WordCount\\src\\test\\input.txt";
    public static String path2 = "C:\\Users\\win\\IdeaProjects\\WordCount\\src\\test\\result.txt";
    public static TreeMap<String, Integer> records = new TreeMap<>();
    public static LinkedList<String> wordList = new LinkedList<>();
    public static LinkedList<String> hotWordList = new LinkedList<>();

    //读取文本所有字符内容
    public static void readFile()throws Exception{
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuffer sbf = new StringBuffer();
        int val;
        while((val=br.read())!=-1){
            sbf.append((char)val);
        }
        br.close();
        fileContent = sbf.toString().toLowerCase();
    }
    //将统计结果写入result文本
    public static void writeFile()throws Exception{
        File file = new File(path2);
        if(!file.exists()){
            file.createNewFile();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        StringBuilder sb = new StringBuilder();
        sb.append("characters:"+countChars+"\r\n");
        sb.append("words:"+countWords+"\r\n");
        sb.append("lines:"+countLines+"\r\n");
        for(String str : hotWordList){
            sb.append(str+"\r\n");
        }
        bw.write(sb.toString());
        bw.close();
    }
    //对统计完的单词进行排序
    public static void sortWord(){
        List<Map.Entry<String, Integer>> list = new ArrayList<>(records.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        String str;
        for (Map.Entry<String, Integer> e: list) {
            str = e.getKey()+":"+e.getValue();
            wordList.add(str);
        }
    }
}
