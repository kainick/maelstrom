package io.kainick.maelstrom

import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.Player
import net.minestom.server.event.GlobalEventHandler
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.instance.*
import net.minestom.server.instance.block.Block

fun main() {
    // Initialization
    val minecraftServer: MinecraftServer = MinecraftServer.init()
    val instanceManager: InstanceManager = MinecraftServer.getInstanceManager()
    // Create the instance
    val instanceContainer: InstanceContainer = instanceManager.createInstanceContainer()
    // Set the ChunkGenerator
    instanceContainer.setGenerator { unit -> unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK) }
    // Add an event callback to specify the spawning instance (and the spawn position)
    val globalEventHandler: GlobalEventHandler = MinecraftServer.getGlobalEventHandler()
    globalEventHandler.addListener(PlayerLoginEvent::class.java) { event ->
        val player: Player = event.player
        event.setSpawningInstance(instanceContainer)
        player.respawnPoint = Pos(0.0, 42.0, 0.0)
    }
    // Start the server on port 25565
    minecraftServer.start("0.0.0.0", 25565)
}
