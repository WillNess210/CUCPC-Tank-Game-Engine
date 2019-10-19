package engine;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class Engine{
    // ENGINE VARIABLES
    private long mapSeed;
    private String[] botFilepaths;
    private Properties config;
    private BotProcess[] bots;

    // CONSTRUCTORS
    public Engine(Properties config, String[] botFilepaths){
        this.config = config;
        this.botFilepaths = botFilepaths;
        this.mapSeed = (new Random()).nextLong();
        this.bots = new BotProcess[this.getNumPlayers()];
    }
    public Engine(Properties config, String[] botFilepaths, int mapSeed){
        this.config = config;
        this.botFilepaths = botFilepaths;
        this.mapSeed = mapSeed;
        this.bots = new BotProcess[this.getNumPlayers()];
    }

    // STARTUP STUFF
    public void init(){
        for(int i = 0; i < this.getNumPlayers(); i++){
            this.bots[i] = new BotProcess(this.botFilepaths[i]);
            this.bots[i].compile();
            this.bots[i].start();
            System.out.println("Compiled bot " + this.bots[i].getBotName());
        }
    }

    // CONFIG FUNCS
    public String getGameName(){
        return config.getProperty("game_name");
    }
    public int getNumPlayers(){
        return Integer.parseInt(config.getProperty("num_players"));
    }
    public long getTimelimitMs(){
        return Integer.parseInt(config.getProperty("max_turn_length_ms"));
    }
}