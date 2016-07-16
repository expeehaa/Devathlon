package de.expeehaa.spigot.config.wrapper;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Particle;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import de.expeehaa.spigot.config.ParticleTrail;

public class ParticleTrailConfigWrapper implements ConfigurationSerializable {
	
	private String particle;
	
	private double offsetX, offsetY, offsetZ;
	
	private int count;
	
	//constructor for ConfigurationSerializable
	public ParticleTrailConfigWrapper(Map<String, Object> map){
		this.particle = (String) map.get("particle");
		this.count = (Integer) map.get("count");
		this.offsetX = (Double) map.get("offsetX");
		this.offsetY = (Double) map.get("offsetY");
		this.offsetZ = (Double) map.get("offsetZ");
	}
	
	//constructor with parameters
	public ParticleTrailConfigWrapper(String particle, int count, double offsetX, double offsetY, double offsetZ) {
		this.particle = particle;
		this.count = count;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
	}
	
	//creates a ParticleTrail object out of this wrapper object
	public ParticleTrail toParticle(){
		return new ParticleTrail(Particle.valueOf(particle), count, offsetX, offsetY, offsetZ);
	}

	//serialization method from ConfigurationSerializable
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("particle", particle);
		map.put("offsetX", offsetX);
		map.put("offsetY", offsetY);
		map.put("offsetZ", offsetZ);
		map.put("count", count);
		
		return map;
	}
}
