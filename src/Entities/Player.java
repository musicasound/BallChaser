package Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import DataTypes.CharacterType;
import DataTypes.MissileType;
import Displays.DisplayManager;
import IngameSystem.EntityTimer;
import IngameSystem.GlobalDataManager;
import IngameSystem.GlobalMissileManager;
import KeySystem.KeyboardManager;
import Physics.Transform;
import Textures.EntityTexture;
import KeySystem.CharacterKeySetting;


public class Player extends Entity {
	float velocityScale;
	Vector2f velocityDirection;
	CharacterStatus status;
	float stun_remainTime;
	float pushCool_remainTime;
	ArrayList<MissileType> missileItemQueue;
	CharacterType type;
	//1P인지 2P인지 구분
	int playerIndex;
	
	float shootMissileAngle;
	float slow_remainTime;
	boolean isSlow;
	
	Rectangle _CollisionRange;
	
	EntityTimer slowTimer;
	EntityTimer missileShotTimer;
	
	
	HashMap<KeySystem.CharacterKeySetting, Integer> keySettings;//=new HashMap<KeySystem.CharacterKeySetting, Integer>();
	
	
	public Player(CharacterType type, int playerIndex,Vector2f position) {
		super(position, type._ImageScale);
		this.playerIndex = playerIndex;
		this.type=type;
		this._CollisionRange=new Rectangle(0, 0, 40, 40);//캐릭터에 CollisionRange있음 type.collision...으로변경해야할것같음 -예찬
		this.velocityScale=0.0f;
		this.velocityDirection=new Vector2f(0,1);
		keySettings=GlobalDataManager.getKeySettings(playerIndex);
		this.status=CharacterStatus.LIVE;
		this.missileItemQueue=new ArrayList<MissileType>();
		
		slowTimer=new EntityTimer(4.0f);
		slowTimer.stop();
		
		missileShotTimer=new EntityTimer(1.0f);
		missileShotTimer.start();
	}
	
	public CharacterType getType()
	{
		return type;
	}
	
	public Transform getTransform() {
		return transform;
	}
	public void setTransform(Transform transform) {
		this.transform = transform;
	}
	public Vector2f getVelocity() {
		Vector2f ret=new Vector2f(velocityDirection);
		
		ret.scale(velocityScale);
		return ret;
	}
	public CharacterStatus getStatus() {
		return status;
	}
	public void setStatus(CharacterStatus status) {
		this.status = status;
	}
	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public float getShootMissileAngle() {
		return shootMissileAngle;
	}
	public void setShootMissileAngle(float shootMissileAngle) {
		this.shootMissileAngle = shootMissileAngle;
	}
	public boolean isSlow() {
		return isSlow;
	}
	public void setSlow(boolean isSlow) {
		this.isSlow = isSlow;
	}

	
	public void addMissileInQueue(MissileType missile)
	{
		if(missileItemQueue.size()>=3)
		{
			missileItemQueue.remove(2);
		}
		
		missileItemQueue.add(missile);
	}
	
	public MissileType popMissile()
	{
		if(missileItemQueue.isEmpty()) return null;
		
		MissileType ret=missileItemQueue.get(0);
		missileItemQueue.remove(0);
		
		return ret;
	}
	
	//방향키에 따라서 회전함. 외부에서 처리
	public void rotate(RotationDirection rotDirection)
	{
		switch(rotDirection)
		{
		case CCW:
			transform.rotate(type._RotationSpeed*DisplayManager.fixedDeltaTime());
			break;
		case CW:
			transform.rotate(-type._RotationSpeed*DisplayManager.fixedDeltaTime());
			break;
		}
		float angle=(float)Math.toRadians(transform.getRotationAngle());
		velocityDirection=new Vector2f((float)Math.sin(-angle),(float) Math.cos(-angle));
		
	}
	
	public void update()
	{
		missileShotTimer.update();
		
		//
		if(status==CharacterStatus.DEAD)
			return;
		
		if(status==CharacterStatus.STUN)
		{
			stun_remainTime-=DisplayManager.fixedDeltaTime();
			
			if(stun_remainTime<=0.0f)
			{
				status=CharacterStatus.LIVE;
			}
			
			velocityScale-=type._StopAccel*DisplayManager.fixedDeltaTime();
			
			if(velocityScale<=0.0f)
			{
				velocityScale=0.0f;
			}
		}
		else if(status==CharacterStatus.SLOW)
		{
			slowTimer.update();
			
			Move();
			
			if(slowTimer.isEventOn())
			{
				status=CharacterStatus.LIVE;
				slowTimer.stop();
			}
		}
		else
		{
			if(KeyboardManager.isKeyPressed(keySettings.get((CharacterKeySetting.FORWARD))))
			{
			velocityScale+=type._MaxAcceleration*DisplayManager.fixedDeltaTime();
			
				if(velocityScale>type._MaxVelocity)
				{
					velocityScale=type._MaxVelocity;
				}
			}
			else // key forward가 눌리지 않음
			{		
				velocityScale-=type._StopAccel*DisplayManager.fixedDeltaTime();
			
				if(velocityScale<=0.0f)
				{
					velocityScale=0.0f;
				}
			}
			
			if(KeyboardManager.isKeyPressed(keySettings.get(CharacterKeySetting.CCW_ROT)))
			{
				rotate(RotationDirection.CCW);
			}
			else if(KeyboardManager.isKeyPressed(keySettings.get(CharacterKeySetting.CW_ROT)))
			{
				rotate(RotationDirection.CW);
			}
			
			if(KeyboardManager.isKeyPressed(keySettings.get(CharacterKeySetting.MISSILE_SHOT)))
			{
				if(missileShotTimer.isEventOn())
				{
					//instantiate missile
					System.out.println("instantiate missile");
					
					if(!missileItemQueue.isEmpty())
					{
						MissileType type=missileItemQueue.get(0);
						missileItemQueue.remove(0);
						
						float angle=(float)Math.toRadians(transform.getRotationAngle());
						Vector2f missileDirection=new Vector2f((float)Math.sin(-angle),(float) Math.cos(-angle));
						int targetPlayerIdx= (playerIndex==1? 2 : 1);
						
						GlobalMissileManager.Instantiate(new Missile(type, new Vector2f(transform.getPosition().x, transform.getPosition().y), missileDirection, targetPlayerIdx));
					}
					
					//set timer
					missileShotTimer.start();
				}
			}
		}
		
		Move();
	}
	
	
	//벽면충돌 사망 처리 및 미사일 충돌처리 등에 필요
	public Rectangle getCollider()
	{
		Vector2f pos=transform.getPosition();
		
		return new Rectangle((int)pos.x-_CollisionRange.getWidth()/2, (int)pos.y+_CollisionRange.getHeight()/2, _CollisionRange.getWidth(), _CollisionRange.getHeight());
		
	}
	
	private void initTimers()
	{
		slowTimer.stop();
	}
	
	public void Die()
	{
		initTimers();
		status=CharacterStatus.DEAD;
		this.velocityScale=0.0f;
		this.velocityDirection=new Vector2f(0,1);
		transform.setRotationAngle(0.0f);
		
	}
	
	private void Move()
	{
		Vector2f pos=new Vector2f(transform.getPosition());
		Vector2f delta=new Vector2f(velocityDirection);
		delta.scale(velocityScale*DisplayManager.fixedDeltaTime());
		
		Vector2f.add(pos, delta, pos);
		transform.setPosition(pos);
	}
	
	public void Stun()
	{
		initTimers();
		status=CharacterStatus.STUN;
		stun_remainTime=GlobalDataManager.STUN_TOTAL_TIME;
	}
	
	public void Slow()
	{
		initTimers();
		velocityScale=10.0f;
		status=CharacterStatus.SLOW;
		slowTimer.start();
	}

	@Override
	public EntityTexture getEntityTexture() {
		// TODO Auto-generated method stub
		return type._ImageTexture;
	}
	
	public void Separate(Missile missile)
	{
		float angle=(float)Math.atan(missile.getVelocityDirection().y/missile.getVelocityDirection().x);
		angle+=Math.toRadians(-45.0f);
		
		transform.setRotationAngle((float)Math.toDegrees(angle));
		velocityScale=120.0f;
	}
	
}
