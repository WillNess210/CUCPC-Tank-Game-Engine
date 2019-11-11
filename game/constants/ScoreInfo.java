package constants;

public class ScoreInfo{
    public static final int SITE_EARNINGS = 10;
    public static final int ROVER_MAX_STORAGE = 100;
    public static final int PLAYER_0_BASE_RIGHT_X = 100;
    public static final int PLAYER_1_BASE_LEFT_X = 900;
    public static final boolean WITHIN_DEPOSIT_BOUNDARY(int x, int owner){
        return owner == 0 ? x <= PLAYER_0_BASE_RIGHT_X : x >= PLAYER_1_BASE_LEFT_X;
    }
}