package game;

import game.constants.ActionType;

public class Command{
    private int type, param1, param2, param3;
    private boolean error;
    public Command(String input){
    	this.error = false;
        String[] args = input.split(" ");
        if(args.length == 0) {
        	this.error = true;
        	return;
        }
        String cmd = args[0];
        try {
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
	        }else {
	        	this.type = -1;
	        }
        } catch (NumberFormatException e) {
        	this.error = true;
        	return;
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
    
    public String toString() {
    	switch(this.getType()) {
    		case ActionType.SPAWN:
    			return "SPAWN " + this.getParam1();
    		case ActionType.IDLE:
    			return "IDLE";
    		case ActionType.MOVE:
    			return "MOVE " + this.getParam1() + " " + this.getParam2() + " " + this.getParam3();
    		case ActionType.FIRE:
    			return "FIRE " + this.getParam1() + " " + this.getParam2() + " " + this.getParam3();
    	}
    	return "COMMAND ERROR";
    }
    public boolean badCommand() {
    	return this.type == -1 || this.error;
    }
}