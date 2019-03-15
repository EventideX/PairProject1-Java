import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @description: function library
 * @author: hlx 2019-03-10
 **/
class Lib {

    static boolean isLetter(int charInt) {
        return (charInt >= 65 && charInt <= 90) || (charInt >= 97 && charInt <= 122);
    }

    static boolean isNumber(int charInt) {
        return charInt >= 48 && charInt <= 57;
    }

    static boolean isDivision(int charInt) {
        return (charInt < 48 || (charInt > 57 && charInt < 65) || (charInt > 90 && charInt < 97) || charInt > 122);
    }

    static boolean isBlank(int charInt) {
        return charInt <= 32 || charInt == 127;
    }

    static void strOutFile(String s, String f) throws IOException {
        BufferedOutputStream bf = new BufferedOutputStream(new FileOutputStream(f));
        bf.write(s.getBytes());
        bf.flush();
    }

    static void sortMapAndOut(Map<String, Integer> map, StringBuilder builder) {
        map.entrySet()
                .stream()
                .sorted((e1, e2) -> {
                    int cmp = e2.getValue().compareTo(e1.getValue());
                    if (cmp == 0) return e1.getKey().compareTo(e2.getKey());
                    else return cmp;
                })
                .limit(10)
                .forEach(o -> builder.append("<").append(o.getKey()).append(">").append(": ").append(o.getValue()).append("\r\n"));
    }

}
