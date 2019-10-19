import java.util.Random;

public class Engine{
    // ENGINE VARIABLES
    private long mapSeed;
    private String[] botFilepaths;

    // CONSTRUCTORS
    public Engine(String[] botFilepaths){
        this.botFilepaths = botFilepaths;
        this.mapSeed = (new Random()).nextLong();
    }
    public Engine(String[] botFilepaths, int mapSeed){
        this.botFilepaths = botFilepaths;
        this.mapSeed = mapSeed;
    }
}