import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Lib {

    private static class MyComparator implements Comparator<Map.Entry<String, Integer>> {
        public int compare(Map.Entry<String,Integer>m1,Map.Entry<String,Integer>m2){
            int result = m2.getValue()-m1.getValue();
            if(result!=0)return result;
            else return m1.getKey().compareTo(m2.getKey());
        }
    }

    public static void characterCount(String readPath,ResultString rString){
        FileReader  reader = null;
        int character = 0;

        try{
            reader = new FileReader(readPath);
            int ch=-1;
            while((ch=reader.read())!=-1) {
                if ((ch >= 0 && ch <= 12) || (ch >= 14 && ch <= 127)) character++;
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try{
                    reader.close();
                }catch (IOException e){
                    throw new RuntimeException("FileReader close failure");
                }
            }
        }
        System.out.println("characters:"+character);
        rString.outString +="characters:"+character+"\r\n";
    }

    public static void lineCount(File firstFile,ResultString rString) throws Exception{
        int line = 0;


        BufferedReader in =null;


        in = new BufferedReader(new InputStreamReader(new FileInputStream(firstFile),"utf-8"));
        // out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(secondeFile),"gbk"));

        String content = "";
        while((content = in.readLine())!=null){
            for(int i=0;i<content.length();i++){
                char ch=content.charAt(i);
                if((ch>=0&&ch<=32)||ch==127);
                else {
                    line++;
                    break;
                }
            }
        }
        if(null!=in){
            in.close();
        }
       /* if(null!=out){
            out.close();
        }*/
        System.out.println("line:"+line);
        rString.outString += "line:"+line+"\r\n";
    }

    public static void wordMatch(File firstFile,Map<String,Integer>map,ResultString rString)throws  Exception{
        BufferedReader in =null;
        in = new BufferedReader(new InputStreamReader(new FileInputStream(firstFile), "gbk"));

        int wordNum=0;
        String reg = "^[a-zA-Z]{4}.*";
        String result[] = null;
        String content = "";

        Pattern p = Pattern.compile(reg);

        boolean flag=true;

        while((content = in.readLine())!=null){
            result=content.split("[^0-9a-zA-Z]");
            for(String str : result){
                Matcher m = p.matcher(str);
                if(m.find()){
                    String strMatch=m.group().toLowerCase();
                    wordNum++;
                    boolean foundOrNew=false;
                    int num=0;
                    if(flag){
                        map.put(strMatch,1);
                        flag =false;
                    }else {
                        for (Iterator<Map.Entry<String,Integer>> it = map.entrySet().iterator();it.hasNext();) {
                            Map.Entry<String,Integer> item = it.next();
                            if (strMatch.compareTo(item.getKey()) == 0) {
                                num = item.getValue();
                                num++;
                                it.remove();
                                foundOrNew=true;
                                break;
                            }
                        }
                        if(foundOrNew){ map.put(strMatch,num);}
                        else map.put(strMatch,1);
                    }
                }
            }
        }
        rString.outString += "words:"+wordNum+"\r\n";
        System.out.println("words:"+wordNum);
    }

    public static void wordSort(Map<String,Integer>map,ResultString rString){
        int num=0;
        List<Map.Entry<String,Integer>> list = new ArrayList<>();
        list.addAll(map.entrySet());
        Lib.MyComparator myc=new MyComparator();
        Collections.sort(list,myc);
        for(Iterator<Map.Entry<String,Integer>>it =list.iterator();it.hasNext();){
            Map.Entry<String,Integer> item = it.next();
            System.out.println("<"+item.getKey()+">:"+item.getValue());
            rString.outString += "<"+item.getKey()+">:"+item.getValue()+"\r\n";
            if(num==9)break;
            else num++;
        }
    }

    public static  void write(ResultString resultString){
        try{
            File fileWrite = new File("result.txt");
            fileWrite.createNewFile();
            FileWriter writer = new FileWriter(fileWrite);
            BufferedWriter out =new BufferedWriter(writer);
            out.write(resultString.outString);
            out.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
