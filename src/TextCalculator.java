import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TextCalculator{
	private static int wordCount=0;
	private static int charCount=0;
	private static int rowCount=0;
	private static StringBuffer tempWord=new StringBuffer();
	private static boolean rFlag=false;
	private static boolean rowFlag=false;
	private static charNode wordTreeRoot=null;

	public static int charCount(File inFile) {
		charCount=0;
		rFlag=false;
		rowFlag=false;
		FileInputStream inStream;
		try {
			inStream=new FileInputStream(inFile);
			try {
				int inChar;
				boolean flagEOF=false;
				while(!flagEOF) {
					inChar=inStream.read();
					if(inChar==-1) {
						flagEOF=true;
					}
					calculateChar(inChar);/////////////////////////////
				}
				
				inStream.close();
			} catch (IOException e) {
				System.err.println("Read char failed!");
				System.exit(0);
			}
		} catch (FileNotFoundException e) {
			System.err.println("File do not exists!");
			System.exit(0);
		}
		return charCount;
	}
	
	public static int rowCount(File inFile) {
		rowCount=0;
		rFlag=false;
		rowFlag=false;
		FileInputStream inStream;
		try {
			inStream=new FileInputStream(inFile);
			try {
				int inChar;
				boolean flagEOF=false;
				while(!flagEOF) {
					inChar=inStream.read();
					if(inChar==-1) {
						flagEOF=true;
					}
					calculateRow(inChar);/////////////////////////////
				}
				
				inStream.close();
			} catch (IOException e) {
				System.err.println("Read char failed!");
				System.exit(0);
			}
		} catch (FileNotFoundException e) {
			System.err.println("File do not exists!");
			System.exit(0);
		}
		return rowCount;
	}
	
	public static int wordCount(File inFile) {
		wordCount=0;
		tempWord=new StringBuffer();
		wordTreeRoot=new charNode('_');
		FileInputStream inStream;
		try {
			inStream=new FileInputStream(inFile);
			try {
				int inChar;
				boolean flagEOF=false;
				while(!flagEOF) {
					inChar=inStream.read();
					if(inChar==-1) {
						flagEOF=true;
					}
					calculateWord(inChar);/////////////////////////////
				}
				
				inStream.close();
			} catch (IOException e) {
				System.err.println("Read char failed!");
				System.exit(0);
			}
		} catch (FileNotFoundException e) {
			System.err.println("File do not exists!");
			System.exit(0);
		}
		return wordCount;
	}
	public static String topWordCount(File inFile) {
		if(wordTreeRoot==null) {
			wordCount(inFile);
		}
		return topWordCount(wordTreeRoot);
	}
	public static String topWordCount(charNode wordTreeRoot) {
		wordTreeRoot.preVisit("");
		Word[] words = TopWordList.getList().getTopWords();

		String s="";
		for(int i=0;i<TopWordList.getList().getSize();i++) {
			s+=("<"+words[i].s+">: "+words[i].num+'\n');
		}
		if(s.length()>=1)
			s=s.substring(0, s.length()-1);//delete the last '\n'
		return s;
	}

	
	private static void calculateChar(int i) {		
		if(i!=-1) {
			char c=(char)i;
			charCount++;
			
			if(c=='\n'&&rFlag) {
				charCount--;
			}
			
			if(c=='\r') {
				rFlag=true;
			}
			else {
				rFlag=false;
			}

		}
		else {
		}
	}
	private static void calculateRow(int i) {		
		if(i!=-1) {
			char c=(char)i;
			charCount++;
			
			if(c>33&&c!=127) {
				rowFlag=true;
			}
			
			if(c=='\n'&&rFlag) {
				charCount--;
				if(rowFlag) {
					rowCount++;
					rowFlag=false;
				}
			}
			
			if(c=='\r') {
				rFlag=true;
			}
			else {
				rFlag=false;
			}

		}
		else {
			if(rowFlag==true) {  //flush rowCount
				rowCount++;
			}
		}
	}
	
	private static void calculateWord(int i) {
		if(i!=-1) {
			char c=(char)i;
			if(Character.isLetter(c)) {
				tempWord.append(Character.toLowerCase(c));
			}
			else if(Character.isDigit(c)&&tempWord.length()>=4){
				tempWord.append(c);
			}
			else {
				if(tempWord.length()>=4) {
					wordCount++;
					wordTreeRoot.generateTree(tempWord.toString()); //TopWordCalculate
				}
				
				tempWord=new StringBuffer();
			}
		}
		else {
			if(tempWord.length()>=4) {    //flush tempword(wordCount&TopWordCalculate)
				wordCount++;
				wordTreeRoot.generateTree(tempWord.toString());
			}
		}
	}
	
	private static class charNode{//use this node_class to build a tree of chars for wordCount
		private char value;
		private charNode[] nextChar;// sequence:  0123456789abcdefghijklmnopqrstuvwxyz
		private int count=0;
		
		public void nextCharInit()
		{
			nextChar=new charNode[36];
			for(int i=0;i<26;i++) {
				nextChar[i+10]=new charNode('a'+i);
			}
			for(int i=0;i<10;i++) {
				nextChar[i]=new charNode('0'+i);
			}
		}
		
		public void generateTree(String word) {
			if(nextChar==null) {
				nextCharInit();
			}
			if(word.length()==1) {
				nextChar[getNextCharIndex(word.charAt(0))].plus();
			}
			else {
				nextChar[getNextCharIndex(word.charAt(0))].generateTree(word.substring(1));
			}
		}
		
		private int getNextCharIndex(char c) {
			if(Character.isLetter(c)) {
				return (int)Character.toLowerCase(c)-'a'+10;
			}
			else {
				return (int)c-'0';
			}
		}
		
		public charNode(char value) {
			this.value=value;
		}
		public charNode(int value) {
			this((char)value);
		}
		
		public void plus() {
			count++;
		}
		
		public void preVisit(String s) {
			s+=this.value;
			if(s.length()>4) {
				if(TopWordList.getList().getMinOccur()<this.count) {
					TopWordList.getList().insert(new Word(s.substring(1),this.count));
				}
			}
				
			if(nextChar!=null) {
				for(int i=0;i<36;i++) {
					nextChar[i].preVisit(s);
				}
			}
		}
	}
	
	private static class TopWordList{// as the name, this is a singleton of Top hot words
		private static Word[] words=new Word[10];
		private static TopWordList topWordList=new TopWordList();
		private int size=0;
		
		/*public void clear() {
			topWordList=new TopWordList();
		}*/
		
		public int getSize() {
			return size;
		}
		
		public Word[] getTopWords() {
			return words;
		}
		
		public int getMinOccur() {
			if(size<10)
				return 0;
			return words[size-1].num;
		}
		
		public void insert(Word word) {
			if(size<10) {
				words[size++]=word;
			}
			else {
				words[9]=word;
			}
			insertSort();
		}
		
		public void insertSort() {
			Word temp=words[size-1];
			
			int i=size-1;
			for(;i>0;i--) {
				if(words[i-1].num<temp.num)
					words[i]=words[i-1];
				else
					break;
			}
			words[i]=temp;
		}
		public static TopWordList getList() {
			return topWordList;
		}
		private TopWordList() {
			
		}
	}
	
	private static class Word{
		public String s;
		public int num;
		public Word(String s,int n) {
			this.s=s;
			this.num=n;
		}
	}
}






