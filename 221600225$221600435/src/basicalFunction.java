import java.util.*;
import java.io.*;

public class basicalFunction {
    public static void main(String[] args)throws Exception{
            System.out.println(fileReader());
    }

    private static int  fileReader(){
        String filePath1="D:/java/input2.txt";
        FileReader reader=null;
        int character=0;

        try{
            reader = new FileReader(filePath1);
            int ch=-1;
            while((ch=reader.read())!=-1){
                        if((ch>=0&&ch<=12)||(ch>=14&&ch<=127))character++;
                        System.out.println((char)ch);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader!=null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("关闭流失败");
                }
            }
        }
        return character;
    }
}
