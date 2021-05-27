
package Quad;

import IBO.IBO;
import Texture.Textura;
import java.util.ArrayList;
import org.joml.Vector3f;

public class Model {

	public IBO ibo;
	public Textura texture;   
	public int INDEX_T;
	public int INDEX_TEMP;
	public int ANIMATION_COUNT=0;
	public int ANIMATION_RESET_V = 2;
	public int ANIMATION_DELETE_INDEX = 0;
	public int ANIMATION_FRAME_NUMBER = 10;
        
	public Vector3f position;
	public Vector3f quadRotation;

	public Vector3f scale;  
        
        public ArrayList<Vector3f>  pointsList  =  new ArrayList<Vector3f>();
        Vector3f vec;
        
	public Model(IBO ibo , Textura texture,int INDEX_T , Vector3f position,  Vector3f quadRotation, Vector3f scale) {
		this.ibo = ibo;
		this.texture = texture;
		this.INDEX_T = INDEX_T;
		this.INDEX_TEMP = INDEX_T;
		this.position = position;
		this.quadRotation = quadRotation;
		this.scale = scale;  
                
           loadPointsData();
	}
    public void loadPointsData(){
            for(int i=0 ; i < ibo.dataIBO.pointsList.size() ; i++)  {
               Vector3f tempVec =  ibo.dataIBO.pointsList.get(i);
               vec = new Vector3f(tempVec.x , tempVec.y , tempVec.z);
               pointsList.add(vec);
            }    
    }        
    public float getTextureXOffset() {
        int column = INDEX_T % texture.rows;
        return (float) column / (float) (texture.rows);
    }
    public float getTextureYOffset() {
        int row = INDEX_T / texture.rows;
        return (float) row / (float) texture.rows;
    }
    public void setPosition(Vector3f position) {
        this.position = position;
    }
    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
    public Vector3f getPosition() {
        return position;
    }
    public Vector3f getScale() {
        return scale;
    }   
}
