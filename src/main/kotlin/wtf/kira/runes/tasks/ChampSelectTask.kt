package wtf.kira.runes.tasks

import com.stirante.lolclient.ApiResponse
import generated.LolChampSelectChampSelectSession
import generated.LolGameflowGameflowPhase
import generated.LolPerksPerkPageResource
import generated.LolSummonerSummoner
import javafx.application.Platform
import javafx.scene.image.Image
import wtf.kira.runes.containers.AppRoot
import wtf.kira.runes.data.LeagueImageType
import wtf.kira.runes.runes.RunePage
import wtf.kira.runes.utils.LeagueUtils
import wtf.kira.runes.utils.UGGScraper
import wtf.kira.runes.utils.Utils
import java.util.concurrent.locks.ReentrantLock

class ChampSelectTask(val root: AppRoot): Runnable {

    enum class Stage { RUNNING, LOBBY, CHAMP_SELECT, STOPPED }

    companion object {

        var stage = Stage.STOPPED

        val lock = ReentrantLock()

    }

    lateinit var stage: Stage

    var runePage: RunePage? = null

    override fun run() {
        synchronized(lock) {
            stage = Stage.RUNNING
            while (stage != Stage.STOPPED) {

                var queryClientPhase: ApiResponse<LolGameflowGameflowPhase>?

                var queryChampSelect: ApiResponse<LolChampSelectChampSelectSession>?

                var querySummoner: ApiResponse<LolSummonerSummoner>?

                try {

                    queryClientPhase = LeagueUtils.lcuApi.executeGet(
                        "/lol-gameflow/v1/gameflow-phase",
                        LolGameflowGameflowPhase::class.java
                    )

                    querySummoner = LeagueUtils.lcuApi.executeGet(
                        "/lol-summoner/v1/current-summoner",
                        LolSummonerSummoner::class.java
                    )

                    queryChampSelect = LeagueUtils.lcuApi.executeGet(
                        "/lol-champ-select/v1/session",
                        LolChampSelectChampSelectSession::class.java
                    )

                } catch (ignored: Throwable) {
                    println("error!")
                    Platform.runLater {
                        root.appToolBar.lblConnectedAs.setConnectedAs(null)
                    }
                    break
                }

                if (queryClientPhase == null || queryChampSelect == null || querySummoner == null) {
                    println("$queryClientPhase, $queryChampSelect, $querySummoner")
                    Thread.sleep(2000L)
                    continue
                }

                val responsePhase = queryClientPhase.responseObject

                val responseSummoner = querySummoner.responseObject

                if (responsePhase == null || responseSummoner == null) {
                    Thread.sleep(2000L)
                    continue
                }

                Platform.runLater {
                    root.appToolBar.lblConnectedAs.setConnectedAs(responseSummoner.displayName)
                }

                if (responsePhase.name == LolGameflowGameflowPhase.NONE.name || responsePhase.name == LolGameflowGameflowPhase.LOBBY.name)
                    stage = Stage.LOBBY

                if (responsePhase.name == LolGameflowGameflowPhase.CHAMPSELECT.name)
                    stage = Stage.CHAMP_SELECT

                //println("current stage ${stage.name} (${responsePhase.name})")

                if (stage == Stage.CHAMP_SELECT) {
                    val responseChampSelect = queryChampSelect.responseObject

                    if (responseChampSelect != null) {
                        if (root.champSelectContext.champSelectInterface.isImportingRunes())
                            processRunes(responseSummoner, responseChampSelect)
                    }
                } else
                    runePage = null

                Thread.sleep(2000L)
            }
            stage = Stage.STOPPED
            Thread.sleep(2000L)
            root.executor.submit(ChampSelectTask(root))
        }
    }

    private fun processRunes(querySummoner: LolSummonerSummoner, queryChampSelect: LolChampSelectChampSelectSession) {
        val summonerId = querySummoner.summonerId
        val team = queryChampSelect.myTeam

        for (player in team) {
            if (player.summonerId == summonerId) {

                val championId = player.championId
                val champion = LeagueUtils.dDragonApi.getChampion(championId)
                val position = player.assignedPosition

                if (champion == null) {
                    println("champion is null for id $championId")
                    return
                }

                val championName = champion.name
                val runePage = runePage

                val championImage = Image(LeagueUtils.getImageURLForChampionName(championName, LeagueImageType.TILE))
                Utils.applyBorderRadius(championImage, 1000.0)

                val gameMode = root.champSelectContext.champSelectInterface.getSelectedGameMode()

                if (runePage != null) {
                    if (championName == runePage.championName && gameMode == runePage.gameMode)
                        return
                }

                val page = RunePage(championName, gameMode, UGGScraper.scrape(championName, gameMode, position))
                this.runePage = page

                try {
                    /*
                    Get currently-selected Rune Page
                     */
                    val currentPage = LeagueUtils.lcuApi.executeGet(
                        "/lol-perks/v1/currentpage",
                        LolPerksPerkPageResource::class.java
                    )


                    /*
                    Delete the page via ID
                    */
                    if (currentPage != null && currentPage.responseObject != null) {
                        LeagueUtils.lcuApi.executeDelete("/lol-perks/v1/pages/${currentPage.responseObject.id}")
                    }

                    /*
                    Add our new page
                     */
                    LeagueUtils.lcuApi.executePost("/lol-perks/v1/pages", page)

                    Platform.runLater {

                        root.champSelectContext.champSelectInterface.updateBuildName(championName, page.role, page.gameMode)
                        root.champSelectContext.champSelectInterface.updateChampionImage(championImage)
                        root.champSelectContext.champSelectInterface.updateRunes(page.data)

                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
                break
            }
        }
    }
}