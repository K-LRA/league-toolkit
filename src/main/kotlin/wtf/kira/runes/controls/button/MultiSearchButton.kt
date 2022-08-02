package wtf.kira.runes.controls.button

import javafx.scene.control.ToggleGroup
import javafx.scene.input.KeyCode
import wtf.kira.runes.contexts.AppContext
import wtf.kira.runes.controls.AppToolBar

class MultiSearchButton(appToolBar: AppToolBar, tgToolBar: ToggleGroup) : MenuToggleButton(appToolBar, KeyCode.F5) {

    init {
        text = "Multi-Search"
        tgToolBar.toggles.add(this)
    }

    override fun run() {
        appToolBar.root.switchContext(AppContext.MULTI_SEARCH)
    }
}