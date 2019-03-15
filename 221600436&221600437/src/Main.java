public class Main {
    static Result result=new Result();
    public static void main(String args[]){
        long start,end;
        start = System.currentTimeMillis();

        String inputFileName="input77.txt",outputFileName="result.txt";//指定输入输出文件名
        int n=10,CodeMode=0;

        inputFileName=args[0];
        Process.read_file(inputFileName,outputFileName,result, CodeMode,n);

        end = System.currentTimeMillis();
        System.out.println("start time:" + start+ "; end time:" + end+ "; Run Time:" + (end - start) + "(ms)");
    }

    public int getWord_count() {
        return result.getWord_count();
    }
    public int getChar_count() {
        return result.getChar_count();
    }
    public int getLine_count() {
        return result.getLine_count();
    }
}
