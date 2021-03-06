package game.defencesim.render

import game.defencesim.AppDefenceSimulator
import game.defencesim.util.Position
import game.defencesim.util.Scale
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.Image
import javafx.scene.paint.Paint
import javafx.scene.text.Font

class Renderer(val gc: GraphicsContext, val aspectRatio: Scale = Scale(4.0, 3.0)) {

    companion object {
        const val RATIO_W = 1.0
        const val RATIO_H = 1.0

        fun getSquareScale(w: Double, aspectRatio: Scale = Scale(4.0, 3.0)) = Scale(w, w / aspectRatio.h * aspectRatio.w)

    }

    val canvasWidth get() = gc.canvas.width
    val canvasHeight get() = gc.canvas.height

    var fontFamily = "serif"

    var fill: Paint
        set(value) { gc.fill = value }
        get() = gc.fill

    var stroke: Paint
        set(value) { gc.stroke = value }
        get() = gc.stroke

    var lineWidth: Double
        set(value) { gc.lineWidth = value }
        get() = gc.lineWidth

    var textAlignment
        set(value) { gc.textAlign = value }
        get() = gc.textAlign

    fun getSquareScale(w: Double) = getSquareScale(w, aspectRatio)

    fun update() {

        gc.canvas.width = AppDefenceSimulator.INSTANCE.primaryStage.scene.width
        gc.canvas.height = AppDefenceSimulator.INSTANCE.primaryStage.scene.height

        if(gc.canvas.width * aspectRatio.h > gc.canvas.height * aspectRatio.w) {
            gc.canvas.width = gc.canvas.height / aspectRatio.h * aspectRatio.w
        } else {
            gc.canvas.height = gc.canvas.width / aspectRatio.w * aspectRatio.h
        }

        gc.clearRect(0.0, 0.0, canvasWidth, canvasHeight)

    }

    fun toRelativePos(ax: Double, ay: Double) = Position(ax / canvasWidth * RATIO_W, ay / canvasHeight * RATIO_H)
    fun toRelativePos(abs: Position) = toRelativePos(abs.x, abs.y)
    fun toRelativePos(abs: Scale) = Scale(abs.w / canvasWidth * RATIO_W, abs.h / canvasHeight * RATIO_H)

    fun toAbsolutePos(rx: Double, ry: Double) = Position(rx / RATIO_W * canvasWidth, ry / RATIO_H * canvasHeight)
    fun toAbsolutePos(rel: Position) = toAbsolutePos(rel.x, rel.y)
    fun toAbsoluteScale(rel: Scale) = Scale(rel.w / RATIO_W * canvasWidth, rel.h / RATIO_H * canvasHeight)

    fun fillRect(x: Double, y: Double, w: Double, h: Double) {
        val apos = toAbsolutePos(x, y)
        val ascale = toAbsolutePos(w, h)
        gc.fillRect(apos.x, apos.y, ascale.x, ascale.y)
    }

    fun fillRect(pos: Position, scale: Scale) = fillRect(pos.x, pos.y, scale.w, scale.h)

    fun strokeRect(x: Double, y: Double, w: Double, h: Double) {
        val apos = toAbsolutePos(x, y)
        val ascale = toAbsolutePos(w, h)
        gc.strokeRect(apos.x, apos.y, ascale.x, ascale.y)
    }

    fun strokeRect(pos: Position, scale: Scale) = strokeRect(pos.x, pos.y, scale.w, scale.h)

    fun strokeLine(x0: Double, y0: Double, x1: Double, y1: Double) {
        val apos0 = toAbsolutePos(x0, y0)
        val apos1 = toAbsolutePos(x1, y1)
        gc.strokeLine(apos0.x, apos0.y, apos1.x, apos1.y)
    }

    fun strokeLine(pos0: Position, pos1: Position) = strokeLine(pos0.x, pos0.y, pos1.x, pos1.y)

    fun fillText(text: String, x: Double, y: Double, size: Double) {
        val apos = toAbsolutePos(x, y)
        gc.font = Font.font(fontFamily, size)
        gc.fillText(text, apos.x, apos.y)
    }

    fun fillText(text: String, pos: Position, size: Double) = fillText(text, pos.x, pos.y, size)

    fun strokeText(text: String, x: Double, y: Double, size: Double) {
        val apos = toAbsolutePos(x, y)
        gc.font = Font.font(fontFamily, size)
        gc.strokeText(text, apos.x, apos.y)
    }

    fun strokeText(text: String, pos: Position, size: Double) = strokeText(text, pos.x, pos.y, size)

    fun drawImage(image: Image, x: Double, y: Double, w: Double, h: Double) {
        val apos = toAbsolutePos(x, y)
        val ascale = toAbsolutePos(w, h)
        gc.drawImage(image, apos.x, apos.y, ascale.x, ascale.y)
    }

    fun drawImage(image: Image, pos: Position, scale: Scale) = drawImage(image, pos.x, pos.y, scale.w, scale.h)

}