package Main;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import com.sun.corba.se.spi.orbutil.fsm.Input;

import AudioSystem.AudioManager;
import AudioSystem.SoundSource;
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
import RenderEngine.Render2DMaster;
import Scenes.SceneManager;
import Textures.EntityTexture;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.RenderTextMaster;



public class MainLoop {
	public static void main(String[] args) throws IOException
	{
		DisplayManager.createDisplay();
		
		Loader loader =new Loader();
		GlobalDataManager.init(loader);
		SceneManager.init();
		AudioManager.init();
		AudioManager.setListenerData();

		
		
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
