package DataTypes;

import org.lwjgl.util.vector.Vector2f;

public class MissileType extends DataType{

	public int missileEventType;

	public MissileType(float _MaxVelocity, float _Acceleration, Vector2f _ImageScale, Vector2f _CollisionRange,
			int missileEventType) {
		super(_MaxVelocity, _Acceleration, _ImageScale, _CollisionRange);
		this.missileEventType = missileEventType;
	}
	
	
}
