package Entities;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import Main.GlobalDataManager;
import Physics.Transform;
import Textures.EntityTexture;

public class Tile extends Entity{
	
	Rectangle _CollisionRange;
	
	boolean marked=false;
	

	public Tile(Vector2f position) {
		super(new Transform(position, 0.0f,new Vector2f(GlobalDataManager.TILE_SCALE,GlobalDataManager.TILE_SCALE)));
		this._CollisionRange = new Rectangle(0,0, (int)GlobalDataManager.TILE_SCALE, (int)GlobalDataManager.TILE_SCALE);
	}
	
	public void setMarked(boolean mark)
	{
		marked=mark;
	}

	@Override
	public Rectangle getCollider() {
		// TODO Auto-generated method stub
		return _CollisionRange;
	}

	@Override
	public EntityTexture getEntityTexture() {
		// TODO Auto-generated method stub
		if(marked)
		{
			return GlobalDataManager.mark_tileTexture;
		}
		else
		{
			return GlobalDataManager.nonmark_tileTexture;
		}
	}
	
	public void update()
	{}
}
