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

### Moving - BOUNDARY/INVALID

I tested to see how my program handles moving in invalid directions (i.e. outside the map or through a wall) and how if
I can move in expected
ways along the boundaries of the map.

### Test Data To Use

I moved along a 3 different boundary squares, and then tried to move out of the map 2 times, and through a maze wall 2
times.

### Test Result

![moving-invalid.gif](screenshots/moving-invalid.gif)

The movement was just as expected. I could move perfectly along the border, but I could not move out of the map, or
through a wall

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

I test to see how my game handles invalid trades, such as trading at a square where you do not have the needed resource, or trying to re-trade
at the same location.

### Test Data To Use

I tried to execute 3 trades where I did not have the correct resource, and 3 where I had already traded to test if the 
game would handle invalid trades.

### Test Result

![trading.gif](screenshots/trading.gif)

The game handled invalid trades as expected, when I executed a invalid trade, no item was added into my inventory, and the UI didn't update.

---

