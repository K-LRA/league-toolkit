package wtf.kira.runes.controls.button

import javafx.scene.control.ToggleButton
import javafx.scene.input.KeyCode
import wtf.kira.runes.controls.AppToolBar

abstract class MenuToggleButton(val appToolBar: AppToolBar, val keyCode: KeyCode?) : ToggleButton() {

    init {
        id = "menuToggleButton"
        setOnMouseClicked {
            run()
        }
    }

    abstract fun run()
}