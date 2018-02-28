package Physics;

import org.lwjgl.util.vector.Vector2f;

public class Transform {

	Vector2f position;
	float rotationAngle;
	
	
	
	public Transform(Vector2f position, float rotationAngle) {
		super();
		this.position = position;
		this.rotationAngle = rotationAngle;
	}
	
	
	public Vector2f getPosition() {
		return position;
	}
	public void setPosition(Vector2f position) {
		this.position = position;
	}
	public float getRotationAngle() {
		return rotationAngle;
	}
	public void setRotationAngle(float rotationAngle) {
		this.rotationAngle = rotationAngle;
	}
	
	public void translate(float dx, float dy)
	{
		position.x+=dx;
		position.y+=dy;
	}
	
	public void rotate(float angle)
	{
		rotationAngle+=angle;
	}
	
}
