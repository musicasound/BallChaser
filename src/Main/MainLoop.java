package Main;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.sun.corba.se.spi.orbutil.fsm.Input;

import Displays.DisplayManager;
import Input.String_Input;



public class MainLoop {
	public static void main(String[] args)
	{
		DisplayManager.createDisplay();
		
		/*
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
			
			//Render
			
			
			//update delta time and display
			DisplayManager.updateDisplay();
		}
	}
}
