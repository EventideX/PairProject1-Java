public class Main {
    public static void main(String[] args) throws Exception {
        TextLib textLib = new TextLib();
        textLib.readFile();
        Lib lib = new Lib();
        lib.countChars();
        lib.countLines();
        lib.countWords();
        lib.countHotWords();
        textLib.writeFile();
    }
}
