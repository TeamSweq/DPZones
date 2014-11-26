package io.github.teamsweq.dpzones.classes;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.teamsweq.dpzonesv2.ClassAssign;
import io.github.teamsweq.dpzonesv2.ClassInit;
import io.github.teamsweq.dpzonesv2.ClassManager;
import io.github.teamsweq.dpzonesv2.ClassUnAssign;
import io.github.teamsweq.dpzonesv2.ZonesClass;

public class Medic implements ZonesClass {
	
	private Medic(){}
	
	@ClassInit
	public static void onInit(JavaPlugin plugin){
		plugin.getServer().getPluginManager().registerEvents(new Listener(){
			
			@EventHandler
			public void onItemConsume(PlayerItemConsumeEvent event){
				if(ClassManager.getClass(event.getPlayer()).equals(Medic.class)){
					if(event.getItem().getType()==Material.COOKED_BEEF){
						event.setCancelled(true);
					}
				}
			}
			
			@EventHandler
			public void onSteak(PlayerInteractEvent event){
				if(ClassManager.getClass(event.getPlayer()).equals(Medic.class)){
					if(event.getAction()==Action.RIGHT_CLICK_AIR||event.getAction()==Action.RIGHT_CLICK_BLOCK){
						event.getPlayer().setHealth(Math.min(event.getPlayer().getHealth()+8D, event.getPlayer().getMaxHealth()));
					}
				}
			}
		}, plugin);
	}
	
	@ClassAssign
	public static void onAssign(Player player){
		PlayerInventory inventory = player.getInventory();
		inventory.addItem(new ItemStack(Material.GOLD_SWORD),
				new ItemStack(Material.COOKED_BEEF, 3),
				new ItemStack(Material.SNOW_BALL, 12));
		inventory.setHelmet(new ItemStack(Material.GOLD_HELMET));
		inventory.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
		inventory.setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
		inventory.setBoots(new ItemStack(Material.GOLD_BOOTS));
	}
	
	@ClassUnAssign
	public static void onUnAssign(Player player){
		PlayerInventory inventory = player.getInventory();
		inventory.clear();
	}
}
