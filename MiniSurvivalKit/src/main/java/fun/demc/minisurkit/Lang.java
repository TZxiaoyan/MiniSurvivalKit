package fun.demc.minisurkit;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Lang {
    private static YamlConfiguration messages;
    public static void load(JavaPlugin plugin) {
        String langCode = plugin.getConfig().getString("lang", "zh");
        if(plugin.getConfig().getString("lang") == null) {
            plugin.getConfig().set("lang", "zh");
        }
        File file = new File(plugin.getDataFolder(), "message_" + langCode + ".yml");
        if (!file.exists()) {
            plugin.saveResource("message_" + langCode + ".yml", false);
        }
        messages = YamlConfiguration.loadConfiguration(file);
    }
    public static String getMessage(String key) {
        return messages.getString(key);
    }
}
