package com.wordCount;

import java.io.IOException;
import java.util.Scanner;

public class Main 
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		String input = sc.next();
		try {
				wordCount wc = new wordCount("result.txt");
				wc.countWords(input, 10, false); // 选择输出单词数
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		sc.close();
	}
}
