package RenderEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;


//버퍼는 바뀔수있고 어느시점에 어느목적으로든 사용가능

public class Loader {
	
	//메모리관리를 위한 자료구조 vao,vbo들 메모리해제
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();
	
	

	
	public VaoObject loadToVAO(float[] positions,int dimensions) {
		int vaoID=createVAO();
		this.storeDataInAttributeList(0, dimensions, positions);
		unbindVAO();
		return new VaoObject(vaoID, positions.length/dimensions);//x,y좌표 각각..
	}
	
	
	//for font rendering
	public int loadToVAO(float[] positions,float[] textureCoords){
		int vaoID=createVAO();
		
		storeDataInAttributeList(0,2,positions);
		storeDataInAttributeList(1,2,textureCoords);//texture은 2d
	
		
		unbindVAO();
		
		
		return vaoID;
	}
	


	
	
	//메모리로드후 idx반환
	public int loadTexture(String fileName) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+fileName+".png"));
			
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS,0.4f);//-1하면 좀낮게
			
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int textureID=texture.getTextureID();
		textures.add(textureID);
		 
		return textureID;
		
	}
	
	//메모리로드후 idx반환
	public int loadFontTextureAtlas(String fileName) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+fileName+".png"));
			
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS,0f);//-1하면 좀낮게
			
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int textureID=texture.getTextureID();
		textures.add(textureID);
		 
		return textureID;
		
	}
	
	public void cleanUp() {
		for(int vao:vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		for(int vbo:vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		for(int texture:textures) {
			GL11.glDeleteTextures(texture);
		}
	}
	
	//emptyVAO생성자
	private int createVAO() {
		int vaoID=GL30.glGenVertexArrays();//emptyVAO를 만들고 idx반환
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	
	
	private void storeDataInAttributeList(int attributeIdx,int coordinateSize, float [] data){
		int vboID=GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);//어떤버퍼에 할당할지결정,이제부터쓸 vboID 결정
		FloatBuffer buffer=storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER,buffer,GL15.GL_STATIC_DRAW);//세번째인자값은 어떻게 사용될건지 static,edit 등..
		GL20.glVertexAttribPointer(attributeIdx,coordinateSize,GL11.GL_FLOAT,false,0,0);//속성넘버,컴포넌트개수,타입,정규화유무 ,각각의값사이에추가데이터간격, 시작위치
		//같은버퍼에 offset을 달리해서 설정할수도있다.
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	//다사용한거 unbind 나중에설명..
	private void unbindVAO() {
		GL30.glBindVertexArray(0);//아마 기본상태가 0번이라 unbind의 의미를 가짐
	}
	
	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();//write끝 읽기전용설정
		return buffer;
	}
}

