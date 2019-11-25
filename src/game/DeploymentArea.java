package game;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public class DeploymentArea{
    private Area deployArea;
    private int ownerId;

    public DeploymentArea(int deploymentType, int mapWidth, int mapHeight, int ownerId){
    	this.ownerId = ownerId;
    	if (deploymentType == 1) {
    		if (ownerId == 0) {
	    		int[] px = new int[]{0, 305, (int)(mapWidth/2)-229, 305, 0};
	            int[] py = new int[]{0, 0, (int)mapHeight/2, mapHeight, mapHeight};
	    		this.deployArea = new Area(new Polygon(px, py, 5));
    		}
    		else if (ownerId == 1) {
    			int[] px = new int[]{mapWidth, mapWidth-305, (int)(mapWidth/2)+229, mapWidth-305, mapWidth};
	            int[] py = new int[]{0, 0, (int)mapHeight/2, mapHeight, mapHeight};
	    		this.deployArea = new Area(new Polygon(px, py, 5));
    		}
    	}
    	else if (deploymentType == 2) {
    		if (ownerId == 0) {
    			this.deployArea = new Area(new Rectangle(0, 0, mapWidth, (int)mapHeight/2-305));
    		}
    		else if (ownerId == 1) {
    			this.deployArea = new Area(new Rectangle(0, mapHeight-305, mapWidth, (int)mapHeight/2-305));
    		}
    	}
    	else if (deploymentType == 3) {
    		Ellipse2D center = new Ellipse2D.Double((int)mapWidth/2-229, (int)mapHeight/2-229, 457, 457);
    		if (ownerId == 0) {
	    		Rectangle part1 = new Rectangle(0, 0, (int)mapWidth/2, (int)mapHeight/2);
	    		this.deployArea = new Area(part1);
	    		this.deployArea.subtract(new Area(center));
    		}
    		else if (ownerId == 1) {
    			Rectangle part1 = new Rectangle((int)mapWidth/2, (int)mapHeight/2, (int)mapWidth/2, (int)mapHeight/2);
	    		this.deployArea = new Area(part1);
	    		this.deployArea.subtract(new Area(center));
    		}
    	}
    	else if (deploymentType == 4) {
    		if (ownerId == 0) {
    			this.deployArea = new Area(new Rectangle(0, 0, (int)(mapWidth/2)-305, mapHeight));
    		}
    		else if (ownerId == 1) {
    			this.deployArea = new Area(new Rectangle((int)mapWidth/2+305, 0, (int)(mapWidth/2)-305, mapHeight));
    		}
    	}
    	else if (deploymentType == 5) {
    		if (ownerId == 0) {
    			int[] px = new int[]{0, mapWidth, mapWidth, (int)(mapWidth/2), 0};
                int[] py = new int[]{0, 0, 152, (int)(mapHeight/2)-229, 152};
        		this.deployArea = new Area(new Polygon(px, py, 5));
    		}
    		else if (ownerId == 1) {
    			int[] px = new int[]{0, mapWidth, mapWidth, (int)(mapWidth/2), 0};
                int[] py = new int[]{mapHeight, mapHeight, mapHeight-152, (int)(mapHeight/2)+229, mapHeight-152};
        		this.deployArea = new Area(new Polygon(px, py, 5));
    		}
    		
    	}
    	else if (deploymentType == 6) {
    		if (ownerId == 0) {
    			int[] px = new int[]{0, mapWidth-305, 0};
                int[] py = new int[]{305, mapHeight, mapHeight};
        		this.deployArea = new Area(new Polygon(px, py, 3));
    		}
    		else if (ownerId == 1) {
    			int[] px = new int[]{305, mapWidth, mapWidth};
                int[] py = new int[]{0, 0, mapHeight-305};
        		this.deployArea = new Area(new Polygon(px, py, 3));
    		}
    		
    	}
    }
    
    public boolean contains(Point p) {
    	return this.deployArea.contains(p.getX(), p.getY());
    }
    
    public Point getCenter() {
    	Rectangle rect = this.deployArea.getBounds();
    	return new Point((int)rect.getCenterX(), (int)rect.getCenterY());
    }
    
}