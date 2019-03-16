package unitl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOUtils {

	/**
	 * 读入指定文件名的文件数据
	 * @param fileName 文件名
	 * @return BufferedReader
	 */
	public static BufferedReader readFile(String fileName) {
		InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
       
        //字节流方式读入文件
        try {
        	inputStreamReader = new InputStreamReader(new FileInputStream(fileName));
        	if(inputStreamReader != null) {
        		bufferedReader= new BufferedReader(inputStreamReader);
        	}
        }catch(Exception e) {
        	System.out.println("找不到"+fileName+"文件");
        	e.printStackTrace();
        }
		return bufferedReader;
	}
	/**
	 * 将制定字符串输出到result.txt
	 * @param fileContent 字符串
	 */
	public static void writeFile(String fileContent) {
		 File outputFile = new File("result.txt");
		 BufferedWriter writer = null;
         try {
			writer = new BufferedWriter(new FileWriter(outputFile));
			writer.write(fileContent);
		} catch (IOException e) {
			System.out.println("打开result.txt文件发生错误");
			e.printStackTrace();
		}finally {
			try {
				if(writer != null) {
				writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
