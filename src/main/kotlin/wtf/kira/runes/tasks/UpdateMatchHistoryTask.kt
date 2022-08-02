package wtf.kira.runes.tasks

import javafx.animation.FadeTransition
import javafx.application.Platform
import javafx.scene.control.Tooltip
import javafx.scene.image.Image
import javafx.util.Duration
import no.stelar7.api.r4j.basic.constants.types.lol.GameModeType
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType
import no.stelar7.api.r4j.basic.constants.types.lol.MatchlistMatchType
import no.stelar7.api.r4j.basic.constants.types.lol.TeamType
import no.stelar7.api.r4j.impl.lol.builders.matchv5.match.MatchListBuilder
import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner
import wtf.kira.runes.OphieGG
import wtf.kira.runes.contexts.MatchHistoryContext
import wtf.kira.runes.controls.PlayerImage
import wtf.kira.runes.data.LeagueImageType
import wtf.kira.runes.data.MatchHistoryData
import wtf.kira.runes.data.SummonerMatchData
import wtf.kira.runes.containers.MatchHistoryEntryHBox
import wtf.kira.runes.utils.Constants
import wtf.kira.runes.utils.LeagueUtils
import wtf.kira.runes.utils.Utils
import java.sql.Timestamp
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class UpdateMatchHistoryTask(private val context: MatchHistoryContext, private val summoner: Summoner): Runnable {

    override fun run() {
        synchronized(context.lock) {

            var matchListBuilder = MatchListBuilder()
            matchListBuilder = matchListBuilder.withPuuid(summoner.puuid).withPlatform(summoner.platform)

            matchListBuilder = matchListBuilder.withCount(30)

            val ranked = LeagueUtils.getLeagueEntry(summoner, GameQueueType.RANKED_SOLO_5X5)
            val flex = LeagueUtils.getLeagueEntry(summoner, GameQueueType.RANKED_TEAM_5X5)
            val unranked = (ranked == null && flex == null)

            when (context.root.appToolBar.cbQueueType.selectionModel.selectedItem) {
                "Normal" -> matchListBuilder = matchListBuilder.withType(MatchlistMatchType.NORMAL)
                "ARAM" -> matchListBuilder = matchListBuilder.withQueue(GameQueueType.ARAM)
                "Ranked Solo" -> {
                    matchListBuilder = matchListBuilder.withType(if (unranked) MatchlistMatchType.NORMAL else MatchlistMatchType.RANKED)
                    matchListBuilder = matchListBuilder.withQueue(GameQueueType.TEAM_BUILDER_RANKED_SOLO)
                }
                "Ranked Flex" -> {
                    matchListBuilder = matchListBuilder.withType(if (unranked) MatchlistMatchType.NORMAL else MatchlistMatchType.RANKED)
                    matchListBuilder = matchListBuilder.withQueue(GameQueueType.RANKED_FLEX_SR)
                }
                "Clash" -> {
                    matchListBuilder = matchListBuilder.withType(if (unranked) MatchlistMatchType.NORMAL else MatchlistMatchType.RANKED)
                    matchListBuilder = matchListBuilder.withQueue(GameQueueType.CLASH)
                }
            }

            val matchIds = matchListBuilder.get()
            val matchListSize = matchIds.size

            val containers = context.matchHistoryContainers
            val containersSize = containers.size

            if (matchIds.isEmpty()) {
                println("No data available; try again.")
                OphieGG.sendNotification("No data available for this queue; try again.")
                return
            }

            var index = 0
            while (index < if (matchListSize < containersSize) matchListSize else containersSize) {

                val match = LOLMatch.get(summoner.platform, matchIds[index])

                if (match == null || match.queue == GameQueueType.CUSTOM) {
                    matchIds.removeAt(index)
                    continue
                }

                if (matchIds.isEmpty()) {
                    println("matchIds was empty.") //TODO fix Str8 Flexxn search hang?
                    Platform.runLater {
                        OphieGG.sendNotification("No matches available; please try again.")
                    }
                    return
                }

                if (match.gameMode == GameModeType.URF ||
                    match.gameMode == GameModeType.ARAM ||
                    match.gameMode == GameModeType.ULTBOOK ||
                    match.gameMode == GameModeType.CLASSIC) {

                    var summonerInMatch: MatchParticipant? = null

                    for (player in match.participants) {
                        if (player == null)
                            throw java.lang.NullPointerException("MatchParticipant was null")
                        if (summoner.name == player.summonerName) {
                            summonerInMatch = player
                            break
                        }
                    }

                    if (summonerInMatch == null)
                        throw java.lang.NullPointerException("MatchParticipant was null")

                    val summonerMatchData = SummonerMatchData(summoner, summonerInMatch, match)
                    val container = containers[index]

                    populateContainer(summonerMatchData, container, match)
                    println("Loaded data from game ${index + 1} for ${summoner.name}")
                } else {
                    matchIds.removeAt(index)
                }
                index++
            }
        }
    }

    private fun populateContainer(summonerMatchData: SummonerMatchData, container: MatchHistoryEntryHBox, match: LOLMatch) {

        val data = MatchHistoryData()

        /*
        Match result
         */
        if (summonerMatchData.didWin()) {
            data.gameResultString = "Victory"
            data.gameResultColourString = Constants.RGB_VICTORY
        } else {
            data.gameResultString = "Defeat"
            data.gameResultColourString = Constants.RGB_DEFEAT
        }

        /*
        Game type
         */
        data.gameModeString = summonerMatchData.getGameModeName()

        /*
        Game duration
         */
        data.gameDurationString = SimpleDateFormat("MMM dd").format(Timestamp(summonerMatchData.getTimestamp())) + " (${summonerMatchData.getGameDurationMinutes()}m)"

        /*
        Played Champion portrait image
         */
        val championPortraitImage = Utils.cropPortraitImage(Image(LeagueUtils.getImageURLForChampionName(summonerMatchData.getChampionName(), LeagueImageType.TILE)))
        data.championPortraitImage = championPortraitImage

        /*
        Summoner spell 1 image
         */
        data.summonerSpell1Image = summonerMatchData.getSummonerSpell1().image

        /*
        Summoner spell 2 image
         */
        data.summonerSpell2Image = summonerMatchData.getSummonerSpell2().image

        /*
        Keystone image
         */
        data.keystoneImage = summonerMatchData.getKeystone().getImage()

        /*
        Secondary tree image
         */
        data.secondaryTreeImage = summonerMatchData.getSecondaryRune1().getRuneTreeImage()

        /*
        Kills / Deaths / Assists
         */
        data.killsDeathsAssistsString = "${summonerMatchData.getKills()} / ${summonerMatchData.getDeaths()} / ${summonerMatchData.getAssists()}"

        /*
        KDA
         */
        if (summonerMatchData.getDeaths() > 0) {
            try {
                data.kdaString = "${summonerMatchData.getKDA()} KDA"
            } catch (e: ArithmeticException) {
                e.printStackTrace()
                data.kdaString = " -1 KDA"
            }
        } else {
            data.kdaString = "Perfect KDA"
        }

        /*
        Kill Participation/KP
         */
        data.kpString = "${summonerMatchData.getKillParticipation()}% KP"

        /*
        Champion level
         */
        data.levelString = "Level ${summonerMatchData.getChampionLevel()}"

        /*
        CS per min
         */
        data.csString = "${summonerMatchData.getTotalCS()} CS (${summonerMatchData.getCSPerMinute()})"

        /*
        Champion damage
         */
        data.damageDealtPercentString = "Damage: ${summonerMatchData.getChampionDamageDealtPercent()}%"//"Wards: ${summonerMatchData.getTotalWardsPlaced()}"

        data.didMostDamage = summonerMatchData.didMostDamage()

        /*
        Total damage
         */
        data.damageDealtString = "Damage dealt to champions: ${NumberFormat.getNumberInstance(Locale.US).format(summonerMatchData.getDamageDealtToChampions())}" + (if (summonerMatchData.didMostDamage()) "\n${summoner.name} did the most damage on the team" else "")

        /*
        Vision
         */
        data.controlWardsBought = summonerMatchData.getControlWardsBought()

        data.wardsPlaced = summonerMatchData.getTotalWardsPlaced()

        data.visionScore = summonerMatchData.getVisionScore()

        /*
        Item images
         */
        data.itemImageList = summonerMatchData.getImageListForInventoryItems()

        /*
        Champion portrait images
         */
        val redTeam = summonerMatchData.getPlayers(TeamType.RED)
        val blueTeam = summonerMatchData.getPlayers(TeamType.BLUE)

        val redTeamImageList = mutableListOf<PlayerImage>()
        val blueTeamImageList = mutableListOf<PlayerImage>()

        for ((index, _) in redTeam.withIndex()) {
            redTeamImageList.add(PlayerImage(Image(LeagueUtils.getImageURLForChampionName(redTeam[index].championName, LeagueImageType.PORTRAIT)), redTeam[index].summonerName, match.platform))
        }
        for ((index, _) in blueTeam.withIndex()) {
            blueTeamImageList.add(PlayerImage(Image(LeagueUtils.getImageURLForChampionName(blueTeam[index].championName, LeagueImageType.PORTRAIT)), blueTeam[index].summonerName, match.platform))
        }
        data.redTeamImageList = redTeamImageList
        data.blueTeamImageList = blueTeamImageList

        Platform.runLater {
            val fadeTransition = FadeTransition(Duration(500.0))
            fadeTransition.node = container
            fadeTransition.fromValue = 0.0
            fadeTransition.toValue = 1.0

            container.labelGameResult.text = data.gameResultString
            container.labelGameResult.style = data.gameResultColourString
            container.labelGameType.text = data.gameModeString
            container.labelGameDuration.text = data.gameDurationString
            container.imageViewChampionPortrait.image = championPortraitImage
            container.imageViewSummonerSpell1.image = data.summonerSpell1Image
            container.imageViewSummonerSpell2.image = data.summonerSpell2Image
            container.imageViewRunePrimary.image = data.keystoneImage
            container.imageViewRuneSecondary.image = data.secondaryTreeImage
            container.labelKillsDeathsAssists.text = data.killsDeathsAssistsString
            container.labelKDA.text = data.kdaString
            container.labelKP.text = data.kpString
            container.labelLevel.text = data.levelString
            container.labelCS.text = data.csString
            container.labelDamageDealt.text = data.damageDealtPercentString
            container.labelDamageDealt.style = if (data.didMostDamage) "-fx-text-fill: rgb(227, 119, 39);" else ""
            container.damageDealtTooltip.text = data.damageDealtString
            container.labelControlWardsBought.text = "Control Wards: ${data.controlWardsBought}"
            container.labelWardsPlaced.text = "Total Wards: ${data.wardsPlaced}"
            container.labelVisionScore.text = "Vision Score: ${data.visionScore}"

            /**
             * TODO Gross but easier this way, clean up later?
             */

            container.imageViewItem1.image = data.itemImageList[1]
            container.imageViewItem2.image = data.itemImageList[2]
            container.imageViewItem3.image = data.itemImageList[3]
            container.imageViewItem4.image = data.itemImageList[4]
            container.imageViewItem5.image = data.itemImageList[5]
            container.imageViewItem6.image = data.itemImageList[6]

            val reddTeam = listOf(container.imageViewPlayer1, container.imageViewPlayer2, container.imageViewPlayer3, container.imageViewPlayer4, container.imageViewPlayer5)
            val blueeTeam = listOf(container.imageViewPlayer6, container.imageViewPlayer7, container.imageViewPlayer8, container.imageViewPlayer9, container.imageViewPlayer10)

            for ((index, player) in reddTeam.withIndex()) {
                reddTeam[index].image = redTeamImageList[index].image
                reddTeam[index].id = "playerImageView"
                val tooltip = Tooltip()
                tooltip.showDelay = Duration(100.0)
                tooltip.text = redTeamImageList[index].summonerName
                Tooltip.install(player, tooltip)
                player.setOnMouseClicked {
                    redTeamImageList[index].getSummoner()
                        ?.let { it1 -> context.root.appToolBar.btnSummonerSearch.search(it1) }
                }
            }

            for ((index, player) in blueeTeam.withIndex()) {
                blueeTeam[index].image = blueTeamImageList[index].image
                blueeTeam[index].id = "playerImageView"
                val tooltip = Tooltip()
                tooltip.showDelay = Duration(100.0)
                tooltip.text = blueTeamImageList[index].summonerName
                Tooltip.install(player, tooltip)
                player.setOnMouseClicked {
                    blueTeamImageList[index].getSummoner()
                        ?.let { it1 -> context.root.appToolBar.btnSummonerSearch.search(it1) }
                }
            }

/*            container.imageViewPlayer1.image = redTeamImageList[0].image
            container.imageViewPlayer2.image = redTeamImageList[1].image
            container.imageViewPlayer3.image = redTeamImageList[2].image
            container.imageViewPlayer4.image = redTeamImageList[3].image
            container.imageViewPlayer5.image = redTeamImageList[4].image

            container.imageViewPlayer6.image = blueTeamImageList[0].image
            container.imageViewPlayer7.image = blueTeamImageList[1].image
            container.imageViewPlayer8.image = blueTeamImageList[2].image
            container.imageViewPlayer9.image = blueTeamImageList[3].image
            container.imageViewPlayer10.image = blueTeamImageList[4].image*/

            if (context.children.contains(container)) {
                context.children.remove(container)
            }
            context.children.add(container)

            fadeTransition.play()
            container.visibleProperty().set(true)
        }
    }
}