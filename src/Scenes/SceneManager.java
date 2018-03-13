package Scenes;

public class SceneManager {
	static Scene currentScene=null;
	
	public static void init()
	{
		currentScene=new IngameScene();
	}
	
	public static void loadScene(Scene scene)
	{
		currentScene=scene;
	}
	
	public static Scene getCurrentScene()
	{
		return currentScene;
	}
}
