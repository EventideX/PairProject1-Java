//import java.util.Scanner;

public class Main {
	
	
	public static void main(String[] args)
	{
		//long startTime = System.currentTimeMillis();
		/*
		 *IO������
		Scanner input = new Scanner(System.in);
		System.out.print("�������ļ���:");
		String path = input.next();
		input.close();
		*/
		String path=null;
		try
		{
			path = args[0];  //����args����
		}
		catch(Exception e)
		{
			System.out.println("û�д����ļ�·������\n"+e);
		}
		
	
		lib cout = new lib();
		cout.CountChar(path);	//��ȡ��ǰʱ��	

		
		cout.CountWord(path);

		cout.CountLine(path);

		cout.print();								//inputs.txt

		cout.CountFre();
		//long endTime = System.currentTimeMillis();	
		

		
		//System.out.println("��������ʱ�䣺"+(endTime-startTime)+"ms");
	}
}
