
package IBO;

import java.util.ArrayList;
import org.joml.Vector3f;


public class IBO_Data {

    float[] vertices;
    int[] indices;
    float[] textureCoords;
  
    int mod;
    
  public ArrayList<Vector3f>  pointsList = new ArrayList<Vector3f>();
  Vector3f point;
  public IBO_Data(){}             
  public IBO_Data(Vector3f pozition , float width , float height , int mod){
    this.mod = mod;
    if(mod == 0) {
        initData_quad(pozition , width , height);
    }
    if(mod == 1) {
        initData_triangle(pozition , width , height);
    }
  }
  public void initData_triangle(Vector3f pozition , float width , float height) {
      float[] vertices = {
             -width+pozition.x ,  height+pozition.y , pozition.z,
              pozition.x       , -height+pozition.y , pozition.z,
              pozition.x       , -height+pozition.y , pozition.z,
      };
      point = new Vector3f(-width+pozition.x , height+pozition.y , pozition.z);
      pointsList.add(point);
      point = new Vector3f(-width+pozition.x , -height+pozition.y , pozition.z);
      pointsList.add(point);
      point = new Vector3f(width+pozition.x , -height+pozition.y , pozition.z);
      pointsList.add(point);
      point = new Vector3f(width+pozition.x , height+pozition.y , pozition.z);
      pointsList.add(point);       
      int[] indices = {
           0,1,2
      };
      float[] textureCoords = {
              0,0,
              0,1,
              1,1,
              1,0
      };
      this.vertices = vertices;
      this.indices = indices;
      this.textureCoords = textureCoords;

  }  
  public void initData_quad(Vector3f pozition , float width , float height) {
      float[] vertices = {
              -width+pozition.x,  height+pozition.y, pozition.z,
              -width+pozition.x, -height+pozition.y, pozition.z,
               width+pozition.x, -height+pozition.y, pozition.z,
               width+pozition.x,  height+pozition.y, pozition.z
      };
      pointsList.add(new Vector3f(-width+pozition.x , height+pozition.y , pozition.z));
      pointsList.add(new Vector3f(-width+pozition.x , -height+pozition.y , pozition.z));
      pointsList.add(new Vector3f(width+pozition.x , -height+pozition.y , pozition.z));
      pointsList.add(new Vector3f(width+pozition.x , height+pozition.y , pozition.z));      
      int[] indices = {
              0,1,3,2
      };
      float[] textureCoords = {
              0,0,
              0,1,
              1,1,
              1,0
      };
      this.vertices = vertices;
      this.indices = indices;
      this.textureCoords = textureCoords;
  }
    public float[] getVertices() {
        return vertices;
    }
    public int[] getIndices() {
        return indices;
    }
    public float[] getTextureCoords() {
        return textureCoords;
    }
 
}
