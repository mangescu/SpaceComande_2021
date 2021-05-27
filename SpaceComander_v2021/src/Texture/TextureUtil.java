
package Texture;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TextureUtil { 
    public Textura ringTexture , mapTex  , solarSystemTex_max , curs , shipTex_max ,shipTex_med , shipTex_min ,  bulletTex , explosionTex , shieldTex,phaserTex , textChart_ON,textChart_OFF , board , phasetEmiter , galaxyTex , testPic;
    public void loadTextures () {
    
        try {
            String curentDirectory = new File("").getAbsoluteFile().toString().concat("/");
            ringTexture =new Textura(new FileInputStream(new File(curentDirectory+"res/textures/ring_texture.png")),6 , 1,true);
            ringTexture.createDataBuffer();
            ringTexture.glInit(true);
            textChart_ON =new Textura(new FileInputStream(new File(curentDirectory+"res/textures/textChart_ON.png")),10, 1,true);
            textChart_ON.createDataBuffer();
            textChart_ON.glInit(true);
            textChart_OFF =new Textura(new FileInputStream(new File(curentDirectory+"res/textures/textChart_OFF.png")),10, 1,true);
            textChart_OFF.createDataBuffer();
            textChart_OFF.glInit(true);            
            mapTex =new Textura(new FileInputStream(new File(curentDirectory+"res/textures/map_512.png")),1, 1,true);
            mapTex.createDataBuffer();
            mapTex.glInit(true);        
            galaxyTex =new Textura(new FileInputStream(new File(curentDirectory+"res/textures/galaxy.png")),1, 0.525f,true);
            galaxyTex.createDataBuffer();
            galaxyTex.glInit(true);            
            solarSystemTex_max =new Textura(new FileInputStream(new File(curentDirectory+"res/textures/solar_system_max.png")),10, 1f,true);
            solarSystemTex_max.createDataBuffer();
            solarSystemTex_max.glInit(true);            
            shipTex_max =new Textura(new FileInputStream(new File(curentDirectory+"res/textures/ship_max_F.png")),10, 1,true);
            shipTex_max.createDataBuffer();
            shipTex_max.glInit(true);           
            board =new Textura(new FileInputStream(new File(curentDirectory+"res/textures/board.png")),6, 1,true);
            board.createDataBuffer();
            board.glInit(true);           
            bulletTex =new Textura(new FileInputStream(new File(curentDirectory+"res/textures/bullet_animated.png")),5, 1,true);
            bulletTex.createDataBuffer();
            bulletTex.glInit(true);            
            explosionTex =new Textura(new FileInputStream(new File(curentDirectory+"res/textures/explsion_animation.png")),10, 1,true);
            explosionTex.createDataBuffer();
            explosionTex.glInit(true);           
            shieldTex =new Textura(new FileInputStream(new File(curentDirectory+"res/textures/shield.png")),10, 1,true);
            shieldTex.createDataBuffer();
            shieldTex.glInit(true);           
            phaserTex =new Textura(new FileInputStream(new File(curentDirectory+"res/textures/phaser.png")),10, 1,true);
            phaserTex.createDataBuffer();
            phaserTex.glInit(true);            
            phasetEmiter =new Textura(new FileInputStream(new File(curentDirectory+"res/textures/emiter.png")),10, 1,true);
            phasetEmiter.createDataBuffer();
            phasetEmiter.glInit(true);
            System.gc();   
        } catch (FileNotFoundException ex) 
               {
                  System.out.println("texture error");
               }
    }
    public void loadTextura(){
           testPic.createDataBuffer(); 
    }
    public void clearGLText() {
       testPic.glDispose();
    }
}
