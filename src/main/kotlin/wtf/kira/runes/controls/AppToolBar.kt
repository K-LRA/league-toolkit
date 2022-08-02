package wtf.kira.runes.controls

import javafx.geometry.Insets
import javafx.scene.control.ToggleGroup
import javafx.scene.control.ToolBar
import javafx.scene.input.KeyCode
import wtf.kira.runes.containers.AppRoot
import wtf.kira.runes.controls.button.*
import wtf.kira.runes.tasks.ChampSelectTask

class AppToolBar(val root: AppRoot) : ToolBar() {

    val tgToolBar = ToggleGroup()

    val tbOverview = OverviewButton(this, tgToolBar)
    val tbMatchHistory = MatchHistoryButton(this, tgToolBar)
    val tbImportRunes = ImportRunesButton(this, tgToolBar)
    val tbLiveGame = LiveGameButton(this, tgToolBar)
    val tbMultiSearch = MultiSearchButton(this, tgToolBar)

    val lblConnectedAs = ConnectedAsLabel()

    val tfSummonerSearch = SummonerSearchTextField(this)

    val cbQueueType = QueueTypeChoiceBox(this)

    val cbSelectLeagueShard = LeagueShardChoiceBox(this)

    val btnSummonerSearch = SummonerSearchButton(this)

    init {
        paddingProperty().set(Insets(15.0, 15.0, 15.0, 15.0))
        items.addAll(
            tbOverview,
            tbMatchHistory,
            tbImportRunes,
            tbLiveGame,
            tbMultiSearch,
            lblConnectedAs,
            tfSummonerSearch,
            cbQueueType,
            cbSelectLeagueShard,
            btnSummonerSearch
        )
        tgToolBar.selectToggle(tbOverview)

        setOnKeyPressed {
            if (it.code == KeyCode.BACK_QUOTE) {
                login()
            } else {
                for (item in items) {
                    if (item is MenuToggleButton) {
                        if (item.keyCode != null)
                            if (it.code == item.keyCode) {
                                tgToolBar.selectToggle(item)
                                item.run()
                            }
                    }
                    if (item is SummonerSearchButton) {
                        if (it.code == KeyCode.ENTER) {
                            btnSummonerSearch.search()
                        }
                    }
                }
            }
        }
    }

    private fun login() {
        root.executor.submit(ChampSelectTask(root))
    }
}