import java.io.*;
import java.util.*;

public class Count 
{
	//private static List<String> list = new ArrayList<String>();
	private int lineNum=0;
	private static String text = new String();
	private static Map<String,Integer> map = new HashMap<String,Integer>();
	private static List<HashMap.Entry<String, Integer>> hotWords = null;

    public static List<HashMap.Entry<String, Integer>> gethotWords() {
        return hotWords;
    }
	public Count(File filein)
	{
		try {
			String encoding="UTF-8";
			if(filein.exists()) //判断文件是否存在
			{
				InputStreamReader read =new InputStreamReader(
						new FileInputStream(filein),encoding); //考虑编码格式
				InputStreamReader read1 =new InputStreamReader(
						new FileInputStream(filein),encoding); //考虑编码格式
				BufferedReader bf = new BufferedReader(read);
				BufferedReader bf1 = new BufferedReader(read1);
				int value = 0;
				StringBuffer textTemp = new StringBuffer();
				
				while ((value=bf.read())!=-1) //按字符读取
				{
					textTemp.append((char)value);
				}
				text = textTemp.toString();
				String lineTxt = null;
				
                while ((lineTxt = bf1.readLine()) != null) //按列读取
                {
                	
          
                	String regexSpace = "\\s*";
            		if(!(lineTxt.matches(regexSpace)))
            		{
            			lineNum++;
                	
                	}
                }
                bf.close();
                bf1.close();
                read.close();
                read1.close();
			}
			 else
			 {
	            System.out.println("找不到指定的文件");
	         }
		}
		catch (Exception e)
        {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
	}
	
	/*public static String StringToA(String content){ // ASCII码转换
	    String result = "";
	    int max = content.length();
	    for (int i=0; i<max; i++){
	      char c = content.charAt(i);
	      int b = (int)c;
	      result = result + " "+b;
	    }
	    return result;
	  }*/
	public int CountCharacter() 
	{
		String textNew = text.replaceAll("\r\n", "\n");

		return textNew.length();
	}
	public int CountLine() {
		
		return lineNum;
	}
	public int  CountWord() {
		int wordNum=0;
		String regex="[^A-Za-z0-9]";
		String textLowerCase= text.toLowerCase();
		String textcontents = textLowerCase.replaceAll(regex, " ");
		
		String[] textarrays = textcontents.split("\\s+");
		for(int i=0; i<textarrays.length;i++)
		{
			if(textarrays[i].length()>=4)
				if(Character.isLetter(textarrays[i].charAt(0)) && 
						Character.isLetter(textarrays[i].charAt(0)) && 
							Character.isLetter(textarrays[i].charAt(0)) && 
								Character.isLetter(textarrays[i].charAt(0)))
			{
				wordNum++;
				if(!map.containsKey(textarrays[i]))
					map.put(textarrays[i],1);
				else
				{
					int num=map.get(textarrays[i]);
					num++;
					map.put(textarrays[i], num);
				}
			}
		}
		
		hotWords=Sort(map);
		return wordNum;
		
	}
	
	public static List<HashMap.Entry<String, Integer>> Sort(Map m){
	Map<String, Integer> map = new HashMap<String, Integer>();
	
	// 通过ArrayList构造函数把map.entrySet()转换成list
	List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(m.entrySet());
	// 通过比较器实现比较排序
	Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
	    @Override
	    public int compare(Map.Entry<String,Integer> mapping1, Map.Entry<String, Integer> mapping2) {
	        if(mapping1.getValue()==mapping2.getValue())
                return mapping1.getKey().compareTo(mapping2.getKey());//字典排序
            return mapping2.getValue()-mapping1.getValue();//从大到小
	    	
	    }
	});
	
	return list;
	
}
}
	
