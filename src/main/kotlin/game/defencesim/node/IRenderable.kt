package game.defencesim.node

import game.defencesim.render.Renderer

interface IRenderable {
    fun render(renderer: Renderer)
}