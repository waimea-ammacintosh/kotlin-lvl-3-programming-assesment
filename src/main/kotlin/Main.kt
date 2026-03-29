import com.formdev.flatlaf.themes.FlatMacDarkLaf
import java.awt.Color
import java.awt.Font
import java.awt.Point
import javax.swing.*

/**
 * Application entry point
 */
fun main() {
    FlatMacDarkLaf.setup()                // Initialise the LAF
    val game = Game()                     // Get a game state object
    val window = MainWindow(game)    // Spawn the UI, passing in the game state
    val minimap = InfoWindow(window, game)
    SwingUtilities.invokeLater { window.show() }
    SwingUtilities.invokeLater { minimap.show() }

}

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

class Game {
    val mapSize = 4
    val tiles: Array<Array<Location?>> = Array(mapSize) { Array(mapSize) { null } }
    var currentCoords: Point
    var currentLocation: Location?

    init {
        val start = Location("Start", "The starting square", "None", "None")
        val forest = Location("Forest", "A dark forest", "Coal", "Wood")
        val farm = Location("Farm", "An old farm", "Wood", "Meat")
        val castle = Location("Castle", "A large Castle", "Meat", "Torch")
        val cave = Location("Cave", "A dark cave", "Torch", "Stone")
        val road = Location("Road", "A stony Road", "Stone", "Coins")
        val hall = Location("Hall", "A big Hall", "Coins", "Paper")
        val postOffice = Location("Post Office", "The post office", "Paper", "Bag")
        val huntersHouse = Location("Hunters House", "The house of the Hunter", "Bag", "Bow")
        val armory = Location("Armory", "The Royal Armory", "Bow", "Armor")
        val knightsHouse = Location("Knight's House", "The house of the local Knight", "Armor", "Tapestry")
        val museum = Location("Museum", "A large museum", "Tapestry", "Fossil")
        val apothecary = Location("Apothecary", "An apothecary", "Fossil", "Herbs")
        val composter = Location("Composter", "A big Compost Pile", "Herbs", "Compost")
        val garden = Location("Garden", "A large garden", "Compost", "Carrots")
        val mine = Location("Mine", "A deep mine", "Carrots", "Coal")


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

        currentCoords = Point(0, 0)
        currentLocation = getLocation()
    }

    private fun getLocation(): Location? {
        return tiles[currentCoords.y][currentCoords.x]
    }

    private fun addLocation(
        location: Location,
        posX: Int = (0..<mapSize).random(),
        posY: Int = (0..<mapSize).random()
    ) {
        var x = posX
        var y = posY

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
                println("${location.name} is at $x, $y, Move North: ${location.canMoveNorth}, Move South: ${location.canMoveSouth}, Move East: ${location.canMoveEast}, Move West: ${location.canMoveWest}")
                break
            } else {
                x = (0..<mapSize).random()
                y = (0..<mapSize).random()
            }
        }
    }

    fun move(direction: Char) {
        currentLocation = getLocation()
        println("Initial:${currentCoords.x} ${currentCoords.y}")
        println("        ${currentLocation!!.name}")
        println("        $direction")
        println()

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

        currentLocation = getLocation()
        println("Final:${currentCoords.x} ${currentCoords.y}")
        println("      ${currentLocation!!.name}")
    }

    fun trade() {
        println("Traded")
    }

}


/**
 * Main UI window, handles user clicks, etc.
 *
 * @param game the game state object
 */
class MainWindow(val game: Game) {

    val frame = JFrame("GAME")
    private val panel = JPanel().apply { layout = null }

    private var nameLabel = JLabel()

    private val descriptionLabel = JLabel()
    private val tradesLabel = JLabel()
    private val tradeButton = JButton("Trade")
    private val northButton = JButton("^")
    private val southButton = JButton("v")
    private val eastButton = JButton(">")
    private val westButton = JButton("<")

    private val infoWindow = InfoWindow(this, game)      // Pass game state to dialog too

    init {
        setupLayout()
        setupStyles()
        setupActions()
        setupWindow()
        updateUI()

    }

    private fun setupLayout() {
        panel.preferredSize = java.awt.Dimension(400, 250)

        nameLabel.setBounds(30, 30, 340, 50)
        descriptionLabel.setBounds(30, 90, 340, 30)
        tradesLabel.setBounds(30, 110, 150, 100)
        tradeButton.setBounds(30, 190, 100, 40)
        northButton.setBounds(300, 150, 40, 40)
        southButton.setBounds(300, 200, 40, 40)
        eastButton.setBounds(350, 175, 40, 40)
        westButton.setBounds(250, 175, 40, 40)

        panel.add(nameLabel)
        panel.add(descriptionLabel)
        panel.add(tradesLabel)
        panel.add(tradeButton)
        panel.add(northButton)
        panel.add(southButton)
        panel.add(eastButton)
        panel.add(westButton)
    }

    private fun setupStyles() {
        nameLabel.font = Font(Font.SANS_SERIF, Font.BOLD, 32)
        tradesLabel.font = Font(Font.SANS_SERIF, Font.PLAIN, 18)
        descriptionLabel.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)

        tradeButton.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        tradeButton.background = Color(0xcc0055)

        northButton.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        southButton.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        eastButton.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        westButton.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)

    }

    private fun setupWindow() {
        frame.isResizable = false                           // Can't resize
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE  // Exit upon window close
        frame.contentPane = panel                           // Define the main content
        frame.pack()
        frame.setLocationRelativeTo(null)                   // Centre on the screen
    }

    private fun setupActions() {
        tradeButton.addActionListener { handleTrade() }
        northButton.addActionListener { handleMove('N') }
        southButton.addActionListener { handleMove('S') }
        eastButton.addActionListener { handleMove('E') }
        westButton.addActionListener { handleMove('W') }
    }

    private fun handleTrade() {
        game.trade()
        updateUI()
    }

    private fun handleMove(direction: Char) {
        game.move(direction)
        updateUI()

    }

    fun updateUI() {
        nameLabel.text = game.currentLocation!!.name
        descriptionLabel.text = game.currentLocation!!.description
        tradesLabel.text = """<html><wrap>Wants: ${game.currentLocation!!.wantedResource}
            Selling: ${game.currentLocation!!.sellingResource}
        """.trimMargin()
        eastButton.isEnabled = game.currentLocation!!.canMoveEast
        northButton.isEnabled = game.currentLocation!!.canMoveNorth
        westButton.isEnabled = game.currentLocation!!.canMoveWest
        southButton.isEnabled = game.currentLocation!!.canMoveSouth

        infoWindow.updateUI()       // Keep child dialog window UI up-to-date too
    }

    fun show() {
        frame.isVisible = true
    }
}


/**
 * Info UI window is a child dialog and shows how the
 * app state can be shown / updated from multiple places
 *
 * @param owner the parent frame, used to position and layer the dialog correctly
 * @param game the app state object
 */
class InfoWindow(val owner: MainWindow, val game: Game) {
    private val dialog = JDialog(owner.frame, "MiniMap", false)
    private val panel = JPanel().apply { layout = null }

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
    private val player = JLabel()


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
        location4Label.setBounds(225, 0, 85, 85)
        location5Label.setBounds(0, 85, 85, 85)
        location6Label.setBounds(85, 85, 85, 85)
        location7Label.setBounds(170, 85, 85, 85)
        location8Label.setBounds(225, 85, 85, 85)
        location9Label.setBounds(0, 170, 85, 85)
        location10Label.setBounds(85, 170, 85, 85)
        location11Label.setBounds(170, 170, 85, 85)
        location12Label.setBounds(225, 170, 85, 85)
        location13Label.setBounds(0, 225, 85, 85)
        location14Label.setBounds(85, 225, 85, 85)
        location15Label.setBounds(170, 225, 85, 85)
        location16Label.setBounds(225, 225, 85, 85)



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


    }

    private fun setupStyles() {
        location1Label.font = Font(Font.SANS_SERIF, Font.PLAIN, 16)
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
        // Use app properties to display state

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