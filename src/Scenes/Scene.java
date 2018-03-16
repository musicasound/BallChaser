package Scenes;

import IngameSystem.GlobalDataManager;
import RenderEngine.Loader;
import RenderEngine.Render2DMaster;

public abstract class Scene {
	
	//상속받은 곳에서 새로 할당되어 사용되고있음 삭제필요 or 상속받은 씬에서 새로이 할당 하지않기
	//protected Loader loader=new Loader();
	//protected Render2DMaster entityRenderer=new Render2DMaster(loader,GlobalDataManager.shader2D);
	//protected Render2DMaster guiRenderer=new Render2DMaster(loader,GlobalDataManager.shader2D);

	public abstract void update();
	public abstract void cleanUp();
	public abstract void render();
}
