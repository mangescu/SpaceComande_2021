
package Input;

import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import org.lwjgl.glfw.GLFWKeyCallback;

public class KeyCallBack extends GLFWKeyCallback{
    public static boolean[] keys = new boolean[65535];       
    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if(action == GLFW_PRESS){
          keys[key] = true;
        } 
        if(action == GLFW_RELEASE) {
            keys[key] = false;
        }
    }
}
