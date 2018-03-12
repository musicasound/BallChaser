package Entities;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import Physics.Transform;
import Textures.EntityTexture;

public class Tile extends Entity{
	
	Rectangle _CollisionRange;
	EntityTexture tileTexture;
	

	public Tile(Vector2f position) {
		super(new Transform(position, 0.0f,new Vector2f(1.0f,1.0f)));
		this._CollisionRange = new Rectangle(0,0, 70, 70);
	}

	@Override
	public Rectangle getCollider() {
		// TODO Auto-generated method stub
		return _CollisionRange;
	}

	@Override
	public EntityTexture getEntityTexture() {
		// TODO Auto-generated method stub
		return tileTexture;
	}
	
	public void update()
	{}
}
