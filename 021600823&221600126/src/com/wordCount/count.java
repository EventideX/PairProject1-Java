package com.wordCount;

import java.util.HashMap;

public class count {
    private static wordHeap weightTable; // 利用词堆，建立一个权重表

    public count() {
        weightTable = new wordHeap();
        weightTable.add("", 1); // 添加默认权重
    }

    void addWeight(String type, Integer value) {// 添加类型权重
        type = type.toLowerCase(); // 全部替换成小写
        int index = weightTable.isExist(type); // 判断原先是否有权重记录，如果有则更新，没有则添加。
        if (index == -1)
            weightTable.add(type, value);
        else
            weightTable.heap.get(index).value = value;
    }

    public boolean isAscii(char c) { //判断是否是0~127的字符
        int x = (int) c;
        return x <= 127;
    }

    public boolean isNotDigitOrAlpha(char c) { // 判断是否是非字母数字字符
        return !isAscii(c) || !Character.isLetterOrDigit(c);
    }

    public boolean isBlank(char c) { // 判断是否是空白字符
        return isAscii(c) && (Character.isISOControl(c) || c == ' ');
    }

    public boolean isWord(String input) { // 判断是否是符合标准的单词
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

    public int countCharacters(String input, boolean all) { // 统计字符数
        int result = 0;
        char[] charArray = input.toCharArray();
        for (char c : charArray) {
            if (isAscii(c))
                result++;
        }
        if (all) // 如果需要 自动统计readline()方法省略的换行符
            result++;
        return result;
    }

    public int countLines(String input) { // 统计行数
        int result = 0;
        char[] charArray = input.toCharArray();
        for (char c : charArray) {
            if (!isBlank(c)) // 出现非空字符则统计
            {
                result++;
                break;
            }
        }
        return result;
    }

    private void addMap(String word, Integer weight, HashMap<String, Integer> wordMap) { // 将单词加入哈希表中
        wordMap.merge(word, weight, (a, b) -> a + b);
    }

    public int cutWords(String input) { // 分隔单词

        char[] charArray = input.toCharArray();
        for (int index = 0; index < charArray.length; index++) {
            if (isNotDigitOrAlpha(charArray[index])) // 出现非字母数字字符则返回下标
                return index;
        }
        return charArray.length;
    }

    private int cutSign(String input) { // 分割符号

        char[] charArray = input.toCharArray();
        for (int index = 0; index < charArray.length; index++) {
            if (!isNotDigitOrAlpha(charArray[index])) // 出现非字母数字字符则返回下标
                return index;
        }
        return charArray.length;
    }

    int countWords(String input, HashMap<String, Integer> wordMap, String type) { // 统计单词数，并存入哈希表

        int result = 0;
        String word;
        int cut;
        int weight = 1; // 初始权重为一
        if (!type.equals("")) {
            int i = weightTable.isExist(type); // 判断权值表中是否含有此类，有则替换权值。
            if (i != -1) {
                weight = weightTable.heap.get(i).value;
            }
        }
        input = input.toLowerCase(); // 转化为小写
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
        if (isWord(word)) // 防止最后一个单词漏读
        {
            addMap(word, weight, wordMap);
            result++;
        }
        return result;
    }
}
