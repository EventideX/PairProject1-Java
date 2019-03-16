public class Main {
    public static void main(String[] args) throws Exception {
        if(args.length == 0){
            throw new IllegalArgumentException();
        }

        lib lib = new lib();
        lib.path += args[0];
        lib.countChars();
        lib.countLines();
        lib.countWords();
        lib.countHotWords();
        lib.writeFile();
    }
}
