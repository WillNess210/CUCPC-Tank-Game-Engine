import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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
            // START UP ENGINE
            Engine engine = new Engine(engine_config, config, args);
            engine.init();
            engine.run();
        } catch (FileNotFoundException ex){
            System.out.println("Could not find 'config.properties'");
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}