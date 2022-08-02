package wtf.kira.runes.data

import javafx.scene.image.Image
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard
import no.stelar7.api.r4j.basic.constants.types.lol.TierDivisionType
import wtf.kira.runes.utils.LeagueUtils

class OverviewData {
    var flexTier: TierDivisionType = TierDivisionType.UNRANKED
    var rankedTier: TierDivisionType = TierDivisionType.UNRANKED
    var flexLosses: Int = 0
    var flexWins: Int = 0
    var rankedLosses: Int = 0
    var rankedWins: Int = 0
    var flexLp: Int = 0
    var rankedLp: Int = 0
    var bestChampionId: Int = 266
    private var profileIconId: Int = 0
    var platform: LeagueShard? = null
    var summonerLevel: Int = 1
    var puuid: String? = null
    var name: String? = null

    var profileIconImage: Image? = null
    var splashImage: Image? = null
    var rankedCrestImage: Image? = null
    var flexCrestImage: Image? = null
    var borderImage: Image? = null


    fun setProfileIconIdAndImage(id: Int) {
        profileIconId = id
        profileIconImage = Image(LeagueUtils.getProfileIconURLForId(id))
    }

    fun setSplashImage() {
        splashImage = Image(LeagueUtils.getSplashURLForChampionId(bestChampionId))
    }

    fun setRankedCrestImage() {
        rankedCrestImage = Image(LeagueUtils.getRankedCrestImageURL(rankedTier))
    }

    fun setFlexCrestImage() {
        flexCrestImage = Image(LeagueUtils.getRankedCrestImageURL(flexTier))
    }

    fun setBorderImage() {
        borderImage = Image(LeagueUtils.getPrestigeBorderURLForSummonerLevel(summonerLevel))
    }


}