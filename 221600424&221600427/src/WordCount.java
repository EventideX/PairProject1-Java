import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
public class WordCount{
/*-----------统计函数--------------*/
	public static int countChar(File file) throws IOException {
		int charnum=0;
		String mString=readToString(file);
		String s = mString.replaceAll("(\\r\\n\\r\\n[0-9]+\\r\\n)|([0-9]+\\r\\n)","");
		String ns1=s.replace("Title: ", "");
		String ns2=ns1.replace("Abstract: ", "");
		for(int i=0;i<ns2.length();i++) {
			if (isChar(ns2.charAt(i))) {
				charnum++;
			}
		}
		return charnum;
	}
	
	public static int countLine(File file) throws IOException {
		int linenum=0;
		BufferedReader reader=new BufferedReader(new FileReader(file));
		String s=null;
		while((s=reader.readLine())!=null) {
			if (!inputProcess(s).equals("")&&!Pattern.matches("\\s*", s)) {
				linenum++;	
			}
		}
		reader.close();
		return linenum;
	}
	
	public static int countWord(File file) throws IOException{
		int wordnum=0;
		BufferedReader reader=new BufferedReader(new FileReader(file));
		String s=null;
		while((s=reader.readLine())!=null) {
			if (Pattern.matches("[0-9]+", s)) {
				continue;
			}	
			String ns=inputProcess(s);
			String[] strings=toWordList(ns);
			for(String string:strings) {
				if (isWord(string)) {
					wordnum++;
				}
			}
		}
		return wordnum;
	}
	
	
	public static String[] outPutTop10(File file,int w,int n,int m) throws IOException {
		HashMap<String, Integer> wordMap=toPhraseMap(file,w,m);
		
        ArrayList<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(wordMap.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				// TODO Auto-generated method stub
				if (o1.getValue()>o2.getValue()) {				
					return -1;
				}
				else if (o1.getValue()==o2.getValue()) {
					return o1.getKey().compareTo(o2.getKey());
				}
				 return 1;
			}
        });
        int num=0;
        String[] top10=new String[n];
        for(Map.Entry<String,Integer> mapping:list){ 
        	if (num==n||mapping.getKey()==null) {
				break;
			}
        	top10[num]="<"+mapping.getKey()+">"+":"+mapping.getValue();
        	num++;
        } 
        return top10;
    }
	
	
/*------------HashMap--------------*/
	public static HashMap<String, Integer> toPhraseMap(File file,int w,int m) throws IOException {
		HashMap<String, Integer> hashMap=new HashMap<>();
		BufferedReader reader=new BufferedReader(new FileReader(file));
		String s=null;
		int weight=1;
		while((s=reader.readLine())!=null) {
			if (Pattern.matches("[0-9]+", s)) {
				continue;
			}
			weight=1;
			if (Pattern.matches(".*Title: .*",s)&&w==1) {
				weight=10;
			}		
			String ns=inputProcess(s);
			
			String[] strings=toWordList(ns);
			String[] strings2=toBreakList(ns);
			String phrase="";
			String lowerstring=null;
			boolean find=true;
			for(int i=0;i<strings.length-m+1;i++) {
				find=true;
				phrase="";
				for(int j=i;j<i+m;j++) {
					if (isWord(strings[j])) {
						lowerstring=strings[j].toLowerCase();
						phrase=phrase+lowerstring;
						if (m!=1&&j!=i+m-1) {
							if (strings[0].equals("")) {
								phrase=phrase+strings2[j];
							}
							else if (strings2[0].equals("")) {
								phrase=phrase+strings2[j+1];
							}
						}
					}
					else {
						find=false;
						break;
					}
				}
				if (find) {
					if (hashMap.containsKey(phrase)) {
						hashMap.put(phrase, hashMap.get(phrase)+weight);
					}
					else {
						hashMap.put(phrase, weight);
					}
				}
			}
		}
		reader.close();
		return hashMap;
	}
	
	
	
/*------------字符处理--------------*/
	
	public static String inputProcess(String s) {
		if (s.matches("[0-9]*")) {
			return s.replaceAll("[0-9]*","");
		}
		else if (s.contains("Title: ")) {
			return s.substring(7);
		}
		else if (s.contains("Abstract: ")) {
			return s.substring(10);
		}
		return s;
	}
	public static String[] toWordList(String s) {
		Pattern pattern2=Pattern.compile("[^A-Za-z0-9]+");
		String[] strings=pattern2.split(s);
		return strings;
	}
	public static String[] toBreakList(String s) {
		Pattern pattern2=Pattern.compile("[a-zA-Z0-9]+");
		String[] strings=pattern2.split(s);
		return strings;
	}
	public static boolean isChar(int c) {
		if (c>=0&&c<=127&&c!=13) {
			return true;
		}
		return false;
	}
	public static boolean isWord(String s) {
		return s.matches("[a-zA-Z]{4}[a-zA-Z0-9]*");
	}

/*----------------IO-------------------*/
	public static String readToString(File file) throws IOException {
        String encoding = "UTF-8";  
        Long filelength = file.length();  
        byte[] filecontent = new byte[filelength.intValue()];  
        FileInputStream in = new FileInputStream(file);  
        in.read(filecontent);  
        String temp=new String(filecontent, encoding);
        in.close();
        return temp;
    }  
	public static void outCount(File input,File output,int w,int n,int m) throws IOException {
		if (!output.exists()) {
			output.createNewFile();
		}
		BufferedWriter writer=new BufferedWriter(new FileWriter(output));
		writer.write("characters:"+WordCount.countChar(input)+"\r\n");
		writer.write("words:"+WordCount.countWord(input)+"\r\n");
		writer.write("lines:"+WordCount.countLine(input)+"\r\n");
		String[] top10=WordCount.outPutTop10(input,w,n,m);
		for(int i=0;i<n;i++) {
			if (top10[i]!=null) {
				writer.write(top10[i]+"\r\n");
			}
		}
		writer.close();
	}
}

