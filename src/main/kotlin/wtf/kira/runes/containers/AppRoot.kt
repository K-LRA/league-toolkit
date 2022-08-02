package wtf.kira.runes.containers

import javafx.scene.input.KeyCode
import javafx.scene.layout.*
import wtf.kira.runes.OphieGG
import wtf.kira.runes.contexts.*
import wtf.kira.runes.controls.AppToolBar
import wtf.kira.runes.utils.Constants
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor


class AppRoot: BorderPane() {

    val executor = Executors.newFixedThreadPool(6) as ThreadPoolExecutor

    val appToolBar = AppToolBar(this)

    private val matchHistoryScrollPane = MatchHistoryScrollPane(this)

    val matchHistoryContext = MatchHistoryContext(this)

    val overviewBanner = OverviewBannerVBox(this)

    val overviewContext = OverviewContext(this)

    val multiSearchContext = MultiSearchContext(this)

    val champSelectContext = ChampSelectContext(this)

    val liveGameContext = LiveGameContext(this)

    init {
        id = "appRootContainer"
        top = appToolBar
        left = overviewBanner
        center = overviewContext
        setPrefSize(Constants.APP_WIDTH, Constants.APP_HEIGHT)
        matchHistoryScrollPane.content = matchHistoryContext
        setOnKeyPressed {
            when (it.code) {
                KeyCode.F9 -> {
                    Constants.CONSOLE_ENABLED = !Constants.CONSOLE_ENABLED
                    OphieGG.sendNotification("Console ${if (Constants.CONSOLE_ENABLED) "enabled" else "disabled"}.")
                }
                KeyCode.F10 -> OphieGG.openConsole()
                else -> {}
            }
        }
    }

    fun switchContext(context: AppContext) {
        when (context) {

            AppContext.OVERVIEW -> {
                center = overviewContext
                appToolBar.tbOverview.selectedProperty().set(true)
            }

            AppContext.MATCH_HISTORY -> {
                center = matchHistoryScrollPane
                appToolBar.tbMatchHistory.selectedProperty().set(true)
            }

            AppContext.CHAMP_SELECT -> {
                center = champSelectContext
                appToolBar.tbImportRunes.selectedProperty().set(true)
            }

            AppContext.MULTI_SEARCH -> {
                center = multiSearchContext
                appToolBar.tbMultiSearch.selectedProperty().set(true)
            }

            AppContext.LIVE_GAME -> {
                center = liveGameContext
                appToolBar.tbLiveGame.selectedProperty().set(true)
            }
        }
    }
}