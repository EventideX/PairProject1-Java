import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class lib {
	String regex="\\s+|[^A-Za-z0-9]+";//匹配分隔符，空格和非字母数字
	String wordRegex="[A-Za-z]{4,}[A-Za-z0-9]*";
	/**
	 * 统计字符数
	 * @param inputPath
	 * @return
	 */
	public int countLetters(String inputPath)throws Exception{
		FileInputStream fileInputStream = new FileInputStream(new File(inputPath));
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
		BufferedReader br = new BufferedReader(inputStreamReader);
		int after;
		int pre=-10;
		int charCount=0;
		while ((after = br.read()) != -1) {
			if(pre==13&&after==10){
				pre=after;
				continue;
			}
			pre=after;
			++charCount;//加上回车
		}
		br.close();
		return charCount;
	}

	/**
	 * 转小写
	 * @param s
	 * @return
	 */
	public String toLower(String s){
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<s.length();++i){
			char ch=s.charAt(i);
			if(ch>=65&&ch<=90){
				ch+=32;
			}
			sb.append(ch);
		}
		return sb.toString();
	}

	/**
	 * 统计单词
	 * @param s
	 * @return
	 */
	public List<String> splitStringIntoList(String s){
		String[] list=s.split(regex);
		List<String> result=new ArrayList<>();
		for(String item:list){
			item=item.trim();
			if(item.length()>0){
				if(item.matches(wordRegex)) {
					result.add(item);
				}
			}
		}
		return result;
	}

	/**
	 * 统计前十字符
	 * @param strList
	 * @return
	 */
	public Map<String,Integer> getTopTenWords(List<String> strList,int arrayNum){
		Map<String,Integer> map=new HashMap<>();
		for(String item:strList){
			map.put(item,map.getOrDefault(item,0)+1);
		}
		return getOrderTopTenMap(map,arrayNum);
	}

	/**
	 * map按频率和字典序排序
	 * @param m
	 * @return
	 */
	public Map<String,Integer>getOrderTopTenMap(Map<String, Integer>m,Integer arrayNum){
		Map<String, Integer> map=sortMapByValue(m);//通过Value排序;
		int order=0;
		int numInten=0;
		for(Integer item:map.values()){
			++order;
			if(order==arrayNum){
				numInten=item;
			}
		}

		Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Integer> entry = it.next();
			if(entry.getValue()<numInten)
				it.remove();//使用迭代器的remove()方法删除元素
		}

		Set<Integer> valueSet=new TreeSet<>();//有序
		for(String key:map.keySet()){//看看有几种值结果
			valueSet.add(map.get(key));
		}

		Map<Integer,Integer> sameMap=new HashMap<>();//每种结果有多少个
		for(Integer value:valueSet){
			for(String key:map.keySet()){
				if(map.get(key)==value){
					sameMap.put(value,sameMap.getOrDefault(value,0)+1);
				}
			}
		}
		//相同频率拆段
		List<Map<String,Integer> > mapList=new ArrayList<>();
		for(Integer item:valueSet){
			Map<String,Integer> mapItem=new HashMap<>();
			for(String s:map.keySet()){
				if(map.get(s)==item){
					mapItem.put(s,map.get(s));
				}
			}
			mapList.add(mapItem);
		}

		List<Map<String,Integer> > mapList2=new ArrayList<>();
		//频率相同排序
		for(int i=mapList.size()-1;i>=0;--i){//set升序，频率最高的段在后面
			mapList2.add(sortMapByKey(mapList.get(i)));
		}
		Map<String,Integer>result=new LinkedHashMap<>();
		int num=0;
		for(int i=0;i<mapList2.size();++i){
			for(String key: mapList2.get(i).keySet()){
				++num;
				if(num<=arrayNum)
					result.put(key,mapList2.get(i).get(key));
				else
					break;
			}
		}
		return result;
	}

	/**
	 * 统计行数，获取文件内容成字符串，全转为小写
	 * @param path
	 * @return
	 */
	public String getContentAndLinesFromFilePath(String path){
		try{
			FileInputStream fileInputStream=new FileInputStream(new File(path));
			InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
			BufferedReader br = new BufferedReader(inputStreamReader);
			StringBuilder stringBuilder=new StringBuilder();
			String item;
			while((item=br.readLine())!=null){
				item=toLower(item);
				stringBuilder.append(item);
				stringBuilder.append("\r\n");
			}
			return stringBuilder.toString();
		}
		catch (Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 统计行数，效率低，丢弃
	 * @param path
	 * @return
	 */
	public int getLineCountFromFilePath(String path){
		try{
			int lineCount=0;
			FileInputStream fileInputStream=new FileInputStream(new File(path));
			InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
			BufferedReader br = new BufferedReader(inputStreamReader);
			String item;
			while((item=br.readLine())!=null){
				if(item.trim().length()>0){
					++lineCount;
				}
			}
			return lineCount;
		}
		catch (Exception ex){
			ex.printStackTrace();
			return 0;
		}
	}


	/**
	 * 使用 Map按key进行排序
	 * @param map
	 * @return
	 */
	public Map<String, Integer> sortMapByKey(Map<String, Integer> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Map<String, Integer> sortMap = new TreeMap<>(new MapKeyComparator());
		sortMap.putAll(map);
		return sortMap;
	}

	/**
	 * 使用 Map按value进行排序
	 * @param
	 * @return
	 */
	public Map<String, Integer> sortMapByValue(Map<String, Integer> oriMap) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		Map<String, Integer> sortedMap = new LinkedHashMap<>();
		List<Map.Entry<String, Integer>> entryList = new ArrayList<>(oriMap.entrySet());
		Collections.sort(entryList, new MapValueComparator());

		Iterator<Map.Entry<String, Integer>> iter = entryList.iterator();
		Map.Entry<String, Integer> tmpEntry;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}
	class MapValueComparator implements Comparator<Map.Entry<String, Integer>> {
		@Override
		public int compare(Map.Entry<String, Integer> me1, Map.Entry<String, Integer> me2) {
			return -1*me1.getValue().compareTo(me2.getValue());
		}
	}
	class MapKeyComparator implements Comparator<String>{
		@Override
		public int compare(String str1, String str2) {
			return str1.compareTo(str2);
		}
	}
}