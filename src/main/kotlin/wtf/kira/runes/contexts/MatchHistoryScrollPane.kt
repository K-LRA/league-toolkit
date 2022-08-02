package wtf.kira.runes.contexts

import javafx.geometry.Insets
import javafx.scene.control.ScrollPane
import javafx.scene.layout.BorderPane
import wtf.kira.runes.containers.AppRoot

class MatchHistoryScrollPane(root: AppRoot): ScrollPane() {


    init {
        id = "matchHistoryScrollPane"
        style = "-fx-background-color: transparent;"
        vbarPolicy = ScrollBarPolicy.NEVER;
        BorderPane.setMargin(this, Insets(10.0, 10.0, 10.0, 0.0))
    }
}