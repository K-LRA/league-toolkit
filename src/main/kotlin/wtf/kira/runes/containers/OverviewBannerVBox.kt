package wtf.kira.runes.containers

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import wtf.kira.runes.utils.Utils

class OverviewBannerVBox(val root: AppRoot): VBox() {

    private val backgroundUrl = "https://raw.communitydragon.org/latest/plugins/rcp-fe-lol-shared-components/global/default/banner-primary.png"

    val overviewSummary = OverviewBannerSummaryVBox()

    init {
        id = "overviewBanner"
        alignment = Pos.TOP_CENTER
        prefWidth = 196.0
        children.add(overviewSummary)
        BorderPane.setMargin(this, Insets(-62.0, 0.0, 0.0, 20.0))
        Utils.setBackground(this, backgroundUrl)
    }
}