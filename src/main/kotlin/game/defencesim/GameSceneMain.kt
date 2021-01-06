package game.defencesim

import game.defencesim.node.IClickable
import game.defencesim.node.Node
import game.defencesim.node.NodeButton
import game.defencesim.render.Renderer
import game.defencesim.util.IntPos
import game.defencesim.util.Position
import game.defencesim.util.Scale
import game.defencesim.weapon.WeaponRadar
import javafx.animation.AnimationTimer
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseButton
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment

class GameSceneMain(val map: MapData) : AnimationTimer() {

    companion object {

        lateinit var instance: GameSceneMain
            private set

        const val isDebugMode = true

        const val TILES_COUNT_IN_WIDTH = 15.0
        val TILES_COUNT_IN_HEIGHT get() = TILES_COUNT_IN_WIDTH / AppDefenceSimulator.INSTANCE.renderer.aspectRatio.w * AppDefenceSimulator.INSTANCE.renderer.aspectRatio.h

        val TILE_WIDTH get() = 1 / TILES_COUNT_IN_WIDTH
        val TILE_HEIGHT get() = 1 / TILES_COUNT_IN_HEIGHT

        const val SCROLL_SPEED = 0.02

        const val ZOOM_MAX = 1.5
        const val ZOOM_MIN = 0.2

    }

    enum class Mode {

        EDIT,
        PLAY

    }

    enum class PlayerType {
        JAPAN,
        CHINA
    }

    var mode = Mode.PLAY

    var turnCount = 0
    var currentPlayer = PlayerType.JAPAN

    var scrollX = 0.0
    var scrollY = 0.0

    var zoom = 1.0

    val nodes = sortedSetOf<Node>()

    init {

        instance = this

        val iconScale = Renderer.getSquareScale(0.08)
        val buttonNext = NodeButton(Position(1.0 - iconScale.w - 0.01, 0.0 + 0.01), iconScale, 0, Registry.getTextureResource("icon/next.png"), {
            isEnable = mode == Mode.PLAY
        }, ::onNextButtonClicked)

        nodes += buttonNext

    }

    fun getTilePosOn(pos: Position): IntPos {

        val x = (pos.x / zoom + scrollX) * TILES_COUNT_IN_WIDTH
        val y = (pos.y / zoom + scrollY) * TILES_COUNT_IN_HEIGHT

        return IntPos(x.toInt(), y.toInt())

    }

    fun getTilePosForRender(x: Int, y: Int) = Position((x / TILES_COUNT_IN_WIDTH - scrollX) * zoom, (y / TILES_COUNT_IN_HEIGHT - scrollY) * zoom)

    fun getTileScaleForRender() = Scale(TILE_WIDTH * zoom, TILE_HEIGHT * zoom)

    override fun handle(now: Long) {
        onUpdate()
        onRender()
    }

    fun onNextButtonClicked() {

        if(currentPlayer == PlayerType.JAPAN) {

            currentPlayer = PlayerType.CHINA
            return

        }

        currentPlayer = PlayerType.JAPAN
        turnCount++

    }

    private fun onUpdate() {

        DeviceManager.update()

        nodes.forEach(Node::onUpdate)

        when {
            DeviceManager.isPressed(KeyCode.LEFT) -> scrollX -= SCROLL_SPEED / zoom
            DeviceManager.isPressed(KeyCode.RIGHT) -> scrollX += SCROLL_SPEED / zoom
            DeviceManager.isPressed(KeyCode.UP) -> {
                if(DeviceManager.isPressed(KeyCode.SHIFT)) {
                    if (zoom < ZOOM_MAX) {
                        zoom *= 1.01
                    }
                } else
                    scrollY -= SCROLL_SPEED / zoom
            }
            DeviceManager.isPressed(KeyCode.DOWN) -> {
                if(DeviceManager.isPressed(KeyCode.SHIFT)) {
                    if (zoom > ZOOM_MIN) {
                        zoom *= 0.99
                    }
                } else
                    scrollY += SCROLL_SPEED / zoom
            }
            DeviceManager.isPressed(KeyCode.F) -> {

                if(DeviceManager.isPressed(KeyCode.SHIFT)) {

                    zoom = 1.0
                    scrollX = 0.0
                    scrollY = 0.0

                }

            }
            DeviceManager.isGetPressed(KeyCode.F1) -> {
                if(isDebugMode) {

                    mode = if(mode == Mode.PLAY) Mode.EDIT else Mode.PLAY
                    println("DEBUG: Mode changed into $mode")

                }
            }
        }

        //click event
        if(DeviceManager.lastClicked != null) {

            val (b, p) = DeviceManager.lastClicked!!

            val pos = getTilePosOn(p)

            var flag = false

            if(b == MouseButton.PRIMARY) {

                val node = nodes.firstOrNull { p in it }
                node?.onClicked()
                flag = node == null

            }

            if(flag && mode == Mode.EDIT) {

                if(b == MouseButton.MIDDLE) {

                    map.weapons += WeaponRadar(pos, currentPlayer)

                } else {

                    val index = pos.y * map.mapWidth + pos.x

                    if(0 <= index && index <= map.tileMap.size)
                        map.tileMap[index] = if(b == MouseButton.PRIMARY) 2 else 1

                }

            }

        }

    }

    private fun onRender() = AppDefenceSimulator.INSTANCE.renderer.apply {

        update()

        val header = 0.0
        val body = 0.125

        fill = Color.AQUAMARINE
        fillRect(0.0, body, 1.0, 1 - body)

        map.tileMap.forEachIndexed { index, id ->

            val x = map.getXFromIndex(index)
            val y = map.getYFromIndex(index)

            val tile = Registry.tiles[id]!!

            drawImage(tile.getImage(x, y), getTilePosForRender(x, y), getTileScaleForRender())

        }

        for(lx in 0 until map.mapWidth) {
            val x = (lx / TILES_COUNT_IN_WIDTH - scrollX) * zoom
            strokeLine(x, 0.0, x, 1.0)
        }

        for(ly in 0 until map.mapHeight) {
            val y = (ly / TILES_COUNT_IN_HEIGHT - scrollY) * zoom
            strokeLine(0.0, y, 1.0, y)
        }

        map.weapons.forEach { weapon ->

            val x = weapon.position.x
            val y = weapon.position.y

            val pos = getTilePosForRender(x, y)
            val scale = getTileScaleForRender()

            drawImage(weapon.getImage(), pos, scale)

            fill = if(weapon.side == currentPlayer)
                Color.color(0.0, 1.0, 0.0, 0.3)
            else
                Color.color(1.0, 0.0, 0.0, 0.3)

            fillRect(pos, scale)

        }

        //### header

        fill = Color.GRAY
        fillRect(0.0, header, 1.0, body)

        stroke = Color.BLACK
        strokeLine(0.0, body, 1.0, body)

        textAlignment = TextAlignment.LEFT

        when (mode) {
            Mode.PLAY -> {

                fill = Color.WHITE

                fillText("TURN $turnCount | SIDE $currentPlayer", 0.02, 0.05, 32.0)
//                fillText("ZOOM x${String.format("%.2f", zoom)}", 0.02, 0.1, 32.0)

            }
            Mode.EDIT -> {

                lineWidth = 1.0
                fill = Color.YELLOW
                stroke = Color.BLACK
                fillText("[EDIT MODE]", 0.02, 0.05, 32.0)
                strokeText("[EDIT MODE]", 0.02, 0.05, 32.0)

            }
        }

        nodes.sortedBy { it.priority }.forEach { it.render(this) }

        //

    }

}