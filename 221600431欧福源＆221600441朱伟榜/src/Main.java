import java.io.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;



import java.util.*;
import java.util.regex.Matcher;

class EntryComparator implements Comparator<Entry<String, Integer>> {
	public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
		if(o2.getValue() == o1.getValue()) {
			return o1.getKey().compareTo(o2.getKey());
		}
		else return (o2.getValue() - o1.getValue());
	}
}

public class Main {
	
	private static ArrayList<String> wordStrings =new ArrayList<String>();
	private static int count = 0;
	private static int lines = 0;
	private static int words = 0;
	

	
	//获取字符数
	private static void getCharacter(String filename)
	{
		int ch, ef = 0;
        try {
        	BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(filename)));
        	BufferedReader in = new BufferedReader(new InputStreamReader(bis, "utf-8"), 20* 1024* 1024  );
        	while (in.ready()) {
        		ch = in.read();
        		count++;
            	if((char)ch == '\n') count--;
            	if((char)ch != ' ' && (char)ch != '\t' && (char)ch != '\n' && (char)ch != '\r') ef++;
            	if((char)ch == '\n' && ef > 0) {
            		lines++;
            		ef = 0;
            	}
        	}
        	if(ef > 0) {
            	lines++;
            	ef = 0;
            }
        	in.close();
        	} catch (IOException ex) {
        		ex.printStackTrace();
        	}
    }
	
	//获取单词数
	private static void getWords(String filename)throws IOException {
		FileReader fr = new FileReader(filename);
		String s = "([A-Za-z]{4,})([A-Za-z0-9]*)";
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		while((line = br.readLine()) != null) {
			line = line.replaceAll("[^a-zA-Z0-9]([0-9]{1,})([a-zA-Z0-9]*)", "");
			Pattern  pattern=Pattern.compile(s);  
	        Matcher  ma=pattern.matcher(line);  
	        while(ma.find()){ 
	        	words++;
	            //System.out.println(ma.group());  
	        }
		}
		br.close();
        fr.close();
	}

	//输出前10的单词及个数
	private static void getMostWord(String filename)throws IOException {
		FileReader fr = new FileReader(filename);
		String s = "([A-Za-z]{4,})([A-Za-z0-9]*)";
		ArrayList<String> text = new ArrayList<String>();
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		while((line = br.readLine()) != null) {
			line = line.toLowerCase();
			line = line.replaceAll("[^a-z0-9]([0-9]{1,})([a-z0-9]*)", "");
			Pattern  pattern=Pattern.compile(s);  
	        Matcher  ma=pattern.matcher(line);  
	        while(ma.find()){ 
	        	text.add(ma.group());
	            //System.out.println(ma.group());  
	        }
		}
		br.close();
        fr.close();
        Map<String, Integer> map = new HashMap<String, Integer>();
        for(String st : text) {
        	if(map.containsKey(st)) {
        		map.put(st, map.get(st)+1);
        	}else {
        		map.put(st, 1);
        	}
        }
        
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String,Integer>>();
        for(Entry<String, Integer> entry : map.entrySet()) {
        	list.add(entry);
        }
        Collections.sort(list,new EntryComparator());
        int i = 0;
        String ssString;
        for(Entry<String, Integer> obj : list) {
     	   if(i>9) break;
     	   ssString="<"+obj.getKey()+">: " + obj.getValue()+"\r\n";
     	   wordStrings.add(ssString);
     	   ++i;
     	  // System.out.print(ssString);
        }
	}


	private static void writers(String c,String w,String l,ArrayList<String>ws,String path) {
		try {
			File file1 =new File(path);
     		Writer out =new FileWriter(file1);
		    out.write(c);
	    	out.write(w);
	    	out.write(l);
	    	for(int i=0;i<ws.size();i++)out.write(ws.get(i));
	    	out.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void main(String[] args) throws IOException {
		//String path = "input2.txt";
		String path = args[0];
		//long start = System.currentTimeMillis();//要测试的程序或方法
		getCharacter(path);
		getWords(path);
		getMostWord(path);
		String c,w,l;
		c = "characters: "+count+"\r\n";
		w = "words: "+words+"\r\n";
		l = "lines: "+lines+"\r\n";
		writers(c, w, l, wordStrings, "result.txt");
		//long end = System.currentTimeMillis();
		//System.out.println("程序运行时间："+(end-start)+"ms");
			
	}

}