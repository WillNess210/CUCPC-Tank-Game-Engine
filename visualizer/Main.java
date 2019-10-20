package visualizer;

import java.io.File;

public class Main{
    public static void main(String[] args){
        if(args.length != 1){
            System.out.println("This program needs 1 argument - the filepath of the replay file.");
            return;
        }
        String fileLoc = args[0];
        File replayFile = new File(fileLoc);
        if(!replayFile.exists()){
            System.out.println("Could not find file");
        }
    }
}