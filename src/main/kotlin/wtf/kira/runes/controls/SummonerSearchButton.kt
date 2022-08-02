package wtf.kira.runes.controls

import javafx.scene.control.Button
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner
import wtf.kira.runes.OphieGG
import wtf.kira.runes.contexts.AppContext
import wtf.kira.runes.tasks.UpdateLiveGameTask
import wtf.kira.runes.tasks.UpdateMatchHistoryTask
import wtf.kira.runes.tasks.UpdateOverviewTask
import wtf.kira.runes.utils.Constants

class SummonerSearchButton(val appToolBar: AppToolBar): Button() {

    private var searchCooldown = 0L

    init {
        text = "Search"
        maxHeight = 30.0
        setOnMouseClicked {
            search()
        }
    }

    fun search() {
        if (!canSearch()) {
            return
        }
        var name = appToolBar.tfSummonerSearch.text
        val shard = appToolBar.cbSelectLeagueShard.selectionModel.selectedItem

        if (name != null && name.length >= 3) {
            name = name.replace("klra", "kíra")
            name = name.replace("k�ra", "kíra")


            if (!name.contains(",")) {
                val summoner = Summoner.byName(shard, name) ?: return
                appToolBar.root.matchHistoryContext.children.clear()
                appToolBar.root.switchContext(AppContext.MATCH_HISTORY)
                appToolBar.tbMatchHistory.selectedProperty().set(true)
                appToolBar.root.executor.submit(UpdateOverviewTask(appToolBar.root.overviewContext, summoner))
                appToolBar.root.executor.submit(UpdateMatchHistoryTask(appToolBar.root.matchHistoryContext, summoner))
                appToolBar.root.executor.submit(UpdateLiveGameTask(summoner))

            } else {
                val names = name.split(",")
                if (names.size == 2 && names[1] == "") {
                    val summoner = Summoner.byName(shard, name) ?: return
                    appToolBar.root.matchHistoryContext.children.clear()
                    appToolBar.root.switchContext(AppContext.MATCH_HISTORY)
                    appToolBar.tbMatchHistory.selectedProperty().set(true)
                    appToolBar.root.executor.submit(UpdateOverviewTask(appToolBar.root.overviewContext, summoner))
                    appToolBar.root.executor.submit(UpdateMatchHistoryTask(appToolBar.root.matchHistoryContext, summoner))
                    appToolBar.root.executor.submit(UpdateLiveGameTask(summoner))

                } else {
                    appToolBar.root.multiSearchContext.search(names)
                    appToolBar.root.switchContext(AppContext.MULTI_SEARCH)
                }
            }
        }
    }

    fun search(summoner: Summoner) {
        if (!canSearch()) {
            return
        }
        appToolBar.root.matchHistoryContext.children.clear()
        appToolBar.root.switchContext(AppContext.MATCH_HISTORY)
        appToolBar.root.executor.submit(UpdateOverviewTask(appToolBar.root.overviewContext, summoner))
        appToolBar.root.executor.submit(UpdateMatchHistoryTask(appToolBar.root.matchHistoryContext, summoner))
        appToolBar.root.executor.submit(UpdateLiveGameTask(summoner))
    }

    private fun canSearch(): Boolean {
        if (Constants.DEBUG)
            return true
        if ((searchCooldown + 15000L) > System.currentTimeMillis()) {
            OphieGG.sendNotification("Please wait another ${((searchCooldown + 15000L - System.currentTimeMillis()) / 1000)} seconds before making another search.")
            return false
        }
        searchCooldown = System.currentTimeMillis()
        return true
    }

}