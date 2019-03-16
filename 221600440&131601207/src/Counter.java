

import java.util.*;
import java.util.regex.*;

class Counter {
	private int charCnt=0;
	private int wordCnt=0;
	private Map<String, Integer> map = new HashMap<String, Integer>(); //Map<单词，数量>
	private String text;
	
	public int getCharCnt() {
		return charCnt;
	}
	public int getWordCnt() {
		return wordCnt;
	}
	public Map<String, Integer> getMap() {
		return map;
	}
	public String getText() {
		return text;
	}
	
	public Counter(String text) {
		text=text.replaceAll("\r\n","\n");
		this.text=text;
	}
	public void charCount() {
		String charRegex="[\\x00-\\x7F]";//[\p{ASCII}]
		Pattern p=Pattern.compile(charRegex);
		Matcher m=p.matcher(text);
		while(m.find()){
			charCnt++;
		}
	}
	
	Map<String, Integer> wordCount() {
		String lowerText=text.toLowerCase();
		String splitRegex="[^a-z0-9]";//分隔符
		lowerText=lowerText.replaceAll(splitRegex," ");//将非字母数字替换为空格
		String words[]=lowerText.split("\\s+");//利用空白分割所有单词
		String wordRegex = "[a-z]{4,}[a-z0-9]*";//单词匹配正则表达式
		for(int i=0;i<words.length;i++) {
			Pattern p=Pattern.compile(wordRegex);
	        Matcher m=p.matcher(words[i]);
	        if(m.find()) {//符合单词定义
	        	wordCnt++;
	        	Integer num = map.get(words[i]); 
                if(num == null || num == 0){  
                    map.put(words[i], 1);  //map中无该单词，数量置1
                }else if(num > 0){  
                    map.put(words[i], num+1);  //map中有该单词，数量加1
                }
	        }
		}
		map=sortMap(map);
		return map;
	}
	
	//按键值对排序
	public static <K extends Comparable<? super K>, V extends Comparable<? super V>> Map<K, V> sortMap(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int re=o2.getValue().compareTo(o1.getValue());
        		if(re!=0)  return re;
        		else return o1.getKey().compareTo(o2.getKey());
            }
        });
        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
