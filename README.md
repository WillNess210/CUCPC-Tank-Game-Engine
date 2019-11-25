# Tank Game AI Programming Competition - Engine
Tank Game is played on a map with two players. Each player is controlled by a user's programmed AI. 

The game is played till the end of 200 turns, or until a bot errors or times out (whicever comes first). The winner is dictated by whoever owns more "coins" at the end of the game. If your bot errors or times out, you will lose.

This game supports the following languages:
1. Python 3.6.8
2. Java 1.8.0_231
3. C++ 17

## Map
Maps will randomly be selected from one of three sizes:
 - Small (1219 x 1219)
 - Medium (1829 x 1219)
 - Large (2438 x 1219)

You can specify a map size to play on with -S, -M, or -L flags.

## Deployment
Each player has a deployment area.
There are six different deployment areas:
 1. Left and Right Triangle Regions (with a mimimum distance of 427 units between the two)
 2. Top and Bottom Rectangular Regions (with a minimum distance of 610 units between the two)
 3. Upper Left and Lower Right Quadrants (with a munum distance of 323 units between the two)
 4. Left and Reight Rectangular Regions (with a minimum distance of 610 units between the two)
 5. Top and Bottom Triangular Regions (with a minimum distance of 427 units between the two)
 6. Lower Left and Upper Right Triangular Regions (with a minimum distance of 610 units between the two)

Units inside their home bases cannot be killed.
Bots can spawn units anywhere within their deployment area.

## Sites

Each map will have randomly generated Sites. There will always be either 4, 6, or 8 sites that are generated in pairs and mirrored across the center of the map.
Rovers collect resources from sites. Once full they must return to your deployment area to deposit the resources.

## Rules / Units
Tank Game consists of two different kinds of units: Rovers, and Tanks. Each player can spawn their own Rover or Tank for a cost of coins, and each player starts with one Rover.

Rovers are resource gathering units. They can move up to 305 units in any turn. Rovers have a storage capacity of 30 coins, and if they end a turn on a Site, they will add 5 coins to their storage. If a rover ends a turn on their home base, the coins in it's storage will be deposited to the players balance, and the rover's storage will be set back to 0. Rovers cost 70 coins to spawn. Rover's param1 value is their current storage value (read input section for more on param1).

Tanks are attacking units. They can move up to 305 units in any turn. Tanks can fire anywhere within a 762 unit radius, and their explosions have a radius of 10. If the center of an opposing unit falls within the explosion radius, that opposing unit will be removed from the game. Tanks have a fire cooldown of 10 turns. Tanks cost 138 coins to spawn. Tank's param1 value is how many turns they have to wait until they can fire again, a tank with a param1 value of 0 means they can shoot that turn.

## Initilization Input
At the start of each game, the engine will feed your bot the following information via the standard input stream:
```
Line 1: botId - your bot's 
Line 2: mapWidth mapHeight deploymentType - Dimensions of the map and deployment type
Line 3: deploymentCenterX deploymentCenterY - The (x,y) coordinates of the center of your bots deployment area
Line 4: numSites - the number of sites in the given map
Next numSites Lines: siteX siteY - x and y coordinates of each site
```
ex:
```
0
1829 1219 3
50 250
4
400 250
600 250
450 200
550 300
```
## Input Per Turn
At the start of each turn, the engine will feed your bot the following information via the standard input stream:
```
Line 1: yourCoins opCoins - your current balance of coins followed by your opponents.
Line 2: numUnits - the number of units currently on the map
Next numUnits Lines: id owner type x y param1 - unit info
```
ex:
```
250 212
3
0 0 0 250 300 25
0 1 0 300 400 20
1 1 1 298 291 5
```

## Bot Output
Each turn, the game engine will expect your bot to send commands. There are four possible commands you can send, and some of them take parameters:
1. IDLE
2. SPAWN type x, y
3. MOVE id x y
4. FIRE id x y

You may only issue one command per unit per turn.

Idle: Have your bot output "IDLE" in the case that you'd not like to do any other commands. You **MUST** send some output each turn, so IDLE is necessary in this case. Example: "IDLE"

Spawn: A unit of the type specified will be spawned at (x, y) if you have enough coins to afford it. The cost of the unit will be taken out of your balance. You cannot use another command for the unit on the same turn it's being spawned in. Example: "SPAWN 0 50 250"

Move: The unit with the specified id will move towards / arrive at (x, y). If (x, y) is farther away than the unit's range allows, it'll move the maximum distance it can directly towards (x, y) for that turn. If (x, y) is within the unit's range, then the unit will end up at (x, y) at the end of the turn. Example: "MOVE 0 350 400" (means move unit 0 towards (350, 400))

Fire: The tank (must be a tank, not a rover) with the specified id will fire at (x, y). If (x, y) is farther than the unit's range allows, nothing will happen. Read above to learn more about how tank's fire mechanics can kill opposing units. Important note: fires are processed BEFORE moves, and happen at the very start of the turn simultaneously. Example: "FIRE 1 400 130" (means fire tank w/ ID 1 at (400, 130)).

You can do multiple commands per turn, separated by commas. Your bot **must only produce 1 line of output per turn**.
Example: "MOVE 0 350 400,FIRE 1 400 130,MOVE 2 490 492"

Anything printed to the standard error stream will be printed out in the console when you're running the engine, so you may use that to help debug your bot.

# Using the Engine
 You can use the provided tank-engine.jar file to run games. The jar file takes in two parameters when running, which will be filepaths to folders containing bots. Here is an example call of the engine:
 ```
 java -jar bots/MyBot bots/OtherBot
 java -jar -M bots/Mybot bots/OtherBot
```
This will produce the following two replay files in a folder called replays/ that will be created in the same directory as your engine:
```
latest_replay.log
replay_[timestamp].log
```
If you'd like to watch the game that just played on the engine, take your .log file and head over to the visualizer located [here](https://github.com/WillNess210/CUCPC-Tank-Game-Visualizer).
