
import java.util.*;

public class Main {

	
	private Main()
	{
		String fName = "";
		Scanner scanner = new Scanner(System.in);
		fName = scanner.next();
		//fName = "test.txt";
		lib l = new lib(fName);
		l.getWord("output.txt");
		scanner.close();
	}

	public static void main(String[] args) {
		new Main(); 
	}

}
