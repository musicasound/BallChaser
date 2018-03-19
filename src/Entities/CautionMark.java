package Entities;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import IngameSystem.EntityTimer;
import IngameSystem.GlobalDataManager;
import Textures.EntityTexture;

public class CautionMark extends Entity {

	EntityTimer lifeTimer;
	Rectangle _CollisionRange=new Rectangle(0,0, 120, 120);
	
	public CautionMark(Vector2f position)
	{
		super(position, new Vector2f(120.0f, 120.0f));
		lifeTimer=new EntityTimer(GlobalDataManager.CAUTION_MARK_LIFETIME);
		lifeTimer.start();
	}
	
	public Rectangle getCollider()
	{
		Vector2f pos=transform.getPosition();
		
		return new Rectangle((int)pos.x-_CollisionRange.getWidth()/2, (int)pos.y+_CollisionRange.getHeight()/2, _CollisionRange.getWidth(), _CollisionRange.getHeight());
		
	}

	@Override
	public EntityTexture getEntityTexture() {
		// TODO Auto-generated method stub
		return GlobalDataManager.cautionMarkTexture;
	}

	public void update()
	{
		lifeTimer.update();
	}
	
	public boolean isLifeOver()
	{
		return lifeTimer.isEventOn();
	}
}
