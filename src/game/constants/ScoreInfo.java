package game.constants;

import game.Unit;

public class ScoreInfo{
    public static final int SITE_EARNINGS = 5;
    public static final int ROVER_MAX_STORAGE = 30;
    public static final int PLAYER_0_BASE_RIGHT_X = 100;
    public static final int PLAYER_1_BASE_LEFT_X = 900;
    public static final boolean WITHIN_DEPOSIT_BOUNDARY(int x, int owner){
        return owner == 0 ? x <= PLAYER_0_BASE_RIGHT_X : x >= PLAYER_1_BASE_LEFT_X;
    }
    public static final boolean WITHIN_DEPOSIT_BOUDARY(Unit un, int owner) {
    	return WITHIN_DEPOSIT_BOUNDARY(un.getX(), owner);
    }
    public static final int ROVER_COST = 25;
    public static final int TANK_COST = 75;
    public static final int IDLE_EARNINGS = 1;
    public static final int STARTING_BALANCE = 0;
    public static final int NUM_ROVERS_EARN_PER_SITE = 4;
}