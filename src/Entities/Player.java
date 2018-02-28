package Entities;

import java.util.Queue;

import org.lwjgl.util.vector.Vector2f;

import DataTypes.CharacterType;
import DataTypes.MissileType;
import Physics.Transform;

public class Player {
	enum CharacterStatus{DEAD, LIVE, STUN, BALL_CATCHED};
	Transform transform;
	Vector2f velocity;
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
	public Player(CharacterType type, int playerIndex) {
		super();
		this.playerIndex = playerIndex;
		this.type=type;
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
		return velocity;
	}
	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
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
	public Queue<MissileType> getMissileItemQueue() {
		return missileItemQueue;
	}
	
	public void update()
	{
		//
		
	}
	
	
}
