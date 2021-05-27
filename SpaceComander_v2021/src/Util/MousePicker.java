
package Util;

import Camera.Camera;
import Display.SetupDisplay_LWJGL_3;
import Maths.Maths;
import java.nio.DoubleBuffer;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

public class MousePicker {
	public  Vector3f curentRay,nearPlaneRay,farPlaneRay;
	private Vector3f menuSelectRay;        
	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private Camera camera;
        Maths maths;
        SetupDisplay_LWJGL_3   display;
        DoubleBuffer posX =  BufferUtils.createDoubleBuffer(1);
        DoubleBuffer posY =  BufferUtils.createDoubleBuffer(1);
        float delta;
	public MousePicker(SetupDisplay_LWJGL_3   display , Camera camera , Matrix4f projection , Maths maths) {
		this.camera = camera;
		this.projectionMatrix = projection;
                this.maths = maths;
                this.display = display;
		this.viewMatrix = maths.createViewMatrix(camera);                
                nearPlaneRay = new Vector3f();
                farPlaneRay  = new Vector3f();
	}
	public Vector3f getCurentRay() { 
		return curentRay;
	}
	public Vector3f getMCurentRay() { 
		return menuSelectRay;
	}        
	public Vector3f update(boolean input , double x , double y) {  
		viewMatrix = maths.createViewMatrix(camera);
		curentRay = calculateMouseRay(input , x , y);   
                getIntersectionPoint();
        return curentRay;        
	}
	private Vector3f calculateMouseRay(boolean input ,double  x , double y) {            
            posX.clear();
            posY.clear();            
            float mouseX , mouseY;
            if(!input)  {               
                 glfwGetCursorPos(display.getWindow(), posX, posY);
                 mouseX = (float) posX.get();
                 mouseY = (float) posY.get();             
            }  else {
                        mouseX = (float) x;
                        mouseY = (float) y;           
                    }
		Vector2f normalizedCoords = getNormalizedDeviceCoords(mouseX , mouseY);
		Vector4f clipCoords = new Vector4f(normalizedCoords.x , normalizedCoords.y , -1f , 1f);
		Vector4f eyeCoords = toEyeCoords(clipCoords);
		Vector3f worldRay = toWorldCoords(eyeCoords);
	return worldRay;
	}
	private Vector4f toEyeCoords(Vector4f clipCoords) {
		Matrix4f invertedProjection  =new Matrix4f();
                projectionMatrix.invert(invertedProjection);
		Vector4f eyeCoords = invertedProjection.transform(clipCoords);
	return new Vector4f(eyeCoords.x , eyeCoords.y , -1f , 0f);
	}
        
	private Vector3f toWorldCoords(Vector4f eyeCoords) {
		Matrix4f invertedView = viewMatrix.invert();       
		Vector4f rayWorld = invertedView.transform(eyeCoords);    
		Vector3f mouseRay = new Vector3f(rayWorld.x , rayWorld.y , rayWorld.z);        
		mouseRay.normalize();
		return mouseRay;
	}
	private Vector2f getNormalizedDeviceCoords(float mouseX , float mouseY) {
		float x = (2f * mouseX) / display.getWIN_WIDTH()  - 1;
		float y = 1.0f - (2f * mouseY) / display.getWIN_HEIGHT();
		return new Vector2f(x,y);
	}
        public void getIntersectionPoint(){  
              float deltaM = - (0.25f / curentRay.z);
              menuSelectRay = new Vector3f();
              menuSelectRay.x = camera.getPosition().x + curentRay.x  * deltaM;
              menuSelectRay.y = camera.getPosition().y + curentRay.y  * deltaM; 
              menuSelectRay.z = camera.getPosition().z + curentRay.z  * deltaM; 
              delta =  -camera.getPosition().z / curentRay.z;
              curentRay.x = camera.getPosition().x + (curentRay.x  * delta);
              curentRay.y = camera.getPosition().y + (curentRay.y  * delta);
              curentRay.z =0; 
        }
}
