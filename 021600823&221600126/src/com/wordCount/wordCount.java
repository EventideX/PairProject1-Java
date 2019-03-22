package com.wordCount;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class wordCount {
    private String output; // 默认输出文件
    // 计数
    private int characters = 0;
    private int lines = 0;
    private int words = 0;
    private count counter;

    public wordCount(String outputFile) {
        counter = new count();
        output = outputFile;
    }

    public void setCountWithWeight() {
        counter.addWeight("title", 10);
        counter.addWeight("abstract", 1);
    }

    public void countWords(String fileName, int top, boolean all) throws IOException // top为前几的词频
    {
        wordHeap wh = new wordHeap();
        HashMap<String, Integer> wordMap = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        // 判断是否存在输出文件，不存在则创建
        File file = new File(output);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(output));
        String content; // 利用readline()函数读取每一行的值
        while ((content = br.readLine()) != null) {
            String type = "";
            if (!all) {
                int index = counter.cutWords(content); // 调用count类的单词分割，将第一个单词分割出来
                if ((index + 2) >= content.length())
                    continue;
                type = content.toLowerCase().substring(0, counter.cutWords(content));
                content = content.substring(index + 2); // 冒号与空格不计
            }
            characters += counter.countCharacters(content, true); // 字符计数
            lines += counter.countLines(content); // 有效行计数
            words += counter.countWords(content, wordMap, type); // 单词计数
        }
        wordClassify(wordMap, wh); // 单词归类并进行
        br.close();
        bw.write("character:" + characters);
        bw.newLine();
        bw.write("words:" + words);
        bw.newLine();
        bw.write("lines:" + lines);
        bw.newLine();
        top = (top < wh.size() - 1) ? top : wh.size() - 1;
        for (int i = 1; i <= top; i++) // 堆排序
        {
            bw.write("<" + wh.heap.get(1).word + ">: " + wh.heap.get(1).value);
            bw.newLine();
            wh.delete();
        }
        bw.flush();
        bw.close();
    }

    private void wordClassify(HashMap<String, Integer> wordMap, wordHeap wh) // 单词归类，将哈希表中的单词归入词堆（最大堆）
    {
        String word;
        java.util.Iterator<Entry<String, Integer>> iter = wordMap.entrySet().iterator(); // 键值对遍历哈希表
        Integer value;
        while (iter.hasNext()) {
            Entry<String, Integer> entry = iter.next();
            word = entry.getKey(); // 获取key
            value = entry.getValue(); // 获取value
            wh.insert(word, value);
        }
    }
}
