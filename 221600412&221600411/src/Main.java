package soft.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static soft.src.Lib.*;

/**
 * @program: java8
 * @description:
 * @author: ChenYu
 * @create: 2019-03-10 22:56
 **/
public class Main {
    public static void main(String args[]) throws IOException {
        args = new String[5];
        args[1] = "G:\\DubboProject\\SuanFa\\src\\main\\java\\soft\\input.txt";
        String content = readFile(args[1]);
        countChar(content);  // 计算 字符数
        countWord(content);  // 计算单词总数
        countLine(content); // 计算行数
        countMostWord();// 计算词频
    }

    // 读取文件 内容并返回
    public static String readFile(String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        FileInputStream in = new FileInputStream(new File(fileName));
        int length = 0 ;
        byte[] buf = new byte[1024];  //建立缓存数组，缓存数组的大小一般都是1024的整数倍，理论上越大效率越好
        while ((length = in.read(buf))!=-1) {
            sb.append(new String(buf,0 ,length));
        }
        in.close(); //关闭资源
        return sb.toString();
    }

}
