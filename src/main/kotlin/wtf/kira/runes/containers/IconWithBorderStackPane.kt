package wtf.kira.runes.containers

import javafx.geometry.Insets
import javafx.scene.image.ImageView
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import wtf.kira.runes.controls.BorderType
import wtf.kira.runes.utils.Utils

class IconWithBorderStackPane(val icon: ImageView, val border: ImageView, val borderType: BorderType): StackPane() {

    init {
        when (borderType) {

            BorderType.PRESTIGE -> {
                icon.fitWidth = 90.0
                icon.fitHeight = 90.0

                border.fitWidth = border.image.width / 2.4
                border.fitHeight = border.image.height / 2.4
            }

            BorderType.RANKED -> {
                icon.fitWidth = 80.0
                icon.fitHeight = 80.0

                border.fitWidth = border.image.width / 10.5
                border.fitHeight = border.image.height / 10.5
                setMargin(border, Insets(-70.0, 0.0, 0.0, 0.0))
            }
        }

        Utils.applyBorderRadius(icon, 1000.0)
        setMargin(icon, Insets(-10.0, 0.0, 0.0, 0.0))

        VBox.setMargin(this, Insets(-90.0, 0.0, 0.0, 0.0))
        children.addAll(icon, border)
    }
}