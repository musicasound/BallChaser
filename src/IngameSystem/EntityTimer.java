package IngameSystem;

import Displays.DisplayManager;

public class EntityTimer {
	boolean eventOn=false;
	boolean isUpdate=false;
	
	float targetTime;
	float currentTime=0.0f;
	
	public EntityTimer(float targetTime)
	{
		this.targetTime=targetTime;
	}
	
	public void start()
	{
		eventOn=false;
		currentTime=0.0f;
		isUpdate=true;
	}
	
	public void update()
	{
		if(isUpdate)
		{
			currentTime+=DisplayManager.fixedDeltaTime();
			
			if(currentTime>=targetTime)
			{
					eventOn=true;
			}
		
		}
		
	}
	
	public void stop()
	{
		eventOn=false;
		isUpdate=false;
		currentTime=0.0f;
	}
	
	public boolean isUpdating()
	{
		return isUpdate;
	}
	
	public void pause()
	{
		isUpdate=false;
	}
	
	public void resume()
	{
		isUpdate=true;
	}
	
	public boolean isEventOn()
	{
		return eventOn;
	}
}
