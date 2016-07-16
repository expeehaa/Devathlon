package de.expeehaa.spigot.config;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;

public class Wand {
	
	public Material material;
	public int damaged;
	public boolean rightClick;
	
	public List<PotionEffect> potionEffectList;
	
	public double speed;
	
	public double damage;
	
	public Wand(Material material, int damaged, List<PotionEffect> potionEffectList, double speed, boolean rightClick, double damage) {
		this.material = material;
		this.damaged = damaged;
		this.potionEffectList = potionEffectList;
		this.speed = speed;
		this.rightClick = rightClick;
		this.damage = damage;
	}
	
}
