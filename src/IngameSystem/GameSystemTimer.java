package IngameSystem;

import Displays.DisplayManager;

public class GameSystemTimer {
	public enum TimerStatus{COUNT_DOWN, IN_PLAY, GAME_OVER};
	
	public static final float TOTAL_GAMETIME=180.0f;
	
	private float currentTime=0.0f;
	private float countdown_remainTime=5.0f;
	private TimerStatus status;
	
	
	public GameSystemTimer() {
		status=TimerStatus.COUNT_DOWN;
	}
	
	public void update()
	{
		switch(status)
		{
		case COUNT_DOWN:
			countdown_remainTime-=DisplayManager.fixedDeltaTime();
			
			if(countdown_remainTime<=0.0f)
			{
				status=TimerStatus.IN_PLAY;
			}
			
			break;
		case IN_PLAY:
			currentTime+=DisplayManager.fixedDeltaTime();
			
			if(getRemainGameTime()<=0.0f)
			{
				status=TimerStatus.GAME_OVER;
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
		return (int)Math.ceil(countdown_remainTime);
	}
}
