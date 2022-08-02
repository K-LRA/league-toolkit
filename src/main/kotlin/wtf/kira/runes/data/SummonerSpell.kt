package wtf.kira.runes.data

import javafx.scene.image.Image
import wtf.kira.runes.utils.LeagueUtils
import wtf.kira.runes.utils.Resources

class SummonerSpell(private val id: Int) {

    private val name: String = if (id == 0) "placeholder" else LeagueUtils.dDragonApi.getSummonerSpell(id).name

    val image: Image get() = if (id != 0) Image(LeagueUtils.getSummonerSpellImageURLForName(name)) else Image(Resources.getResourcePath("summoner_spell_placeholder.png"))

}