package game.defencesim

import game.defencesim.util.Position
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseButton

object DeviceManager {

    // keyboard

    const val NOT_PRESSED = -2
    const val GET_RELEASED = -1
    const val GET_PRESSED = 0
    const val PRESSED = 1

    val map = mutableMapOf<KeyCode, Int>()
    val req = mutableMapOf<KeyCode, Int>()

    fun isGetPressed(code: KeyCode) = map[code] == GET_PRESSED
    fun isPressed(code: KeyCode) = (map[code] ?: NOT_PRESSED) >= 0
    fun isGetReleased(code: KeyCode) = map[code] == GET_RELEASED
    fun isNotPressed(code: KeyCode) = (map[code] ?: NOT_PRESSED) < 0

    fun pressed(code: KeyCode) {
        if(code !in map || map[code]!! < 0)
            req[code] = GET_PRESSED
    }

    fun released(code: KeyCode) {
        req[code] = GET_RELEASED
    }

    //

    //mouse

    var lastClicked: Pair<MouseButton, Position>? = null
        private set
    var _lastClicked: Pair<MouseButton, Position>? = null
        private set

    fun clickedAt(button: MouseButton, pos: Position) {
        _lastClicked = button to pos
    }

    //

    fun update() {

        lastClicked = _lastClicked
        _lastClicked = null

        map.keys.filter { map[it] == GET_PRESSED }.forEach {
            map[it] = PRESSED
        }

        map.keys.filter { map[it] == GET_RELEASED }.forEach {
            map[it] = NOT_PRESSED
        }

        map.putAll(req)
        req.clear()

    }

}