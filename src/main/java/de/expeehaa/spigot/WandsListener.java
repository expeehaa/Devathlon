package de.expeehaa.spigot;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import de.expeehaa.spigot.config.Configuration;
import de.expeehaa.spigot.config.Wand;

public class WandsListener implements Listener {

	private Wands main;
	
	private Map<Snowball, Wand> sbWandMap = new HashMap<Snowball, Wand>();
	
	public WandsListener(Wands main) {
		this.main = main;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		
		
		Player p = e.getPlayer();
		Wand wand = null;
		
		ItemStack is = e.getItem();
		
		for (Wand w : Configuration.wandList) {
			boolean matchMaterial 	= 	is.getData().getItemType() == w.material ? true : false;
			boolean matchDurability = 	is.getDurability() == w.damaged ? true : false;
			boolean matchClick 		= 	(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) & w.rightClick ? true : false;
			if(matchMaterial && matchDurability && matchClick){
				wand = w;
				break;
			}
		}
		if(wand == null) return;
		
		Snowball sb = (Snowball)p.getWorld().spawnEntity(p.getEyeLocation().add(p.getEyeLocation().getDirection()), EntityType.SNOWBALL);
		sb.setGravity(false);
		sb.setShooter(p);
		sb.setVelocity(p.getEyeLocation().getDirection().multiply(wand.speed));
		sbWandMap.put(sb, wand);
		
	}
	
	@EventHandler
	public void onSnowballDamaging(EntityDamageByEntityEvent e){
		if(!e.getDamager().getType().equals(EntityType.SNOWBALL)) return;
		if(!sbWandMap.containsKey((Snowball)e.getDamager())) return;
		
		Snowball sb = (Snowball) e.getDamager();
		Wand wand = sbWandMap.get(sb);
		Entity victim = e.getEntity();
		
		try {
			LivingEntity le = (LivingEntity) victim;
			le.addPotionEffects(wand.potionEffectList);
		} catch (ClassCastException cce) {
			//no action needed
		}
		
		e.setDamage(wand.damage);
		
	}
	
	@EventHandler
	public void onSnowballDeath(EntityDeathEvent e){
		if(!e.getEntityType().equals(EntityType.SNOWBALL)) return;
		if(sbWandMap.containsKey((Snowball)e.getEntity())){
			sbWandMap.remove((Snowball) e.getEntity());
		}
	}
}
