package io.github.teamsweq.dpzones.classes;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.teamsweq.dpzones.ClassAssign;
import io.github.teamsweq.dpzones.ClassInit;
import io.github.teamsweq.dpzones.ClassManager;
import io.github.teamsweq.dpzones.ClassUnAssign;
import io.github.teamsweq.dpzones.ZonesClass;

public class Scout implements ZonesClass {
	
	private Scout(){}
	
	@ClassInit
	public static void onInit(JavaPlugin plugin){
		plugin.getServer().getPluginManager().registerEvents(new Listener(){
			@EventHandler
			public void headshot(EntityDamageByEntityEvent event) {
				if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
					Player attacker = (Player) event.getDamager();
					Player defender = (Player) event.getEntity();
					if(ClassManager.getClass(attacker) != null){
						if(ClassManager.getClass(attacker).equals(Scout.class)){
							if(attacker.getItemInHand().getType()==Material.STONE_SWORD){
								defender.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 4, 1, true));
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
				new ItemStack(Material.FISHING_ROD));
		inventory.setHelmet(new ItemStack(Material.LEATHER_HELMET));
		inventory.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		inventory.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		inventory.setBoots(new ItemStack(Material.LEATHER_BOOTS));
		player.setGameMode(GameMode.ADVENTURE);
		player.setFoodLevel(15);
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, true));
	}
	
	@ClassUnAssign
	public static void onUnAssign(Player player){
		PlayerInventory inventory = player.getInventory();
		inventory.clear();
		player.setGameMode(GameMode.SURVIVAL);
		player.removePotionEffect(PotionEffectType.SPEED);
	}
}
