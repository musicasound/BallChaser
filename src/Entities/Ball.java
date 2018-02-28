package Entities;

import org.lwjgl.util.vector.Vector2f;

import Displays.DisplayManager;
import Physics.Transform;

public class Ball {
	Transform transform;
	
	float velocityScale;
	Vector2f velocityDirection;
	
	float _Acceleration;
	
	boolean isCatched=false;
	int catchingPlayerIdx=-1;
	
	Vector2f _CollisionRange;
	
	public Ball(Vector2f position)
	{
		this.transform=new Transform(position, 0.0f);
		this.velocityScale=0.0f;
		this.velocityDirection=new Vector2f(0.0f, 0.0f);
		this._Acceleration=6.0f;
	}
	
	public void update()
	{
		//accelerate
		
		if(!isCatched)
		{
		velocityScale-=_Acceleration*DisplayManager.fixedDeltaTime();
		
			if(velocityScale<=0.0f)
				velocityScale=0.0f;
			
		
		
		transform.translate(velocityScale*velocityDirection.x*DisplayManager.fixedDeltaTime(),
										velocityScale*velocityDirection.y*DisplayManager.fixedDeltaTime());
		
		//�ݻ��Ģ�� ����Ѵ�.
		
		}
		else//else if ball is catched
		{
			//��ġ�� ���� ���� ���� �ִ� �÷��̾��� ��ġ�� ���� �Ѵ�.
		}
		
		
	}

	public Transform getTransform() {
		return transform;
	}

	public float getVelocityScale() {
		return velocityScale;
	}

	public void setVelocityScale(float velocityScale) {
		this.velocityScale = velocityScale;
	}

	public Vector2f getVelocityDirection() {
		return velocityDirection;
	}

	public void setVelocityDirection(Vector2f velocityDirection) {
		this.velocityDirection = velocityDirection;
	}

	public float getAcceleration() {
		return _Acceleration;
	}

	public void setAcceleration(float _Acceleration) {
		this._Acceleration = _Acceleration;
	}

	public boolean isCatched() {
		return isCatched;
	}

	public void setCatched(boolean isCatched) {
		this.isCatched = isCatched;
	}

	public int getCatchingPlayerIdx() {
		return catchingPlayerIdx;
	}

	public void setCatchingPlayerIdx(int catchingPlayerIdx) {
		this.catchingPlayerIdx = catchingPlayerIdx;
	}

	public Vector2f getCollisionRange() {
		return _CollisionRange;
	}
}
