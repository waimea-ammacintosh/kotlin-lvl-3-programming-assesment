# Plan for Testing the Program

The test plan lays out the actions and data I will use to test the functionality of my program.

Terminology:

- **VALID** data values are those that the program expects
- **BOUNDARY** data values are at the limits of the valid range
- **INVALID** data values are those that the program should reject

---

## Moving - VALID

I will test to see if valid moves are possible in my game.

### Test Data To Use

I will move in 3 valid directions, to test if the movement works, as that will give me a good idea of if moving works.

### Expected Test Result

When I click a valid button, I should move in that direction.

---

## Moving - BOUNDARY

I will test to see how my program handles movement on the boundary.

### Test Data To Use

I will try going to each boundary, to check if I can move to the boundary, away from the boundary, and along the boundaries.

### Expected Test Result

I should be able to move normally going to and from the boundary and along the boundaries.

---

## Moving - INVALID

I will test to see how my program handles moving in invalid directions (i.e. outside the map or through a wall).

### Test Data To Use

I will first move along a 3 different boundary squares, and then try to move out of the map 2 times, and through a maze wall 2 times.

### Expected Test Result

I should be able to move normally along the boundary, but when I try to move out of the map, it should not move me, and it should not move me when 
I try to move through a wall.

---

## Trading - VALID

I will test to see if I can trade for an item when I have an item that a square wants.

### Test Data To Use

I will do 3 valid trades to test if trading works.

### Expected Test Result

When I trade an item that a square wants, and I also have in my inventory, for an item a square is selling, it should add that item to my inventory
and update the UI to show that I have already traded with that location.

---

## Trading - INVALID

I will test to see how my program handles invalid trades (i.e. trade an item I don't have, or at a square I have already traded at).

### Test Data To Use

I will try trade 3 times when I don't have the item they want, and I will try trade at 3 squares I have already traded at.

### Expected Test Result

None of these trades should work, the UI should not update, and I should gain nothing into my inventory.

---

## Map generation - GAME MECHANIC

I will test my map generation algorithm to check if the map generation always works, and if the maze is always implemented correctly.

### Test Data To Use

I will generate 3 different maps and check of all locations have a unique index in the array, and check if the maze is implemented properly.

### Expected Test Result

Every time, I expect all locations to have a unique index, the start location should always be at (0,0), and one of three should be implemented 
with no complications.

---

## End states - GAME MECHANIC

I will test my end states. When I win, and when I lose, to check if the correct end screen is shown when the correct end state is reached.

### Test Data To Use

I will win, and then lose, to test the two win states.

### Expected Test Result

When the timer runs out, I expect the lose screen to appear, and when I collect all resources and go back to the start, I should see the win screen.

---

## Score - GAME MECHANIC

I will test How my game scores points when the player wins.

### Test Data To Use

I will win, and then lose, to test how my game handles both states, and how points are scored.

### Expected Test Result

When I lose, I expect the score to be 0. When I win I expect the score to be 300,000 (which is the length of the game timer in milliseconds) minus the 
time it takes the player to complete the game.

---

## Inventory - GAMEPLAY

I will test How items are added into my inventory during a trade.

### Test Data To Use

I will execute 3 trades, to see how items are added to my inventory list.

### Expected Test Result

When I execute a trade, I expect the item I traded for to be displayed in the inventory list, and the total items gathered to increase by one.

---

## Minimap - VALID

I will test how the minimap updates when a valid move is executed.

### Test Data To Use

I will perform a variety of valid moves and see how the minimap updates in relation to those moves.

### Expected Test Result

When I move, the player Icon on the minimap should move in the same direction as I moved.

---

## Minimap - BOUNDARY

I will test how the minimap updates when moving along the boundary.

### Test Data To Use

I will move around the boundary of the map a variety of times and see how the minimap updates

### Expected Test Result

When I move along the boundary, the player icon should move in the correct direction that corresponds to the direction
I chose to move. It should also update correctly when going to, and coming off the boundary.

---

## Minimap - INVALID

I will test how my minimap updates when invalid inputs are provided by the user.

### Test Data To Use

I will try go out of the boundary, and through a map wall multiple times.

### Expected Test Result

No invalid move should update the position of the player on the minimap.

---

## Trade - BOUNDARY

I will test if how my game handles trading on the edge of the map.

### Test Data To Use

I will execute 3 trades on the map boundary, to see how items are added to my inventory list when on map boundary.

### Expected Test Result

When I execute a trade, I expect the item I traded for to be displayed in the inventory list.

---