package wtf.kira.runes.controls.button

import javafx.scene.control.ToggleGroup
import javafx.scene.input.KeyCode
import wtf.kira.runes.contexts.AppContext
import wtf.kira.runes.controls.AppToolBar

class ImportRunesButton(appToolBar: AppToolBar, tgToolBar: ToggleGroup) : MenuToggleButton(appToolBar, KeyCode.F3) {

    init {
        text = "Champ Select"
        tgToolBar.toggles.add(this)
    }

    override fun run() {
        appToolBar.root.switchContext(AppContext.CHAMP_SELECT)
    }
}