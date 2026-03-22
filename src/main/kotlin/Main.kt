import com.formdev.flatlaf.themes.FlatMacDarkLaf
import java.awt.Color
import java.awt.Font
import javax.swing.*

/**
 * Application entry point
 */
fun main() {
    FlatMacDarkLaf.setup()                // Initialise the LAF

    val app = App()                       // Get an app state object
    val game = Game()                     // Instantiate game object
    val window = MainWindow(app, game)    // Spawn the UI, passing in the app state

    SwingUtilities.invokeLater { window.show() }
}


/**
 * Manage app state
 *
 * @property name the user's name
 * @property score the points earned
 */
class App {
    var name = "Test"
    var score = 0

    fun scorePoints(points: Int) {
        score += points
    }

    fun resetScore() {
        score = 0
    }

    fun maxScoreReached(): Boolean {
        return score >= 10000
    }
}


/**
 * Main UI window, handles user clicks, etc.
 *
 * @param app the app state object
 */
class MainWindow(val app: App, val game: Game, val currentLocation: Location? = game.currentLocation) {

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

    private val infoWindow = InfoWindow(this, app)      // Pass app state to dialog too

    init {
        setupLayout()
        setupStyles()
        setupActions()
        setupWindow()
        updateUI()
    }

    private fun setupLayout() {
        panel.preferredSize = java.awt.Dimension(400, 220)

        nameLabel.setBounds(30, 30, 340, 30)
        descriptionLabel.setBounds(30, 90, 340, 30)
        tradesLabel.setBounds(100, 110, 100, 100)
        tradeButton.setBounds(30, 150, 100, 40)
        northButton.setBounds(300, 150, 40, 40)
        southButton.setBounds(300, 200, 40, 40)
        eastButton.setBounds(250, 175, 40, 40)
        westButton.setBounds(350, 175, 40, 40)

        panel.add(nameLabel)
        panel.add(descriptionLabel)
        panel.add(tradeButton)
        panel.add(northButton)
        panel.add(southButton)
        panel.add(eastButton)
        panel.add(westButton)
    }

    private fun setupStyles() {
        nameLabel.font = Font(Font.SANS_SERIF, Font.BOLD, 32)
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
        clickButton.addActionListener { handleMainClick() }
        infoButton.addActionListener { handleInfoClick() }
    }

    private fun handleMainClick() {
        app.scorePoints(1000)       // Update the app state
        updateUI()                  // Update this window UI to reflect this
    }

    private fun handleInfoClick() {
        infoWindow.show()
    }

    fun updateUI() {
        nameLabel.text = currentLocation!!.name
        
        if (app.maxScoreReached()) {
            clickButton.text = "No More!"
            clickButton.isEnabled = false
        } else {
            clickButton.text = "Click Me!"
            clickButton.isEnabled = true
        }

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
 * @param app the app state object
 */
class InfoWindow(val owner: MainWindow, val app: App) {
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

    private fun handleResetClick() {
        app.resetScore()    // Update the app state
        owner.updateUI()    // Update the UI to reflect this, via the main window
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

class Game(
    val tiles: Array<Array<Location?>> = Array(4) { Array(4) { null } },
    var currentLocation: Location? = null
) {

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

        tiles[0][0] = start
        start.currentLocation = true
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

        currentLocation = start
    }

    private fun addLocation(location: Location) {
        while (true) {
            val randX = (0..3).random()
            val randY = (0..3).random()
            if (((randX and randY) != 0) && (tiles[randX][randY] == null)) {
                tiles[randX][randY] = location
                if (randX == 0) {
                    location.canMoveWest = false
                }
                if (randX == 3) {
                    location.canMoveEast = false
                }
                if (randY == 0) {
                    location.canMoveNorth = false
                }
                if (randY == 3) {
                    location.canMoveSouth = false
                }
                break
            }
        }
    }

    fun move(direction: Char) {

    }

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
    var canMoveWest: Boolean = true,
    var currentLocation: Boolean = false
) {

    fun move(direction: Char): Boolean {
        when (direction) {
            'N' -> if (canMoveNorth) {
                return true
            }

            'S' -> if (canMoveSouth) {
                return true
            }

            'E' -> if (canMoveEast) {
                return true
            }

            'W' -> if (canMoveWest) {
                return true
            }
        }
        return false

    }

}