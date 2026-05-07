/**
 * =====================================================================
 * Programming Project for NCEA Level 3, Standard 91906
 * ---------------------------------------------------------------------
 * Project Name:   Save The Cat
 * Project Author: Aaron Macintosh
 * GitHub Repo:    https://github.com/waimea-ammacintosh/kotlin-lvl-3-programming-assesment
 * ---------------------------------------------------------------------
 * =====================================================================
 */

import com.formdev.flatlaf.themes.FlatMacDarkLaf
import java.awt.Color
import java.awt.Font
import java.awt.Point
import javax.swing.Timer
import javax.swing.*
import kotlin.time.DurationUnit
import kotlin.time.TimeSource
import kotlin.time.TimeMark

/**
 * Application entry point.
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
 * @param name location name.
 * @param description location description.
 * @param wantedResource the name of the resource wanted at the location.
 * @param sellingResource the name of the resource sold at the location.
 * @param traded has the location been traded at yet.
 * @param canMoveEast can the player move East at this location.
 * @param canMoveWest can the player move West at this location.
 * @param canMoveNorth can the player move North at this location.
 * @param canMoveSouth can the player move South at this location.
 *
 */
data class Location(
    val name: String,
    val description: String,
    val wantedResource: String,
    val sellingResource: String,
    var traded: Boolean = false,
    var canMoveNorth: Boolean = true,
    var canMoveSouth: Boolean = true,
    var canMoveEast: Boolean = true,
    var canMoveWest: Boolean = true
)

/**
 * Game class, stores data relating to the state of the game, to pass on to the Main Window.
 *
 */
class Game {
    //map properties/list
    val mapSize = 4
    val tiles: Array<Array<Location?>> = Array(mapSize) { Array(mapSize) { null } }

    //private properties
    private val items = mutableListOf<String>()
    private val gameTimer = Timer(300000, null)
    private var timerStart: TimeMark? = null


    //inventory list
    val inventory = mutableListOf<String>()

    //variables
    var currentCoords: Point
    var currentLocation: Location?
    var hasWon = false
    var hasLost = false
    var totalTime: Int? = null
    var score: Int = 0

    //instantiate all the location objects
    private val start = Location("Start", "The starting square. 'Come back here with your 15 resources to save your cat' - evil man.", "Everything", "Cat")
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
    private val composter = Location("Composter", "A big Pile of Compost", "Herbs", "Compost")
    private val garden = Location("Garden", "A vast garden", "Compost", "Carrots")
    private val mine = Location("Mine", "A deep mine", "Carrots", "Coal")

    /**
     * runs on game object instantiation.
     *
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


        //add locations to tiles list
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

        //set current location to start
        currentCoords = Point(0, 0)
        currentLocation = getLocation()

        //adds one of three random mazes to the area by blocking certain paths
        val mazeNum = (1..3).random()
        when (mazeNum) {
            1 -> {
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

            2 -> {
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

            3 -> {
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
        }

        //add action listener to timer
        gameTimer.addActionListener { hasLost = true }
    }

    /**
     * returns the location object that is located at the point specified by currentCoords.
     *
     * @return the current location as a Location object.
     */
    private fun getLocation(): Location? {
        return tiles[currentCoords.y][currentCoords.x]
    }

    /**
     * adds a location to the tiles array to create game map.
     *
     * @param location Location object to be added to the array.
     * @param posX x co-ordinate of the position in the array (generated randomly unless specified).
     * @param posY y co-ordinate of the position in the array (generated randomly unless specified).
     */
    private fun addLocation(
        location: Location,
        posX: Int = (0..<mapSize).random(),
        posY: Int = (0..<mapSize).random()
    ) {
        var x = posX
        var y = posY

        //check if the proposed index has no location at it, if it doesn't, add it to the array and
        //block the required directions to contain the map. If it does contain a location, try again
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
                break
            } else {
                x = (0..<mapSize).random()
                y = (0..<mapSize).random()
            }
        }
    }

    /**
     * changes currentCoords to update the players position.
     *
     * @param direction direction of movement, (N)orth, (S)outh
     * (E)ast, or (W)est.
     */
    fun move(direction: Char) {
        currentLocation = getLocation()
        //checks direction, and updates currentCoords accordingly, can't have an invalid input, as
        //invalid direction buttons are disabled.
        when (direction) {
            'N' ->  currentCoords.y -= 1

            'S' -> currentCoords.y += 1

            'E' -> currentCoords.x += 1

            'W' -> currentCoords.x -= 1

        }

        //set new current location
        currentLocation = getLocation()
    }

    /**
     * Executes a trade if it is possible at that location.
     */
    fun trade() {
        if (canTrade()) {
            inventory.add(currentLocation!!.sellingResource)
            currentLocation!!.traded = true
        }
    }

    /**
     * creates a string that has each item in the players inventory on a new line.
     *
     * @return current inventory, with each item on a new line.
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
     * checks if the player can trade at the current location by checking if they have the correct item to trade.
     *
     * @return true if they have the required item, false if they don't.
     */
    fun canTrade(): Boolean {
        return if (inventory.size == 15) {
            false
        } else {
            currentLocation!!.wantedResource in inventory
        }

    }

    /**
     * checks if the player has reached a win state.
     */
    fun checkWin() {
        if (currentLocation == start && inventory.size == 15) {
            hasWon = true
        }
    }

    /**
     * stops game timer and calculates how long timer has been running for score.
     */
    fun stopTimer() {
        gameTimer.stop()
        //calculate score as gameTimer length - elapsed time in milliseconds
        totalTime = timerStart?.elapsedNow()?.toInt(DurationUnit.MILLISECONDS)
        score = gameTimer.delay - totalTime!!
    }

    /**
     * starts game timer and notes start time for scoring.
     */
    fun startTimer() {
        timerStart = TimeSource.Monotonic.markNow()
        gameTimer.start()
    }
}


/**
 * Main UI window, handles user clicks, etc.
 *
 * @param game the game state object.
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
    private val scoreLabel = JLabel()
    private val tradeButton = JButton("Trade")
    private val northButton = JButton("^")
    private val southButton = JButton("v")
    private val eastButton = JButton(">")
    private val westButton = JButton("<")
    private val timerLabel = JLabel(timerIcon)
    private val knifeLabel = JLabel(knifeIcon)
    private val winScreen = JLabel(winIcon)
    private val loseScreen = JLabel(loseIcon)

    // create timers
    val tickTimer = Timer(882, null)
    val checkTimer = Timer(10, null)

    //create and pass game state to child windows
    val minimapWindow = MinimapWindow(this, game)
    val inventoryWindow = InventoryWindow(this, game)

    /**
     * runs on object instantiation.
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

        // set bounds/alignments of all elements
        nameLabel.setBounds(30, 30, 340, 50)
        descriptionLabel.setBounds(30, 90, 340, 100)
        tradesLabel.setBounds(30, 190, 170, 100)
        scoreLabel.setBounds(30, 300,300,40)
        scoreLabel.horizontalAlignment = JLabel.LEFT
        tradeButton.setBounds(30, 270, 150, 40)
        northButton.setBounds(300, 180, 40, 40)
        southButton.setBounds(300, 230, 40, 40)
        eastButton.setBounds(350, 205, 40, 40)
        westButton.setBounds(250, 205, 40, 40)
        timerLabel.setBounds(395, 5, 100, 340)
        knifeLabel.setBounds(450, -62, 50, 70)
        winScreen.setBounds(0, 0, 500, 350)
        loseScreen.setBounds(0, 0, 500, 350)

        //add elements to panel
        panel.add(nameLabel)
        panel.add(descriptionLabel)
        panel.add(tradesLabel)
        panel.add(scoreLabel)
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
     * set up styles of elements in the window, including fonts, colours and visibility.
     */
    private fun setupStyles() {
        // fonts
        nameLabel.font = Font(Font.SANS_SERIF, Font.BOLD, 32)
        tradesLabel.font = Font(Font.SANS_SERIF, Font.PLAIN, 18)
        descriptionLabel.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        scoreLabel.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        tradeButton.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        northButton.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        southButton.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        eastButton.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        westButton.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)

        // colours
        tradeButton.background = Color(0x013220)

        // visibility
        winScreen.isVisible = false
        loseScreen.isVisible = false
        scoreLabel.isVisible = false

    }

    /**
     * defines key properties of the window.
     */
    private fun setupWindow() {
        frame.isResizable = false                           // Can't resize
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE  // Exit upon window close
        frame.contentPane = panel                           // Define the main content
        frame.pack()
        frame.setLocationRelativeTo(null)                   // Centre on the screen
    }

    /**
     * sets up action listeners for timers/buttons.
     */
    private fun setupActions() {
        //button actions
        tradeButton.addActionListener { handleTrade() }
        northButton.addActionListener { handleMove('N') }
        southButton.addActionListener { handleMove('S') }
        eastButton.addActionListener { handleMove('E') }
        westButton.addActionListener { handleMove('W') }
        //timer actions
        tickTimer.addActionListener { handleKnifeMove() }
        checkTimer.addActionListener { handleGameEndCheck() }
    }

    /**
     * Checks if a game end state has been reached, and updates the UI accordingly.
     */
    private fun handleGameEndCheck() {
        game.checkWin() //check for game end

        // handle game win
        if (game.hasWon) {
            cleanWindow()
            scoreLabel.text = "Score: ${game.score}"
            winScreen.isVisible = true
            scoreLabel.isVisible = true
        }

        // handle game lose
        if (game.hasLost) {
            cleanWindow()
            scoreLabel.text = "Score: 0"
            loseScreen.isVisible = true
            scoreLabel.isVisible = true
        }
    }

    /**
     * clears window of all elements, and stops timers in preparation of showing a game end screen.
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
     * starts UI-relevant timers.
     */
    fun startTimers() {
        checkTimer.start()
        tickTimer.start()

    }

    /**
     * moves the knife down the screen.
     */
    private fun handleKnifeMove() {
        val y = knifeLabel.y
        knifeLabel.setLocation(450, y + 1)
    }

    /**
     * handles trading by calling trade function in the game, then updates UI.
     */
    private fun handleTrade() {
        game.trade()
        updateUI()
    }

    /**
     * handles moving by calling move function in the game, then updates UI.
     *
     * @param direction direction of movement.
     */
    private fun handleMove(direction: Char) {
        game.move(direction)
        updateUI()

    }

    /**
     * updates UI so it is up-to-date with current game state.
     */
    private fun updateUI() {
        //set texts
        nameLabel.text = "<html><wrap>${game.currentLocation!!.name}"
        descriptionLabel.text = "<html><wrap>${game.currentLocation!!.description}"
        tradesLabel.text = """<html>Wants: ${game.currentLocation!!.wantedResource}<br>
            Selling: ${game.currentLocation!!.sellingResource}
        </html>""".trimMargin()
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
        minimapWindow.updateUI()
        inventoryWindow.updateUI()
    }

    /**
     * shows frame.
     */
    fun show() {
        frame.isVisible = true
    }


}


/**
 * Minimap UI window is a child dialogue and shows where in the
 * map the player is at all times.
 *
 * @param owner the parent frame, used to position and layer the dialogue correctly
 * @param game the app state object
 */
class MinimapWindow(private val owner: MainWindow, private val game: Game) {
    //create panel
    private val dialog = JDialog(owner.frame, "MiniMap", false)
    private val panel = JPanel().apply { layout = null }
    //create elements
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


    /**
     * runs on object instantiation, sets up window.
     */
    init {
        setupLayout()
        setupStyles()
        setupWindow()
        updateUI()
    }

    /**
     * sets bounds for elements, and window.
     */
    private fun setupLayout() {
        //set panel size
        panel.preferredSize = java.awt.Dimension(340, 340)
        //set label bounds
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

        //and add them to the panel
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

    /**
     * sets up styles for elements on the GUI panel.
     */
    private fun setupStyles() {
        //set borders for location labels
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

    /**
     * sets up important aspects of the window.
     */
    private fun setupWindow() {
        dialog.isResizable = false                              // Can't resize
        dialog.defaultCloseOperation = JDialog.HIDE_ON_CLOSE    // Hide upon window close
        dialog.contentPane = panel                              // Main content panel
        dialog.pack()
    }

    /**
     * updates UI to reflect game state.
     */
    fun updateUI() {

        // Use game properties to display state
        val x = game.currentCoords.x
        val playerX = (85 * x) + 30

        val y = game.currentCoords.y
        val playerY = (85 * y) + 30

        player.setLocation(playerX, playerY)
    }

    /**
     * shows window, and sets relative position to owner window.
     */
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
    //window/
    private val dialog = JDialog(owner.frame, "Inventory", false)
    private val panel = JPanel().apply { layout = null }
    private val inventoryLabel = JLabel()

    /**
     * runs on object instantiation, sets up window for display.
     */
    init {
        setupLayout()
        setupStyles()
        setupWindow()
        updateUI()
    }

    /**
     * sets up layout of window and elements.
     */
    private fun setupLayout() {
        panel.preferredSize = java.awt.Dimension(230, 490)
        inventoryLabel.setBounds(5, 5, 220, 480)
        inventoryLabel.verticalAlignment = JLabel.TOP
        inventoryLabel.horizontalAlignment = JLabel.LEFT

        panel.add(inventoryLabel)
    }

    /**
     * sets up style of elements.
     */
    private fun setupStyles() {
        inventoryLabel.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
    }

    /**
     * defines key properties of the window.
     */
    private fun setupWindow() {
        dialog.isResizable = false                              // Can't resize
        dialog.defaultCloseOperation = JDialog.HIDE_ON_CLOSE    // Hide upon window close
        dialog.contentPane = panel                              // Main content panel
        dialog.pack()
    }

    /**
     * updates UI to show current game state.
     */
    fun updateUI() {
        // Use game properties to display state
        val text = game.printInventory()
        inventoryLabel.text = "<html>${text.replace("\n", "<br>")}<br>Items Gathered: ${game.inventory.size}<br> ${if (game.inventory.size == 15){"ALL ITEMS GATHERED"} else{""}}</html>"
    }

    /**
     * shows panel, and sets location relevant to main UI window.
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
 * showing the user how to play, and the premise of the game.
 *
 *@param game the game state.
 * @param window main window state.
 */

class IntroWindow(private val game: Game, private val window: MainWindow) {
    // setup window elements and window
    private val frame = JFrame("INSTRUCTIONS")
    private val panel = JPanel().apply { layout = null }

    private var infoLabel = JLabel("",JLabel.CENTER)
    private var continueButton = JButton("Continue")
    private var startButton = JButton("Start")

    /**
     * runs on instantiation, sets up window for use.
     */
    init {
        setupLayout()
        setupStyles()
        setupWindow()
        setupActions()

    }

    /**
     * sets up layout of window, and bounds of elements.
     */
    private fun setupLayout() {
        //panel dimensions
        panel.preferredSize = java.awt.Dimension(410, 250)
        //element bounds
        infoLabel.setBounds(10, 10, 390, 120)
        continueButton.setBounds(260, 200, 100, 40)
        startButton.setBounds(260, 200, 90, 40)
        //add them to the panel
        panel.add(infoLabel)
        panel.add(continueButton)
        panel.add(startButton)

    }

    /**
     * defines key properties of the window.
     */
    private fun setupWindow() {
        frame.isResizable = false                           // Can't resize
        frame.defaultCloseOperation = JFrame.DO_NOTHING_ON_CLOSE  // cant close
        frame.contentPane = panel                           // Define the main content
        frame.pack()
        frame.setLocationRelativeTo(null)
    }

    /**
     * sets up fonts and colours of elements.
     */
    private fun setupStyles() {
        // fonts
        infoLabel.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER)
        infoLabel.setVerticalAlignment(SwingConstants.CENTER)
        continueButton.font = Font(Font.SANS_SERIF, Font.PLAIN, 15)
        startButton.font = Font(Font.SANS_SERIF, Font.PLAIN, 15)

        //colours
        continueButton.background = Color.DARK_GRAY
        startButton.background = Color.DARK_GRAY

    }

    /**
     * sets up action listeners for buttons.
     */
    private fun setupActions() {
        continueButton.addActionListener { showContext() }
        startButton.addActionListener {
            //starts main game by starting timers and showing/hiding relevant windows
            frame.isVisible = false
            window.show()
            window.minimapWindow.show()
            window.inventoryWindow.show()
            startTimers()

        }

    }

    /**
     * starts all timers for game.
     */
    private fun startTimers() {
        window.startTimers()
        game.startTimer()
    }

    /**
     * shows instructions to player, and shows continue button to move on to game context.
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
     * shows game context to player, and shows start button to begin the game.
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