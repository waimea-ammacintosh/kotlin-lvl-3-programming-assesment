/*************************************************************
 *
 * Save the Cat
 *
 * A game made for NCEA level 3 A.S. 91906
 *
 * By: Aaron Macintosh
 *
 * Date:
 *************************************************************/

import com.formdev.flatlaf.themes.FlatMacDarkLaf
import java.awt.Color
import java.awt.Font
import java.awt.Point
import javax.swing.Timer
import javax.swing.*

/**
 * Application entry point
 */
fun main() {
    FlatMacDarkLaf.setup()                // Initialise the LAF
    val game = Game()                     // Get a game state object
    val window = MainWindow(game)    // Spawn the UI, passing in the game state
    val cutscene = IntroWindow(game, window)    // Spawns the Intro UI, passing in the game state, and the Main Window state
    SwingUtilities.invokeLater { cutscene.start() }     // Start introduction sequence
}

/**
 * Scales an image to the selected size using
 * SCALE_SMOOTH.
 *
 * @param width The width of the scaled image.
 * @param height The height of the scaled image.
 */
fun ImageIcon.scaled(width: Int, height: Int): ImageIcon =
    ImageIcon(image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH))

/**
 * Location class, used to store data about a specific location
 * in the game.
 *
 * @param name location name
 * @param description location description
 * @param wantedResource the name of the resource wanted at the location
 * @param sellingResource the name of the resource sold at the location
 * @param visited has the location been visited yet
 * @param traded has the location been traded at yet
 * @param canMoveEast can the player move East at this location
 * @param canMoveWest can the player move West at this location
 * @param canMoveNorth can the player move North at this location
 * @param canMoveSouth can the player move South at this location
 *
 */
class Location(
    val name: String,
    val description: String,
    val wantedResource: String,
    val sellingResource: String,
    var visited: Boolean = false,
    var traded: Boolean = false,
    var canMoveNorth: Boolean = true,
    var canMoveSouth: Boolean = true,
    var canMoveEast: Boolean = true,
    var canMoveWest: Boolean = true
)

/**
 * Game class, stores data relating to the state of the game, to pass on to the Main Window
 *
 *
 */
class Game {
    //private properties
    private val mapSize = 4
    private val tiles: Array<Array<Location?>> = Array(mapSize) { Array(mapSize) { null } }
    private val items = mutableListOf<String>()
    private val inventory = mutableListOf<String>()
    private val gameTimer = Timer(300000, null)

    var currentCoords: Point
    var currentLocation: Location?
    var hasWon = false
    var hasLost = false

    //instantiate all the location objects
    private val start = Location("Start", "The starting square. 'Come back here with your 16 resources to save your cat' - evil man.", "Everything", "Cat", visited = true)
    private val forest = Location("Forest", "A dark forest", "Coal", "Wood")
    private val farm = Location("Farm", "An old farm", "Wood", "Meat")
    private val castle = Location("Castle", "A large Castle", "Meat", "Torch")
    private val cave = Location("Cave", "A dark cave", "Torch", "Stone")
    private val road = Location("Road", "A stony Road", "Stone", "Coins")
    private val hall = Location("Hall", "A big Hall", "Coins", "Paper")
    private val postOffice = Location("Post Office", "The post office", "Paper", "Bag")
    private val huntersHouse = Location("Hunters House", "The house of the Hunter", "Bag", "Bow")
    private val armory = Location("Armory", "The Royal Armory", "Bow", "Armor")
    private val knightsHouse = Location("Knight's House", "The house of the local Knight", "Armor", "Tapestry")
    private val museum = Location("Museum", "A large museum", "Tapestry", "Fossil")
    private val apothecary = Location("Apothecary", "An apothecary", "Fossil", "Herbs")
    private val composter = Location("Composter", "A big Compost Pile", "Herbs", "Compost")
    private val garden = Location("Garden", "A large garden", "Compost", "Carrots")
    private val mine = Location("Mine", "A deep mine", "Carrots", "Coal")

    /**
     * runs on game object creation
     * creates items list, adds one random item from the list to the players inventory
     * creates map and adds timer action listener.
     */
    init {
        //initialise items list
        items.add("Coal")
        items.add("Wood")
        items.add("Meat")
        items.add("Torch")
        items.add("Stone")
        items.add("Coins")
        items.add("Paper")
        items.add("Bag")
        items.add("Bow")
        items.add("Armor")
        items.add("Tapestry")
        items.add("Fossil")
        items.add("Herbs")
        items.add("Compost")
        items.add("Carrots")

        //add one random item to inventory
        val randItem = items.indices.random()
        inventory.add(items[randItem])
        println(inventory)


        // add locations to tiles list
        addLocation(start, 0, 0)
        addLocation(forest)
        addLocation(farm)
        addLocation(castle)
        addLocation(cave)
        addLocation(road)
        addLocation(hall)
        addLocation(postOffice)
        addLocation(huntersHouse)
        addLocation(armory)
        addLocation(knightsHouse)
        addLocation(museum)
        addLocation(apothecary)
        addLocation(composter)
        addLocation(garden)
        addLocation(mine)

        //set current location
        currentCoords = Point(0, 0)
        currentLocation = getLocation()

        //adds one of three random mazes to the area
        addMaze()

        //adds action listener to timer
        gameTimer.addActionListener { hasLost = true }
    }

    /**
     * adds one of 3 random mazes to the map, to increase challenge
     */
    private fun addMaze() {
        val mazeNum = (1..3).random()
        println("Maze: $mazeNum")
        when (mazeNum) {
            1 -> initMaze1()

            2 -> initMaze2()

            3 -> initMaze3()
        }
    }

    /**
     * initialises maze 1 by blocking certain paths
     */
    private fun initMaze1() {
        tiles[0][0]!!.canMoveSouth = false
        tiles[0][1]!!.canMoveSouth = false
        tiles[0][2]!!.canMoveSouth = false
        tiles[1][0]!!.canMoveNorth = false
        tiles[1][0]!!.canMoveSouth = false
        tiles[1][1]!!.canMoveNorth = false
        tiles[1][1]!!.canMoveSouth = false
        tiles[1][2]!!.canMoveNorth = false
        tiles[1][2]!!.canMoveEast = false
        tiles[1][3]!!.canMoveWest = false
        tiles[2][0]!!.canMoveNorth = false
        tiles[2][0]!!.canMoveSouth = false
        tiles[2][1]!!.canMoveNorth = false
        tiles[2][1]!!.canMoveEast = false
        tiles[2][2]!!.canMoveWest = false
        tiles[2][3]!!.canMoveSouth = false
        tiles[3][0]!!.canMoveNorth = false
        tiles[3][3]!!.canMoveNorth = false
    }

    /**
     * initialises maze 2 by blocking certain paths
     */
    private fun initMaze2() {
        tiles[0][1]!!.canMoveSouth = false
        tiles[1][0]!!.canMoveSouth = false
        tiles[1][1]!!.canMoveNorth = false
        tiles[1][1]!!.canMoveEast = false
        tiles[1][2]!!.canMoveWest = false
        tiles[1][2]!!.canMoveEast = false
        tiles[1][3]!!.canMoveSouth = false
        tiles[1][3]!!.canMoveWest = false
        tiles[2][0]!!.canMoveNorth = false
        tiles[2][1]!!.canMoveEast = false
        tiles[2][2]!!.canMoveWest = false
        tiles[2][3]!!.canMoveSouth = false
        tiles[2][3]!!.canMoveNorth = false
        tiles[3][0]!!.canMoveEast = false
        tiles[3][1]!!.canMoveWest = false
        tiles[3][1]!!.canMoveEast = false
        tiles[3][2]!!.canMoveWest = false
        tiles[3][3]!!.canMoveNorth = false
    }

    /**
     * initialises maze 3 by blocking certain paths
     */
    private fun initMaze3() {
        tiles[0][1]!!.canMoveSouth = false
        tiles[0][2]!!.canMoveEast = false
        tiles[0][3]!!.canMoveWest = false
        tiles[1][0]!!.canMoveSouth = false
        tiles[1][1]!!.canMoveNorth = false
        tiles[1][1]!!.canMoveEast = false
        tiles[1][1]!!.canMoveSouth = false
        tiles[1][2]!!.canMoveWest = false
        tiles[1][3]!!.canMoveSouth = false
        tiles[2][0]!!.canMoveNorth = false
        tiles[2][1]!!.canMoveNorth = false
        tiles[2][1]!!.canMoveSouth = false
        tiles[2][2]!!.canMoveEast = false
        tiles[2][3]!!.canMoveNorth = false
        tiles[2][3]!!.canMoveWest = false
        tiles[3][0]!!.canMoveEast = false
        tiles[3][1]!!.canMoveWest = false
        tiles[3][1]!!.canMoveNorth = false
    }

    /**
     * returns the location object that is located at the point specified by currentCoords
     *
     * @return the current location as a Location object
     */
    private fun getLocation(): Location? {
        return tiles[currentCoords.y][currentCoords.x]
    }

    /**
     * adds a location to the tiles array to create game map
     *
     * @param location Location object to be added to the array
     * @param posX x co-ordinate of the position in the array
     * @param posY y co-ordinate of the position in the array
     */
    private fun addLocation(
        location: Location,
        posX: Int = (0..<mapSize).random(),
        posY: Int = (0..<mapSize).random()
    ) {
        var x = posX
        var y = posY

        // check if the proposed index has no location at it, if it doesn't, add it to the array and
        // block the required directions to contain the map. If it does contain a location, try again
        while (true) {
            if (tiles[y][x] == null) {
                tiles[y][x] = location
                if (y == 0) {
                    location.canMoveNorth = false
                }
                if (y == mapSize - 1) {
                    location.canMoveSouth = false
                }
                if (x == 0) {
                    location.canMoveWest = false
                }
                if (x == mapSize - 1) {
                    location.canMoveEast = false
                }
                println("${location.name} is at $x $y")
                break
            } else {
                x = (0..<mapSize).random()
                y = (0..<mapSize).random()
            }
        }
    }

    /**
     * changes currentCoords to update the players position
     *
     * @param direction direction of movement, (N)orth, (S)outh
     * (E)ast, or (W)est
     */
    fun move(direction: Char) {
        currentLocation = getLocation()
        // checks direction, and updates currentCoords accordingly
        when (direction) {
            'N' -> if (currentLocation!!.canMoveNorth) {
                currentCoords.y -= 1
            }

            'S' -> if (currentLocation!!.canMoveSouth) {
                currentCoords.y += 1
            }

            'E' -> if (currentLocation!!.canMoveEast) {
                currentCoords.x += 1
            }

            'W' -> if (currentLocation!!.canMoveWest) {
                currentCoords.x -= 1
            }

        }

        // update the new location's visited status
        currentLocation = getLocation()
        if (!currentLocation!!.visited) {
            currentLocation!!.visited = true
        }
    }

    /**
     * function that executes a trade if it is possible at that location
     */
    fun trade() {
        if (canTrade()) {
            inventory.add(currentLocation!!.sellingResource)
            currentLocation!!.traded = true
        }
    }

    /**
     * creates a string that has each item in the players inventory on a new line
     *
     * @return current inventory, with each item on a new line
     */
    fun printInventory(): String {
        val text = buildString {
            inventory.forEachIndexed { _, name ->
                appendLine(name)
            }
        }
        return text
    }

    /**
     * checks if the player can trade at the current location by checking if they have the correct item to trade
     *
     * @return true if they have the required item, false if they don't
     */
    fun canTrade(): Boolean {
        return currentLocation!!.wantedResource in inventory

    }

    /**
     * checks if the player has reached a win state
     */
    fun checkWin() {
        if (currentLocation == start && inventory.size == 15) {
            hasWon = true
        }
    }

    /**
     * stops game timer
     */
    fun stopTimer() {
        gameTimer.stop()
    }

    /**
     * starts game timer
     */
    fun startTimers() {
        gameTimer.start()
    }
}


/**
 * Main UI window, handles user clicks, etc.
 *
 * @param game the game state object
 */
class MainWindow(private val game: Game) {

    // setting up GUI
    val frame = JFrame("GAME")
    private val panel = JPanel().apply { layout = null }

    //creating image icons
    private val timerIcon = ImageIcon(ClassLoader.getSystemResource("images/timer.png")).scaled(100, 340)
    private val knifeIcon = ImageIcon(ClassLoader.getSystemResource("images/knife.png")).scaled(50, 70)
    private val winIcon = ImageIcon(ClassLoader.getSystemResource("images/winscreen.png")).scaled(500, 350)
    private val loseIcon = ImageIcon(ClassLoader.getSystemResource("images/losescreen.png")).scaled(500, 350)

    //creating elements to be displayed on GUI
    private var nameLabel = JLabel()
    private val descriptionLabel = JLabel()
    private val tradesLabel = JLabel()
    private val tradeButton = JButton("Trade")
    private val northButton = JButton("^")
    private val southButton = JButton("v")
    private val eastButton = JButton(">")
    private val westButton = JButton("<")
    private val timerLabel = JLabel(timerIcon)
    private val knifeLabel = JLabel(knifeIcon)
    private val winScreen = JLabel(winIcon)
    private val loseScreen = JLabel(loseIcon)
    val tickTimer = Timer(882, null)
    val checkTimer = Timer(10, null)

    //create and pass game state to child windows
    val infoWindow = MinimapWindow(this, game)
    val inventoryWindow = InventoryWindow(this, game)

    /**
     * runs on object instantiation
     *
     * sets up window, setting up things like the window layout, styles, and actions.
     */
    init {
        setupLayout()
        setupStyles()
        setupActions()
        setupWindow()
        updateUI()


    }

    /**
     * sets up layout of the window, specifying the bounds for all objects, and adds them to the panel.
     */
    private fun setupLayout() {
        // set size of panel
        panel.preferredSize = java.awt.Dimension(500, 350)

        // set bounds of all elements
        nameLabel.setBounds(30, 30, 340, 50)
        descriptionLabel.setBounds(30, 90, 340, 100)
        tradesLabel.setBounds(30, 190, 200, 100)
        tradeButton.setBounds(30, 220, 150, 40)
        northButton.setBounds(300, 150, 40, 40)
        southButton.setBounds(300, 200, 40, 40)
        eastButton.setBounds(350, 175, 40, 40)
        westButton.setBounds(250, 175, 40, 40)
        timerLabel.setBounds(395, 5, 100, 340)
        knifeLabel.setBounds(450, -62, 50, 70)
        winScreen.setBounds(0, 0, 500, 350)
        loseScreen.setBounds(0, 0, 500, 350)

        //add elements to panel
        panel.add(nameLabel)
        panel.add(descriptionLabel)
        panel.add(tradesLabel)
        panel.add(tradeButton)
        panel.add(northButton)
        panel.add(southButton)
        panel.add(eastButton)
        panel.add(westButton)
        panel.add(knifeLabel)
        panel.add(timerLabel)
        panel.add(winScreen)
        panel.add(loseScreen)
    }

    /**
     * set up styles of elements in the window, including fonts, colours and visibility
     */
    private fun setupStyles() {
        // fonts
        nameLabel.font = Font(Font.SANS_SERIF, Font.BOLD, 32)
        tradesLabel.font = Font(Font.SANS_SERIF, Font.PLAIN, 18)
        descriptionLabel.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        tradeButton.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        northButton.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        southButton.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        eastButton.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        westButton.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)

        // colours
        tradeButton.background = Color(0xcc0055)

        // visibility
        winScreen.isVisible = false
        loseScreen.isVisible = false

    }

    /**
     * defines key properties of the window
     */
    private fun setupWindow() {
        frame.isResizable = false                           // Can't resize
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE  // Exit upon window close
        frame.contentPane = panel                           // Define the main content
        frame.pack()
        frame.setLocationRelativeTo(null)                   // Centre on the screen
    }

    /**
     * sets up action listeners for timers/buttons
     */
    private fun setupActions() {
        //buttons
        tradeButton.addActionListener { handleTrade() }
        northButton.addActionListener { handleMove('N') }
        southButton.addActionListener { handleMove('S') }
        eastButton.addActionListener { handleMove('E') }
        westButton.addActionListener { handleMove('W') }
        //timers
        tickTimer.addActionListener { handleKnifeMove() }
        checkTimer.addActionListener { handleGameEndCheck() }
    }

    /**
     * Checks if a game end state has been reached, and updates the UI accordingly
     */
    private fun handleGameEndCheck() {
        game.checkWin() //check for game end

        // handle game win
        if (game.hasWon) {
            cleanWindow()
            winScreen.isVisible = true
        }

        // handle game lose
        if (game.hasLost) {
            cleanWindow()
            loseScreen.isVisible = true
        }
    }

    /**
     * clears window of all elements, and stops timers in preparation of showing a game end screen
     */
    private fun cleanWindow() {
        // clear UI
        nameLabel.isVisible = false
        descriptionLabel.isVisible = false
        tradesLabel.isVisible = false
        tradeButton.isVisible = false
        northButton.isVisible = false
        southButton.isVisible = false
        eastButton.isVisible = false
        westButton.isVisible = false
        knifeLabel.isVisible = false
        timerLabel.isVisible = false

        //stop timers
        checkTimer.stop()
        tickTimer.stop()
        game.stopTimer()
    }

    /**
     * starts UI-relevant timers
     */
    fun startTimers() {
        checkTimer.start()
        tickTimer.start()

    }

    /**
     * moves the knife on the timer
     */
    private fun handleKnifeMove() {
        val y = knifeLabel.y
        knifeLabel.setLocation(450, y + 1)
    }

    /**
     * handles trading by calling trade function in the game, then updates UI
     */
    private fun handleTrade() {
        game.trade()
        updateUI()
    }

    /**
     * handles moving by calling move function in the game, then updates UI
     */
    private fun handleMove(direction: Char) {
        game.move(direction)
        updateUI()

    }

    /**
     * updates UI so it is up-to-date with current game state
     */
    private fun updateUI() {
        //set texts
        nameLabel.text = game.currentLocation!!.name
        descriptionLabel.text = game.currentLocation!!.description
        tradesLabel.text = """<html><wrap>Wants: ${game.currentLocation!!.wantedResource}
            Selling: ${game.currentLocation!!.sellingResource}
        """.trimMargin()
        tradeButton.text = if (!game.currentLocation!!.traded) {
            if (game.canTrade()) {
                "Trade"
            } else {
                "Can't Trade"
            }
        } else {
            "Traded!"
        }

        //enable/disable buttons
        tradeButton.isEnabled = if (!game.currentLocation!!.traded) {
            game.canTrade()
        } else {
            false
        }
        eastButton.isEnabled = game.currentLocation!!.canMoveEast
        northButton.isEnabled = game.currentLocation!!.canMoveNorth
        westButton.isEnabled = game.currentLocation!!.canMoveWest
        southButton.isEnabled = game.currentLocation!!.canMoveSouth

        // Keep child dialogue window UIs up-to-date too
        infoWindow.updateUI()
        inventoryWindow.updateUI()
    }

    /**
     * shows frame
     */
    fun show() {
        frame.isVisible = true
    }


}


/**
 * Minimap UI window is a child dialogue and shows where in the
 * map the player is at all times
 *
 * @param owner the parent frame, used to position and layer the dialogue correctly
 * @param game the app state object
 */
class MinimapWindow(private val owner: MainWindow, private val game: Game) {
    private val dialog = JDialog(owner.frame, "MiniMap", false)
    private val panel = JPanel().apply { layout = null }

    private val playerIcon = ImageIcon(ClassLoader.getSystemResource("images/player.png")).scaled(25, 25)

    private val location1Label = JLabel()
    private val location2Label = JLabel()
    private val location3Label = JLabel()
    private val location4Label = JLabel()
    private val location5Label = JLabel()
    private val location6Label = JLabel()
    private val location7Label = JLabel()
    private val location8Label = JLabel()
    private val location9Label = JLabel()
    private val location10Label = JLabel()
    private val location11Label = JLabel()
    private val location12Label = JLabel()
    private val location13Label = JLabel()
    private val location14Label = JLabel()
    private val location15Label = JLabel()
    private val location16Label = JLabel()
    private val player = JLabel(playerIcon)


    init {
        setupLayout()
        setupStyles()
        setupActions()
        setupWindow()
        updateUI()
    }

    private fun setupLayout() {
        panel.preferredSize = java.awt.Dimension(340, 340)

        location1Label.setBounds(0, 0, 85, 85)
        location2Label.setBounds(85, 0, 85, 85)
        location3Label.setBounds(170, 0, 85, 85)
        location4Label.setBounds(255, 0, 85, 85)
        location5Label.setBounds(0, 85, 85, 85)
        location6Label.setBounds(85, 85, 85, 85)
        location7Label.setBounds(170, 85, 85, 85)
        location8Label.setBounds(255, 85, 85, 85)
        location9Label.setBounds(0, 170, 85, 85)
        location10Label.setBounds(85, 170, 85, 85)
        location11Label.setBounds(170, 170, 85, 85)
        location12Label.setBounds(255, 170, 85, 85)
        location13Label.setBounds(0, 255, 85, 85)
        location14Label.setBounds(85, 255, 85, 85)
        location15Label.setBounds(170, 255, 85, 85)
        location16Label.setBounds(255, 255, 85, 85)
        player.setBounds(0, 0, 25, 25)


        panel.add(location1Label)
        panel.add(location2Label)
        panel.add(location3Label)
        panel.add(location4Label)
        panel.add(location5Label)
        panel.add(location6Label)
        panel.add(location7Label)
        panel.add(location8Label)
        panel.add(location9Label)
        panel.add(location10Label)
        panel.add(location11Label)
        panel.add(location12Label)
        panel.add(location13Label)
        panel.add(location14Label)
        panel.add(location15Label)
        panel.add(location16Label)
        panel.add(player)


    }

    private fun setupStyles() {
        location1Label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3))
        location2Label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3))
        location3Label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3))
        location4Label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3))
        location5Label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3))
        location6Label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3))
        location7Label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3))
        location8Label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3))
        location9Label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3))
        location10Label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3))
        location11Label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3))
        location12Label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3))
        location13Label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3))
        location14Label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3))
        location15Label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3))
        location16Label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3))
    }

    private fun setupWindow() {
        dialog.isResizable = false                              // Can't resize
        dialog.defaultCloseOperation = JDialog.HIDE_ON_CLOSE    // Hide upon window close
        dialog.contentPane = panel                              // Main content panel
        dialog.pack()
    }

    private fun setupActions() {

    }


    fun updateUI() {
        // Use game properties to display state
        val x = game.currentCoords.x
        val newX = (x + 1) * 85
        val y = game.currentCoords.x
        val newY = (y + 1) * 85
        player.setLocation(newX, newY)

    }

    fun show() {
        val ownerBounds = owner.frame.bounds          // get location of the main window
        dialog.setLocation(                           // Position next to main window
            ownerBounds.x + ownerBounds.width + 10,
            ownerBounds.y
        )
        dialog.isVisible = true
    }
}

/**
 * Inventory Window class. Shows Players current Inventory.
 *
 * @param owner parent frame of this Window, used to position and layer the window correctly
 * @param game the game state
 */
class InventoryWindow(private val owner: MainWindow, private val game: Game) {
    private val dialog = JDialog(owner.frame, "Inventory", false)
    private val panel = JPanel().apply { layout = null }
    private val inventoryLabel = JLabel()

    /**
     * runs on object instantiation, sets up window for display
     */
    init {
        setupLayout()
        setupStyles()
        setupWindow()
        updateUI()
    }

    /**
     * sets up layout of window and elements
     */
    private fun setupLayout() {
        panel.preferredSize = java.awt.Dimension(200, 450)
        inventoryLabel.setBounds(5, 5, 190, 430)
        inventoryLabel.verticalAlignment = JLabel.TOP
        inventoryLabel.horizontalAlignment = JLabel.LEFT

        panel.add(inventoryLabel)
    }

    /**
     * sets up style of elements
     */
    private fun setupStyles() {
        inventoryLabel.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
    }

    /**
     * defines key properties of the window
     */
    private fun setupWindow() {
        dialog.isResizable = false                              // Can't resize
        dialog.defaultCloseOperation = JDialog.HIDE_ON_CLOSE    // Hide upon window close
        dialog.contentPane = panel                              // Main content panel
        dialog.pack()
    }

    /**
     * updates UI to show current game state
     */
    fun updateUI() {
        // Use game properties to display state
        val text = game.printInventory()
        inventoryLabel.text = "<html>${text.replace("\n", "<br>")}</html>"
    }

    /**
     * shows panel, and sets location relevant to main UI window
     */
    fun show() {
        val ownerBounds = owner.frame.bounds          // get location of the main window
        dialog.setLocation(                           // Position next to main window
            ownerBounds.x - ownerBounds.width + 190,
            ownerBounds.y
        )
        dialog.isVisible = true
    }
}

/**
 * Intro UI window handles the introduction and instructions of the game
 * showing the user how to play, and the premise of the game
 *
 *@param game the game state
 * @param window main window state
 */

class IntroWindow(private val game: Game, private val window: MainWindow) {
    // setup window elements
    private val frame = JFrame("INSTRUCTIONS")
    private val panel = JPanel().apply { layout = null }

    private var infoLabel = JLabel()
    private var continueButton = JButton("Continue")
    private var startButton = JButton("Start")

    /**
     * runs on instantiation, sets up window for use
     */
    init {
        setupLayout()
        setupStyles()
        setupWindow()
        setupActions()

    }

    /**
     * sets up layout of window, and bounds of elements
     */
    private fun setupLayout() {
        panel.preferredSize = java.awt.Dimension(400, 250)


        infoLabel.setBounds(5, 5, 390, 200)
        continueButton.setBounds(260, 200, 100, 40)
        startButton.setBounds(260, 200, 90, 40)

        panel.add(infoLabel)
        panel.add(continueButton)
        panel.add(startButton)

    }

    /**
     * defines key properties of the window
     */
    private fun setupWindow() {
        frame.isResizable = false                           // Can't resize
        frame.defaultCloseOperation = JFrame.DO_NOTHING_ON_CLOSE  // cant close
        frame.contentPane = panel                           // Define the main content
        frame.pack()
        frame.setLocationRelativeTo(null)
    }

    /**
     * sets up fonts and colours of elements
     */
    private fun setupStyles() {
        // fonts
        infoLabel.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        continueButton.font = Font(Font.SANS_SERIF, Font.PLAIN, 15)
        startButton.font = Font(Font.SANS_SERIF, Font.PLAIN, 15)

        //colours
        continueButton.background = Color.DARK_GRAY
        startButton.background = Color.DARK_GRAY

    }

    /**
     * sets up action listeners for buttons
     */
    private fun setupActions() {
        continueButton.addActionListener { showContext() }
        startButton.addActionListener {
            frame.isVisible = false
            window.show()
            window.infoWindow.show()
            window.inventoryWindow.show()
            startTimers()

        }

    }

    /**
     * starts all timers for game
     */
    private fun startTimers() {
        window.startTimers()
        game.startTimers()
    }

    /**
     * shows instructions to player, and shows continue button to move on to game context
     */
    private fun showInstructions() {
        startButton.isVisible = false
        infoLabel.text = """<html><wrap>To move, click the arrow button that corresponds to the direction you
            wish to move. To trade, you need to find a square that wants a resource that you have available
            to trade. 
        """.trimMargin()
        infoLabel.isVisible = true
        continueButton.isVisible = true

    }

    /**
     * shows game context to player, and shows start button to begin the game
     */
    private fun showContext() {
        continueButton.isVisible = false
        infoLabel.text =
            """<html><wrap>The Evil man has stolen your precious Cat, and will kill it if you do not scour the
            land to find the 15 resources he wants for his new house and come back in 5 minutes.
        """.trimMargin()
        infoLabel.isVisible = true
        startButton.isVisible = true
    }

    /**
     * shows frame and instructions
     */
    fun start() {
        frame.isVisible = true
        showInstructions()
    }

}