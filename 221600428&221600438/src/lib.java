import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class lib {
	//System.out.println(System.getProperty("user.dir"));
	//String str = System.getProperty("user.dir");  //获取当前目录
	int charCount = 0;
	int lineCount = 0;
	int wordCount = 0;
	List<String> list = new ArrayList<String>();
	Map<String, Integer> words = new TreeMap<String, Integer>();
	static ArrayList<Map.Entry<String, Integer>> maplist=null;
	
	public void CountChar(String path)
	{		
		try
		{
			
			InputStreamReader r = new InputStreamReader(new FileInputStream(path));
			BufferedReader br = new BufferedReader(r);
			int a=0;
			int len=0;
			String str=null;
			while((str=br.readLine()) != null)
			{
				//str = str +(char)a;
				a=str.length()-1;
				len=a+1;
				while(a>=0)
				{
					char o=str.charAt(a);
					if(o>=0 && o<=127)
					{
						charCount++;
					}
					a--;
				}
				charCount++;
			}
			if(len != 0) charCount--;
			//str=str.replaceAll("\\r\\n+", "a");
			//charCount=str.length();
			r.close();
		}
		catch(IOException e)
		{
			System.out.println("文件读取出错！");
		}	
	}
	
	public void CountLine(String path)
	{
		try
		{
			InputStreamReader r = new InputStreamReader(new FileInputStream(path));
			BufferedReader br = new BufferedReader(r);
			String s= br.readLine();
			while(s != null)
			{
				//charCount+=s.length();
				//System.out.println(s);
				if(!s.trim().equals(""))
				{
					lineCount++;
				}
				s=br.readLine();
			}	
			r.close();		
		}
		catch(IOException e)
		{
			System.out.println("文件读取出错！");
		}		
		
	}
	
	public void CountWord(String path)
	{	
		try
		{
			InputStreamReader r = new InputStreamReader(new FileInputStream(path));
			BufferedReader br = new BufferedReader(r);
			String s= br.readLine();
			while(s != null)
			{
				s=s.toLowerCase();			
				String wordstr[] = s.split("[^a-zA-Z0-9]");
				int x = wordstr.length;
				for(int i=0; i<x; i++)
				{					
					/*if(wordstr[i].length() >= 4)
					{	
						//System.out.println(wordstr[i]+"  "+wordstr[i].length()+"  ");
						String st = wordstr[i].substring(0, 4);
						//System.out.print(st.matches("[a-zA-Z]+"));
						if(st.matches("[a-zA-Z]+"))
						{
							++wordCount;
							list.add(wordstr[i]);
						}
					}*/
					if(wordstr[i].matches("[a-z]{4}[a-z0-9]*"))
					{
						++wordCount;
						list.add(wordstr[i]);
					}
				}
				s=br.readLine();
			}	
			r.close();		
			
		}
		catch(IOException e)
		{
			System.out.println("文件读取出错！");
		}	
	}
	
	public void CountFre()
	{
		for(String li: list)
		{
			if(words.get(li) != null)
			{
				words.put(li, words.get(li)+1);
			}
			else words.put(li, 1);
		}
		maplist = new ArrayList<Map.Entry<String, Integer>>(words.entrySet());
		Collections.sort(maplist, new Comparator<Map.Entry<String, Integer>>(){
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				// TODO Auto-generated method stub
				return o2.getValue() - o1.getValue(); //降序
			}
		});
		
		try
		{
			File file = new File("result.txt");
			BufferedWriter br = new BufferedWriter(new FileWriter(file));
			br.write("characters: "+charCount+"\r\n");
			br.write("words: "+wordCount+"\r\n");
			br.write("lines: "+lineCount+"\r\n");
			for(int i=0; i<maplist.size(); i++)
			{
				if(i>=10) break;
				br.write("<"+maplist.get(i).getKey()+">: "+maplist.get(i).getValue()+"\r\n");
			}	
			br.close();
		}
		catch(Exception e)
		{
			System.out.println("文件读取出错！");
		}
		
		for(int i=0; i<maplist.size(); i++)
		{
			if(i>=10) break;
			System.out.println("<"+maplist.get(i).getKey()+">: "+maplist.get(i).getValue());
		}		
	}
	
	//Scanner input = new Scanner(System.in);
	//System.out.print("请输入路径:");
	//String path = input.next();
	//path = "src/"+path;
	//System.out.println(path); //获取文件路径
	
	public void print()
	{
		System.out.println("characters: "+charCount);	
		System.out.println("words: "+wordCount);
		System.out.println("lines: "+lineCount);
	}
	
}
