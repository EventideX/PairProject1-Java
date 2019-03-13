import java.io.*;
import java.security.KeyStore.Entry;
import java.util.*;

public class wordCount {

	private HashMap<String,Integer> map = new HashMap<String,Integer>();
	
	private wordCount()
	{
		String fName = "";
		Scanner scanner = new Scanner(System.in);
		fName = scanner.next();
		//fName = "input.txt";
		lib l = new lib(fName);
		l.getWord("output.txt");
	}

	public static void main(String[] args) {
		new wordCount(); 
	}

}
