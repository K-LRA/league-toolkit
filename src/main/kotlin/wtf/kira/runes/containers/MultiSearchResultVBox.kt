package wtf.kira.runes.containers

import javafx.animation.FadeTransition
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Tooltip
import javafx.scene.effect.DropShadow
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.util.Duration
import no.stelar7.api.r4j.basic.constants.types.lol.TierDivisionType
import wtf.kira.runes.contexts.MultiSearchContext
import wtf.kira.runes.controls.TitleLabel
import wtf.kira.runes.data.LeagueImageType
import wtf.kira.runes.tasks.MultiSearchTask
import wtf.kira.runes.utils.Constants
import wtf.kira.runes.utils.LeagueUtils
import wtf.kira.runes.utils.Utils
import kotlin.math.roundToInt

class MultiSearchResultVBox(private val context: MultiSearchContext): VBox() {

    private val icon = ImageView(Image(Constants.DEFAULT_ICON))

    private var name = TitleLabel("Summoner").setNewId("multiSearchResultName")

    private val winLoss = TitleLabel("0 W / 0 L (100%)").setNewId("multiSearchResultRankName")

    val rankedDetails = MultiSearchResultRankHBox()

    val gameResults = VBox()

    init {
        id = "multiSearchVBox"
        alignment = Pos.TOP_CENTER
        minWidth = ((979.0 / 5) - 10)
        maxHeight = 494.0
        icon.fitWidth = 100.0
        icon.fitHeight = 100.0
        Utils.applyBorderRadius(icon, 1000.0)

        HBox.setMargin(this, Insets(0.0, 10.0, 0.0, 0.0))
        setMargin(winLoss, Insets(0.0, 0.0, 10.0, 0.0))
        setMargin(icon, Insets(10.0))
        children.addAll(icon, name, rankedDetails, winLoss, gameResults)
        val fadeOut = FadeTransition(Duration(300.0))
        fadeOut.node = this
        fadeOut.fromValue = 1.0
        fadeOut.toValue = 0.75
        val fadeIn = FadeTransition(Duration(300.0))
        fadeIn.node = this
        fadeIn.fromValue = 0.75
        fadeIn.toValue = 1.0
        setOnMouseEntered {
            fadeOut.play()
        }
        setOnMouseExited {
            fadeIn.play()
        }
        winLoss.visibleProperty().set(false)
        visibleProperty().set(false)
    }

    fun updateGameResults(results: MutableList<LastGameResult>) { //TODO: fix
        synchronized(MultiSearchTask.lock) {
            gameResults.children.clear()
            for (result in results) {
                if (gameResults.children.contains(result))
                    gameResults.children.remove(result)
                gameResults.children.add(result)
            }
        }
    }

    fun updateSummonerName(name: String) {
        this.name.text = name
    }

    fun updateProfileIcon(image: Image) {
        icon.image = image
    }

    fun updateWinLoss(wins: Int, losses: Int) {
        try {
            winLoss.text = "$wins W / $losses L (${((wins.toDouble() / (wins + losses)) * 100).roundToInt()}%)"
        } catch (e: Throwable) {
            winLoss.text = "$wins W / $losses L"
        }
        winLoss.visibleProperty().set(true)
    }
}

class MultiSearchResultRankHBox: HBox() {

    private val miniCrest = ImageView(Image(LeagueUtils.getMiniCrestForRank(TierDivisionType.UNRANKED)))

    private var rankName = TitleLabel("Unranked").setNewId("multiSearchResultRankName")

    init {
        alignment = Pos.CENTER
        miniCrest.fitWidth = 20.0
        miniCrest.fitHeight = 20.0
        setMargin(miniCrest, Insets(2.0, 0.0, 0.0, 0.0))
        children.addAll(miniCrest, rankName)
    }

    fun updateRank(tier: TierDivisionType, lp: Int) {
        rankName.text = tier.prettyName() + " $lp LP"
    }

    fun updateMiniCrest(tierImage: Image) {
        miniCrest.image = tierImage
    }
}

class LastGameResult(championPortrait: ImageView, private val gameResult: TitleLabel, private val tooltip: Tooltip): HBox() {

    init {
        id = "multiSearchResultLastGameResult"
        style = Constants.RGB_VICTORY
        gameResult.setNewId("multiSearchResultGameResult")
        alignment = Pos.CENTER
        championPortrait.fitWidth = 20.0
        championPortrait.fitHeight = 20.0
        VBox.setMargin(this, Insets(10.0, 25.0, 0.0, 25.0))
        setMargin(championPortrait, Insets(0.0, 5.0, 0.0, 0.0))
        Utils.applyBorderRadius(1000.0, championPortrait)
        paddingProperty().set(Insets(2.0))
        children.addAll(championPortrait, gameResult)

        tooltip.showDelay = Duration(200.0)
        Tooltip.install(this, tooltip)
    }

    fun updateGameResult(kills: Int, assists: Int, deaths: Int) {
        val totalKills = (kills + assists).toDouble()
        gameResult.text = "$kills / $deaths / $assists (${if (deaths == 0) "P" else (((totalKills / deaths.toDouble()) * 10.0).roundToInt() / 10.0)})"
    }

    fun setWon(won: Boolean, ranked: Boolean) {
        val sb = StringBuilder(if (won) Constants.RGB_VICTORY else Constants.RGB_DEFEAT)
        if (ranked)
            sb.append(if (won) Constants.BORDER_RGB_VICTORY else Constants.BORDER_RGB_DEFEAT)
        tooltip.text = if (ranked) "This was a ranked game." else "This was not a ranked game."
        style = sb.toString()
    }
}