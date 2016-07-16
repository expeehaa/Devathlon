package de.expeehaa.spigot.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.expeehaa.spigot.Wands;

public class Configuration {
	
	public static List<Wand> wandList = new ArrayList<Wand>();
	
	public static void reloadConfig(Wands main){
		main.reloadConfig();
		
		if(!main.getConfig().isList("wands")) return;
		List<?> list = main.getConfig().getList("wands");
		int loadingFailed = 0;
		
		for (Object obj : list) {
			if(obj instanceof WandConfigWrapper){
				try {
					WandConfigWrapper wcw = (WandConfigWrapper) obj;
					wandList.add(wcw.toWand());
				} catch (Exception e) {
					loadingFailed++;
					main.getLogger().warning(e.getMessage());
				}
			}
		}
		
		main.getLogger().info("Successfully loaded " + wandList.size() + " Wands. Loading failed " + loadingFailed + " times.");
	}
	
	private class WandConfigWrapper {
		
		public String material;
		
		public int damaged;
		
		public List<PotionEffectConfigWrapper> potionEffects;
		
		public double speed;
		
		public boolean rightClick;
		
		public double damage;
		
		public WandConfigWrapper(String material, int damaged, List<PotionEffectConfigWrapper> potionEffects, double speed, boolean rightClick, double damage) {
			this.material = material;
			this.damaged = damaged;
			this.potionEffects = potionEffects;
			this.speed = speed;
			this.rightClick = rightClick;
			this.damage = damage;
		}
		
		public Wand toWand(){
			List<PotionEffect> potionEffectList = new ArrayList<PotionEffect>();
			for (PotionEffectConfigWrapper pecw : potionEffects) {
				potionEffectList.add(pecw.toPotionEffect());
			}
			return new Wand(Material.getMaterial(material), damaged, potionEffectList, speed, rightClick, damage);
		}
	}
	
	private class PotionEffectConfigWrapper {
		
		public String potionEffectType;
		
		public int duration;
		
		public int amplifier;
		
		public boolean ambient;
		
		public boolean particles;
		
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
	}
}
