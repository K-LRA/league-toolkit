package wtf.kira.runes.controls

import javafx.scene.control.TextField

class SummonerSearchTextField(appToolBar: AppToolBar): TextField() {

    init {
        promptText = "Search Summoner"
        maxHeight = 30.0
    }

    override fun paste() {
        super.paste()
        text = text.replace(" joined the lobby", ",")
    }
}