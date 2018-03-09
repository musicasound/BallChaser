package Entities;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import Physics.Transform;
import Textures.EntityTexture;

public abstract class Entity
{
	protected Transform transform;
	
	public Entity(Transform transform) {
		this.transform=transform;
	}
	//DataType에 Collider가있어서...
	public abstract Rectangle getCollider();
	//DataType에 EntityTexture 가있어서 구조를 바꾸기엔 다시해야하고.. 일단 이런식으로..
	public abstract EntityTexture getEntityTexture();
	
	public Transform getTransform() {
		return transform;
	}

}
