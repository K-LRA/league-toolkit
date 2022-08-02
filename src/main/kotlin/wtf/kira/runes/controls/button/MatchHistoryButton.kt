package wtf.kira.runes.controls.button

import javafx.scene.control.ToggleGroup
import javafx.scene.input.KeyCode
import wtf.kira.runes.contexts.AppContext
import wtf.kira.runes.controls.AppToolBar

class MatchHistoryButton(appToolBar: AppToolBar, tgToolBar: ToggleGroup) : MenuToggleButton(appToolBar, KeyCode.F2) {

    init {
        text = "Match History"
        tgToolBar.toggles.add(this)
    }

    override fun run() {
        appToolBar.root.switchContext(AppContext.MATCH_HISTORY)
    }
}