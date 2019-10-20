package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import constants.UnitType;

public class Player{
    private int coins, id;
    private Map<Integer, Unit> units;
    public Player(int id){
        this.id = id;
        this.coins = 100;
        this.units = new HashMap<Integer, Unit>();
    }
    public List<Unit> getUnits(){
        return new ArrayList<Unit>(this.units.values());
    }
    public Unit getUnit(int id){
        return units.get(id);
    }
    public boolean addUnit(int id, int type, int x, int y){
        units.put(id, new Unit(id, type, x, y));
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