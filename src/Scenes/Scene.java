package Scenes;

import RenderEngine.Loader;
import RenderEngine.Renderer2D;

public abstract class Scene {
	
	protected Loader loader=new Loader();
	protected Renderer2D entityRenderer=new Renderer2D(loader);
	protected Renderer2D guiRenderer=new Renderer2D(loader);

	public abstract void update();
	public abstract void cleanUp();
	public abstract void render();
}
