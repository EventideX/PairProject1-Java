package src;

public class Main {
    public static void main(String[] args)  {
        WordCount wordCount = new WordCount(new Signal(args));
        wordCount.show();
    }
}
