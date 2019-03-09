import java.io.*;
import java.util.*;

public class Main{

	// 字符数
	public int charNum = 0;

	// 单词数：至少四个英文字母开头，不区分大小写
	public int wordNum = 0;

	// 输入文件名
	public String inputFileName = "";

	public static void main(String[] args) {
		
		inputFileName = args[0];
		System.out.println(inputFileName);
	}
}