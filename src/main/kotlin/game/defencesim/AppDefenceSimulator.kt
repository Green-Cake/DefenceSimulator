package game.defencesim

import game.defencesim.render.Renderer
import game.defencesim.util.IntPos
import game.defencesim.util.Position
import game.defencesim.weapon.WeaponShip
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.Pane
import javafx.stage.Stage

class AppDefenceSimulator : Application() {

    companion object {

        lateinit var INSTANCE: AppDefenceSimulator
            private set

        const val INITIAL_WIDTH = 1280.0
        const val INITIAL_HEIGHT = 960.0//!720

        const val VERSION = "0.1.0"

    }

    lateinit var primaryStage: Stage
        private set

    val canvas = Canvas(INITIAL_WIDTH, INITIAL_HEIGHT)

    val renderer = Renderer(canvas.graphicsContext2D)

    override fun start(primaryStage: Stage) {

        INSTANCE = this

        this.primaryStage = primaryStage

        val pane = Pane(canvas)

        primaryStage.title = "Defence Simulator v$VERSION"

        primaryStage.scene = Scene(pane, 1280.0, 960.0)

        primaryStage.scene.setOnKeyPressed {
            DeviceManager.pressed(it.code)
        }

        primaryStage.scene.setOnKeyReleased {
            DeviceManager.released(it.code)
        }

        primaryStage.scene.setOnMouseClicked {
            DeviceManager.clickedAt(it.button, Position(it.sceneX / primaryStage.scene.width, it.sceneY / primaryStage.scene.height))
        }

        primaryStage.show()

        val w = 100
        val h = 100

        val sceneMain = GameSceneMain(MapData(IntArray(w*h) { 1 }, setOf(WeaponShip(IntPos(2, 2), GameSceneMain.PlayerType.JAPAN)), w, h))

        sceneMain.start()

    }

}