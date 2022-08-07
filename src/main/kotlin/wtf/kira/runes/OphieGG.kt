package wtf.kira.runes

import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.layout.Region
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import wtf.kira.runes.containers.AppRoot
import wtf.kira.runes.containers.ConsoleStage
import wtf.kira.runes.containers.NotificationStage
import wtf.kira.runes.utils.Constants
import wtf.kira.runes.utils.LeagueUtils
import wtf.kira.runes.utils.Resources
import wtf.kira.runes.utils.Utils
import java.net.URL
import kotlin.system.exitProcess

class OphieGG: Application() {

    companion object {

        val backgroundRegion = Region()

        val root = AppRoot()

        private val notificationStage = NotificationStage()

        private val consoleStage = ConsoleStage()

        fun sendNotification(message: String) {
            Platform.runLater {
                notificationStage.sendNotification(message)
            }
        }

        fun openConsole() {
            if (!Constants.CONSOLE_ENABLED)
                return
            Platform.runLater {
                consoleStage.open()
            }
        }
    }

    override fun start(stage: Stage) {
        val startTime = System.currentTimeMillis()
        LeagueUtils.initialize()

        Utils.updateBackground(Image(Constants.DEFAULT_APP_BACKGROUND), backgroundRegion)

        val stackPane = StackPane(backgroundRegion, root)
        val scene = Scene(stackPane, Constants.APP_WIDTH, Constants.APP_HEIGHT)

        loadStyleSheets(scene)
        stage.icons.add(Image(Constants.DEFAULT_FAVICON))
        stage.title = Constants.APP_NAME
        stage.isResizable = false
        stage.scene = scene
        stage.show()

        val stopTime = System.currentTimeMillis()
        val delta = stopTime - startTime
        println("Took ${delta}ms to start")
    }

    @Throws(Exception::class)
    override fun stop() {
        notificationStage.close()
        consoleStage.close()
        super.stop()
        exitProcess(0)
    }

    private fun loadStyleSheets(scene: Scene) {
        val styles: URL? = Resources.getResource("styles.css")
        val fontStyles: URL? = Resources.getResource("fontstyle.css")

        if (styles == null || fontStyles == null) {
            println("Resource(s) not found. Aborting.")
            exitProcess(-1)
        }
        scene.stylesheets.add(styles.toExternalForm())
        scene.stylesheets.add(fontStyles.toExternalForm())
    }
}

fun main() {
    Application.launch(OphieGG::class.java)
}
