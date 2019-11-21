package game;

import game.constants.UnitType;
import game.constants.ScoreInfo;

public class Unit extends Point{
    private int id, type, param1;
    private boolean actionUsedThisTurn;
    public boolean hasBeenHit;
    public Unit(){
        this.id = -1;
        this.type = -1;
        this.hasBeenHit = false;
        this.actionUsedThisTurn = false;
    }
    public Unit(int id, int type){
        this.id = id;
        this.type = type;
        this.hasBeenHit = false;
        this.actionUsedThisTurn = false;
    }
    public Unit(int id, int type, int x, int y){
        super(x, y);
        this.id = id;
        this.type = type;
        this.hasBeenHit = false;
        this.actionUsedThisTurn = false;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public int getParam1(){
        return this.param1;
    }
    public void setParam1(int param1){
        this.param1 = param1;
    }
    
    public void action() {
    	this.actionUsedThisTurn = true;
    }
    
    public boolean hasUsedAction() {
    	return this.actionUsedThisTurn;
    }

    public double dist(Point b){
        return Math.hypot(b.getX() - this.getX(), b.getY() - this.getY());
    }

    public String getLogString(int owner){
        return owner + " " + this.getId() + " " + this.getType() + " " + this.getX() + " " + this.getY() + " " + this.getParam1();
    }

    // ACTION FUCNS

    public void moveTowards(Point goal){
        Point nextP = this.getNextPoint(goal, this.getType() ==  UnitType.ROVER ? UnitType.ROVER_MOVE_RANGE : UnitType.TANK_MOVE_RANGE);
        this.setX(nextP.getX());
        this.setY(nextP.getY());
    }

    public void addSiteIncome(){
        this.setParam1(Math.min(this.getParam1() + ScoreInfo.SITE_EARNINGS, ScoreInfo.ROVER_MAX_STORAGE));
    }
    public int getStorage(){
        return this.getParam1();
    }
    public void clearStorage(){
        this.setParam1(0);
    }

    public boolean withinDepositBoundary(int owner){
        return owner == 0 ? (this.getX() <= ScoreInfo.PLAYER_0_BASE_RIGHT_X) : (this.getX() >= ScoreInfo.PLAYER_1_BASE_LEFT_X);
    }
    
    public int getPurchaseCost() {
    	return this.getType() == 0 ? 25 : this.getType() == 1 ? 75 : -1;
    }
    
    public boolean isOutOfBounds() {
    	return this.getX() < 0 || this.getX() >= 1000 || this.getY() < 0 || this.getY() >= 500;
    }
    
    public void newTurnUpdate() {
    	this.actionUsedThisTurn = false;
    	if(this.getType() == UnitType.TANK) {
    		this.setParam1(Math.max(0, this.getParam1() - 1));
    	}
    }
}