package game;

public class Unit extends Point{
    private int id, type, param1;

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
}