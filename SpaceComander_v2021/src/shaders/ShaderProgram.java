
package shaders;

import Camera.Camera;
import IBO.IBO;
import Maths.Maths;
import QuadModel.Model;
import java.nio.FloatBuffer;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import static org.lwjgl.opengl.GL20.*;
import org.lwjgl.opengl.GL32;

public class ShaderProgram {
    
	public int programID;
	public int vertexShaderID;
	public int fragmentShaderID;
	private int location_transformationMatrix , location_projectionMatrix , location_viewMatrix , location_numberOfRows , location_offset , texTransparanci;  
        public int point0_ID,point1_ID,point2_ID,point3_ID;
        public  FloatBuffer matrixBuffer     = BufferUtils.createFloatBuffer(16);
        public  FloatBuffer projectionBuffer = BufferUtils.createFloatBuffer(16);       
        Matrix4f viewMatrix;
        Maths maths;        
        public ShaderProgram(Maths maths) {         
            
                this.maths = maths;
 		vertexShaderID   =  loadVertexShader(GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadFragmentShader(GL20.GL_FRAGMENT_SHADER);
                programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
 		bindAttribute(0, "position");
		bindAttribute(1, "textureCoords");
                GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
                loadUniformLocation();
        }
        public void loadUniformLocation(){
		location_transformationMatrix = getUniformLocation("transformationMatrix");
		location_projectionMatrix = getUniformLocation("projectionMatrix");
		location_viewMatrix = getUniformLocation("viewMatrix");
		location_numberOfRows = getUniformLocation("numberOfRows");
		location_offset = getUniformLocation("offset");
		texTransparanci = getUniformLocation("blendColor");
                point0_ID   = getUniformLocation("point0");
                point1_ID   = getUniformLocation("point1");
                point2_ID   = getUniformLocation("point2");
                point3_ID   = getUniformLocation("point3");        
        }
        public void loadTexTransparency(float alpha) {
		loadFloat(texTransparanci, alpha);
	}
        public void loadQuadVertex(Model model){
             loadVector(point0_ID , model.pointsList.get(0));
             loadVector(point1_ID , model.pointsList.get(1));
             loadVector(point2_ID , model.pointsList.get(2));
             loadVector(point3_ID , model.pointsList.get(3));
        }
	public void loadNumbetOfRows(int numberOfRows) {
		loadFloat(location_numberOfRows, numberOfRows);
	}
	public void loadOffset(float x , float y) {
		load2DVector(location_offset, new Vector2f(x,y));
	}
	public void loadTransformationMatrix(Matrix4f matrix)	{
		loadMatrix(location_transformationMatrix, matrix);
	}
	public void loadViewMatrix(Camera camera) {
	        viewMatrix =  maths.createViewMatrix(camera);
		loadMatrix(location_viewMatrix, viewMatrix);
	}
	public void loadProjectionMatrix(Matrix4f projection){
		projection.get(projectionBuffer);
		glUniformMatrix4fv(location_projectionMatrix, false, projectionBuffer);            
	}	
	public  int getUniformLocation(String uniformName) {
		return glGetUniformLocation(programID, uniformName);
	}
	public void start() {
		 glUseProgram(programID);
	}
	public void stop() {
		glUseProgram(0);
	}
	public  void loadFloat(int location , float value) {
		glUniform1f(location, value);
	}
	public  void load2DVector(int location , Vector2f vector){
		glUniform2f(location, vector.x, vector.y);
	}	
	public  void loadVector(int location , Vector3f vector){
		glUniform3f(location, vector.x, vector.y, vector.z);
	}
	public  void loodBoolean(int location , boolean value){
		float toLoad = 0;
		if(value) {
			toLoad = 1;
		}
		glUniform1f(location, toLoad);
	}
	public  void loadMatrix(int location , Matrix4f matrix){
		matrix.get(matrixBuffer);
		glUniformMatrix4fv(location, false, matrixBuffer);
	} 
	public  void bindAttribute(int attribute , String variableName){
		glBindAttribLocation(programID, attribute, variableName);
	}        
	public  int loadVertexShader(int type) {
		StringBuilder sheaderSource = new StringBuilder();		
		sheaderSource.append("#version 330 core                                                                              ").append("\n");
		sheaderSource.append("in vec3 position;                                                                              ").append("\n");
		sheaderSource.append("in vec2 textureCoords;                                                                         ").append("\n");
		sheaderSource.append("out vec2 pass_textureCoords;                                                                   ").append("\n");
		sheaderSource.append("uniform mat4 transformationMatrix;                                                             ").append("\n");
		sheaderSource.append("uniform mat4 projectionMatrix;                                                                 ").append("\n");
		sheaderSource.append("uniform mat4 viewMatrix;                                                                       ").append("\n");
		sheaderSource.append("uniform float numberOfRows;                                                                    ").append("\n");
		sheaderSource.append("uniform float blendColor;                                                                      ").append("\n");
		sheaderSource.append("uniform vec2 offset;                                                                           ").append("\n");
		sheaderSource.append("out float blend;                                                                               ").append("\n");
                sheaderSource.append("uniform vec3 point0;                                                                           ").append("\n");
                sheaderSource.append("uniform vec3 point1;                                                                           ").append("\n");
                sheaderSource.append("uniform vec3 point2;                                                                           ").append("\n");
                sheaderSource.append("uniform vec3 point3;                                                                           ").append("\n");
		sheaderSource.append("void main(void)                                                                                ").append("\n");
		sheaderSource.append("{                                                                                              ").append("\n");  
		sheaderSource.append("     blend =   blendColor;                                                                     ").append("\n");          
                sheaderSource.append("     vec3  pos =  position;                                                                    ").append("\n");  
                sheaderSource.append("                                                                                               ").append("\n");       
                sheaderSource.append("     if(gl_VertexID == 0)  {                                                                   ").append("\n");       
                sheaderSource.append("        pos = point0;                                                                          ").append("\n");       
                sheaderSource.append("     }                                                                                         ").append("\n"); 
                sheaderSource.append("     if(gl_VertexID == 1)  {                                                                   ").append("\n");       
                sheaderSource.append("        pos = point1;                                                                          ").append("\n");       
                sheaderSource.append("     }                                                                                         ").append("\n");  
                sheaderSource.append("     if(gl_VertexID == 2)  {                                                                   ").append("\n");       
                sheaderSource.append("        pos = point2;                                                                          ").append("\n");       
                sheaderSource.append("     }                                                                                         ").append("\n");  
                sheaderSource.append("     if(gl_VertexID == 3)  {                                                                   ").append("\n");       
                sheaderSource.append("        pos = point3;                                                                          ").append("\n");       
                sheaderSource.append("     }                                                                                         ").append("\n");                    
		sheaderSource.append("     gl_Position = projectionMatrix*viewMatrix*transformationMatrix*vec4(pos,1.0);             ").append("\n");          
		sheaderSource.append("     pass_textureCoords = (textureCoords/numberOfRows) + offset;                               ").append("\n");
		sheaderSource.append("}                                                                                              ");
                int shaderID = compileShader(type , sheaderSource);
	return shaderID;		
	}
        public   int loadFragmentShader(int type) {
		StringBuilder sheaderSource = new StringBuilder();
		sheaderSource.append("#version 330 core                                                                               ").append("\n");
		sheaderSource.append("in vec2 pass_textureCoords;                                                                     ").append("\n");
	        sheaderSource.append("in float  blend;                                                                                ").append("\n");
		sheaderSource.append("out vec4 out_Color;                                                                             ").append("\n");
		sheaderSource.append("uniform sampler2D textureSampler;                                                               ").append("\n");	
		sheaderSource.append("void main(void)                                                                                 ").append("\n");
		sheaderSource.append("{                                                                                               ").append("\n");
                sheaderSource.append("     vec2 textCoord = pass_textureCoords;                                                       ").append("\n");
                sheaderSource.append("                                                                                                ").append("\n");
                sheaderSource.append("     vec4 color1 = vec4(0.0f, 0.0f, 0.0f, 0.0f);                                                ").append("\n");
                sheaderSource.append("     vec4 color2 = texture(textureSampler,pass_textureCoords);                                  ").append("\n");
                sheaderSource.append("                                                                                                ").append("\n");
                sheaderSource.append("     out_Color = mix(color1 , color2 , blend);                                                  ").append("\n");
                sheaderSource.append("}                                                                                               ");
                int shaderID = compileShader(type , sheaderSource);    
	return shaderID;		
	}        
	public  int compileShader(int type , StringBuilder sheaderSource) {
		int shaderID = glCreateShader(type);
		glShaderSource(shaderID, sheaderSource);
		glCompileShader(shaderID);
		if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == 0){
			System.exit(0);
		}
	return shaderID;	
	} 
	public void cleanUP() {
		stop();
		glDetachShader(programID, vertexShaderID);
		glDetachShader(programID, fragmentShaderID);
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);
		glDeleteProgram(programID);
	} 
    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }    
}
