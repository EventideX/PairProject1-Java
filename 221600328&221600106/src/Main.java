import java.io.*;
import java.util.*;

public class Main {
	 public static void main(String[] args) throws IOException
	 {
	         File filein = null;
	         if(args.length>0)
	         {
	        	 filein = new File(args[0]); 	
	         }
	         else
	         {
	        	 filein = new File("input.txt");   	 
	         }
	         
	         
	         
	         Count count = new Count(filein);
	         int characterNum = count.CountCharacter();
	         int lineNum = count.CountLine();
	         int wordNum = count.CountWord();
	         
	         String result="result.txt";
	         
	         
	         try { 
	        	 FileOutputStream fileout = new FileOutputStream(result);
                String resultcontent ="characters:"+ characterNum + "\r\n" 
	        	 + "words:" +wordNum + "\r\n" 
                	+"lines:"+ lineNum+"\r\n";
                List<HashMap.Entry<String, Integer>> m = count.gethotWords();

                int i = 0;
                String j = new String();
                if(m != null){
                    if(m.size()!=0){
                        for(;((i<10)&&(i<m.size()));i++){
                            j= "<"+ m.get(i).getKey() + ">:" + m.get(i).getValue();
                            resultcontent += j + "\r\n";
                        }
                    }
                }
                System.out.println(resultcontent);
                fileout.write(resultcontent.getBytes());
              
                fileout.flush();
                fileout.close();
	         	} 
	         catch (FileNotFoundException e) 
	         {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	         }catch (IOException e) 
	         {
	        	 // TODO Auto-generated catch block
	        	 e.printStackTrace();
	         }

	         
	         
	         
	 }	         
}

