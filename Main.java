import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

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
            System.out.println(config.getProperty("num_players"));
        } catch (FileNotFoundException ex){
            System.out.println("Could not find 'config.properties'");
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}