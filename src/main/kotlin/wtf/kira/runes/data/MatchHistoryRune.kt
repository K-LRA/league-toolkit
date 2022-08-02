package wtf.kira.runes.league

import javafx.scene.image.Image
import wtf.kira.runes.utils.LeagueUtils

class MatchHistoryRune(private val id: Int) {

    fun getName(): String {
        val name = LeagueUtils.dDragonApi.perkPaths[id]?.name
        if (id == 0 || name == null)
            return "placeholder"
        return name
    }

    fun getImage(): Image {
        val image: Image
        val perk = LeagueUtils.dDragonApi.perks[id]
        image = if (id == 0 || perk == null)  {
            Image(System.getProperty("user.dir") + "/data/img/rune_placeholder.png")
        } else
            Image(LeagueUtils.getPerkImageURL(perk))
        return image
    }

    fun getRuneTreeImage(): Image {
        val image: Image
        val perk = LeagueUtils.dDragonApi.perks[id]
        image = if (id == 0 || perk == null)  {
            Image(System.getProperty("user.dir") + "/data/img/rune_tree_placeholder.png")
        } else
            Image(LeagueUtils.getRuneTreeImageURL(perk))
        return image
    }
}