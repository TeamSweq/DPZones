package io.github.teamsweq.dpzones;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ClassManager {
	private static Map<Class<? extends ZonesClass>, List<UUID>> players;
	private static Map<Class<? extends ZonesClass>, Map<String, List<Method>>> clazzes;
	
	static{
		clazzes = new HashMap<Class<? extends ZonesClass>, Map<String, List<Method>>>();
		players = new HashMap<Class<? extends ZonesClass>, List<UUID>>();
	}
	/**
	 * Register a class
	 * @param clazz Class to register
	 */
	public static void registerZonesClass(Class<? extends ZonesClass> clazz, JavaPlugin plugin){
		Map<String, List<Method>> clazzMethods = clazzes.get(clazz);
		if(clazzMethods==null){
			clazzMethods = new HashMap<String, List<Method>>();
		}
		for(Method method: clazz.getMethods()){
			if(Modifier.isStatic(method.getModifiers())){
				for(Annotation annotation: method.getAnnotations()){
					if(annotation.annotationType().getPackage().equals(ClassManager.class.getPackage())){
						String name = annotation.annotationType().getSimpleName().substring("CLASS".length()).toUpperCase();
						List<Method> methods = clazzMethods.get(name);
						if(methods==null){
							methods = new ArrayList<Method>();
						}
						methods.add(method);
						clazzMethods.put(name, methods);
					}
				}
			}
		}
		clazzes.put(clazz, clazzMethods);
		invokeMethod(clazz, "INIT", plugin);
	}
	/**
	 * 
	 * @param uuid UUID of Player
	 * @param clazz Class of Player to be Assigned
	 * @return Old Class of Player - Null if None
	 */
	public static Class<? extends ZonesClass> assignClass(Player player, Class<? extends ZonesClass> clazz){
		Class<? extends ZonesClass> oldClass = getClass(player);
		if(oldClass!=null){
			removeClass(player, oldClass);
			invokeMethod(oldClass, "UNASSIGN", player);
		}
		List<UUID> players = ClassManager.players.get(clazz);
		if(players==null){
			players = new ArrayList<UUID>();
		}
		players.add(player.getUniqueId());
		ClassManager.players.put(clazz, players);
		invokeMethod(clazz, "ASSIGN", player);
		return oldClass;
	}
	private static void removeClass(Player player, Class<? extends ZonesClass> clazz){
		List<UUID> players = ClassManager.players.get(clazz);
		if(players!=null){
			players.remove(player.getUniqueId());
		}
		ClassManager.players.put(clazz, players);
	}
	public static void resetClass(Player player){
		if(getClass(player)!=null){
			invokeMethod(getClass(player), "UNASSIGN", player);
			invokeMethod(getClass(player), "ASSIGN", player);
		}
	}
	/**
	 * 
	 * @param uuid UUID of Player
	 * @return Class of Player - Null if None
	 */
	public static Class<? extends ZonesClass> getClass(Player player){
		for(Class<? extends ZonesClass> clazz: players.keySet()){
			for(UUID uuid: players.get(clazz)){
				if(uuid.equals(player.getUniqueId())){
					return clazz;
				}
			}
		}
		return null;
	}
	/**
	 * 
	 * @param name Name of Class
	 * @return Class - Null if not Registered
	 */
	public static Class<? extends ZonesClass> getClass(String name){
		for(Class<? extends ZonesClass> clazz: clazzes.keySet()){
			if(clazz.getSimpleName().equalsIgnoreCase(name)){
				return clazz;
			}
		}
		return null;
	}
	private static void invokeMethod(Class<? extends ZonesClass> clazz, String methodName, Object... arguments){
		if(clazzes.get(clazz)!=null){
			if(clazzes.get(clazz).get(methodName)!=null){
				for(Method method: clazzes.get(clazz).get(methodName)){
					ReflectionUtil.invokePublicMethod(method, null, arguments);
				}
			}
		}
	}
}
