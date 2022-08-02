package wtf.kira.runes.controls

import javafx.scene.control.ChoiceBox
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard

class LeagueShardChoiceBox(appToolBar: AppToolBar): ChoiceBox<LeagueShard>() {

    init {
        items.addAll(LeagueShard.getDefaultPlatforms())
        selectionModel.select(LeagueShard.NA1)
        maxHeight = 30.0
        maxWidth = 67.0
    }
}