package fun.demc.minisurkit;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


public class EffectsManager {
    private final Map<PotionEffectType, Effects> effectsMap;

    public EffectsManager() {
        effectsMap = Arrays.stream(PotionEffectType.values())
                .collect(Collectors.toMap(
                        type -> type,
                        Effects::new
                ));
    }

    public void enableEffect(Effects effect){
        effect.setEnabled(true);
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.removePotionEffect(effect.getType());
            applyToPlayer(player, effect);
        });
    }

    public void disableEffect(Effects effect){
        effect.setEnabled(false);
        Bukkit.getOnlinePlayers().forEach(player -> player.removePotionEffect(effect.getType()));
    }
    
    public void applyToPlayer(Player player, Effects effect){
        player.addPotionEffect(new PotionEffect(effect.getType(), Integer.MAX_VALUE, effect.getAmplifier()));
    }

    public void applyAllToPlayer(Player player){
        effectsMap.values().forEach(effect -> {
            if(effect.isEnabled()){
                player.addPotionEffect(new PotionEffect(effect.getType(), Integer.MAX_VALUE, effect.getAmplifier()));
            }
        });
    }

    public void refresh(){
        for(Player player : Bukkit.getOnlinePlayers()){
            for(Effects effect : effectsMap.values()){
                if(effect.isEnabled()) {
                    player.removePotionEffect(effect.getType());
                    applyToPlayer(player, effect);
                }
            }
        }
    }

    public void removeAllEffects(){
        for(Player player : Bukkit.getOnlinePlayers()){
            for(Effects effect : effectsMap.values()){
                if(effect.isEnabled()){
                    player.removePotionEffect(effect.getType());
                }
            }
        }
    }

    public boolean effectHasEdited(Effects effect){
        return findEffect(effect.getType()).hasEdited();
    }

    public ArrayList<Effects> getEnabledEffects(){
        ArrayList<Effects> enabledEffects = new ArrayList<>();
        effectsMap.values().forEach(effect -> {
            if(effect.isEnabled()){
                enabledEffects.add(effect);
            }
        });
        return enabledEffects;
    }

    public Map<PotionEffectType, Effects> getEffectsMap() {
        return effectsMap;
    }

    public void setEffectLevel(Effects effect, int level){
        effect.setAmplifier(level - 1);
        refresh();
    }

    public Effects findEffect(PotionEffectType type) {
        return effectsMap.get(type);
    }

}
