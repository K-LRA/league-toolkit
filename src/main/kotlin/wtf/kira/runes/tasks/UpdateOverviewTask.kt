package wtf.kira.runes.tasks

import javafx.application.Platform
import javafx.scene.image.Image
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner
import wtf.kira.runes.OphieGG
import wtf.kira.runes.contexts.OverviewContext
import wtf.kira.runes.data.OverviewData
import wtf.kira.runes.utils.LeagueUtils
import wtf.kira.runes.utils.Utils
import java.util.concurrent.locks.ReentrantLock

class UpdateOverviewTask(private val context: OverviewContext, private val summoner: Summoner): Runnable {

    companion object {

        val lock = ReentrantLock()

    }

    override fun run() {
        synchronized(lock) {
            val data = OverviewData()

            data.name = summoner.name
            data.puuid = summoner.puuid
            data.summonerLevel = summoner.summonerLevel
            data.platform = summoner.platform
            data.setProfileIconIdAndImage(summoner.profileIconId)
            data.bestChampionId = LeagueUtils.getBestChampionId(summoner)

            val rankedEntry = LeagueUtils.getLeagueEntry(summoner, GameQueueType.RANKED_SOLO_5X5)
            val flexEntry = LeagueUtils.getLeagueEntry(summoner, GameQueueType.RANKED_FLEX_SR)

            if (rankedEntry != null) {
                data.rankedTier = LeagueUtils.getRank(rankedEntry)
                data.rankedLp = rankedEntry.leaguePoints
                data.rankedWins = rankedEntry.wins
                data.rankedLosses = rankedEntry.losses
            }

            if (flexEntry != null) {
                data.flexTier = LeagueUtils.getRank(flexEntry)
                data.flexLp = flexEntry.leaguePoints
                data.flexWins = flexEntry.wins
                data.flexLosses = flexEntry.losses
            }

            data.setSplashImage()
            data.setRankedCrestImage()
            data.setFlexCrestImage()
            data.setBorderImage()

            Platform.runLater {
                val summary = context.root.overviewBanner.overviewSummary

                summary.titleLabel.text = data.name
                summary.iconWithBorder.icon.image = data.profileIconImage
                summary.iconWithBorder.border.image = data.borderImage
                summary.show(true)

                Image(LeagueUtils.getSplashURLForChampionId(data.bestChampionId))
                if (data.splashImage != null)
                    Utils.updateBackground(data.splashImage!!, OphieGG.backgroundRegion)

                context.overviewFooter.overviewRanked.updateRank(data.rankedTier.prettyName().uppercase())
                if (data.rankedCrestImage != null)
                    context.overviewFooter.overviewRanked.rankedCrest.image = data.rankedCrestImage

                context.overviewFooter.overviewFlex.updateRank(data.flexTier.prettyName().uppercase())
                if (data.flexCrestImage != null)
                    context.overviewFooter.overviewFlex.rankedCrest.image = data.flexCrestImage
            }
        }
    }
}