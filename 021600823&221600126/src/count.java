import java.util.HashMap;

public class count 
{
	public count()
	{
		
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
		if(x <= 48 || x >= 123 || (x >=58 && x <= 64) || (x >= 91 && x <= 96))
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
	
	public int countCharacters(String input) // 统计字符数
	{
		int result = 0;
		char[] charArray = input.toCharArray();
		for(int index = 0; index < charArray.length; index ++)
		{
			if(isAscii(charArray[index]))
				result ++;
		}
		result ++; // readline()方法不返回换行符 自动+1
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
	
	public int countWords(String input, HashMap<String, Integer> wordMap) // 统计单词数，并存入哈希表
	{
		int result = 0;
		String word = "";
		input = input.toLowerCase(); // 转化为小写
		char[] charArray = input.toCharArray();
		for(int index = 0; index < charArray.length; index ++)
		{
			if(isCharacter(charArray[index])) // 出现非字母数字字符则进入开始判断
			{
				if(isWord(word)) // 是符合规范的单词，则存入哈希表中
				{
					if(wordMap.get(word) == null)
						wordMap.put(word, 1);
					else
						wordMap.put(word, wordMap.get(word) + 1);
					result ++;
				}
				word = "";
			}
			else
			{
				word += charArray[index];
			}
		}
		if(isWord(word)) // 防止换行符导致最后一个单词漏读
		{
			if(wordMap.get(word) == null)
				wordMap.put(word, 1);
			else
				wordMap.put(word, wordMap.get(word) + 1);
			result ++;
		}
		return result;
	}
}
