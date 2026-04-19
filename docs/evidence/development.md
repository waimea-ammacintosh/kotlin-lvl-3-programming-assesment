# Development Log

The development log captures key moments in your application development:

- **Design ideas / notes** for features, UI, etc.
- **Key features** completed and working
- **Interesting bugs** and how you overcame them
- **Significant changes** to your design
- Etc.

---

## Date: 25/03/2026

Today I managed to finalize my map generation. Initially I had problems with how the map was generating, as multiple
locations could be put on the same tile of the locations array, so it would never work. However, I refined my generation
function to be much simpler by adding a co-ordinate system for setting up the map. This also meant that I could initialize
the starting square in a similar fashion, and is now much simpler
![example.png](screenshots/example.png)

---

## Date: 19/04/2026

today I decided to implement a maze feature in my game to add a layer of complexity to the map. I picked 3 different
maze designs off the web to implement into my game, shown in this picture: ![maze ideas](screenshots/)
I then implemented it using this code:

![example.png](screenshots/example.png)

---

## Date: 19/04/2026

I fixed the problem where my Inventory window UI seemed to not update. I originally had the window be created in the 
the main function, but was updating everything in the mainWindow. I changed it so that it was all being handled in
the MainWindow, and now the UI updates as expected

![example.png](screenshots/example.png)

---

## Date: xx/xx/20xx

Example description and notes. Example description and notes. Example description and notes. Example description and
notes. Example description and notes. Example description and notes.

![example.png](screenshots/example.png)

---

## Date: xx/xx/20xx

Example description and notes. Example description and notes. Example description and notes. Example description and
notes. Example description and notes. Example description and notes.

![example.png](screenshots/example.png)

---


