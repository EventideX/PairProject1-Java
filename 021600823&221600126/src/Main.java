import java.io.IOException;
import java.util.Scanner;


public class Main 
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		String input = sc.next();
		try {
				wordCount wc = new wordCount();
				wc.counter.addWeight("title", 1); // 设置权重
				wc.counter.addWeight("abstract", 1); // 设置权重
				wc.countWords(input, 10); // 选择输出单词数
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		sc.close();
	}
}
