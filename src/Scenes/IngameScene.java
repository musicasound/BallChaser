package Scenes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import DataTypes.MissileType;
import Entities.Ball;
import Entities.CautionMark;
import Entities.CharacterStatus;
import Entities.DeadSpace;
import Entities.Missile;
import Entities.MissileItem;
import Entities.Player;
import Entities.Tile;
import Guis.GuiButton;
import IngameSystem.CollisionManager;
import IngameSystem.EntityTimer;
import IngameSystem.GameSystemTimer;
import IngameSystem.GameSystemTimer.SysTimerStatus;
import IngameSystem.GlobalDataManager;
import IngameSystem.GlobalMissileManager;
import IngameSystem.ScoreSystem;
import Physics.Transform;
import RenderEngine.Loader;
import RenderEngine.Render2DMaster;
import Textures.EntityTexture;
import fontMeshCreator.GUIText;
import fontRendering.RenderTextMaster;

public class IngameScene extends Scene{
	
	Tile[] tiles=new Tile[16];
	ArrayList<CautionMark> cautionMarks=new ArrayList<CautionMark>();
	ArrayList<DeadSpace> deadSpaces=new ArrayList<DeadSpace>();
	Player player1;
	Player player2;
	
	GameSystemTimer systemTimer=new GameSystemTimer();
	EntityTimer player1RebirthTimer=new EntityTimer(GlobalDataManager.PLAYER_DIETIME);
	EntityTimer player2RebirthTimer=new EntityTimer(GlobalDataManager.PLAYER_DIETIME);
	
	Ball ball;
	
	GuiButton quitButton;
	
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
	
	
	//
	EntityTimer cautionTimer=new EntityTimer(7.0f);
	EntityTimer missileItemGenTimer=new EntityTimer(3.0f);
	int[] tileIdx=new int[6];
	
	
	
	
	public IngameScene()
	{
		ScoreSystem.initialize();
		GlobalMissileManager.initialize();
		
		//YechanTestEntity entity = new YechanTestEntity(texture,new Transform(new Vector2f(150,150),0,new Vector2f(10,10)));
		loadTiles();
		loadGameObjects();
		loadGuis();
		
		cautionTimer.start();
	}
	
	
	
	public void update()
	{
		systemTimer.update();
		player1RebirthTimer.update();
		player2RebirthTimer.update();
		updateDynamicGuiText();
		
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
		//rendertextMaster.loadText(scoreText);
		
		/*버튼로드 이벤트 매니저 생성해서 관리해야할것같다..*/
		EntityTexture downedButton=new EntityTexture(loader.loadTexture("images/redButton"));
		EntityTexture clickedButton=new EntityTexture(loader.loadTexture("images/orengeButton"));
		EntityTexture nonEventButton=new EntityTexture(loader.loadTexture("images/yellowButton"));
		EntityTexture mouseOnButton=new EntityTexture(loader.loadTexture("images/ball"));
		Transform guiTransform = new Transform(new Vector2f(150,150),30,new Vector2f(70,70));
		quitButton = new GuiButton(guiTransform, downedButton,mouseOnButton,clickedButton,nonEventButton);
		
		
	}
	
	public void updateDynamicGuiText() {
		
		//화면잘돌아가나 실험하는코드 무시..
		int floatTime=(int)systemTimer.getRemainGameTime();
		
		
		curTime=(int)floatTime;
		/*이전에 렌더링리스트와 gpu자원 없애기*/
		loaderForDynamicText.cleanUp();
		renderDynamicTextMaster.cleanUpList();
		
		/**/
		
		//시작 카운트다운 상태일 때.
		if(systemTimer.getCurrentCountDown()>0)
		{
			
			countdownText=new GUIText(systemTimer.getCurrentCountDown()+"", 5.0F, GlobalDataManager.defaultFontType , new Vector2f(0,0.3f), 1.0f, true);
			Random random=new Random();
			countdownText.setColour(new Vector3f(1.0f, 0.0f, 1.0f));
			renderDynamicTextMaster.loadText(countdownText);
		}
		else //게임시간 차감 상태일 때.
		{
			//render countdown
			//System.out.print(curTime);
			int minutes=floatTime/60;
			String strMinutes="0"+minutes;
			int seconds=floatTime%60;
			String strSec=(seconds<10)? "0"+seconds : seconds+"";
			
			countdownText=new GUIText(strMinutes+" : "+strSec, 5.0F, GlobalDataManager.defaultFontType , new Vector2f(0,0.3f), 1.0f, true);
			Random random=new Random();
			countdownText.setColour(new Vector3f(0.0f, 0.0f, 1.0f));
			renderDynamicTextMaster.loadText(countdownText);
		}
		
		scoreText.setText(ScoreSystem.getScore(1)+" : "+ScoreSystem.getScore(2));
		renderDynamicTextMaster.loadText(scoreText);
	}
	
	private void updateIngame()
	{
		GlobalDataManager.mousePicking.update();//현재좌표얻어오기
		
		player1.update();
		player2.update();
		ball.update();
		quitButton.update(GlobalDataManager.mousePicking);
		updateMissileItemTimeSystem();
		
		
		for(Missile missile : GlobalMissileManager.getMissileList())
		{
			missile.update();
		}
		
		Iterator<MissileItem> iter=GlobalMissileManager.getMissileItems().iterator();
		
		while(iter.hasNext())
		{
			MissileItem item=iter.next();
			item.update();
			
			if(item.isLifeOver())
			{
				iter.remove();
			}
		}
		
		for(Tile tile : tiles)
		{
			tile.update();
		}
		
		CollisionManager.ProcessCollision(player1, GlobalMissileManager.getMissileList(), deadSpaces, ball);
		CollisionManager.ProcessCollision(player2, GlobalMissileManager.getMissileList(), deadSpaces, ball);
		
		Iterator<MissileItem> itemIter=GlobalMissileManager.getMissileItems().iterator();
		
		//check collision with missileItem
		if(player1.getStatus()!=CharacterStatus.DEAD)
		while(itemIter.hasNext())
		{
			MissileItem item=itemIter.next();
			if(CollisionManager.CollisionDetected(player1, item))
			{
				itemIter.remove();
				player1.addMissileInQueue(item.getMissileType());
			}
		}
		
		itemIter=GlobalMissileManager.getMissileItems().iterator();
		//check collision with missileItem
		if(player2.getStatus()!=CharacterStatus.DEAD)
		while(itemIter.hasNext())
		{
			MissileItem item=itemIter.next();
			
			if(CollisionManager.CollisionDetected(player2, item))
			{
				itemIter.remove();
				player2.addMissileInQueue(item.getMissileType());
			}
		}
		
		//update deadSpaces
		for(DeadSpace space : deadSpaces)
		{
			space.update();
			
			//check collision with deadSpace
			if(player1.getStatus()!=CharacterStatus.DEAD)
			{
				if(CollisionManager.CollisionDetected(player1, space))
				{
					if(ball.getCatchingPlayerIdx()==1)
					{
						
					}
				}
			}
				
			if(player2.getStatus()!=CharacterStatus.DEAD)
			{
				
			}
		}
		
		
		processScore();
		
		
		processPlayerTimer();
		
		CollisionManager.checkReflection(ball);
		
	}
	
	public void cleanUp()
	{
		
	}
	
	public void updateMissileItemTimeSystem()
	{
		cautionTimer.update();
		missileItemGenTimer.update();
		
		if(cautionTimer.isEventOn())
		{
			generateCautionMarks();
			missileItemGenTimer.start();
			cautionTimer.stop();
			deadSpaces.clear();
			
			return;
		}
		
		if(missileItemGenTimer.isEventOn())
		{
			cautionTimer.start();
			missileItemGenTimer.stop();
			
			cautionMarks.clear();
			generateItems();
			generateDeadSpace();
			
		}
	}
	
	public void generateItems()
	{
		Random rand=new Random();
		for(int i=0; i<3; i++)
		{
			int idx=tileIdx[i];
			Vector2f position=new Vector2f(tiles[idx].getTransform().getPosition());
			MissileType type=null;
			
			//select type by random value
			int type_idx=rand.nextInt(3);
			
			switch(type_idx)
			{
			case 0:
				type=MissileType.SEPARATE;
				break;
			case 1:
				type=MissileType.SLOW;
				break;
			case 2:
				type=MissileType.STUN;
				break;
			}
			
			GlobalMissileManager.Instantiate(new MissileItem(position, type));
		}
		
	}
	
	public void generateDeadSpace()
	{
		for(int i=3; i<6; i++)
		{
			int idx=tileIdx[i];
			Vector2f position=new Vector2f(tiles[idx].getTransform().getPosition());
			
			deadSpaces.add(new DeadSpace(position));
		}
	}
	
	public void generateCautionMarks()
	{
		//처음 0~2번은 item tile, 다음 3~5번 타일은 함정 타일
		//tileIdx
		
		Random random=new Random();
		
		for(int i=0; i<6; i++)
		{
			
			boolean repetition=true;
			
			while(repetition)
			{
			int tmp=random.nextInt(15)+1;
			if(i==0)
			{
				repetition=false;
				tileIdx[i]=tmp;
			}
			
			for(int j=0; j<i; j++)
			{
				if(tmp==tileIdx[j])
				{
					repetition=true;
					break;
				}
				
				if(j==i-1)
				{
					repetition=false;
					tileIdx[i]=tmp;
				}
			}
			
			}
		}
		
		for(int i=0; i<6; i++)
		{
			cautionMarks.add(new CautionMark(new Vector2f(tiles[tileIdx[i]].getTransform().getPosition())));
		}
	}
	
	public void render()
	{
		processRendering();
		
		tileRenderer.render();
		entityRenderer.render();

		rendertextMaster.render();
		renderDynamicTextMaster.render();
		GUIRenderer.render();
		
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
		GUIRenderer.processInstancingEntity(quitButton);
		
		
		for(MissileItem item : GlobalMissileManager.getMissileItems())
		{
			entityRenderer.processInstancingEntity(item);
		}
		
		for(Missile missile : GlobalMissileManager.getMissileList())
		{
			entityRenderer.processInstancingEntity(missile);
		}
		
		for(CautionMark mark : cautionMarks)
		{
			entityRenderer.processInstancingEntity(mark);
		}
		
		for(DeadSpace deadSpace : deadSpaces)
		{
			entityRenderer.processInstancingEntity(deadSpace);
		}
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
						
						//스코어 텍스트를 업데이트 한다.
						scoreText.setText(ScoreSystem.getScore(1)+" : "+ScoreSystem.getScore(2));
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
