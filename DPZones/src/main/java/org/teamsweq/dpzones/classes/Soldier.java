package org.teamsweq.dpzones.classes;

import org.teamsweq.dpzones.ClassAssign;
import org.teamsweq.dpzones.ClassInit;
import org.teamsweq.dpzones.ClassManager;
import org.teamsweq.dpzones.ClassUnAssign;
import org.teamsweq.dpzones.ZonesClass;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Soldier implements ZonesClass {
	
	private Soldier(){}
	
	@ClassInit
	public static void onInit(JavaPlugin plugin){
		plugin.getServer().getPluginManager().registerEvents(new Listener(){
			@EventHandler
			public void soldierSword(PlayerInteractEvent event) {
				Player player = event.getPlayer();
				if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					if(ClassManager.getClass(event.getPlayer())!=null){
						if(ClassManager.getClass(event.getPlayer()).equals(Soldier.class)) {
							if (player.getItemInHand().getType().equals(Material.IRON_SWORD)) {
								player.setVelocity(new Vector(0,.9,0));
							}
						}
					}
				}
			}
			@EventHandler
			public void fallDamage(EntityDamageEvent event) {
				Entity player = event.getEntity();
				if ((player instanceof Player)) {
					if(ClassManager.getClass((Player)event.getEntity())!=null){
						if(ClassManager.getClass((Player)event.getEntity()).equals(Soldier.class)) {
							if (event.getCause().equals(DamageCause.FALL)) {
								event.setCancelled(true);
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
		inventory.addItem(new ItemStack(Material.IRON_SWORD),
				new ItemStack(Material.COOKED_BEEF, 4));
		inventory.setHelmet(new ItemStack(Material.IRON_HELMET));
		inventory.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		inventory.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		inventory.setBoots(new ItemStack(Material.IRON_BOOTS));
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
