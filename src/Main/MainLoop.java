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
				//���⿡ ������Ʈ ������ �ۼ���.
				
			}
			
			//Render
			
			
			//update delta time and display
			DisplayManager.updateDisplay();
		}
	}
}
