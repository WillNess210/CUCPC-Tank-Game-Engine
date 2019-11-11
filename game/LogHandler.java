package game;

import java.util.ArrayList;

import game.Game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogHandler{
    ArrayList<ArrayList<String>> turns;
    ArrayList<String> scores;
    public LogHandler(){
        this.turns = new ArrayList<ArrayList<String>>();
        this.scores = new ArrayList<String>();
    }
    public void addTurn(Game game){
        // turns
        ArrayList<String> turn = new ArrayList<String>();
        for(Player ply : game.getPlayers()){
            for(Unit un : ply.getUnits()){
                turn.add(un.getLogString(ply.getId()));
            }
        }
        this.turns.add(turn);
        // scores
        this.scores.add(game.getScores());
    }

    public void generateLogFile(Game game){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("replays/replay_" + System.currentTimeMillis() + ".log"));
            writer.write(game.getWinner() + "\n");
            writer.write(game.getScores() + "\n");
            String[] sites = game.getSitesStrings();
            writer.write(sites.length + "\n");
            for(String siteLine : sites){
                writer.write(siteLine + "\n");
            }
            writer.write(this.turns.size() + "\n");
            for(int i = 0; i < this.turns.size(); i++){
                ArrayList<String> units = this.turns.get(i);
                String scores = this.scores.get(i);
                writer.write(scores + "\n");
                writer.write(units.size() + "\n");
                for(String unitLine : units){
                    writer.write(unitLine + "\n");
                }
            }
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}