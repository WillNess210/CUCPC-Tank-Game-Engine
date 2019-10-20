# CUCPC-Tank-Game
Internal prototype AI programming competition game for the CU Boulder Competitive Programming Club.




# Instructions  
Tank Game is played on a 1000 by 500 board with two players controlled by AI. Each player can spawn units, move them, and perform actions with the units.
## Game Input - initialization  
Line 1: numSites : integer representing number of sites on board  
Next numSites Lines: x y : x and y locations for each site
Ex:
```
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
Next n Lines: id owner type x y param1 : the id, owner, type, x position, y position, and param1 for each unit. All integers. For rovers, param1 is the number of units stored, and for tanks param1 is 1 if they can fire, and 0 if they can't. Owner is 0 if it's your unit, 1 if it's opponents.
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
Your program must output a string of valid commands to take part of in a single turn. Possible outputs are:  
Spawn a unit: SPAWN type : type is an integer, 0 for a Rover, 1 for a Tank  
Move a unit: MOVE id x y : id, x, y are all integers. Bot must be within 50 units of desired target position.  
Fire a unit: FIRE id x y : id, x, y are all integers. Tank must be within 75 units of desired target position. A tank may not MOVE & FIRE on same turn.  
  
Ex:
```
SPAWN 0,SPAWN 0,MOVE 2 394 600,FIRE 3 400 450  
```