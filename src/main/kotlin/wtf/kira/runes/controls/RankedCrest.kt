package wtf.kira.runes.controls

import javafx.geometry.Insets
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox

class RankedCrest(path: String): ImageView() {

    init {
        image = Image(path)
        fitWidth = 200.0
        fitHeight = 200.0
        VBox.setMargin(this, Insets(-25.0, 0.0,0.0,0.0))
    }
}