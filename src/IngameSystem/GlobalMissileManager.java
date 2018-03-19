package IngameSystem;

import java.util.ArrayList;

import Entities.Entity;
import Entities.Missile;
import Entities.MissileItem;

public class GlobalMissileManager {
	
	private static ArrayList<MissileItem> missileItems;
	private static ArrayList<Missile> missiles;
	
	public static void initialize()
	{
		missileItems=new ArrayList<MissileItem>();
		missiles=new ArrayList<Missile>();
		missileItems.clear();
		missiles.clear();
		
	}
	
	public static ArrayList<Missile> getMissileList()
	{
		return missiles;
	}
	
	public static ArrayList<MissileItem> getMissileItems()
	{
		return missileItems;
	}
	
	public static void Instantiate(Missile object)
	{
		missiles.add(object);
	}
	
	public static void Instantiate(MissileItem object)
	{
		missileItems.add(object);
	}
}
