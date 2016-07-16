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
		ConfigurationSerialization.registerClass(WandConfigWrapper.class, "WandWrapper");
		ConfigurationSerialization.registerClass(PotionEffectConfigWrapper.class, "PotionEffectWrapper");
		ConfigurationSerialization.registerClass(ParticleTrailConfigWrapper.class, "ParticleTrailWrapper");
	}
	
	@Override
	public void onEnable() {
		
		Configuration.reloadConfig(this);
		this.getServer().getPluginManager().registerEvents(new WandsListener(this), this);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		
		Player p = null;
		if(sender instanceof Player) p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("wand")){
			if(p == null){
				sender.sendMessage("You have to be a player to execute this command!");
				return true;
			}
			
			if(args.length < 1){
				p.sendMessage(ChatColor.RED + "No wand name given! Available wand names can be retrieved with TAB.");
				return true;
			}
			
			Wand wand = Configuration.wandMap.get(args[0]);
			if(wand == null){
				p.sendMessage(ChatColor.RED + "Wand name not available. Retrieve available wand names with TAB");
				return true;
			}
			
			ItemStack is = new ItemStack(wand.material, 1, (short)wand.damaged);
			p.getInventory().addItem(is);
			return true;
		}
		
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		List<String> list = new ArrayList<String>();
		
		if(cmd.getName().equalsIgnoreCase("wand")){
			list.addAll(Configuration.wandMap.keySet());
		}
		
		return list;
	}
}
