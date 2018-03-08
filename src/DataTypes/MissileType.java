package DataTypes;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

public class MissileType extends DataType{

	public int missileEventType;

	public MissileType(float _MaxVelocity, float _MaxAcceleration, float _DeltaAccel,Vector2f _ImageScale, Rectangle _CollisionRange,
			int missileEventType) {
		super(_MaxVelocity, _MaxAcceleration, _DeltaAccel, _ImageScale, _CollisionRange);
		this.missileEventType = missileEventType;
	}
	
	
}
