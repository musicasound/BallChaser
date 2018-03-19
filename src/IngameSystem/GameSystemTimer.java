package IngameSystem;

import Displays.DisplayManager;
import Scenes.ResultScene;
import Scenes.Scene;
import Scenes.SceneManager;

public class GameSystemTimer {
	public enum SysTimerStatus{COUNT_DOWN, IN_PLAY, GAME_OVER};
	
	public static final float TOTAL_GAMETIME=180.0f;
	
	private float currentTime=0.0f;
	private float countdown_remainTime=5.8f;
	private SysTimerStatus status;
	
	
	public GameSystemTimer() {
		status=SysTimerStatus.COUNT_DOWN;
	}
	
	public SysTimerStatus getStatus()
	{
		return status;
	}
	
	public void update()
	{
		switch(status)
		{
		case COUNT_DOWN:
			countdown_remainTime-=DisplayManager.fixedDeltaTime();
			
			if(countdown_remainTime<=0.0f)
			{
				status=SysTimerStatus.IN_PLAY;
			}
			
			break;
		case IN_PLAY:
			currentTime+=DisplayManager.fixedDeltaTime();
			
			if(getRemainGameTime()<=0.0f)
			{
				status=SysTimerStatus.GAME_OVER;
			}
			break;
		case GAME_OVER:
			currentTime=TOTAL_GAMETIME;
			break;
			
		}
	}
	
	public float getRemainGameTime()
	{
		return TOTAL_GAMETIME-currentTime;
	}
	
	public int getCurrentCountDown()
	{
		return (int)Math.floor(countdown_remainTime);
	}
}
