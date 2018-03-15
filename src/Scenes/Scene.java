package Scenes;

import IngameSystem.GlobalDataManager;
import RenderEngine.Loader;
import RenderEngine.Render2DMaster;

public abstract class Scene {
	
	protected Loader loader=new Loader();
	protected Render2DMaster entityRenderer=new Render2DMaster(loader,GlobalDataManager.shader2D);
	protected Render2DMaster guiRenderer=new Render2DMaster(loader,GlobalDataManager.shader2D);

	public abstract void update();
	public abstract void cleanUp();
	public abstract void render();
}
