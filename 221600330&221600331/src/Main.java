import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.Map.Entry;

public class Main {

	public static void main(String[] args) {
		int countChar;
		int countLinnes;
		Map<String, String> wordsMap;
		countChar = count_Characters("input.txt");
		wordsMap = count_Words("input.txt");
		countLinnes = count_Lines("input.txt");
		
		writeToFile(countChar,wordsMap.get("count_words"),countLinnes);
	}
	
	
	
	private static int count_Lines(String string) {
		// TODO �Զ����ɵķ������
		return 0;
	}



	private static Map<String, String> count_Words(String string) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("count_words", "0");
		
		
		return map;
	}
	private static void writeToFile(int countChar, String countWords, int countLinnes) {
		try {
			File output_file=new File("result.txt");
			OutputStreamWriter writer;
			writer = new OutputStreamWriter(new FileOutputStream(output_file));
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write("characters: "+countChar+"\r\n");
			bufferedWriter.write("words: "+countWords+"\r\n");
			bufferedWriter.write("lines: "+countLinnes+"\r\n");
            bufferedWriter.flush();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static int count_Characters(String filePath){
        try {
                File input_file=new File(filePath);
                File output_file=new File("result.txt");
                if(input_file.isFile() && input_file.exists()){ //�ж��ļ��Ƿ����
                    InputStreamReader read = new InputStreamReader(new FileInputStream(input_file));
                    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(output_file));
//                    BufferedReader bufferedReader = new BufferedReader(read);
//                    BufferedWriter bufferedWriter = new BufferedWriter(writer);
                    String text = null;
                    int sum = 0;
                    int temp;
                    int last_char=-1;
                    int n=0;
                    while((temp = read.read() )!= -1){
                    	sum++;
                    	n++;
                    	if(last_char=='\r'||temp=='\n')
                    	{
                    		System.out.println("����:"+(n-1) );
                    		n=0;
                    		sum--;
                    	}
                    	System.out.print((char) temp);
                    	last_char=temp;
//                    	text = text.toLowerCase();
//                    	text.replaceAll("\r\n", "\n");
//                        System.out.println(text+"  ����:"+ (text.length()) );
//                        sum+=text.length();
//                        bufferedWriter.write(text+"\r\n");
//                        bufferedWriter.flush();
                    }
                    System.out.println("sum="+sum);
                    
                    read.close();
                    writer.close();
                    return sum;
                 }
                 else{
                	 System.out.println("�Ҳ���ָ�����ļ�");
                	 return -1;
                 }
        } catch (Exception e) {
            System.out.println("��ȡ�ļ����ݳ���");
            e.printStackTrace();
            return -1;
        }
     
    }


}
