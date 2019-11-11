package game;

import constants.UnitType;
import constants.ScoreInfo;

public class Unit extends Point{
    private int id, type, param1;
    private boolean actionUsedThisTurn;
    public Unit(){
        this.id = -1;
        this.type = -1;
    }
    public Unit(int id, int type){
        this.id = id;
        this.type = type;
    }
    public Unit(int id, int type, int x, int y){
        super(x, y);
        this.id = id;
        this.type = type;
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

}