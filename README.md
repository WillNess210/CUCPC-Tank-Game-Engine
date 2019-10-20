# CUCPC-Tank-Game
Internal prototype AI programming competition game for the CU Boulder Competitive Programming Club.




# Instructions  
## Game Input - initialization  
## Game Input - for each turn  
Line 1: yourcoins opcoins : two integers showing your current coins and your opponents  
Line 2: n : Integer of number of units on board currently  
Next n Lines: id type x y param1 : the id, type, x position, y position, and param1 for each unit. All integers. For rovers, param1 is the number of units stored, and for tanks param1 is 1 if they can fire, and 0 if they can't.  
## Bot Output - for each turn  
Your program must output a string of valid commands to take part of in a single turn. Possible outputs are:  
Spawn a unit: SPAWN type : type is an integer, 0 for a Rover, 1 for a Tank  
Move a unit: MOVE id x y : id, x, y are all integers. Bot must be within 50 units of desired target position.  
Fire a unit: FIRE id x y : id, x, y are all integers. Tank must be within 75 units of desired target position. A tank may not MOVE & FIRE on same turn.  
Ex: "SPAWN 0,SPAWN 0,MOVE 2 394 600,FIRE 3 400 450"  