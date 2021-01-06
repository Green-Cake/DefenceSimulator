package game.defencesim.node

import game.defencesim.render.Renderer
import game.defencesim.util.BoundingBox
import game.defencesim.util.Position
import game.defencesim.util.Scale

abstract class Node(val position: Position, val scale: Scale, override val priority: Int) : IRenderable, IClickable, Comparable<Node> {

    var isEnable = true

    val boundingBox = BoundingBox(position, scale)

    override operator fun contains(pos: Position) = isEnable && pos in boundingBox

    abstract override fun render(renderer: Renderer)

    override fun compareTo(other: Node) = priority.compareTo(other.priority)

    open fun onUpdate() = Unit
    open fun onClicked() = Unit

}