package RenderEngine;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import Shaders.Shader2D;

public class Renderer2D {
	//same quad ��
	
	public interface myRequest{
		int getTexture();
		Vector2f getPosition();
		float getRotateZ();
		float getScale();
	}
	
	private final VaoObject quad ;//2d�� �׻� ���� ���� ��ǥ (-1,-1)~(1,1)���簢��
	private Shader2D shader;
	
	public Renderer2D(Loader loader) {
		float[]positions = {-1,1,-1,-1,1,1,1,-1};//using triangle Strips
		quad=loader.loadToVAO(positions,2);
		shader = new Shader2D();
	}
	
	public void render(List<myRequest> entities) {
		shader.start();
		
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);//textTure���� 0���� Ȱ��ȭ �Ƹ�Ȱ��ȭ����
		GL11.glEnable(GL11.GL_BLEND);//������ֱ�
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);//�ڿ������°ž��ֱ�
		
		for(myRequest entity:entities) {
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D,entity.getTexture());
			Matrix4f matrix=Physics.Maths.createTransformation2DMatrix(entity.getPosition(), entity.getScale(),entity.getRotateZ());
			shader.loadTransformation(matrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);//�ڿ������°ž��ֱ�
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		
		shader.stop();
	}
	public void cleanUp() {
		shader.cleanUp();
	}
}
