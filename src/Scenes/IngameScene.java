package Scenes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

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
import RenderEngine.Render2DMaster;
import fontMeshCreator.GUIText;
import fontRendering.RenderTextMaster;

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
	Render2DMaster tileRenderer=new Render2DMaster(loader,GlobalDataManager.shader2D);
	Render2DMaster entityRenderer=new Render2DMaster(loader,GlobalDataManager.shader2D);
	Render2DMaster GUIRenderer=new Render2DMaster(loader,GlobalDataManager.shader2D);
	
	RenderTextMaster rendertextMaster =new RenderTextMaster(loader,GlobalDataManager.fontRenderer);
	
	//현재 그럴듯한 하드코딩으로 dynamic text에대해 자원관리.. 그냥루프마다 String에대한 vao,vbo할당하고 draw하고 할당해제
	Loader loaderForDynamicText=new Loader();
	RenderTextMaster renderDynamicTextMaster =new RenderTextMaster(loaderForDynamicText,GlobalDataManager.fontRenderer);
	int curTime;//실험코드 무시..
	
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
		loadGuis();
		
	
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
			
			updateIngame();
			break;
		case GAME_OVER:
			SceneManager.loadScene((Scene)new ResultScene());
			break;
		}
	}
	
	public void loadGuis() {
		scoreText=new GUIText("0 : 0", 2.0F, GlobalDataManager.defaultFontType , new Vector2f(0,0), 1.0f, true);
		scoreText.setColour(1, 1, 1);
		rendertextMaster.loadText(scoreText);
	}
	
	public void updateDynamicGuiText() {
		
		//화면잘돌아가나 실험하는코드 무시..
		float floatTime=systemTimer.getRemainGameTime();
		if(curTime ==(int)floatTime) {
			return;
		}
		
		
		curTime=(int)floatTime;
		/*이전에 렌더링리스트와 gpu자원 없애기*/
		loaderForDynamicText.cleanUp();
		renderDynamicTextMaster.cleanUpList();
		
		/**/
		System.out.print(curTime);
		String remainGameTimeStr=String.valueOf(curTime);
		countdownText=new GUIText(remainGameTimeStr, 5.0F, GlobalDataManager.defaultFontType , new Vector2f(0,0.3f), 1.0f, true);
		Random random=new Random();
		countdownText.setCustomizing(new Vector3f(random.nextFloat(),random.nextFloat(),random.nextFloat()),
				new Vector3f(random.nextFloat(),random.nextFloat(),random.nextFloat()),
				0.5f, 0.2f, 0.5f, 0.3f,new Vector2f(0.006f,0.0006f));
		renderDynamicTextMaster.loadText(countdownText);
	}
	
	private void updateIngame()
	{
		player1.update();
		player2.update();
		ball.update();
		updateDynamicGuiText();
		
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
		rendertextMaster.render();
		renderDynamicTextMaster.render();
		
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
