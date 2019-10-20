package game;

import constants.ActionType;

public class Command{
    private int type, param1, param2, param3;
    public Command(String input){
        String[] args = input.split(" ");
        String cmd = args[0];
        if(cmd.equals("IDLE")){
            this.type = ActionType.IDLE;
        }else if(cmd.equals("SPAWN")){
            this.type = ActionType.SPAWN;
            this.param1 = Integer.parseInt(args[1]);
        }else if(cmd.equals("MOVE")){
            this.type = ActionType.MOVE;
            this.param1 = Integer.parseInt(args[1]);
            this.param2 = Integer.parseInt(args[2]);
            this.param3 = Integer.parseInt(args[3]);
        }else if(cmd.equals("FIRE")){
            this.type = ActionType.FIRE;
            this.param1 = Integer.parseInt(args[1]);
            this.param2 = Integer.parseInt(args[2]);
            this.param3 = Integer.parseInt(args[3]);
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getParam1() {
        return param1;
    }

    public void setParam1(int param1) {
        this.param1 = param1;
    }

    public int getParam2() {
        return param2;
    }

    public void setParam2(int param2) {
        this.param2 = param2;
    }

    public int getParam3() {
        return param3;
    }

    public void setParam3(int param3) {
        this.param3 = param3;
    }
    
}