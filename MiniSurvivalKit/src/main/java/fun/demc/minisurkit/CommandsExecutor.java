package fun.demc.minisurkit;

import mondocommand.CallInfo;
import mondocommand.dynamic.Sub;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.potion.PotionEffectType;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandsExecutor implements TabCompleter {
    EffectsManager effectsManager;
    ConfigManager configManager;

    public CommandsExecutor(EffectsManager effectsManager, ConfigManager configManager) {
        this.effectsManager = effectsManager;
        this.configManager = configManager;
    }

    @Sub(description = "启用一个效果.", minArgs = 1, usage = "<effect>")
    public void enable(@NonNull CallInfo call) {
        if (call.numArgs() > 1) {
            call.reply(Lang.getMessage("enable_too_many_parameters"));
            return;
        }
        PotionEffectType type = PotionEffectType.getByName(call.getArg(0));
        if (type != null) {
            Effects effect = effectsManager.findEffect(type);
            if (!effect.isEnabled()) {
                effect.setEnabled(true);
                effectsManager.enableEffect(effect);
                configManager.saveConfig();
                    call.reply(Lang.getMessage("enabled_an_effect") + effect.getType().getKey().getKey());
            } else {
                call.reply(Lang.getMessage("has_enabled"));
            }
        } else {
            call.reply(Lang.getMessage("effect_does_not_extist"));
        }
    }

    @Sub(description = "禁用一个效果.", minArgs = 1, usage = "<effect>")
    public void disable(@NonNull CallInfo call) {
        if (call.numArgs() > 1) {
            call.reply(Lang.getMessage("disable_too_many_parameters"));
            return;
        }
        PotionEffectType type = PotionEffectType.getByName(call.getArg(0));
        if (type != null) {
            Effects effect = effectsManager.findEffect(type);
            if(!effect.isEnabled()) {
                call.reply(Lang.getMessage("already_disabled"));
                return ;
            }
            effect.setEnabled(false);
            effectsManager.disableEffect(effect);
            configManager.saveConfig();
            call.reply(Lang.getMessage("disabled_an_effect") + effect.getType().getKey().getKey());
        } else {
            call.reply(Lang.getMessage("effect_does_not_extist"));
        }
    }

    @Sub(description = "设置效果等级", minArgs = 2, usage = "<effect> <level>")
    public void levelset(@NonNull CallInfo call) {
        if (call.numArgs() > 2) {
            call.reply(Lang.getMessage("levelset_too_many_parameters"));
            return;
        }
        PotionEffectType type = PotionEffectType.getByName(call.getArg(0));
        if (type != null) {
            Effects effect = effectsManager.findEffect(type);
            int level;
            try {
                level = Integer.parseInt(call.getArg(1));
            } catch (NumberFormatException e) {
                call.reply(Lang.getMessage("invalid_level"));
                return;
            }

            if (level < 1 || level > 255) {
                call.reply(Lang.getMessage("invalid_level"));
                return;
            }
            effectsManager.setEffectLevel(effect, level);
            configManager.saveConfig();

            call.reply("{YELLOW}The level of " + effect.getType().getKey().getKey() + " has been set to" + level);
        } else {
            call.reply(Lang.getMessage("effect_does_not_extist"));
        }
    }

    @Sub(description = "列出所有已启用的效果", minArgs = 0, usage = "")
    public void list(@NonNull CallInfo call) {
        if (call.numArgs() > 0) {
            call.reply(Lang.getMessage("list_too_many_parameters"));
            return;
        }

        List<Effects> enabledEffects = effectsManager.getEnabledEffects();

        if (enabledEffects.isEmpty()) {
            call.reply(Lang.getMessage("no_effects_enabled"));
            return;
        }

        List<String> effectNames = enabledEffects.stream()
                .map(effect -> effect.getType().getKey().getKey())
                .toList();

        int itemsPerLine = 3;
        StringBuilder message = new StringBuilder();
        message.append(Lang.getMessage("the_effects_has_been_enabled"));

        for (int i = 0; i < effectNames.size(); i++) {
            message.append(effectNames.get(i));
            message.append(":" + effectsManager.findEffect(PotionEffectType.getByName(effectNames.get(i))).getAmplifier() + 1);
            if ((i + 1) % itemsPerLine != 0 && i != effectNames.size() - 1) {
                message.append("  ");
            }
            if ((i + 1) % itemsPerLine == 0 && i != effectNames.size() - 1) {
                message.append("\n");
            }
        }

        call.reply(message.toString());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length <= 1) {
            List<String> subCommands = Arrays.asList("enable", "disable", "levelset", "list");
            String partial = args.length == 0 ? "" : args[0].toLowerCase();
            return subCommands.stream()
                    .filter(s -> s.startsWith(partial))
                    .collect(Collectors.toList());
        }

        String sub = args[0].toLowerCase();
        if (sub.equals("enable") || sub.equals("disable") || sub.equals("levelset")) {
            if (args.length == 2) {
                String partial = args[1].toLowerCase();
                return Arrays.stream(PotionEffectType.values())
                        .map(type -> type.getKey().getKey()) // 返回 "speed" 等
                        .filter(name -> name.startsWith(partial))
                        .collect(Collectors.toList());
            }
            if (sub.equals("levelset") && args.length == 3) {
                String partial = args[2];
                List<String> levels = new ArrayList<>();
                for (int i = 1; i <= 10; i++) {
                    if (String.valueOf(i).startsWith(partial)) {
                        levels.add(String.valueOf(i));
                    }
                }
                return levels;
            }
        }
        return new ArrayList<>();
    }
}