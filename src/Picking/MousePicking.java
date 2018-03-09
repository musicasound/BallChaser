package Picking;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import Displays.DisplayManager;

public class MousePicking {
	private Rectangle coordinate2D=new Rectangle(-300,-300,600,600);//2d게임좌표계 쉐이더에는 하드코딩되있음..
	
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
		//width * Height 직사각형을 -> (0,0) ~ (1,1)정사각형으로 normalize
		float x = (mouseX) /Display.getWidth() ;//0 ~ 1
		float y = (mouseY) / Display.getHeight() ;//0 ~ 1
		return new Vector2f(x,y); 
		//ps. 창은 왼쪽위가 0,0 이기때문에 다른곳에서 계산하고싶다면 Vector2f(x,-y);로 맞춰주기
		//지금은 lightweight java game library 를쓰기때문에 괜찮을것
	}
	
	private Vector2f toWorldCoords(Vector2f openglCoords2D) {
		int sizeX=coordinate2D.getWidth();
		int sizeY=coordinate2D.getHeight();
		float worldX=(openglCoords2D.x*sizeX)+coordinate2D.getX();
		float worldY=(openglCoords2D.y*sizeY)+coordinate2D.getY();
		return new Vector2f(worldX,worldY);
	}
	
}
