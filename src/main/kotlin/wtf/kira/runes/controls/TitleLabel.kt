package wtf.kira.runes.controls

import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.scene.text.Font

class TitleLabel(title: String) : Label() {

    init {
        text = title
    }

    init {
        id = "titleLabel"
    }

    fun setMargin(insets: Insets): TitleLabel {
        VBox.setMargin(this, insets)
        return this
    }

    fun setNewId(newId: String): TitleLabel {
        id = newId
        return this
    }

    fun setVisibility(show: Boolean): TitleLabel {
        visibleProperty().set(show)
        return this
    }

    fun uppercase(): TitleLabel {
        text = text.uppercase()
        return this
    }
}