package wordcount;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class Main {
	
	private File file;
	private Vector<Integer> fileCharIntegers;//存储文本中所有字符的ASCII码
	private Vector<String> strings;//文本中的单词数组
	private Map<String,Integer> map;//单词-词频，key-value映射
    private List<Map.Entry<String, Integer>> list;//map以value排序，得到词频最高的10个单词

    public Main(String filepath) {
		file = new File(filepath);
		fileCharIntegers = new Vector<Integer>();
		strings = new Vector<String>();
		map = new TreeMap<String, Integer>();//满足频率相同输出字典序靠前的单词，TreeMap默认按 key排序
	}

	public List<Map.Entry<String, Integer>> topTenWord(){
		//初始化map
		for (int i = 0; i < strings.size(); i++) {
			map.put(strings.elementAt(i), 0);
		}
		//统计词频
		for (int i = 0; i < strings.size(); i++) {
			int temp=map.get(strings.elementAt(i));
			temp++;
			map.put(strings.elementAt(i), temp);
		}
		
		//输出出现频率最高的10个单词， map<key,value>以value排序
		
		//通过ArrayList构造函数把map.entrySet()转换成list
        list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        //通过比较器实现比较排序
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> mapping1, Map.Entry<String, Integer> mapping2) {
                return mapping2.getValue().compareTo(mapping1.getValue());
            }
        });
        
        
		return list;
    }

    public int countLine() {
	    int count=0;
    	try {
			DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
			BufferedReader d  = new BufferedReader(new InputStreamReader(inputStream));
			String string;
			while((string = d.readLine()) != null){
				//System.out.println(count);
				char chars[] = new char[string.length()];
				chars=string.toCharArray();
				for (int i = 0; i < chars.length; i++) {
					//System.out.println(chars[i]);
					if ((int)chars[i] > 32 && (int)chars[i] != 127) {
						count++;
					    break;
					}
				}
			}
			d.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return count;
	}
    
	public int countWord() {
		StringBuffer sb = new StringBuffer();
		int countFourLetter = 0;
		for (int i = 0; i < fileCharIntegers.size(); i++) {
			//四个英文字母开头
			if (Character.isLetter((char)(int)fileCharIntegers.elementAt(i))) {
				countFourLetter++;		
				sb.append((char)(int)fileCharIntegers.elementAt(i));
				if (countFourLetter>=4 && i == fileCharIntegers.size()-1) {
					strings.add(sb.toString().toLowerCase());
				}
			}else if (Character.isDigit((char)(int)fileCharIntegers.elementAt(i))&&countFourLetter>=4) {
				sb.append((char)(int)fileCharIntegers.elementAt(i));
				if (countFourLetter>=4 && i == fileCharIntegers.size()-1) {
					strings.add(sb.toString().toLowerCase());
				}
				//System.out.println("the digit:"+sb.toString());			
			}else {
				if (countFourLetter>=4) {
					strings.add(sb.toString().toLowerCase());
				}
				countFourLetter=0;
				sb.delete(0, sb.length());
			}
		}
		
		return strings.size();
	}


	public int countChar() {
		int count=0;
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			int charInt = fileInputStream.read();
			for (count = 0; charInt != -1; count++) {
				fileCharIntegers.add(charInt);
				charInt=fileInputStream.read();
			}
			fileInputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//处理回车换行\r\n 13 10 为一个字符
		for (int i = 0; i < fileCharIntegers.size(); i++) {
			if (fileCharIntegers.elementAt(i)==13) {
				if (i<fileCharIntegers.size() && fileCharIntegers.elementAt(i+1)==10) {
					count--;
				}
			}
		}
		
		return count;
	}
    
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//读文件
		try {
			/*重定向标准输出流*/
			System.setOut(new PrintStream("result.txt"));
			
			/*接收命令行参数，即文件路径，构造Main类*/
			Main wordcount = new Main(args[0]);
			
			
			//统计文件的字符数numOfChar
			int numOfChar=wordcount.countChar();
			System.out.println("characters: "+numOfChar);


			//统计文件的单词数numOfWord
			int numOfWord = wordcount.countWord();
			System.out.println("words: "+numOfWord);
			
			//统计文件的有效行数numOfLine
			int numOfLine=wordcount.countLine();
			System.out.println("lines: "+numOfLine);

			//统计各个单词的出现次数 KEY-VALUE映射
			//输出频率最高的10个单词，频率相同输出字典序靠前的单词，String key排序
			List<Map.Entry<String, Integer>> list = wordcount.topTenWord();
	        for (int i = 0; i < 10 && i < list.size(); i++) {
				System.out.print("<"+list.get(i).getKey()+">: "+list.get(i).getValue());
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
