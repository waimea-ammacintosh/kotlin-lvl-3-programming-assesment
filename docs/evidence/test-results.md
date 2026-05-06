# Results of Testing

The test results show the actual outcome of the testing, following the [Test Plan](test-plan.md)

---

## Moving - VALID

I tested to see if valid moves are possible in my game.

### Test Data To Use

I moved in 3 valid directions, to test if the movement works, as that will give me a good idea of if moving works.

### Test Result

![moving-valid.gif](screenshots/moving-valid.gif)

Valid moving worked just how I expected it, all movements took me in the right direction, and every time I pushed a
button, I moved.

---

## Moving - BOUNDARY

I tested to see how movement along and to the boundary are possible in my game.

### Test Data To Use

I moved to and from each boundary, and along each boundary to check if boundary moves work.

### Test Result

![move-boundary.gif](screenshots/move-boundary.gif)

Movement along the boundary worked as expected, I could move to, from and along the boundary without any mishaps.

---

### Moving - INVALID

I tested to see how my program handles moving in invalid directions (i.e. outside the map or through a wall)

### Test Data To Use

I tried to move out of the map 2 times, and through a maze wall 2
times.

### Test Result

![moving-invalid.gif](screenshots/moving-invalid.gif)

The movement was just as expected. I could not move out of the map, or through a wall

---

### Trading - VALID

I test to see if I can trade for an item when I have an item that a square wants, i.e. a valid trade.

### Test Data To Use

I executed 3 valid trades, to test if the game would handle valid trades.

### Test Result

![trading.gif](screenshots/trading.gif)

The trading worked as expected, when I executed a valid trade, it added the item into my inventory, and updated to show that 
I had traded at that location.

---

### Trading - INVALID

I tested to see how my game handles invalid trades, such as trading at a square where you do not have the needed resource, or trying to re-trade
at the same location.

### Test Data To Use

I tried to execute 3 trades where I did not have the correct resource, and 3 where I had already traded to test if the 
game would handle invalid trades.

### Test Result

![trading.gif](screenshots/trading.gif)

The game handled invalid trades as expected, when I executed a invalid trade, no item was added into my inventory, and the UI didn't update.

---

### Map Generation - GAME MECHANIC

I test to see how my game creates and implements random maps.

### Test Data To Use

I generated 3 different maps to test how my program handles map generation, and if it is actually random, and if a random of 3 maze is always implemented

### Test Result

Trial 1: ![map-test1.png](screenshots/map-test1.png)

Trial 2: ![map-test2.png](screenshots/map-test2.png)

Trial 3: ![map-test3.png](screenshots/map-test3.png)

Each Time the map was generated, it was random, apart from start always being at (0,0) as expected, and a random of 3 mazes
was implemented each time, as expected

---

### End States - GAME MECHANIC

I tested to see how my game displays end screens when different end states are reached.

### Test Data To Use

I first lost a game, and then won a game, to test both possible win states

### Test Result

![trading.gif](screenshots/trading.gif)

The game handled invalid trades as expected, when I executed a invalid trade, no item was added into my inventory, and the UI didn't update.

---

### Score - GAME MECHANIC

I tested to see how my game scores the player when the game ends.

### Test Data To Use

I first lost a game, and then won a game, to test how both ends score

### Test Result

Lose: 
![lose.gif](screenshots/lose.gif)

As expected, the score displays as 0 when you lose

Win:
![score-win.gif](screenshots/score-win.gif)

the time elapsed is: 
![time-elapsed.png](screenshots/time-elapsed.png)

and 79251 + 220749 = 300,000 which is the total timer length, so scoring works

---

### Inventory - GAMEPLAY

I tested how items are added into my inventory during a trade.

### Test Data To Use

I executed 3 valid trades, to test how items are added to my inventory, and how items gathered on the UI is updated.

### Test Result

![trading.gif](screenshots/trading.gif)

The trading worked as expected, when I executed a valid trade, it added the item into my inventory, and updated to show that
I had traded at that location.

---