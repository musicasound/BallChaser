package Picking;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import Displays.DisplayManager;

public class MousePicking {
	private Rectangle coordinate2D=new Rectangle(-300,-300,600,600);//2d������ǥ�� ���̴����� �ϵ��ڵ�������..
	
	private Vector2f currentMousePos=new Vector2f(0,0);
	
	public MousePicking() {}
	
	
	public void update() {
		calculateMousePosition();
	}
	public Vector2f getCurrentMousePos() {
		return currentMousePos;
	}
	
	private void calculateMousePosition() {
		float mouseX=Mouse.getX();
		float mouseY=Mouse.getY();
		Vector2f openglCoords2D=getNormalizedDeviceCoords(mouseX, mouseY);
		currentMousePos=toWorldCoords(openglCoords2D);
	}
	
	private Vector2f getNormalizedDeviceCoords(float mouseX,float mouseY) {
		//width * Height ���簢���� -> (0,0) ~ (1,1)���簢������ normalize
		float x = (mouseX) /Display.getWidth() ;//0 ~ 1
		float y = (mouseY) / Display.getHeight() ;//0 ~ 1
		return new Vector2f(x,y); 
		//ps. â�� �������� 0,0 �̱⶧���� �ٸ������� ����ϰ�ʹٸ� Vector2f(x,-y);�� �����ֱ�
		//������ lightweight java game library �����⶧���� ��������
	}
	
	private Vector2f toWorldCoords(Vector2f openglCoords2D) {
		int sizeX=coordinate2D.getWidth();
		int sizeY=coordinate2D.getHeight();
		float worldX=(openglCoords2D.x*sizeX)+coordinate2D.getX();
		float worldY=(openglCoords2D.y*sizeY)+coordinate2D.getY();
		return new Vector2f(worldX,worldY);
	}
	
}
