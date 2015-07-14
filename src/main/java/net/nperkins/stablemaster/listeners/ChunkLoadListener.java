package net.nperkins.stablemaster.listeners;

import java.util.concurrent.ConcurrentHashMap;
import net.nperkins.stablemaster.StableMaster;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class ChunkLoadListener implements Listener {

    final StableMaster plugin;

    public ChunkLoadListener(StableMaster plugin) {
        this.plugin = plugin;
    }

    public void onChunkLoadEvent(ChunkLoadEvent event) {
        Chunk c = event.getChunk();

        if(plugin.chunkLoadQueue.containsKey(c)) {

            Player player = (Player) plugin.chunkLoadQueue.get(c);
            Horse horse = (Horse)plugin.TeleportQueue.get(player);

            horse.teleport(player);
            player.sendMessage(StableMaster.playerMessage("Teleporting..."));

            plugin.chunkLoadQueue.remove(c);
            plugin.TeleportQueue.remove(player);
            horse.getWorld().unloadChunkRequest(c.getX(), c.getZ(), true);
        }
    }
}
