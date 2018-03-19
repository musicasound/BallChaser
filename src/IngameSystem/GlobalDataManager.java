package IngameSystem;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import DataTypes.CharacterType;
import Picking.MousePicking;
import RenderEngine.Loader;
import Shaders.Shader2D;
import Textures.EntityTexture;
import fontMeshCreator.FontType;
import fontRendering.FontRenderer;
import fontRendering.FontShader;

public class GlobalDataManager {

	public static EntityTexture nonmark_tileTexture;
	public static EntityTexture mark_tileTexture;
	public static EntityTexture speedyCharTexture;
	public static EntityTexture powerfulCharTexture;
	public static EntityTexture ballTexture;
	public static EntityTexture cautionMarkTexture;
	public static EntityTexture missileItemTexture;
	public static EntityTexture separationMissileTexture;
	public static EntityTexture stunMissileTexture;
	public static EntityTexture slowMissileTexture;
	public static EntityTexture deadSpaceTexture;
	public static EntityTexture deadSpaceNoDeadTexture;
	public static final float TILE_SCALE=120.0f;
	public static final int TILES_COUNT=16;
	public static final float CAUTION_MARK_LIFETIME=5.0f;
	public static final float STUN_TOTAL_TIME=4.0f;
	
	public static MousePicking mousePicking;
	
	//일관성있게 하지 않은점 죄송함당..  2DRenderer 라는 클래스가 생략되었습니다.
	//shader program 
	//shader 는 shaderProgram 
	//renderer 는 shader를 인자로받아 생성되고  opengl상태를 변경하는등.. function집합  이둘은 static함
	//scene에서 생성되는 Master(ex RendertextMaster,Render2DMaster)는 Object들을 모은뒤에 생성할때정한 static renderer로 render한다.
	public static Shader2D shader2D =new Shader2D();
	
	private static FontShader fontShader  =new FontShader();
	public static FontRenderer fontRenderer = new FontRenderer(fontShader);
	
	//표준 폰트
	public static FontType defaultFontType;
	
	public static final float PLAYER_DIETIME=3.0f;
	
	public static final Vector2f PLAYER1_INIT_POSITION=new Vector2f(-250, -240);
	public static final Vector2f PLAYER2_INIT_POSITION=new Vector2f(-210, -240);
	
	
	public static CharacterType speedyCharacter;
	public static CharacterType powerfulCharacter;
	
	public static ArrayList<HashMap<KeySystem.CharacterKeySetting, Integer>> keySettings=new ArrayList<HashMap<KeySystem.CharacterKeySetting, Integer>>();
	
	public static void init(Loader loader)
	{
		loadTextures(loader);
		loadCharacters();
		setDefaultKeySettings();
		
		mousePicking=new MousePicking();
		
		
		defaultFontType=new FontType(loader.loadFontTextureAtlas("candara"), new File("res/candara.fnt"));
		
	}
	
	private static void setDefaultKeySettings()
	{
		keySettings.add(new HashMap<>());
		keySettings.add(new HashMap<KeySystem.CharacterKeySetting, Integer>());
		keySettings.add(new HashMap<KeySystem.CharacterKeySetting, Integer>());
		
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
	
	private static void loadTextures(Loader loader)
	{
		nonmark_tileTexture=new EntityTexture(loader.loadTexture("images/tile"));
		mark_tileTexture=new EntityTexture(loader.loadTexture("images/tile_mark"));
		speedyCharTexture=new EntityTexture(loader.loadTexture("images/arrow"));
		powerfulCharTexture=new EntityTexture(loader.loadTexture("images/arrow"));
		ballTexture=new EntityTexture(loader.loadTexture("images/ball"));
		missileItemTexture=new EntityTexture(loader.loadTexture("images/missileItem"));
		separationMissileTexture=new EntityTexture(loader.loadTexture("images/separateMissile"));
		stunMissileTexture=new EntityTexture(loader.loadTexture("images/stunMissile"));
		slowMissileTexture=new EntityTexture(loader.loadTexture("images/slowMissile"));
		cautionMarkTexture=new EntityTexture(loader.loadTexture("images/cautionMark"));
		deadSpaceTexture=new EntityTexture(loader.loadTexture("images/deadspace"));
		deadSpaceNoDeadTexture=new EntityTexture(loader.loadTexture("images/yellowsquare"));
	}
	
	private static void loadCharacters()
	{
		
		speedyCharacter=new CharacterType(210.0f, 1800.0f, 50.0f, new Vector2f(50,50), new Rectangle(0,0, 50, 50), speedyCharTexture, 10.0f, 3.0f, 90.0f);
		powerfulCharacter=new CharacterType(180.0f, 1800.0f, 50.0f, new Vector2f(50,50), new Rectangle(0,0, 50, 50), powerfulCharTexture, 10.0f, 3.0f, 90.0f);
		
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
