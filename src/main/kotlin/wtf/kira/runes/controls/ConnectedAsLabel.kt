package wtf.kira.runes.controls

import javafx.geometry.Pos
import javafx.scene.control.Label

class ConnectedAsLabel: Label() {

    init {
        text = "Open League client to connect"
        id = "LoggedInAs"
        maxWidth = 210.0
        minWidth = 210.0
        alignment = Pos.CENTER
    }

    fun setConnectedAs(displayName: String?) {
        text = if (displayName == null)
            "Open League client to connect"
        else
            "Connected as: $displayName"
    }
}