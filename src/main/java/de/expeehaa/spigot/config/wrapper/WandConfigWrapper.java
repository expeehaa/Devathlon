package de.expeehaa.spigot.config.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.potion.PotionEffect;

import de.expeehaa.spigot.Wands;
import de.expeehaa.spigot.config.Wand;

public class WandConfigWrapper implements ConfigurationSerializable {
	
	private String material;
	
	private int damaged;
	
	private List<PotionEffectConfigWrapper> potionEffects;
	
	private double speed;
	
	private boolean rightClick;
	
	private double damage;
	
	public WandConfigWrapper(Map<String, Object> map, Wands main, String cfgPath){
		this.material = (String) map.get("material");
		this.damaged = (Short) map.get("damaged");
		this.speed = (Double) map.get("speed");
		this.rightClick = (Boolean) map.get("rightClick");
		this.damage = (Double) map.get("damage");
		
		if(main.getConfig().contains(cfgPath + "potionEffects")){
			List<PotionEffectConfigWrapper> pecwList = new ArrayList<PotionEffectConfigWrapper>();
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