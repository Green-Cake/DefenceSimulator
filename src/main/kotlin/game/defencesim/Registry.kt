package game.defencesim

import javafx.scene.image.Image

object Registry {

    val tiles = mutableMapOf<Int, ITile>()

    val textures = mutableMapOf<String, Image>()

    fun getTextureResource(path: String, w: Double=64.0, h: Double=64.0): Image {

        if(path in textures)
            return textures[path]!!

        textures[path] = Image(ClassLoader.getSystemResourceAsStream("game/defencesim/assets/textures/$path"), w, h, false, false)

        return textures[path]!!

    }

    fun getTileTextureResource(name: String) = getTextureResource("tile/$name.png", 64.0, 64.0)

    fun getWeaponTextureResource(name: String) = getTextureResource("weapon/$name.png", 64.0, 64.0)

    init {

        tiles[1] = SimpleTile("water", getTileTextureResource("water"))

        tiles[2] = SimpleTile("land", getTileTextureResource("land"))

    }

}