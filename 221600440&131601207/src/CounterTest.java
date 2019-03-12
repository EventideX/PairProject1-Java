import java.io.IOException;

import org.junit.Test;

public class CounterTest {
	FileUtil fileUtil1=new FileUtil("d:/input1.txt");
	FileUtil fileUtil2=new FileUtil("d:/input2.txt");
	@Test
	public void testCharCount() throws IOException {
		String text1=fileUtil1.FiletoText();
		String text2=fileUtil2.FiletoText();
		Counter c=new Counter(text1);
		c.charCount();
		System.out.println("test charCount with input1.txt:"+c.getCharCnt());
		c=new Counter(text2);
		c.charCount();
		System.out.println("test charCount with input2.txt:"+c.getCharCnt());
	}

	@Test
	public void testWordCount() throws IOException {
		String text1=fileUtil1.FiletoText();
		String text2=fileUtil2.FiletoText();
		Counter c=new Counter(text1);
		c.wordCount();
		System.out.println("test wordCount with input1.txt:"+c.getWordCnt());
		System.out.println(c.getMap());
		c=new Counter(text2);
		c.wordCount();
		System.out.println("test wordCount with input2.txt:"+c.getWordCnt());
		System.out.println(c.getMap());
	}
}
