package wtf.kira.runes.tasks

import javafx.animation.FadeTransition
import javafx.application.Platform
import javafx.scene.image.Image
import javafx.util.Duration
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType
import no.stelar7.api.r4j.basic.constants.types.lol.TeamType
import no.stelar7.api.r4j.impl.lol.raw.SpectatorAPI
import no.stelar7.api.r4j.pojo.lol.spectator.SpectatorParticipant
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner
import wtf.kira.runes.OphieGG
import wtf.kira.runes.containers.LiveGameChampionDetails
import wtf.kira.runes.data.LeagueImageType
import wtf.kira.runes.utils.LeagueUtils
import java.util.concurrent.locks.ReentrantLock
import kotlin.math.roundToInt

class UpdateLiveGameTask(val summoner: Summoner): Runnable {

    companion object {

        val lock = ReentrantLock()

    }

    override fun run() {
        synchronized(lock) {
            val inter = OphieGG.root.liveGameContext.liveGameInterface
            val game = SpectatorAPI.getInstance().getCurrentGame(summoner.platform, summoner.summonerId)

            if (game == null) {
                Platform.runLater {
                    inter.resetVisiblity()
                    inter.updateGameModeName("${summoner.name} is not currently in a game.")
                    OphieGG.root.appToolBar.tbLiveGame.style = ""
                }
                return
            }

            val players = game.participants
            val currentTime = game.gameLength
            var gameQueueType = ""

            when (game.gameQueueConfig) {
                GameQueueType.TEAM_BUILDER_RANKED_SOLO -> gameQueueType = "(Ranked Solo)"
                GameQueueType.RANKED_FLEX_SR -> gameQueueType = "(Ranked Flex)"
                GameQueueType.CLASH -> gameQueueType = "(Clash)"
                GameQueueType.ARAM -> gameQueueType = "(ARAM)"
                else -> {}
            }

            Platform.runLater {
                inter.updateGameModeName(game.map.prettyName().replace("Summoners", "Summoner's") + " $gameQueueType")
                OphieGG.root.appToolBar.tbLiveGame.style = "-fx-background-color: rgba(139, 0, 0, 0.3); -fx-background-radius: 3; -fx-text-fill: white;"
            }

            for ((index, player) in players.withIndex()) {
                if (player == null)
                    continue
                println("${player.summonerName}, $index")
                when (player.team) {
                    TeamType.RED -> {
                        val container = inter.redTeamContainer.getChampionDetails(index - 5)
                        updateContainer(player, container, summoner.platform)
                    }
                    TeamType.BLUE -> {
                        val container = inter.blueTeamContainer.getChampionDetails(index)
                        updateContainer(player, container, summoner.platform)
                    }
                    else -> {}
                }
            }



        }
    }

    private fun updateContainer(player: SpectatorParticipant, container: LiveGameChampionDetails, shard: LeagueShard) {
        val playerAsSummoner = Summoner.byName(summoner.platform, player.summonerName) ?: return
        val rankedDetails = LeagueUtils.getLeagueEntry(playerAsSummoner, GameQueueType.RANKED_SOLO_5X5)
        val championImage = Image(LeagueUtils.getImageURLForChampionName(LeagueUtils.dDragonApi.getChampion(player.championId).name, LeagueImageType.LOADING))

        Platform.runLater {
            container.updateChampionBackground(championImage)
            container.setSummonerName(player.summonerName)

            if (rankedDetails != null) {
                val wins = rankedDetails.wins.toDouble()
                val losses = rankedDetails.losses.toDouble()
                val totalGames = (wins + losses).toInt()
                val winRate = ((wins / totalGames) * 100.0).roundToInt()
                container.setLeagueShard(shard)
                container.setSummonerDetails(winRate, totalGames, Image(LeagueUtils.getMiniCrestForRank(rankedDetails.tier)), rankedDetails.tierDivisionType.prettyName())
            }

            val fadeIn = FadeTransition(Duration(500.0))
            fadeIn.node = container
            fadeIn.fromValue = 0.0
            fadeIn.toValue = 1.0

            fadeIn.play()
            container.visibleProperty().set(true)
        }
    }
}