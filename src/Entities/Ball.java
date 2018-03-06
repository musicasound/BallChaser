package Entities;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import Displays.DisplayManager;
import Physics.Transform;

public class Ball implements Entity{
	Transform transform;
	
	float velocityScale;
	Vector2f velocityDirection;
	
	float _Acceleration;
	
	boolean isCatched=false;
	int catchingPlayerIdx=-1;
	
	public Rectangle _CollisionRange;
	
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
		
			//반사법칙을 계산한다.
		
		}
		else//else if ball is catched
		{
			//위치를 현재 공을 갖고 있는 플레이어의 위치와 같게 한다.
			
		}
		
		
	}
	
	public void Reflect(Vector2f normal)
	{
		Vector2f vel_dir_neg=new Vector2f();
		velocityDirection.negate(vel_dir_neg);
		
		float VDN_dot_normal=Vector2f.dot(vel_dir_neg, normal);
		
		Vector2f k=new Vector2f(normal);
		k.scale(VDN_dot_normal);
		
		Vector2f ret=new Vector2f(k);
		ret.scale(2.0f);
		Vector2f.add(ret, velocityDirection, ret);
		
		velocityDirection=ret;
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

	public Rectangle getCollider() {
		Vector2f pos=transform.getPosition();
		
		return new Rectangle((int)pos.x, (int)pos.y, _CollisionRange.getWidth(), _CollisionRange.getHeight());
	}
}
