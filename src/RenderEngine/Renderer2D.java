package RenderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import Entities.Entity;
import Shaders.Shader2D;
import Textures.EntityTexture;

public class Renderer2D {
	//same quad ��
	
	private Map<EntityTexture, List<Entity>> entities = new HashMap<EntityTexture, List<Entity>>();
	
	private final VaoObject quad ;//2d�� �׻� ���� ���� ��ǥ (-1,-1)~(1,1)���簢��
	private Shader2D shader;
	
	public Renderer2D(Loader loader) {
		float[]positions = {-1,1,-1,-1,1,1,1,-1};//using triangle Strips
		quad=loader.loadToVAO(positions,2);
		shader = new Shader2D();
	}
	
	public void render() {
		shader.start();
		
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);//textTure���� 0���� Ȱ��ȭ �Ƹ�Ȱ��ȭ����
		GL11.glEnable(GL11.GL_BLEND);//������ֱ�
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);//�ڿ������°ž��ֱ�
		for(EntityTexture textureModel:(entities.keySet())) {
			prepareTexturedModel(textureModel);
			
			List<Entity> batch =entities.get(textureModel);
			for(Entity entity:batch) {
				
				Matrix4f matrix=Physics.Maths.createTransformation2DMatrix(entity.getTransform().getPosition(),entity.getTransform().getRotationAngle(),entity.getScale());
				System.out.println(matrix);
				
				shader.loadTransformation(matrix);
				GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
			}
		}
		
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);//�ڿ������°ž��ֱ�
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		
		shader.stop();
	}
	
	private void prepareTexturedModel(EntityTexture texture) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,texture.getID());
	}

	public void processEntity(Entity entity) {
		EntityTexture entityModel = entity.getEntityTexture();
		List<Entity> batch = entities.get(entityModel);
		if (batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
}
