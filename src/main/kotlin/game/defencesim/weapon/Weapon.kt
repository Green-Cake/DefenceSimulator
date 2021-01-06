package game.defencesim.weapon

import game.defencesim.GameSceneMain
import game.defencesim.util.IntPos
import game.defencesim.util.Position
import javafx.scene.image.Image

interface IWeapon {

    var position: IntPos

    val nameDisplay: String

    val side: GameSceneMain.PlayerType

    fun getImage(): Image

    fun serialize(): IntArray
    fun deserialize(bin: IntArray)

}