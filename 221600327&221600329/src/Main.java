import bean.Word;
import unitl.BasicWordCount;
import unitl.IOUtils;
public class Main {
	

	public static void main(String[] args) {
		String fileName = null;

		//读取文件名
		if(args.length <= 0) {
			fileName = "input.txt";
		}else {
			fileName = args[0];
		}
		
		StringBuffer stringBuffer = new StringBuffer();
		BasicWordCount basicWordCount = new BasicWordCount();
		
		//结果字符串
		stringBuffer.append("characters: "+basicWordCount.characterCount(fileName)+"\r\n");
		stringBuffer.append("words: "+basicWordCount.wordCount(fileName)+"\r\n");
		stringBuffer.append("lines: "+basicWordCount.lineCount(fileName)+"\r\n");
		Word[] topTenWord = basicWordCount.topTenWord(fileName);
		for(int i = topTenWord.length - 1; i >= 0; i--) {
			stringBuffer.append("<"+topTenWord[i].getKey()+">: "+topTenWord[i].getCountNum()+"\r\n");
		}
		
		//写入到文件
		IOUtils.writeFile(stringBuffer.toString());
	}	
}

