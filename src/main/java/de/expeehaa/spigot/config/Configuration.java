package de.expeehaa.spigot.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.expeehaa.spigot.Wands;

public class Configuration {
	
	public static Map<String, Wand> wandMap = new HashMap<String, Wand>();
	
	public static void reloadConfig(Wands main){
		main.reloadConfig();
		
		if(!main.getConfig().contains("wands")) {
			
			PotionEffectConfigWrapper pecw1 = new PotionEffectConfigWrapper(PotionEffectType.LEVITATION.getName(), 1000, 3, true, true);
			PotionEffectConfigWrapper pecw2 = new PotionEffectConfigWrapper(PotionEffectType.BLINDNESS.getName(), 1000, 3, true, true);
			List<PotionEffectConfigWrapper> pecwlist = new ArrayList<PotionEffectConfigWrapper>();
			pecwlist.add(pecw1);
			
			Map<String, WandConfigWrapper> wcwmap = new HashMap<String, WandConfigWrapper>();
			
			WandConfigWrapper wcw1 = new WandConfigWrapper(Material.STICK.toString(), (short)0, pecwlist, 1.5d, true, 10);
			pecwlist.add(pecw2);
			wcwmap.put("stickwand", wcw1);
			
			WandConfigWrapper wcw2 = new WandConfigWrapper(Material.DIAMOND_SWORD.toString(), (short)0, pecwlist, 2d, true, 20);
			wcwmap.put("diaswordwand", wcw2);
			
			main.getConfig().addDefault("wands", wcwmap);
			
			main.getConfig().options().copyDefaults(true);
			main.saveConfig();
			main.reloadConfig();
		}
		
		Map<String, Object> values = main.getConfig().getConfigurationSection("wands").getValues(true);
		
		for (Entry<String, Object> entry : values.entrySet()) {
			String cfgPath = "wands." + entry.getKey();
			Map<String, Object> wcwMap = main.getConfig().getConfigurationSection(cfgPath).getValues(true);
			WandConfigWrapper wcw = new WandConfigWrapper(wcwMap, main, cfgPath);
			Wand wand = wcw.toWand();
			wandMap.put(entry.getKey(), wand);
		}
		
		main.getLogger().info("Successfully loaded " + wandMap.size() + " Wands.");
		
	}
	
	private static class WandConfigWrapper implements ConfigurationSerializable {
		
		private String material;
		
		private short damaged;
		
		private List<PotionEffectConfigWrapper> potionEffects;
		
		private double speed;
		
		private boolean rightClick;
		
		private double damage;
		
		private WandConfigWrapper(Map<String, Object> map, Wands main, String cfgPath){
			this.material = (String) map.get("material");
			this.damaged = (Short) map.get("damaged");
			this.speed = (Double) map.get("speed");
			this.rightClick = (Boolean) map.get("rightClick");
			this.damage = (Double) map.get("damage");
			
			if(main.getConfig().contains(cfgPath + "potionEffects")){
				List<PotionEffectConfigWrapper> pecwList = new ArrayList<Configuration.PotionEffectConfigWrapper>();
				List<Map<?,?>> peMapList = main.getConfig().getMapList(cfgPath + "potionEffects");
				
				for (Map<?, ?> peMap : peMapList) {
					try {
						@SuppressWarnings("unchecked")
						Map<String, Object> castedPeMap = (Map<String, Object>) peMap;
						pecwList.add(new PotionEffectConfigWrapper(castedPeMap));
					} catch (Exception e) {
						continue;
					}
				}
				
				this.potionEffects = pecwList;
			}
		}
		
		public WandConfigWrapper(String material, short damaged, List<PotionEffectConfigWrapper> potionEffects, double speed, boolean rightClick, double damage) {
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

		public Map<String, Object> serialize() {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("material", material);
			map.put("damaged", damaged);
			map.put("potionEffects", potionEffects);
			map.put("speed", speed);
			map.put("rightClick", rightClick);
			map.put("damage", damage);
			
			return map;
		}
	}
	
	private static class PotionEffectConfigWrapper implements ConfigurationSerializable {
		
		private String potionEffectType;
		
		private int duration;
		
		private int amplifier;
		
		private boolean ambient;
		
		private boolean particles;
		
		private PotionEffectConfigWrapper(Map<String, Object> map){
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
}
