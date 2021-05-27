
package Border;

import java.util.ArrayList;
import org.joml.Vector3f;

public class BorderVectData {
   public boolean isPinVector;  
   public Vector3f  borderPVector;
   public float ring_radius;
   public int  borderType = 1;
 
   public BorderVectData()    {
       borderPVector = new Vector3f();
   }   
   public BorderVectData(Vector3f  borderPVector , float ring_radius,int borderType)   {
       this.borderPVector = borderPVector;
       this.ring_radius = ring_radius;
       this.borderType = borderType;
   }
    
    
}
