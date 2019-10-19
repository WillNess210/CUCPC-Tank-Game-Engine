import java.util.Scanner;

public class SampleJavaBot{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while(true){
            String fromEngine = in.nextLine();
            System.out.println(fromEngine);
        }
    }
}