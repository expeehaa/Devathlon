package de.expeehaa.spigot.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

import de.expeehaa.spigot.Wands;
import de.expeehaa.spigot.config.wrapper.PotionEffectConfigWrapper;
import de.expeehaa.spigot.config.wrapper.WandConfigWrapper;

public class Configuration {
	
	public static Map<String, Wand> wandMap = new HashMap<String, Wand>();
	
	public static void reloadConfig(Wands main){
		main.reloadConfig();
		
		if(!main.getConfig().contains("wands")) {
			
			PotionEffectConfigWrapper pecw1 = new PotionEffectConfigWrapper(PotionEffectType.LEVITATION.getName(), 1000, 3, true, true);
			PotionEffectConfigWrapper pecw2 = new PotionEffectConfigWrapper(PotionEffectType.BLINDNESS.getName(), 1000, 3, true, true);
			List<PotionEffectConfigWrapper> pecwlist = new ArrayList<PotionEffectConfigWrapper>();
			pecwlist.add(pecw1);
			
			Map<String, WandConfigWrapper> wcwmap = new HashMap<String, WandConfigWrapper>();
			
			WandConfigWrapper wcw1 = new WandConfigWrapper(Material.STICK.toString(), 0, pecwlist, 1.5d, true, 10);
			
			wcwmap.put("stickwand", wcw1);
			pecwlist.add(pecw2);
			
			WandConfigWrapper wcw2 = new WandConfigWrapper(Material.DIAMOND_SWORD.toString(), 0, pecwlist, 2d, true, 20);
			wcwmap.put("diaswordwand", wcw2);
			
			main.getConfig().addDefault("wands", wcwmap);
			
			main.getConfig().options().copyDefaults(true);
			main.saveConfig();
			main.reloadConfig();
		}
		
		Map<String, Object> values = main.getConfig().getConfigurationSection("wands").getValues(true);
		
		main.getLogger().info("containing items: " + values.entrySet().size());
		
		for (Entry<String, Object> entry : values.entrySet()) {
			String cfgPath = "wands." + entry.getKey();
			Map<String, Object> wcwMap = main.getConfig().getConfigurationSection(cfgPath).getValues(true);
			WandConfigWrapper wcw = new WandConfigWrapper(wcwMap, main, cfgPath);
			Wand wand = wcw.toWand();
			wandMap.put(entry.getKey(), wand);
		}
		
		main.getLogger().info("Successfully loaded " + wandMap.size() + " Wands.");
		
	}
	
	
	
	
}
