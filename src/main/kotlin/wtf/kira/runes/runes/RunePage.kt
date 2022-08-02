package wtf.kira.runes.runes

import generated.LolPerksPerkPageResource
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType
import wtf.kira.runes.utils.Constants

class RunePage(val championName: String, val gameMode: GameQueueType, val data: RunePageData): LolPerksPerkPageResource() {

    var role = ""

    var keystone: Rune

    var primaryRunes = mutableListOf<Rune>()
    var secondaryRunes = mutableListOf<Rune>()
    var shards = listOf<Shard>()

    var primaryTree: RuneTreeData

    var secondaryTree: RuneTreeData

    init {

        role = data.assignedPosition

        if (role == "Middle")
            role = "Mid"

        keystone =
            RuneTreeData.getRuneForName(data.perks[0])

        primaryRunes = mutableListOf(
            RuneTreeData.getRuneForName(data.perks[1]),
            RuneTreeData.getRuneForName(data.perks[2]),
            RuneTreeData.getRuneForName(data.perks[3]))

        secondaryRunes = mutableListOf(
            RuneTreeData.getRuneForName(data.perks[4]),
            RuneTreeData.getRuneForName(data.perks[5]))

        println("perks4: ${data.perks[4]} perks5: ${data.perks[5]}")
        println("secondaryrunes: 0 ${secondaryRunes[0]} 1 ${secondaryRunes[1]}")

        shards = listOf(
            ShardData.getShardForName(data.perks[6]),
            ShardData.getShardForName(data.perks[7]),
            ShardData.getShardForName(data.perks[8]))

        primaryTree = RuneTreeData.getTreeForRuneName(keystone.runeName)!!
        secondaryTree = RuneTreeData.getTreeForRuneName(secondaryRunes[0].runeName)!!

        if (primaryTree != null && secondaryTree != null) {

            name = when (gameMode) {
                GameQueueType.ARAM_5X5 -> {
                    "${Constants.APP_NAME}: $championName ARAM"
                }
                GameQueueType.URF_5X5 -> {
                    "${Constants.APP_NAME}: $championName URF"
                }
                else -> {
                    "${Constants.APP_NAME}: $championName $role"
                }
            }

            primaryStyleId = primaryTree.treeId
            selectedPerkIds = listOf(
                keystone.id,
                primaryRunes[0].id,
                primaryRunes[1].id,
                primaryRunes[2].id,
                secondaryRunes[0].id,
                secondaryRunes[1].id,
                shards[0].id,
                shards[1].id,
                shards[2].id
            )
            subStyleId = secondaryTree.treeId
            current = true
            println("${keystone.id}, ${primaryRunes[0].id}, ${primaryRunes[1].id}, ${primaryRunes[2].id}, ${secondaryRunes[0].id}, ${secondaryRunes[1].id}, ${shards[0].id}, ${shards[1].id}, ${shards[2].id}")
        }

    }
}