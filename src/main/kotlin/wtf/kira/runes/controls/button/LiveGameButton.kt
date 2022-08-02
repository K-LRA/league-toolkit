package wtf.kira.runes.controls.button

import javafx.scene.control.ToggleGroup
import javafx.scene.input.KeyCode
import wtf.kira.runes.contexts.AppContext
import wtf.kira.runes.controls.AppToolBar

class LiveGameButton(appToolBar: AppToolBar, tgToolBar: ToggleGroup) : MenuToggleButton(appToolBar, KeyCode.F4) {

    init {
        text = "Live Game"
        tgToolBar.toggles.add(this)
    }

    override fun run() {
        appToolBar.root.switchContext(AppContext.LIVE_GAME)
    }
}