# CUCPC-Tank-Game
Internal prototype AI programming competition game for the CU Boulder Competitive Programming Club.




# Instructions  
Tank Game is played on a 1000 by 500 board with two players controlled by AI. Each player can spawn units, move them, and perform actions with the units. There are two types of units: Rovers, and Tanks. The field is also scattered with Sites, each of which contain coins that your rovers can mine. When your rovers are near a site, it will begin mining and filling up it's inventory, to a maximum of 100 coins. If the rover returns to your base, it's inventory will be emptied and the player will gain those coins. Each player can spend coins to buy more units, 25 for Rovers, and 75 for Tanks. Tanks are the other game unit, and they can move (with less range than the rovers) but they can also shoot at other units with a cooldown of 5 turns. If their shot hits a unit, the hit unit will be removed from the game. The only other way for units to be removed from the game is if they are commanded to move off the map.  

Each turn, each player can spawn units, move units, and fire tanks. A tank cannot be moved and fired on the same turn - the engine will process whichever command is sent first. The engine will first process all fire commands, followed by move commands, followed by spawn commands.  

The map is 1000 by 500. Point (0,0) is in the upper left of the map, with (1000, 500) being in the bottom right. Player 0's home base spans from (0, 0) to (200, 500), with it's spawnpoint at (100, 250). Player 1's home base spans from (800, 0) to (1000, 500) with it's spawnpoint at (900, 250). A rover can drop off it's inventory anywhere in the home base, and it will happen automatically as long as they're inside the range. All units spawn at the spawnpoint, and tank shots can not be fired into a home base.  

## Game Input - initialization  
Line 1: yourId : 0 indicates that your home base is on the left side of the map, 1 indicates that it's on the right side.  
Line 2: numSites : integer representing number of sites on board  
Next numSites Lines: x y : x and y locations for each site  
Ex:  
```
0
5
500 500
300 700
700 300
100 600
900 400
```
## Game Input - for each turn  
Line 1: yourcoins opcoins : two integers showing your current coins and your opponents  
Line 2: n : Integer of number of units on board currently  
Next n Lines: id owner type x y param1 : the id, owner, type, x position, y position, and param1 for each unit. All integers. For rovers, param1 is the number of units stored, and for tanks param1 is how many turns until the tank can fire (0 means can fire now). Owner is 0 if it's your unit, 1 if it's opponents.
Ex:  
```
250 300
5
0 0 0 250 350 65
1 1 0 100 600 38
2 1 0 409 392 98
3 1 1 800 200 1
4 0 0 100 58 12
```


## Bot Output - for each turn  
Your program must output a string of valid commands for a single turn. Possible outputs are:  
Spawn a unit: SPAWN type : type is an integer, 0 for a Rover, 1 for a Tank  
Move a unit: MOVE id x y : id, x, y are all integers. Rovers must be within 75 units of desired target position, Tanks must be within 50 units of the desired target position.  
Fire a unit: FIRE id x y : id, x, y are all integers. Tank must be within 75 units of desired target position. A tank may not MOVE & FIRE on same turn.  
Do nothing: IDLE : If you'd like to not do any other commands, you can do this  

Ex:
```
SPAWN 0,SPAWN 0,MOVE 2 394 600,FIRE 3 400 450  
```