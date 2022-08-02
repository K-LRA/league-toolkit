package wtf.kira.runes.containers

import javafx.animation.FadeTransition
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import javafx.util.Duration
import wtf.kira.runes.controls.BorderType
import wtf.kira.runes.controls.TitleLabel
import wtf.kira.runes.utils.Constants

class OverviewBannerSummaryVBox: VBox() {

    val iconWithBorder = IconWithBorderStackPane(
        ImageView(Constants.DEFAULT_ICON), ImageView(Constants.DEFAULT_PRESTIGE_PORTRAIT), BorderType.PRESTIGE)

    val titleLabel = TitleLabel("kíra").setMargin(Insets(-35.0, 0.0, 0.0, 0.0))

    init {
        children.addAll(iconWithBorder, titleLabel)
        alignment = Pos.TOP_CENTER
        setMargin(this, Insets(300.0, 0.0, 0.0, if (iconWithBorder.borderType == BorderType.PRESTIGE) -8.0 else -11.0))
        visibleProperty().set(false)
    }

    private fun getBorder(borderType: BorderType): String {
        return if (borderType == BorderType.RANKED)
            Constants.DEFAULT_RANKED_PORTRAIT
        else Constants.DEFAULT_PRESTIGE_PORTRAIT
    }

    fun show(visible: Boolean) {
        if (visible) {

            val fade = FadeTransition(Duration(500.0))
            fade.node = this
            fade.toValue = 1.0
            fade.fromValue = 0.0
            fade.play()
        }
        visibleProperty().set(visible)
    }
}