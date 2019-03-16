import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lib {
	public static int charNum(String fileName) {
		File srcFile=new File(fileName);
		int count=0;
		try {
			FileReader fileReader=new FileReader(srcFile);
			int len=-1;
			char[] buffer=new char[1024];
			while((len=fileReader.read(buffer))!=-1) {
				for(int i=0;i<len;i++) {
					if((int)buffer[i]>=0&&(int)buffer[i]<=127) {
						if((i<len-1)&&(int)buffer[i]=='\r'&&(int)buffer[i+1]=='\n') {
							count--;
						}
							
						count++;
					}
				}
			}
			fileReader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
	public static int lineNum(String fileName) {
		int count=0;
		File srcFile=new File(fileName);
		try {
			FileReader fileReader=new FileReader(srcFile);
			BufferedReader bufferedReader=new BufferedReader(fileReader);
			String lineText=null;
			while((lineText=bufferedReader.readLine())!=null) {
				if(!(lineText==null||lineText.trim().length()==0)) {
					count++;
				}
			}
			fileReader.close();
			bufferedReader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  count;
	}
	public static boolean isLetter(String letter) {
		String regex="[a-zA-Z]{4}[a-zA-Z0-9]*";
		return letter.matches(regex);
	}
	public static int letterNum(String fileName) {
		int count=0;
        Map<String,Integer> map = new HashMap();
        String regex="[^a-zA-Z0-9]+";
		File srcFile=new File(fileName);
		try {
			FileReader fileReader=new FileReader(srcFile);
			BufferedReader bufferedReader=new BufferedReader(fileReader);
			String lineText=null;
			while((lineText=bufferedReader.readLine())!=null) {
				 String[] strs=lineText.split(regex);
				 for(int i=0;i<strs.length;i++) {
					 if(isLetter(strs[i])) {
						 count++;
					 }
				 }
			}
			fileReader.close();
			bufferedReader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static void getWordAndCount(String fileName,List<String> list,List<Integer> list1) {
		Map<String,Integer> map = new HashMap();
        String regex="[^a-zA-Z0-9]+";
		File srcFile=new File(fileName);
		try {
			FileReader fileReader=new FileReader(srcFile);
			BufferedReader bufferedReader=new BufferedReader(fileReader);
			String lineText=null;
			while((lineText=bufferedReader.readLine())!=null) {
				 String[] strs=lineText.split(regex);
				 for(int i=0;i<strs.length;i++) {
					 if(isLetter(strs[i])) {
						 strs[i]=strs[i].toLowerCase();
						 if(map.containsKey(strs[i])) {
							 map.put(strs[i], map.get(strs[i])+1);
						 }
						 else {
							 map.put(strs[i], 1);
						 }
					 }
				 }
			}
			String[] words=new String[map.size()];
			Integer[] counts=new Integer[map.size()];
		
			map.keySet().toArray(words);
			map.values().toArray(counts);
			
			for(int i=0;i<map.size();i++) {
				if(i<=9) {
					for(int j=i;j<map.size();j++) {
						if(counts[i]<counts[j]) {
							Integer temp=null;
							temp=counts[i];
							counts[i]=counts[j];
							counts[j]=temp;
							String temp1=null;
							temp1=words[i];
							words[i]=words[j];
							words[j]=temp1;
						}
						else if(counts[i]==counts[j]) {
							if(words[i].compareTo(words[j])<0) {
								Integer temp=null;
								temp=counts[i];
								counts[i]=counts[j];
								counts[j]=temp;
								String temp1=null;
								temp1=words[i];
								words[i]=words[j];
								words[j]=temp1;
							}
						}
					}
				}
			}
			for(int i=0;i<words.length;i++) {
				if(i>9) {
					break;
				}
				else {
					list.add(words[i]);
				}
			}
			for(int i=0;i<counts.length;i++) {
				if(i>9) {
					break;
				}
				else {
					list1.add(counts[i]);
				}
			}
			fileReader.close();
			bufferedReader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
