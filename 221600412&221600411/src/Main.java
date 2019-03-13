

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @program: java8
 * @description:
 * @author: ChenYu
 * @create: 2019-03-10 22:56
 **/
public class Main {

    public static void main(String args[]) throws IOException {
        String content  = readFile(args[0]);
        Lib lib = new Lib();
        String str1 = lib.countChar(content);
        String str2 = lib.countWord(content);
        String str3 = lib.countLine(content);
        String str4 = lib.countMostWord();
        System.out.println(str1 + "\r\n" + str2 + "\r\n" + str3 + "\r\n" + str4);
        writeFile("result.txt", str1 + "\r\n" + str2 + "\r\n" + str3 + "\r\n" + str4);
    }

    public static String readFile(String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        FileInputStream in = new FileInputStream(new File(fileName));
        int length = 0 ;
        byte[] buf = new byte[1024];
        while ((length = in.read(buf))!=-1) {
            sb.append(new String(buf,0 ,length));
        }
        in.close();
        return sb.toString();
    }

    public static void writeFile(String fileName, String content) throws IOException {
        File f = new File(fileName);
        if (!f.exists()) {
            f.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(f, false);
        fileWriter.write(content);
        fileWriter.flush();
    }



}
