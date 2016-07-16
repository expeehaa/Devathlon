package de.expeehaa.spigot.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffect;

public class Wand {
	
	//Item material
	public Material material;
	//durability
	public int damaged;
	//action on right click or left click
	public boolean rightClick;
	
	//list of potion effects
	public List<PotionEffect> potionEffectList;
	
	//list of particle trails
	public List<ParticleTrail> particleTrail;
	
	//list of entityTypes to spawn
	public List<EntityType> entitySpawnList;
	
	//speed multiplier of the snowball
	public double speed;
	
	//damage caused by snowball
	public double damage;
	
	//size of explosion when snowball gets destroyed
	public double explosionSize;
	
	//constructor
	public Wand(Material material, int damaged, List<PotionEffect> potionEffectList, double speed, boolean rightClick, double damage, double explosionSize, List<ParticleTrail> particleTrail, List<EntityType> entitySpawnList) {
		this.material = material;
		this.damaged = damaged;
		this.speed = speed;
		this.rightClick = rightClick;
		this.damage = damage;
		this.explosionSize = explosionSize;
		
		if(potionEffectList == null) this.potionEffectList = new ArrayList<PotionEffect>();
		else this.potionEffectList = potionEffectList;
		
		if(particleTrail == null) this.particleTrail = new ArrayList<ParticleTrail>();
		else this.particleTrail = particleTrail;
		
		if(entitySpawnList == null) this.entitySpawnList = new ArrayList<EntityType>();
		else this.entitySpawnList = entitySpawnList;
	}
	
}
