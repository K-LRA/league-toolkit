package wtf.kira.runes.controls

import javafx.scene.image.Image
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner

class PlayerImage(val image: Image, val summonerName: String, val shard: LeagueShard) {

    fun getSummoner(): Summoner? {
        val summoner = Summoner.byName(shard, summonerName)
        if (summoner != null)
            return summoner
        return null
    }

}