import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lib extends TextLib {
    //统计字符数
    public static void countChars()throws Exception{
        countChars = 0;
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        int val;
        while((val=br.read())!=-1){
            if((0<=val && val<=9)){
                countChars++;
            }else if(11<=val && val<=127){
                countChars++;
            }
        }
        br.close();
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
