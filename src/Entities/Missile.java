package Entities;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import DataTypes.MissileType;
import Displays.DisplayManager;
import Physics.Transform;
import Textures.EntityTexture;


public class Missile extends Entity {
	
	//타입에 따른 처리는 인게임 시스템에서 한다.
	MissileType type;
	Transform transform;
	Vector2f velocityDirection;
	int targetPlayerIndex;
	Rectangle _CollisionRange;
	
	public Missile(MissileType type, Vector2f position, Vector2f moveDirection, int targetPlayerIdx 
			) {
		super(new Transform(position, 0.0f,type._ImageScale));
		this.type=type;
		this.velocityDirection=moveDirection;
		this.targetPlayerIndex=targetPlayerIdx;
		this._CollisionRange=new Rectangle(0,0, 25,25);
	}
	
	public void update()
	{
		transform.translate(type._MaxVelocity*velocityDirection.x*DisplayManager.fixedDeltaTime(),
										type._MaxVelocity*velocityDirection.y*DisplayManager.fixedDeltaTime());
		
	}

	public MissileType getType() {
		return type;
	}

	public void setType(MissileType type) {
		this.type = type;
	}

	

	public Vector2f getVelocityDirection() {
		return velocityDirection;
	}

	public void setVelocityDirection(Vector2f velocityDirection) {
		this.velocityDirection = velocityDirection;
	}

	public int getTargetPlayerIndex() {
		return targetPlayerIndex;
	}

	public void setTargetPlayerIndex(int targetPlayerIndex) {
		this.targetPlayerIndex = targetPlayerIndex;
	}

	


	@Override
	public EntityTexture getEntityTexture() {
		// TODO Auto-generated method stub
		return type._ImageTexture;
	}


	//벽면충돌 사망 처리 및 미사일 충돌처리 등에 필요
	public Rectangle getCollider()
	{
		Vector2f pos=transform.getPosition();
		
		return new Rectangle((int)pos.x, (int)pos.y, _CollisionRange.getWidth(), _CollisionRange.getHeight());
		
	}
	
	
}
