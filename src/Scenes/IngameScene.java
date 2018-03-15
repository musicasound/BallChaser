package Scenes;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import Entities.Ball;
import Entities.CharacterStatus;
import Entities.Missile;
import Entities.Player;
import Entities.Tile;
import IngameSystem.CollisionManager;
import IngameSystem.EntityTimer;
import IngameSystem.GameSystemTimer;
import IngameSystem.GameSystemTimer.SysTimerStatus;
import IngameSystem.GlobalDataManager;
import IngameSystem.ScoreSystem;
import RenderEngine.Loader;
import RenderEngine.Renderer2D;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;

public class IngameScene extends Scene{
	
	Tile[] tiles=new Tile[16];
	ArrayList<Missile> missiles=new ArrayList<Missile>();
	Player player1;
	Player player2;
	
	GameSystemTimer systemTimer=new GameSystemTimer();
	EntityTimer player1RebirthTimer=new EntityTimer(GlobalDataManager.PLAYER_DIETIME);
	EntityTimer player2RebirthTimer=new EntityTimer(GlobalDataManager.PLAYER_DIETIME);
	
	Ball ball;
	
	Loader loader=new Loader();
	Renderer2D tileRenderer=new Renderer2D(loader);
	Renderer2D entityRenderer=new Renderer2D(loader);
	Renderer2D GUIRenderer=new Renderer2D(loader);
	
	GUIText scoreText;
	GUIText countdownText;
	
	//현재 플레이어가 볼을 잡고 있는 타일 인덱스
	int currentCatchIdx=-1;
	
	int catchStartIdx=-1;
	
	int _tileLoadingIdx=0;
	
	public IngameScene()
	{
		ScoreSystem.initialize();
		
		//YechanTestEntity entity = new YechanTestEntity(texture,new Transform(new Vector2f(150,150),0,new Vector2f(10,10)));
		loadTiles();
		loadGameObjects();
		scoreText=new GUIText("0 : 0", 160.0F, GlobalDataManager.defaultFontType , new Vector2f(0,0), 360, true);
		scoreText.setColour(1, 0, 0);
		
		countdownText=new GUIText("5", 320.0F, GlobalDataManager.defaultFontType , new Vector2f(0,0), 360, true);
		countdownText.setColour(1, 0, 0);
	}
	
	public void update()
	{
		systemTimer.update();
		player1RebirthTimer.update();
		player2RebirthTimer.update();
		
		switch(systemTimer.getStatus())
		{
		case COUNT_DOWN:
			break;
		case IN_PLAY:
			System.out.println("remain time :"+systemTimer.getRemainGameTime());
			updateIngame();
			break;
		case GAME_OVER:
			SceneManager.loadScene((Scene)new ResultScene());
			break;
		}
	}
	
	private void updateIngame()
	{
		player1.update();
		player2.update();
		ball.update();
		
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
		
		
		processScore();
		
		
		processPlayerTimer();
		
		CollisionManager.checkReflection(ball);
	}
	
	public void cleanUp()
	{
		missiles.clear();
		
	}
	
	public void render()
	{
		processRendering();
		
		tileRenderer.render();
		entityRenderer.render();
		guiRenderer.render();
		
		if(systemTimer.getCurrentCountDown()>0)
		{
			//render countdown
			
		}
		
		// if player 1 die
		// render rebirth countdown
		
		// if player 2 die
		// render rebirth countdown
	}
	
	private void processRendering()
	{
		for(int i=0; i<tiles.length; i++)
		{
			tileRenderer.processInstancingEntity(tiles[i]);
		}
		
		entityRenderer.processInstancingEntity(player1);
		entityRenderer.processInstancingEntity(player2);
		entityRenderer.processInstancingEntity(ball);
	}
	
	private void loadGameObjects()
	{
		player1=new Player(GlobalDataManager.speedyCharacter, 1, new Vector2f(GlobalDataManager.PLAYER1_INIT_POSITION));
		player2=new Player(GlobalDataManager.powerfulCharacter, 2, new Vector2f(GlobalDataManager.PLAYER2_INIT_POSITION));
		
		ball=new Ball(new Vector2f(-240,0));
		entityRenderer.processInstancingEntity(player1);
		entityRenderer.processInstancingEntity(player2);
		entityRenderer.processInstancingEntity(ball);
	}
	
	private void loadTiles()
	{
		//load tiles
		
		for(int i=0; i<5; i++)
		{
			tiles[_tileLoadingIdx]=new Tile(new Vector2f(-240,-240+i*GlobalDataManager.TILE_SCALE),_tileLoadingIdx);
			_tileLoadingIdx++;
		}
		
		for(int i=0; i<4; i++)
		{
			tiles[_tileLoadingIdx]=new Tile(new Vector2f(-120+i*GlobalDataManager.TILE_SCALE,240),_tileLoadingIdx);
			_tileLoadingIdx++;
		}
		
		for(int i=0; i<4; i++)
		{
			tiles[_tileLoadingIdx]=new Tile(new Vector2f(240,120-GlobalDataManager.TILE_SCALE*i),_tileLoadingIdx);
			_tileLoadingIdx++;
		}
		
		for(int i=0; i<3; i++)
		{
			tiles[_tileLoadingIdx]=new Tile(new Vector2f(120-GlobalDataManager.TILE_SCALE*i, -240),_tileLoadingIdx);
			_tileLoadingIdx++;
		}

	}
	
	void processPlayerTimer()
	{
		if(player1.getStatus()==CharacterStatus.DEAD && !player1RebirthTimer.isUpdating())
		{
			player1RebirthTimer.start();
		}
		
		if(player2.getStatus()==CharacterStatus.DEAD && !player2RebirthTimer.isUpdating())
		{
			player2RebirthTimer.start();
		}
		
		if(player1RebirthTimer.isEventOn())
		{
			player1.setStatus(CharacterStatus.LIVE);
			player1.getTransform().setPosition(new Vector2f(GlobalDataManager.PLAYER1_INIT_POSITION));
			player1RebirthTimer.stop();
		}
		
		if(player2RebirthTimer.isEventOn())
		{
			player2.setStatus(CharacterStatus.LIVE);
			player2.getTransform().setPosition(new Vector2f(GlobalDataManager.PLAYER2_INIT_POSITION));
			player2RebirthTimer.stop();
		}
	}

	
	
	//스코어 시스템을 처리함
	void processScore()
	{
		int catchingPlayerIdx=ball.getCatchingPlayerIdx();
		
		Player player;
		
		switch(catchingPlayerIdx)
		{
		case 1:
			player=player1;
		break;
		
		case 2:
			player=player2;
			break;
			
			default:
				player=null;
			break;
		}
		
		
		//스코어링을 시작하는 경우
		if(currentCatchIdx==-1 && catchingPlayerIdx!=-1)
		{
			int catchIdx=0;
			for(; catchIdx<tiles.length; catchIdx++)
			{
				if(CollisionManager.CollisionDetected(tiles[catchIdx], player))
				{
					tiles[catchIdx].setMarked(true);
					break;
				}	
			}
			
			currentCatchIdx=catchIdx;
			catchStartIdx=catchIdx;
			ScoreSystem.setCatchingPlayer(player.getPlayerIndex(), catchIdx);
			
			
		}
		else if(currentCatchIdx!=-1 && catchingPlayerIdx!=-1)
		{
			//스코어링이 진행중인 경우
			int nextTileIdx=(currentCatchIdx+1)%GlobalDataManager.TILES_COUNT;
			
			//다음 타일을 밟았을 경우
			if(CollisionManager.CollisionDetected(tiles[nextTileIdx], player))
			{
				//점수를 계산하고
				ScoreSystem.playerOnTile(nextTileIdx);
				
				//타일을 마크한다.
				tiles[nextTileIdx].setMarked(true);
				currentCatchIdx=nextTileIdx;
				
				//1점을 "득" 했다면
				if(currentCatchIdx==catchStartIdx)
				{
					for(int i=0; i<tiles.length; i++)
					{
						//시작 타일을 제외하고 마크를 해제한다.
						if(i!=catchStartIdx)
						tiles[i].setMarked(false);
					}
				}
				
			}
		}
		else if(catchingPlayerIdx==-1) //플레이어가 볼을 놓친 경우
		{
			currentCatchIdx=-1;
			ScoreSystem.missTheBall();
			for(int i=0; i<tiles.length; i++)
			{
				tiles[i].setMarked(false);
			}
			
		}
	}
}
