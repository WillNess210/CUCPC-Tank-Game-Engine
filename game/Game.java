package game;

import java.util.Random;
import constants.UnitType;
import constants.ActionType;

// THIS CLASS WILL HANDLE ALL GAME INFO (taking user input, parsing it to make sure it's correct, updating gamestate, then returning new strings to send)
public class Game{
    public static final int MAX_TURNS = 50;
    private Player[] players;
    private Unit[] sites;
    private Random rand;

    public Game(long mapSeed){
        players = new Player[2];
        for(int i = 0; i < players.length; i++){
            players[i] = new Player(i);
        }
        rand = new Random(mapSeed);
        sites = new Unit[4 + rand.nextInt(4) * 2];
        for(int i = 0; i < sites.length; i += 2){
            int nX = 300 + rand.nextInt(401);
            int nY = rand.nextInt(500);
            sites[i] = new Unit(i, UnitType.SITE, nX, nY);
            sites[i + 1] = new Unit(i + 1, UnitType.SITE, 1000 - nX, 500 - nY);
        }
    }

    // this method is called when the engine receives info from bots
    public void updateBot(int botid, String resultFromBot){
        String[] commandsUnparsed = resultFromBot.split(",");
        Command[] commands = new Command[commandsUnparsed.length];
        for(int i = 0; i < commands.length; i++){
            commands[i] = new Command(commandsUnparsed[i]);
        }
        // FIRE ACTIONS FIRST
        for(int i = 0; i < commands.length; i++){
            if(commands[i].getType() == ActionType.FIRE){
                // look up unit
                Unit target = players[botid].getUnit(commands[i].getParam1());
                Point hitPoint = new Point(commands[i].getParam2(), commands[i].getParam3());
                if(target != null && target.getType() == UnitType.TANK && target.getParam1() == 0 && target.dist(hitPoint) <= 75){
                    // TODO destruction logic
                    target.setParam1(5);
                }
            }
        }
        // MOVE ACTIONS NEXT
        for(int i = 0; i < commands.length; i++){
            if(commands[i].getType() == ActionType.MOVE){
                // look up unit
                Unit target = players[botid].getUnit(commands[i].getParam1());
                Point movePoint = new Point(commands[i].getParam2(), commands[i].getParam3());
            }
        }
    }

    // this method is called to get the initialization string
    public String getGameInit(int botid){
        String toReturn = botid + "\n";
        toReturn += this.sites.length;
        for(int i = 0; i < this.sites.length; i++){
            toReturn += "\n" + this.sites[i].getX() + " " + this.sites[i].getY();
        }
        return toReturn;
    }
    // this method is called to get the game to send game state to the bot
    public String getStringToSendToBot(int botid){
        String toReturn = "";
        // LINE 1 - coins
        if(botid == 0){
            toReturn += players[0].getCoins() + " " + players[1].getCoins() + "\n";
        }else{
            toReturn += players[1].getCoins() + " " + players[0].getCoins() + "\n";
        }
        // LINE 2 - num units
        int numTotalUnits = this.getTotalNumUnits();
        toReturn += numTotalUnits + "";
        // for numunits long
        for(Unit unit : players[0].getUnits()){
            toReturn += "\n" + unit.getId() + " 0 " + unit.getType() + " " + unit.getX() + " " + unit.getY() + " " + unit.getParam1();
        }
        for(Unit unit : players[1].getUnits()){
            toReturn += "\n" + unit.getId() + " 1 " + unit.getType() + " " + unit.getX() + " " + unit.getY() + " " + unit.getParam1();
        }
        return toReturn;
    }

    // when this method returns true, the game will stop playing. Suggestion: turn limit or when a player has won
    public boolean isGameOver(){
        return false;
    }

    // return ID of winning bot, -1 when no winner has been established yet
    public int getWinner(){
        return 0;
    }

    public int getTotalNumUnits(){
        return players[0].numUnits() + players[1].numUnits();
    }

}