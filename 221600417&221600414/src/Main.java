import java.io.*;
import java.util.*;

/**
 * @description: main
 * @author: hlx 2019-03-10
 **/
public class Main {

    // TODO:/r/n
    public static void main(String[] args) throws IOException {
        StringBuilder resultBuilder = new StringBuilder();

        Map<String, Integer> letterMap = new HashMap<>();
        int charNum = 0, lineNum = 0;
        String letter;
        int letterCheck = -4;
        boolean haveChar = false;
        StringBuilder letterBuilder = new StringBuilder();

        Reader reader = new InputStreamReader(new FileInputStream(args[0]));
        int charInt;
        while ((charInt = reader.read()) != -1) {
            charNum++;
            if (Lib.isLetter(charInt)) {
                if (letterCheck < 0) {
                    letterCheck++;
                }
                letterBuilder.append((char) charInt);
            } else if (Lib.isNumber(charInt)) {
                // 4 letter check
                if (letterCheck == 0) {
                    letterBuilder.append((char) charInt);
                } else {
                    if (letterBuilder.length() != 0) letterBuilder.setLength(0);
                    letterCheck = -4;
                    charNum += Lib.readToDivision(reader);
                }
            } else {
                if (letterCheck == 0) {
                    letter = letterBuilder.toString()
                            .toLowerCase();
                    letterMap.merge(letter, 1, (a, b) -> a + b);
                }
                if (letterBuilder.length() != 0) letterBuilder.setLength(0);
                letterCheck = -4;
                charNum += Lib.readToDivision(reader);
            }
            // line handle
            if (charInt == 10) {
                if (haveChar) {
                    lineNum++;
                }
                haveChar = false;
            } else if (!Lib.isBlank(charInt)) {
                haveChar = true;
            }
        }
        if (letterCheck == 0) {
            letter = letterBuilder.toString()
                    .toLowerCase();
            letterMap.merge(letter, 1, (a, b) -> a + b);
        }
        if (haveChar) {
            lineNum++;
        }
        resultBuilder.append("characters: ").append(charNum).append("\n")
                .append("words: ").append(letterMap.size()).append("\n")
                .append("lines: ").append(lineNum).append("\n");
        letterMap.entrySet()
                .stream()
                .sorted((e1, e2) -> {
                    int cmp = e2.getValue().compareTo(e1.getValue());
                    if (cmp == 0) return e1.getKey().compareTo(e2.getKey());
                    else return cmp;
                })
                .limit(10)
                .forEach(o -> resultBuilder.append(o.getKey()).append(": ").append(o.getValue()).append("\n"));
        BufferedOutputStream bf = new BufferedOutputStream(new FileOutputStream("result.txt"));
        bf.write(resultBuilder.toString().getBytes());
        bf.flush();
    }
}
