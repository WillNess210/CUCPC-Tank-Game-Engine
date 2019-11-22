package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import game.Game;
import game.constants.GameMap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogHandler{
    private ArrayList<ArrayList<String>> turns;
    private ArrayList<String> scores;
    private Map<Integer, ArrayList<Point>> explosions;
    
    public LogHandler(){
        this.turns = new ArrayList<ArrayList<String>>();
        this.scores = new ArrayList<String>();
        this.explosions = new HashMap<Integer, ArrayList<Point>>();
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
    
    public void addExplosion(Point explosion) {
    	if(!explosions.containsKey(this.turns.size())) {
    		explosions.put(this.turns.size(), new ArrayList<Point>());
    	}
    	explosions.get(this.turns.size()).add(explosion);
    }
    
    public void generateLogFile(Game game, String replay_name) {
    	try{
        	File directory = new File(String.valueOf("replays"));
        	if(!directory.exists()){
        		directory.mkdir();
        	}
            BufferedWriter writer = new BufferedWriter(new FileWriter("replays/" + replay_name + ".log"));
            writer.write(game.getWinner() + "\n");
            writer.write(game.getScores() + "\n");
            writer.write(GameMap.WIDTH + " " + GameMap.HEIGHT + "\n");
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
                if(this.explosions.containsKey(i)) {
                	ArrayList<Point> expls = this.explosions.get(i);
                	writer.write(expls.size() + "\n");
                	for(Point ex : expls) {
                		writer.write(ex.getX() + " " + ex.getY() + "\n");
                	}
                }else {
                	writer.write(0 + "\n");
                }
            }
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public void generateLogFile(Game game){
        generateLogFile(game, "replay_" + System.currentTimeMillis());
    }
}