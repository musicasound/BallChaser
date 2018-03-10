package Scenes;

import java.util.ArrayList;
import java.util.List;

import Entities.Tile;
import IngameSystem.ScoreSystem;

public class IngameScene extends Scene{
	
	List<Tile> tiles=new ArrayList<Tile>();
	
	public IngameScene()
	{
		ScoreSystem.initialize();
		
		
	}
	
	public void update()
	{}
	
	public void cleanUp()
	{}
	
	public void render()
	{}
}
