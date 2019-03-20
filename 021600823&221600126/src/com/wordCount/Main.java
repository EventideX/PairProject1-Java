package com.wordCount;

import java.io.IOException;

public class Main 
{
	public static class NoOprandException extends Exception{
		private static final long serialVersionUID = 1L;

		public NoOprandException(String s)
		   {
		      super(s);
		   }
		 
	}
	
	public static void main(String[] args) throws NoOprandException
	{
		if(args.length == 0)
		{
			throw new NoOprandException("û�в���");
		}
		String input = args[0];
		try {
				wordCount wc = new wordCount("result.txt");
				wc.countWords(input, 10, false); // ѡ�����������
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
