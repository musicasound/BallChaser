package Displays;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	private static final int WIDTH=720;
	private static final int HEIGHT=720;
	private static final int FPS_CAP=120;

	private static long lastFrameTime;
	private static float delta;
	private static float totalTime;
	/*
	 * 
	 * 0.02�� fixed update��  �� ���̳� ����Ǿ�� �ϴ����� ��Ÿ����.
	 */
	private static int updatingCount=0;
	
	//deltatime �հ踦 ������Ų��.
	//0.02�̻��� �Ǹ� fixedUpdate�� �����Ѵ�.
	private static float updateTimeSum=0.0f;
	
	//���÷��̸� �����ϰ�, ȭ���� ũ�⸦ �����Ѵ�.
	public static void createDisplay(){
		ContextAttribs attribs = new ContextAttribs(3,3)
				.withForwardCompatible(true)
				.withProfileCore(true);
		
		
		//Determine the size of display
		try
		{
		Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
		Display.create(new PixelFormat().withDepthBits(24), attribs);
		Display.setTitle("Ball Chaser");
		GL11.glEnable(GL13.GL_MULTISAMPLE);
		}
		catch(LWJGLException e)
		{
			e.printStackTrace();			
		}
		
		GL11.glViewport(0,0,WIDTH, HEIGHT);
		lastFrameTime=getCurrentTime();
		totalTime=0;
	}
	
	
	//game�� FPS�� ���� Synchronize�Ѵ�.
	public static void updateDisplay(){
		
		Display.sync(FPS_CAP);
		Display.update();
		long currentFrameTime=getCurrentTime();
		delta=(currentFrameTime-lastFrameTime)/1000.0f;
		lastFrameTime=currentFrameTime;
		
		updateTimeSum+=delta;
		totalTime+=delta;
		
		while(updateTimeSum>=0.02f)
		{
			updatingCount++;
			updateTimeSum-=0.02f;
		}
	}
	
	public static void closeDisplay(){
		
		Display.destroy();
		
	}
	
	public static float deltaTime()
	{
		return delta;		
	}
	
	public static float fixedDeltaTime()
	{
		return 0.02f;
	}
	
	
	/*
	 *<pre>
	 *������Ʈ�� ����Ǿ�� �ϴ����� �˻���.
	 * </pre>
	 * */
	public static boolean isUpdate()
	{
		
		if(updatingCount>0)
		{
			updatingCount--;
			return true;
		}
		else
			return false;
	}
	
	private static long getCurrentTime()
	{
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
	
	public static float getTotalTime()
	{
		return totalTime;
	}
}
