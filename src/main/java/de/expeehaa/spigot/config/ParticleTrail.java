package de.expeehaa.spigot.config;

import org.bukkit.Particle;

public class ParticleTrail {

	public Particle particle;
	
	public double offsetX, offsetY, offsetZ;
	
	public int count;
	
	public ParticleTrail(Particle particle, int count, double offsetX, double offsetY, double offsetZ) {
		this.particle = particle;
		this.count = count;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
	}
}
