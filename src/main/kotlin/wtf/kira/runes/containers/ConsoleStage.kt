package wtf.kira.runes.containers

import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.control.TextArea
import javafx.scene.image.Image
import javafx.scene.layout.VBox
import javafx.stage.Stage
import wtf.kira.runes.utils.Constants
import java.io.IOException
import java.io.OutputStream
import java.io.PrintStream


class ConsoleStage: Stage() {

    private val container = VBox()

    private val consoleScene = Scene(container)

    private val taConsole = TextArea()

    private val stream = PrintStream(Console(taConsole))

    init {
        icons.add(Image(Constants.DEFAULT_FAVICON))
        title = Constants.APP_NAME + " Console"
        scene = consoleScene
        isResizable = false

        System.setOut(stream)
        System.setErr(stream)
        container.children.addAll(taConsole)
        container.setOnKeyPressed {
            hide()
        }
    }

    fun open() {
        if (isShowing) {
            requestFocus()
            return
        }
        show()
    }
}

class Console(private val console: TextArea): OutputStream() {

    private fun appendText(valueOf: String?) {
        if (Constants.CONSOLE_ENABLED) {
            Platform.runLater {
                console.appendText(valueOf)
            }
        }
    }

    @Throws(IOException::class)
    override fun write(b: Int) {
        appendText(b.toChar().toString())
    }
}