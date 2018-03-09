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
	//DataType�� Collider���־...
	public abstract Rectangle getCollider();
	//DataType�� EntityTexture ���־ ������ �ٲٱ⿣ �ٽ��ؾ��ϰ�.. �ϴ� �̷�������..
	public abstract EntityTexture getEntityTexture();
	
	public Transform getTransform() {
		return transform;
	}

}
