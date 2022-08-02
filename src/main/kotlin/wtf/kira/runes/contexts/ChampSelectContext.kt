package wtf.kira.runes.contexts

import javafx.geometry.Insets
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import wtf.kira.runes.containers.AppRoot
import wtf.kira.runes.containers.ChampSelectInterface
import wtf.kira.runes.tasks.ChampSelectTask

class ChampSelectContext(val root: AppRoot): VBox() {

    val champSelectInterface = ChampSelectInterface(this)

    init {
        id = "importRunesContext"
        children.add(champSelectInterface)
        BorderPane.setMargin(this, Insets(10.0, 10.0, 10.0, 0.0))
    }
}