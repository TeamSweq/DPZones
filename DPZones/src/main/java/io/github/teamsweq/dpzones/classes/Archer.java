package io.github.teamsweq.dpzones.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
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
			public void onItemConsume(PlayerItemConsumeEvent event){
				if(ClassManager.getClass(event.getPlayer()).equals(Archer.class)){
					if(event.getItem().getType()==Material.COOKED_BEEF){
						event.setCancelled(true);
					}
				}
			}
			@EventHandler
			public void onSteak(PlayerInteractEvent event){
				if(ClassManager.getClass(event.getPlayer()).equals(Archer.class)){
					if(event.getAction()==Action.RIGHT_CLICK_AIR||event.getAction()==Action.RIGHT_CLICK_BLOCK){
						if(event.getItem()!=null){
							if(event.getItem().getType()==Material.COOKED_BEEF){
								if(event.getPlayer().getHealth()<event.getPlayer().getMaxHealth()){
									event.getPlayer().setHealth(Math.min(event.getPlayer().getHealth()+8D, event.getPlayer().getMaxHealth()));
									if(event.getItem().getAmount()==1){
										event.getPlayer().setItemInHand(null);
									}else{
										event.getItem().setAmount(event.getItem().getAmount()-1);
									}
								}
							}
						}
					}
				}
			}
			@EventHandler
			public void headShot(EntityDamageByEntityEvent event) {
				if(event.getEntity() instanceof Player) {
					Player defender = (Player) event.getEntity();
					if(event.getDamager() instanceof Arrow) {
						Arrow arrow = (Arrow) event.getDamager();
						if(arrow.getShooter() instanceof Player) {
							Player attacker = (Player) arrow.getShooter();
							if(ClassManager.getClass((Player) arrow.getShooter()).equals(Archer.class)) {
								if(attacker.getLocation().distance(defender.getLocation()) >= 30.0) {
									defender.setHealth(0.0D);
									defender.sendMessage(ChatColor.GOLD + "You were headshotted by " + attacker.getDisplayName() + "!");
									attacker.sendMessage(ChatColor.GOLD + "You headshotted " + defender.getDisplayName() + "!");
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
		inventory.addItem(new ItemStack(Material.STONE_SWORD),
				new ItemStack(Material.COOKED_BEEF, 4),
				new ItemStack(Material.BOW),
				new ItemStack(Material.ARROW, 64),
				new ItemStack(Material.ARROW, 64));
		inventory.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
		inventory.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		inventory.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		inventory.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
		player.setFoodLevel(15);
	}
	
	@ClassUnAssign
	public static void onUnAssign(Player player){
		PlayerInventory inventory = player.getInventory();
		inventory.clear();
	}
}
