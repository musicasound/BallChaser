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
import Input.String_Input;
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
		//EntityTexture texture = new EntityTexture(loader.loadTexture("box"));
		
		ArrayList<Tile> tiles=new ArrayList<Tile>();
		
		
		//YechanTestEntity entity = new YechanTestEntity(texture,new Transform(new Vector2f(150,150),0,new Vector2f(10,10)));
		
		
		
		//load tiles
		
		int _tileLoadingIdx=0;
		for(int i=0; i<10; i++)
		{
			tiles.add(new Tile(new Vector2f(-270,-270+i*60.0f)));
			renderer2d.processInstancingEntity(tiles.get(_tileLoadingIdx));
			_tileLoadingIdx++;
		}
		
		for(int i=0; i<9; i++)
		{
			tiles.add(new Tile(new Vector2f(-210+i*60.0f,270)));
			renderer2d.processInstancingEntity(tiles.get(_tileLoadingIdx));
			_tileLoadingIdx++;
		}
		
		for(int i=0; i<9; i++)
		{
			tiles.add(new Tile(new Vector2f(270,210-60.0f*i)));
			renderer2d.processInstancingEntity(tiles.get(_tileLoadingIdx));
			_tileLoadingIdx++;
		}
		
		for(int i=0; i<8; i++)
		{
			tiles.add(new Tile(new Vector2f(210-60.0f*i, -270)));
			renderer2d.processInstancingEntity(tiles.get(_tileLoadingIdx));
			_tileLoadingIdx++;
		}
		
		
		MousePicking mousePicking = new MousePicking();
		Renderer2D rendererGuis = new Renderer2D(loader);
		Transform Button1Trnsf= new Transform(new Vector2f(270,0),0,new Vector2f(60,60));
		EntityTexture textures[] = new EntityTexture[10];
		textures[0]=new EntityTexture(loader.loadTexture("mud"));
		textures[1]=new EntityTexture(loader.loadTexture("path"));
		textures[2]=new EntityTexture(loader.loadTexture("grass"));
		
		GuiButton guiButton1=new GuiButton(Button1Trnsf,textures[0],textures[1],null,textures[2]);
		
		//rendererGuis.processNonInstancingEntity(guiButton1);
		
		TextMaster.init(loader);
		FontType font = new FontType(loader.loadFontTextureAtlas("candara"),new File("res/candara.fnt"));
		GUIText text = new GUIText("A sample string of text",5,font,new Vector2f(0.0f,0.4f),1f,true);
		text.setColour(1, 1, 0);
		
		/*주석처리 지워도됨
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
