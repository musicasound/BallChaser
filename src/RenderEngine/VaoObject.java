package RenderEngine;

public class VaoObject {

		private int vaoID;
		private int vertexCount;
		public VaoObject(int vaoID, int vertexCount) {
			
			this.vaoID = vaoID;
			this.vertexCount = vertexCount;
		}
		public int getVaoID() {
			return vaoID;
		}

		public int getVertexCount() {
			return vertexCount;
		}
}
