package game.defencesim.weapon

import game.defencesim.GameSceneMain
import game.defencesim.Registry
import game.defencesim.util.IntPos
import game.defencesim.util.Position

class WeaponShip(override var position: IntPos, override val side: GameSceneMain.PlayerType) : IWeapon {

    override val nameDisplay = "aegis"

    override fun getImage() = Registry.getWeaponTextureResource("aegis")

    override fun serialize() = intArrayOf()

    override fun deserialize(bin: IntArray) {

    }

}