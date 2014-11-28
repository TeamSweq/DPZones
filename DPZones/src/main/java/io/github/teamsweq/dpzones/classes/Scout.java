package io.github.teamsweq.dpzones.classes;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fish;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

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
			public void onDamage(EntityDamageByEntityEvent event) {
				if(event.getDamager() instanceof Player) {
					Player attacker = (Player) event.getDamager();
					if(ClassManager.getClass(attacker) != null){
						if(ClassManager.getClass(attacker).equals(Scout.class)){
							if(attacker.getItemInHand().getType()==Material.STONE_SWORD){
								System.out.println("HIT: "+event.getEntityType().toString());
								((LivingEntity) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 4, 1, true));
							}
						}
					}
				}
			}
			@EventHandler
			public void onFish(PlayerFishEvent event){
				if(ClassManager.getClass(event.getPlayer())!=null){
					if(ClassManager.getClass(event.getPlayer()).equals(Scout.class)){
						if(event.getState()==PlayerFishEvent.State.IN_GROUND){
							launch(event.getPlayer(), event.getHook().getLocation());
						}
						if(event.getState()==PlayerFishEvent.State.FAILED_ATTEMPT){
							if(hasSurroundingBlocks(event.getHook().getLocation())){
								launch(event.getPlayer(), event.getHook().getLocation());
							}
						}
					}
				}
			}
			@EventHandler
			public void onLaunch(ProjectileLaunchEvent event){
				if(event.getEntity() instanceof Fish){
					event.getEntity().setVelocity(event.getEntity().getVelocity().multiply(1.2D));
				}
			}
			@EventHandler
			public void fallDamage(EntityDamageEvent event) {
				Entity player = event.getEntity();
				if ((player instanceof Player)) {
					if(ClassManager.getClass((Player)event.getEntity())!=null){
						if(ClassManager.getClass((Player)event.getEntity()).equals(Scout.class)) {
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
	private static boolean hasSurroundingBlocks(Location location){
		for(int x=-1;x<=1;++x){
			for(int y=-1;y<=1;++y){
				for(int z=-1;z<=1;++z){
					if(location.add(x, y, z).getBlock().getType()!=Material.AIR){
						return true;
					}
				}
			}
		}
		return false;
	}
	private static void launch(Player player, Location location){
		Location pLocation = player.getLocation();
		pLocation.setY(pLocation.getY()+0.5D);
		player.teleport(pLocation);
		double gravity = -0.08D;
		double distance = location.distance(pLocation);
		double t = distance;
		double vx = (1.0D+0.07D*t)*(location.getX()-pLocation.getX())/t;
		double vy = (1.0D+0.03D*t)*(location.getY()-pLocation.getY())/t-0.5D*gravity*t;
		double vz = (1.0D+0.07D*t)*(location.getZ()-pLocation.getZ())/t;
		player.setVelocity(new Vector(vx, vy, vz));
	}
}
