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
	
	//플레이어가 볼을 잡기 시작한 인덱스
	int catchStartIndex;
	
	//현재 플레이어가 볼을 잡고 있는 인덱스
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
		
		//ball이 player의 소유 상태이면
		if(ball.getCatchingPlayerIdx())
		{
			//현재 볼을 소유한 플레이어의 타일 위치를 업데이트 한다.
			
			//if 플레이어가 다음 타일을 밟음 (다음 인덱스 타일과 충돌)
				//currentCatchIdx를 업데이트한다.
				//if(currentCatchIdx==catchStartIdx)
				//{
						//현재 볼을 소유중인 플레이어의 스코어를 1점 올린다.
				//}
		}
		else
		{
			//ball이 플레이어의 소유 상태가 아니면
			//ball에 reflection을 적용하여 움직이게 한다.
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
