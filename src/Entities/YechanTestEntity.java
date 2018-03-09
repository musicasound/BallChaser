package Entities;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import Physics.Transform;
import RenderEngine.Loader;
import Textures.EntityTexture;

public class YechanTestEntity extends Entity{

	private EntityTexture textureModel;
	
	public YechanTestEntity(EntityTexture textureModel,Transform transform){
		super(transform);
		this.textureModel = textureModel;
	
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

	

}
