package IngameSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import DataTypes.CharacterType;
import RenderEngine.Loader;
import Textures.EntityTexture;

public class GlobalDataManager {

	public static EntityTexture nonmark_tileTexture;
	public static EntityTexture mark_tileTexture;
	public static EntityTexture speedyCharTexture;
	public static EntityTexture powerfulCharTexture;
	public static EntityTexture ballTexture;
	public static final float TILE_SCALE=120.0f;
	public static final int TILES_COUNT=16;
	
	public static final float PLAYER_DIETIME=3.0f;
	
	public static final Vector2f PLAYER1_INIT_POSITION=new Vector2f(-250, -240);
	public static final Vector2f PLAYER2_INIT_POSITION=new Vector2f(-210, -240);
	
	
	public static CharacterType speedyCharacter;
	public static CharacterType powerfulCharacter;
	
	public static ArrayList<HashMap<KeySystem.CharacterKeySetting, Integer>> keySettings=new ArrayList<HashMap<KeySystem.CharacterKeySetting, Integer>>();
	
	public static void init(Loader loader)
	{
		nonmark_tileTexture=new EntityTexture(loader.loadTexture("images/tile"));
		mark_tileTexture=new EntityTexture(loader.loadTexture("images/tile_mark"));
		speedyCharTexture=new EntityTexture(loader.loadTexture("images/arrow"));
		powerfulCharTexture=new EntityTexture(loader.loadTexture("images/arrow"));
		ballTexture=new EntityTexture(loader.loadTexture("images/ball"));
		
		speedyCharacter=new CharacterType(120.0f, 1800.0f, 50.0f, new Vector2f(50,50), new Rectangle(0,0, 50, 50), speedyCharTexture, 10.0f, 3.0f, 90.0f);
		powerfulCharacter=new CharacterType(90.0f, 1800.0f, 50.0f, new Vector2f(50,50), new Rectangle(0,0, 50, 50), powerfulCharTexture, 10.0f, 3.0f, 90.0f);
		
		keySettings.add(new HashMap<>());
		keySettings.add(new HashMap<KeySystem.CharacterKeySetting, Integer>());
		keySettings.add(new HashMap<KeySystem.CharacterKeySetting, Integer>());
		
		setDefaultKeySettings();
	}
	
	private static void setDefaultKeySettings()
	{
		HashMap<KeySystem.CharacterKeySetting, Integer> player1Settings=keySettings.get(1);
		HashMap<KeySystem.CharacterKeySetting, Integer> player2Settings=keySettings.get(2);
		
		player1Settings.put(KeySystem.CharacterKeySetting.FORWARD, Keyboard.KEY_UP);
		player1Settings.put(KeySystem.CharacterKeySetting.BACKWARD, Keyboard.KEY_DOWN);
		player1Settings.put(KeySystem.CharacterKeySetting.CW_ROT, Keyboard.KEY_RIGHT);
		player1Settings.put(KeySystem.CharacterKeySetting.CCW_ROT, Keyboard.KEY_LEFT);
		player1Settings.put(KeySystem.CharacterKeySetting.MISSILE_SHOT, Keyboard.KEY_RETURN);
	
		player2Settings.put(KeySystem.CharacterKeySetting.FORWARD, Keyboard.KEY_W);
		player2Settings.put(KeySystem.CharacterKeySetting.BACKWARD, Keyboard.KEY_S);
		player2Settings.put(KeySystem.CharacterKeySetting.CW_ROT, Keyboard.KEY_D);
		player2Settings.put(KeySystem.CharacterKeySetting.CCW_ROT, Keyboard.KEY_A);
		player2Settings.put(KeySystem.CharacterKeySetting.MISSILE_SHOT, Keyboard.KEY_SPACE);
	
	}
	
	public static HashMap<KeySystem.CharacterKeySetting, Integer> getKeySettings(int playerIdx)
	{
		if(playerIdx==1 || playerIdx==2)
		{
			return keySettings.get(playerIdx);
		}
		else
			return null;
	}
}
