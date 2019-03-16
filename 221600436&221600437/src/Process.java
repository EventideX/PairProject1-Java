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
    static Pattern p=Pattern.compile("(^|[^a-zA-Z0-9])([a-zA-Z]{4,}[a-zA-Z0-9]*)");//匹配规则
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
            //System.out.println(i);
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
        Matcher m=p.matcher(str);
        while(m.find()) {
            //System.out.println(m.group(2));
            resultClass.addWord(m.group(2));
        }
    }
}
