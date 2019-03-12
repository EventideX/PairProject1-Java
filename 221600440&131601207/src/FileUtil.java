

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
        int char_type; // ��������ÿ�ж�ȡ������
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while ((char_type = reader.read()) != -1) { // ��� line Ϊ��˵��������
            sb.append((char)char_type); // ��������������ӵ� buffer ��
        }
        return sb.toString();
	}
	/*
	@SuppressWarnings("resource")
	public void resultToFile(String result) {
		try {
            File file = new File("result.txt");
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.println(result);// ���ļ���д���ַ���
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	*/
	@SuppressWarnings("resource")
	public void lineCount() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String readline;
		while((readline=br.readLine())!=null) {
			readline=readline.trim();//ȥ���հ���
			if(readline.length()!=0) lineCnt++;
		}
	}
}
