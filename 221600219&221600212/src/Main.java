import java.io.*;
import java.util.*;

public class Main{

    // 行数
    public static int lineNum = 0;

    // 字符数
    public static int charNum = 0;

    // 单词数：至少四个英文字母开头，不区分大小写
    public static int wordNum = 0;

    // 单词集合：<单词, 数目>
    public static Map<String, Integer> wordMap = null;

    // 排序好的单词集合
    public static List<Map.Entry<String, Integer>> wordList = null;

    // 输入文件字节数组
    public static byte[] inputFileBytes= null;

    // 输入文件长度：字节数组长度
    public static int inputFileLength = 0;

    // 排序打印出前几的个数
    public static final int SORT_PRINT_NUM = 10;


    /**
     * 程序入口
     * @param args[0] 输入文件名
     */
    public static void main(String[] args) {
        // 初始化
        String inputFileName = args[0];
        wordMap = new TreeMap<String, Integer>();

        // 读取文件
        inputFileBytes = readFileToBytes(inputFileName);
        inputFileLength = inputFileBytes.length;

        // 预处理，计算字符数、行数
        preproccess();

        // 计算单词数、并将单词装入集合、统计个数
        collectWord();

        // 按照单词频率排序
        sortWordMap();

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

    /**
     * 功能：预处理
     *      将大写字母转为小写字母
     *      计算字节数组中的字符数、包括空字符、//r//n算作一个字符
     *      计算字节数组包含的行数
     */
    public static void preproccess(){
        // 计算字符数、行数
        for (int i = 0; i < inputFileLength; i ++){
            // 预处理，大写字母统一转为小写字母
            if (inputFileBytes[i] >= 65 && inputFileBytes[i] <= 90){
                inputFileBytes[i] += 32;
            }
            if (inputFileBytes[i] == 10){
                // 计算行数
                if (checkLine(inputFileBytes, i)){
                    lineNum ++;
                }
            }else{
                charNum ++;
            }
        }
        // 注意最后一行不以回车结尾的情况，同样算作一行
        if (inputFileBytes[inputFileLength-1] != 10){
            lineNum ++;
        }
    }

    /**
     * 功能：计算单词数、并将单词装入集合、统计个数
     */
    public static void collectWord(){
        int checkWordResult = -1;
        for (int i = 0; i < inputFileLength; i ++){
            if (isLetter(inputFileBytes[i])){
                checkWordResult = checkWord(inputFileBytes, inputFileLength, i, 4);
                if (checkWordResult > 0){
                    String aWordString = subBytesToString(inputFileBytes, i, checkWordResult);
                    // System.out.println(aWordString);
                    // 存入集合中
                    if (wordMap.containsKey(aWordString)){
                        wordMap.put(aWordString, wordMap.get(aWordString)+1);
                    } else{
                        wordMap.put(aWordString, 1);
                    }
                    wordNum ++;
                    // 跳转单词末尾
                    i = checkWordResult;
                } else{
                    // 不是单词，但是同样跳转到词末尾
                    i = - checkWordResult;
                }
                // System.out.println(checkWordResult);
            }
        }
    }

    /**
     * 功能：按照单词频率排序
     */
    public static void sortWordMap(){
        wordList = new ArrayList<Map.Entry<String,Integer>>(wordMap.entrySet());
        Collections.sort(wordList, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> word1, Map.Entry<String, Integer> word2) {
                return word2.getValue() - word1.getValue();
            }
        });
    }

    /**
     * 功能：取出字节数组中的某一段转成String返回
     *
     * 参数：byte[] bytes 字节数组
     *      int start 开始下标
     *      int end 截止下标
     *
     * 返回：String aWordString 截取转成的字符串
     */
    static String subBytesToString(byte[] bytes, int start, int end){
        byte[] aWordByte = new byte[end-start];
        System.arraycopy(bytes, start, aWordByte, 0, end-start);
        return new String(aWordByte);
    }

    /**
     * 功能：判断该换行字符所在行是否是非空白行
     *
     * 参数：byte[] bytes 字节数组
     *      int lineEnd 换行符下标（行末尾）
     *
     * 返回：boolean true 非空行
     *      boolean fasle 是空行
     */
    static boolean checkLine(byte[] bytes, int lineEnd){
        int notBlankCharNum = 0;
        for (int i = lineEnd-1; i >= 0; i --){
            if (bytes[i] == 10){
                // 遇到前一行返回
                break;
            } else if (!isBlankChar(bytes[i])){
                // 当前字母不是空格或制表符
                notBlankCharNum ++;
            }
        }
        return (notBlankCharNum > 0);
    }

    /**
     * 功能：判断byte字节是否是字母
     *
     * 参数：byte b 字节
     *
     * 返回：boolean true 是字母
     *      boolean false 不是字母
    **/
    static boolean isLetter(byte b){
        return (b >= 97 && b <= 122);
    }

    /**
     * 功能：判断byte字节是否是空白字符
     *
     * 参数：byte b 字节
     *
     * 返回：boolean true 是空白字符
     *      boolean false 不是空白字符
    **/
    static boolean isBlankChar(byte b){
        // return (b == 32 || b == 10 || b == 9 || b == 13);
        return (b <= 32);
    }

    /**
     * 功能：判断从某个下标开始的一段长度是否是单词
     *
     * 参数：byte[] bytes 字节数组
     *      int bytesLength 字节数组长度
     *      int start 开始下标
     *      int minWordLength 满足最小需求的开头字母数
     *
     * 返回：int < 0 不是单词，负的词末尾分隔符的下标
     *      int > 0 是单词，单词末尾分隔符的下标
    **/
    static int checkWord(byte[] bytes, int bytesLength, int start, int minWordLength){
        int i = start;
        int checkWordResult = 0;

        if (start > 0 && !isLetter(bytes[start-1]) && !isBlankChar(bytes[start-1])){
            checkWordResult = -1;
        } else{
            for (; i < start + minWordLength && i < bytesLength; i++){
                // 满足最小需求的开头字母数
                if (! isLetter(bytes[i])){
                    checkWordResult = -2;
                    break;
                }
            }
            // 已到结尾，不满足最小开头字母数
            if (i == bytesLength && i - start < minWordLength){
                checkWordResult = -3;
            }
        }
        for (; i < bytesLength; i++){
            // 遍历到词结束，返回词末尾的下标
            if (isBlankChar(bytes[i])){
                // 字符不是空格、换行、制表符
                break;
            }
        }

        return checkWordResult < 0 ? -i : i;
    }
}