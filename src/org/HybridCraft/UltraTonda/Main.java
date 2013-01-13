package org.HybridCraft.UltraTonda;

import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.HybridCraft.UltraTonda.Listeners;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{


	Logger logger = Logger.getLogger("Minecraft");

	private void RegisterEvents(){
		pjl = new Listeners(this);
	}

	private Listeners pjl;

	@Override
	public void onDisable(){
		this.getLogger().log(Level.INFO,"Disabled NoDrop");
		this.saveConfig();
	}



	@Override
	public void onEnable(){
		/*
		 * 
		 * Config
		 */
		this.saveDefaultConfig(); //creates a config if there is none
		
		/*
		 * Listener
		 */
		RegisterEvents();

		/*
		 * Logger
		 */
		this.getLogger().log(Level.INFO,"Enabled NoDrop");

	}
	public void unArm(Player p1){
		p1.getInventory().setBoots(null);
		p1.getInventory().setChestplate(null);
		p1.getInventory().setHelmet(null);
		p1.getInventory().setLeggings(null);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

		Player p = (Player)sender;
		if(label.equalsIgnoreCase("kit")){
			if(args.length==1){
				if(args[0].equalsIgnoreCase("archer")){
					p.getInventory().clear();
					unArm(p);
					p.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
					p.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
					p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
					p.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
					p.getInventory().addItem(new ItemStack(Material.BOW));
					p.getInventory().addItem(new ItemStack(Material.ARROW,128));
					p.getInventory().addItem(new ItemStack(Material.WOOD_SWORD));
					return true;	
				}else if(args[0].equalsIgnoreCase("assassin")){
					p.getInventory().clear();
					unArm(p);
					p.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
					p.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
					p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
					p.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
					p.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
					return true;	
				}else if(args[0].equalsIgnoreCase("tank")){
					p.getInventory().clear();
					unArm(p);
					p.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
					p.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
					p.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
					p.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
					p.getInventory().addItem(new ItemStack(Material.WOOD_SWORD));
					return true;	
				}
			}else{
				p.sendMessage(ChatColor.GREEN + "Aviable kits for "+ ChatColor.BOLD +"members:");
				p.sendMessage(ChatColor.YELLOW +"Archer | Assassin | Tank");
				p.sendMessage(ChatColor.GREEN + "Aviable kits for "+ ChatColor.BOLD +"VIP:");
				p.sendMessage(ChatColor.YELLOW + "Comming in a few days.");
				return true;
			}
			

		}if(label.equalsIgnoreCase("score")){
			/*
			 * Points
			 */
				int score = getConfig().getInt("player.points."+p.getName(),1000);
				p.sendMessage("Points: "+score);
			
			/*
			 * Kills
			 */
				
				int kills =getConfig().getInt("player.kills."+p.getName(),0);
				p.sendMessage("Kills: "+kills);
			
			/*
			 * Deaths
			 */
				
				int deaths = getConfig().getInt("player.death."+p.getName(),0);
				
				p.sendMessage("Deaths: "+deaths);
//				double kdr = (double)kills/(double)deaths;
//				if(kdr>0){
//					p.sendMessage("KDR: "+roundTwoDecimals(kdr));
//				}else{
//					p.sendMessage("KDR: "+0);
//				}
				
			

			


		
			
			
			
			return true;
			
			
		}

		return false;

	}
	double roundTwoDecimals(double d) {
    	DecimalFormat twoDForm = new DecimalFormat("#.##");
	return Double.valueOf(twoDForm.format(d));
}


}

