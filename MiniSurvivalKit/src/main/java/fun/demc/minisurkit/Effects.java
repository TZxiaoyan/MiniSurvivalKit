package fun.demc.minisurkit;

import org.bukkit.potion.PotionEffectType;

public class Effects{
    private final PotionEffectType type;
    private boolean isEnabled;
    private int amplifier;

    public Effects(PotionEffectType type){
        this.type = type;
        amplifier = 0;
        isEnabled = false;
    }

    public int getAmplifier(){
        return amplifier;
    }
    public void setAmplifier(int amplifier){
        this.amplifier = amplifier;
    }
    public boolean isEnabled(){
        return isEnabled;
    }
    public void setEnabled(boolean enabled){
        isEnabled = enabled;
    }
    public PotionEffectType getType(){
        return type;
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof Effects e){
            return type.equals(e.type);
        }
        return false;
    }


}
