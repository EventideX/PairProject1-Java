import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: main
 * @author: hlx 2019-03-10
 **/
public class Main {

    public static void main(String[] args) throws IOException {
        StringBuilder resultBuilder = new StringBuilder();

        Map<String, Integer> letterMap = new HashMap<>();
        int charNum = 0, lineNum = 0, wordNum = 0;
        String letter;
        int letterCheck = -4;
        boolean haveChar = false, letterCheckAble = true;
        StringBuilder letterBuilder = new StringBuilder();

        File f = new File(args[0]);

        Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        int charInt, preCharInt = -1;
        while ((charInt = reader.read()) != -1) {
            if(charInt != 10 && preCharInt != 13){
                charNum++;
            }
            preCharInt = charInt;
            if (Lib.isLetter(charInt)) {
                if (letterCheck < 0) {
                    letterCheck++;
                }
                letterBuilder.append((char) charInt);
            } else if (Lib.isNumber(charInt)) {
                if (letterCheck == 0) {
                    letterBuilder.append((char) charInt);
                } else {
                    if (letterBuilder.length() != 0) letterBuilder.setLength(0);
                    letterCheck = -4;
                    letterCheckAble = false;
                }
            } else {
                if (letterCheck == 0 && letterCheckAble) {
                    wordNum++;
                    letter = letterBuilder.toString()
                            .toLowerCase();
                    letterMap.merge(letter, 1, (a, b) -> a + b);
                }
                if (letterBuilder.length() != 0) letterBuilder.setLength(0);
                letterCheck = -4;
                letterCheckAble = Lib.isDivision(charInt);
            }
            if (charInt == 10) {
                if (haveChar) {
                    lineNum++;
                }
                haveChar = false;
            } else if (!Lib.isBlank(charInt)) {
                haveChar = true;
            }
        }
        if (letterCheck == 0 && letterCheckAble) {
            letter = letterBuilder.toString()
                    .toLowerCase();
            wordNum++;
            letterMap.merge(letter, 1, (a, b) -> a + b);
        }
        if (haveChar)   lineNum++;
        resultBuilder.append("characters: ").append(charNum).append("\r\n")
                .append("words: ").append(wordNum).append("\r\n")
                .append("lines: ").append(lineNum).append("\r\n");
        Lib.sortMapAndOut(letterMap, resultBuilder);
        Lib.strOutFile(resultBuilder.toString(), "result.txt");
    }
}