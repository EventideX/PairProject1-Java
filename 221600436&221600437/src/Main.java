public class Main {

    public static void main(String args[]){
        long start,end;
        start = System.currentTimeMillis();

        String inputFileName="input77.txt",outputFileName="result.txt";//指定输入输出文件名
        int n=10,CodeMode=0;
        Result tempresult=new Result();

        inputFileName=args[0];
        Process.read_file(inputFileName,outputFileName,tempresult, CodeMode,n);

        end = System.currentTimeMillis();
        System.out.println("start time:" + start+ "; end time:" + end+ "; Run Time:" + (end - start) + "(ms)");
    }
}
