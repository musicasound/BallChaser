package Physics;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

//scale,ry,translation�� Transform�� ������ �����Ͱ���!
public class Maths {
	//���� (T * R * S) ... * position
	public static Matrix4f createTransformation2DMatrix(Vector2f translation,float rz,float scale){
		Matrix4f matrix=new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(rz),new Vector3f(0,0,1),matrix,matrix);
		Matrix4f.scale(new Vector3f(scale,scale,scale), matrix, matrix);
		return matrix;
	}
}
