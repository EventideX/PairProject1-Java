

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class Main {
	public static void main (String args[]) throws IOException {
		String path=args[0];
		FileUtil fileutil=new FileUtil(path);
		String text=fileutil.FiletoText();
		Counter c=new Counter(text);
		c.charCount();
		c.wordCount();
		
		System.out.println("characters:"+c.getCharCnt());
		System.out.println("words:"+c.getWordCnt());
		fileutil.lineCount();
		Set<Entry<String,Integer>> entry = c.getMap().entrySet();
		System.out.println("lines:"+fileutil.getLineCnt());
		for(Entry<String,Integer> ent : entry){
            System.out.println("<"+ent.getKey()+">:"+ent.getValue());
        }
	}
}
