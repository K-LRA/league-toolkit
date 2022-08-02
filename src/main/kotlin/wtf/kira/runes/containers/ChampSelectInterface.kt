package wtf.kira.runes.containers

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.ToggleGroup
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType
import wtf.kira.runes.contexts.ChampSelectContext
import wtf.kira.runes.controls.TitleLabel
import wtf.kira.runes.controls.button.ChampSelectButton
import wtf.kira.runes.data.LeagueImageType
import wtf.kira.runes.runes.*
import wtf.kira.runes.utils.LeagueUtils
import wtf.kira.runes.utils.Utils

class ChampSelectInterface(val context: ChampSelectContext): HBox() {

    val containerLeft = ChampSelectInterfaceLeftContainer()

    val containerRight = ChampSelectInterfaceRightContainer()

    init {
        id = "ChampSelectInterface"
        alignment = Pos.TOP_CENTER
        prefHeight = 500.0
        containerLeft.prefWidth = 490.0
        containerRight.prefWidth = 490.0
        containerLeft.alignment = Pos.TOP_CENTER
        containerLeft.paddingProperty().set(Insets(50.0, 0.0 ,0.0, 0.0))
        containerRight.paddingProperty().set(Insets(50.0, 0.0 ,0.0, 0.0))
        containerRight.alignment = Pos.TOP_CENTER
        children.addAll(containerLeft, containerRight)
    }

    fun updateBuildName(name: String, position: String, gameMode: GameQueueType) {
        var pos = position.lowercase()
        if (pos == "utility") pos = "support"
        if (pos == "bottom") pos = "adc"
        if (pos == "middle") pos = "mid"
        pos = if (position == "adc") "ADC" else pos.capitalize()
        pos = when (gameMode) {
            GameQueueType.ARAM_5X5 -> "ARAM"
            GameQueueType.URF_5X5 -> "URF"
            else -> pos
        }
        containerLeft.buildName.text = "Runes for $name $pos"
    }

    fun updateChampionImage(image: Image) {
        containerRight.updateChampionImage(image)
    }

    fun updateRunes(data: RunePageData) {
        containerLeft.runeList.clear()
        containerLeft.shardList.clear()

        for ((index, rune) in data.perks.withIndex()) {
            val r: Any = if (index >= (data.perks.size - 3)) ShardData.getShardForName(rune) else RuneTreeData.getRuneForName(rune)
            if (r is Rune) {
                containerLeft.runeList.add(RuneSelection(r))
            }
            if (r is Shard) {
                containerLeft.shardList.add(ShardSelection(r))
            }
        }
        containerLeft.updateRunes()
        containerLeft.updateWinRate(data.winRate)
    }

    fun isImportingRunes(): Boolean {
        return containerLeft.isImportingRunes()
    }

    fun getSelectedGameMode(): GameQueueType {
        return containerLeft.getSelectedGameMode()
    }

}

class ChampSelectInterfaceLeftContainer: VBox() {

    var buildName = TitleLabel("Waiting for Champ Select").setNewId("champSelectBuildTitle")

    private var winRate = TitleLabel("Win rate: --%").setNewId("champSelectWinRate").setVisibility(false)

    var runeList = mutableListOf<RuneSelection>()
    var shardList = mutableListOf<ShardSelection>()

    private var parentContainer = HBox()

    private var runesContainer = VBox()

    private var settingsContainer = VBox()

    var tgRuneButtons = ToggleGroup()

    var tbUseRankedRunes = ChampSelectButton("Ranked", tgRuneButtons)
    var tbUseAramRunes = ChampSelectButton("ARAM", tgRuneButtons)
    var tbUseUrfRunes = ChampSelectButton("URF", tgRuneButtons)
    var tbDontImportRunes = ChampSelectButton("Don't Import", tgRuneButtons)

    init {
        buildName.paddingProperty().set(Insets(0.0, 0.0, 5.0, 0.0))
        winRate.paddingProperty().set(Insets(0.0, 0.0, 15.0, 0.0))
        runesContainer.prefWidth = 155.0
        settingsContainer.prefWidth = 155.0
        parentContainer.alignment = Pos.CENTER
        parentContainer.children.addAll(runesContainer, settingsContainer)
        tgRuneButtons.selectToggle(tbUseRankedRunes)
        settingsContainer.children.addAll(tbUseRankedRunes, tbUseAramRunes, tbUseUrfRunes, tbDontImportRunes)
        children.addAll(buildName, winRate, parentContainer)
    }

    fun updateWinRate(value: String) {
        winRate.text = value
        winRate.visibleProperty().set(true)
    }

    fun updateRunes() {
        runesContainer.children.clear()

        for (r in runeList)
            runesContainer.children.add(r)
        for (r in shardList)
            runesContainer.children.add(r)
    }

    fun isImportingRunes(): Boolean {
        return tgRuneButtons.selectedToggle != tbDontImportRunes
    }

    fun getSelectedGameMode(): GameQueueType {
        return when (tgRuneButtons.selectedToggle) {

            tbUseRankedRunes -> GameQueueType.RANKED_SOLO_5X5
            tbUseAramRunes -> GameQueueType.ARAM_5X5
            tbUseUrfRunes -> GameQueueType.URF_5X5
            else -> GameQueueType.RANKED_SOLO_5X5

        }
    }
}

data class RuneSelection(val rune: Rune): HBox() {

    var runeImage = ImageView(Image(rune.runeImageUrl))

    var runeName = TitleLabel(rune.runeName).setNewId("champSelectRuneName")

    init {
        VBox.setMargin(this, Insets(0.0, 0.0, 5.0, 0.0))
        runeName.paddingProperty().set(Insets(0.0, 0.0, 0.0, 5.0))
        runeImage.fitWidth = 32.0
        runeImage.fitHeight = 32.0
        alignment = Pos.CENTER_LEFT
        children.addAll(runeImage, runeName)
    }
}

data class ShardSelection(val rune: Shard): HBox() {

    var runeImage = ImageView(Image(rune.shardImageUrl))

    var runeName = TitleLabel(rune.shardName).setNewId("champSelectRuneName")

    init {
        alignment = Pos.CENTER_LEFT
        children.addAll(runeImage, runeName)
    }
}

class ChampSelectInterfaceRightContainer: VBox() {

    private var championImage = ImageView(Image(LeagueUtils.getImageURLForChampionName("Akali", LeagueImageType.TILE)))

    init {
        championImage.fitHeight = 300.0
        championImage.fitWidth = 300.0
        Utils.applyBorderRadius(championImage, 1000.0)
        championImage.visibleProperty().set(false)
        children.add(championImage)
        //style = "-fx-background-color: purple;"
    }

    fun updateChampionImage(image: Image) {
        championImage.image = image
        championImage.visibleProperty().set(true)
    }
}