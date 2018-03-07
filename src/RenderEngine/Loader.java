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


//���۴� �ٲ���ְ� ��������� ����������ε� ��밡��

public class Loader {
	
	//�޸𸮰����� ���� �ڷᱸ�� vao,vbo�� �޸�����
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();
	
	

	
	public VaoObject loadToVAO(float[] positions,int dimensions) {
		int vaoID=createVAO();
		this.storeDataInAttributeList(0, dimensions, positions);
		unbindVAO();
		return new VaoObject(vaoID, positions.length/dimensions);//x,y��ǥ ����..
	}
	
	
	//for font rendering
	public int loadToVAO(float[] positions,float[] textureCoords){
		int vaoID=createVAO();
		
		storeDataInAttributeList(0,2,positions);
		storeDataInAttributeList(1,2,textureCoords);//texture�� 2d
	
		
		unbindVAO();
		
		
		return vaoID;
	}
	


	
	
	//�޸𸮷ε��� idx��ȯ
	public int loadTexture(String fileName) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+fileName+".png"));
			
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS,0.4f);//-1�ϸ� ������
			
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int textureID=texture.getTextureID();
		textures.add(textureID);
		 
		return textureID;
		
	}
	
	//�޸𸮷ε��� idx��ȯ
	public int loadFontTextureAtlas(String fileName) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+fileName+".png"));
			
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS,0f);//-1�ϸ� ������
			
		
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
	
	//emptyVAO������
	private int createVAO() {
		int vaoID=GL30.glGenVertexArrays();//emptyVAO�� ����� idx��ȯ
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	
	
	private void storeDataInAttributeList(int attributeIdx,int coordinateSize, float [] data){
		int vboID=GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);//����ۿ� �Ҵ���������,�������;� vboID ����
		FloatBuffer buffer=storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER,buffer,GL15.GL_STATIC_DRAW);//����°���ڰ��� ��� ���ɰ��� static,edit ��..
		GL20.glVertexAttribPointer(attributeIdx,coordinateSize,GL11.GL_FLOAT,false,0,0);//�Ӽ��ѹ�,������Ʈ����,Ÿ��,����ȭ���� ,�����ǰ����̿��߰������Ͱ���, ������ġ
		//�������ۿ� offset�� �޸��ؼ� �����Ҽ����ִ�.
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	//�ٻ���Ѱ� unbind ���߿�����..
	private void unbindVAO() {
		GL30.glBindVertexArray(0);//�Ƹ� �⺻���°� 0���̶� unbind�� �ǹ̸� ����
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
		buffer.flip();//write�� �б����뼳��
		return buffer;
	}
}

