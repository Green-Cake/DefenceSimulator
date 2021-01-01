package game.defencesim.util

data class Position(val x: Double, val y: Double) {

    constructor(origin: Position) : this(origin.x, origin.y)

}