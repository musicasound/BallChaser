package Scenes;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import Entities.Ball;
import Entities.Missile;
import Entities.Player;
import Entities.Tile;
import IngameSystem.CollisionManager;
import IngameSystem.ScoreSystem;
import Main.GlobalDataManager;
import RenderEngine.Loader;
import RenderEngine.Renderer2D;
import fontRendering.TextMaster;

public class IngameScene extends Scene{
	
	ArrayList<Tile> tiles=new ArrayList<Tile>();
	ArrayList<Missile> missiles=new ArrayList<Missile>();
	Player player1;
	Player player2;
	Ball ball;
	
	Loader loader=new Loader();
	Renderer2D tileRenderer=new Renderer2D(loader);
	Renderer2D entityRenderer=new Renderer2D(loader);
	Renderer2D GUIRenderer=new Renderer2D(loader);
	
	//���� �÷��̾ ���� ��� �ִ� �ε���
	int currentCatchIdx=-1;
	
	int _tileLoadingIdx=0;
	
	public IngameScene()
	{
		ScoreSystem.initialize();
		
		ArrayList<Tile> tiles=new ArrayList<Tile>();
		//YechanTestEntity entity = new YechanTestEntity(texture,new Transform(new Vector2f(150,150),0,new Vector2f(10,10)));
		loadTiles();
		loadGameObjects();
	}
	
	public void update()
	{
		player1.update();
		player2.update();
		
		for(Missile missile : missiles)
		{
			missile.update();
		}
		
		for(Tile tile : tiles)
		{
			tile.update();
		}
		
		CollisionManager.ProcessCollision(player1, missiles, ball);
		CollisionManager.ProcessCollision(player2, missiles, ball);
		
		//ball�� player�� ���� �����̸�
		if(ball.getCatchingPlayerIdx()!=-1)
		{
			//���� ���� ������ �÷��̾��� Ÿ�� ��ġ�� ������Ʈ �Ѵ�.
			//if �÷��̾ ���� Ÿ���� ���� (���� �ε��� Ÿ�ϰ� �浹)
			//currentCatchIdx�� ������Ʈ�Ѵ�.
			//if(currentCatchIdx==catchStartIdx)
			//{
					//���� ���� �������� �÷��̾��� ���ھ 1�� �ø���.
			//}
			//ScoreSystem.playerOnTile(currentTileIdx);
		}
		else
		{
			//ball�� �÷��̾��� ���� ���°� �ƴϸ�
			//ball�� reflection�� �����Ͽ� �����̰� �Ѵ�.
		}
	}
	
	public void cleanUp()
	{
		tiles.clear();
		missiles.clear();
		TextMaster.cleanUp();
	}
	
	public void render()
	{
		tileRenderer.render();
		tileRenderer.cleanUp();
		guiRenderer.render();
	}
	
	private void loadGameObjects()
	{
		player1=new Player(GlobalDataManager.speedyCharacter, 1, new Vector2f(-270, -270));
		player2=new Player(GlobalDataManager.speedyCharacter, 2, new Vector2f(-240, -270));
		
		ball=new Ball(new Vector2f(-270,0));
	}
	
	private void loadTiles()
	{
		//load tiles
		
		for(int i=0; i<5; i++)
		{
			tiles.add(new Tile(new Vector2f(-240,-240+i*GlobalDataManager.TILE_SCALE)));
			tileRenderer.processInstancingEntity(tiles.get(_tileLoadingIdx));
			_tileLoadingIdx++;
		}
		
		for(int i=0; i<4; i++)
		{
			tiles.add(new Tile(new Vector2f(-120+i*GlobalDataManager.TILE_SCALE,240)));
			tileRenderer.processInstancingEntity(tiles.get(_tileLoadingIdx));
			_tileLoadingIdx++;
		}
		
		for(int i=0; i<4; i++)
		{
			tiles.add(new Tile(new Vector2f(240,120-GlobalDataManager.TILE_SCALE*i)));
			tileRenderer.processInstancingEntity(tiles.get(_tileLoadingIdx));
			_tileLoadingIdx++;
		}
		
		for(int i=0; i<3; i++)
		{
			tiles.add(new Tile(new Vector2f(120-GlobalDataManager.TILE_SCALE*i, -240)));
			tileRenderer.processInstancingEntity(tiles.get(_tileLoadingIdx));
			_tileLoadingIdx++;
		}
	}
}
