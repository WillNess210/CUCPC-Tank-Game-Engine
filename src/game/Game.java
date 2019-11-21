package game;

import java.util.Random;
import game.constants.UnitType;
import game.constants.ActionType;
import game.constants.ScoreInfo;

// THIS CLASS WILL HANDLE ALL GAME INFO (taking user input, parsing it to make sure it's correct, updating gamestate, then returning new strings to send)
public class Game{
    public static final int MAX_TURNS = 200;
    private Player[] players;
    private Unit[] sites;
    private Random rand;
    public long mapSeed;
    public LogHandler log;
    private boolean isOver;

    public Game(long mapSeed){
        this.isOver = false;
        this.mapSeed = mapSeed;
        rand = new Random(mapSeed);
        System.out.println("init w/ seed: " + this.mapSeed);
        players = new Player[2];
        for(int i = 0; i < players.length; i++){
            players[i] = new Player(i, rand);
        }
        
        sites = new Unit[2 + rand.nextInt(2) * 2];
        for(int i = 0; i < sites.length; i += 2){
            int nX = 300 + rand.nextInt(401);
            int nY = 100 + rand.nextInt(300);
            sites[i] = new Unit(i, UnitType.SITE, nX, nY);
            sites[i + 1] = new Unit(i + 1, UnitType.SITE, 1000 - nX, 500 - nY);
        }

        this.log = new LogHandler();
    }

    // this method is called when the engine receives info from bots
    public void updateBots(String[] resultsFromBot){
    	int numBots = resultsFromBot.length;
    	// UPDATE COUNTDOWN ON TANKS
    	for(int i = 0; i < numBots; i++) {
    		for(Unit un : players[i].getUnits()) {
    			un.newTurnUpdate();
    		}
    	}
    	// COMMANDS
    	String[][] commandsUnparsed = new String[numBots][];
    	Command[][] commands = new Command[numBots][];
    	for(int i = 0; i < numBots; i++) {
    		// CHECKING FOR TIMEOUT
    		if(resultsFromBot[i].equals("timeout")) {
    			this.players[i].setErrored(true);
    			return;
    		}
    		// CONTINUING ON
    		commandsUnparsed[i] = resultsFromBot[i].split(",");
    		commands[i] = new Command[commandsUnparsed[i].length];
    		for(int j = 0; j < commands[i].length; j++) {
    			commands[i][j] = new Command(commandsUnparsed[i][j]);
    			if(commands[i][j].badCommand()) {
    				this.players[i].setErrored(true);
    				System.out.println("Received unparseable command from player " + i + ": \"" + commandsUnparsed[i][j] + "\".Killing bot");
    				return;
    			}
    		}
    	}
        // FIRE ACTIONS FIRST
    	for(int j = 0; j < numBots; j++) {
	        for(int i = 0; i < commands[j].length; i++){
	            if(commands[j][i].getType() == ActionType.FIRE){
	                // look up unit
	                Unit target = players[j].getUnit(commands[j][i].getParam1());
	                Point hitPoint = new Point(commands[j][i].getParam2(), commands[j][i].getParam3());
	                if(target != null && target.hasUsedAction() == false && target.getType() == UnitType.TANK && target.getParam1() == 0 && target.dist(hitPoint) <= UnitType.TANK_FIRE_RANGE){
	                    // CHECK EVERY OPPONENT, IF THEY ARE WITHIN TANK_EXPLOSION_RADIUS UNITS, MARK THEM TO DIE
	                	int opId = j == 0 ? 1 : 0;
	                	for(Unit opp : players[opId].getUnits()) {
	                		if(!ScoreInfo.WITHIN_DEPOSIT_BOUDARY(opp, opId) && opp.dist(hitPoint) <= UnitType.TANK_EXPLOSION_RADIUS) {
	                			opp.hasBeenHit = true;
	                		}
	                	}
	                    target.setParam1(UnitType.TANK_COOLDOWN);
	                    target.action();
	                    this.log.addExplosion(hitPoint);
	                }
	            }
	        }
    	}
        // after fires have taken, remove everything that was hit
    	for(int j = 0; j < numBots; j++) {
    		for(Unit un : players[j].getUnits()) {
    			if(un.hasBeenHit) {
    				players[j].removeUnit(un);
    			}
    		}
    	}
        // MOVE ACTIONS NEXT
    	for(int j = 0; j < numBots; j++) {
	        for(int i = 0; i < commands[j].length; i++){
	            Command com = commands[j][i];
	            if(com.getType() == ActionType.MOVE){
	                // look up unit
	                Unit target = players[j].getUnit(com.getParam1());
	                if(target != null && target.hasUsedAction() == false) {
	                	target.action();
		                Point movePoint = new Point(com.getParam2(), com.getParam3());
		                target.moveTowards(movePoint);
		                if(target.isOutOfBounds()) {
		                	players[j].removeUnit(target);
		                }
	                }
	            }
	        }
    	}
        // IF IN SITE, EARN $$
    	for(int j = 0; j < numBots; j++) {
	        for(Unit un : players[j].getUnits()){
	            for(Unit site : sites){
	                if(un.isWithinDist(site, UnitType.SITE_RADIUS)){
	                    un.addSiteIncome();
	                }
	                if(un.withinDepositBoundary(j)){
	                    players[j].depositCoins(un);
	                }
	            }
	        }
    	}
    	// SPAWN ACTION NEXT
    	for(int j = 0; j < numBots; j++) {
    		for(int i = 0; i < commands[j].length; i++) {
    			Command com = commands[j][i];
    			if(com.getType() == ActionType.SPAWN) {
        			System.out.println(com.toString());
        			System.out.println(players[j].getCoins());
        			System.out.println(players[j].canAffordUnit(com.getParam1()));
    			}
    			if(com.getType() == ActionType.SPAWN && (com.getParam1() == 0 || com.getParam1() == 1) && players[j].canAffordUnit(com.getParam1())) {
    				int coins = -1;
    				if(com.getParam1() == UnitType.ROVER){
    		            coins = ScoreInfo.ROVER_COST;
    		        }else if(com.getParam1() == UnitType.TANK){
    		            coins = ScoreInfo.TANK_COST;
    		        }
    				players[j].subtractCoins(coins);
    				players[j].spawnUnit(com.getParam1(), this.rand);
    				System.out.println("Spawned a " + com.getParam1() + " for player " + j);
    			}
    		}
    	}
    	
    	// ADD IDLE INCOME
    	for(int j = 0; j < numBots; j++) {
    		players[j].addCoins(ScoreInfo.IDLE_EARNINGS);
    	}
    }
    // checks for errored bots, returns id of errored bot, or 2 if all have errored out. -1 if no errors
    public int checkForErroredBots() {
    	boolean allErrored = true;
    	int errorId = -1;
    	for(int i = 0; i < this.players.length; i++) {
    		allErrored &= this.players[i].errored();
    		errorId = this.players[i].errored() ? i : errorId;
    	}
    	return allErrored ? 2 : errorId;
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
        return this.isOver;
    }

    // return ID of winning bot, -1 when no winner has been established yet
    public int getWinner(){
        if(this.isGameOver() == false){
            return -1;
        }
        int s0 = this.players[0].getScore(), s1 = this.players[1].getScore();
        if(s0 == s1){
            return 2;
        }
        return s0 > s1 ? 0 : 1;
    }
    public String getScores(){
        return players[0].getScore() + " " + players[1].getScore();
    }
    public int getTotalNumUnits(){
        return players[0].numUnits() + players[1].numUnits();
    }

    public String[] getSitesStrings(){
        String[] toRet = new String[this.sites.length];
        for(int i = 0; i < toRet.length; i++){
            toRet[i] = this.sites[i].getX() + " " + this.sites[i].getY();
        }
        return toRet;
    }

    public void finish(){
        this.isOver = true;
    }

    public Player[] getPlayers(){
        return this.players;
    }

    public void updateLogHandler(){
        this.log.addTurn(this);
    }
    
    public void errorOutPlayers() {
    	for(int i = 0; i < this.players.length; i++) {
    		this.errorOutPlayer(i);
    	}
    }
    
    public void errorOutPlayer(int id) {
    	this.players[id].setErrored(true);
    }
    
    public void generateLog(){
    	this.log.generateLogFile(this);
        this.log.generateLogFile(this, "latest_replay");
    }
}