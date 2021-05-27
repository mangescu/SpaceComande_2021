
package IBO;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.*;
import org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.*;
import org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

public class IBO {
    
   public int iboID;
   public int vertexCount;

   public int vaoID;
   public int vboID;

	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();	
	private List<Integer> textures = new ArrayList<Integer>();
	FloatBuffer	bufferFloatV , bufferFloatT;
	IntBuffer bufferInt;
	public FloatBuffer bufferVertices;
	public FloatBuffer bufferTextCoords;
	public IntBuffer   bufferIndices;
	public int V_ID;
	public int I_ID;
	public int T_ID;

Vector3f vec;
public IBO_Data   dataIBO;

    public IBO(IBO_Data dataIBO){
        this.dataIBO = dataIBO;
        loadIBO();
    }    

    public void loadIBO() {
   
       int vaoID = createIBO();
       this.vaoID = vaoID;
       vaos.add(vaoID);
       bindIndicesBuffer(dataIBO.indices);
       storeVerticesInAttributeList(0 ,3, dataIBO.vertices);
       storeTextCoordsInAttributeList(1 ,2, dataIBO.textureCoords);
       unbindVAO();

	this.iboID = vaoID;
	this.vertexCount = dataIBO.indices.length;       
    }
    public int createIBO()
    {
		int vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		return vaoID;
    }
	public void  bindIndicesBuffer(int[] indices)
	{
		int vboID = glGenBuffers();
		this.I_ID = vboID;
		vbos.add(vboID);
		this.vboID = vboID;
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER , vboID);
		bufferIndices = storeDataInIntBuffer(indices);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER , bufferIndices , GL_STATIC_DRAW);
	}
	private void storeVerticesInAttributeList(int attributeNumber ,int coordinateSize , float[] data)
	{
		int vboID = glGenBuffers();
		this.V_ID = vboID;
		vbos.add(vboID);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		bufferVertices = storeDataInVFloatBuffer(data);
		glBufferData(GL_ARRAY_BUFFER , bufferVertices , GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNumber , coordinateSize , GL11.GL_FLOAT , false , 0 , 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	private void storeTextCoordsInAttributeList(int attributeNumber ,int coordinateSize , float[] data) {
	int vboID = glGenBuffers();
	this.T_ID = vboID;
	textures.add(T_ID);
	glBindBuffer(GL_ARRAY_BUFFER, vboID);
	bufferTextCoords = storeDataInTFloatBuffer(data);
	glBufferData(GL_ARRAY_BUFFER , bufferTextCoords , GL_STATIC_DRAW);
	glVertexAttribPointer(attributeNumber , coordinateSize , GL11.GL_FLOAT , false , 0 , 0);
	glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
    private IntBuffer storeDataInIntBuffer(int[] data) {
              bufferInt = BufferUtils.createIntBuffer(data.length);
	          bufferInt.put(data);
	          bufferInt.flip();
	return bufferInt;
    }
    private FloatBuffer storeDataInVFloatBuffer(float[] data) {

		bufferFloatV = BufferUtils.createFloatBuffer(data.length);
		bufferFloatV.put(data);
		bufferFloatV.flip();
	return bufferFloatV;
    }
	private FloatBuffer storeDataInTFloatBuffer(float[] data) {

		bufferFloatT = BufferUtils.createFloatBuffer(data.length);
		bufferFloatT.put(data);
		bufferFloatT.flip();
		return bufferFloatT;
	}
	private void unbindVAO() {
        glBindVertexArray(0);
	}

    public void cleanUp() {

	for(int vao:vaos){
            glDeleteVertexArrays(vao);
	}
	for(int ibo:vbos) {
            glDeleteBuffers(ibo);
	}
	for(int texture:textures) {
            glDeleteTextures(texture);
	}
    }


    public void deleteTexture()
    {
        bufferFloatT.clear();
        bufferTextCoords.clear();
        glDeleteTextures(T_ID);
    }
    public void loadTextureInGL() {
        storeTextCoordsInAttributeList(1 ,2, dataIBO.textureCoords);
    }



}
