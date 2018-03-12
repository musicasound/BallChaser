package Scenes;

import java.util.ArrayList;
import java.util.List;

import Entities.Ball;
import Entities.Missile;
import Entities.Player;
import Entities.Tile;
import IngameSystem.CollisionManager;
import IngameSystem.ScoreSystem;
import RenderEngine.Loader;
import RenderEngine.Renderer2D;

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
		if(ball.getCatchingPlayerIdx())
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
	}
	
	public void render()
	{
	}
}
