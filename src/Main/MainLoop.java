package Main;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import com.sun.corba.se.spi.orbutil.fsm.Input;

import Displays.DisplayManager;
import Entities.YechanTestEntity;
import Input.String_Input;
import Physics.Transform;
import RenderEngine.Loader;
import RenderEngine.Renderer2D;
import Textures.EntityTexture;



public class MainLoop {
	public static void main(String[] args)
	{
		DisplayManager.createDisplay();
		
		Loader loader =new Loader();
		EntityTexture texture = new EntityTexture(loader.loadTexture("box"));
		YechanTestEntity entity = new YechanTestEntity(texture,new Transform(new Vector2f(150,150),30),new Vector2f(20f,20f));
		
		Renderer2D renderer2d = new Renderer2D(loader);
		renderer2d.processEntity(entity);
		/*林籍贸府 瘤况档凳
		String_Input string_input1=new String_Input();
		String_Input string_input2=new String_Input();
		int state =0;
		*/
		
		
		while(!Display.isCloseRequested())
		{
			
			while(DisplayManager.isUpdate())
			{
				
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
			renderer2d.render();
			//Render
			
			
			//update delta time and display
			DisplayManager.updateDisplay();
		}
	}
}
