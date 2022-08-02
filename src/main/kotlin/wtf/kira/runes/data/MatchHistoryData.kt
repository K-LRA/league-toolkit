package wtf.kira.runes.data

import javafx.scene.image.Image
import wtf.kira.runes.controls.PlayerImage
import kotlin.properties.Delegates

class MatchHistoryData {

    lateinit var gameResultString: String

    lateinit var gameResultColourString: String

    lateinit var gameModeString: String

    lateinit var gameDurationString: String

    lateinit var championPortraitImage: Image

    lateinit var summonerSpell1Image: Image

    lateinit var summonerSpell2Image: Image

    lateinit var keystoneImage: Image

    lateinit var secondaryTreeImage: Image

    lateinit var killsDeathsAssistsString: String

    lateinit var kdaString: String

    lateinit var kpString: String

    lateinit var levelString: String

    lateinit var csString: String

    lateinit var damageDealtPercentString: String

    lateinit var damageDealtString: String

    var didMostDamage by Delegates.notNull<Boolean>()

    var controlWardsBought by Delegates.notNull<Int>()

    var wardsPlaced by Delegates.notNull<Int>()

    var visionScore by Delegates.notNull<Int>()

    lateinit var itemImageList: List<Image>

    lateinit var redTeamImageList: List<PlayerImage>

    lateinit var blueTeamImageList: List<PlayerImage>

}