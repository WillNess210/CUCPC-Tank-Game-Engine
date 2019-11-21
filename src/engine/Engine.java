package engine;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import game.Game;

public class Engine{
    // ENGINE VARIABLES
    private long mapSeed;
    private String[] botFilepaths;
    private Properties config, engine_config;
    private BotProcess[] bots;
    private Game game;

    // CONSTRUCTORS
    public Engine(Properties engine_config, Properties config, String[] botFilepaths){
    	this.engine_config = engine_config;
        this.config = config;
        this.botFilepaths = botFilepaths;
        Random r = new Random();
        this.mapSeed = r.nextLong();
        this.bots = new BotProcess[this.getNumPlayers()];
        this.game = new Game(this.mapSeed);
    }
    public Engine(Properties engine_config, Properties config, String[] botFilepaths, long mapSeed){
    	this.engine_config = engine_config;
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
            //this.bots[i].compile();
            //System.out.println("Compiled bot " + this.bots[i].getBotName());
            this.bots[i].start(this);
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
        game.updateLogHandler();
        for(int i = 0; i < this.game.MAX_TURNS && this.game.isGameOver() == false; i++){
            System.out.println("TURN " + i);
            String[] toSend = new String[this.bots.length];
            for(int j = 0; j < this.bots.length; j++){
                String result = bots[j].sendAndReceive(game.getStringToSendToBot(j), this.getTimelimitMs());
                toSend[j] = result;
                System.out.println("bot " + j + ":" + result);
            }
            game.updateBots(toSend);
            game.updateLogHandler();
        }
        game.finish();
        game.generateLog();
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
    public String getPythonCommand() {
    	return engine_config.getProperty("python_command").trim();
    }
}