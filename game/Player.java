package game;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import constants.UnitType;
import java.util.Random;
import constants.ScoreInfo;

public class Player{
    private int coins, id, load_counter;
    private Map<Integer, Unit> units;
    public Player(int id, Random r){
        this.id = id;
        this.coins = 100;
        this.units = new HashMap<Integer, Unit>();
        this.load_counter = 0;
        this.starterUnits(r);
    }
    public void starterUnits(Random r){
        this.spawnUnit(0, r);
    }
    public List<Unit> getUnits(){
        return new ArrayList<Unit>(this.units.values());
    }
    public Unit getUnit(int id){
        return units.get(id);
    }
    public boolean spawnUnit(int type, Random r){
        int nx = 25 + r.nextInt(51) + (this.id == 0 ? 0 : 900);
        int ny = 25 + r.nextInt(451);
        this.addUnit(type, nx, ny);
        return true;
    }
    public boolean addUnit(int type, int x, int y){
        units.put(this.load_counter, new Unit(this.load_counter, type, x, y));
        this.load_counter++;
        return true;
    }
    public boolean removeUnit(int id){
        units.remove(id);
        return true;
    }
    public int numUnits(){
        return this.units.size();
    }
    public boolean canAfford(int cost){
        return cost >= this.coins;
    }
    public boolean canAffordUnit(int unitType){
        if(unitType == UnitType.ROVER){
            return this.canAfford(25);
        }else if(unitType == UnitType.TANK){
            return this.canAfford(75);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}