package Guis;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import Entities.Entity;
import IngameSystem.CollisionManager;
import Physics.Transform;
import Picking.MousePicking;
import Textures.EntityTexture;

enum GuiButtonState {
	MOUSEBUTTONDOWN, NONEVENT,MOUSEBUTTONUP,MOUSEON
}

public class GuiButton extends Entity{
	private EntityTexture tex_mouseButtonDown;
	private EntityTexture tex_mouseOn;
	private EntityTexture tex_mouseButtonUp;
	private EntityTexture tex_nonEvent;
	private EntityTexture current_texture;
	private Rectangle _CollisionRange;

	private GuiButtonState state=GuiButtonState.NONEVENT;
	
	
	public GuiButton(Transform transform, EntityTexture tex_mouseButtonDown, EntityTexture tex_mouseOn,
			EntityTexture tex_mouseButtonUp, EntityTexture tex_nonEvent) {
		super(transform);
		this.tex_mouseButtonDown = tex_mouseButtonDown;
		this.tex_mouseOn = tex_mouseOn;
		this.tex_mouseButtonUp = tex_mouseButtonUp;
		this.tex_nonEvent = tex_nonEvent;
		this.current_texture = tex_nonEvent;
		
		float posX=transform.getPosition().x-(transform.getScale().x/2);
		float posY=transform.getPosition().y-(transform.getScale().y/2);
		
		this._CollisionRange=new Rectangle((int)posX,(int)posY
				,(int)transform.getScale().x,(int)transform.getScale().y);
	}

	public void update(MousePicking mousePicking) {
		
		updatePicking(mousePicking);
	}
	
	public void updatePicking(MousePicking mousePicking) {
		Vector2f worldMousePos = mousePicking.getCurrentMousePos();
		System.out.println(_CollisionRange);
		if(_CollisionRange.contains((int)worldMousePos.x,(int)worldMousePos.y)) {
			updatePickingEvent();
		}
		
	}
	
	
	public void updatePickingEvent() {
		while(Mouse.next()) {
		    if (Mouse.getEventButton() > -1) {
		        if (Mouse.getEventButtonState()) {
		            System.out.println("PRESSED MOUSE BUTTON: " + Mouse.getEventButton());
		        }
		        else System.out.println("RELEASED MOUSE BUTTON: " + Mouse.getEventButton());
		    }
		}
	}
	
	public void mouseOn() {
		state=GuiButtonState.MOUSEON;
		current_texture=tex_mouseOn;
	}
	
	public void mouseButtonUp() {
		state=GuiButtonState.MOUSEBUTTONUP;
		current_texture=tex_mouseButtonUp;
	}
	
	public void mouseButtonDown() {
		state=GuiButtonState.MOUSEBUTTONDOWN;
		current_texture=tex_mouseButtonDown;
	}
	
	public void nonEvent() {
		state=GuiButtonState.NONEVENT;
		current_texture=tex_nonEvent;
	}
	

	@Override
	public Rectangle getCollider() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public EntityTexture getEntityTexture() {
		// TODO Auto-generated method stub
		return current_texture;
	}




}
