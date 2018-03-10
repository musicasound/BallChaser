package IngameSystem;

import java.util.ArrayList;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import Entities.Entity;
import Entities.Missile;
import Entities.Player;

public class CollisionManager {

	public static boolean CollisionDetected(Entity entity1, Entity entity2)
	{
		Rectangle c1=entity1.getCollider();
		Rectangle c2=entity2.getCollider();
		
		return c1.intersects(c2);
	}
	
	public static boolean CollisionDetected(Rectangle entity1, Rectangle entity2)
	  { 
	    return entity1.intersects(entity2);
	  }
	
	public static void ProcessCollision(Player player, ArrayList<Missile> missile)
	{
		
	}
	
	
//���μ������� �׽�Ʈ �Ϸ�.
//�ٱ��� ���̵�, ���� ���̵忡�� ���� �ݻ縦 üũ��.
	
//	void checkReflection()
//	{
//	  PVector pos=ball.getTransform().getPosition();
//	  float radius=12.5f;
//	 //�ٱ��� ���̵� �浹
//	  if(pos.y-radius<0.0f)
//	  {
//	    ball.Reflect(new PVector(0.0f, 1.0f));
//	    return;
//	  }
//	  else if(pos.y+radius>display_height)
//	  {
//	    ball.Reflect(new PVector(0.0f, -1.0f));
//	    return;
//	  }
//	  
//	  if(pos.x+radius>display_width)
//	  {
//	    ball.Reflect(new PVector(-1.0f, 0.0f));
//	    return;
//	  }
//	  else if(pos.x-radius<0.0f)
//	  {
//	    ball.Reflect(new PVector(1.0f, 0.0));
//	    return;
//	  }
//
//
//	  //���� ���� ���̵尡 �浹���� ��
//	  if(col.CollisionDetected(ball.getCollider(), coll))
//	  {     //����
//	     if(pos.x<coll.x)
//	     {
//	       ball.Reflect(new PVector(-1.0f, 0.0f));
//	     }//������
//	     else if(pos.x>coll.x+coll.width)
//	     {
//	       ball.Reflect(new PVector(1.0f, 0.0f));
//	       
//	       System.out.println("coll.x:"+coll.x);
//	       System.out.println("pos.x:"+pos.x);
//	     }//����
//	     else if(pos.y<=coll.y)
//	     {
//	       ball.Reflect(new PVector(0.0f, -1.0f));
//	     }//�Ʒ���
//	     else if(pos.y>=coll.y+coll.height)
//	     {
//	       ball.Reflect(new PVector(0.0f, 1.0f));
//	     }
//	     
//	  }
//	  
//	  
//	  
//	}
}
