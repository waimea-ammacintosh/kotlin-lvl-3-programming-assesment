import com.formdev.flatlaf.themes.FlatMacDarkLaf
import java.awt.Color
import java.awt.Font
import java.util.Random
import javax.swing.*

/**
 * Application entry point
 */
fun main() {
    FlatMacDarkLaf.setup()          // Initialise the LAF

    val app = App()                 // Get an app state object
    val window = MainWindow(app)    // Spawn the UI, passing in the app state

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
class MainWindow(val app: App) {
    val frame = JFrame("WINDOW TITLE")
    private val panel = JPanel().apply { layout = null }

    private val titleLabel = JLabel("APP TITLE")

    private val infoLabel = JLabel()
    private val clickButton = JButton("Click Me!")
    private val infoButton = JButton("Info")

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

        titleLabel.setBounds(30, 30, 340, 30)
        infoLabel.setBounds(30, 90, 340, 30)
        clickButton.setBounds(30, 150, 240, 40)
        infoButton.setBounds(300, 150, 70, 40)

        panel.add(titleLabel)
        panel.add(infoLabel)
        panel.add(clickButton)
        panel.add(infoButton)
    }

    private fun setupStyles() {
        titleLabel.font = Font(Font.SANS_SERIF, Font.BOLD, 32)
        infoLabel.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)

        clickButton.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        clickButton.background = Color(0xcc0055)

        infoButton.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
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
        infoLabel.text = "User ${app.name} has ${app.score} points"

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
        panel.preferredSize = java.awt.Dimension(340, 300)

        location1Label.setBounds(0, 0, 75, 75)


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
        resetButton.addActionListener { handleResetClick() }
    }

    private fun handleResetClick() {
        app.resetScore()    // Update the app state
        owner.updateUI()    // Update the UI to reflect this, via the main window
    }

    fun updateUI() {
        // Use app properties to display state
        infoLabel.text = "<html>User: ${app.name}<br>Score: ${app.score} points"

        resetButton.isEnabled = app.score > 0
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
    val tiles: Array<Array<Location?>> = Array(4) { Array(4) { null } }
) {

    init {
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
    }

    private fun addLocation(location: Location) {
        while (true) {
            val randX = (0..3).random()
            val randY = (0..3).random()
            if (((randX and randY) != 0) && (tiles[randX][randY] == null)) {
                tiles[randX][randY] = location
                break
            }
        }
    }

}

class Location(
    val name: String,
    val description: String,
    val wantedResource: String,
    val sellingResource: String
) {

}