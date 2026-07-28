package fun.demc.minisurkit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;

public class EventsListener implements Listener {
    Plugin plugin;
    EffectsManager effectsManager;
    public EventsListener(EffectsManager effectsManager, Plugin plugin) {
        this.effectsManager =  effectsManager;
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        effectsManager.applyAllToPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerDrinkMilk(PlayerItemConsumeEvent event) {
        if(!(event.getItem().getType() == Material.MILK_BUCKET)){
            return;
        }
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            effectsManager.applyAllToPlayer(event.getPlayer());
        }, 10L);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        effectsManager.applyAllToPlayer(event.getPlayer());
    }
}
