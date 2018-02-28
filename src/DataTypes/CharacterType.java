package DataTypes;

import org.lwjgl.util.vector.Vector2f;

public class CharacterType extends DataType {

	public float _PushRadius;
	public float _PushCoolTime;
	//Skill _Skill;
	
	public CharacterType(float _MaxVelocity, float _Acceleration, Vector2f _ImageScale, Vector2f _CollisionRange,
			float _PushRadius, float _PushCoolTime) {
		super(_MaxVelocity, _Acceleration, _ImageScale, _CollisionRange);
		this._PushRadius = _PushRadius;
		this._PushCoolTime = _PushCoolTime;
	}
	
	
}
