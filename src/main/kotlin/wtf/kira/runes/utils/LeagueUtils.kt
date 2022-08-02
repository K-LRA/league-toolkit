package wtf.kira.runes.utils

import com.stirante.lolclient.ClientApi
import com.stirante.lolclient.ClientConnectionListener
import no.stelar7.api.r4j.basic.APICredentials
import no.stelar7.api.r4j.basic.cache.impl.FileSystemCacheProvider
import no.stelar7.api.r4j.basic.calling.DataCall
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType
import no.stelar7.api.r4j.basic.constants.types.lol.TierDivisionType
import no.stelar7.api.r4j.impl.R4J
import no.stelar7.api.r4j.impl.lol.raw.DDragonAPI
import no.stelar7.api.r4j.pojo.lol.league.LeagueEntry
import no.stelar7.api.r4j.pojo.lol.staticdata.perk.StaticPerk
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner
import wtf.kira.runes.OphieGG
import wtf.kira.runes.data.LeagueImageType
import wtf.kira.runes.tasks.ChampSelectTask

class LeagueUtils {

    companion object {

        val lcuApi = ClientApi()

        val dDragonApi = DDragonAPI.getInstance()

        val r4j = R4J(APICredentials(Constants.API_KEY))

        fun initialize() {
            DataCall.setCacheProvider(FileSystemCacheProvider())
            initLcuApi()
        }

        private fun initLcuApi() {
            lcuApi.addClientConnectionListener(object: ClientConnectionListener {

                override fun onClientConnected() {
                    val root = OphieGG.root
                    root.executor.submit(ChampSelectTask(root))
                }

                override fun onClientDisconnected() {
                    ChampSelectTask.stage = ChampSelectTask.Stage.STOPPED
                }
            })
        }

        fun getLeagueEntry(summoner: Summoner, gameQueueType: GameQueueType): LeagueEntry? {
            for (entry in summoner.leagueEntry) {
                if (entry != null && entry.queueType == gameQueueType)
                    return entry
            }
            return null
        }

        fun getBestChampionId(summoner: Summoner): Int {
            val masteries = summoner.championMasteries
            if (masteries == null || masteries.isEmpty())
                return 266 //Aatrox
            return summoner.championMasteries[0].championId
        }

        fun getRank(leagueEntry: LeagueEntry?): TierDivisionType {
            if (leagueEntry == null)
                return TierDivisionType.UNRANKED
            return leagueEntry.tierDivisionType
        }

        fun getMiniCrestForRank(rankName: String): String {
            return "https://raw.communitydragon.org/latest/plugins/rcp-fe-lol-static-assets/global/default/images/ranked-mini-crests/${rankName.lowercase()}.png"
        }

        fun getMiniCrestForRank(tier: TierDivisionType): String {
            return "https://raw.communitydragon.org/latest/plugins/rcp-fe-lol-static-assets/global/default/images/ranked-mini-crests/${if (tier == TierDivisionType.UNRANKED) "unranked" else tier.tier.lowercase()}.png"
        }

        fun getSummonerSpellImageURLForName(summonerSpellName: String): String {
            var name = summonerSpellName
            if (name == "Ignite")
                name = "Dot"
            if (name == "Ghost")
                name = "Haste"
            if (name == "Clarity")
                name = "Mana"
            if (name == "To the King!")
                name = "PoroRecall"
            if (name == "Poro Toss")
                name = "PoroThrow"
            if (name == "Cleanse")
                name = "Boost"
            if (name == "Mark")
                name = "Snowball"
            return "http://ddragon.leagueoflegends.com/cdn/${Constants.CURRENT_PATCH}/img/spell/Summoner$name.png"
        }

        fun getPerkImageURL(staticPerk: StaticPerk): String {
            val link = staticPerk.icon.substringAfter("perk-images/")
            return "https://raw.communitydragon.org/latest/game/assets/perks/$link".lowercase()
        }

        fun getItemImageURLForId(itemId: Int): String {
            return "http://ddragon.leagueoflegends.com/cdn/${Constants.CURRENT_PATCH}/img/item/$itemId.png"
        }

        fun getImageURLForChampionName(championName: String, imageType: LeagueImageType): String {
            val sb = StringBuilder().append("http://ddragon.leagueoflegends.com/cdn/")

            if (imageType == LeagueImageType.PORTRAIT)
                sb.append(Constants.CURRENT_PATCH + "/")

            sb.append("img/champion/" + imageType.type)

            if (imageType != LeagueImageType.PORTRAIT)
                sb.append("/")

            sb.append(getFormattedNameForChampion(championName))

            if (imageType == LeagueImageType.PORTRAIT) {
                sb.append(".png")
            } else {
                sb.append("_0.jpg")
            }
            var string = sb.toString()

            if (string.contains("FiddleSticks") && imageType == LeagueImageType.PORTRAIT)
                string = string.replace("FiddleSticks", "Fiddlesticks")
            return string
        }

        fun getRuneTreeImageURL(staticPerk: StaticPerk): String {
            var tree = staticPerk.icon.substringAfter("perk-images/Styles/").substringBefore("/").lowercase()

            if (tree == "inspiration")
                tree = "whimsy"

            var prefix = ""


            when (tree) {
                "domination" -> prefix = "7200_"
                "precision" -> prefix = "7201_"
                "sorcery" -> prefix = "7202_"
                "whimsy" -> prefix = "7203_"
                "resolve" -> prefix = "7204_"
            }

            return "https://raw.communitydragon.org/latest/game/assets/perks/styles/$prefix$tree.png"
        }

        fun getProfileIconURLForId(iconId: Int): String {
            var id = iconId
            if (iconId == 495)
                id = 29
            return "http://ddragon.leagueoflegends.com/cdn/${Constants.CURRENT_PATCH}/img/profileicon/$id.png"
        }

        fun getSplashImageURLForChampionName(championName: String): String {
            val formattedName = getFormattedNameForChampion(championName)
            val splashId: Int = when (formattedName) {

                "Akali" -> 0
                "Ahri" -> 27
                "Ekko" -> 1
                "Evelynn" -> 24
                "Vayne" -> 13
                "Gwen" -> 11
                "Pyke" -> 44
                "Leblanc" -> 33
                "Katarina" -> 12
                "Diana" -> 25
                "Cassiopeia" -> 9
                "Yuumi" -> 19
                "Yone" -> 19
                "Yasuo" -> 54
                "Riven" -> 34
                "Irelia" -> 26
                "Yorick" -> 4
                "Zed" -> 13
                "Shen" -> 40
                "Qiyana" -> 2
                "Udyr" -> 5
                "Nami" -> 15
                "Draven" -> 20
                "Vi" -> 29
                "Vex" -> 1
                "Kaisa" -> 16
                "Morgana" -> 39

                else -> 0

            }
            return "http://ddragon.leagueoflegends.com/cdn/img/champion/splash/${formattedName}_$splashId.jpg"
        }

        fun getFormattedNameForChampion(championName: String): String {
            var formattedName = championName

            if (formattedName == "Wukong")
                return "MonkeyKing"

            formattedName = formattedName
                .replace(".", "")
                .replace(" ", "")
                .replace("'", "")

            when (formattedName) {

                "ChoGath" -> formattedName = "Chogath"
                "Masteryi" -> formattedName = "MasterYi"
                "Tahmkench" -> formattedName = "TahmKench"
                "KaiSa" -> formattedName = "Kaisa"
                "KhaZix" -> formattedName = "Khazix"
                "LeBlanc" -> formattedName = "Leblanc"
                "VelKoz" -> formattedName = "Velkoz"
                "Wukong" -> formattedName = "MonkeyKing"
                "Nunu&Willump" -> formattedName = "Nunu"
                "RenataGlasc" -> formattedName = "Renata"
                "BelVeth" -> formattedName = "Belveth"

            }
            return formattedName
        }

        fun getSplashURLForChampionId(championId: Int): String {
            val championName = dDragonApi.getChampion(championId).name
            return getSplashImageURLForChampionName(championName)
        }

        fun getRankedCrestImageURL(tierDivisionType: TierDivisionType): String {
            var tier = tierDivisionType.tier
            if (tier == null)
                tier = "unranked"
            return "https://raw.communitydragon.org/latest/plugins/rcp-fe-lol-shared-components/global/default/${tier.lowercase()}.png"
        }

        fun getPrestigeBorderURLForSummonerLevel(level: Int): String {
            var themeId = 0
            if (level >= 500)
                themeId = 21
            if (level in 499 downTo 475) {
                themeId = 20
            } else if (level in 474 downTo 450) {
                themeId = 19
            } else if (level in 449 downTo 425) {
                themeId = 18
            } else if (level in 424 downTo 400) {
                themeId = 17
            } else if (level in 399 downTo 375) {
                themeId = 16
            } else if (level in 374 downTo 350) {
                themeId = 15
            } else if (level in 349 downTo 325) {
                themeId = 14
            } else if (level in 324 downTo 300) {
                themeId = 13
            } else if (level in 299 downTo 275) {
                themeId = 12
            } else if (level in 274 downTo 250) {
                themeId = 11
            } else if (level in 249 downTo 225) {
                themeId = 10
            } else if (level in 224 downTo 200) {
                themeId = 9
            } else if (level in 199 downTo 175) {
                themeId = 8
            } else if (level in 174 downTo 150) {
                themeId = 7
            } else if (level in 149 downTo 125) {
                themeId = 6
            } else if (level in 124 downTo 100) {
                themeId = 5
            } else if (level in 99 downTo 75) {
                themeId = 4
            } else if (level in 74 downTo 50) {
                themeId = 3
            } else if (level in 49 downTo 25) {
                themeId = 2
            } else if (level in 24 downTo 1) {
                themeId = 1
            }
            return "https://raw.communitydragon.org/pbe/plugins/rcp-fe-lol-static-assets/global/default/images/uikit/themed-borders/theme-$themeId-border.png"
        }
    }
}