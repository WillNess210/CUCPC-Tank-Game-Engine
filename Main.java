import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import engine.*;

public class Main{
    public static void main(String[] args){
        File configFile = new File("config.properties");
        try{
            // READ IN FILE
            FileReader configFileReader = new FileReader(configFile);
            Properties config = new Properties();
            config.load(configFileReader);
            configFileReader.close();
            // START UP ENGINE
            Engine engine = new Engine(config, args);
            engine.init();
            engine.run();
        } catch (FileNotFoundException ex){
            System.out.println("Could not find 'config.properties'");
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}