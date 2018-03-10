package Main;



import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import com.sun.corba.se.spi.orbutil.fsm.Input;

import Displays.DisplayManager;
import Entities.YechanTestEntity;
import Guis.GuiButton;
import Input.String_Input;
import Physics.Transform;
import Picking.MousePicking;
import RenderEngine.Loader;
import RenderEngine.Renderer2D;
import Textures.EntityTexture;



public class MainLoop {
	public static void main(String[] args)
	{
		DisplayManager.createDisplay();
		
		Loader loader =new Loader();
		EntityTexture texture = new EntityTexture(loader.loadTexture("box"));
		YechanTestEntity entity = new YechanTestEntity(texture,new Transform(new Vector2f(150,150),30,new Vector2f(10,10)));
		
		Renderer2D renderer2d = new Renderer2D(loader);
		renderer2d.processInstancingEntity(entity);
		
		MousePicking mousePicking = new MousePicking();
		Renderer2D rendererGuis = new Renderer2D(loader);
		Transform Button1Trnsf= new Transform(new Vector2f(0,0),0,new Vector2f(100,100));
		EntityTexture textures[] = new EntityTexture[10];
		textures[0]=new EntityTexture(loader.loadTexture("mud"));
		textures[1]=new EntityTexture(loader.loadTexture("path"));
		textures[2]=new EntityTexture(loader.loadTexture("grass"));
		
		GuiButton guiButton1=new GuiButton(Button1Trnsf,textures[0],textures[1],null,textures[2]);
		
		rendererGuis.processNonInstancingEntity(guiButton1);
		/*林籍贸府 瘤况档凳
		String_Input string_input1=new String_Input();
		String_Input string_input2=new String_Input();
		int state =0;
		*/
		
		
		while(!Display.isCloseRequested())
		{
			
			mousePicking.update();
			guiButton1.update(mousePicking);
			while(DisplayManager.isUpdate())
			{
				System.out.println(mousePicking.getCurrentMousePos());
				/* Input test
				if(state==0) {
					string_input1.update();
					if(string_input1.IsEnterKeyPressed()) {
						System.out.println(string_input1.Pop());
					}
				}
				
				else if(state ==1) {
					string_input2.update();
					if(string_input2.IsEnterKeyPressed()) {
						System.out.println(string_input2.Pop());
					}
					
				}
				
				if(Keyboard.isKeyDown(Keyboard.KEY_F1))
					state = (state ==1)? 0:1;
				*/
				
			}
			prepareRendering();
			renderer2d.render();
			rendererGuis.render();
			//Render
			
			
			//update delta time and display
			DisplayManager.updateDisplay();
		}
	}
	
	
	static void prepareRendering()
	{
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
}
