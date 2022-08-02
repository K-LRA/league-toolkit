package wtf.kira.runes.contexts

import javafx.application.Platform
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import no.stelar7.api.r4j.basic.constants.types.lol.TierDivisionType
import wtf.kira.runes.OphieGG
import wtf.kira.runes.containers.AppRoot
import wtf.kira.runes.controls.RankedCrest
import wtf.kira.runes.controls.TitleLabel
import wtf.kira.runes.data.OverviewData
import wtf.kira.runes.utils.LeagueUtils
import wtf.kira.runes.utils.Utils
import java.util.concurrent.locks.ReentrantLock

class OverviewContext(val root: AppRoot): VBox() {

    val overviewFooter = OverviewFooter(root)

    init {
        id = "overviewContext"
        alignment = Pos.BOTTOM_CENTER
        children.add(overviewFooter)
    }
}

class OverviewFooter(val root: AppRoot): HBox() {

    val overviewRanked = RankedDetails("Solo/Duo")

    val overviewFlex = RankedDetails("Flex 5v5")

    init {
        id = "overviewFooter"
        children.addAll(overviewRanked, overviewFlex)
        alignment = Pos.BOTTOM_LEFT
        BorderPane.setMargin(this, Insets(0.0, 0.0, 0.0, 0.0))
    }
}

open class RankedDetails(rankTitle: String): VBox() {

    private val titleSoloDuo = TitleLabel(rankTitle).uppercase().setNewId("titleRankedDetails")

    private val titleRank = TitleLabel("Unranked").uppercase().setNewId("titleRank")

    val rankedCrest = RankedCrest(LeagueUtils.getRankedCrestImageURL(TierDivisionType.UNRANKED))

    init {
        id = "overviewRankedDetails"
        prefHeight = 200.0
        prefWidth = 250.0
        alignment = Pos.BOTTOM_CENTER
        HBox.setMargin(this, Insets(0.0, 10.0, 0.0, 0.0))

        children.addAll(titleSoloDuo, titleRank, rankedCrest)
    }

    fun updateRank(rank: String) {
        titleRank.text = rank
    }
}