package Entities;

import java.util.HashMap;
import java.util.Queue;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import DataTypes.CharacterType;
import DataTypes.MissileType;
import Displays.DisplayManager;
import IngameSystem.GlobalDataManager;
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
	Queue<MissileType> missileItemQueue;
	CharacterType type;
	//1P인지 2P인지 구분
	int playerIndex;
	
	float shootMissileAngle;
	float slow_remainTime;
	boolean isSlow;
	
	Rectangle _CollisionRange;
	
	HashMap<KeySystem.CharacterKeySetting, Integer> keySettings;//=new HashMap<KeySystem.CharacterKeySetting, Integer>();
	
	
	public Player(CharacterType type, int playerIndex,Vector2f position) {
		super(position, type._ImageScale);
		this.playerIndex = playerIndex;
		this.type=type;
		this._CollisionRange=new Rectangle(0, 0, 50, 50);//캐릭터에 CollisionRange있음 type.collision...으로변경해야할것같음 -예찬
		this.velocityScale=0.0f;
		this.velocityDirection=new Vector2f(0,1);
		keySettings=GlobalDataManager.getKeySettings(playerIndex);
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

	
	public void addMissile(MissileType missile)
	{
		missileItemQueue.add(missile);
	}
	
	public MissileType popMissile()
	{
		return missileItemQueue.poll();
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
		}
		
		Vector2f pos=new Vector2f(transform.getPosition());
		Vector2f delta=new Vector2f(velocityDirection);
		delta.scale(velocityScale*DisplayManager.fixedDeltaTime());
		
		Vector2f.add(pos, delta, pos);
		transform.setPosition(pos);
	}
	
	
	//벽면충돌 사망 처리 및 미사일 충돌처리 등에 필요
	public Rectangle getCollider()
	{
		Vector2f pos=transform.getPosition();
		
		return new Rectangle((int)pos.x-_CollisionRange.getWidth()/2, (int)pos.y+_CollisionRange.getHeight()/2, _CollisionRange.getWidth(), _CollisionRange.getHeight());
		
	}
	
	public void Die()
	{
		status=CharacterStatus.DEAD;
		this.velocityScale=0.0f;
		this.velocityDirection=new Vector2f(0,1);
		transform.setRotationAngle(0.0f);
	}

	@Override
	public EntityTexture getEntityTexture() {
		// TODO Auto-generated method stub
		return type._ImageTexture;
	}
	
	
}
