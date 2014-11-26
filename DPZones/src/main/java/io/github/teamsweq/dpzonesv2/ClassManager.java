package io.github.teamsweq.dpzonesv2;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ClassManager {
	private static Map<Class<? extends ZonesClass>, List<UUID>> players;
	private static Map<Class<? extends ZonesClass>, Map<String, List<Method>>> clazzes;
	
	static{
		players = new HashMap<Class<? extends ZonesClass>, List<UUID>>();
	}
	/**
	 * Register a class
	 * @param clazz Class to register
	 */
	public static void registerClass(Class<? extends ZonesClass> clazz){
		
	}
	/**
	 * 
	 * @param uuid UUID of Player
	 * @return Class of Player - Null if None
	 */
	public static Class<? extends ZonesClass> getClass(UUID uuid){
		for(Class<? extends ZonesClass> clazz: players.keySet()){
			for(UUID player: players.get(clazz)){
				if(player.equals(uuid)){
					return clazz;
				}
			}
		}
		return null;
	}
	/**
	 * 
	 * @param uuid UUID of Player
	 * @param clazz Class of Player to be Assigned
	 */
	public static void assignClass(UUID uuid, Class<? extends ZonesClass> clazz){
		List<UUID> players = ClassManager.players.get(clazz);
		if(players==null){
			players = new ArrayList<UUID>();
		}
		players.add(uuid);
		ClassManager.players.put(clazz, players);
	}
	private static void invokeMethod(Class<? extends ZonesClass> clazz, String methodName, Object arguments){
		for(Method method: clazzes.get(clazz).get(methodName)){
			ReflectionUtil.invokePublicMethod(method, null, arguments);
		}
	}
}
