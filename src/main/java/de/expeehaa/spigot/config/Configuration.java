package de.expeehaa.spigot.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.potion.PotionEffectType;

import de.expeehaa.spigot.Wands;
import de.expeehaa.spigot.config.wrapper.ParticleTrailConfigWrapper;
import de.expeehaa.spigot.config.wrapper.PotionEffectConfigWrapper;
import de.expeehaa.spigot.config.wrapper.WandConfigWrapper;

public class Configuration {
	
	public static Map<String, Wand> wandMap = new HashMap<String, Wand>();
	
	public static void reloadConfig(Wands main){
		main.reloadConfig();
		
		if(!main.getConfig().contains("wands")) {
			
			PotionEffectConfigWrapper pecw1 = new PotionEffectConfigWrapper(PotionEffectType.LEVITATION.getName(), 1000, 3, true, true);
			PotionEffectConfigWrapper pecw2 = new PotionEffectConfigWrapper(PotionEffectType.POISON.getName(), 1000, 3, true, true);
			List<PotionEffectConfigWrapper> pecwlist1 = new ArrayList<PotionEffectConfigWrapper>();
			pecwlist1.add(pecw1);
			List<PotionEffectConfigWrapper> pecwlist2 = new ArrayList<PotionEffectConfigWrapper>();
			pecwlist2.add(pecw2);
			
			ParticleTrailConfigWrapper ptcw1 = new ParticleTrailConfigWrapper(Particle.EXPLOSION_NORMAL.toString(), 3, 0.5d, 0.5d, 0.5d);
			ParticleTrailConfigWrapper ptcw2 = new ParticleTrailConfigWrapper(Particle.REDSTONE.toString(), 10, 1d, 1d, 1d);
			List<ParticleTrailConfigWrapper> ptcwlist1 = new ArrayList<ParticleTrailConfigWrapper>();
			ptcwlist1.add(ptcw1);
			List<ParticleTrailConfigWrapper> ptcwlist2 = new ArrayList<ParticleTrailConfigWrapper>();
			ptcwlist2.add(ptcw2);
			
			WandConfigWrapper wcw1 = new WandConfigWrapper(Material.STICK.toString(), 0, pecwlist1, 1d, true, 5, 0.5d, ptcwlist1);
			WandConfigWrapper wcw2 = new WandConfigWrapper(Material.DIAMOND_SWORD.toString(), 0, pecwlist2, 5d, true, 20, 5d, ptcwlist2);
			
			Map<String, WandConfigWrapper> wcwmap = new HashMap<String, WandConfigWrapper>();
			wcwmap.put("stickwand", wcw1);
			wcwmap.put("diaswordwand", wcw2);
			
			main.getConfig().createSection("wands", wcwmap);
			
			main.getConfig().options().copyDefaults(true);
			main.saveConfig();
			main.reloadConfig();
		}
		
		Map<String, Object> values = main.getConfig().getConfigurationSection("wands").getValues(true);
		
		main.getLogger().info("root: " + main.getConfig().getValues(false).size() + "\nwands: " + main.getConfig().getConfigurationSection("wands").getValues(false).size());
		
		for (Entry<String, Object> entry : values.entrySet()) {
			WandConfigWrapper wcw = (WandConfigWrapper) entry.getValue();
			Wand wand = wcw.toWand();
			wandMap.put(entry.getKey(), wand);
		}
		
		main.getLogger().info("Successfully loaded " + wandMap.size() + " Wands.");
	}
}
