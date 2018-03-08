package Shaders;
 
import org.lwjgl.util.vector.Matrix4f;
 

public class Shader2D extends ShaderProgram{
     
    private static final String VERTEX_FILE = "src/Shaders/VertexShader.txt";
    private static final String FRAGMENT_FILE = "src/Shaders/FragmentShader.txt";
     
    private int location_transformationMatrix;
    public Shader2D() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
     
    public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }
 
    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");   
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
     
}
