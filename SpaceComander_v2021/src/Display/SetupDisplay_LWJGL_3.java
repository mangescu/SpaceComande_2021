
package Display;

import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWVidMode;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class SetupDisplay_LWJGL_3 {
 
    public long window;
    
    public int squareEdge = 690;
    
    int WIN_HEIGHT = squareEdge;
    int WIN_WIDTH  = squareEdge;
    
    public void initWindow(){ 
        if(!glfwInit()){
            System.out.println("faild to initialize");
        }
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);

        if(!System.getProperty("os.name").startsWith("Win"))
        {
           glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
           glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
           glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE); 
        }  
        window = glfwCreateWindow(WIN_WIDTH, WIN_HEIGHT,"window", 0,0);  
        if(window == 0 ) {
           System.out.println("faild to create window");
        }        
        GLFWVidMode  videoMode  = glfwGetVideoMode(glfwGetPrimaryMonitor());
        
        glfwSetWindowPos(window, (videoMode.width() - squareEdge) / 2,(videoMode.height() - squareEdge) / 2);     
        glfwShowWindow(window);     
        glfwMakeContextCurrent(window);        
        createCapabilities();    
    }
    public long getWindow() {
        return window;
    }
    public int getWIN_HEIGHT() {
        return WIN_HEIGHT;
    }
    public int getWIN_WIDTH() {
        return WIN_WIDTH;
    }
    
}

