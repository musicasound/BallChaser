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
	Renderer2D entityRenderer=new Renderer2D(loader);
	Renderer2D GUIRenderer=new Renderer2D(loader);
	
	//�÷��̾ ���� ��� ������ �ε���
	int catchStartIndex;
	
	//���� �÷��̾ ���� ��� �ִ� �ε���
	int currentCatchIdx;
	
	public IngameScene()
	{
		ScoreSystem.initialize();
		
		ArrayList<Tile> tiles=new ArrayList<Tile>();
		
		
		//YechanTestEntity entity = new YechanTestEntity(texture,new Transform(new Vector2f(150,150),0,new Vector2f(10,10)));
		
		
		//load tiles
		
		int _tileLoadingIdx=0;
		for(int i=0; i<10; i++)
		{
			tiles.add(new Tile(new Vector2f(-270,-270+i*60.0f)));
			entityRenderer.processInstancingEntity(tiles.get(_tileLoadingIdx));
			_tileLoadingIdx++;
		}
		
		for(int i=0; i<9; i++)
		{
			tiles.add(new Tile(new Vector2f(-210+i*60.0f,270)));
			entityRenderer.processInstancingEntity(tiles.get(_tileLoadingIdx));
			_tileLoadingIdx++;
		}
		
		for(int i=0; i<9; i++)
		{
			tiles.add(new Tile(new Vector2f(270,210-60.0f*i)));
			entityRenderer.processInstancingEntity(tiles.get(_tileLoadingIdx));
			_tileLoadingIdx++;
		}
		
		for(int i=0; i<8; i++)
		{
			tiles.add(new Tile(new Vector2f(210-60.0f*i, -270)));
			entityRenderer.processInstancingEntity(tiles.get(_tileLoadingIdx));
			_tileLoadingIdx++;
		}
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
		entityRenderer.render();
		guiRenderer.render();
	}
}
