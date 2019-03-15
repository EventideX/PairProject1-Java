import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Lib {
    private BufferedReader bufferedReader;
    TreeMap<String,Integer> treeMap=new TreeMap<>();

    Lib(String url)
            throws FileNotFoundException,IOException
    {
        File file=new File(url);
        bufferedReader=new BufferedReader(new FileReader(file));
        bufferedReader.mark((int)file.length()+1);
    }

    public int CharCount() throws IOException {   
        int count=0;
        bufferedReader.reset();
        int temp;
        while ((temp=bufferedReader.read())!=-1){
            count++;
            if(temp==13)
                bufferedReader.read();
        }
        return count;
    }

    public int WordCount() throws IOException {
        int count=0;
        String line;
        bufferedReader.reset();
        StringBuffer stringBuffer=new StringBuffer();
        while ((line=bufferedReader.readLine())!=null)
            stringBuffer.append(line+"\n");
        String content=stringBuffer.toString();
        String [] words=content.split("([^a-zA-Z0-9]|\n)+");
        Pattern pattern=Pattern.compile("^[a-zA-Z]{4,}[0-9]*[a-zA-Z]*");

        Integer value=0;
        String temp;
        for(int i=0;i<words.length;i++){
            if(pattern.matcher(words[i]).matches()){
                count++;
                temp=words[i].toLowerCase();
                if(treeMap.containsKey(temp)) {
                    value = treeMap.get(temp) + 1;
                    treeMap.put(temp, value);
                }
                else{
                    treeMap.put(temp,1);
                }
            }

        }
        return count;
    }

    public int LineCount() throws IOException {
        int count=0;
        bufferedReader.reset();
        String line;
        while ((line=bufferedReader.readLine())!=null){
            if(!line.isEmpty())
                count++;
        }
        return count;
    }

    public void OutPutWords() throws Exception{

        File file=new File("result.txt");
        FileWriter writer=new FileWriter(file);
        writer.write("characters: "+this.CharCount()+"\n");
        writer.write("words: "+this.WordCount()+"\n");
        writer.write("lines: "+this.LineCount()+"\n");
        List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(treeMap.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        int i=0;
        for(Map.Entry<String,Integer> mapping:list) {
            writer.write("<"mapping.getKey()+">" + ":" + mapping.getValue()+"\n");
            i++;
            if(i==10)
                break;
        }
        writer.close();
        CloseFile();
    }

    public void CloseFile() throws IOException {
        bufferedReader.close();
    }
}
