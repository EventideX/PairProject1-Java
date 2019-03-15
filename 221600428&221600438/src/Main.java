//import java.util.Scanner;

public class Main {
	
	
	public static void main(String[] args)
	{
		//long startTime = System.currentTimeMillis();
		/*
		 *IO流输入
		Scanner input = new Scanner(System.in);
		System.out.print("请输入文件名:");
		String path = input.next();
		input.close();
		*/
		String path=null;
		try
		{
			path = args[0];  //接收args参数
		}
		catch(Exception e)
		{
			System.out.println("没有传递文件路径参数\n"+e);
		}
		
	
		lib cout = new lib();
		cout.CountChar(path);	//获取当前时间	

		
		cout.CountWord(path);

		cout.CountLine(path);

		cout.print();								//inputs.txt

		cout.CountFre();
		//long endTime = System.currentTimeMillis();	
		

		
		//System.out.println("程序运行时间："+(endTime-startTime)+"ms");
	}
}
