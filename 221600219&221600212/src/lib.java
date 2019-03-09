import java.io.*;
import java.util.*;

public class Lib{

    // ����
    private static int lineNum = 0;

    // �ַ���
    private static int charNum = 0;

    // �������������ĸ�Ӣ����ĸ��ͷ�������ִ�Сд
    private static int wordNum = 0;

    // ���ʼ��ϣ�<����, ��Ŀ>
    private static Map<String, Integer> wordMap = null;

    // ����õĵ��ʼ���
    private static List<Map.Entry<String, Integer>> wordList = null;

    // �ֽ�����
    private static byte[] bytes = null;

    // ���ȣ��ֽ����鳤��
    private static int bytesLength = 0;

    public static int getCharNum(){return charNum;}
    public static int getWordNum(){return wordNum;}
    public static int getLineNum(){return lineNum;}
    public static List<Map.Entry<String, Integer>> getSortedList(){return wordList;}

	/**
	 * ��ʼ������
	**/
	public Lib(byte[] bytes){
		this.bytes = bytes;
        this.bytesLength = bytes.length;
        this.wordMap = new TreeMap<String, Integer>();
	}
    /**
     * ���ܣ�Ԥ����
     *      ����д��ĸתΪСд��ĸ
     *      �����ֽ������е��ַ������������ַ���//r//n����һ���ַ�
     *      �����ֽ��������������
     *
     * ������byte[] bytes �ֽ�����
     */
    public static void preproccess(){
        // �����ַ���������
        for (int i = 0; i < bytesLength; i ++){
            // Ԥ������д��ĸͳһתΪСд��ĸ
            if (bytes[i] >= 65 && bytes[i] <= 90){
                bytes[i] += 32;
            }
            if (bytes[i] == 10){
                // ��������
                if (checkLine(bytes, i)){
                    lineNum ++;
                }
            }else{
                charNum ++;
            }
        }
        // ע�����һ�в��Իس���β�������ͬ������һ��
        if (bytes[bytesLength-1] != 10){
            lineNum ++;
        }
    }

    /**
     * ���ܣ����㵥��������������װ�뼯�ϡ�ͳ�Ƹ���
     */
    public static void collectWord(){
        int checkWordResult = -1;
        for (int i = 0; i < bytesLength; i ++){
            if (isLetter(bytes[i])){
                checkWordResult = checkWord(bytes, bytesLength, i, 4);
                if (checkWordResult > 0){
                    String aWordString = subBytesToString(bytes, i, checkWordResult);
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