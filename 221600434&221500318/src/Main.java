import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		try {
			if (args.length == 0) {				
				writeResult("input1.txt","result.txt");
			}
			else if (args.length == 1){
				String infilename = args[0];
				String outfilename = "result.txt";
				initTxt(outfilename);	
				writeResult(infilename,outfilename);
			}
			else {
				System.out.println("������1������!");
				System.out.println("infilename[Ҫͳ�Ƶ��ĵ�] ");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void initTxt(String string) throws IOException {
        String path = new File(string).getAbsolutePath();
        FileWriter fw = new FileWriter(path, false);
        fw.write("");
        fw.flush();
        fw.close();
	}
	
	public static void writeMostWords(String infilename,String outfilename) throws IOException {
		String outpath = new File(outfilename).getAbsolutePath();
        FileWriter fw = new FileWriter(outpath, true);
        TreeMap<String, Integer> tm = wordMap(infilename);
		if(tm != null && tm.size()>=1)
		{		  
			List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(tm.entrySet());
			// ͨ���Ƚ�����ʵ������
			Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
				@Override
				public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
					//treemapĬ�ϰ��ռ����ֵ����������еģ�����listҲ���Ź���ģ���ֵ��ͬ������²����ٸ�����������		
					// ����ֵ�������� 
					return o2.getValue().compareTo(o1.getValue());
				}
			});   
			int i = 1;
			String key = null;
			Integer value = null;
			for (Map.Entry<String, Integer> mapping : list) {
				key = mapping.getKey();
				value = mapping.getValue();
				System.out.print("<" + key + ">: " + value + '\n');
				fw.write("<" + key + ">: " + value + '\n');
				//ֻ���ǰ10��
				if (i == 10) {
					break;
				}
				i++;
			}
		}
		fw.close();
	}
	
    public static void writeResult(String infilename,String outfilename) throws IOException {
        File file = new File(infilename);
        if (file.exists()) {	
        	initTxt(outfilename);
        	String outpath = new File(outfilename).getAbsolutePath();
        	FileWriter fw = new FileWriter(outpath, true);
        	int charactersNum = charactersNum(infilename);
        	int wordsNum = wordsNum(infilename);
        	int linesNum = linesNum(infilename);
        	System.out.print("characters: " + charactersNum + '\n');
        	System.out.print("words: " + wordsNum + '\n');
        	System.out.print("lines: " + linesNum + '\n');
        	fw.write("characters: " + charactersNum + '\n');
        	fw.write("words: " + wordsNum + '\n');
        	fw.write("lines: " + linesNum + '\n');
        	fw.flush();
        	writeMostWords(infilename,outfilename);
        	if (fw != null) {
        		fw.close();
        	}
        }
		else {
			System.out.println("�ļ�������");
		}
    }
	
	public static int charactersNum(String filename) throws IOException  {
		int num = 0;		
		BufferedReader br = new BufferedReader(new FileReader(filename));
		int value = -1;
		while ((value = br.read()) != -1) {
			if (value > 0 && value < 128 && value != 13) {
				num ++;
			}			
		}
		br.close();		
		return num;
	}
	
	public static int wordsNum(String filename) throws IOException  {
		int num = 0;			
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String separator = "[^A-Za-z0-9]";//�ָ���
		String regex = "^[A-Za-z]{4,}[0-9]*$"; // �����ж�ÿ���������Ƿ������Ч����
		Pattern p = Pattern.compile(regex);
		Matcher m = null;
		String line = null;
		String[] array = null;
		while ((line  = br.readLine()) != null) {
            line = line.replaceAll("[(\\u4e00-\\u9fa5)]", "");// �ÿո��滻����
            line = line.replaceAll(separator, " "); // �ÿո��滻�ָ���
            array = line.split("\\s+"); // ���ո�ָ�                     
            for (int i = 0;i<array.length;i++) { 
            	m = p.matcher(array[i]);
            	if (m.matches()) {
            		num++;
            	}
            }
		}						
		br.close();		
		return num;
	}	
	
	public static TreeMap<String, Integer> wordMap(String filename) throws IOException {	
        // ��һ�ַ���  
        // ʹ��entrySet()��������һ����Map.entry������ɵ�Set,  
        // ��Map.entry���������ÿ��Ԫ�ص�"��"��"ֵ".�����Ϳ�����iterator��  
//		Iterator<Entry<String, Integer>> it = tm.entrySet().iterator();  
//		while (it.hasNext()) {  
//			Map.Entry<String, Integer> entry =(Map.Entry<String, Integer>) it.next();  
//			String key = entry.getKey();  
//			Integer value=entry.getValue();  
//
//			System.out.println("<" + key + ">:" + value); 
//		}
		
		TreeMap<String, Integer> tm = new TreeMap<String, Integer>();				
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String separator = "[^A-Za-z0-9]";//�ָ���
		String regex = "^[A-Za-z]{4,}[0-9]*$"; // �����ж�ÿ���������Ƿ������Ч����
		Pattern p = Pattern.compile(regex);
		String str = null;
		Matcher m = null;
		String line = null;
		String[] array = null;
		while ((line  = br.readLine()) != null) {
            line = line.replaceAll("[(\\u4e00-\\u9fa5)]", "");// ���˺���
            line = line.replaceAll(separator, " "); // �ÿո��滻�ָ���
            array = line.split("\\s+"); // ���ո�ָ�                     
            for (int i = 0;i<array.length;i++) { 
            	m = p.matcher(array[i]);
            	if (m.matches()) {
            		str = array[i].toLowerCase();                
                    if (!tm.containsKey(str)) {
                        tm.put(str, 1);
                    } else {
                        int count = tm.get(str) + 1;
                        tm.put(str, count);
                    }
            	}
            }
		}						
		br.close();		
		return tm;
	}
	
 	public static int linesNum(String filename) throws IOException  {
		int num = 0;			
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = null;
		while ((line  = br.readLine()) != null) {
			if (line.trim().length() != 0) {
				num ++;					
			}
		}
		br.close();		
		return num;
	}	
}
