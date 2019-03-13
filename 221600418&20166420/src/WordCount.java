package src;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class WordCount {
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> separator = new ArrayList<>();
    private ArrayList<String> word = new ArrayList<>();
    private int characterNumber = 0, lineNumber = 0, wordNumber = 0, weight = 1, deviation = 0;
    private Signal signal;
    private File inFile, outFile;
    private TreeMap<String, Integer> map = new TreeMap<>();

    public WordCount(Signal s) {
        signal = s;
        inFile = new File(signal.getInFile());
        outFile = new File(signal.getOutFile());
        setCharacterNumber();
        setLineNumber();
        setWordNumber();
    }

    public WordCount(File inFile) {
        signal = new Signal(new String[]{});
        this.inFile = inFile;
        this.outFile = null;
        setCharacterNumber();
        setLineNumber();
        setWordNumber();
    }

    public int getCharacterNumber() {
        return characterNumber;
    }

    private void setCharacterNumber() {
        int ch;
        try (FileReader fr = new FileReader(inFile)) {
            while ((ch = fr.read()) != -1) {
                if(ch != 13){
                    characterNumber++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getLineNumber() {
        return lineNumber;
    }

    private void setLineNumber() {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(inFile))) {
            while ((line = br.readLine()) != null) {
                if(!line.trim().isEmpty()){
                    lineNumber++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getWordNumber() {
        return wordNumber;
    }

    private void setWordNumber() {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(inFile))) {
            while ((line = br.readLine()) != null) {
                line = line.toLowerCase();
                if (line.indexOf("title:") == 0) {
                    weight = signal.getwValue();
                    line = line.replaceFirst("title:", "");
                }
                if (line.indexOf("abstract:") == 0) {
                    weight = 1;
                    line = line.replaceFirst("abstract:", "");
                }
                splitLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isWord(String s) {
        return s.length() >= 4 && Character.isLetter(s.charAt(0)) && Character.isLetter(s.charAt(1)) && Character.isLetter(s.charAt(2)) && Character.isLetter(s.charAt(3));
    }
    
    private void splitLine(String line) {
        if(line.isEmpty()){
            return;
        }
        if(Character.isLetterOrDigit(line.charAt(0))) {
        	deviation = -1;
        }
        else {
        	deviation = 0;
        }
        String[] str = line.split("[^a-z0-9]");
        for (String aStr : str) {
            if (!"".equals(aStr)) {
                arrayList.add(aStr);
                if (isWord(aStr)) {
                    wordNumber += 1;
                }
            }
        }
        str = line.split("[a-z0-9]");
        for(String aStr : str) {
        	if(!"".equals(aStr)) {
        		separator.add(aStr);
        	}
        }
        setMap();
        arrayList.clear();
        separator.clear();
        word.clear();
    }

    private void combineWord() {
        for (int i = 0; i < arrayList.size() - signal.getmValue() + 1; i++) {
            boolean flag = true;
            for (int j = 0; j < signal.getmValue() && flag; j++) {
                if (!isWord(arrayList.get(i + j))) {
                    flag = false;
                }
            }
            if (flag) {
                StringBuilder s = new StringBuilder(arrayList.get(i));
                for(int j = 1; j < signal.getmValue(); j++) {
                    s.append(separator.get(i + j + deviation)).append(arrayList.get(i + j));
                }
                word.add(s.toString());
            }
        }
    }

    private void setMap() {
        combineWord();
        for (String aWord : word) {
            if (map.containsKey(aWord)) {
                map.put(aWord, map.get(aWord) + weight);
            } else {
                map.put(aWord, weight);
            }
        }
    }

    public String getList() {
        StringBuilder result = new StringBuilder();
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        int i = 0;
        for (Entry<String, Integer> mapping : list) {
            result.append("<").append(mapping.getKey()).append(">: ").append(mapping.getValue()).append("\r\n");
            i++;
            if (i >= signal.getnValue()){
                break;
            }
        }
        return String.valueOf(result);
    }

    public void show() {
        System.out.println("charactors: " + getCharacterNumber());
        System.out.println("words: " + getWordNumber());
        System.out.println("lines: " + getLineNumber());
        System.out.println(getList());
        try {
            writeToFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void writeToFile() throws Exception {
        if (outFile == null){
            throw new Exception("输出文件未定义！");
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile))) {
            bufferedWriter.write("charactors: " + getCharacterNumber() + "\r\n");
            bufferedWriter.write("words: " + getWordNumber() + "\r\n");
            bufferedWriter.write("lines: " + getLineNumber() + "\r\n");
            bufferedWriter.write(getList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
