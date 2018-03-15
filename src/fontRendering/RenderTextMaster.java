package fontRendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import RenderEngine.Loader;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontMeshCreator.TextMeshData;


public class RenderTextMaster {
	private Loader loader;
	private Map<FontType,List<GUIText>> texts = new HashMap<FontType,List<GUIText>>();
	private FontRenderer renderer;
	
	public RenderTextMaster(Loader loader,FontRenderer renderer){
		this.renderer = renderer;
		this.loader =loader;
	
	}
	
	
	public void render() {
		renderer.render(texts);
	}
	
	//String이 저장된 GUIText에대하여 적절한 정점데이터와 텍스처좌표데이터 생성후 add까지
	public void loadText(GUIText text) {
		FontType font=text.getFont();
		TextMeshData data=font.loadText(text);
		
		int vao = loader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
		text.setMeshInfo(vao, data.getVertexCount());
		List<GUIText> textBatch = texts.get(font);
		if(textBatch == null) {
			textBatch 	= new ArrayList<GUIText>();
			texts.put(font, textBatch);
		}
		textBatch.add(text);
	}
	
	//주의 : load한 텍스처와 vao,vbo들은 남아있다. 그냥 렌더링할지말지 add,remove할뿐 더이상 텍스처와 vao가 필요가없다면 그것마저 지워주자
	public void removeText(GUIText text) {
		List<GUIText> textBatch=texts.get(text.getFont());
		textBatch.remove(text);
		if(textBatch.isEmpty()) {
			texts.remove(text.getFont());
		}
	}
	
	public void cleanUpList() {
		texts = new HashMap<FontType,List<GUIText>>();
	}
}
