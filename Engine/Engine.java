import java.util.Properties;
import java.util.Random;

public class Engine{
    // ENGINE VARIABLES
    private long mapSeed;
    private String[] botFilepaths;
    private Properties config;

    // CONSTRUCTORS
    public Engine(Properties config, String[] botFilepaths){
        this.config = config;
        this.botFilepaths = botFilepaths;
        this.mapSeed = (new Random()).nextLong();
    }
    public Engine(Properties config, String[] botFilepaths, int mapSeed){
        this.config = config;
        this.botFilepaths = botFilepaths;
        this.mapSeed = mapSeed;
    }

    // STARTUP STUFF


    // CONFIG FUNCS
    public String getGameName(){
        return config.getProperty("game_name");
    }
    public int getNumPlayers(){
        return Integer.parseInt(config.getProperty("num_players"));
    }
}