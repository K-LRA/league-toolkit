package wtf.kira.runes.controls

import javafx.scene.control.ChoiceBox

class QueueTypeChoiceBox(appToolBar: AppToolBar): ChoiceBox<String>() {


    init {
        items.addAll(listOf("Ranked Solo", "Ranked Flex", "Clash", "ARAM", "Normal"))
        maxHeight = 30.0
        maxWidth = 110.0
        selectionModel.select(0)
    }
}