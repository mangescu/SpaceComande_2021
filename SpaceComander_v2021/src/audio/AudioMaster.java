
package audio;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import static org.lwjgl.openal.ALC10.ALC_FALSE;
import static org.lwjgl.openal.ALC10.ALC_REFRESH;
import static org.lwjgl.openal.ALC10.ALC_SYNC;
import org.lwjgl.openal.ALCCapabilities;
import static org.lwjgl.openal.EXTEfx.ALC_MAX_AUXILIARY_SENDS;



public class AudioMaster {

    private static List<Integer> buffers = new ArrayList<Integer>();
    
    public static void init(){
     
        long device = ALC10.alcOpenDevice((ByteBuffer)null);
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        IntBuffer contextAttribList = BufferUtils.createIntBuffer(16);
        contextAttribList.put(ALC_REFRESH);
        contextAttribList.put(60);
        contextAttribList.put(ALC_SYNC);
        contextAttribList.put(ALC_FALSE);
        contextAttribList.put(ALC_MAX_AUXILIARY_SENDS);
        contextAttribList.put(2);
        contextAttribList.put(0);
        contextAttribList.flip();
        
        long newContext = ALC10.alcCreateContext(device, contextAttribList);
        
        if(!ALC10.alcMakeContextCurrent(newContext)) {
            System.out.println("Failed to make context current");
        }        
        AL.createCapabilities(deviceCaps);
    }
    public static void setListenerData(float x,float y,float z){
       AL10.alListener3f(AL10.AL_POSITION, x, y, z);
       AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
    }
    
    public static int loadSound(String  file) throws IOException{
      int buffer = AL10.alGenBuffers();
      buffers.add(buffer);
      File filePath = new File(file);
      WaveData   waveData = WaveData.create(Files.readAllBytes(filePath.toPath()));
      AL10.alBufferData(buffer, waveData.format , waveData.data, waveData.samplerate);
      waveData.dispose();
      return buffer;
    }
    public static void cleanUP(){      
        for(int buffer : buffers) {
          ALC10.alcCloseDevice(buffer);
          AL10.alDeleteBuffers(buffer);
          ALC10.alcDestroyContext(buffer);
        }

    }

}
