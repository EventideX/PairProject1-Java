import java.util.*;
import java.io.*;
public class Main {
    public static void main(String[] args)throws Exception{
       String filePath = "";
       filePath = args[0];
        //String filePath = "d:/java/input4.txt";

        File firstFile = new File(filePath);

        ResultString result=new ResultString();
        Lib.characterCount(filePath,result);
        Lib.wordMatch(firstFile,result.map);
        Lib.lineCount(firstFile,result);
        Lib.wordSort(result.map,result);

    }
}

 class ResultString{

     Map<String, Integer> map = new HashMap<String, Integer>();

     String outString="";
}