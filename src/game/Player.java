package game;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import game.constants.UnitType;
import java.util.Random;
import game.constants.ScoreInfo;

public class Player{
    private int coins, id, load_counter;
    private Map<Integer, Unit> units;
    private boolean errored;
    private DeploymentArea deploymentArea;
    
    public Player(int id, DeploymentArea deploymentArea, Random r){
    	this.errored = false;
        this.id = id;
        this.deploymentArea = deploymentArea;
        this.coins = ScoreInfo.STARTING_BALANCE;
        this.units = new HashMap<Integer, Unit>();
        this.load_counter = 0;
        this.starterUnits(r);
    }
    public void starterUnits(Random r){
        this.spawnUnit(0, this.deploymentArea.getCenter());
    }
    public List<Unit> getUnits(){
        return new ArrayList<Unit>(this.units.values());
    }
    public Unit getUnit(int id){
        return units.get(id);
    }
    public boolean spawnUnit(int type, Point spawnPoint){
        this.addUnit(type, spawnPoint.getX(), spawnPoint.getY());
        return true;
    }
    public boolean addUnit(int type, int x, int y){
        units.put(this.load_counter, new Unit(this.load_counter, type, x, y));
        this.load_counter++;
        return true;
    }
    public boolean removeUnit(Unit un) {
    	return removeUnit(un.getId());
    }
    public boolean removeUnit(int id){
        units.remove(id);
        return true;
    }
    public int numUnits(){
        return this.units.size();
    }
    public boolean canAfford(int cost){
        return this.coins >= cost;
    }
    public boolean canAffordUnit(int unitType){
        if(unitType == UnitType.ROVER){
            return this.canAfford(ScoreInfo.ROVER_COST);
        }else if(unitType == UnitType.TANK){
            return this.canAfford(ScoreInfo.TANK_COST);
        }
        return false;
    }

    public int getScore(){
        return this.getCoins();
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
    
    public void addCoins(int coins){
        this.setCoins(this.getCoins() + coins);
    }

    public void depositCoins(Unit un){
        this.addCoins(un.getStorage());
        un.clearStorage();
    }
    
    public void subtractCoins(int coins) {
    	this.setCoins(this.getCoins() - coins);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setErrored(boolean val) {
    	this.errored = val;
    	if(val) {
    		this.setCoins(-1);
    	}
    }
    public boolean errored() {
    	return this.errored;
    }
    
}