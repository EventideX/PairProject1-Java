

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
		String result= new String();
		result="characters:"+c.getCharCnt()+"\r\n";
		result+="words:"+c.getWordCnt()+"\r\n";
		fileutil.lineCount();
		result+="lines:"+fileutil.getLineCnt()+"\r\n";
		Set<Entry<String,Integer>> entry = c.getMap().entrySet();
		if(entry.size()<=10) {
			for(Entry<String,Integer> ent : entry){
				result+="<"+ent.getKey()+">:"+ent.getValue()+"\r\n";
	        }
		}
		else {
			int i=0;
			for(Entry<String,Integer> ent : entry) {
				result+="<"+ent.getKey()+">:"+ent.getValue()+"\r\n";
				i++;
				if(i>=10) break;
			}
		}
		fileutil.resultToFile(result);
        }
}
