

import java.io.*;

class FileUtil {
	private String filePath;
	private int lineCnt=0;
	public int getLineCnt() {
		return lineCnt;
	}
	private StringBuffer sb = new StringBuffer();
	public FileUtil(String path) {
		filePath=path;
	}
	
	@SuppressWarnings("resource")
	public String FiletoText() throws IOException {
		InputStream is = new FileInputStream(filePath);
        int char_type; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while ((char_type = reader.read()) != -1) { // 如果 line 为空说明读完了
            sb.append((char)char_type); // 将读到的内容添加到 buffer 中
        }
        return sb.toString();
	}
	
	@SuppressWarnings("resource")
	public void resultToFile(String result) {
		try {
            File file = new File("result.txt");
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.println(result);// 往文件里写入字符串
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	@SuppressWarnings("resource")
	public void lineCount() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String readline;
		while((readline=br.readLine())!=null) {
			readline=readline.trim();//去除空白行
			if(readline.length()!=0) lineCnt++;
		}
	}
}
