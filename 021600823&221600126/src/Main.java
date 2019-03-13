import java.io.FileNotFoundException;
import java.io.IOException;

public class Main 
{
	public static void main(String[] args)
	{
			try {
				new wordCount("input.txt", 10);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
