import java.io.*;
import java.util.*;

public class Main{

    // ����
    public static int lineNum = 0;

    // �ַ���
    public static int charNum = 0;

    // �������������ĸ�Ӣ����ĸ��ͷ�������ִ�Сд
    public static int wordNum = 0;

    // ����õĵ��ʼ���
    public static List<Map.Entry<String, Integer>> wordList = null;

    // �����ļ��ֽ�����
    public static byte[] inputFileBytes = null;

    // �����ӡ��ǰ���ĸ���
    public static final int SORT_PRINT_NUM = 10;


    /**
     * �������
     * @param args[0] �����ļ���
     */
    public static void main(String[] args) {
        // ��ʼ��
        String inputFileName = args[0];

        // ��ȡ�ļ�
        inputFileBytes = readFileToBytes(inputFileName);

        Lib core = new Lib(inputFileBytes);

        // Ԥ���������ַ���������
        core.preproccess();

        // ���㵥��������������װ�뼯�ϡ�ͳ�Ƹ���
        core.collectWord();

        // ���յ���Ƶ������
        core.sortWordMap();

        // ��ȡ���
        charNum = core.getCharNum();
        wordNum = core.getWordNum();
        lineNum = core.getLineNum();
        wordList = core.getSortedList();

        // ��ӡ���
        printResult();

        // ������
        writeResult();
    }


    /**
     * ���ܣ���ȡ�ļ����ֽ�������
     *
     * ������String fileName �ļ���
     *
     * ���أ�byte[] bytes �ֽ�����
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
            System.out.println("�ļ�������");
        }
        catch(Exception e){
            System.out.println("��ȡ�ļ�����");
            e.printStackTrace();
        }

        return fileBytes;
    }

    /**
     * ���ܣ���ӡ���������̨
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
     * ���ܣ����������ļ���
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
            System.out.println("д���ļ�����");
            e.printStackTrace();
        }
    }

}