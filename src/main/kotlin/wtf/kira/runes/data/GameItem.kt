package wtf.kira.runes.data

import javafx.scene.image.Image
import wtf.kira.runes.utils.LeagueUtils
import wtf.kira.runes.utils.Resources

class GameItem(private val id: Int) {

    val name: String = if (id == 0) "placeholder" else LeagueUtils.dDragonApi.getItem(id).name

    val image: Image get() = if (id != 0) Image(LeagueUtils.getItemImageURLForId(id)) else Image(Resources.getResourcePath("item_placeholder.png"))

}