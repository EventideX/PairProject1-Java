import java.io.IOException;
import java.io.Reader;

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

    private static boolean isDivision(int charInt) {
        return (charInt < 48 || (charInt > 57 && charInt < 65) || (charInt > 90 && charInt < 97) || charInt > 122);
    }

    static boolean isBlank(int charInt) {
        return charInt <= 32 || charInt == 127;
    }

    static int readToDivision(Reader reader) throws IOException {
        int charInt, charNum = 0;
        while ((charInt = reader.read()) != -1){
            charNum++;
            if(isDivision(charInt)){
                break;
            }
        }
        return charNum;
    }

}
