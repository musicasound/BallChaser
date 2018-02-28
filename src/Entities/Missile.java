package Entities;

import org.lwjgl.util.vector.Vector2f;

import DataTypes.MissileType;
import Displays.DisplayManager;
import Physics.Transform;


public class Missile {
	
	//타입에 따른 처리는 인게임 시스템에서 한다.
	MissileType type;
	Transform transform;
	Vector2f velocityDirection;
	int targetPlayerIndex;
	
	public Missile(MissileType type, Vector2f position, Vector2f moveDirection, int targetPlayerIdx 
			) {
		
		this.type=type;
		this.transform=new Transform(position, 0);
		this.velocityDirection=moveDirection;
		this.targetPlayerIndex=targetPlayerIdx;
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

	public Transform getTransform() {
		return transform;
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
	
	
}
