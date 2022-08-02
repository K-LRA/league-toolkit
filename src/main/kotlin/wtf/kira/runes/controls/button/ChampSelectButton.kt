package wtf.kira.runes.controls.button

import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup

class ChampSelectButton(text: String, toggleGroup: ToggleGroup): ToggleButton() {

    init {
        this.id = "champSelectButton"
        this.text = text
        toggleGroup.toggles.add(this)

        setOnMouseClicked {
            toggleGroup.selectToggle(this)
        }
    }
}