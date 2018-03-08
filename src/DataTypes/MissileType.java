package DataTypes;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import Textures.EntityTexture;

public class MissileType extends DataType{

	public int missileEventType;

	public MissileType(float _MaxVelocity, float _MaxAcceleration, float _DeltaAccel,Vector2f _ImageScale, Rectangle _CollisionRange,
			EntityTexture _ImageTexture,int missileEventType) {
		super(_MaxVelocity, _MaxAcceleration, _DeltaAccel, _ImageScale,_CollisionRange,_ImageTexture);
		this.missileEventType = missileEventType;
	}
	
	
}
