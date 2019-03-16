import java.rmi.activation.ActivationGroupDesc;

public class Main {

    public static void main(String []args){
        try {
            if(args.length<=0)
                System.out.println("Not Input FileName!");
            else{
                Lib t=new Lib(args[0]);
                t.OutPutWords();
            }
        }catch (Exception t){
            System.out.println(t.getMessage());
        }
    }
}
