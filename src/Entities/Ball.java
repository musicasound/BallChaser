package Entities;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import Displays.DisplayManager;
import Physics.Transform;
import Textures.EntityTexture;

public class Ball extends Entity{
	
	float velocityScale;
	Vector2f velocityDirection;
	
	float _Acceleration;
	
	boolean isCatched=false;
	int catchingPlayerIdx=-1;
	
	
	public Ball(Vector2f position)
	{
		super(new Transform(position, 0.0f,new Vector2f(1,1)));//scale 1*1
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
	
	
	//���� �ݻ�ó���� CollisionManager���� Reflect �Լ��� ȣ���Ͽ� ó���Ѵ�.
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

	/*@@@@@@@@@@@@@@@	�����ʿ�	@@@@@@@@@@@@@@@@@@*/
	@Override
	public EntityTexture getEntityTexture() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle getCollider() {
		// TODO Auto-generated method stub
		return null;
	}

	




}
