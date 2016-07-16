package de.expeehaa.spigot;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.expeehaa.spigot.config.Configuration;
import de.expeehaa.spigot.config.Wand;
import de.expeehaa.spigot.config.wrapper.ParticleTrailConfigWrapper;
import de.expeehaa.spigot.config.wrapper.PotionEffectConfigWrapper;
import de.expeehaa.spigot.config.wrapper.WandConfigWrapper;

public class Wands extends JavaPlugin {

	@Override
	public void onLoad() {
		//register wrapper classes
		ConfigurationSerialization.registerClass(WandConfigWrapper.class, "WandWrapper");
		ConfigurationSerialization.registerClass(PotionEffectConfigWrapper.class, "PotionEffectWrapper");
		ConfigurationSerialization.registerClass(ParticleTrailConfigWrapper.class, "ParticleTrailWrapper");
	}
	
	@Override
	public void onEnable() {
		//reload config
		Configuration.reloadConfig(this);
		//register listener class
		this.getServer().getPluginManager().registerEvents(new WandsListener(this), this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		
		//checking sender instance
		Player p = null;
		if(sender instanceof Player) p = (Player) sender;
		
		//wand cmd
		if(cmd.getName().equalsIgnoreCase("wand")){
			//player check
			if(p == null){
				sender.sendMessage("You have to be a player to execute this command!");
				return true;
			}
			
			//permission check
			if(!p.hasPermission("wands.getWandsByCommand")){
				p.sendMessage(ChatColor.RED + "You don't have permission for this command!");
				return true;
			}
			
			//args check
			if(args.length < 1){
				p.sendMessage(ChatColor.RED + "No wand name given! Available wand names can be retrieved with TAB.");
				return true;
			}
			//getting wand from args
			Wand wand = Configuration.wandMap.get(args[0]);
			if(wand == null){
				p.sendMessage(ChatColor.RED + "Wand name not available. Retrieve available wand names with TAB");
				return true;
			}
			//create ItemStack and give it to player
			ItemStack is = new ItemStack(wand.material, 1, (short)wand.damaged);
			p.getInventory().addItem(is);
			return true;
		}
		//command for reloading configuration
		else if(cmd.getName().equalsIgnoreCase("wandcfg")){
			
			if(!sender.hasPermission("wands.reloadConfig")){
				sender.sendMessage(ChatColor.RED + "You don't have permission for this command!");
				return true;
			}
			
			Configuration.reloadConfig(this);
			sender.sendMessage(ChatColor.GREEN + "Config successfully reloaded!");
		}
		
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		List<String> list = new ArrayList<String>();
		//returns all wand names loaded
		if(cmd.getName().equalsIgnoreCase("wand")){
			list.addAll(Configuration.wandMap.keySet());
		}
		
		return list;
	}
}
