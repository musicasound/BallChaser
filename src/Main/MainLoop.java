package Main;

import org.lwjgl.opengl.Display;

import Displays.DisplayManager;

public class MainLoop {
	public static void main(String[] args)
	{
		DisplayManager.createDisplay();
		
		
		while(!Display.isCloseRequested())
		{
			while(DisplayManager.isUpdate())
			{
				//여기에 업데이트 구문을 작성함.
				
			}
			
			//Render
			
			
			//update delta time and display
			DisplayManager.updateDisplay();
		}
	}
}
