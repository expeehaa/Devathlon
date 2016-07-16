package de.expeehaa.spigot;

import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import de.expeehaa.spigot.config.ParticleTrail;

public class ParticleRunnable extends BukkitRunnable {

	private Entity entity;
	
	private ParticleTrail particleTrail;
	
	public ParticleRunnable(Entity entity, ParticleTrail particleTrail) {
		this.entity = entity;
		this.particleTrail = particleTrail;
	}
	
	public void run() {
		if(entity.isDead()) {
			this.cancel();
			return;
		}
		entity.getWorld().spawnParticle(particleTrail.particle, entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ(), particleTrail.count, particleTrail.offsetX, particleTrail.offsetY, particleTrail.offsetZ);
	}

}
