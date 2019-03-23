
public class Main {
    private Main(String fName)
    {
        lib l = new lib(fName);
        l.getWord("output.txt");
    }

    public static void main(String[] args) {
        if(args.length != 0)
            new Main( args[0] ); 
    }

}
