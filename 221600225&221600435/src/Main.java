import java.util.*;
import java.io.*;
public class Main {
    public static void main(String[] args)throws Exception{
        String filePath = "";
        filePath = args[0];

        File firstFile = new File(filePath);

        ResultString result=new ResultString();
        Lib.characterCount(filePath,result);
        Lib.wordMatch(firstFile,result.map,result);
        Lib.lineCount(firstFile,result);
        Lib.wordSort(result.map,result);
        Lib.write(result);
    }
}

class ResultString{

    Map<String, Integer> map = new HashMap<String, Integer>();

    String outString="";
}