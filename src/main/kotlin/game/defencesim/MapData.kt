package game.defencesim

import game.defencesim.util.IntPos
import game.defencesim.weapon.IWeapon

class MapData(val tileMap: IntArray, val weapons_init: Set<IWeapon>, val mapWidth: Int, val mapHeight: Int) {

    val weapons = weapons_init.toMutableSet()

    init {

        check(tileMap.size == mapWidth * mapHeight) { "Tile-map-data size mismatched!" }

    }

    fun serialize() {



    }

    fun getTileAt(x: Int, y: Int): ITile {

        check(x < mapWidth && y < mapHeight) { "Illegal position specified" }

        return Registry.tiles[tileMap[mapWidth * y + x]]!!

    }

    fun getWeaponAt(x: Int, y: Int): IWeapon? {

        check(x < mapWidth && y < mapHeight) { "Illegal position specified" }

        return weapons.firstOrNull { it.position == IntPos(x, y) }

    }

    fun getXFromIndex(index: Int) = index % mapHeight

    fun getYFromIndex(index: Int) = index / mapHeight

}