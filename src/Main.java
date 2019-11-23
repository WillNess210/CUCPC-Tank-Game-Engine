import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import engine.*;

public class Main{
    public static void main(String[] args) throws URISyntaxException{
        //File configFile = new File("config.properties");
    	//InputStream inp = Main.class.getResourceAsStream("/config.properties");
        try{
            // READ IN FILE
            Properties config = new Properties(), engine_config = new Properties();
            config.load(Main.class.getResourceAsStream("/config.properties"));
            FileReader configFile = new FileReader(new File("engine_config.properties"));
            engine_config.load(configFile);
            ///configFileReader.close();
            // READ COMMAND LINE ARGUMENTS
            int mapSize = -1;
            int deploymentType = -1;
            String[] bots = new String[2];
            for (String arg : args) {
        		if (arg.equals("-S")) {
        			mapSize = 0;
        		}else if (arg.equals("-M")) {
        			mapSize = 1;
        		}else if (arg.equals("-L")) {
        			mapSize = 2;
        		}
        		break;
            }
            bots[0] = args[args.length-2];
            bots[1] = args[args.length-1];

        	Engine engine = new Engine(engine_config, config, mapSize, deploymentType, bots);
        	engine.init();
            engine.run();
 

            
        } catch (FileNotFoundException ex){
            System.out.println("Could not find 'config.properties'");
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}