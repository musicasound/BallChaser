package Main;



import java.io.File;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import com.sun.corba.se.spi.orbutil.fsm.Input;

import Displays.DisplayManager;
import Entities.Tile;
import Entities.YechanTestEntity;
import Guis.GuiButton;
import IngameSystem.GlobalDataManager;
import Input.String_Input;
import KeySystem.KeyboardManager;
import Physics.Transform;
import Picking.MousePicking;
import RenderEngine.Loader;
import RenderEngine.Renderer2D;
import Scenes.SceneManager;
import Textures.EntityTexture;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;



public class MainLoop {
	public static void main(String[] args)
	{
		DisplayManager.createDisplay();
		
		Loader loader =new Loader();
		GlobalDataManager.init(loader);
		SceneManager.init();
		TextMaster.init(loader);
		
		while(!Display.isCloseRequested())
		{
			while(DisplayManager.isUpdate())
			{
				KeyboardManager.update();
				SceneManager.getCurrentScene().update();
			}
			
			prepareRendering();
			
			SceneManager.getCurrentScene().render();
			//Render

			TextMaster.render();//문제 그냥넣어버림 넣고빼기불가,vao,vbo,삭제불가
			
			
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
