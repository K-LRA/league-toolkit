package wtf.kira.runes.utils

import javafx.geometry.Side
import javafx.scene.effect.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.image.WritableImage
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import wtf.kira.runes.OphieGG

class Utils {

    companion object {

        fun updateBackground(image: Image, region: Region) {
            val backgroundImage = BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition(
                    Side.RIGHT, 1.0, true, Side.TOP, 1.0, false),
                BackgroundSize.DEFAULT)
            val background = Background(backgroundImage)
            region.background = background
            region.effect = Glow()
        }

        fun applyBorderRadius(imageView: ImageView, radius: Double) {
            val r = Rectangle(imageView.fitWidth, imageView.fitHeight)
            r.arcWidth = radius
            r.arcHeight = radius
            imageView.clip = r
        }

        fun applyBorderRadius(image: Image, radius: Double): Image {
            val r = Rectangle()
            r.widthProperty().bind(image.widthProperty())
            r.heightProperty().bind(image.heightProperty())
            r.arcWidth = radius
            r.arcHeight = radius
            return ImageView(image).image
        }

        fun applyBorderRadius(radius: Double, vararg imageViews: ImageView) {
            for (iv in imageViews) {
                applyBorderRadius(iv, radius)
            }
        }

        fun cropPortraitImage(image: Image): Image {
            val reader = image.pixelReader
            return WritableImage(reader, 45, 45, image.width.toInt() - 70, image.height.toInt() - 70)
        }

        fun setBackground(pane: Pane, path: String): Pane {
            val image = Image(path)
            val backgroundImage = BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition(
                    Side.RIGHT, 1.0, true, Side.TOP, 1.0, false),
                BackgroundSize.DEFAULT)
            val background = Background(backgroundImage)
            pane.background = background
            return pane
        }
    }
}