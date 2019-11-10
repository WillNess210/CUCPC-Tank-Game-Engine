# Log file structure
```
Line 1: winner : 0 or 1, based on who won, 2 if tie
Line 2: 0_score 1_score : integers representing score
Line 3: numTurns : number of turns the game went on for
for numTurns turns:
    Line 1: numUnits
    for numUnits:
        Line: owner id type x y param1
```

ex:
```
0
189 132
5
2
0 0 0 100 250 0
1 1 0 900 250 0
2
0 0 0 200 250 0
1 1 0 900 150 0
2
0 0 0 250 250 0
1 1 0 900 250 0
2
0 0 0 400 250 0
1 1 0 900 350 0
2
0 0 0 500 250 0
1 1 0 900 250 0
```