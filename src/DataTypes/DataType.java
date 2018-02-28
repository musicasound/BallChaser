package DataTypes;

import org.lwjgl.util.vector.Vector2f;

public abstract class DataType {
	public float _MaxVelocity;
	public float _Acceleration;
	public Vector2f _ImageScale;
	public Vector2f _CollisionRange;
	//TextureData _ImageTexture;
	
	public DataType(float _MaxVelocity, float _Acceleration, Vector2f _ImageScale, Vector2f _CollisionRange) {
		super();
		this._MaxVelocity = _MaxVelocity;
		this._Acceleration = _Acceleration;
		this._ImageScale = _ImageScale;
		this._CollisionRange = _CollisionRange;
	}
	
}