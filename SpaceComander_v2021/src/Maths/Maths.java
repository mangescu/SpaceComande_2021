
package Maths;

import Border.BorderVectData;
import Camera.Camera;
import Planet.Planet;
import Player.Player;
import java.util.ArrayList;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import shaders.ShaderProgram;
import  java.awt.geom.Line2D;
import org.joml.AABBd;
import org.joml.Intersectiond;
import org.joml.Intersectionf;
import org.joml.Planef;
import org.joml.Rayd;
import org.joml.Vector3d;
import org.joml.Vector3dc;


public class Maths {
    
    Matrix4f matrix     = new Matrix4f();
    Matrix4f viewMatrix = new Matrix4f();
    Camera camera;

    ArrayList<Player>    playerList;
    
    ShaderProgram shaderProgram;
     
        boolean ok,done;
        private float w1 , w2 , top , top_1 , top_2 , top_3   , bottom , bottom_1 , bottom_2;    
        private float pointLineD , point_pointD , point_pointA;
        private float deltaAB , deltaBC , deltaCA;
    private Vector3f P  = new  Vector3f();
    private float a,b,c,d;
    public Vector3d   intersectionPoint  = new Vector3d();
    private Vector4f  planeEcuationParam = new Vector4f(); 
                 
    public Maths(ArrayList<Player>    playerList , Camera camera){
          this.playerList = playerList;
          this.camera = camera;
    }
    
	public  Matrix4f createTransformationMatrix(Vector3f translation , Vector3f quadRotation , Vector3f scale){
              
                matrix.identity();
                matrix.translate(translation.x, translation.y, translation.z)
                      .rotate((float) Math.toRadians(quadRotation.x),1,0,0)
                      .rotate((float) Math.toRadians(quadRotation.y),0,1,0)
                      .rotate((float) Math.toRadians(quadRotation.z),0,0,1)
                      .scale(scale.x, scale.y, scale.z);
       
	 return matrix;   
	}
	public  Matrix4f createViewMatrix(Camera camera){

                viewMatrix.identity();
                viewMatrix.rotateX((float) Math.toRadians(camera.getPitch()),viewMatrix)
                          .rotateY((float) Math.toRadians(camera.getYaw()),viewMatrix)
                          .translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z,viewMatrix);
                
	    return viewMatrix;
	}
    public float calcPointPointAngle(Vector2f A , Vector2f B) {
        point_pointA = (float) Math.toDegrees(Math.atan2(B.x - A.x , B.y - A.y));
      if(point_pointA < 0) {
         return  360 + point_pointA;
      } else {        
        return point_pointA;
      }
    }  
    public float calcPointPointAngle(Vector3f A , Vector3f B) {
        point_pointA = (float) Math.toDegrees(Math.atan2(B.x - A.x , B.y - A.y));
      if(point_pointA < 0) {
         return  360 + point_pointA;
      } else {        
        return point_pointA;
      }
    }
    public float calcPointPointAngle(float A_x , float A_y , float B_x , float B_y) {
        point_pointA = (float) Math.toDegrees(Math.atan2(B_x - A_x , B_y - A_y));
      if(point_pointA < 0) {
         return  360 + point_pointA;
      } else {        
        return point_pointA;
      }
    }     
    public float calcPointsPointDistance(Vector2f A , Vector2f B) {
        point_pointD = (float) (Math.sqrt(Math.pow(A.x-B.x, 2) + Math.pow(A.y-B.y, 2)));
        return point_pointD;
    }  
    public float calcPointsPointDistance(float x , float y , Vector2f B) {
        point_pointD = (float) (Math.sqrt(Math.pow(x-B.x, 2) + Math.pow(y-B.y, 2)));
        return point_pointD;
    } 
    public float calcPointsPointDistance(float x1 , float y1 , float x2 , float y2) {
        point_pointD = (float) (Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2)));
        return point_pointD;
    }     
    public float calcPointsPointDistance(Vector3f A , Vector3f B) {
        point_pointD = (float) (Math.sqrt(Math.pow(A.x-B.x, 2) + Math.pow(A.y-B.y, 2)));
        return point_pointD;
    }    
    public float calcPointLineDistance(Vector2f A , Vector2f B , Vector2f P) {
        top_1 = (P.x-A.x)*(A.y-B.y);
        top_2 = (P.y-A.y)*(B.x-A.x);
        pointLineD = (float) ((Math.abs(top_1 + top_2))/Math.sqrt(Math.pow(A.y-B.y, 2) + Math.pow(B.x-A.x, 2)));
        return pointLineD;
    }
    public boolean checkIfPoligonContainsPoint(ArrayList<Vector2f>  poligonPoints , float px , float py){ 
        ok = false;       
        for(int i=1 ; i< poligonPoints.size() - 2 ; i++)      
        {
          done = check_If_Triangle_Conteins_Point_1(poligonPoints.get(0).x,poligonPoints.get(0).y , poligonPoints.get(i).x , poligonPoints.get(i).y , poligonPoints.get(i+1).x , poligonPoints.get(i+1).y , px , py);
          if(done) {
            ok = true;
            i = poligonPoints.size() - 2;
          }
        }
    return ok;
    }    
    public boolean check_If_Triangle_Conteins_Point_1(float a_x , float a_y , float b_x , float b_y , float c_x , float c_y , float p_x , float p_y) {
        ok = true;
    //__W1_______________________________________//    
        top_1 = a_x * (c_y - a_y);
        top_2 = (p_y - a_y) * (c_x - a_x);
        top_3 = p_x * (c_y - a_y);
        top = top_1 + top_2 - top_3;
        bottom_1 = (b_y - a_y) * (c_x - a_x);
        bottom_2 = (b_x - a_x) * (c_y - a_y);
        bottom = bottom_1 - bottom_2;
       
        w1 = top / bottom;
    //__W2_________________________________________//    
       bottom_1 = (c_y - a_y);
       top_1 = w1 *(b_y - a_y);
       w2 = (p_y - a_y - top_1) / bottom_1;
    //____________________________________________//
       if(w1 < 0 || w2 < 0 || (w1 + w2) > 1) {
         ok = false;
       }
       return ok;  
    }
    public boolean checkSegmentColision(Vector3f A,Vector3f B , Vector3f C,Vector3f D)
    {
        float  K1 = B.y -  A.y;
        float  H1 = A.x - B.x;
        float  KH1 = (K1 * A.x) + (H1 * A.y);

        float K2 = D.y - C.y;
        float H2 = C.x - D.x;
        float  KH2 = (K2 * C.x) + (H2 * C.y);

        float  delta = K1 * H2 - K2 * H1;

        if(delta != 0) {
            P.x = (H2 * KH1 - H1 * KH2)/delta;
            P.y = (K1 * KH2 - K2 * KH1)/delta;
        } else {
            return false;
        }
        if((P.x > A.x && P.x < B.x) || ((P.x < A.x && P.x > B.x))) {
            if((P.x > C.x && P.x < D.x) || ((P.x < C.x && P.x > D.x))) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public float  calcPointLineDistance(float a_x,float a_y   ,   float b_x,float b_y    ,    float c_x , float c_y ) {
         float top = Math.abs( (c_x - a_x)*(a_y - b_y)  +  (c_y - a_y)*(b_x - a_x));
         float bottom = (float) Math.sqrt(Math.pow(a_y - b_y , 2) + Math.pow(b_x - a_x , 2));
         return top/bottom;
    }
    public boolean getPointLinePosition(float a_x,float a_y   ,   float b_x,float b_y    ,    float c_x , float c_y ) {
        if(((c_x-a_x)*(a_y-b_y) + (c_y-a_y)*(b_x-a_x)) > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean  testPlaneRayIntersection(Vector3f pointA , Vector3f  pointB , Vector3f  PointC  ,      Vector3f camPos , Vector3f endPos ) {   
        Planef.equationFromPoints​(pointA.x,pointA.y,pointA.z,pointB.x,pointB.y,pointB.z,PointC.x,PointC.y,PointC.z,planeEcuationParam);
        Boolean bool = Intersectiond.intersectLineSegmentPlane(camPos.x,camPos.y,camPos.z,endPos.x,endPos.y,endPos.z,planeEcuationParam.x,planeEcuationParam.y,planeEcuationParam.z,planeEcuationParam.w,intersectionPoint);
        if(bool)  {
            Boolean test = true;
                  test  = Intersectionf.testPointInTriangle((float)intersectionPoint.x,(float) intersectionPoint.y,(float) intersectionPoint.z, (float) pointA.x,(float) pointA.y,(float) pointA.z,(float) pointB.x,(float) pointB.y,(float) pointB.z,(float) PointC.x,(float) PointC.y ,(float) PointC.z);
            if(test) {
                return true;
            } else {
               return false;
            }
        } else {
             return false;
        }
    }
}
