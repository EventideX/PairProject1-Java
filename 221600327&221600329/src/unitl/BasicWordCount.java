package unitl;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.regex.*;
import bean.Word;

public class BasicWordCount {
	
	/**
	 * 计算文件字符数
	 * @param fileName 文件名
	 * @return countOfCharacter 字符数
	 */
	public long characterCount(String fileName) {
		BufferedReader bufferedReader = null;
		StringBuffer stringBuffer = new StringBuffer();
		//读入文件数据
		bufferedReader = IOUtils.readFile(fileName);
		int byteCode = -1;
		//转换为字符串 
		try {
			while((byteCode = bufferedReader.read()) != -1) {
				//将十进制ASCII码值，转化为字符，添加到data
				stringBuffer.append((char)byteCode);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//在windows中，换行符为/r/n,统计时需要替换为/n
		return stringBuffer.toString().replaceAll("\r\n", "\n").length();	
	}

	/**
	 * 计算文件单词数
	 * @param fileName 文件名
	 * @return countOfWord 单词数
	 */
	public long wordCount(String fileName) {
		BufferedReader bufferedReader = null;
		StringBuffer stringBuffer = new StringBuffer();
		//读入文件数据
		bufferedReader = IOUtils.readFile(fileName);
		int byteCode = -1;
		//转换为字符串 
		try {
			while((byteCode = bufferedReader.read()) != -1) {
				//将十进制ASCII码值，转化为字符，添加到data
				stringBuffer.append((char)byteCode);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//用分隔符"|"替换字符串中非字母数字的字符
		String noLetterOrDigitalRegex = "[^a-zA-Z0-9]";
		String updateString = stringBuffer.toString().toLowerCase().replaceAll(noLetterOrDigitalRegex, "|");
		long countOfWord = 0;
		int startPosition = 0;
		int endPosition = 0;
		String regex = "^[a-z]{4}[a-z0-9]*";
		//计算到次数
		for(int i = 0; i<updateString.length();) {
			startPosition = i;
			endPosition = i;
			while(Character.isLetterOrDigit(updateString.charAt(i++))) {
				endPosition++;
			}
			if(Pattern.matches(regex, updateString.substring(startPosition, endPosition))) {
				countOfWord++;
			}
		}
		return countOfWord;
	}
	/**
	 * 计算出现次数top10的单词及其词频
	 * @param fileName 文件名
	 * @return Word[] 单词数组
	 */
	public Word[] topTenWord(String fileName) {
		BufferedReader bufferedReader = null;
		StringBuffer stringBuffer = new StringBuffer();
		//读入文件数据
		bufferedReader = IOUtils.readFile(fileName);
		int byteCode = -1;
		//转换为字符串 
		try {
			while((byteCode = bufferedReader.read()) != -1) {
				//将十进制ASCII码值，转化为字符，添加到data
				stringBuffer.append((char)byteCode);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//用分隔符"|"替换字符串中非字母数字的字符
		String noLetterOrDigitalRegex = "[^a-zA-Z0-9]";
		String updateString = stringBuffer.toString().toLowerCase().replaceAll(noLetterOrDigitalRegex, "|");
		//按分隔符"|"，分割字符串
		String splitStrings[] =  updateString.split("\\|");
		
		String regex = "[a-z]{4}.*";
		//进行统计的散列表
		Map<String, Integer> countMap = new HashMap<>();
		for(int i = 0; i < splitStrings.length; i++) {
			if(Pattern.matches(regex, splitStrings[i])) {
				Integer outValue = countMap.get(splitStrings[i]);
				if (null == outValue) {
				      outValue = 0;
				}
				outValue++;
				countMap.put(splitStrings[i], outValue);
			}
		}
		//求top10
		PriorityQueue<Word> topN = new PriorityQueue<>(10, comp);
	    Iterator<Map.Entry<String, Integer>> iter = countMap.entrySet().iterator();
	    Map.Entry<String, Integer> entry;
	    while (iter.hasNext()) {
	      entry = iter.next();
	      if (topN.size() < 10) {
	        topN.offer(new Word(entry.getKey(), entry.getValue()));
	      } else {
	        // 如果当前数据比小顶堆的队头大，则加入，否则丢弃
	        if (topN.peek().getCountNum() < entry.getValue()) {
	        	topN.poll();
	        	topN.offer(new Word(entry.getKey(), entry.getValue()));
	        }else if(topN.peek().getKey().compareTo(entry.getKey())>0){
	        	topN.poll();
		        topN.offer(new Word(entry.getKey(), entry.getValue()));
	        }
	      }
	    }
	    //结果集
	    Word[] result = null;
	    int wordCount = countMap.size();
	    if(wordCount < 10) {
	    	result = new Word[(int) wordCount];
	    }else {
	    	result = new Word[10];
	    }
	    topN.toArray(result);
	    //对top10单词排序
	    Arrays.sort(result, comp);
		return result;
	}
	/**
	 * 计算文件行数
	 * @param fileName 文件名
	 * @return count 行数
	 */
	public long lineCount(String fileName) {
		FileReader filereader = null;
		BufferedReader bufferedreader = null;
        //读入文件数据
		long count=0;
		//统计行数
		try {
			filereader = new FileReader(fileName);
			bufferedreader = new BufferedReader(filereader);

			String value = bufferedreader.readLine();
			//按行读取
			while (value != null) {
				//先判断是否为空
				if(!value.trim().isEmpty())
					//string.trim()去除空白字符
					count++;
				value = bufferedreader.readLine();
				//继续往下读
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
          finally { 
        	  try {
        		  if (bufferedreader != null)
        			  bufferedreader.close();
				  if (filereader != null)
					  filereader.close();
			      } catch (IOException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
	/**
	 * Comparator接口实现小顶堆排序
	 */
	private Comparator<Word> comp =
			(o1, o2) -> {
				if (o1.getCountNum() > o2.getCountNum()) {
					return 1;
				} else if (o1.getCountNum() < o2.getCountNum()) {
					return -1;
				}else if(o1.getCountNum() == o2.getCountNum()) {
					if(o1.getKey().compareTo(o2.getKey())<0) {	//字典序排序
						return 1;
					}else {
						return -1;
					}
				}
				return 0;
			};

}
