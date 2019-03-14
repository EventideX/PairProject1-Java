import java.util.HashMap;

import com.wordCount.wordHeap;

public class count 
{
	static wordHeap weightTable; // 利用词堆，建立一个权重表
	int weight;
	
	public count()
	{
		weightTable = new wordHeap();
		weightTable.add("", 1); // 添加默认权重
		//weight = 1;
	}
	
	public void addWeight(String type, Integer value)// 添加类型权重
	{
		type = type.toLowerCase(); // 全部替换成小写
		int index = weightTable.isExist(type); // 判断原先是否有权重记录，如果有则更新，没有则添加。
		if(index == -1)
			weightTable.add(type, value);
		else
			weightTable.heap.get(index).value = value;
	}
	
	public boolean isAscii(char c) //判断是否是0~127的字符
	{
		int x = (int)c;
		if(x >= 0 && x <= 127)
		{
			return true;
		}
		return false;
	}
	
	public boolean isCharacter(char c) // 判断是否是非字母数字字符
	{
		int x = (int)c;
		if(x <= 47 || x >= 123 || (x >=58 && x <= 64) || (x >= 91 && x <= 96))
		{
			return true;
		}
		return false;
	}
	
	public boolean isBlank(char c) // 判断是否是空白字符
	{
		int x = (int)c;
		if(x <= 33 || x == 127)
		{
			return true;
		}
		return false;
	}
	
	public boolean isWord(String input) // 判断是否是符合标准的单词
	{
		int x = -1;
		if(input.length() >= 4) //长度是否大于4
		{
			for(int i = 0; i < 4; i ++) // 前4个字符是否为字母
			{
				x = input.charAt(i);
				if(x < 97 || x > 122)
				{
					break;
				}
			}
		}
		if(x < 97 || x > 122)
			return false;
		return true;
	}
	
	public int countCharacters(String input, boolean all) // 统计字符数
	{
		int result = 0;
		char[] charArray = input.toCharArray();
		for(int index = 0; index < charArray.length; index ++)
		{
			if(isAscii(charArray[index]))
				result ++;
		}
		if(all) // 如果是基础需求 自动统计readline()方法省略的换行符
			result ++;
		else // 如果是进阶需求
		{
			
		}
		return result;
	}
	
	public int countLines(String input) // 统计行数
	{
		int result = 0;
		char[] charArray = input.toCharArray();
		for(int index = 0; index < charArray.length; index ++)
		{
			if(!isBlank(charArray[index])) // 出现非空字符则统计
			{
				result ++;
				break;
			}
		}
		return result;
	}
	
	public void addMap(String word, Integer weight, HashMap<String, Integer> wordMap) // 将单词加入哈希表中
	{
		if(wordMap.get(word) == null)
			wordMap.put(word, weight);
		else
			wordMap.put(word, wordMap.get(word) + weight);
	}
	
	public int cutWords(String input) // 分隔单词
	{
		char[] charArray = input.toCharArray();
		for(int index = 0; index < charArray.length; index ++)
		{
			if(isCharacter(charArray[index])) // 出现非字母数字字符则返回下标
				return index;
		}
		return charArray.length;
	}
	
	public int cutSign(String input) // 分割符号
	{
		char[] charArray = input.toCharArray();
		for(int index = 0; index < charArray.length; index ++)
		{
			if(!isCharacter(charArray[index])) // 出现非字母数字字符则返回下标
				return index;
		}
		return charArray.length;
	}
	
	public int countWords(String input, HashMap<String, Integer> wordMap, String type) // 统计单词数，并存入哈希表
	{
		int result = 0;
		String word = "";
		String sign = "";
		int cut = 0;
		weight = 1; // 初始权重为一
		if(type != "")
		{
			int i = weightTable.isExist(type); // 判断权值表中是否含有此类，有则替换权值。
			if(i != -1)
			{
				weight = weightTable.heap.get(i).value;
			}
		}
		input = input.toLowerCase(); // 转化为小写
		cut = cutWords(input);
		while(cut != input.length())
		{
			word = input.substring(0, cut);
			input = input.substring(cut);
			if(isWord(word))
			{
				addMap(word, weight, wordMap);
				result ++;
			}
			cut = cutSign(input);
			sign = input.substring(0, cut);
			input = input.substring(cut);
			cut = cutWords(input);
		}
		word = input;
		if(isWord(word)) // 防止最后一个单词漏读
		{
			addMap(word, weight, wordMap);
			result ++;
		}
		return result;
	}
}
