package de.expeehaa.spigot;

import org.bukkit.plugin.java.JavaPlugin;

import de.expeehaa.spigot.config.Configuration;

public class Wands extends JavaPlugin {

	@Override
	public void onEnable() {
		Configuration.reloadConfig(this);
	}
	
	@Override
	public void onDisable() {
		
	}
}
