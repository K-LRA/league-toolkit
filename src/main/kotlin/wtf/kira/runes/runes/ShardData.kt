package wtf.kira.runes.runes

enum class ShardData(val shards: Array<Shard>) {

    OFFENCE(arrayOf(
        Shard(5008, "Offence: Adaptive Force", "https://raw.communitydragon.org/12.7/game/assets/perks/statmods/statmodsadaptiveforceicon.png"),
        Shard(5005, "Offence: Attack Speed", "https://raw.communitydragon.org/12.7/game/assets/perks/statmods/statmodsattackspeedicon.png"),
        Shard(5007, "Offence: Ability Haste", "https://raw.communitydragon.org/12.7/game/assets/perks/statmods/statmodscdrscalingicon.png"))),

    FLEX(arrayOf(
        Shard(5008, "Flex: Adaptive Force", "https://raw.communitydragon.org/12.7/game/assets/perks/statmods/statmodsadaptiveforceicon.png"),
        Shard(5002, "Flex: Armor", "https://raw.communitydragon.org/12.7/game/assets/perks/statmods/statmodsarmoricon.png"),
        Shard(5003, "Flex: Magic Resist", "https://raw.communitydragon.org/12.7/game/assets/perks/statmods/statmodsmagicresicon.png"))),

    DEFENCE(arrayOf(
        Shard(5001, "Defence: Health", "https://raw.communitydragon.org/12.7/game/assets/perks/statmods/statmodshealthscalingicon.png"),
        Shard(5002, "Defence: Armor", "https://raw.communitydragon.org/12.7/game/assets/perks/statmods/statmodsarmoricon.png"),
        Shard(5003, "Defence: Magic Resist", "https://raw.communitydragon.org/12.7/game/assets/perks/statmods/statmodsmagicresicon.png")));

    companion object {

        val values = values()

        fun getIdForShardName(shardName: String): Int {
            for (value in values) {
                for (shard in value.shards) {
                    if (shard.shardName == shardName)
                        return shard.id
                }
            }
            return 0
        }

        fun getShardForName(shardName: String): Shard {
            for (value in values) {
                for (shard in value.shards) {
                    if (shard.shardName == shardName)
                        return shard
                }
            }
            return values[0].shards[0]
        }

    }
}