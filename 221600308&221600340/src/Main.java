import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class WordCount {
	public static void main(String[] args) throws Exception {
		try {
 			if(args.length!=1) {
				System.out.println("请输入正确的参数!");
				return;
			}
			File file = new File(args[0]);
			String pathname = file.getPath();
			Reader myReader = new FileReader(pathname);
			Reader myBufferedReader = new BufferedReader(myReader);
			// 对文本处理
			CharArrayWriter tempStream = new CharArrayWriter();
			int i = -1;
			do {
				if (i != -1)
					tempStream.write(i);
				i = myBufferedReader.read();
				if (i >= 65 && i <= 90) {
					i += 32;
				}
			} while (i != -1);
			myBufferedReader.close();
			Writer myWriter = new FileWriter(pathname);
			tempStream.writeTo(myWriter);
			tempStream.flush();
			tempStream.close();
			myWriter.close();
			//
	        Long filelength = file.length();  
	        byte[] fileTemp = new byte[filelength.intValue()];  
	        FileInputStream in = new FileInputStream(file);  
            in.read(fileTemp);  
            in.close(); 
            String content=new String(fileTemp);
			String fileText[]=content.split("\r\n");
			//
			int characterscount = 0;
			int wordline = 0;
			int noline = 0;
			int wordcount = 0;
			//存储过滤后单词的列表
			List<String> lists = new ArrayList<String>(); 
			String readLine = null;
			//开始遍历内容
			for(int n=0;n<fileText.length;n++) {
				readLine=fileText[n];
				// 过滤无效行
				if (!(readLine.equals("") || readLine.equals("	") || readLine.equals(" "))) {
					wordline++;
				}
				// 过滤出只含有字母的单词
				String[] wordsArr1 = readLine.split("[^a-zA-Z0-9]"); 
				characterscount += readLine.length();
				for (String word : wordsArr1) {
					if (word.length() != 0) { // 只包含
						while (word.length()!=0 && !(word.charAt(0) >= 97 && word.charAt(0) <= 122)) {
							word = word.substring(1, word.length());
						}
						if (word.length() >= 4)
							wordcount++;
						lists.add(word);
					}
				}
			}
			characterscount+=getCount(content,"\r\n");
			// 
			Map<String, Integer> wordsCount = new TreeMap<String, Integer>();
			// 单词的词频统计
			for (String li : lists) {
				if (wordsCount.get(li) != null) {
					wordsCount.put(li, wordsCount.get(li) + 1);
				} else {
					wordsCount.put(li, 1);
				}
			}
			//
			writeFile(wordsCount, wordline, wordcount, characterscount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeFile(Map<String, Integer> oldmap, int wordline, int wordcount, int characterscount) {
		ArrayList<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(oldmap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o2.getValue() - o1.getValue(); // 降序
			}
		});
		try {
			File file = new File("result.txt");
			BufferedWriter bi = new BufferedWriter(new FileWriter(file));
			bi.write("characters: " + (characterscount) + "\r\n");
			bi.write("words: " + wordcount + "\r\n");
			bi.write("lines: " + wordline + "\r\n");
			int k = 0;
			for (int i = 0; i < list.size(); i++) {
				if (k >= 10)
					break;
				if (list.get(i).getKey().length() > 3) {
					bi.write("<" + list.get(i).getKey() + ">" + ": " + list.get(i).getValue() + "\r\n");
					k++;
				}

			}
			bi.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static int getCount(String str,String text) {
		int fromIndex = 0;
        int count = 0;
        while(true){
            int index = str.indexOf(text, fromIndex);
            if(-1 != index){
                fromIndex = index + 1;
                count++;
            }else{
                break;
            }
        }
        return count;
	}
}