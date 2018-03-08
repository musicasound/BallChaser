package Entities;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import Physics.Transform;
import Textures.EntityTexture;

public interface Entity
{
	public Rectangle getCollider();
	public EntityTexture getEntityTexture();
	public Transform getTransform();
	public Vector2f getScale();
}
