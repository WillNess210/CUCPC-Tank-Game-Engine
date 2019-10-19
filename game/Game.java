package game;
// THIS CLASS WILL HANDLE ALL GAME INFO (taking user input, parsing it to make sure it's correct, updating gamestate, then returning new strings to send)
public class Game{
    public static final int MAX_TURNS = 50;
    public Game(){

    }

    // this method is called when the engine receives info from bots
    public void updateBot(int botid, String resultFromBot){
    }

    // this method is called to get the game to send game state to the bot
    public String getStringToSendToBot(int botid){
        return "TODO";
    }

    // when this method returns true, the game will stop playing. Suggestion: turn limit or when a player has won
    public boolean isGameOver(){
        return false;
    }

    // return ID of winning bot, -1 when no winner has been established yet
    public int getWinner(){
        return 0;
    }

}