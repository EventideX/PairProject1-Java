import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Process {
    //Result resultClass;
//    public Process(Result result_class){
//        resultClass=result_class;
//    }
    public static void read_file(String file_name,String outputFileName, Result resultClass, int CodeMode,int outputSize) {//1 GBK
        MyBufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            //设置文件编码，解决文件乱码问题
            //将字节流转换为字符流，实际上使用了一种设计模式——适配器模式
            InputStreamReader isr;
            if (CodeMode == 1) {//选择编码
                isr = new InputStreamReader(new FileInputStream(file_name), "GBK");
            } else isr = new InputStreamReader(new FileInputStream(file_name), StandardCharsets.UTF_8);
            //UTF-8
            bufferedReader = new MyBufferedReader(isr);
            bufferedWriter = new BufferedWriter(new FileWriter(outputFileName));
            //每次读一行开始
            String s;
            int i = 0;
            while ((s = bufferedReader.readLine()) != null) {
                i++;

                resultClass.char_count_plus(s.length());//统计字符数
                s = s.replaceAll("[^\\x00-\\x80]", "");
                s=trim(s);//去掉开头不显示字符
                if(s.length()!=0){//若有可显示字符则处理
                    resultClass.line_count_plus();//统计行数
                    process_line_withRegularExpression(s,resultClass);
                }

                //process_line(s.toCharArray(), resultClass);
            }
            System.out.println(i);
            resultClass.char_count_plus(i - 1);//统计换行符 实际行数减一
            //每次读一行结束

            //结果写入文件
            bufferedWriter.write(String.format("characters: %s\n",resultClass.getChar_count()));

            //bufferedWriter.newLine();//按行读取，写入一个分行符，否则所有内容都在一行显示

            bufferedWriter.write(String.format("words: %s\n",resultClass.getWord_count()));

            //bufferedWriter.newLine();

            bufferedWriter.write(String.format("lines: %s\n",resultClass.getLine_count()));

            //bufferedWriter.newLine();

            //控制台输出
            System.out.format("characters: %s\n",resultClass.getChar_count());
            System.out.format("words: %s\n",resultClass.getWord_count());
            System.out.format("lines: %s\n",resultClass.getLine_count());

            //输出单词排序
            List<Map.Entry<String, Integer>> list=resultClass.sort();
            for (int ii = 0; ii < outputSize && ii < list.size(); ii++) {//输出前size个数据
                System.out.format("<%s>: %s\n",list.get(ii).getKey(),list.get(ii).getValue());
                bufferedWriter.write(String.format("<%s>: %s\n",list.get(ii).getKey(),list.get(ii).getValue()));
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String trim(String str) {//切除头尾不可显示字符 ASCII中的33个控制字符和空格（0-32、127）
        int var1 = str.length();
        int var2 = 0;

        for (; var2 < var1 && (str.charAt(var2) < 33 || str.charAt(var2) == 127); ++var2) {
        }

        while (var2 < var1 && (str.charAt(var1 - 1) < 33 || str.charAt(var1 - 1) == 127)) {
            --var1;
        }
        return var2 <= 0 && var1 >= str.length() ? str : str.substring(var2, var1);
    }

    private static void process_line_withRegularExpression(String str, Result resultClass){
        Pattern p=Pattern.compile("(^|[^a-z0-9])([a-z]{4,}[a-z0-9]*)");
        Matcher m=p.matcher(str);
        while(m.find()) {
            System.out.println(m.group(2));
            resultClass.addWord(m.group(2));
        }
    }
    //废弃代码

//    public static boolean isBlank(String str,Result resultClass){
//        Pattern p=Pattern.compile("[^ \\f\\n\\r\\t\\v0-9]+");
//        Matcher m=p.matcher(str);
//        return m.find();
//    }
//    public static void process_line_withRegularExpression(String str, Result resultClass,int weight){
//        Pattern p=Pattern.compile("[a-zA-Z]{4,}[a-zA-Z0-9]*");
//        Matcher m=p.matcher(str);
//        while(m.find()) {
//            resultClass.addWord(m.group(), weight);
////            if(m.group().toLowerCase().equals("mdqbjjx9s"))
////            System.out.println(str);
//        }
//    }
//    public static void process_line_withPharse(String str, Result resultClass){
//        Pattern p=Pattern.compile("([a-zA-Z]{4,}[a-zA-Z0-9]*){3,}");
//        Matcher m=p.matcher(str);
//        while(m.find()) {
//            resultClass.addWord(m.group(), 1);
//
////            if(m.group().toLowerCase().equals("mdqbjjx9s"))
////                System.out.println(str);
//        }
//    }


//    public static void process_line(char char_str[], Result resultClass) {
//        //final int  isAlpha=4;//几个字母以上才开始算单词
//        int length = char_str.length;
//        int alphaCount = 0;
//        boolean emptyLine = true;
//        for (int i = 0; i < length; i++) {
//            if (emptyLine) {
//                if (!isBlank(char_str[i])) {
//                    emptyLine = false;
//                    resultClass.line_count_plus();//add line count
//                }
//            }
//            if (char_str[i] > 0x7F) continue;//is_ ascii?
//            resultClass.char_count_plus();
//            switch (alphaCount) {
//                case 0:
//                    if (Character.isAlphabetic(char_str[i])) {
//                        alphaCount++;
//                        continue;
//                    }else {
//                        alphaCount = 0;
//                    }
//                    break;
//                case 1:
//                case 2:
//                case 3:
//                case 4:
//                    if (Character.isAlphabetic(char_str[i])) {// 1-4
//                        alphaCount++;
//                        continue;
//                    }else{
//                        alphaCount=0;
//                        continue;
//                    }
//                default:// >4
//                    if (isSplit(char_str[i])) {
//                        String word = new String(char_str, i - alphaCount, alphaCount);
//                        //System.out.format("i:%d  account:%d \n",i,alphaCount);
//                        resultClass.addWord(word, 1);
//                        alphaCount=0;
//                        continue;
//                    }else{
//                        alphaCount++;
//                    }
//                    break;
//            }
//        }
//        if (alphaCount > 3) {
//            String word = new String(char_str, length - alphaCount, alphaCount);
//            resultClass.addWord(word, 1);
//        }
//    }
//
//    public static void process_line_with_phrase(char char_str[], Result resultClass,int phrase_length) {
//        //final int  isAlpha=4;//几个字母以上才开始算单词
//        int length = char_str.length;
//        int alphaCount = 0;
//        boolean emptyLine = true;
//        int currentWordNum=0;
//        int pharseStart = 0;
//        for (int i = 0; i < length; i++) {
//            if (emptyLine) {
//                if (!isBlank(char_str[i])) {
//                    emptyLine = false;
//                    resultClass.line_count_plus();//add line count
//                }
//            }
//            if (char_str[i] > 0x7F) continue;//is_ ascii?
//            resultClass.char_count_plus();
//            switch (alphaCount) {
//                case 0:
//                    if (Character.isAlphabetic(char_str[i])) {
//                        alphaCount++;
//                        if(currentWordNum==0) pharseStart=i;
//                        continue;
//                    }else {
//                        alphaCount = 0;
//                        currentWordNum=0;
//                    }
//                    break;
//                case 1:
//                case 2:
//                case 3:
//                case 4:
//                    if (Character.isAlphabetic(char_str[i])) {// 1-4
//                        alphaCount++;
//                        continue;
//                    }else{
//                        alphaCount=0;
//                        currentWordNum=0;
//                        continue;
//                    }
//                default:// >4
//                    if (isSplit(char_str[i])) {
//                        if(currentWordNum<phrase_length){
//                            currentWordNum++;
//                            alphaCount=0;
//                            continue;
//                        }else {
//                            String word = new String(char_str,pharseStart , i-pharseStart);
//                            //System.out.format("i:%d  account:%d \n",i,alphaCount);
//                            resultClass.addWord(word, 1);
//                            alphaCount=0;
//                            currentWordNum=0;
//                        }
//                    }else{
//                        alphaCount++;
//                    }
//                    break;
//            }
//        }
//        if(currentWordNum<phrase_length){
//            currentWordNum++;
//            alphaCount=0;
//        }else {
//            String word = new String(char_str,pharseStart , length-pharseStart);
//            //System.out.format("i:%d  account:%d \n",i,alphaCount);
//            resultClass.addWord(word, 1);
//            alphaCount=0;
//            currentWordNum=0;
//        }
//    }
//
//    private static boolean isSplit(char a) {
//        return !Character.isAlphabetic(a) && !Character.isDigit(a);
//    }
//
//    private static boolean isBlank(char a) {
//        switch (a) {
//            case ' ':
//            case '\n':
//            case '\t':
//            case 11:
//            case '\r':
//            case '\f':
//                return true;
//            default:
//                return false;
//        }
//    }

}
