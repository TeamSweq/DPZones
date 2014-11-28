package io.github.teamsweq.dpzones.classes;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.teamsweq.dpzones.ClassAssign;
import io.github.teamsweq.dpzones.ClassInit;
import io.github.teamsweq.dpzones.ClassManager;
import io.github.teamsweq.dpzones.ClassUnAssign;
import io.github.teamsweq.dpzones.ZonesClass;

public class Archer implements ZonesClass {
	
	private Archer(){}
	
	@ClassInit
	public static void onInit(JavaPlugin plugin){
		plugin.getServer().getPluginManager().registerEvents(new Listener(){
			@EventHandler
			public void headshot(EntityDamageByEntityEvent event) {
				if(event.getEntity() instanceof Player) {
					Player defender = (Player) event.getEntity();
					if(event.getDamager() instanceof Arrow) {
						Arrow arrow = (Arrow) event.getDamager();
						if(arrow.getShooter() instanceof Player) {
							Player attacker = (Player) arrow.getShooter();
							if(ClassManager.getClass(attacker) != null){
								if(ClassManager.getClass(attacker).equals(Archer.class)) {
									if(attacker.getLocation().distance(defender.getLocation()) >= 30.0) {
										event.setDamage(Double.MAX_VALUE);
										defender.sendMessage(ChatColor.GOLD + "You were headshotted by " + attacker.getDisplayName() + "!");
										attacker.sendMessage(ChatColor.GOLD + "You headshotted " + defender.getDisplayName() + "!");
									}
								}
							}
						}
					}
				}
			}
		}, plugin);
	}
	
	@ClassAssign
	public static void onAssign(Player player){
		PlayerInventory inventory = player.getInventory();
		inventory.clear();
		inventory.addItem(new ItemStack(Material.STONE_SWORD),
				new ItemStack(Material.COOKED_BEEF, 4),
				new ItemStack(Material.BOW),
				new ItemStack(Material.ARROW, 64),
				new ItemStack(Material.ARROW, 64));
		inventory.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
		inventory.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		inventory.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		inventory.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
		player.setGameMode(GameMode.ADVENTURE);
		player.setFoodLevel(15);
	}
	
	@ClassUnAssign
	public static void onUnAssign(Player player){
		PlayerInventory inventory = player.getInventory();
		inventory.clear();
		player.setGameMode(GameMode.SURVIVAL);
	}
}
