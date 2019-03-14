import org.junit.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* CharsLib Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 14, 2019</pre> 
* @version 1.0 
*/ 
public class TestLib extends TextLib {
/** 
* 
* Method: countChars() 
* 
*/ 
    @Test
    public void testCountChars() throws Exception {
    //TODO: Test goes here...
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
        System.out.println(countChars);
    }

    @Test
    public void testCountLines() throws Exception {
        //TODO: Test goes here...
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
        System.out.println(countLines);
    }

    @Test
    public void testCountWords() throws Exception {
        //TODO: Test goes here...
        readFile();
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
        for (Map.Entry<String,Integer> item : records.entrySet()) {
            System.out.println(item.getKey() + ":" + item.getValue());
        }
    }

    @Test
    public void testCountHotWords() throws Exception {
        //TODO: Test goes here...
        readFile();
        Pattern expression = Pattern.compile("[a-z]{4,}[a-z0-9]*");
        String str = fileContent;
        Matcher matcher = expression.matcher(str);
        String word;
        while (matcher.find()) {
            word = matcher.group();
            if (records.containsKey(word)) {
                records.put(word, records.get(word) + 1);
            } else {
                records.put(word, 1);
            }
        }
        sortWord();
        String str2;
        int length = wordList.size();
        if(length > 10){
            for(int i = 0; i < 10; i++){
                str2 = wordList.get(i);
                hotWordList.add(str2);
            }
        }
        else{
            hotWordList.addAll(wordList);
        }
        for (String hotWord : hotWordList) {
            System.out.println(hotWord);
        }
    }

} 
