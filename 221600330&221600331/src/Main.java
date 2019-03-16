import java.io.File;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		int countChar;
		int countLinnes;
		String file_name=args[0];
		Map<String, String> wordsMap;
		countChar = wordCount.count_Characters(file_name);
		wordsMap = wordCount.count_Words(file_name);
		countLinnes = wordCount.count_Lines(file_name);
		
		wordCount.writeToFile(countChar,wordsMap,countLinnes);
	}
	
	


}