package game.defencesim

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

        primaryStage.show()

        val sceneMain = GameSceneMain()

        sceneMain.start()

    }

}