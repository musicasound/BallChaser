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
	
	//플레이어와 미사일과 볼의 충돌 상태를 점검 및 업데이트한다.
	public static void ProcessCollision(Player player, ArrayList<Missile> missiles, Ball ball)
	{
		if(player.getStatus()==Entities.CharacterStatus.DEAD)
			return;
		
		//바깥으로 나가면 죽는다.
		if(isOutsideOfBoundary(player))
		{
			Vector2f velocity=new Vector2f(player.getVelocity());
			float velocityScale=player.getVelocity().length();
			velocity.normalise();
			
			player.Die();
			
			//플레이어가 볼을 가지고 있었다면
			if(player.getPlayerIndex()==ball.getCatchingPlayerIdx())
			{
				//볼을 놓치게 처리하고
				ball.setCatchingPlayerIdx(-1);
				
				//플레이어가 이동중이었던 방향으로 볼이 움직이게 처리한다.
				ball.setVelocityScale(velocityScale);
				ball.setVelocityDirection(velocity);
			}
			
			
			return;
		}
		
		//미사일과의 충돌을 처리한다.
		for(Missile missile : missiles)
		{
			if(CollisionDetected(missile, player))
			{
				//미사일의 타입에 따른 처리를 한다.
			}
		}
		
		if(player.getStatus()==Entities.CharacterStatus.STUN)
		{
			return;
		}
		
		//ball과 플레이어
		//ball이 소유중이 아닐 때
		if(ball.getCatchingPlayerIdx()==-1)
		{
			//ball과 플레이어가 닿았다면
			if(CollisionDetected(player, ball))
			{
				//해당 플레이어의 소유로 변경한다.
				ball.setCatchingPlayerIdx(player.getPlayerIndex());
			}
		}
		else //ball이 소유중일 때
		{
			//소유중인 플레이어라면
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
//프로세싱으로 테스트 완료.
//바깥쪽 사이드, 안쪽 사이드에서 공의 반사를 체크함.
	
	public static void checkReflection(Ball ball)
	{
		//ball이 소유중이면 반사를 적용하지 않는다.
		if(ball.isCatched())
			return;
		
		
	  Vector2f pos=ball.getTransform().getPosition();
	  float radius=ball.getRadius();
	 //바깥쪽 사이드 충돌
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
	  

	  //공과 안쪽 사이드가 충돌했을 때
	  	if(intersect(ball.getCollider(), coll))
	  {     //왼쪽
	     if(pos.x<coll.getX())
	     {
	       ball.Reflect(new Vector2f(-1.0f, 0.0f));
	     }//오른쪽
	     else if(pos.x>coll.getX()+coll.getWidth())
	     {
	       ball.Reflect(new Vector2f(1.0f, 0.0f));
	       
	     }//위쪽
	     else if(pos.y>coll.getY())
	     {
	       ball.Reflect(new Vector2f(0.0f, -1.0f));
	     }//아래쪽
	     else if(pos.y<coll.getY()+coll.getHeight())
	     {
	       ball.Reflect(new Vector2f(0.0f, 1.0f));
	     }
	     
	  }
	  
	  
	  
	}
}
