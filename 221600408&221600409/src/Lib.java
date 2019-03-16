import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class lib {
    public static int countLines,countChars,countWords;
    public static String fileContent = new String();
    public static String path = "C:\\Users\\win\\IdeaProjects\\WordCount\\src\\test\\";
    public static String path2 = "C:\\Users\\win\\IdeaProjects\\WordCount\\src\\test\\result.txt";
    public static TreeMap<String, Integer> records = new TreeMap<>();
    public static LinkedList<String> wordList = new LinkedList<>();
    public static LinkedList<String> hotWordList = new LinkedList<>();

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
            str = "<"+e.getKey()+">: "+e.getValue();
            wordList.add(str);
        }
    }
    //统计字符数
    public static void countChars()throws Exception{
        countChars = 0;
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuffer sbf = new StringBuffer();
        int val;
        while((val=br.read())!=-1){
            if((0<=val && val<=9)){
                countChars++;
            }else if(val == 10){
                continue;
            }else if(11<=val && val<=127){
                countChars++;
            }
            sbf.append((char)val);
        }
        br.close();
        fileContent = sbf.toString().toLowerCase();
    }
    //统计有效行数
    public static void countLines()throws Exception{
        countLines = 0;
        String regex = "\\s*";
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while((line=br.readLine())!=null){
            if(!line.matches(regex)){
                countLines++;
            }
        }
        br.close();
    }
    //统计单词数
    public static void countWords(){
        countWords = 0;
        Pattern expression = Pattern.compile("[a-z]{4,}[a-z0-9]*");
        String str = fileContent;
        Matcher matcher = expression.matcher(str);
        String word;
        while (matcher.find()) {
            word = matcher.group();
            countWords++;
            if (records.containsKey(word)) {
                records.put(word, records.get(word) + 1);
            } else {
                records.put(word, 1);
            }
        }
    }
    //统计热门单词数
    public static void countHotWords(){
        sortWord();
        String str;
        int length = wordList.size();
        if(length > 10){
            for(int i = 0; i < 10; i++){
                str = wordList.get(i);
                hotWordList.add(str);
            }
        }
        else{
            hotWordList.addAll(wordList);
        }
    }
}
