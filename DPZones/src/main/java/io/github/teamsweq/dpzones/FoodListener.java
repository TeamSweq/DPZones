package io.github.teamsweq.dpzones;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class FoodListener implements Listener {
	
	DPZones plugin;
	
	public FoodListener(DPZones instance)
	  {
	    this.plugin = instance;
	  }
	
	//Sets players hunger level
	
	public static void hungerLevel(Player player, int hunger, double health) {
		player.setFoodLevel(hunger);
		player.setExhaustion(-1);
		player.setSaturation(100);
		player.setHealth(health);
	}
	
	//Takes 1 steak away if they player tries to eat it
	
	public static void subtractAmount(Player player, int amount) {
	    if (player.getItemInHand().getAmount() <= amount)
	      player.setItemInHand(new ItemStack(Material.AIR));
	    else
	      player.getItemInHand().setAmount(player.getItemInHand().getAmount() - amount);
	  }
	
	//Prevents the player from consuming the steak.
	
	@EventHandler
	public void consumeBlock(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		if (player.getItemInHand().getType() == Material.COOKED_BEEF) {
			event.setCancelled(true);
		}
	}

	
	//Checks to see if the player tries to eat the steak 
	//Adds 4 hearts to their health
	
	@EventHandler
	public void onPlayerSteakEvent(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
				if (player.getItemInHand().getType() == Material.COOKED_BEEF) {
					double health = 8.0D;
					double playerHealth = player.getHealth();
					if (!(playerHealth >= 20.0D)) {
						
						if (playerHealth >= 13.0D) {
							player.setHealth(20.0D);
						}
						
						if (playerHealth <= 12.0D) {
							player.setHealth(playerHealth + health);
						}
						event.setCancelled(true);
						subtractAmount(player, 1);
					}
				}
			}
		}
	
}
