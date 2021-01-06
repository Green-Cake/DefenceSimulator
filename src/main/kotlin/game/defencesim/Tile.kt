package game.defencesim

import javafx.scene.image.Image

interface ITile {

    val nameDisplay: String

    fun getImage(x: Int, y: Int): Image

}

class SimpleTile(override val nameDisplay: String, val image: Image) : ITile {
    override fun getImage(x: Int, y: Int) = image
}