package wtf.kira.runes.utils

import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import wtf.kira.runes.runes.RunePageData

class UGGScraper {

    companion object {

        fun scrape(championName: String, gameMode: GameQueueType, assignedPosition: String): RunePageData {
            val champName = if (championName == "Renata Glasc") "Renata" else championName
            var position = assignedPosition

            if (position == "utility")
                position = "support"
            if (position == "bottom")
                position = "adc"

            val doc: Document

            when (gameMode) {
                GameQueueType.RANKED_SOLO_5X5 -> {
                    doc = Jsoup.connect("https://u.gg/lol/champions/$champName/build?role=$position").timeout(10000)
                        .get()
                }
                GameQueueType.ARAM_5X5 -> {
                    doc = Jsoup.connect("https://u.gg/lol/champions/aram/$champName-aram").timeout(10000)
                        .get()
                }

                GameQueueType.URF_5X5 -> {
                    doc = Jsoup.connect("https://u.gg/lol/champions/urf/$champName-urf").timeout(10000)
                        .get()
                }
                else -> {
                    doc = Jsoup.connect("https://u.gg/lol/champions/$champName/build?role=$position").timeout(10000)
                        .get()
                }
            }

            position = if (position == "adc") "ADC" else position.capitalize()

            val eles = doc.select("div.recommended-build_runes")

            val title = eles.select("div.main-header .content-section_blurb").html()
            val winRate = eles.select("span.win-rate").html()
            val matchCount = eles.select("span.matches").html()

            val trees = arrayOf<Elements>(eles.select("div.primary-tree .perks"), eles.select("div.secondary-tree .perks"))
            val perks = mutableListOf<String>()

            for (branch in trees) {
                for (perk in branch) {
                    val p = perk.select("div.perk.perk-active")
                    if (p.isEmpty())
                        continue
                    val name = p.select("img").attr("alt").replace("The Rune ", "").replace("The Keystone ", "")
                    if (!perks.contains(name)) {
                        perks.add(name)
                    }
                    //val imgUrl = p.select("img").attr("src")
                    //imgUrls.add(imgUrl)
                    //runeHandler.runesData.add(RuneData(name, imgUrl))
                }
            }

            val statShards = eles.select("div.stat-shards-container_v2").select("div.shard.shard-active")

            for ((index, shard) in statShards.withIndex()) {
                if (index < 3) {
                    val sh = shard.select("div.shard-active")
                    var runeName = sh.select("img").attr("alt")

                    when (index) {
                        0 -> {
                            if (runeName == "The Attack Speed Shard")
                                runeName = "Offence: Attack Speed"
                            if (runeName == "The Adaptive Force Shard")
                                runeName = "Offence: Adaptive Force"
                            if (runeName == "The Scaling CDR Shard")
                                runeName = "Offence: Ability Haste"
                        }
                        1 -> {
                            if (runeName == "The Adaptive Force Shard")
                                runeName = "Flex: Adaptive Force"
                            if (runeName == "The Armor Shard")
                                runeName = "Flex: Armor"
                            if (runeName == "The Magic Resist Shard")
                                runeName = "Flex: Magic Resist"
                        }
                        2 -> {
                            if (runeName == "The Scaling Bonus Health Shard")
                                runeName = "Defence: Health"
                            if (runeName == "The Armor Shard")
                                runeName = "Defence: Armor"
                            if (runeName == "The Magic Resist Shard")
                                runeName = "Defence: Magic Resist"
                        }
                    }
                    perks.add(runeName)
                }
            }
            return RunePageData(champName, perks, title, "$winRate $matchCount", position)
        }

    }
}