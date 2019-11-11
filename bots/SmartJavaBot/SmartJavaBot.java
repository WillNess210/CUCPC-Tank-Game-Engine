import java.util.Scanner;

public class SmartJavaBot{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        // INIT
        int myId = in.nextInt();
        int numSites = in.nextInt();
        int siteX = 0, siteY = 0;
        for(int i = 0; i < numSites; i++){
            siteX = in.nextInt();
            siteY = in.nextInt();
        }
        // FOR EACH TURN
        while(true){
            int myCoins = in.nextInt();
            int opCoins = in.nextInt();
            int numUnits = in.nextInt();
            int storage = -1;
            for(int i = 0; i < numUnits; i++){
                int unitID = in.nextInt();
                int unitOwner = in.nextInt();
                int unitType = in.nextInt();
                int unitX = in.nextInt();
                int unitY = in.nextInt();
                int unitParam1 = in.nextInt();
                if(unitOwner == myId && unitParam1 == 100){
                    storage = 100;
                }
            }
            System.out.println(storage == 100 ? ("MOVE 0 " + (myId == 0 ? 50 : 950) + " " + 250) : ("MOVE 0 " + siteX + " " + siteY));
        }
    }
}