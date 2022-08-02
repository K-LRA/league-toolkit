package wtf.kira.runes.utils

import wtf.kira.runes.OphieGG
import java.net.URL

class Resources {

    companion object {

        fun getResource(fileName: String): URL? {
            return OphieGG::class.java.getResource(fileName)
        }

        fun getResourcePath(fileName: String): String {
            return OphieGG::class.java.getResource(fileName)?.toExternalForm() ?: ""
        }

    }

}