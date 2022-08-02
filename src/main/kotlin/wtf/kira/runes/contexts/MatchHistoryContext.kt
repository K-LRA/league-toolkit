package wtf.kira.runes.contexts

import javafx.geometry.Insets
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import wtf.kira.runes.containers.AppRoot
import wtf.kira.runes.containers.MatchHistoryEntryHBox
import java.util.concurrent.locks.ReentrantLock

class MatchHistoryContext(val root: AppRoot): VBox() {

    val lock = ReentrantLock()

    val matchHistoryContainers = mutableListOf<MatchHistoryEntryHBox>()

    init {
        id = "matchHistoryContext"
        BorderPane.setMargin(this, Insets(10.0, 10.0, 10.0, 0.0))

        for (index in 0 until 10) {
            matchHistoryContainers.add(MatchHistoryEntryHBox())
        }
    }
}