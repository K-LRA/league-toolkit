package wtf.kira.runes.containers

import javafx.animation.FadeTransition
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.util.Duration
import wtf.kira.runes.utils.Resources
import wtf.kira.runes.utils.Utils
import java.io.File

class MatchHistoryEntryHBox: HBox() {

    private val vBoxGameDetails = VBox()

    val labelGameResult = Label()

    val labelGameType = Label()

    val labelGameDuration = Label()

    private val hBoxLoadOutContainer = HBox()

    val imageViewChampionPortrait = ImageView()

    private val vBoxSummonerSpells = VBox()

    val imageViewSummonerSpell1 = ImageView()

    val imageViewSummonerSpell2 = ImageView()

    private val vBoxRunes = VBox()

    val imageViewRunePrimary = ImageView()

    val imageViewRuneSecondary = ImageView()

    private val vBoxKDA = VBox()

    val labelKillsDeathsAssists = Label()

    val labelKDA = Label()

    val labelKP = Label()

    private val vBoxLevelCsTierAverage = VBox()

    private val vBoxVision = VBox()

    val labelLevel = Label()

    val labelCS = Label()

    val labelDamageDealt = Label()

    val damageDealtTooltip = Tooltip()

    val labelWardsPlaced = Label()

    val labelControlWardsBought = Label()

    val labelVisionScore = Label()

    private val gridPaneItems = GridPane()

    var imageViewItem1: ImageView
    var imageViewItem2: ImageView
    var imageViewItem3: ImageView
    var imageViewItem4: ImageView
    var imageViewItem5: ImageView
    var imageViewItem6: ImageView

    private val gridPanePlayers = GridPane()

    var imageViewPlayer1: ImageView
    var imageViewPlayer2: ImageView
    var imageViewPlayer3: ImageView
    var imageViewPlayer4: ImageView
    var imageViewPlayer5: ImageView
    var imageViewPlayer6: ImageView
    var imageViewPlayer7: ImageView
    var imageViewPlayer8: ImageView
    var imageViewPlayer9: ImageView
    var imageViewPlayer10: ImageView

    init {

        this.id = "hBoxMatchHistory"
        alignment = Pos.CENTER
        this.setPrefSize(940.0, 100.0)
        VBox.setMargin(this, Insets(0.0, 0.0, 10.0, 19.0))

        vBoxGameDetails.setPrefSize(80.0, 100.0)
        setMargin(vBoxGameDetails, Insets(0.0, 10.0, 10.0, 10.0))
        vBoxGameDetails.alignment = Pos.CENTER

        this.children.add(vBoxGameDetails)

        labelGameResult.id = "labelGameResult"
        labelGameResult.setPrefSize(60.0, 20.0)
        VBox.setMargin(labelGameResult, Insets(0.0, 0.0, 10.0, 0.0))
        labelGameResult.text = "Result"
        labelGameResult.alignment = Pos.CENTER
        vBoxGameDetails.children.add(labelGameResult)

        labelGameType.id = "labelGameType"
        labelGameType.setPrefSize(80.0, 14.0)
        labelGameType.layoutX = 10.0
        labelGameType.layoutY = 10.0
        labelGameType.text = "Game Type"
        labelGameType.alignment = Pos.CENTER
        vBoxGameDetails.children.add(labelGameType)

        labelGameDuration.id = "labelGameDuration"
        labelGameDuration.setPrefSize(80.0, 14.0)
        labelGameDuration.layoutX = 10.0
        labelGameDuration.layoutY = 60.0
        labelGameDuration.text = "0 min"
        labelGameDuration.alignment = Pos.CENTER
        vBoxGameDetails.children.add(labelGameDuration)

        imageViewChampionPortrait.fitHeight = 80.0
        imageViewChampionPortrait.fitWidth = 80.0
        imageViewChampionPortrait.pickOnBoundsProperty().set(true)
        imageViewChampionPortrait.preserveRatioProperty().set(true)
        //setMargin(imageViewChampionPortrait, Insets(10.0, 0.0, 0.0, 10.0))
        //println(Resources.getResourcePath("/data/img/champion_portrait_placeholder.png"))
        //imageViewChampionPortrait.image = Image(Resources.getResourcePath("/data/img/champion_portrait_placeholder.png"))
        imageViewChampionPortrait.image =
            Image(Resources.getResourcePath("champion_portrait_placeholder.png"))
        setMargin(imageViewChampionPortrait, Insets(10.0, 0.0, 10.0, 0.0))
        this.children.add(imageViewChampionPortrait)


        hBoxLoadOutContainer.id = "hBoxLoadOutContainer"
        setMargin(hBoxLoadOutContainer, Insets(10.0, 0.0, 10.0, 10.0))
        this.children.add(hBoxLoadOutContainer)

        vBoxSummonerSpells.setPrefSize(32.0, 200.0)
        setMargin(vBoxSummonerSpells, Insets(0.0, 0.0, 0.0, 2.0))
        hBoxLoadOutContainer.children.add(vBoxSummonerSpells)

        val summonerSpellPlaceholderImage =
            Image(Resources.getResourcePath("summoner_spell_placeholder.png"))

        imageViewSummonerSpell1.fitWidth = 32.0
        imageViewSummonerSpell1.fitHeight = 32.0
        imageViewSummonerSpell1.pickOnBoundsProperty().set(true)
        imageViewSummonerSpell1.preserveRatioProperty().set(true)
        imageViewSummonerSpell1.image = summonerSpellPlaceholderImage
        VBox.setMargin(imageViewSummonerSpell1, Insets(5.0))
        vBoxSummonerSpells.children.add(imageViewSummonerSpell1)

        imageViewSummonerSpell2.fitWidth = 32.0
        imageViewSummonerSpell2.fitHeight = 32.0
        imageViewSummonerSpell2.pickOnBoundsProperty().set(true)
        imageViewSummonerSpell2.preserveRatioProperty().set(true)
        imageViewSummonerSpell2.image = summonerSpellPlaceholderImage
        VBox.setMargin(imageViewSummonerSpell2, Insets(0.0, 5.0, 5.0, 5.0))
        vBoxSummonerSpells.children.add(imageViewSummonerSpell2)


        vBoxRunes.setPrefSize(32.0, 200.0)
        hBoxLoadOutContainer.children.add(vBoxRunes)

        imageViewRunePrimary.fitWidth = 32.0
        imageViewRunePrimary.fitHeight = 32.0
        imageViewRunePrimary.pickOnBoundsProperty().set(true)
        imageViewRunePrimary.preserveRatioProperty().set(true)

        val imageViewRunePlaceholder = Image(Resources.getResourcePath("rune_placeholder.png"))

        imageViewRunePrimary.image = imageViewRunePlaceholder
        VBox.setMargin(imageViewRunePrimary, Insets(5.0, 5.0, 5.0, 0.0))
        vBoxRunes.children.add(imageViewRunePrimary)

        imageViewRuneSecondary.fitWidth = 22.0
        imageViewRuneSecondary.fitHeight = 22.0
        imageViewRuneSecondary.pickOnBoundsProperty().set(true)
        imageViewRuneSecondary.preserveRatioProperty().set(true)
        imageViewRuneSecondary.image = imageViewRunePlaceholder
        VBox.setMargin(imageViewRuneSecondary, Insets(5.0, 5.0, 5.0, 5.0))
        vBoxRunes.children.add(imageViewRuneSecondary)

        vBoxKDA.id = "vBoxKDA"
        vBoxKDA.setPrefSize(85.0, 80.0)
        setMargin(vBoxKDA, Insets(10.0, 0.0, 10.0, 10.0))
        vBoxKDA.alignment = Pos.BASELINE_CENTER
        this.children.add(vBoxKDA)

        labelKillsDeathsAssists.id = "labelKillsDeathsAssists"
        labelKillsDeathsAssists.setPrefSize(100.0, 17.0)
        labelKillsDeathsAssists.text = "0 / 0 / 0"
        VBox.setMargin(labelKillsDeathsAssists, Insets(5.0, 0.0, 0.0, 0.0))
        labelKillsDeathsAssists.alignment = Pos.BASELINE_CENTER
        vBoxKDA.children.add(labelKillsDeathsAssists)

        labelKDA.id = "labelKDA"
        labelKDA.setPrefSize(100.0, 17.0)
        labelKDA.layoutX = 10.0
        labelKDA.layoutY = 10.0
        labelKDA.text = "Perfect KDA"
        VBox.setMargin(labelKDA, Insets(5.0, 0.0, 0.0, 0.0))
        labelKDA.alignment = Pos.BASELINE_CENTER
        vBoxKDA.children.add(labelKDA)

        labelKP.id = "labelKP"
        labelKP.setPrefSize(100.0, 17.0)
        labelKP.layoutX = 10.0
        labelKP.layoutY = 50.0
        labelKP.text = "0% KP"
        VBox.setMargin(labelKP, Insets(5.0, 0.0, 0.0, 0.0))
        labelKP.alignment = Pos.BASELINE_CENTER
        vBoxKDA.children.add(labelKP)

        vBoxLevelCsTierAverage.id = "vBoxLevelCsTierAverage"
        vBoxLevelCsTierAverage.layoutX = 291.0
        vBoxLevelCsTierAverage.layoutY = 20.0
        vBoxLevelCsTierAverage.setPrefSize(90.0, 80.0)
        setMargin(vBoxLevelCsTierAverage, Insets(10.0, 10.0, 10.0, 0.0))
        vBoxLevelCsTierAverage.alignment = Pos.BASELINE_CENTER
        this.children.add(vBoxLevelCsTierAverage)

        labelLevel.id = "labelLevel"
        labelLevel.setPrefSize(100.0, 17.0)
        labelLevel.text = "Level 1"
        VBox.setMargin(labelLevel, Insets(5.0, 0.0, 0.0, 0.0))
        labelLevel.alignment = Pos.BASELINE_CENTER
        vBoxLevelCsTierAverage.children.add(labelLevel)

        labelCS.id = "labelCS"
        labelCS.layoutX = 10.0
        labelCS.layoutY = 28.0
        labelCS.setPrefSize(100.0, 17.0)
        labelCS.text = "0 CS (0.0)"
        VBox.setMargin(labelCS, Insets(5.0, 0.0, 0.0, 0.0))
        labelCS.alignment = Pos.BASELINE_CENTER
        vBoxLevelCsTierAverage.children.add(labelCS)


        labelDamageDealt.id = "labelTierAverage"
        labelDamageDealt.layoutX = 10.0
        labelDamageDealt.layoutY = 45.0
        labelDamageDealt.setPrefSize(100.0, 17.0)
        labelDamageDealt.text = "Damage: 0%"
        VBox.setMargin(labelDamageDealt, Insets(5.0, 0.0, 0.0, 0.0))
        labelDamageDealt.alignment = Pos.BASELINE_CENTER

        damageDealtTooltip.text = "Damage: 0"
        damageDealtTooltip.showDelay = Duration(300.0)
        Tooltip.install(labelDamageDealt, damageDealtTooltip)

        vBoxLevelCsTierAverage.children.add(labelDamageDealt)

        /*****************************************************************************/

        vBoxVision.id = "vBoxVision"
        vBoxVision.layoutX = 291.0
        vBoxVision.layoutY = 20.0
        vBoxVision.setPrefSize(90.0, 80.0)
        setMargin(vBoxVision, Insets(10.0, 10.0, 10.0, 0.0))
        vBoxVision.alignment = Pos.BASELINE_CENTER
        this.children.add(vBoxVision)

        labelControlWardsBought.id = "labelControlWardsBought"
        labelControlWardsBought.layoutX = 10.0
        labelControlWardsBought.layoutY = 28.0
        labelControlWardsBought.setPrefSize(100.0, 17.0)
        labelControlWardsBought.text = "Control Wards: 0"
        VBox.setMargin(labelControlWardsBought, Insets(5.0, 0.0, 0.0, 0.0))
        labelControlWardsBought.alignment = Pos.BASELINE_CENTER
        vBoxVision.children.add(labelControlWardsBought)

        labelWardsPlaced.id = "labelWardsPlaced"
        labelWardsPlaced.setPrefSize(100.0, 17.0)
        labelWardsPlaced.text = "Total Wards: 0"
        VBox.setMargin(labelWardsPlaced, Insets(5.0, 0.0, 0.0, 0.0))
        labelWardsPlaced.alignment = Pos.BASELINE_CENTER
        vBoxVision.children.add(labelWardsPlaced)

        labelVisionScore.id = "labelVisionScore"
        labelVisionScore.layoutX = 10.0
        labelVisionScore.layoutY = 45.0
        labelVisionScore.setPrefSize(100.0, 17.0)
        labelVisionScore.text = "Vision Score: 0"
        VBox.setMargin(labelVisionScore, Insets(5.0, 0.0, 0.0, 0.0))
        labelVisionScore.alignment = Pos.BASELINE_CENTER
        vBoxVision.children.add(labelVisionScore)

        /*******************************************************************************/

        gridPaneItems.id = "gridPaneItems"
        for (i in 1..3) {
            val cc = ColumnConstraints()
            cc.minWidth = 32.0
            cc.prefWidth = 32.0
            cc.hgrow = Priority.SOMETIMES
            gridPaneItems.columnConstraints.add(cc)
        }
        for (i in 1..2) {
            val rc = RowConstraints()
            rc.minHeight = 32.0
            rc.prefHeight = 32.0
            rc.vgrow = Priority.SOMETIMES
            gridPaneItems.rowConstraints.add(rc)
        }

        setMargin(gridPaneItems, Insets(10.0, 10.0, 10.0, 0.0))
        this.children.add(gridPaneItems)

        val imageViewItemPlaceholder = Image(Resources.getResourcePath("item_placeholder.png"))

        imageViewItem1 = imageViewMatchHistory(0, 0, imageViewItemPlaceholder)
        GridPane.setMargin(imageViewItem1, Insets(2.0, 5.0, 0.0, 6.0))
        gridPaneItems.children.add(imageViewItem1)

        imageViewItem2 = imageViewMatchHistory(1, 0, imageViewItemPlaceholder)
        GridPane.setMargin(imageViewItem2, Insets(2.0, 5.0, 0.0, 3.0))
        gridPaneItems.children.add(imageViewItem2)

        imageViewItem3 = imageViewMatchHistory(2, 0, imageViewItemPlaceholder)
        GridPane.setMargin(imageViewItem3, Insets(2.0, 6.0, 0.0, 1.0))
        gridPaneItems.children.add(imageViewItem3)

        imageViewItem4 = imageViewMatchHistory(0, 1, imageViewItemPlaceholder)
        GridPane.setMargin(imageViewItem4, Insets(10.0, 3.0, 7.0, 6.0))
        gridPaneItems.children.add(imageViewItem4)

        imageViewItem5 = imageViewMatchHistory(1, 1, imageViewItemPlaceholder)
        GridPane.setMargin(imageViewItem5, Insets(10.0, 3.0, 7.0, 3.0))
        gridPaneItems.children.add(imageViewItem5)

        imageViewItem6 = imageViewMatchHistory(2, 1, imageViewItemPlaceholder)
        GridPane.setMargin(imageViewItem6, Insets(10.0, 6.0, 7.0, 1.0))
        gridPaneItems.children.add(imageViewItem6)

        gridPanePlayers.layoutX = 476.0
        gridPanePlayers.layoutY = 20.0
        gridPanePlayers.setPrefSize(235.0, 80.0)
        gridPanePlayers.id = "gridPanePlayers"

        for (i in 1..5) {
            val cc = ColumnConstraints()
            cc.minWidth = 32.0
            cc.prefWidth = 32.0
            cc.hgrow = Priority.SOMETIMES
            gridPanePlayers.columnConstraints.add(cc)
        }
        for (i in 1..2) {
            val rc = RowConstraints()
            rc.minHeight = 32.0
            rc.prefHeight = 32.0
            rc.vgrow = Priority.SOMETIMES
            gridPanePlayers.rowConstraints.add(rc)
        }

        setMargin(gridPanePlayers, Insets(10.0, 10.0, 10.0, 5.0))
        this.children.add(gridPanePlayers)

        val portraitImagePlaceholder =
            Image(Resources.getResourcePath("champion_portrait_placeholder.png"))


        imageViewPlayer1 = imageViewMatchHistory(0, 0, portraitImagePlaceholder)

        GridPane.setMargin(imageViewPlayer1, Insets(1.0, 0.0, 0.0, 9.0))
        gridPanePlayers.children.add(imageViewPlayer1)

        imageViewPlayer2 = imageViewMatchHistory(1, 0, portraitImagePlaceholder)
        GridPane.setMargin(imageViewPlayer2, Insets(1.0, 0.0, 0.0, 9.0))
        gridPanePlayers.children.add(imageViewPlayer2)

        imageViewPlayer3 = imageViewMatchHistory(2, 0, portraitImagePlaceholder)
        GridPane.setMargin(imageViewPlayer3, Insets(1.0, 0.0, 0.0, 9.0))
        gridPanePlayers.children.add(imageViewPlayer3)

        imageViewPlayer4 = imageViewMatchHistory(3, 0, portraitImagePlaceholder)
        GridPane.setMargin(imageViewPlayer4, Insets(1.0, 0.0, 0.0, 9.0))
        gridPanePlayers.children.add(imageViewPlayer4)

        imageViewPlayer5 = imageViewMatchHistory(4, 0, portraitImagePlaceholder)
        GridPane.setMargin(imageViewPlayer5, Insets(1.0, 0.0, 0.0, 9.0))
        gridPanePlayers.children.add(imageViewPlayer5)

        imageViewPlayer6 = imageViewMatchHistory(0, 1, portraitImagePlaceholder)
        GridPane.setMargin(imageViewPlayer6, Insets(3.0, 0.0, 0.0, 9.0))
        gridPanePlayers.children.add(imageViewPlayer6)

        imageViewPlayer7 = imageViewMatchHistory(1, 1, portraitImagePlaceholder)
        GridPane.setMargin(imageViewPlayer7, Insets(3.0, 0.0, 0.0, 9.0))
        gridPanePlayers.children.add(imageViewPlayer7)

        imageViewPlayer8 = imageViewMatchHistory(2, 1, portraitImagePlaceholder)
        GridPane.setMargin(imageViewPlayer8, Insets(3.0, 0.0, 0.0, 9.0))
        gridPanePlayers.children.add(imageViewPlayer8)

        imageViewPlayer9 = imageViewMatchHistory(3, 1, portraitImagePlaceholder)
        GridPane.setMargin(imageViewPlayer9, Insets(3.0, 0.0, 0.0, 9.0))
        gridPanePlayers.children.add(imageViewPlayer9)

        imageViewPlayer10 = imageViewMatchHistory(4, 1, portraitImagePlaceholder)
        GridPane.setMargin(imageViewPlayer10, Insets(3.0, 0.0, 0.0, 9.0))
        gridPanePlayers.children.add(imageViewPlayer10)

        Utils.applyBorderRadius(imageViewChampionPortrait, 500.0)
        Utils.applyBorderRadius(6.0, imageViewSummonerSpell1, imageViewSummonerSpell2)

        visibleProperty().set(false)

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
    }

    private fun imageViewMatchHistory(columnIndex: Int, rowIndex: Int, image: Image): ImageView {
        val imageView = ImageView()
        imageView.fitHeight = 24.0
        imageView.fitWidth = 24.0
        imageView.pickOnBoundsProperty().set(true)
        imageView.preserveRatioProperty().set(true)
        imageView.image = image
        if (columnIndex > 0)
            GridPane.setColumnIndex(imageView, columnIndex)
        if (rowIndex > 0)
            GridPane.setRowIndex(imageView, rowIndex)
        Utils.applyBorderRadius(imageView, 6.0)
        return imageView
    }
}