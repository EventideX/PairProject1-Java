import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length<=0)
			System.exit(-1);
		String filename=args[0];
		if(filename.equals(""))
			System.exit(-1);
		WordsHandler wordsHandler=new WordsHandler();
		//统计字符
		wordsHandler.charCnt(filename);
		//统计行数和单词数
		wordsHandler.lineCnt(filename);
		wordsHandler.printInfo();
		
	}
}

//处理类
class WordsHandler{
	int wordCnt=0;//单词总数
	int charCnt=0;//字符数
	int lineCnt=0;//有效行数

	Map<String, Integer> wordCntMap=new HashMap<String,Integer>();//每个单词的数量
	
	public WordsHandler() {
		// TODO Auto-generated constructor stub
	}
	
	//每行去开头和结尾的所有空白字符
	public String standard(String line) {
		String newLine=line.trim();
		return newLine;
	}
	
	//字符数统计
	public void charCnt(String filename) {
		int ch;
		FileReader reader;
		try {
			reader=new FileReader(filename);
			while ((ch=reader.read())!=-1) {
				if(ch=='\r')
					continue;
				if(ch<128) {
					charCnt++;
				}
			}
			reader.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	//行数统计
	public void lineCnt(String filename) {
		BufferedReader reader;
		String line;
		try {
			reader=new BufferedReader(new FileReader(filename));
			while ((line=reader.readLine())!=null) {
				if(!standard(line).equals("")) {
					lineCnt++;
					wordCnt(standard(line));
				}
			}
			reader.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//将单词转为小写
	public void tolowerCase(String arr[]) {
		for(int i=0;i<arr.length;++i)
			arr[i]=arr[i].toLowerCase();
	}
	
	//单词数统计
	public void wordCnt(String line) {
		String arr[]=line.split("[^a-zA-Z0-9]"); //分割出潜在的单词
		tolowerCase(arr);
		for(int i=0;i<arr.length;++i) {
			//System.out.println(arr[i]);
			if(arr[i].matches("[a-z]{4}[a-z0-9]*")) {//判断是否是单词
				wordCnt++;
				if(!wordCntMap.containsKey(arr[i])) {
					wordCntMap.put(arr[i], 1);
				}else {
					wordCntMap.put(arr[i], wordCntMap.get(arr[i])+1);
				}
				
			}
		}
	}
	
	//输出信息
	public void printInfo() {
		//单词排序
		ArrayList<Map.Entry<String, Integer>> list=new ArrayList<Map.Entry<String,Integer>>(wordCntMap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
					@Override
					public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
						// TODO Auto-generated method stub
						if(o1.getValue()>o2.getValue())
							return -1;
						if(o1.getValue()==o2.getValue()) {
							return o1.getKey().compareTo(o2.getKey());
						}
						return 1;
					}
		});
		try {
			BufferedWriter writer=new BufferedWriter(new FileWriter("result.txt"));
			String string="";
			string+="characters: "+charCnt+"\r\n"+"words: "+wordCnt+"\r\n"+"lines: "+lineCnt+"\r\n";
			int i=0;
			//输出top10单词
			for(Map.Entry<String, Integer> item:list) {
				i++;
				if(i>10)
					break;
				if(i<10)
					string+="<"+item.getKey()+">: "+item.getValue()+"\r\n";	
			}
			writer.write(string);
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
