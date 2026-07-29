package fun.demc.minisurkit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MiniSurvivalKit extends JavaPlugin {
    EffectsManager effectsManager;
    ConfigManager configManager;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        effectsManager = new EffectsManager();
        Lang.load(this);
        configManager = new ConfigManager(this, effectsManager);
        configManager.loadConfig();

        CommandsExecutor commandsExecutor = new CommandsExecutor(effectsManager, configManager);
        mondocommand.MondoCommand base = new mondocommand.MondoCommand();
        base.autoRegisterFrom(commandsExecutor);   // 扫描 @Sub 方法
        getCommand("surkit").setExecutor(base);
        getCommand("surkit").setTabCompleter(commandsExecutor);


        getServer().getPluginManager().registerEvents(new EventsListener(effectsManager, this), this);


        Bukkit.getScheduler().runTaskTimer(this, () -> effectsManager.refresh(), 0L, 5L);


        getLogger().info("[MiniSurvivalKit] Plugin has been enabled!");

    }


    @Override
    public void onDisable() {
        if (configManager != null) {
            configManager.saveConfig();
        }

        effectsManager.removeAllEffects();

        getLogger().info("[MiniSurvivalKit] Plugin has been disabled!");
    }
}
