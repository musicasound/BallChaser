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
	MOUSEBUTTONDOWN, NONEVENT,CLICKED,MOUSEON
}

public class GuiButton extends Entity{
	private EntityTexture tex_mouseButtonDown;
	private EntityTexture tex_mouseOn;
	private EntityTexture tex_clickFinish;
	private EntityTexture tex_nonEvent;
	private EntityTexture current_texture;
	private Rectangle _CollisionRange;

	private GuiButtonState state=GuiButtonState.NONEVENT;
	
	public boolean isClicked=false;
	
	public GuiButton(Transform transform, EntityTexture tex_mouseButtonDown, EntityTexture tex_mouseOn,
			EntityTexture tex_clickFinish, EntityTexture tex_nonEvent) {
		super(transform);
		this.tex_mouseButtonDown = tex_mouseButtonDown;
		this.tex_mouseOn = tex_mouseOn;
		this.tex_clickFinish = tex_clickFinish;
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
		System.out.println(worldMousePos);
		if(_CollisionRange.contains((int)worldMousePos.x,(int)worldMousePos.y)) {
			updatePickingEvent();
		}
		else {
			while(Mouse.next()) {}
			nonEvent();
		}
	}
	
	
	public void updatePickingEvent() {
		if(state!=GuiButtonState.MOUSEBUTTONDOWN && state!=GuiButtonState.CLICKED)
			mouseOn();
		while(Mouse.next()) {
		    if (Mouse.getEventButton() > -1) {
		    	//´©¸£±â
		        if (Mouse.getEventButtonState()) {
		        	//¿ÞÂÊ´©¸£±â
		            if(Mouse.getEventButton()==0) {
		            	mouseButtonDown();
		            }
		            	
		        }
		        //¶¼±â
		        else {
		        	//¿ÞÂÊ¶¼±â
		        	if(Mouse.getEventButton()==0) {
		        		if(state==GuiButtonState.MOUSEBUTTONDOWN)
		        			mouseClicked();
		            }
		        }
		    }
		}
	}
	
	
	
	protected void mouseOn() {
		state=GuiButtonState.MOUSEON;
		current_texture=tex_mouseOn;
	}
	
	//¾È¾²ÀÓ
	protected void mouseClicked() {
		state=GuiButtonState.CLICKED;
		current_texture=tex_clickFinish;
	}
	
	protected void mouseButtonDown() {
		state=GuiButtonState.MOUSEBUTTONDOWN;
		current_texture=tex_mouseButtonDown;
		
	}
	
	protected void nonEvent() {
		state=GuiButtonState.NONEVENT;
		current_texture=tex_nonEvent;
	}
	

	@Override
	public Rectangle getCollider() {
		// TODO Auto-generated method stub
		return _CollisionRange;
	}



	@Override
	public EntityTexture getEntityTexture() {
		// TODO Auto-generated method stub
		return current_texture;
	}




}
