package io.github.teamsweq.dpzones;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class PlayerListener implements Listener {

	DPZones plugin;
	
	public PlayerListener(DPZones instance)
	  {
	    this.plugin = instance;
	  }
	
	/*
	 * 
	 * SOLDIER CLASS
	 * 
	 */
	
	//makes soldiers fly
	@EventHandler(priority = EventPriority.NORMAL)
	public void soldierSword(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if(plugin.classListener.getClassID(playerName) == ClassListener.SOLDIER_ID) {
				if (player.getItemInHand().getType().equals(Material.IRON_SWORD)) {
					player.setVelocity(new Vector(0,.9,0));
				}
			}
		}
	}
	
	//turns off fall damage for soldiers
	@EventHandler(priority = EventPriority.LOW)
	public void fallDamage(EntityDamageEvent event) {
		Entity player = event.getEntity();
		if ( (player instanceof Player) ) {
			if (plugin.classListener.getClassID(((Player)player).getName()) == ClassListener.SOLDIER_ID) {
				if (event.getCause().equals(DamageCause.FALL)) {
					event.setCancelled(true);
				}
			}
		}
	}
	
	/*
	 * 
	 * ARCHER CLASS
	 * 
	 */
	
	//makes archers able to headshot ppl
	@EventHandler(priority = EventPriority.HIGH)
	public void headShot(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			Player defender = (Player) event.getEntity();
			if(event.getDamager() instanceof Arrow) {
				Arrow arrow = (Arrow) event.getDamager();
				if(arrow.getShooter() instanceof Player) {
					Player attacker = (Player) arrow.getShooter();
					String playerName = attacker.getName();
					if(plugin.classListener.getClassID(playerName) == ClassListener.ARCHER_ID) {
						if(attacker.getLocation().distance(defender.getLocation()) >= 5.0) {
							defender.setHealth(0.00D);
							defender.sendMessage(ChatColor.GOLD + "You were headshotted by " + attacker.getDisplayName() + "!");
							attacker.sendMessage(ChatColor.GOLD + "You headshotted " + defender.getDisplayName() + "!");
						}
					}
				}
			}
		}
	}
	
	/*
	 * 
	 * MEDIC
	 * 
	 */
	
//	@EventHandler(priority = EventPriority.HIGH)
//	public void snowballKnockback() {
//		
//	}
	
}
