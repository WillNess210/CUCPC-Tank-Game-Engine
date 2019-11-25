package game;
import java.util.Random;

import game.constants.UnitType;

public class GameMap{
    private int width, height;
    private int deploymentType;
    private Unit[] sites;
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
    	this.sites = new Unit[4 + r.nextInt(3) * 2];
        for(int i = 0; i < sites.length; i += 2){
            Point tp = new Point(-1, -1);
            boolean validPoint = false;
            while(validPoint == false) {
            	int nX = (int) this.width/2 + r.nextInt((int) this.width/3);
                int nY = (int) this.height/2 + r.nextInt((int) this.height/3);
                tp.setX(nX);
                tp.setY(nY);
            	validPoint = true;
            	for(int j = 0; j < i && validPoint; j++) {
            		validPoint &= !tp.isWithinDist(sites[j], 75);
            	}
            }
            this.sites[i] = new Unit(i, UnitType.SITE, tp.getX(), tp.getY());
            this.sites[i + 1] = new Unit(i + 1, UnitType.SITE, this.width - tp.getX(), this.height - tp.getY());
        }
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
    
    public Unit[] getSites() {
    	return this.sites;
    }
}