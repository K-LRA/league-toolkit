package wtf.kira.runes.containers

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.layout.VBox
import javafx.stage.Stage
import wtf.kira.runes.utils.Constants

class NotificationStage: Stage() {

    private val container = VBox()

    private val notificationScene = Scene(container, 350.0, 100.0)

    private val lblNotification = Label()

    private val btnOk = Button("Ok")

    init {
        icons.add(Image(Constants.DEFAULT_FAVICON))
        title = Constants.APP_NAME
        isResizable = false

        container.alignment = Pos.CENTER
        VBox.setMargin(lblNotification, Insets(0.0, 0.0, 10.0, 0.0))

        scene = notificationScene
        container.children.addAll(lblNotification, btnOk)
        btnOk.setOnMouseClicked {
            hide()
        }
        btnOk.setOnKeyPressed {
            hide()
        }
    }

    fun sendNotification(message: String) {
        lblNotification.text = message
        if (isShowing) {
            requestFocus()
            return
        }
        show()
    }
}