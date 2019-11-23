package game;
import java.util.Random;

public class GameMap{
    private int width, height;
    private int deploymentType;
    private Point[] sites;
    private DeploymentArea[] deploymentAreas;

    public GameMap(int width, int height, int deploymentType, int numPlayers,  Random r) {
    	this.width = width;
    	this.height = height;
    	this.deploymentType = deploymentType;
    	this.initDeployment(numPlayers);
    	this.initSites(r);

    }
    
    public void initDeployment(int numPlayers) {
    	deploymentAreas = new DeploymentArea[numPlayers];
    	for (int i = 0; i< numPlayers; i++) {
    		deploymentAreas[i] = new DeploymentArea(this.deploymentType, this.width, this.height, i);
    	}
    	
    }
    
    public void initSites(Random r) {
        // logic
    }
    
    public int getWidth() {
    	return this.width;
    }
    public int getHeight() {
    	return this.height;
    }
    
    public DeploymentArea getDeploymentArea(int ownerId) {
    	return deploymentAreas[ownerId];
    }
    
    public boolean isOutOfBounds(Unit u) {
    	if (u.getX() > this.width || u.getX() < 0) {
    		return true;
    	}
    	if (u.getY() > this.height || u.getY() < 0) {
    		return true;
    	}
    	return false;
    }
}