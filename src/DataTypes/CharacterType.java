package DataTypes;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

public class CharacterType extends DataType {

	public float _PushRadius;
	public float _PushCoolTime;
	public float _RotationSpeed;
	//Skill _Skill;
	
	public CharacterType(float _MaxVelocity, float _MaxAcceleration, float _DeltaAccel, Vector2f _ImageScale, Rectangle _CollisionRange,
			float _PushRadius, float _PushCoolTime, float _RotationSpeed) {
		super(_MaxVelocity, _MaxAcceleration, _DeltaAccel, _ImageScale, _CollisionRange);
		this._PushRadius = _PushRadius;
		this._PushCoolTime = _PushCoolTime;
		this._RotationSpeed=_RotationSpeed;
	}
	
	
}
