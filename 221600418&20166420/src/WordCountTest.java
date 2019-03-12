package src;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class WordCountTest {
    @Test
    public void getCharacterNumber() {
        File input = new File("testExample/input1.txt");
        WordCount wordCount = new WordCount(input);
        assertEquals(102, wordCount.getCharacterNumber());
    }

    @Test
    public void getLineNumber() {
        File input = new File("testExample/input1.txt");
        WordCount wordCount = new WordCount(input);
        assertEquals(2, wordCount.getLineNumber());
    }

    @Test
    public void getWordNumber() {
        File input = new File("testExample/input1.txt");
        WordCount wordCount = new WordCount(input);
        assertEquals(2, wordCount.getWordNumber());
    }

    @Test
    public void getList() {
        File input = new File("testExample/input2.txt");
        WordCount wordCount = new WordCount(input);
        assertEquals("<abcdefghijklmnopqrstuvwxyz>: 1", wordCount.getList().trim());
    }
}