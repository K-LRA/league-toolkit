package wtf.kira.runes.controls.button

import javafx.scene.control.ToggleGroup
import javafx.scene.input.KeyCode
import wtf.kira.runes.contexts.AppContext
import wtf.kira.runes.controls.AppToolBar

class OverviewButton(appToolBar: AppToolBar, tgToolBar: ToggleGroup) : MenuToggleButton(appToolBar, KeyCode.F1) {

    init {
        text = "Overview"
        tgToolBar.toggles.add(this)
    }

    override fun run() {
        appToolBar.root.switchContext(AppContext.OVERVIEW)
    }
}