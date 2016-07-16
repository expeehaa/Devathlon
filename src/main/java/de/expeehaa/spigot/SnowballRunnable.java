package de.expeehaa.spigot;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SnowballRunnable extends BukkitRunnable {

	private Vector vector;
	
	private Entity entity;
	
	private Location startLocation;
	
	public SnowballRunnable(Entity entity, Vector v) {
		this.vector = v;
		this.entity = entity;
		startLocation = entity.getLocation();
	}
	
	//don't allow speed changes
	public void run() {
		entity.setVelocity(vector);
		if(startLocation.distance(entity.getLocation()) > 1000) entity.remove();
	}

}
