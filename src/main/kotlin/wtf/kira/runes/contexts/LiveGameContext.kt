package wtf.kira.runes.contexts

import javafx.geometry.Insets
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import wtf.kira.runes.containers.AppRoot
import wtf.kira.runes.containers.LiveGameInterface

class LiveGameContext(val root: AppRoot) : VBox() {

    val liveGameInterface = LiveGameInterface(this)

    init {
        id = "liveGameContext"
        children.add(liveGameInterface)
        BorderPane.setMargin(this, Insets(10.0, 0.0, 10.0, 0.0))
    }
}