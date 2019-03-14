import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class wordCount 
{
	private static String output = "result.txt"; // 默认输出文件
	// 计数
	int characters = 0;
	int lines = 0;
	int words = 0;
	count counter;
	
	public wordCount()
	{
		counter = new count();
	}
	public void countWords(String fileName, int top, boolean all) throws IOException // top为前几的词频
	{
		wordHeap wh = new wordHeap();
		HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		// 判断是否存在输出文件，不存在则创建
		File file = new File(output);
		if(!file.exists())
		{
			try {    
		        file.createNewFile();    
		    } catch (IOException e) {    
		        // TODO Auto-generated catch block    
		        e.printStackTrace();    
		    }
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		String content; // 利用readline()函数读取每一行的值
		while((content = br.readLine()) != null)
		{
			String type = "";
			if(!all)
			{
				if(content.length() < 4)
					continue;
				int index = counter.cutWords(content); // 调用count类的单词分割，将第一个单词分割出来
				if(index == content.length())
					continue;
				type = content.substring(0, counter.cutWords(content));
				content = content.substring(index + 2); // 冒号与空格不计
			}
			characters += counter.countCharacters(content, all); // 字符计数
			lines += counter.countLines(content); // 有效行计数
			words += counter.countWords(content, wordMap, type); // 单词计数
		}
		wordClassify(wordMap, wh); // 单词归类并进行
		br.close();
		bw.write("character:" + characters);
		bw.newLine();
		bw.write("words:" + words);
		bw.newLine();
		bw.write("lines:" + lines);
		bw.newLine();
		top = (top < wh.size() - 1) ? top : wh.size() - 1;
		for(int i = 1; i <= top ; i ++) // 堆排序
		{
			bw.write("<" + wh.heap.get(1).word + ">: " + wh.heap.get(1).value); 
			bw.newLine();
			wh.delete(1);
		}
		bw.flush();
		bw.close();
	}
	
	public void wordClassify(HashMap<String, Integer> wordMap, wordHeap wh) // 单词归类，将哈希表中的单词归入词堆（最大堆）
	{
		String word;
		java.util.Iterator<Entry<String, Integer>> iter = wordMap.entrySet().iterator(); // 键值对遍历哈希表
		Integer value = null;
		while(iter.hasNext())
		{
			Entry<String, Integer> entry = (Entry<String, Integer>)iter.next();
		    word = (String)entry.getKey(); // 获取key
		    value = (Integer)entry.getValue(); // 获取value
		    wh.insert(word, value);
		}
	}
}
