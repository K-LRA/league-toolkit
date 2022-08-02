package wtf.kira.runes.runes

data class RunePageData(val championName: String,
                        val perks: MutableList<String>,
                        val buildName: String,
                        val winRate: String,
                        val assignedPosition: String)