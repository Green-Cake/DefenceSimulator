package game.defencesim.util

class BoundingBox(val posMin: Position, val posMax: Position) {

    constructor(pos: Position, scale: Scale) : this(pos, Position(pos.x + scale.w, pos.y + scale.h))

    fun contains(x: Double, y: Double) = posMin.x < x && posMin.y < y && x < posMax.x && y < posMax.y
    operator fun contains(p: Position) = contains(p.x, p.y)

}