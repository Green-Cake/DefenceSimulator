package game.defencesim.node

import game.defencesim.util.Position

interface IClickable {
    val priority: Int
    fun contains(pos: Position): Boolean
}