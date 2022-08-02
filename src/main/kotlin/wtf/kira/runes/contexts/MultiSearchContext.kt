package wtf.kira.runes.contexts

import javafx.application.Platform
import javafx.geometry.Insets
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import wtf.kira.runes.containers.AppRoot
import wtf.kira.runes.containers.MultiSearchResultVBox
import wtf.kira.runes.tasks.MultiSearchTask

class MultiSearchContext(val root: AppRoot): HBox() {

    private val multiSearchResultContainers = mutableListOf(
        MultiSearchResultVBox(this),
        MultiSearchResultVBox(this),
        MultiSearchResultVBox(this),
        MultiSearchResultVBox(this),
        MultiSearchResultVBox(this))

    init {
        id = "multiSearchContext"
        BorderPane.setMargin(this, Insets(10.0, 0.0, 10.0, 0.0))
        children.addAll(multiSearchResultContainers)
    }

    fun search(names: List<String>) {
        root.executor.submit(MultiSearchTask(names, this))
    }

    fun getContainer(index: Int): MultiSearchResultVBox {
        return multiSearchResultContainers[index]
    }
}