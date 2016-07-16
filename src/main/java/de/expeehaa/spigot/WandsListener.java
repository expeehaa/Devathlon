package de.expeehaa.spigot;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.expeehaa.spigot.config.Configuration;
import de.expeehaa.spigot.config.ParticleTrail;
import de.expeehaa.spigot.config.Wand;

public class WandsListener implements Listener {

	//main class
	private Wands main;
	//contains active flying snowballs with its wands
	private Map<Snowball, Wand> sbWandMap = new HashMap<Snowball, Wand>();
	
	//constructor
	public WandsListener(Wands main) {
		this.main = main;
	}
	
	//used to fire snowballs
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		//get player
		Player p = e.getPlayer();
		
		//player permission check
		if(!p.hasPermission("wands.useWands")){
			return;
		}
		
		Wand wand = null;
		ItemStack is = p.getInventory().getItemInMainHand();
		//air can't be a wand
		if(is.getData().getItemType().equals(Material.AIR)) return;
		
		//searches for a wand
		for (Entry<String, Wand> entry : Configuration.wandMap.entrySet()) {
			Wand w = entry.getValue();
			boolean matchMaterial 	= 	is.getData().getItemType().equals(w.material) ? true : false;
			boolean matchDurability = 	is.getDurability() == w.damaged ? true : false;
			boolean matchClick 		= 	(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) == w.rightClick ? true : false;
			if(matchMaterial && matchDurability && matchClick){
				wand = w;
				break;
			}
		}
		//breaks, if no wand was found for the item
		if(wand == null) return;
		
		//creates a snowball, giving him a direction to fly, a shooter and nogravity
		Snowball sb = (Snowball)p.getWorld().spawnEntity(p.getEyeLocation().add(p.getEyeLocation().getDirection()), EntityType.SNOWBALL);
		sb.setGravity(false);
		sb.setShooter(p);
		new SnowballRunnable(sb, p.getEyeLocation().getDirection().multiply(wand.speed)).runTaskTimer(main, 0, 20);
		//puts snowball and wand into map -> currently flying
		sbWandMap.put(sb, wand);
		//start runnable for particle trails
		for (ParticleTrail pt : wand.particleTrail) {
			new ParticleRunnable(sb, pt).runTaskTimer(main, 1, 1);
		}
	}
	
	//used when entity was hit by a snowball fired by a wand
	@EventHandler
	public void onSnowballDamaging(EntityDamageByEntityEvent e){
		if(!e.getDamager().getType().equals(EntityType.SNOWBALL)) return;
		if(!sbWandMap.containsKey((Snowball)e.getDamager())) return;
		
		Snowball sb = (Snowball) e.getDamager();
		Wand wand = sbWandMap.get(sb);
		Entity victim = e.getEntity();
		
		//add potion effects to entity, if it is a living entity
		try {
			LivingEntity le = (LivingEntity) victim;
			le.addPotionEffects(wand.potionEffectList);
		} catch (ClassCastException cce) {
			//no action needed
		}
		//set custom damage
		e.setDamage(wand.damage);
	}
	
	//used when a snowball hits anything and gets destroyed
	@EventHandler
	public void onSnowballDeath(org.bukkit.event.entity.ProjectileHitEvent e){
		if(!e.getEntityType().equals(EntityType.SNOWBALL)) return;
		if(sbWandMap.containsKey((Snowball)e.getEntity())){
			
			Wand wand = sbWandMap.get((Snowball)e.getEntity());
			//make explosions
			if(wand.explosionSize > 0d){
				e.getEntity().getWorld().createExplosion(e.getEntity().getLocation(), (float)wand.explosionSize);
			}
			//spawn entities
			for (EntityType et : wand.entitySpawnList) {
				e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), et);
			}
			//remove from map -> not active anymore
			sbWandMap.remove((Snowball) e.getEntity());
		}
	}
}
