package fun.demc.minisurkit;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;


public class ConfigManager {
    private final JavaPlugin plugin;
    private final EffectsManager effectsManager;

    public ConfigManager(JavaPlugin plugin, EffectsManager effectsManager) {
        this.plugin = plugin;
        this.effectsManager = effectsManager;
    }

    public void loadConfig() {
        FileConfiguration config = plugin.getConfig();

        for (PotionEffectType type : PotionEffectType.values()) {
            Effects effect = effectsManager.findEffect(type);
            if (effect == null) continue;

            String path = "effects." + type.getKey().getKey().toLowerCase();

            boolean enabled = config.getBoolean(path + ".enabled", false);
            int level = config.getInt(path + ".level", -1);

            effect.setEnabled(enabled);
            if(level != -1)
                effect.setAmplifier(level - 1);
            else{
                effect.setAmplifier(0);
                config.set(path + ".level", 0);
            }
        }

        saveConfig();
        plugin.getLogger().info("Configs have been loaded!");
    }


    public void saveConfig() {
        FileConfiguration config = plugin.getConfig();

        for (Effects effect : effectsManager.getEffectsMap().values()) {
            PotionEffectType type = effect.getType();
            String path = "effects." + type.getKey().getKey().toLowerCase();

            config.set(path + ".enabled", effect.isEnabled());
            config.set(path + ".level", effect.getAmplifier());
        }

        plugin.saveConfig();
    }
}

