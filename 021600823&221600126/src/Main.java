import java.io.IOException;

public class Main 
{
	public static void main(String[] args)
	{
		wordCount wc = new wordCount();
		wc.counter.addWeight("title", 1); // 设置权重
		try {
			wc.countWords("test.txt", 10);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
