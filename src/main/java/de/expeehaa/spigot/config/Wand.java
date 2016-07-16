package de.expeehaa.spigot.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;

public class Wand {
	
	public Material material;
	public int damaged;
	public boolean rightClick;
	
	public List<PotionEffect> potionEffectList;
	
	public List<ParticleTrail> particleTrail;
	
	public double speed;
	
	public double damage;
	
	public double explosionSize;
	
	public Wand(Material material, int damaged, List<PotionEffect> potionEffectList, double speed, boolean rightClick, double damage, double explosionSize, List<ParticleTrail> particleTrail) {
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
	}
	
}
