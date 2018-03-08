package Entities;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import Physics.Transform;
import RenderEngine.Loader;
import Textures.EntityTexture;

public class YechanTestEntity implements Entity{

	private EntityTexture textureModel;
	private Transform transform;//scale내용이없음..
	private Vector2f scale2D;
	
	public YechanTestEntity(EntityTexture textureModel,Transform transform,Vector2f scale){
		this.textureModel = textureModel;
		this.transform=transform;
		this.scale2D=scale;
	}
	
	@Override
	public Rectangle getCollider() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public EntityTexture getEntityTexture() {
		// TODO Auto-generated method stub
		return textureModel;
	}

	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return transform;
	}

	@Override
	public Vector2f getScale() {
		// TODO Auto-generated method stub
		return scale2D;
	}

}
