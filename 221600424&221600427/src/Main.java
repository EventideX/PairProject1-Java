import java.io.File;
import java.io.IOException;
public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String inFileName = "input.txt",outFileName = "result.txt";
		int n=10,w=1,m=1;
		for(int i=0;i<args.length;i+=2) {
			switch (args[i]) {
			case "-i":
				inFileName=args[i+1];
				break;
			case "-o":
				outFileName=args[i+1];
				break;
			case "-w":
				w=Integer.valueOf(args[i+1]);
				break;
			case "-n":
				n=Integer.valueOf(args[i+1]);
				break;
			case "-m":
				m=Integer.valueOf(args[i+1]);
				break;
			default:
				break;
			}
		}	
		
		File input=new File(inFileName);
		File output=new File(outFileName);
		WordCount.outCount(input,output,w,n,m);
	}
	
}
