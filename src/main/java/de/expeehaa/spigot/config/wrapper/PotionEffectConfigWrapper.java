package de.expeehaa.spigot.config.wrapper;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionEffectConfigWrapper implements ConfigurationSerializable {
	
	private String potionEffectType;
	
	private int duration;
	
	private int amplifier;
	
	private boolean ambient;
	
	private boolean particles;
	
	public PotionEffectConfigWrapper(Map<String, Object> map){
		this.potionEffectType = (String) map.get("potionEffectType");
		this.duration = (Integer) map.get("duration");
		this.amplifier = (Integer) map.get("amplifier");
		this.ambient = (Boolean) map.get("ambient");
		this.particles = (Boolean) map.get("particles");
	}
	
	public PotionEffectConfigWrapper(String potionEffectType, int duration, int amplifier, boolean ambient, boolean particles) {
		this.potionEffectType = potionEffectType;
		this.duration = duration;
		this.amplifier = amplifier;
		this.ambient = ambient;
		this.particles = particles;
	}
	
	public PotionEffect toPotionEffect(){
		return new PotionEffect(PotionEffectType.getByName(potionEffectType), duration, amplifier, ambient, particles);
	}

	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("potionEffectType", potionEffectType);
		map.put("duration", duration);
		map.put("amplifier", amplifier);
		map.put("ambient", ambient);
		map.put("particles", particles);
		
		return map;
	}
}