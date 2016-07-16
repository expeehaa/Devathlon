package de.expeehaa.spigot;

import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import de.expeehaa.spigot.config.ParticleTrail;

public class ParticleRunnable extends BukkitRunnable {

	//Entity to follow
	private Entity entity;
	
	//particle trail to create
	private ParticleTrail particleTrail;
	
	//constructor
	public ParticleRunnable(Entity entity, ParticleTrail particleTrail) {
		this.entity = entity;
		this.particleTrail = particleTrail;
	}
	
	
	public void run() {
		//cancel task when entity is dead
		if(entity.isDead()) {
			this.cancel();
			return;
		}
		//create particles
		entity.getWorld().spawnParticle(particleTrail.particle, entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ(), particleTrail.count, particleTrail.offsetX, particleTrail.offsetY, particleTrail.offsetZ);
	}

}
