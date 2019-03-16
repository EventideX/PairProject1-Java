import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	public static void main(String[] args) throws IOException {
		
		String destFile="result.txt";
		File fileName=new File(destFile);
		if(!fileName.exists()) {
			fileName.createNewFile();		
		}
		BufferedWriter bw=new BufferedWriter(new FileWriter(destFile));
		bw.write("characters: "+Lib.charNum(args[0])+"\r\n");
		bw.write("words: "+Lib.letterNum(args[0])+"\r\n");
		bw.write("lines: "+Lib.lineNum(args[0])+"\r\n");
		String[] strs=null;
		List<String> list=new ArrayList<String>();
		List<Integer>list1=new ArrayList<Integer>();
		Lib.getWordAndCount(args[0],list,list1);
		for(int i=0;i<list.size();i++) {
			bw.write("<"+list.get(i)+">: "+list1.get(i)+"\r\n");
		}
		bw.close();
	}
	
}
