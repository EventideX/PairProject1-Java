import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

public class Main {

	private static String output_path="result.txt";

	public static void main(String[] args)throws Exception{
		String input_path=args[0];
		//基本需求
		lib lib=new lib();
		String contentStr=lib.getContentAndLinesFromFilePath(input_path);
		List<String> wordList=lib.splitStringIntoList(contentStr);
		Integer lines=lib.getLineCountFromFilePath(input_path);
		Integer characters=lib.countLetters(input_path);
		Integer words=wordList.size();
		Map<String,Integer> map=lib.getTopTenWords(wordList,10);
		BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(output_path))));
		bw.write("characters: "+characters+"\r\n");
		bw.write("words: "+words+"\r\n");
		bw.write("lines: "+lines+"\r\n");
		for(String key:map.keySet()){
			bw.write("<"+key+">: "+map.get(key)+"\r\n");
		}
		bw.flush();
		bw.close();
		//基本需求
	}
}