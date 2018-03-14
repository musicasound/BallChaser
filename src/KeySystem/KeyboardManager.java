package KeySystem;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

public class KeyboardManager {

	public static class KeyEvent
	{
		public final boolean press;
		public final int keyID;
		
		public KeyEvent(boolean press, int keyID)
		{
			this.press=press;
			this.keyID=keyID;
		}
	}
	
	
	private static boolean[] keys=new boolean[65536];
	
	public static void update()
	{
		
		while(Keyboard.next())
		{
			boolean pressed=Keyboard.getEventKeyState();
			int keyID=Keyboard.getEventKey();
			
			keys[keyID]=pressed;
		}
	}
	
	public static boolean isKeyPressed(int key)
	{
		return keys[key];
	}
}
