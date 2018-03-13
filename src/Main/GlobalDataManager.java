package Main;

import RenderEngine.Loader;
import Textures.EntityTexture;

public class GlobalDataManager {

	public static EntityTexture tileTexture;
	
	public static void init(Loader loader)
	{
		tileTexture=new EntityTexture(loader.loadTexture("box"));
	}
}
