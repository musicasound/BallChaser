package Entities;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import DataTypes.MissileType;
import IngameSystem.EntityTimer;
import IngameSystem.GlobalDataManager;
import Textures.EntityTexture;

public class MissileItem extends Entity {

	EntityTimer lifeTimer;
	Rectangle _CollisionRange=new Rectangle(0, 0, 60, 60);
	MissileType missileType;
	
	public MissileItem(Vector2f position, MissileType type) {
		super(position, new Vector2f(60.0f,60.0f));
		// TODO Auto-generated constructor stub
		lifeTimer=new EntityTimer(3.0f);
		lifeTimer.start();
		this.missileType=type;
	}
	
	@Override
	public Rectangle getCollider()
	{
		Vector2f pos=transform.getPosition();
		
		return new Rectangle((int)pos.x-_CollisionRange.getWidth()/2, (int)pos.y+_CollisionRange.getHeight()/2, _CollisionRange.getWidth(), _CollisionRange.getHeight());
	}

	@Override
	public EntityTexture getEntityTexture() {
		// TODO Auto-generated method stub
		return GlobalDataManager.missileItemTexture;
	}
	
	public void update()
	{
		lifeTimer.update();
	}
	
	public boolean isLifeOver()
	{
		return lifeTimer.isEventOn();
	}
	
	public MissileType getMissileType()
	{
		return missileType;
	}

}
