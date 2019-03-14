package src;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class WordCountTest {
    private String[] files = {
            "complexTest1.txt", "complexTest2.txt",
            "emptyFileTest.txt", "emptyLineTest.txt", "frequencyTest.txt",
            "mixedCharTest.txt", "mixedLineTest.txt", "mixedWordTest.txt",
            "normalCharTest.txt", "normalLineTest.txt", "normalWordTest.txt",
    };
    private int[] chars = {
            102, 76,
            0, 10, 49,
            55, 31, 38,
            11, 19, 26
    };
    private int[] lines = {
            2, 3,
            0, 0, 7,
            4, 4, 3,
            3, 5, 5
    };
    private int[] words = {
            2, 1,
            0, 0, 5,
            1, 3, 3,
            1, 0, 4
    };
    private String[] lists = {
            "<abcdefghijklmnopqrstuvwxyz>: 2", "<abcdefghijklmnopqrstuvwxyz>: 1",
            "", "", "<aaaa>: 3\r\n<file123>: 2",
            "<rwehhhkk>: 1", "<aaaaa>: 1\r\n<fasdf10>: 1\r\n<safda>: 1", "<aaaa>: 1\r\n<fefeffffff>: 1\r\n<file1daa>: 1",
            "<bbbcc>: 1", "", "<aaaa>: 1\r\n<bbbb>: 1\r\n<ccccss>: 1\r\n<dddddd>: 1"
    };

    @Test
    public void getCharacterNumber() {
        for (int i = 0; i < files.length; i++) {
            File input = new File("testExample/" + files[i]);
            WordCount wordCount = new WordCount(input);
            assertEquals(chars[i], wordCount.getCharacterNumber());
        }
    }

    @Test
    public void getLineNumber() {
        for (int i = 0; i < files.length; i++) {
            File input = new File("testExample/" + files[i]);
            WordCount wordCount = new WordCount(input);
            assertEquals(lines[i], wordCount.getLineNumber());
        }
    }

    @Test
    public void getWordNumber() {
        for (int i = 0; i < files.length; i++) {
            File input = new File("testExample/" + files[i]);
            WordCount wordCount = new WordCount(input);
            assertEquals(words[i], wordCount.getWordNumber());
        }
    }

    @Test
    public void getList() {
        for (int i = 0; i < files.length; i++) {
            File input = new File("testExample/" + files[i]);
            WordCount wordCount = new WordCount(input);
            assertEquals(lists[i], wordCount.getList().trim());
        }
    }
}