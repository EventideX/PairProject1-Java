import java.io.*;
import java.util.*;

public class lib {
	private int fWordCount = 0;//字词数
	private int fRowCount = 0;//行数
	private int fByteCount = 0;//字节数
	private int maxWordNum = 10;//词频输出数
	private HashMap<String,Integer> map = new HashMap<String,Integer>();//存放字词处
	
	public lib(String fName)
	{
		setWord(fName);
	}
	public int getFWordCount()
	{
		return fWordCount;
	}
	public int getFRowCount()
	{
		return fRowCount;
	}
	public int getfByteCount()
	{
		return fByteCount;
	}
	public int getMaxWordNum()
	{
		return maxWordNum;
	}
	public int getMaxWordNum(int num)
	{
		return maxWordNum = num;
	}
	public boolean isLower(char c)
	{
		return (c>='a' && c<='z');
	}
	public boolean isDigit(char c)
	{
		return c<='0' && c<='9';
	}
	public LinkedHashMap<String,Integer> sortMap(int num)
	{
		List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {   
		    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {      
		        return o2.getValue() != o1.getValue() ? (o2.getValue() - o1.getValue()) : (o1.getKey()).toString().compareTo(o2.getKey());
		        //return (o1.getKey()).toString().compareTo(o2.getKey());
		    }
		});
		LinkedHashMap<String,Integer> tmp = new LinkedHashMap<String,Integer>();
		for (int i = 0; i < list.size() && i< num; i++) {
		    String id = list.get(i).toString();
		    Integer value = list.get(i).getValue();
		    tmp.put(id, value);
		    //System.out.println(id + (value));
		}
		return tmp;
	}
	public void getWord(String fName)
	{
		LinkedHashMap<String,Integer> list  = sortMap(maxWordNum);
		
		try {
			FileOutputStream fos = new FileOutputStream(fName);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter buff = new BufferedWriter(osw);
			
			String content = "characters: " + fByteCount + "\r\n";
			content += "words: "+ getFWordCount() + "\r\n";
			content += "lines: "+ fRowCount + "\r\n";
			Iterator<String> iterator = list.keySet().iterator();
			while (iterator.hasNext()) {
			    String key = iterator.next();
			    content += "<" + key.replace("=", ">: ") + "\r\n";
			    //System.out.println(key.replace("=", ">: "));
			}
			//System.out.print(content);
			buff.write(content);
			buff.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public void setMap(String fContent)
	{
		String [] ch = fContent.split(" |	|!|=|-|,|\\*|\\+|\\(|\\)|\\^|/|\\.|\\||@|#|~|`|%");
		for(int i = 0; i< ch.length ;i++)
		{
			if(ch[i].length()>=4)
			{
				ch[i] = ch[i].toLowerCase();
				if (isLower(ch[i].charAt(0)) && isLower(ch[i].charAt(1)) && isLower(ch[i].charAt(2)) && isLower(ch[i].charAt(3)) )
				{
					//System.out.print(ch[i]);
					//新增纪录或者记录数+1
					fWordCount++;
					if( map.containsKey(ch[i]) )
						map.put(ch[i], map.get(ch[i])+1);
					else 
						map.put(ch[i], 1);
				}
			}
		}
	}
	public void setWord(String fName)
	{
		try {
			String fContent = "";
			FileInputStream fis = new FileInputStream(fName);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			fWordCount = fByteCount = fRowCount = 0;
			while ((fContent = br.readLine()) != null) {
				fRowCount ++;
				fByteCount += fContent.length()+1;//换行
				setMap(fContent);
			}
			fis.close();
		} catch (FileNotFoundException e) {
			System.out.print("文件不存在");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
