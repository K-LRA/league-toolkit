package wtf.kira.runes.containers

import javafx.animation.FadeTransition
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.geometry.Side
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.shape.Rectangle
import javafx.util.Duration
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard
import no.stelar7.api.r4j.basic.constants.types.lol.TeamType
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner
import wtf.kira.runes.OphieGG
import wtf.kira.runes.contexts.LiveGameContext
import wtf.kira.runes.controls.TitleLabel
import wtf.kira.runes.data.LeagueImageType
import wtf.kira.runes.utils.LeagueUtils

class LiveGameInterface(context: LiveGameContext): VBox() {

    val redTeamContainer = LiveGameTeamContainer()

    val blueTeamContainer = LiveGameTeamContainer()

    private val lblGameMode = TitleLabel("Live Game will automatically update when you search a Summoner currently in a game.").setNewId("gameModeName")

    init {
        alignment = Pos.TOP_CENTER
        prefHeight = 634.0
        prefWidth = 940.0
        children.addAll(redTeamContainer, lblGameMode, blueTeamContainer)
    }

    fun updateGameModeName(gameModeName: String) {
        lblGameMode.text = gameModeName
    }

    fun getTeam(teamType: TeamType): LiveGameTeamContainer {
        if (teamType == TeamType.RED)
            return redTeamContainer
        return blueTeamContainer
    }

    fun resetVisiblity() {
        for (container in redTeamContainer.teamDetails)
            container.visibleProperty().set(false)
        for (container in blueTeamContainer.teamDetails)
            container.visibleProperty().set(false)
    }
}

class LiveGameTeamContainer: HBox() {

    val teamDetails = mutableListOf(
        LiveGameChampionDetails(),
        LiveGameChampionDetails(),
        LiveGameChampionDetails(),
        LiveGameChampionDetails(),
        LiveGameChampionDetails())

    init {
        prefHeight = 317.0
        prefWidth = 940.0
        children.addAll(teamDetails)
        paddingProperty().set(Insets(5.0))
    }

    fun getChampionDetails(index: Int): LiveGameChampionDetails {
        if (index > 4)
            return teamDetails[4]
        return teamDetails[index]
    }
}

class LiveGameChampionDetails: VBox() {

    private lateinit var leagueShard: LeagueShard

    private var championImage = Image(LeagueUtils.getImageURLForChampionName("Ahri", LeagueImageType.LOADING))

    private val detailsContainer = VBox()

    private val lblSummonerName = TitleLabel("Summoner").setNewId("liveGameSummonerNameLabel")
    private val lblWinRate = TitleLabel("Unranked").setNewId("liveGameWinRateLabel")
    private val lblGamesPlayed = TitleLabel("").setNewId("liveGameGamesPlayedLabel")

    private val rankedDetailsContainer = HBox()

    private val ivRankedIcon = ImageView("https://raw.communitydragon.org/pbe/plugins/rcp-fe-lol-static-assets/global/default/images/ranked-mini-crests/unranked.png")
    private val lblRank = TitleLabel("Unranked").setNewId("liveGameRankLabel")

    fun setSummonerName(summonerName: String) {
        lblSummonerName.text = summonerName
    }

    fun setSummonerDetails(winRate: Int, gamesPlayed: Int, rankedIcon: Image, rank: String) {
        lblWinRate.text = "$winRate% win rate"
        lblWinRate.style =
            if ((winRate) >= 70) "-fx-text-fill: rgb(227, 119, 39);"
            else if ((winRate) >= 60) "-fx-text-fill: rgb(61, 149, 229);"
            else ""
        lblGamesPlayed.text = "$gamesPlayed games played"
        ivRankedIcon.image = rankedIcon
        lblRank.text = rank
    }

    init {
        id = "liveGameChampionDetails"
        alignment = Pos.BOTTOM_CENTER
        prefWidth = 196.0
        prefHeight = 307.0
        HBox.setMargin(this, Insets(0.0, 10.0, 0.0, 0.0))

        val r = Rectangle()
        r.widthProperty().bind(widthProperty())
        r.heightProperty().bind(heightProperty())
        r.arcWidth = 6.0
        r.arcHeight = 6.0
        clip = r

        updateChampionBackground(championImage)

        val fadeOut = FadeTransition(Duration(300.0))
        fadeOut.node = this
        fadeOut.fromValue = 1.0
        fadeOut.toValue = 0.85

        val fadeIn = FadeTransition(Duration(300.0))
        fadeIn.node = this
        fadeIn.fromValue = 0.85
        fadeIn.toValue = 1.0

        setOnMouseEntered {
            fadeOut.play()
        }
        setOnMouseExited {
            fadeIn.play()
        }
        setOnMouseClicked {
            val summoner = Summoner.byName(leagueShard, lblSummonerName.text)
            println(lblSummonerName.text)
            if (summoner != null) {
                val search = OphieGG.root.appToolBar.btnSummonerSearch
                search.search(summoner)
            }
        }

        HBox.setMargin(ivRankedIcon, Insets(0.0, 2.0, 0.0, 0.0))
        ivRankedIcon.fitHeight = 23.0
        ivRankedIcon.fitWidth = 23.0
        rankedDetailsContainer.paddingProperty().set(Insets(3.0))
        rankedDetailsContainer.alignment = Pos.CENTER
        rankedDetailsContainer.style = "-fx-background-color: rgba(0,0,0,0.8);"
        rankedDetailsContainer.children.addAll(ivRankedIcon, lblRank)

        detailsContainer.paddingProperty().set(Insets(3.0, 0.0, 0.0, 0.0))
        detailsContainer.alignment = Pos.TOP_CENTER
        detailsContainer.style = "-fx-background-color: rgba(0,0,0,0.8);"

        lblGamesPlayed.paddingProperty().set(Insets(3.0, 0.0, 3.0, 0.0))
        detailsContainer.children.addAll(lblSummonerName, lblWinRate, lblGamesPlayed, rankedDetailsContainer)
        children.add(detailsContainer)

        visibleProperty().set(false)
    }

    fun updateChampionBackground(image: Image) {
        championImage = image
        val backgroundImage = BackgroundImage(championImage, BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition(
                Side.LEFT, .5, true, Side.TOP, .1, true),
            BackgroundSize.DEFAULT)
        background = Background(backgroundImage)
    }

    fun setLeagueShard(shard: LeagueShard) {
        leagueShard = shard
    }
}