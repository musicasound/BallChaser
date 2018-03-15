package fontRendering;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;


public class FontRenderer {

	private FontShader shader;

	//shader는 static하게 여러씬에서 쓸것이기때문 scene Map<FontType,List<GUIText>> 가달라짐
	public FontRenderer(FontShader shader) {
		this.shader = shader;
	}
	
	public void render(Map<FontType,List<GUIText>> texts) {
		prepare();
		for(FontType font: texts.keySet()) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
			List<GUIText> textBatch = texts.get(font);
			for(GUIText text : textBatch) {
				renderText(text);
			}
			
		}
		endRendering();
	}


	private void prepare(){
		GL11.glEnable(GL11.GL_BLEND);//까만배경없애기
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);//뒤에막히는거없애기
		shader.start();
	}
	
	private void renderText(GUIText text){
		GL30.glBindVertexArray(text.getMesh());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		shader.loadColour(text.getColour());
		shader.loadEdge(text.getEdge());
		shader.loadWidth(text.getWidth());
		shader.loadBorderColour(text.getBorderColour());
		shader.loadBoaderEdge(text.getBorderEdge());
		shader.loadBoaderWidth(text.getBorderWidth());
		shader.loadShadowOffset(text.getShadowOffset());
		
		shader.loadTranslation(text.getPosition());
		
		
		
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	private void endRendering(){
		shader.stop();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

}
