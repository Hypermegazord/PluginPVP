package org.HybridCraft.UltraTonda;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerLoginEvent;
public class Listeners implements Listener {
	private Main plugin;

	Logger logger = Logger.getLogger("Minecraft");


	public Listeners(Main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

	}
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		Player sender = e.getPlayer();

		int score = plugin.getConfig().getInt("player.points."+sender.getName(),1000);
		e.setFormat("["+score+"]" +e.getFormat());
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event){

		Player dead = event.getEntity();
		Player killer = event.getEntity().getKiller();
		event.setDroppedExp(0);

		if(event.getEntity() instanceof Player){
			event.setDeathMessage(ChatColor.AQUA +""+ChatColor.BOLD + "[PVE] " +ChatColor.RESET + "" + killer.getDisplayName()+ChatColor.RESET +  " killed " +  dead.getDisplayName());
			/*
			 * Points from killer
			 */
			if(!plugin.getConfig().isInt("player.points."+killer.getName())){
				plugin.getConfig().set("player.points."+killer.getName(), plugin.getConfig().getInt("player.points."+killer.getName())+1010);
				killer.setLevel(plugin.getConfig().getInt("player.points."+killer.getName()));
			}else{
				plugin.getConfig().set("player.points."+killer.getName(), plugin.getConfig().getInt("player.points."+killer.getName())+10);
				killer.setLevel(plugin.getConfig().getInt("player.points."+killer.getName()));
			}
			/*
			 * Kills/death
			 */
			plugin.getConfig().set("player.death."+dead.getName(), plugin.getConfig().getInt("player.death."+dead.getName())+1);
			plugin.getConfig().set("player.kills."+killer.getName(), plugin.getConfig().getInt("player.kills."+killer.getName())+1);
			/*
			 * Death from dead
			 */
			if(!plugin.getConfig().isInt("player.points."+dead.getName())){
				plugin.getConfig().set("player.points."+dead.getName(), plugin.getConfig().getInt("player.points."+dead.getName())+990);	
				killer.setLevel(plugin.getConfig().getInt("player.points."+dead.getName()));
			}else{
				plugin.getConfig().set("player.points."+dead.getName(), plugin.getConfig().getInt("player.points."+dead.getName())-10);	
				dead.setLevel(dead.getLevel()-10);
			}
			/*
			 * Saves it
			 */
			plugin.saveConfig();
		}


	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event){
		if(!event.getPlayer().isOp()){
			event.getItemDrop().remove();
		}



	}
	@EventHandler
	public void BlockBreakEvent(BlockBreakEvent event){
		if(!event.getPlayer().isOp()){
			event.setCancelled(true);
		}

	}
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if(!event.getPlayer().isOp()){
			event.setCancelled(true);
		}	
	}




	@EventHandler
	public void onPlayerLog(PlayerLoginEvent event){
		Player p = event.getPlayer();
		//gives a player 1000 points if he hasnt got any
		if(!plugin.getConfig().isInt("player.points."+p.getName())){
			p.setLevel(1000);
		}
		// Checking to see if he was kicked for not being on the whitelist.
		if (event.getResult().equals(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_WHITELIST))
		{
			// Setting the message he will recieve with a color.
			event.setKickMessage(p.getName() + ":" + ChatColor.RED+ " You are not whitelistedd!");
			return;
		}else if(event.getResult().equals(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_FULL)){
			event.setKickMessage("Please buy a VIP for reserved Slots.");




			return;
		}
		p.getInventory().clear();
		p.teleport(new Location(p.getWorld(), 195,60,28));

	}

	public static boolean stringContains(String inputString, String[] items) {
		for (int i = 0; i < items.length; i++) {
			if (inputString.contains(items[i])) {
				return true;
			}
		}
		return false;
	}

	@EventHandler
	public void onEntityRegainHealth(EntityRegainHealthEvent event){
		Entity entity = event.getEntity();
		if(entity instanceof Player){
			event.setAmount(0);
		}

	}
}
