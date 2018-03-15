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
	
	//String�� ����� GUIText�����Ͽ� ������ ���������Ϳ� �ؽ�ó��ǥ������ ������ add����
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
	
	//���� : load�� �ؽ�ó�� vao,vbo���� �����ִ�. �׳� �������������� add,remove�һ� ���̻� �ؽ�ó�� vao�� �ʿ䰡���ٸ� �װ͸��� ��������
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
