package DataTypes;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import Textures.EntityTexture;

public abstract class DataType {
	public float _MaxVelocity;
	public float _MaxAcceleration;
	public float _StopAccel;
	public Vector2f _ImageScale;
	public Rectangle _CollisionRange;
	public EntityTexture _ImageTexture;
	
	public DataType(float _MaxVelocity, float _MaxAcceleration, float _DeltaAcceleration, Vector2f _ImageScale, Rectangle _CollisionRange,EntityTexture _ImageTexture) {
		super();
		this._MaxVelocity = _MaxVelocity;
		this._MaxAcceleration = _MaxAcceleration;
		this._StopAccel=_DeltaAcceleration;
		this._ImageScale = _ImageScale;
		this._CollisionRange = _CollisionRange;
		this._ImageTexture=_ImageTexture;
	}
	
}
