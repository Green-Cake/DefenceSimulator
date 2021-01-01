package game.defencesim

import game.defencesim.util.Position
import game.defencesim.util.Scale
import javafx.animation.AnimationTimer
import javafx.scene.paint.Color

class GameSceneMain : AnimationTimer() {

    override fun handle(now: Long) {
        onUpdate()
        onRender()
    }

    private fun onUpdate() {

    }

    private fun onRender() = AppDefenceSimulator.INSTANCE.renderer.apply {

        update()

        /*
        0.125
        0.75
        0.125
         */

        val header = 0.0
        val body = 0.125

        //### header

        lineWidth = 2.0

        fill = Color.RED
        fillRect(0.0, header, 1.0, body)

        stroke = Color.BLACK
        strokeLine(0.0, body, 1.0, body)

        fill = Color.GREEN
        fillRect(0.0, body, 1.0, 1 - body)

    }

}