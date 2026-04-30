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

## Moving - BOUNDARY/INVALID

I will test to see how my program handles moving in invalid directions (i.e. outside the map or through a wall) and how if I can move in expected 
ways along the boundaries of the map.

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

When I trade an item that a square wants, and I also have in my inventory, for an item a square is selling, it should give me that item.

---

## Trading - INVALID

I will test to see how my program handles invalid trades (i.e. trade an item I don't have, or at a square I have already traded at).

### Test Data To Use

I will try trade 3 times where I don't have the item they want, and I will try trade at 3 squares I have already traded at.

### Expected Test Result

None of these trades should work.

---


