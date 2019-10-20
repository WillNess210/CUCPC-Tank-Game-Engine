package engine;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import game.Game;

public class Engine{
    // ENGINE VARIABLES
    private long mapSeed;
    private String[] botFilepaths;
    private Properties config;
    private BotProcess[] bots;
    private Game game;

    // CONSTRUCTORS
    public Engine(Properties config, String[] botFilepaths){
        this.config = config;
        this.botFilepaths = botFilepaths;
        this.mapSeed = (new Random()).nextLong();
        this.bots = new BotProcess[this.getNumPlayers()];
        this.game = new Game(this.mapSeed);
    }
    public Engine(Properties config, String[] botFilepaths, int mapSeed){
        this.config = config;
        this.botFilepaths = botFilepaths;
        this.mapSeed = mapSeed;
        this.bots = new BotProcess[this.getNumPlayers()];
        this.game = new Game(this.mapSeed);
    }

    // STARTUP STUFF
    public void init(){
        for(int i = 0; i < this.getNumPlayers(); i++){
            this.bots[i] = new BotProcess(this.botFilepaths[i]);
            this.bots[i].compile();
            System.out.println("Compiled bot " + this.bots[i].getBotName());
            this.bots[i].start();
        }
        try{
            java.util.concurrent.TimeUnit.MILLISECONDS.sleep(this.getInitTime());
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
        
    }
    // RUN A GAME
    public void run(){
        // send init
        for(int j = 0; j < this.bots.length; j++){
            bots[j].send(game.getGameInit(j));
        }
        // run turns
        for(int i = 0; i < this.game.MAX_TURNS && this.game.isGameOver() == false; i++){
            System.out.println("TURN " + i);
            for(int j = 0; j < this.bots.length; j++){
                String result = bots[j].sendAndReceive(game.getStringToSendToBot(j), this.getTimelimitMs());
                game.updateBot(j, result);
                System.out.println("bot " + j + ":" + result);
            }
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
        return Long.parseLong(config.getProperty("max_turn_length_ms"));
    }
    public int getInitTime(){
        return Integer.parseInt(config.getProperty("init_game_setup_time"));
    }
}