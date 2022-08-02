package wtf.kira.runes.tasks

import javafx.animation.FadeTransition
import javafx.application.Platform
import javafx.scene.control.Tooltip
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.util.Duration
import no.stelar7.api.r4j.basic.constants.types.lol.GameModeType
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType
import no.stelar7.api.r4j.basic.constants.types.lol.TierDivisionType
import no.stelar7.api.r4j.impl.lol.builders.matchv5.match.MatchListBuilder
import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner
import wtf.kira.runes.containers.LastGameResult
import wtf.kira.runes.contexts.MultiSearchContext
import wtf.kira.runes.controls.TitleLabel
import wtf.kira.runes.data.LeagueImageType
import wtf.kira.runes.data.MultiSearchMatchHistory
import wtf.kira.runes.utils.LeagueUtils
import java.util.concurrent.locks.ReentrantLock

class MultiSearchTask(private val names: List<String>, private val context: MultiSearchContext) : Runnable {

    companion object {

        val lock = ReentrantLock()

    }

    override fun run() {
        synchronized(lock) {
            for ((index, name) in names.withIndex()) {
                if (index >= 5) break; if (name == "") continue
                val shard = context.root.appToolBar.cbSelectLeagueShard.selectionModel.selectedItem
                val summoner = Summoner.byName(shard, name) ?: continue

                val summonerName = summoner.name
                val profileIconImage = Image(LeagueUtils.getProfileIconURLForId(summoner.profileIconId))
                val ranked = LeagueUtils.getLeagueEntry(summoner, GameQueueType.RANKED_SOLO_5X5)
                val flex = LeagueUtils.getLeagueEntry(summoner, GameQueueType.RANKED_FLEX_SR)
                val queue = ranked ?: flex

                val container = context.getContainer(index)

                val miniCrestImage = Image(LeagueUtils.getMiniCrestForRank(if (queue == null) TierDivisionType.UNRANKED else queue.tierDivisionType))

                val historyList = mutableListOf<MultiSearchMatchHistory>()

                var matchListBuilder = MatchListBuilder()
                matchListBuilder = matchListBuilder.withPuuid(summoner.puuid).withPlatform(summoner.platform)
                matchListBuilder = matchListBuilder.withCount(30)

                val matchIds = matchListBuilder.get()

                if (matchIds.isEmpty())
                    println("empty")

                var i = 0
                while (i < 7) {
                    val match = LOLMatch.get(shard, matchIds[i])

                    if (match == null || match.queue == GameQueueType.CUSTOM) {

                        matchIds.removeAt(i)
                        continue
                    }

                    if (matchIds.isEmpty()) {
                        println("matchIds was empty.") //TODO fix Str8 Flexxn search hang?
                        break
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

                        val history = MultiSearchMatchHistory()

                        history.assists = summonerInMatch.assists
                        history.deaths = summonerInMatch.deaths
                        history.won = summonerInMatch.didWin()
                        history.championImage = Image(
                            LeagueUtils.getImageURLForChampionName(
                                summonerInMatch.championName,
                                LeagueImageType.PORTRAIT
                            )
                        )
                        history.kills = summonerInMatch.kills
                        history.isRanked = match.queue == GameQueueType.TEAM_BUILDER_RANKED_SOLO
                        historyList.add(history)

                    } else {
                        matchIds.removeAt(i)
                    }
                    i++
                }

                val results = mutableListOf<LastGameResult>()

                for (history in historyList) {
                    val result = LastGameResult(ImageView(history.championImage), TitleLabel(""), Tooltip())
                    result.updateGameResult(history.kills, history.assists, history.deaths)
                    result.setWon(history.won, history.isRanked)
                    results.add(result)
                }

                val fadeTransition = FadeTransition(Duration(500.0))
                fadeTransition.node = container
                fadeTransition.fromValue = 0.0
                fadeTransition.toValue = 1.0

                Platform.runLater {
                    container.updateSummonerName(summonerName)
                    container.updateProfileIcon(profileIconImage)

                    if (queue != null) {
                        container.updateWinLoss(queue.wins, queue.losses)
                        container.rankedDetails.updateRank(queue.tierDivisionType, queue.leaguePoints)
                        container.rankedDetails.updateMiniCrest(miniCrestImage)
                    }

                    container.gameResults.children.clear()
                    container.gameResults.children.addAll(results)

                    fadeTransition.play()
                    container.visibleProperty().set(true)
                }
            }
        }
    }
}