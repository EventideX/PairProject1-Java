import java.io.*;
import java.util.*;

public class Main{

    // 行数
    public static int lineNum = 0;

    // 字符数
    public static int charNum = 0;

    // 单词数：至少四个英文字母开头，不区分大小写
    public static int wordNum = 0;

    // 排序好的单词集合
    public static List<Map.Entry<String, Integer>> wordList = null;

    // 输入文件字节数组
    public static byte[] inputFileBytes = null;

    // 排序打印出前几的个数
    public static final int SORT_PRINT_NUM = 10;


    /**
     * 程序入口
     * @param args[0] 输入文件名
     */
    public static void main(String[] args) {
        // 初始化
        String inputFileName = args[0];

        // 读取文件
        inputFileBytes = readFileToBytes(inputFileName);

        Lib core = new Lib(inputFileBytes);

        // 预处理，计算字符数、行数
        core.preproccess();

        // 计算单词数、并将单词装入集合、统计个数
        core.collectWord();

        // 按照单词频率排序
        core.sortWordMap();

        // 获取结果
        charNum = core.getCharNum();
        wordNum = core.getWordNum();
        lineNum = core.getLineNum();
        wordList = core.getSortedList();

        // 打印结果
        printResult();

        // 保存结果
        writeResult();
    }


    /**
     * 功能：读取文件到字节数组中
     *
     * 参数：String fileName 文件名
     *
     * 返回：byte[] bytes 字节数组
     */
    public static byte[] readFileToBytes(String fileName){
        byte[] fileBytes = null;

        try {
            File file = new File(fileName);
            if (file.isFile() && file.exists()){
                FileInputStream reader = new FileInputStream(file);
                Long fileLength = file.length();
                fileBytes = new byte[fileLength.intValue()];
                reader.read(fileBytes);
                reader.close();
            }
        }
        catch(FileNotFoundException e){
            System.out.println("文件不存在");
        }
        catch(Exception e){
            System.out.println("读取文件出错");
            e.printStackTrace();
        }

        return fileBytes;
    }

    /**
     * 功能：打印结果到控制台
     */
    public static void printResult(){
        System.out.println("characters: " + charNum);
        System.out.println("words: " + lineNum);
        System.out.println("lines: " + wordNum);
        int i = 0;
        for (Map.Entry<String, Integer> entry : wordList) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
            if (++ i >= SORT_PRINT_NUM){
                break;
            }
        }
    }

    /**
     * 功能：输出结果到文件中
     */
    public static void writeResult(){
        String resultString = String.format(
            "characters: %d\nwords: %d\nlines: %d\n",
            charNum, wordNum, lineNum
        );
        int i = 0;
        for (Map.Entry<String, Integer> entry : wordList) {
            resultString += String.format("%s: %d\n", entry.getKey(), entry.getValue());
            if (++ i >= SORT_PRINT_NUM){
                break;
            }
        }
        try{
            File outPutFile = new File("result.txt");
            if (! outPutFile.exists()){
                outPutFile.createNewFile();
            }
            FileWriter writter = new FileWriter(outPutFile.getName(), false);
            writter.write(resultString);
            writter.close();
        }catch(Exception e){
            System.out.println("写入文件出错");
            e.printStackTrace();
        }
    }

}