package fontRendering;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Shaders.ShaderProgram;


public class FontShader extends ShaderProgram{

	private static final String VERTEX_FILE = "src/fontRendering/fontVertex.txt";
	private static final String FRAGMENT_FILE = "src/fontRendering/fontFragment.txt";
	
	private int location_colour;
	private int location_translation;
	private int location_width;
	private int location_edge;
	private int location_borderColour;
	private int location_borderWidth;
	private int location_borderEdge;
	private int location_shadowOffset;
	
	public FontShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_colour = super.getUniformLocation("colour");
		location_translation = super.getUniformLocation("translation");
		location_width = super.getUniformLocation("width");
		location_edge = super.getUniformLocation("edge");
		location_borderWidth = super.getUniformLocation("borderWidth");
		location_borderEdge = super.getUniformLocation("borderEdge");
		location_borderColour = super.getUniformLocation("borderColour");
		location_shadowOffset = super.getUniformLocation("shadowOffset");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	
	protected void loadColour(Vector3f colour) {
		super.loadVector(location_colour, colour);
	}
	protected void loadBorderColour(Vector3f borderColour) {
		super.loadVector(location_borderColour, borderColour);
	}
	
	protected void loadTranslation(Vector2f translation) {
		super.load2DVector(location_translation, translation);
	}
	protected void loadShadowOffset(Vector2f shadowOffset) {
		super.load2DVector(location_shadowOffset, shadowOffset);
	}

	protected void loadWidth(float width) {
		super.loadFloat(location_width, width);
	}
	protected void loadEdge(float edge) {
		super.loadFloat(location_edge, edge);
	}
	protected void loadBoaderWidth(float width) {
		super.loadFloat(location_borderWidth, width);
	}
	protected void loadBoaderEdge(float edge) {
		super.loadFloat(location_borderEdge, edge);
	}
	
	
}
