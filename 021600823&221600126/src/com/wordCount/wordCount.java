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
    private String output; // Ĭ������ļ�
    // ����
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

    public void countWords(String fileName, int top, boolean all) throws IOException // topΪǰ���Ĵ�Ƶ
    {
        wordHeap wh = new wordHeap();
        HashMap<String, Integer> wordMap = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        // �ж��Ƿ��������ļ����������򴴽�
        File file = new File(output);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(output));
        String content; // ����readline()������ȡÿһ�е�ֵ
        while ((content = br.readLine()) != null) {
            String type = "";
            if (!all) {
                int index = counter.cutWords(content); // ����count��ĵ��ʷָ����һ�����ʷָ����
                if ((index + 2) >= content.length())
                    continue;
                type = content.toLowerCase().substring(0, counter.cutWords(content));
                content = content.substring(index + 2); // ð����ո񲻼�
            }
            characters += counter.countCharacters(content, true); // �ַ�����
            lines += counter.countLines(content); // ��Ч�м���
            words += counter.countWords(content, wordMap, type); // ���ʼ���
        }
        wordClassify(wordMap, wh); // ���ʹ��ಢ����
        br.close();
        bw.write("character:" + characters);
        bw.newLine();
        bw.write("words:" + words);
        bw.newLine();
        bw.write("lines:" + lines);
        bw.newLine();
        top = (top < wh.size() - 1) ? top : wh.size() - 1;
        for (int i = 1; i <= top; i++) // ������
        {
            bw.write("<" + wh.heap.get(1).word + ">: " + wh.heap.get(1).value);
            bw.newLine();
            wh.delete();
        }
        bw.flush();
        bw.close();
    }

    private void wordClassify(HashMap<String, Integer> wordMap, wordHeap wh) // ���ʹ��࣬����ϣ���еĵ��ʹ���ʶѣ����ѣ�
    {
        String word;
        java.util.Iterator<Entry<String, Integer>> iter = wordMap.entrySet().iterator(); // ��ֵ�Ա�����ϣ��
        Integer value;
        while (iter.hasNext()) {
            Entry<String, Integer> entry = iter.next();
            word = entry.getKey(); // ��ȡkey
            value = entry.getValue(); // ��ȡvalue
            wh.insert(word, value);
        }
    }
}
