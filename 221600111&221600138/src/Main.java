import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import  java.lang.String;

class Main{

	public static void main(String[] args) {
		String resultString="";
		
		//parameter check
		if(args.length!=1) {
			if(args.length==0) {
				System.err.println("No files inputed!"); 
				System.exit(0);
			}
			System.err.println("Only one parameter could be inpted!");
			System.exit(0);
		}
		if(!args[0].equals("input.txt")) {
			System.err.println("Incorrect file name!");
			System.exit(0);
		}
		
		//read file "input.txt"
		File inFile=new File(args[0]);
		/*TextCalculatorTest t=new TextCalculatorTest();
		t.testCharCount();
		t.testRowCount();
		t.testWordCount();
		t.testTopWordCount();*/
		
		//count
		resultString+="characters: "+TextCalculator.charCount(inFile)+'\n';
		resultString+="words: "+TextCalculator.wordCount(inFile)+'\n';
		resultString+="lines: "+TextCalculator.rowCount(inFile)+'\n';
		resultString+=TextCalculator.topWordCount(inFile);
			
			
		//output file result.txt
		File oFile=new File("result.txt");
		if(!oFile.exists()) {
			try {
				oFile.createNewFile();
			} catch (IOException e) {
				System.err.println("Create file result.txt failed!");
				System.exit(0);
			}
		}
		try {
			FileOutputStream oStream=new FileOutputStream(oFile);
			oStream.write(resultString.getBytes());
			oStream.close();
		} catch (IOException e) {
			System.err.println("Write result.txt failed!");
			System.exit(0);
		}
	}
	
	

	

}