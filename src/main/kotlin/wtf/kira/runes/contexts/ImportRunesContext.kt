package wtf.kira.runes.contexts

import javafx.geometry.Insets
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import wtf.kira.runes.containers.AppRoot
import wtf.kira.runes.tasks.ChampSelectTask

class ImportRunesContext(val root: AppRoot): VBox() {

    init {
        id = "importRunesContext"
        //style = "-fx-background-color: purple;"
        BorderPane.setMargin(this, Insets(10.0, 10.0, 10.0, 0.0))
    }

    fun switchInterface(stage: ChampSelectTask.Stage) {
        if (stage != ChampSelectTask.Stage.CHAMP_SELECT) {
            //style = "-fx-background-color: purple;"
        } else {
            //style = "-fx-background-color: red;"

        }
    }
}