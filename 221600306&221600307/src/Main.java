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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//读文件
		//System.out.println(args[0]);
		try {
			/*重定向标准输出流*/
			System.setOut(new PrintStream("result.txt"));
			
			File file = new File(args[0]);
			
			
			FileInputStream fin = new FileInputStream(file);
			int numOfChar;
			Vector<Integer> bs = new Vector<Integer>();
			int b = fin.read();
			for (numOfChar = 0; b != -1; numOfChar++) {
				//System.out.println(b);	
				bs.add(b);
				b=fin.read();
			}
			fin.close();
			
			//处理\r\n 13 10
			for (int i = 0; i < bs.size(); i++) {
				if (bs.elementAt(i)==13) {
					if (i<bs.size() && bs.elementAt(i+1)==10) {
						numOfChar--;
					}
				}
			}
			//统计文件的字符数numOfChar
			System.out.println("characters: "+numOfChar);//换行符中\r\n算两个，\n算一个
			
			
			
			Vector<String> strings = new Vector<String>();
			StringBuffer sb = new StringBuffer();
			
			int countFourLetter = 0;
			for (int i = 0; i < bs.size(); i++) {
				//四个英文字母开头
				if (Character.isLetter((char)(int)bs.elementAt(i))) {
					countFourLetter++;		
					sb.append((char)(int)bs.elementAt(i));
					if (countFourLetter>=4 && i == bs.size()-1) {
						strings.add(sb.toString().toLowerCase());
					}
				}else if (Character.isDigit((char)(int)bs.elementAt(i))&&countFourLetter>=4) {
					sb.append((char)(int)bs.elementAt(i));
					if (countFourLetter>=4 && i == bs.size()-1) {
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
			//统计文件的单词数numOfWord
				//单词判断函数
			//System.out.println(strings);
			System.out.println("words: "+strings.size());
			
			//统计文件的有效行数numOfLine
			int numOfLine=0;
			DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
			BufferedReader d  = new BufferedReader(new InputStreamReader(inputStream));

			String count;
			while((count = d.readLine()) != null){
				//System.out.println(count);
				char chars[] = new char[count.length()];
				chars=count.toCharArray();
				for (int i = 0; i < chars.length; i++) {
					//System.out.println(chars[i]);
					if ((int)chars[i] > 32 && (int)chars[i] != 127) {
					    numOfLine++;
					    break;
					}
				}
			}
			d.close();
			inputStream.close();
			System.out.println("lines: "+numOfLine);
			
			
			//统计各个单词的出现次数 KEY-VALUE映射
			Map<String,Integer> map = new TreeMap<String, Integer>();
			//初始化map
			for (int i = 0; i < strings.size(); i++) {
				map.put(strings.elementAt(i), 0);
			}
			//统计
			for (int i = 0; i < strings.size(); i++) {
				int temp=map.get(strings.elementAt(i));
				temp++;
				map.put(strings.elementAt(i), temp);
			}
			//System.out.println(map);
				//输出出现频率最高的10个 value SORT排序
			 // 通过ArrayList构造函数把map.entrySet()转换成list
	        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
	        // 通过比较器实现比较排序
	        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
	            public int compare(Map.Entry<String, Integer> mapping1, Map.Entry<String, Integer> mapping2) {
	                return mapping2.getValue().compareTo(mapping1.getValue());
	            }
	        });
	      //频率相同输出字典序靠前的单词	String key排序
	        for (int i = 0; i < 10 && i < list.size(); i++) {
				System.out.print("<"+list.get(i).getKey()+">: "+list.get(i).getValue());
			}
			
	        //System.out.println(list);
			//频率相同输出字典序靠前的单词	String key排序
			//characters: number
			//words: number
			//lines: number
			//<word1>: number
			//<word2>: number
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



}
