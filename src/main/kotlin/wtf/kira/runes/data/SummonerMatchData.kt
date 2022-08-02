package wtf.kira.runes.data

import javafx.scene.image.Image
import no.stelar7.api.r4j.basic.constants.types.lol.TeamType
import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner
import wtf.kira.runes.league.MatchHistoryRune
import kotlin.math.ceil
import kotlin.math.roundToInt

class SummonerMatchData(private val summoner: Summoner, private val summonerInMatch: MatchParticipant, private val match: LOLMatch) {

    fun getKillParticipation(): Int {
        var teamKills = 0
        for (player in match.participants) {
            if (player.team == summonerInMatch.team) {
                teamKills += player.kills
            }
        }
        if (teamKills > 0) {
            return ceil(((getTotalKills().toDouble() / teamKills) * 100)).toInt()
        }
        return -1
    }

    fun didMostDamage(): Boolean {
        val summonerDamage = summonerInMatch.totalDamageDealtToChampions
        var mostDamage = 0
        for (player in match.participants) {
            if (player.team == summonerInMatch.team) {
                val dmg = player.totalDamageDealtToChampions
                if (dmg > mostDamage)
                    mostDamage = dmg
            }
        }
        return summonerDamage == mostDamage
    }

    fun getTimestamp(): Long {
        return match.gameEndTimestamp;
    }

    fun getDamageDealtToChampions(): Int {
        return summonerInMatch.totalDamageDealtToChampions
    }

    fun getChampionDamageDealtPercent(): Int {
        return ((getDamageDealtToChampions().toDouble() / getTeamDamageDealttoChampions().toDouble()) * 100.0).roundToInt()
    }

    fun getTeamDamageDealttoChampions(): Int {
        var teamDamage = 0
        for (player in match.participants) {
            if (player.team == summonerInMatch.team) {
                teamDamage += player.totalDamageDealtToChampions
            }
        }
        return teamDamage
    }

    fun didWin(): Boolean {
        return summonerInMatch.didWin()
    }

    fun getChampionName(): String {
        return summonerInMatch.championName
    }

    fun getKills(): Int {
        return summonerInMatch.kills
    }

    fun getDeaths(): Int {
        return summonerInMatch.deaths
    }

    fun getAssists(): Int {
        return summonerInMatch.assists
    }

    fun getTotalKills(): Int {
        return (getKills() + getAssists())
    }

    fun getChampionLevel(): Int {
        return summonerInMatch.championLevel
    }

    fun getTotalCS(): Int {
        return summonerInMatch.totalMinionsKilled + summonerInMatch.neutralMinionsKilled
    }

    fun getPlayers(teamType: TeamType): MutableList<MatchParticipant> {
        val team = mutableListOf<MatchParticipant>()
        for (player in match.participants) {
            if (player == null || player.team != teamType)
                continue
            team.add(player)
        }
        return team
    }

    fun getImageListForInventoryItems(): List<Image> {
        val imageList = mutableListOf<Image>()
        for (i in 0..6) {
            imageList.add(getItemForInventorySlot(i).image)
        }
        return imageList
    }

    private fun getItem(itemId: Int): GameItem {
        return GameItem(itemId)
    }

    private fun getItemForInventorySlot(index: Int): GameItem {
        var id = 0

        when (index) {

            1 -> id = summonerInMatch.item0
            2 -> id = summonerInMatch.item1
            3 -> id = summonerInMatch.item2
            4 -> id = summonerInMatch.item3
            5 -> id = summonerInMatch.item4
            6 -> id = summonerInMatch.item5

        }
        return GameItem(id)
    }

    fun getTrinketItem(): Int {
        return summonerInMatch.item6
    }

    fun getTotalWardsPlaced(): Int {
        return summonerInMatch.wardsPlaced + getControlWardsBought()
    }

    fun getControlWardsBought(): Int {
        return summonerInMatch.visionWardsBoughtInGame
    }

    fun getVisionScore(): Int {
        return summonerInMatch.visionScore
    }

    fun getCSPerMinute(): Double {
        return ((getTotalCS() / (getGameDurationSeconds() / 60.0)) * 10.0).roundToInt() / 10.0
    }

    fun getKDA(): Double  {
        val totalKills = getTotalKills().toDouble()
        val deaths = getDeaths().toDouble()
        return ((totalKills / deaths) * 10.0).roundToInt() / 10.0
    }

    fun getKeystone(): MatchHistoryRune {
        return MatchHistoryRune(summonerInMatch.perks.perkStyles[0].selections[0].perk)
    }

    fun getPrimaryRune1(): MatchHistoryRune {
        return MatchHistoryRune(summonerInMatch.perks.perkStyles[0].selections[1].perk)
    }

    fun getPrimaryRune2(): MatchHistoryRune {
        return MatchHistoryRune(summonerInMatch.perks.perkStyles[0].selections[2].perk)
    }

    fun getPrimaryRune3(): MatchHistoryRune {
        return MatchHistoryRune(summonerInMatch.perks.perkStyles[0].selections[3].perk)
    }

    fun getSecondaryRune1(): MatchHistoryRune {
        return MatchHistoryRune(summonerInMatch.perks.perkStyles[1].selections[0].perk)
    }

    fun getSecondaryRune2(): MatchHistoryRune {
        return MatchHistoryRune(summonerInMatch.perks.perkStyles[1].selections[1].perk)
    }

    fun getSummonerSpell1(): SummonerSpell {
        return SummonerSpell(summonerInMatch.summoner1Id)
    }

    fun getSummonerSpell2(): SummonerSpell {
        return SummonerSpell(summonerInMatch.summoner2Id)
    }

    private fun getGameDurationSeconds(): Long {
        return match.gameDurationAsDuration.seconds
    }

    fun getGameDurationMinutes(): Int {
        return (getGameDurationSeconds() / 60).toInt()
    }

    fun getGameModeName(): String {
        val gameModeName: String = if (match.gameMode.prettyName() == "Classic") {
            when (val queueName = match.queue.prettyName()) {
                "5v5 Dynamic Ranked Solo Queue" -> "Ranked Solo"
                "5v5 Dynamic Queue" -> "Normal"
                "5v5 Ranked Flex Queue" -> "Ranked Flex"
                else -> queueName
            }
        } else {
            return match.gameMode.prettyName()
        }
        return gameModeName
    }
}