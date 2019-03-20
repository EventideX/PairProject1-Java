import java.io.File;
import java.io.IOException;

public class Main{
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		if(args.length<=0)
			System.exit(-1);
		String inFileName = "input.txt",outFileName = "result.txt";
		inFileName=args[0];
		File input=new File(inFileName);
		File output=new File(outFileName);
		WordCount.outCount(input,output);
	}
}
