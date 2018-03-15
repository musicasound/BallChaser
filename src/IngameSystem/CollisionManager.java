package IngameSystem;

import java.util.ArrayList;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import Entities.Ball;
import Entities.Entity;
import Entities.Missile;
import Entities.Player;

public class CollisionManager {

	public static boolean CollisionDetected(Entity entity1, Entity entity2)
	{
		Rectangle c1=entity1.getCollider();
		Rectangle c2=entity2.getCollider();
		
		return intersect(c1, c2);
	}
	
	public static boolean intersect(Rectangle r1, Rectangle r2)
	{
		int r1_left=r1.getX();
		int r2_left=r2.getX();
		int r1_right=r1.getX()+r1.getWidth();
		int r2_right=r2_left+r2.getWidth();
		int r1_top=r1.getY();
		int r2_top=r2.getY();
		
		int r1_bottom=r1_top-r1.getHeight();
		int r2_bottom=r2_top-r2.getHeight();
		
		boolean isIntersect=
				(
						r1_left<r2_right &&
						r2_left<r1_right &&
						r1_top>r2_bottom &&
						r2_top >r1_bottom				
				);
				
		return isIntersect;		
	}
	
	//�÷��̾�� �̻��ϰ� ���� �浹 ���¸� ���� �� ������Ʈ�Ѵ�.
	public static void ProcessCollision(Player player, ArrayList<Missile> missiles, Ball ball)
	{
		if(player.getStatus()==Entities.CharacterStatus.DEAD)
			return;
		
		//�ٱ����� ������ �״´�.
		if(isOutsideOfBoundary(player))
		{
			Vector2f velocity=new Vector2f(player.getVelocity());
			float velocityScale=player.getVelocity().length();
			velocity.normalise();
			
			player.Die();
			
			//�÷��̾ ���� ������ �־��ٸ�
			if(player.getPlayerIndex()==ball.getCatchingPlayerIdx())
			{
				//���� ��ġ�� ó���ϰ�
				ball.setCatchingPlayerIdx(-1);
				
				//�÷��̾ �̵����̾��� �������� ���� �����̰� ó���Ѵ�.
				ball.setVelocityScale(velocityScale);
				ball.setVelocityDirection(velocity);
			}
			
			
			return;
		}
		
		//�̻��ϰ��� �浹�� ó���Ѵ�.
		for(Missile missile : missiles)
		{
			if(CollisionDetected(missile, player))
			{
				//�̻����� Ÿ�Կ� ���� ó���� �Ѵ�.
			}
		}
		
		if(player.getStatus()==Entities.CharacterStatus.STUN)
		{
			return;
		}
		
		//ball�� �÷��̾�
		//ball�� �������� �ƴ� ��
		if(ball.getCatchingPlayerIdx()==-1)
		{
			//ball�� �÷��̾ ��Ҵٸ�
			if(CollisionDetected(player, ball))
			{
				//�ش� �÷��̾��� ������ �����Ѵ�.
				ball.setCatchingPlayerIdx(player.getPlayerIndex());
			}
		}
		else //ball�� �������� ��
		{
			//�������� �÷��̾���
			if(ball.getCatchingPlayerIdx()==player.getPlayerIndex())
			{
				ball.getTransform().setPosition(new Vector2f(player.getTransform().getPosition()));
			}
		}
		
	}
	
	public static boolean isOutsideOfBoundary(Entity entity)
	{
		float boundary_left=-300.0f;
		float boundary_right=300.0f;
		float boundary_up=300.0f;
		float boundary_down=-300.0f;
		
		Rectangle entityBoundary=entity.getCollider();
		Vector2f pos=entity.getTransform().getPosition();
		
		float entity_left=entityBoundary.getX();
		float entity_right=entity_left+entityBoundary.getWidth();
		float entity_up=entityBoundary.getY();
		float entity_down=entity_up-entityBoundary.getHeight();
		
		if(entity_left<boundary_left) return true;
		if(entity_right>boundary_right) return true;
		if(entity_up>boundary_up) return true;
		if(entity_down<boundary_down) return true;
		
		
		  Rectangle innerBoundary=new Rectangle(-180, 180, 360, 360);
		  
		  if(intersect(innerBoundary ,entity.getCollider()))
		  {
			  return true;
		  }
		  
		  return false;
	}
//���μ������� �׽�Ʈ �Ϸ�.
//�ٱ��� ���̵�, ���� ���̵忡�� ���� �ݻ縦 üũ��.
	
	public static void checkReflection(Ball ball)
	{
		//ball�� �������̸� �ݻ縦 �������� �ʴ´�.
		if(ball.isCatched())
			return;
		
		
	  Vector2f pos=ball.getTransform().getPosition();
	  float radius=ball.getRadius();
	 //�ٱ��� ���̵� �浹
	  if(pos.y-radius<-300.0f)
	  {
	    ball.Reflect(new Vector2f(0.0f, 1.0f));
	    return;
	  }
	  else if(pos.y+radius>300.0f)
	  {
	    ball.Reflect(new Vector2f(0.0f, -1.0f));
	    return;
	  }
	  
	  if(pos.x+radius>300.0f)
	  {
	    ball.Reflect(new Vector2f(-1.0f, 0.0f));
	    return;
	  }
	  else if(pos.x-radius<-300.0f)
	  {
	    ball.Reflect(new Vector2f(1.0f, 0.0f));
	    return;
	  }

	  Rectangle coll=new Rectangle(-180, 180, 360, 360);
	  

	  //���� ���� ���̵尡 �浹���� ��
	  	if(intersect(ball.getCollider(), coll) && ball.isEnableBoundaryCollision())
	  {     //����
	  	ball.setEnableBoundaryCollision();
	  	
	     if(pos.x<coll.getX())
	     {
	       ball.Reflect(new Vector2f(-1.0f, 0.0f));
	     }//������
	     else if(pos.x>coll.getX()+coll.getWidth())
	     {
	       ball.Reflect(new Vector2f(1.0f, 0.0f));
	       
	     }//����
	     else if(pos.y>coll.getY())
	     {
	       ball.Reflect(new Vector2f(0.0f, -1.0f));
	     }//�Ʒ���
	     else if(pos.y<coll.getY()+coll.getHeight())
	     {
	       ball.Reflect(new Vector2f(0.0f, 1.0f));
	     }
	     
	  }
	  
	  
	  
	}
}
