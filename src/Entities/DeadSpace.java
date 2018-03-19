package Entities;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import IngameSystem.EntityTimer;
import IngameSystem.GlobalDataManager;
import Textures.EntityTexture;

public class DeadSpace extends Entity {

	Rectangle _CollisionRange=new Rectangle(0,0, 120, 120);
	EntityTimer statusChangeTimer;
	
	boolean inDeadStatus=false;
	
	@Override
	public Rectangle getCollider()
	{
		Vector2f pos=transform.getPosition();
		
		return new Rectangle((int)pos.x-_CollisionRange.getWidth()/2, (int)pos.y+_CollisionRange.getHeight()/2, _CollisionRange.getWidth(), _CollisionRange.getHeight());
		
	}

	@Override
	public EntityTexture getEntityTexture() {
		// TODO Auto-generated method stub
		if(inDeadStatus) return GlobalDataManager.deadSpaceTexture;
		else return GlobalDataManager.deadSpaceNoDeadTexture;
	}
	
	public void update()
	{
		statusChangeTimer.update();
		
		if(statusChangeTimer.isEventOn())
		{
			inDeadStatus=!inDeadStatus;
			statusChangeTimer.start();
		}
	}

	public DeadSpace(Vector2f position) {
		super(position, new Vector2f(120.0f, 120.0f));
		// TODO Auto-generated constructor stub
		statusChangeTimer=new EntityTimer(2.0f);
		statusChangeTimer.start();
	}
	
	public boolean isDeadStatus()
	{
		return inDeadStatus;
	}

}
