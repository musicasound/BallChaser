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
import Physics.Transform;
import Shaders.Shader2D;
import Textures.EntityTexture;

public class Renderer2D {
	//same quad 모델
	
	private Map<EntityTexture, List<Entity>> instancingEntities = new HashMap<EntityTexture, List<Entity>>();
	List<Entity> nonInstancingEntities=new ArrayList<Entity>();;
	
	private final VaoObject quad ;//2d는 항상 같은 로컬 좌표 (-0.5,-0.5)~(0.5,0.5)정사각형
	private Shader2D shader;
	
	public Renderer2D(Loader loader) {
		float[]positions = {-0.5f,0.5f,-0.5f,-0.5f,0.5f,0.5f,0.5f,-0.5f};//using triangle Strips
		quad=loader.loadToVAO(positions,2);
		shader = new Shader2D();
	}

	
	public void render() {
		shader.start();
		renderInstancingEntity();
		renderNonIntancingEntity();
		shader.stop();
	}
	
	
	// renderNonIntancingEntity : 동적인 텍스처 데이터를 가진 엔티티
	// renderIntancingEntity : 정적인 텍스처 데이터를 가진 엔티티
	//원래 인스턴싱을 위한 렌더러지만 아직배우지않아 묶어서 렌더링하긴해도 묶어서 draw콜을하지않음 
	//나눈이유 : key로 텍스처를 받고 key기반으로 texture를 로드하기때문에
	//동적인 텍스처를 가능하게하려면 해쉬에서없애고 다시 add시켜야하는 방법도있긴함 
	
	protected void renderInstancingEntity() {
		
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);//textTure유닛 0번을 활성화 아마활성화역할
		GL11.glEnable(GL11.GL_BLEND);//까만배경없애기
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);//뒤에막히는거없애기
		for(EntityTexture textureModel:(instancingEntities.keySet())) {
			prepareTexturedModel(textureModel);
			
			List<Entity> batch =instancingEntities.get(textureModel);
			for(Entity entity:batch) {
				
				Transform tranform= entity.getTransform();
				Matrix4f matrix=Physics.Maths.createTransformation2DMatrix(tranform.getPosition(),tranform.getRotationAngle(),tranform.getScale());
		
				shader.loadTransformation(matrix);
				GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
			}
		}
		
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);//뒤에막히는거없애기
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		
	}
	

	protected void renderNonIntancingEntity() {
		shader.start();
		
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);//textTure유닛 0번을 활성화 아마활성화역할
		GL11.glEnable(GL11.GL_BLEND);//까만배경없애기
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);//뒤에막히는거없애기
		
			
		for(Entity entity:nonInstancingEntities) {
			
			prepareTexturedModel(entity.getEntityTexture());//entity한개한개마다 텍스처바인드
			Transform tranform= entity.getTransform();
			Matrix4f matrix=Physics.Maths.createTransformation2DMatrix(tranform.getPosition(),tranform.getRotationAngle(),tranform.getScale());
		
			shader.loadTransformation(matrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
			
		}
		
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);//뒤에막히는거없애기
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		
		shader.stop();
	}
	
	
	private void prepareTexturedModel(EntityTexture texture) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,texture.getID());
	}

	public void processInstancingEntity(Entity entity) {
		EntityTexture entityModel = entity.getEntityTexture();
		List<Entity> batch = instancingEntities.get(entityModel);
		if (batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			instancingEntities.put(entityModel, newBatch);
		}
	}
	
	//texture가변경되던말던 상관 없다.
	public void processNonInstancingEntity(Entity entity) {
		nonInstancingEntities.add(entity);
	}
	
	public void initialize() {
		instancingEntities = new HashMap<EntityTexture, List<Entity>>();
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
}
