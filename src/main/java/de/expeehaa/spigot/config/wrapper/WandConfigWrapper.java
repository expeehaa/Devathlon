package de.expeehaa.spigot.config.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffect;

import de.expeehaa.spigot.config.ParticleTrail;
import de.expeehaa.spigot.config.Wand;

public class WandConfigWrapper implements ConfigurationSerializable {
	
	private String material;
	
	private int damaged;
	
	private List<PotionEffectConfigWrapper> potionEffects;
	
	private List<ParticleTrailConfigWrapper> particleTrail;
	
	private List<String> entitySpawnList;
	
	private double speed;
	
	private boolean rightClick;
	
	private double damage;
	
	private double explosionSize;
	
	//constructor for ConfigurationSerializable
	@SuppressWarnings("unchecked")
	public WandConfigWrapper(Map<String, Object> map){
		this.material = (String) map.get("material");
		this.damaged = (Integer) map.get("damaged");
		this.speed = (Double) map.get("speed");
		this.rightClick = (Boolean) map.get("rightClick");
		this.damage = (Double) map.get("damage");
		this.explosionSize = (Double) map.get("explosionSize");
		
		//if possible, take list from config. Otherwise create an empty List
		try {
			this.potionEffects = (List<PotionEffectConfigWrapper>)map.get("potionEffects");
		} catch (Exception e) {
			Bukkit.getLogger().warning(e.getMessage());
			this.potionEffects = new ArrayList<PotionEffectConfigWrapper>();
		}
		
		try {
			this.particleTrail = (List<ParticleTrailConfigWrapper>)map.get("particleTrail");
		} catch (Exception e) {
			Bukkit.getLogger().warning(e.getMessage());
			this.particleTrail = new ArrayList<ParticleTrailConfigWrapper>();
		}
		
		try {
			this.entitySpawnList = (List<String>)map.get("entitySpawnList");
		} catch (Exception e) {
			Bukkit.getLogger().warning(e.getMessage());
			this.entitySpawnList = new ArrayList<String>();
		}
	}
	
	//constructor with parameters
	public WandConfigWrapper(String material, int damaged, List<PotionEffectConfigWrapper> potionEffects, double speed, boolean rightClick, double damage, double explosionSize, List<ParticleTrailConfigWrapper> particleTrail, List<String> entitySpawnList) {
		this.material = material;
		this.damaged = damaged;
		this.speed = speed;
		this.rightClick = rightClick;
		this.damage = damage;
		this.explosionSize = explosionSize;
		
		if(potionEffects == null) this.potionEffects = new ArrayList<PotionEffectConfigWrapper>();
		else this.potionEffects = potionEffects;
		
		if(particleTrail == null) this.particleTrail = new ArrayList<ParticleTrailConfigWrapper>();
		else this.particleTrail = particleTrail;
		
		if(entitySpawnList == null) this.entitySpawnList = new ArrayList<String>();
		else this.entitySpawnList = entitySpawnList;
	}
	
	//creates a Wand object out of this wrapper object
	public Wand toWand(){
		List<PotionEffect> potionEffectList = new ArrayList<PotionEffect>();
		for (PotionEffectConfigWrapper pecw : potionEffects) {
			potionEffectList.add(pecw.toPotionEffect());
		}
		List<ParticleTrail> particleTrail = new ArrayList<ParticleTrail>();
		for (ParticleTrailConfigWrapper ptcw : this.particleTrail) {
			particleTrail.add(ptcw.toParticle());
		}
		
		List<EntityType> entitySpawnList = new ArrayList<EntityType>();
		for (String etStr : this.entitySpawnList) {
			entitySpawnList.add(EntityType.valueOf(etStr));
		}
		
		return new Wand(Material.getMaterial(material), damaged, potionEffectList, speed, rightClick, damage, explosionSize, particleTrail, entitySpawnList);
	}

	//serialization method from ConfigurationSerializable
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("material", material);
		map.put("damaged", damaged);
		map.put("potionEffects", potionEffects);
		map.put("speed", speed);
		map.put("rightClick", rightClick);
		map.put("damage", damage);
		map.put("explosionSize", explosionSize);
		map.put("particleTrail", particleTrail);
		map.put("entitySpawnList", entitySpawnList);
		
		return map;
	}
}