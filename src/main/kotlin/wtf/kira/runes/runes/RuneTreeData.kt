package wtf.kira.runes.runes

enum class RuneTreeData(val treeId: Int, val keystones: Array<Rune>, private val runeSet1: Array<Rune>, private val runeSet2: Array<Rune>, private val runeSet3: Array<Rune>) {

    PRECISION(8000,
        arrayOf(
            Rune(8005, "Press the Attack", "https://raw.communitydragon.org/latest/game/assets/perks/styles/precision/presstheattack/presstheattack.png"),
            Rune(8008, "Lethal Tempo", "https://raw.communitydragon.org/latest/game/assets/perks/styles/precision/lethaltempo/lethaltempotemp.png"),
            Rune(8021, "Fleet Footwork", "https://raw.communitydragon.org/latest/game/assets/perks/styles/precision/fleetfootwork/fleetfootwork.png"),
            Rune(8010, "Conqueror", "https://raw.communitydragon.org/latest/game/assets/perks/styles/precision/conqueror/conqueror.png")),
        arrayOf(
            Rune(9101, "Overheal", "https://raw.communitydragon.org/latest/game/assets/perks/styles/precision/overheal.png"),
            Rune(9111, "Triumph", "https://raw.communitydragon.org/latest/game/assets/perks/styles/precision/triumph.png"),
            Rune(8009, "Presence of Mind", "https://raw.communitydragon.org/latest/game/assets/perks/styles/precision/presenceofmind/presenceofmind.png")),
        arrayOf(
            Rune(9104, "Legend: Alacrity", "https://raw.communitydragon.org/latest/game/assets/perks/styles/precision/legendalacrity/legendalacrity.png"),
            Rune(9105, "Legend: Tenacity", "https://raw.communitydragon.org/latest/game/assets/perks/styles/precision/legendtenacity/legendtenacity.png"),
            Rune(9103, "Legend: Bloodline", "https://raw.communitydragon.org/latest/game/assets/perks/styles/precision/legendbloodline/legendbloodline.png")),
        arrayOf(
            Rune(8014, "Coup de Grace", "https://raw.communitydragon.org/latest/game/assets/perks/styles/precision/coupdegrace/coupdegrace.png"),
            Rune(8017, "Cut Down", "https://raw.communitydragon.org/latest/game/assets/perks/styles/precision/cutdown/cutdown.png"),
            Rune(8299, "Last Stand", "https://raw.communitydragon.org/latest/game/assets/perks/styles/sorcery/laststand/laststand.png"))),

    DOMINATION(8100,
        arrayOf(
            Rune(8112, "Electrocute", "https://raw.communitydragon.org/latest/game/assets/perks/styles/domination/electrocute/electrocute.png"),
            Rune(8124, "Predator", "https://raw.communitydragon.org/latest/game/assets/perks/styles/domination/predator/predator.png"),
            Rune(8128, "Dark Harvest", "https://raw.communitydragon.org/latest/game/assets/perks/styles/domination/darkharvest/darkharvest.png"),
            Rune(9923, "Hail of Blades", "https://raw.communitydragon.org/latest/game/assets/perks/styles/domination/hailofblades/hailofblades.png")),
        arrayOf(
            Rune(8126, "Cheap Shot", "https://raw.communitydragon.org/latest/game/assets/perks/styles/domination/cheapshot/cheapshot.png"),
            Rune(8139, "Taste of Blood", "https://raw.communitydragon.org/latest/game/assets/perks/styles/domination/tasteofblood/greenterror_tasteofblood.png"),
            Rune(8143, "Sudden Impact", "https://raw.communitydragon.org/latest/game/assets/perks/styles/domination/suddenimpact/suddenimpact.png")),
        arrayOf(
            Rune(8136, "Zombie Ward", "https://raw.communitydragon.org/latest/game/assets/perks/styles/domination/zombieward/zombieward.png"),
            Rune(8120, "Ghost Poro", "https://raw.communitydragon.org/latest/game/assets/perks/styles/domination/ghostporo/ghostporo.png"),
            Rune(8138, "Eyeball Collection", "https://raw.communitydragon.org/latest/game/assets/perks/styles/domination/eyeballcollection/eyeballcollection.png")),
        arrayOf(
            Rune(8135, "Treasure Hunter", "https://raw.communitydragon.org/latest/game/assets/perks/styles/domination/treasurehunter/treasurehunter.png"),
            Rune(8134, "Ingenious Hunter", "https://raw.communitydragon.org/latest/game/assets/perks/styles/domination/ingenioushunter/ingenioushunter.png"),
            Rune(8105, "Relentless Hunter", "https://raw.communitydragon.org/latest/game/assets/perks/styles/domination/relentlesshunter/relentlesshunter.png"),
            Rune(8106, "Ultimate Hunter", "https://raw.communitydragon.org/latest/game/assets/perks/styles/domination/ultimatehunter/ultimatehunter.png"))),

    SORCERY(8200,
        arrayOf(
            Rune(8214, "Summon Aery", "https://raw.communitydragon.org/latest/game/assets/perks/styles/sorcery/summonaery/summonaery.png"),
            Rune(8229, "Arcane Comet", "https://raw.communitydragon.org/latest/game/assets/perks/styles/sorcery/arcanecomet/arcanecomet.png"),
            Rune(8230, "Phase Rush", "https://raw.communitydragon.org/latest/game/assets/perks/styles/sorcery/phaserush/phaserush.png")),
        arrayOf(
            Rune(8224, "Nullifying Orb", "https://raw.communitydragon.org/latest/game/assets/perks/styles/sorcery/nullifyingorb/pokeshield.png"),
            Rune(8226, "Manaflow Band", "https://raw.communitydragon.org/latest/game/assets/perks/styles/sorcery/manaflowband/manaflowband.png"),
            Rune(8275, "Nimbus Cloak", "https://raw.communitydragon.org/latest/game/assets/perks/styles/sorcery/nimbuscloak/6361.png")),
        arrayOf(
            Rune(8210, "Transcendence", "https://raw.communitydragon.org/latest/game/assets/perks/styles/sorcery/transcendence/transcendence.png"),
            Rune(8234, "Celerity", "https://raw.communitydragon.org/latest/game/assets/perks/styles/sorcery/celerity/celeritytemp.png"),
            Rune(8233, "Absolute Focus", "https://raw.communitydragon.org/latest/game/assets/perks/styles/sorcery/absolutefocus/absolutefocus.png")),
        arrayOf(
            Rune(8237, "Scorch", "https://raw.communitydragon.org/latest/game/assets/perks/styles/sorcery/scorch/scorch.png"),
            Rune(8232, "Waterwalking", "https://raw.communitydragon.org/latest/game/assets/perks/styles/sorcery/waterwalking/waterwalking.png"),
            Rune(8236, "Gathering Storm", "https://raw.communitydragon.org/latest/game/assets/perks/styles/sorcery/gatheringstorm/gatheringstorm.png"))),

    RESOLVE(8400,
        arrayOf(
            Rune(8437, "Grasp of the Undying", "https://raw.communitydragon.org/latest/game/assets/perks/styles/resolve/graspoftheundying/graspoftheundying.png"),
            Rune(8439, "Aftershock", "https://raw.communitydragon.org/latest/game/assets/perks/styles/resolve/veteranaftershock/veteranaftershock.png"),
            Rune(8465, "Guardian", "https://raw.communitydragon.org/latest/game/assets/perks/styles/resolve/guardian/guardian.png")),
        arrayOf(
            Rune(8446, "Demolish", "https://raw.communitydragon.org/latest/game/assets/perks/styles/resolve/demolish/demolish.png"),
            Rune(8463, "Font of Life", "https://raw.communitydragon.org/latest/game/assets/perks/styles/resolve/fontoflife/fontoflife.png"),
            Rune(8401, "Shield Bash", "https://raw.communitydragon.org/latest/game/assets/perks/styles/resolve/mirrorshell/mirrorshell.png")),
        arrayOf(
            Rune(8429, "Conditioning", "https://raw.communitydragon.org/latest/game/assets/perks/styles/resolve/conditioning/conditioning.png"),
            Rune(8444, "Second Wind", "https://raw.communitydragon.org/latest/game/assets/perks/styles/resolve/secondwind/secondwind.png"),
            Rune(8473, "Bone Plating", "https://raw.communitydragon.org/latest/game/assets/perks/styles/resolve/boneplating/boneplating.png")),
        arrayOf(
            Rune(8451, "Overgrowth", "https://raw.communitydragon.org/latest/game/assets/perks/styles/resolve/overgrowth/overgrowth.png"),
            Rune(8453, "Revitalize", "https://raw.communitydragon.org/latest/game/assets/perks/styles/resolve/revitalize/revitalize.png"),
            Rune(8242, "Unflinching", "https://raw.communitydragon.org/latest/game/assets/perks/styles/sorcery/unflinching/unflinching.png"))),

    INSPIRATION(8300,
        arrayOf(
            Rune(8351, "Glacial Augment", "https://raw.communitydragon.org/latest/game/assets/perks/styles/inspiration/glacialaugment/glacialaugment.png"),
            Rune(8360, "Unsealed Spellbook", "https://raw.communitydragon.org/latest/game/assets/perks/styles/inspiration/unsealedspellbook/unsealedspellbook.png"),
            Rune(8369, "First Strike", "https://raw.communitydragon.org/latest/game/assets/perks/styles/inspiration/firststrike/firststrike.png")),
        arrayOf(
            Rune(8306, "Hextech Flashtraption", "https://raw.communitydragon.org/latest/game/assets/perks/styles/inspiration/hextechflashtraption/hextechflashtraption.png"),
            Rune(8304, "Magical Footwear", "https://raw.communitydragon.org/latest/game/assets/perks/styles/inspiration/magicalfootwear/magicalfootwear.png"),
            Rune(8313, "Perfect Timing", "https://raw.communitydragon.org/latest/game/assets/perks/styles/inspiration/perfecttiming/perfecttiming.png")),
        arrayOf(
            Rune(8321, "Future's Market", "https://raw.communitydragon.org/latest/game/assets/perks/styles/inspiration/futuresmarket/futuresmarket.png"),
            Rune(8316, "Minion Dematerializer", "https://raw.communitydragon.org/latest/game/assets/perks/styles/inspiration/miniondematerializer/miniondematerializer.png"),
            Rune(8345, "Biscuit Delivery", "https://raw.communitydragon.org/latest/game/assets/perks/styles/inspiration/biscuitdelivery/biscuitdelivery.png")),
        arrayOf(
            Rune(8347, "Cosmic Insight", "https://raw.communitydragon.org/latest/game/assets/perks/styles/inspiration/cosmicinsight/cosmicinsight.png"),
            Rune(8410, "Approach Velocity", "https://raw.communitydragon.org/latest/game/assets/perks/styles/resolve/approachvelocity/approachvelocity.png"),
            Rune(8352, "Time Warp Tonic", "https://raw.communitydragon.org/latest/game/assets/perks/styles/inspiration/timewarptonic/timewarptonic.png")));

    fun getAllRunes(): MutableList<Rune> {
        val runes = mutableListOf<Rune>()
        for (rune in runeSet1)
            runes.add(rune)
        for (rune in runeSet2)
            runes.add(rune)
        for (rune in runeSet3)
            runes.add(rune)
        return runes
    }

    fun getSoundPath(): String {
        return "https://raw.communitydragon.org/latest/plugins/rcp-fe-lol-collections/global/default/perks/audio/sfx-perks-dom-keystone-elec.ogg"
    }

    fun getAllRuneSets(): Array<Array<Rune>> {
        return arrayOf(runeSet1, runeSet2, runeSet3)
    }

    fun getSetForRuneName(runeName: String): Array<Rune> {
        for (runeSet in getAllRuneSets()) {
            for (rune in runeSet) {
                if (rune.runeName == runeName)
                    return runeSet
            }
        }
        return emptyArray()
    }

    companion object {

        val values = values()

        fun getTreeForRuneName(runeName: String): RuneTreeData? {
            for (value in values) {
                for (keystone in value.keystones)
                    if (runeName == keystone.runeName)
                        return value
                for (rune in value.getAllRunes())
                    if (runeName == rune.runeName)
                        return value
            }
            return null
        }

        fun getRuneForName(name: String): Rune {
            for (value in values) {
                for (keystone in value.keystones)
                    if (keystone.runeName == name)
                        return keystone
                for (rune in value.getAllRunes())
                    if (rune.runeName == name)
                        return rune
            }
            throw NullPointerException("Cannot find Rune for name $name")
        }

    }

}