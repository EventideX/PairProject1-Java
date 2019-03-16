package com.wordCount;

import java.util.HashMap;

public class count {
    private static wordHeap weightTable; // ���ôʶѣ�����һ��Ȩ�ر�

    public count() {
        weightTable = new wordHeap();
        weightTable.add("", 1); // ���Ĭ��Ȩ��
    }

    void addWeight(String type, Integer value) {// �������Ȩ��
        type = type.toLowerCase(); // ȫ���滻��Сд
        int index = weightTable.isExist(type); // �ж�ԭ���Ƿ���Ȩ�ؼ�¼�����������£�û������ӡ�
        if (index == -1)
            weightTable.add(type, value);
        else
            weightTable.heap.get(index).value = value;
    }

    public boolean isAscii(char c) { //�ж��Ƿ���0~127���ַ�
        int x = (int) c;
        return x <= 127;
    }

    public boolean isNotDigitOrAlpha(char c) { // �ж��Ƿ��Ƿ���ĸ�����ַ�
        return !isAscii(c) || !Character.isLetterOrDigit(c);
    }

    public boolean isBlank(char c) { // �ж��Ƿ��ǿհ��ַ�
        return isAscii(c) && (Character.isISOControl(c) || c == ' ');
    }

    public boolean isWord(String input) { // �ж��Ƿ��Ƿ��ϱ�׼�ĵ���
        if(input.length() < 4)
            return false;
        for(int i = 0; i < 4; i++) {
            if(isNotDigitOrAlpha(input.charAt(i)) ||
                    !Character.isLowerCase(input.charAt(i)))
                return false;
        }
        for(int i = 4; i < input.length(); i++) {
            if(isNotDigitOrAlpha(input.charAt(i)))
                return false;
        }
        return true;
    }

    public int countCharacters(String input, boolean all) { // ͳ���ַ���
        int result = 0;
        char[] charArray = input.toCharArray();
        for (char c : charArray) {
            if (isAscii(c))
                result++;
        }
        if (all) // �����Ҫ �Զ�ͳ��readline()����ʡ�ԵĻ��з�
            result++;
        return result;
    }

    public int countLines(String input) { // ͳ������
        int result = 0;
        char[] charArray = input.toCharArray();
        for (char c : charArray) {
            if (!isBlank(c)) // ���ַǿ��ַ���ͳ��
            {
                result++;
                break;
            }
        }
        return result;
    }

    private void addMap(String word, Integer weight, HashMap<String, Integer> wordMap) { // �����ʼ����ϣ����
        wordMap.merge(word, weight, (a, b) -> a + b);
    }

    public int cutWords(String input) { // �ָ�����

        char[] charArray = input.toCharArray();
        for (int index = 0; index < charArray.length; index++) {
            if (isNotDigitOrAlpha(charArray[index])) // ���ַ���ĸ�����ַ��򷵻��±�
                return index;
        }
        return charArray.length;
    }

    private int cutSign(String input) { // �ָ����

        char[] charArray = input.toCharArray();
        for (int index = 0; index < charArray.length; index++) {
            if (!isNotDigitOrAlpha(charArray[index])) // ���ַ���ĸ�����ַ��򷵻��±�
                return index;
        }
        return charArray.length;
    }

    int countWords(String input, HashMap<String, Integer> wordMap, String type) { // ͳ�Ƶ��������������ϣ��

        int result = 0;
        String word;
        int cut;
        int weight = 1; // ��ʼȨ��Ϊһ
        if (!type.equals("")) {
            int i = weightTable.isExist(type); // �ж�Ȩֵ�����Ƿ��д��࣬�����滻Ȩֵ��
            if (i != -1) {
                weight = weightTable.heap.get(i).value;
            }
        }
        input = input.toLowerCase(); // ת��ΪСд
        cut = cutWords(input);
        while (cut != input.length()) {
            word = input.substring(0, cut);
            input = input.substring(cut);
            if (isWord(word)) {
                addMap(word, weight, wordMap);
                result++;
            }
            cut = cutSign(input);
            input = input.substring(cut);
            cut = cutWords(input);
        }
        word = input;
        if (isWord(word)) // ��ֹ���һ������©��
        {
            addMap(word, weight, wordMap);
            result++;
        }
        return result;
    }
}
