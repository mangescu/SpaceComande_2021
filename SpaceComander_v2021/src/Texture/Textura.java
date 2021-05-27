
package Texture;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Random;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glGetFloat;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_BGRA;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL14.GL_GENERATE_MIPMAP;
import static org.lwjgl.opengl.GL14.GL_TEXTURE_LOD_BIAS;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Textura {
    
    public int textureID;
    public int rows;
    public float transparency = 1.0f;
    public   ByteBuffer buffer;
    private int handle = -1;
    public BufferedImage image;
    int[] pixels;

    public Textura(final InputStream is , int rows , float transparency , boolean createBuffer) {
        this.rows = rows;
        this.transparency = transparency;
        image = loadImage(is);
        pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth()); 
    }
    public void createDataBuffer() {
        pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
        buffer = BufferUtils.createByteBuffer(pixels.length * 4);
        buffer.asIntBuffer().put(pixels);
        buffer.rewind();
        pixels = null; 
    }

    private BufferedImage loadImage(final InputStream is) {
        BufferedImage image;
        try {
                image = ImageIO.read(is);
                is.close();
        } catch (final IOException e) 
               {
                   throw new RuntimeException(e);
               }
        return image;
    }

    public final void glInit(boolean memoryFlush) {
        handle = glGenTextures();
        textureID = handle;
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, handle);
      	glGenerateMipmap(GL_TEXTURE_2D);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);        
    //    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    //    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);      
        glTexParameteri(GL_TEXTURE_2D, GL_GENERATE_MIPMAP, GL_TRUE);
        loadGLImageBuffer();
        if(memoryFlush)    {
            image.flush();
            image = null;  
            buffer.clear();
            buffer = null;
        }
    }
    public void loadGLImageBuffer(){
         glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, this.image.getWidth(), this.image.getHeight(), 0, GL_BGRA, GL_UNSIGNED_BYTE, buffer);
    }
    public final void glBind() {
        glBindTexture(GL_TEXTURE_2D, textureID);
    }
    public final void glUnbindBind() {
        glUnbindBind();
    }
    public final void glDispose() {
       glDeleteTextures(textureID);
       GL11.glFinish();
    }
}
