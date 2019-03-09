import java.io.*;
import java.util.*;

public class Main{

    // ����
    public static int lineNum = 0;

    // �ַ���
    public static int charNum = 0;

    // �������������ĸ�Ӣ����ĸ��ͷ�������ִ�Сд
    public static int wordNum = 0;

    // ���ʼ��ϣ�<����, ��Ŀ>
    public static Map<String, Integer> wordMap = null;

    // ����õĵ��ʼ���
    public static List<Map.Entry<String, Integer>> wordList = null;

    // �����ļ��ֽ�����
    public static byte[] inputFileBytes= null;

    // �����ļ����ȣ��ֽ����鳤��
    public static int inputFileLength = 0;

    // �����ӡ��ǰ���ĸ���
    public static final int SORT_PRINT_NUM = 10;


    /**
     * �������
     * @param args[0] �����ļ���
     */
    public static void main(String[] args) {
        // ��ʼ��
        String inputFileName = args[0];
        wordMap = new TreeMap<String, Integer>();

        // ��ȡ�ļ�
        inputFileBytes = readFileToBytes(inputFileName);
        inputFileLength = inputFileBytes.length;

        // Ԥ���������ַ���������
        preproccess();

        // ���㵥��������������װ�뼯�ϡ�ͳ�Ƹ���
        collectWord();

        // ���յ���Ƶ������
        sortWordMap();

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

    /**
     * ���ܣ�Ԥ����
     *      ����д��ĸתΪСд��ĸ
     *      �����ֽ������е��ַ������������ַ���//r//n����һ���ַ�
     *      �����ֽ��������������
     */
    public static void preproccess(){
        // �����ַ���������
        for (int i = 0; i < inputFileLength; i ++){
            // Ԥ������д��ĸͳһתΪСд��ĸ
            if (inputFileBytes[i] >= 65 && inputFileBytes[i] <= 90){
                inputFileBytes[i] += 32;
            }
            if (inputFileBytes[i] == 10){
                // ��������
                if (checkLine(inputFileBytes, i)){
                    lineNum ++;
                }
            }else{
                charNum ++;
            }
        }
        // ע�����һ�в��Իس���β�������ͬ������һ��
        if (inputFileBytes[inputFileLength-1] != 10){
            lineNum ++;
        }
    }

    /**
     * ���ܣ����㵥��������������װ�뼯�ϡ�ͳ�Ƹ���
     */
    public static void collectWord(){
        int checkWordResult = -1;
        for (int i = 0; i < inputFileLength; i ++){
            if (isLetter(inputFileBytes[i])){
                checkWordResult = checkWord(inputFileBytes, inputFileLength, i, 4);
                if (checkWordResult > 0){
                    String aWordString = subBytesToString(inputFileBytes, i, checkWordResult);
                    // System.out.println(aWordString);
                    // ���뼯����
                    if (wordMap.containsKey(aWordString)){
                        wordMap.put(aWordString, wordMap.get(aWordString)+1);
                    } else{
                        wordMap.put(aWordString, 1);
                    }
                    wordNum ++;
                    // ��ת����ĩβ
                    i = checkWordResult;
                } else{
                    // ���ǵ��ʣ�����ͬ����ת����ĩβ
                    i = - checkWordResult;
                }
                // System.out.println(checkWordResult);
            }
        }
    }

    /**
     * ���ܣ����յ���Ƶ������
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
     * ���ܣ�ȡ���ֽ������е�ĳһ��ת��String����
     *
     * ������byte[] bytes �ֽ�����
     *      int start ��ʼ�±�
     *      int end ��ֹ�±�
     *
     * ���أ�String aWordString ��ȡת�ɵ��ַ���
     */
    static String subBytesToString(byte[] bytes, int start, int end){
        byte[] aWordByte = new byte[end-start];
        System.arraycopy(bytes, start, aWordByte, 0, end-start);
        return new String(aWordByte);
    }

    /**
     * ���ܣ��жϸû����ַ��������Ƿ��Ƿǿհ���
     *
     * ������byte[] bytes �ֽ�����
     *      int lineEnd ���з��±꣨��ĩβ��
     *
     * ���أ�boolean true �ǿ���
     *      boolean fasle �ǿ���
     */
    static boolean checkLine(byte[] bytes, int lineEnd){
        int notBlankCharNum = 0;
        for (int i = lineEnd-1; i >= 0; i --){
            if (bytes[i] == 10){
                // ����ǰһ�з���
                break;
            } else if (!isBlankChar(bytes[i])){
                // ��ǰ��ĸ���ǿո���Ʊ��
                notBlankCharNum ++;
            }
        }
        return (notBlankCharNum > 0);
    }

    /**
     * ���ܣ��ж�byte�ֽ��Ƿ�����ĸ
     *
     * ������byte b �ֽ�
     *
     * ���أ�boolean true ����ĸ
     *      boolean false ������ĸ
    **/
    static boolean isLetter(byte b){
        return (b >= 97 && b <= 122);
    }

    /**
     * ���ܣ��ж�byte�ֽ��Ƿ��ǿհ��ַ�
     *
     * ������byte b �ֽ�
     *
     * ���أ�boolean true �ǿհ��ַ�
     *      boolean false ���ǿհ��ַ�
    **/
    static boolean isBlankChar(byte b){
        // return (b == 32 || b == 10 || b == 9 || b == 13);
        return (b <= 32);
    }

    /**
     * ���ܣ��жϴ�ĳ���±꿪ʼ��һ�γ����Ƿ��ǵ���
     *
     * ������byte[] bytes �ֽ�����
     *      int bytesLength �ֽ����鳤��
     *      int start ��ʼ�±�
     *      int minWordLength ������С����Ŀ�ͷ��ĸ��
     *
     * ���أ�int < 0 ���ǵ��ʣ����Ĵ�ĩβ�ָ������±�
     *      int > 0 �ǵ��ʣ�����ĩβ�ָ������±�
    **/
    static int checkWord(byte[] bytes, int bytesLength, int start, int minWordLength){
        int i = start;
        int checkWordResult = 0;

        if (start > 0 && !isLetter(bytes[start-1]) && !isBlankChar(bytes[start-1])){
            checkWordResult = -1;
        } else{
            for (; i < start + minWordLength && i < bytesLength; i++){
                // ������С����Ŀ�ͷ��ĸ��
                if (! isLetter(bytes[i])){
                    checkWordResult = -2;
                    break;
                }
            }
            // �ѵ���β����������С��ͷ��ĸ��
            if (i == bytesLength && i - start < minWordLength){
                checkWordResult = -3;
            }
        }
        for (; i < bytesLength; i++){
            // �������ʽ��������ش�ĩβ���±�
            if (isBlankChar(bytes[i])){
                // �ַ����ǿո񡢻��С��Ʊ��
                break;
            }
        }

        return checkWordResult < 0 ? -i : i;
    }
}