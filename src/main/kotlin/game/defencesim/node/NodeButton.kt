package game.defencesim.node

import game.defencesim.render.Renderer
import game.defencesim.util.Position
import game.defencesim.util.Scale
import javafx.scene.image.Image

class NodeButton(position: Position, scale: Scale, priority: Int=0, val texture: Image, val eventUpdate: NodeButton.()->Unit, val eventClicked: ()->Unit) : Node(position, scale, priority) {

    override fun render(renderer: Renderer) = if(isEnable) renderer.drawImage(texture, position, scale) else Unit

    override fun onUpdate() = eventUpdate()
    override fun onClicked() = eventClicked()

}