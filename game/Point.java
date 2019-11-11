package game;

public class Point{
    private int x, y;
    
    public Point(){
        this.x = -1;
        this.y = -1;
    }
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public double dist(Point b){
        return Math.hypot(b.x - this.x, b.y - this.y);
    }

    public double dist2(Point b){
        int xx = b.getX() - this.getX();
        int yy = b.getY() - this.getY();
        return xx * xx + yy * yy;
    }

    public boolean isWithinDist(Point b, int maxDist){
        return dist2(b) <= (maxDist * maxDist);
    }

    public Point getNextPoint(Point goal, int maxDist){
        if(isWithinDist(goal, maxDist)){
            return goal;
        }
        double ang = Math.atan2(goal.getY() - this.getY(), goal.getX() - this.getX());
        return new Point(this.getX() + maxDist * Math.cos(ang), this.getY() + maxDist * Math.sin(ang));
    }
}