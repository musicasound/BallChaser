package Main;

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
	public static final float TILE_SCALE=120.0f;
	public static final int TILES_COUNT=16;
	
	public static CharacterType speedyCharacter;
	public static CharacterType powerfulCharacter;
	
	public static void init(Loader loader)
	{
		nonmark_tileTexture=new EntityTexture(loader.loadTexture("images/tile"));
		mark_tileTexture=new EntityTexture(loader.loadTexture("images/tile_mark"));
		speedyCharTexture=new EntityTexture(loader.loadTexture("images/arrow"));
		powerfulCharTexture=new EntityTexture(loader.loadTexture("images/arrow"));
		
		speedyCharacter=new CharacterType(10.0f, 20.0f, 5.0f, new Vector2f(50,50), new Rectangle(0,0, 50, 50), speedyCharTexture, 10.0f, 3.0f, 10.0f);
		powerfulCharacter=new CharacterType(10.0f, 20.0f, 5.0f, new Vector2f(50,50), new Rectangle(0,0, 50, 50), powerfulCharTexture, 10.0f, 3.0f, 10.0f);
		
	}
}
