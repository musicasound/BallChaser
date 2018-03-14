package Entities;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import IngameSystem.GlobalDataManager;
import Physics.Transform;
import Textures.EntityTexture;

public class Tile extends Entity{
	
	Rectangle _CollisionRange;
	
	boolean marked;
	int id;
	int counter=0;

	public Tile(Vector2f position, int id) {
		super(new Transform(position, 0.0f,new Vector2f(GlobalDataManager.TILE_SCALE,GlobalDataManager.TILE_SCALE)));
		this._CollisionRange = new Rectangle(0,0, (int)GlobalDataManager.TILE_SCALE, (int)GlobalDataManager.TILE_SCALE);
		this.id=id;
		marked=false;
	}
	
	public void setMarked(boolean mark)
	{
		marked=mark;
	}

	@Override
	public Rectangle getCollider() {
		// TODO Auto-generated method stub
		Vector2f pos=transform.getPosition();
		
		return new Rectangle((int)pos.x-_CollisionRange.getWidth()/2, (int)pos.y+_CollisionRange.getHeight()/2, _CollisionRange.getWidth(), _CollisionRange.getHeight());
	}

	@Override
	public EntityTexture getEntityTexture() {
		// TODO Auto-generated method stub
		if(marked) return GlobalDataManager.mark_tileTexture;
		else return GlobalDataManager.nonmark_tileTexture;
	}
	
	public void update()
	{}
}
