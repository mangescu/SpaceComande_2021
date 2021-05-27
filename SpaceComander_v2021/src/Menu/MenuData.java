
package Menu;

import Camera.Camera;
import Campaign.GameSetings;
import Display.SetupDisplay_LWJGL_3;
import IBO.IBO;
import Planet.Planet;
import Player.Player;
import Sun.Sun;
import TextUtil.TextData;
import Texture.Textura;
import Texture.TextureUtil;
import java.util.ArrayList;
import spacecomander.Quad;
import spacecomander.QuadData;
import spacecomander.QuadUtil;
import spacecomander.SpaceComander;


public class MenuData {
    
    public ArrayList<Quad>  menuPoliList;  
    public MenuPoligons menuPoligons;
    TextureUtil textureUtil; 
    public Textura curentTexture;  
    float planet_stacer , delta;
    QuadUtil quadUtil;  
    public float ship_select_delta = 0;
    public float coloniDelta = 0;
    SetupDisplay_LWJGL_3   display;
     QuadData  quadData; 
     Camera camera;
     
     Quad poligon;
     IBO ibo;
     
     GameSetings  gameSetings;
     
     TextData  textData;
    public MenuData(ArrayList<Quad>  menuPoliList , IBO ibo, TextureUtil textureUtil , QuadUtil quadUtil , SetupDisplay_LWJGL_3   display ,  QuadData  quadData,Camera camera,GameSetings  gameSetings) {
        this.menuPoliList = menuPoliList;
        this.textureUtil = textureUtil;
        this.quadUtil = quadUtil;
        this.display = display;
        this.ibo = ibo;
        this.quadData = quadData;
        this.camera = camera;
        this.gameSetings = gameSetings;
        curentTexture = textureUtil.solarSystemTex_max;
        menuPoligons = new MenuPoligons(ibo,menuPoliList);
    }
    
    public void create_game_pause_Menu(Camera cam){
       float deltaX=0,deltaY=0;
       float scaleX=0,scaleY=0;
       int windowWidth = display.getWIN_WIDTH();
       if(windowWidth >=600 && windowWidth < 700) {            
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.002f     ,   0.045f , 0.05f,     91  , 0);                 
                quadData.textToRender.add(new TextData(camera , "GAME PAUSE",  -0.01f,  0.027f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.0352f      ,   0.045f  , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "New campagne",  -0.013f,  0.021f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.02425f     ,   0.045f , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Continue campagne",  -0.02f,  +0.015f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.002f     ,   0.045f , 0.0045f ,     97  , 0);
                quadData.textToRender.add(new TextData(camera , "Multiplayer",  -0.0125f,  0.008f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.013f       ,   0.045f , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Load game",     -0.01f,   0.001f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.00925f     ,   0.045f , 0.00452f ,    97  , 0);  
                quadData.textToRender.add(new TextData(camera , "Save game",     -0.01f,  -0.0055f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));                 
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.0205f      ,   0.045f , 0.00452f ,    97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Setings",     -0.007f, -0.0125f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.03175f    ,   0.045f , 0.00452f +scaleY,    97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Main menu",     -0.0085f,  -0.0195f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true)); 
        }
        if(windowWidth >=700 && windowWidth < 800) {            
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.002f     ,   0.045f , 0.05f,     91  , 0);                 
                quadData.textToRender.add(new TextData(camera , "GAME PAUSE",  -0.01f,  0.027f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                poligon =  menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.0352f      ,   0.045f  , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "New campagne",  -0.013f,  0.021f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.02425f     ,   0.045f , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Continue campagne",  -0.02f,  +0.015f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.002f     ,   0.045f , 0.0045f ,     97  , 0);
                quadData.textToRender.add(new TextData(camera , "Multiplayer",  -0.0125f,  0.008f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.013f       ,   0.045f , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Load game",     -0.01f,   0.001f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.00925f     ,   0.045f , 0.00452f ,    97  , 0);  
                quadData.textToRender.add(new TextData(camera , "Save game",     -0.01f,  -0.0055f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                 
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.0205f      ,   0.045f , 0.00452f ,    97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Setings",     -0.007f, -0.0125f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.03175f    ,   0.045f , 0.00452f +scaleY,    97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Main menu",     -0.0085f,  -0.0195f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true)); 
        }   
        if(windowWidth >=800 && windowWidth < 900) {            
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.002f     ,   0.045f , 0.05f,     91  , 0);                 
                quadData.textToRender.add(new TextData(camera , "GAME PAUSE",  -0.01f,  0.027f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                poligon =  menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.0352f      ,   0.045f  , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "New campagne",  -0.013f,  0.021f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.02425f     ,   0.045f , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Continue campagne",  -0.017f,  +0.015f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.002f     ,   0.045f , 0.0045f ,     97  , 0);
                quadData.textToRender.add(new TextData(camera , "Multiplayer",  -0.0120f,  0.008f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.013f       ,   0.045f , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Load game",     -0.01f,   0.001f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.00925f     ,   0.045f , 0.00452f ,    97  , 0);  
                quadData.textToRender.add(new TextData(camera , "Save game",     -0.01f,  -0.0055f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                 
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.0205f      ,   0.045f , 0.00452f ,    97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Setings",     -0.007f, -0.0125f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.03175f    ,   0.045f , 0.00452f +scaleY,    97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Main menu",     -0.0085f,  -0.0195f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true)); 
        }
        if(windowWidth >=900 && windowWidth <= 1000) {            
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.002f     ,   0.045f , 0.05f,     91  , 0);                 
                quadData.textToRender.add(new TextData(camera , "GAME PAUSE",  -0.01f,  0.027f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                poligon =  menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.0352f      ,   0.045f  , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "New campagne",  -0.013f,  0.021f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.02425f     ,   0.045f , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Continue campagne",  -0.016f,  +0.015f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.002f     ,   0.045f , 0.0045f ,     97  , 0);
                quadData.textToRender.add(new TextData(camera , "Multiplayer",  -0.0125f,  0.008f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.013f       ,   0.045f , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Load game",     -0.01f,   0.001f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.00925f     ,   0.045f , 0.00452f ,    97  , 0);  
                quadData.textToRender.add(new TextData(camera , "Save game",     -0.01f,  -0.0055f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                 
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.0205f      ,   0.045f , 0.00452f ,    97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Setings",     -0.007f, -0.0125f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.03175f    ,   0.045f , 0.00452f +scaleY,    97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Main menu",     -0.0085f,  -0.0195f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true)); 
        }         
    }

    public void createGameMainMenu(Camera cam){
        
        float deltaX=0,deltaY=0;
        float scaleX=0,scaleY=0;
        int windowWidth = display.getWIN_WIDTH();
        
        if(windowWidth >=600 && windowWidth < 700) {            
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.002f     ,   0.045f , 0.05f,     91  , 0);                 
                quadData.textToRender.add(new TextData(camera , "MAIN MENU",  -0.01f,  0.027f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.0352f      ,   0.045f  , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "New campagne",  -0.013f,  0.021f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.02425f     ,   0.045f , 0.0045f ,     97  , 0);
                quadData.textToRender.add(new TextData(camera , "Continue campagne",  -0.02f,  +0.015f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.002f     ,   0.045f , 0.0045f ,     97  , 0);
                quadData.textToRender.add(new TextData(camera , "Multiplayer",  -0.0125f,  0.008f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.013f       ,   0.045f , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Load game",     -0.01f,   0.001f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.00925f     ,   0.045f , 0.00452f ,    97  , 0);  
                quadData.textToRender.add(new TextData(camera , "Save game",     -0.01f,  -0.0055f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                 
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.0205f      ,   0.045f , 0.00452f ,    97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Setings",     -0.007f, -0.0125f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.03175f    ,   0.045f , 0.00452f +scaleY,    97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Exit game",     -0.0085f,  -0.0195f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true)); 
        }
        if(windowWidth >=700 && windowWidth < 800) {            
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.002f     ,   0.045f , 0.05f,     91  , 0);                 
                quadData.textToRender.add(new TextData(camera , "MAIN MENU",  -0.01f,  0.027f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                poligon =  menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.0352f      ,   0.045f  , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "New campagne",  -0.013f,  0.021f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.02425f     ,   0.045f , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Continue campagne",  -0.02f,  +0.015f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.002f     ,   0.045f , 0.0045f ,     97  , 0);
                quadData.textToRender.add(new TextData(camera , "Multiplayer",  -0.0125f,  0.008f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.013f       ,   0.045f , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Load game",     -0.01f,   0.001f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.00925f     ,   0.045f , 0.00452f ,    97  , 0);  
                quadData.textToRender.add(new TextData(camera , "Save game",     -0.01f,  -0.0055f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                 
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.0205f      ,   0.045f , 0.00452f ,    97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Setings",     -0.007f, -0.0125f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.03175f    ,   0.045f , 0.00452f +scaleY,    97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Exit game",     -0.0085f,  -0.0195f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true)); 
        }   
        if(windowWidth >=800 && windowWidth < 900) {            
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.002f     ,   0.045f , 0.05f,     91  , 0);                 
                quadData.textToRender.add(new TextData(camera , "MAIN MENU",  -0.01f,  0.027f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                poligon =  menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.0352f      ,   0.045f  , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "New campagne",  -0.013f,  0.021f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.02425f     ,   0.045f , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Continue campagne",  -0.017f,  +0.015f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.002f     ,   0.045f , 0.0045f ,     97  , 0);
                quadData.textToRender.add(new TextData(camera , "Multiplayer",  -0.0120f,  0.008f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.013f       ,   0.045f , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Load game",     -0.01f,   0.001f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.00925f     ,   0.045f , 0.00452f ,    97  , 0);  
                quadData.textToRender.add(new TextData(camera , "Save game",     -0.01f,  -0.0055f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                 
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.0205f      ,   0.045f , 0.00452f ,    97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Setings",     -0.007f, -0.0125f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.03175f    ,   0.045f , 0.00452f +scaleY,    97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Exit game",     -0.0085f,  -0.0195f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true)); 
        }
        if(windowWidth >=900 && windowWidth <= 1000) {            
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.002f     ,   0.045f , 0.05f,     91  , 0);                 
                quadData.textToRender.add(new TextData(camera , "MAIN MENU",  -0.01f,  0.027f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                poligon =  menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.0352f      ,   0.045f  , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "New campagne",  -0.013f,  0.021f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.02425f     ,   0.045f , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Continue campagne",  -0.016f,  +0.015f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.002f     ,   0.045f , 0.0045f ,     97  , 0);
                quadData.textToRender.add(new TextData(camera , "Multiplayer",  -0.0125f,  0.008f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  0.013f       ,   0.045f , 0.0045f ,     97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Load game",     -0.01f,   0.001f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.00925f     ,   0.045f , 0.00452f ,    97  , 0);  
                quadData.textToRender.add(new TextData(camera , "Save game",     -0.01f,  -0.0055f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,false));                 
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.0205f      ,   0.045f , 0.00452f ,    97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Setings",     -0.007f, -0.0125f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));                
                menuPoligons.createPoligon(curentTexture, cam ,    0.0f    , -0.03175f    ,   0.045f , 0.00452f +scaleY,    97  , 0); 
                quadData.textToRender.add(new TextData(camera , "Exit game",     -0.0085f,  -0.0195f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true)); 
        }         
    }
    public void createSetingsMenu(Camera cam , boolean mode){
         menuPoligons.createPoligon(curentTexture, cam ,    0 ,  0     ,   0.12f , 0.12f ,     91  , 0);
         menuPoligons.createPoligon(curentTexture, cam ,    0 -0.028f,  -0.106f     ,   0.025f , 0.007f ,     97  , 0);
         menuPoligons.createPoligon(curentTexture, cam ,    0 +0.028f,  -0.106f     ,   0.025f , 0.007f ,     97  , 0);
         quadData.textToRender.add(new TextData(camera , "[-Setings-]:",  -0.067f,  0.068f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));        
         quadData.textToRender.add(new TextData(camera , "SAVE ",  -0.02f,  -0.064f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));       
         quadData.textToRender.add(new TextData(camera , "CANCEL ",  +0.01f,  -0.064f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));       
         
        if (mode) {
            quadData.textToRender.add(new TextData(camera, "Map size:", -0.067f, 0.06f, -0.15f, ibo, textureUtil.textChart_ON, false, 0, display, true));
            quadData.textToRender.add(new TextData(camera, "MIN ", -0.0325f, 0.06f, -0.15f, ibo, textureUtil.textChart_ON, false, 0, display, true));
            quadData.textToRender.add(new TextData(camera, "MAX ", +0.047f, 0.06f, -0.15f, ibo, textureUtil.textChart_ON, false, 0, display, true));
        } else {
            quadData.textToRender.add(new TextData(camera, "Map size:", -0.067f, 0.06f, -0.15f, ibo, textureUtil.textChart_ON, false, 0, display, false));
            quadData.textToRender.add(new TextData(camera, "MIN ", -0.0325f, 0.06f, -0.15f, ibo, textureUtil.textChart_ON, false, 0, display, false));
            quadData.textToRender.add(new TextData(camera, "MAX ", +0.047f, 0.06f, -0.15f, ibo, textureUtil.textChart_ON, false, 0, display, false));

        }
         float delta = 0;
         for(int i=0 ; i<10 ; i++) {
             if(i == gameSetings.galaxy_size-1) {
                 if (mode) {
                     menuPoligons.createPoligon(curentTexture, cam, -0.035f + delta, 0.1055f, 0.004f, 0.008f, 97, 0);
                 } else {
                     menuPoligons.createPoligon(curentTexture, cam, -0.035f + delta, 0.1055f, 0.004f, 0.008f, 98, 0);
                 }
             } else {
                 if (mode) {
                     menuPoligons.createPoligon(curentTexture, cam, -0.035f + delta, 0.102f, 0.004f, 0.004f, 97, 0);
                 } else {
                     menuPoligons.createPoligon(curentTexture, cam, -0.035f + delta, 0.102f, 0.004f, 0.004f, 98, 0);
                 }
             }
             delta += 0.0115f; 
         }
        if (mode) {
            quadData.textToRender.add(new TextData(camera, "Nr.players:   " + String.valueOf(gameSetings.numberOfPlayers), -0.067f, 0.0475f, -0.15f, ibo, textureUtil.textChart_ON, false, 0, display, true));
        } else {
            quadData.textToRender.add(new TextData(camera, "Nr.players:   " + String.valueOf(gameSetings.numberOfPlayers), -0.067f, 0.0475f, -0.15f, ibo, textureUtil.textChart_ON, false, 0, display, false));
        }
        delta = 0;
         for(int i=0 ; i<10 ; i++) {
             if(i == gameSetings.numberOfPlayers-1) {
                 if (mode) {
                     menuPoligons.createPoligon(curentTexture, cam, -0.035f + delta, 0.084f, 0.004f, 0.008f, 97, 0);
                 } else {
                     menuPoligons.createPoligon(curentTexture, cam, -0.035f + delta, 0.084f, 0.004f, 0.008f, 98, 0);
                 }
             } else {
                 if (mode) {
                     menuPoligons.createPoligon(curentTexture, cam, -0.035f + delta, 0.08f, 0.004f, 0.004f, 97, 0);
                 } else {
                     menuPoligons.createPoligon(curentTexture, cam, -0.035f + delta, 0.08f, 0.004f, 0.004f, 98, 0);
                 }
             }
             delta += 0.0115f; 
         }
         
         quadData.textToRender.add(new TextData(camera , "Enable auto colonization   ",  -0.067f,  0.0415f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
         if(gameSetings.auto_colonizePlanet){
             quadData.textToRender.add(new TextData(camera , "ON",  -0.002f,  0.0415f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
         } else {
             quadData.textToRender.add(new TextData(camera , "OFF",  -0.002f,  0.0415f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
         }
         menuPoligons.createPoligon(curentTexture, cam ,    0.0115f ,  0.07f    ,   0.004f , 0.004f ,     97  , 0);
         
         
         
        quadData.textToRender.add(new TextData(camera , "Enable auto construction   ",  -0.067f,  0.036f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
         if(gameSetings.auto_addShip){
             quadData.textToRender.add(new TextData(camera , "ON",  -0.002f,  0.036f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
         } else {
             quadData.textToRender.add(new TextData(camera , "OFF",  -0.002f,  0.036f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
         } 
         menuPoligons.createPoligon(curentTexture, cam ,    0.0115f ,  0.06f    ,   0.004f , 0.004f ,     97  , 0);
         

        quadData.textToRender.add(new TextData(camera , "Enable auto deploy    ",  -0.067f,  0.03f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
         if(gameSetings.auto_deploySquad){
             quadData.textToRender.add(new TextData(camera , "ON",  -0.002f,  0.03f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
         } else {
             quadData.textToRender.add(new TextData(camera , "OFF",  -0.002f,  0.03f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
         }                 
            menuPoligons.createPoligon(curentTexture, cam ,    0.0115f ,  0.05f    ,   0.004f , 0.004f ,     97  , 0);
         
            
        quadData.textToRender.add(new TextData(camera , "Enable assign worker   ",  -0.067f,  0.024f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
         if(gameSetings.auto_assign_worker){
             quadData.textToRender.add(new TextData(camera , "ON",  -0.002f,  0.024f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
         } else {
             quadData.textToRender.add(new TextData(camera , "OFF",  -0.002f,  0.024f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
         }                
         menuPoligons.createPoligon(curentTexture, cam ,    0.0115f ,  0.04f    ,   0.004f , 0.004f ,     97  , 0);
        
    }
    public void createVictoryMenu(Camera cam){
        menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,   0.002f      ,   0.045f  , 0.045f ,     91  , 0);  
        menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,   0.002f      ,   0.045f  , 0.0045f ,     97  , 0); 
        menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,   0.013f      ,   0.045f  , 0.0045f ,     97  , 0); 
        menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  -0.00925f    ,   0.045f  , 0.0045f ,     97  , 0);  
        
          quadData.textToRender.add(new TextData(camera , "VICTORY",  -0.01f,  0.014f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
          quadData.textToRender.add(new TextData(camera , "Next mission",  -0.009f,  0.008f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
          quadData.textToRender.add(new TextData(camera , "Restart mision",     -0.01f,   0.001f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
          quadData.textToRender.add(new TextData(camera , "Main menu",     -0.007f,  -0.00570f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));         
    }    
    public void createGameOverMenu(Camera cam){
        menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,   0.002f      ,   0.045f  , 0.045f ,     91  , 0); 
        menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,   0.002f      ,   0.045f  , 0.0045f ,     97  , 0); 
        menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,   0.013f      ,   0.045f  , 0.0045f ,     97  , 0); 
        
        quadData.textToRender.add(new TextData(camera , "GAME OVER",  -0.01f,  0.014f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
        quadData.textToRender.add(new TextData(camera , "Restart mision",  -0.009f,  0.008f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
        quadData.textToRender.add(new TextData(camera , "Main menu",     -0.01f,   0.001f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));        
    }
    public void createShipSelectMenu(Camera cam , Player player){
        menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  -0.127f     ,   0.14f , 0.012f,     99  , 0);
        ship_select_delta = 0;
            if(quadUtil.cargo_ship_count != 0) {
                menuPoligons.createPoligon(curentTexture, cam ,    -0.12f + ship_select_delta  ,  -0.125f        ,   0.008f , 0.008f,     player.ship_tex_index  , 0);
                ship_select_delta += 0.025f;
            }
            if(quadUtil.coloni_ship_count != 0) {
                menuPoligons.createPoligon(curentTexture, cam ,    -0.12f + ship_select_delta  ,  -0.125f        ,   0.008f , 0.008f,     player.ship_tex_index + 1  , 0);
                ship_select_delta += 0.025f;
            }
            if(quadUtil.explorer_ship_count != 0) {
                menuPoligons.createPoligon(curentTexture, cam ,    -0.12f + ship_select_delta  ,  -0.125f        ,   0.008f , 0.008f,     player.ship_tex_index + 3  , 0);
                ship_select_delta += 0.025f;
            }
            if(quadUtil.fregate_ship_count != 0) {
                if(player.index == 0 || player.index == 1) {
                    menuPoligons.createPoligon(curentTexture, cam, -0.12f + ship_select_delta, -0.125f, 0.008f, 0.008f, player.ship_tex_index + 7, 0);
                    ship_select_delta += 0.025f;
                } else {
                    menuPoligons.createPoligon(curentTexture, cam, -0.12f + ship_select_delta, -0.125f, 0.008f, 0.008f, player.ship_tex_index + 2, 0);
                    ship_select_delta += 0.025f;
                }
            }
            if(quadUtil.destroier_ship_count != 0) {
                menuPoligons.createPoligon(curentTexture, cam ,    -0.12f + ship_select_delta  ,  -0.125f        ,   0.008f , 0.008f,     player.ship_tex_index + 4  , 0);
                ship_select_delta += 0.025f;
            }
            if(quadUtil.carrier_ship_count != 0) {
                menuPoligons.createPoligon(curentTexture, cam ,    -0.12f + ship_select_delta  ,  -0.125f        ,   0.008f , 0.008f,     player.ship_tex_index + 5  , 0);
                ship_select_delta += 0.025f;
            }
            if(quadUtil.interceptor_ship_count != 0) {
                menuPoligons.createPoligon(curentTexture, cam ,    -0.12f + ship_select_delta  ,  -0.125f        ,   0.003f , 0.003f,     player.ship_tex_index + 2  , 0);
                ship_select_delta += 0.025f;
            }
            if(quadUtil.mother_ship_count != 0) {
                menuPoligons.createPoligon(curentTexture, cam ,    -0.12f + ship_select_delta  ,  -0.125f        ,   0.008f , 0.008f,     player.ship_tex_index + 6  , 0);
                ship_select_delta += 0.025f;
            }

    }
    public  void createColonyMenu_1(Camera cam){
        menuPoligons.createPoligon(curentTexture, cam ,    0.13f ,  0.07f     ,   0.01f , 0.01f ,     71  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    0.13f ,  0.045f     ,   0.01f , 0.01f ,     76  , 0);
    }
    public void createTecMenu(Camera cam , Player player) {
        menuPoligons.createPoligon(curentTexture, cam ,    0 ,  0     ,   0.12f , 0.12f ,     91  , 0);
        if(!player.laserTec) {
            menuPoligons.createPoligon(curentTexture, cam ,   -0.1f ,  0.1f     ,   0.0175f , 0.0175f ,     99  , 0);
        } else {
            menuPoligons.createPoligon(curentTexture, cam ,   -0.1f ,  0.1f     ,   0.0175f , 0.0175f ,     89  , 0);
        }
        if(!player.torpedoTec) {
            menuPoligons.createPoligon(curentTexture, cam ,   -0.06f ,  0.1f     ,   0.0175f , 0.0175f ,     19  , 0);
        } else {
            menuPoligons.createPoligon(curentTexture, cam ,   -0.06f ,  0.1f     ,   0.0175f , 0.0175f ,     9  , 0);
        }    
        if(!player.shieldTec) {
            menuPoligons.createPoligon(curentTexture, cam ,   -0.02f ,  0.1f     ,   0.0175f , 0.0175f ,     39  , 0);
        } else {
            menuPoligons.createPoligon(curentTexture, cam ,   -0.02f ,  0.1f     ,   0.0175f , 0.0175f ,     29  , 0);
        }  
        if(!player.warpDriveTec) {
            menuPoligons.createPoligon(curentTexture, cam ,   0.02f ,  0.1f     ,   0.0175f , 0.0175f ,     59  , 0);
        } else {
            menuPoligons.createPoligon(curentTexture, cam ,   0.02f ,  0.1f     ,   0.0175f , 0.0175f ,     49  , 0);
        } 
        if(!player.InterceptorTec) {
            menuPoligons.createPoligon(curentTexture, cam ,   0.06f ,  0.1f     ,   0.0175f , 0.0175f ,     79  , 0);
        } else {
            menuPoligons.createPoligon(curentTexture, cam ,   0.06f ,  0.1f     ,   0.0175f , 0.0175f ,     69  , 0);
        }        
    }
    public  void createColonyMenu_2(int start , int end , Camera cam , Player player,Planet p){
        menuPoligons.createPoligon(curentTexture, cam ,    0 ,  0     ,   0.12f , 0.12f ,     91  , 0);      
        menuPoligons.createPoligon(curentTexture, cam ,   -0.08f ,  0.112f     ,   0.005f , 0.005f ,     60  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,   -0.05f ,  0.112f     ,   0.005f , 0.005f ,     61  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,   -0.02f ,  0.112f     ,   0.005f , 0.005f ,     64  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,   +0.01f ,  0.112f     ,   0.005f , 0.005f ,     65  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,   +0.04f ,  0.112f     ,   0.005f , 0.005f ,     66  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,   +0.07f ,  0.112f     ,   0.005f , 0.005f ,     67  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,   +0.1f  ,  0.112f     ,   0.005f , 0.005f ,     62  , 0);
        if(p == null)
        {
           menuPoligons.createPoligon(curentTexture, cam ,   -0.01f ,  -0.112f     ,   0.005f , 0.004f ,     92  , 0);
           menuPoligons.createPoligon(curentTexture, cam ,   +0.01f ,  -0.112f     ,   0.005f , 0.004f ,     92  , 180);
        }
        coloniDelta = 0;  
        if(p == null)
        {
            if(player.watherPList.size() > end) {        
                for(int i=start ; i<end ; i++) {
                    if(i < end){
                        menuPoligons.createPoligon(curentTexture, cam ,   -0.1f ,  0.1f - coloniDelta    ,   0.01f , 0.01f ,     player.watherPList.get(i).model.INDEX_T  , 0);
                        coloniDelta += 0.025f;
                    }
                }
            } else {            
                        for(int i=start ; i<player.watherPList.size() ; i++)  {
                            if(i < end) {
                                menuPoligons.createPoligon(curentTexture, cam ,   -0.1f ,  0.1f - coloniDelta    ,   0.01f , 0.01f ,     player.watherPList.get(i).model.INDEX_T  , 0);
                                coloniDelta += 0.025f;
                            }
                        }        
                   }
        } else {
            menuPoligons.createPoligon(curentTexture, cam ,   -0.1f ,  0.1f     ,   0.01f , 0.01f ,  p.model.INDEX_T  , 0);                
        }
    }
    public void createCargoBayMenu(Camera cam , Planet planet,String message)  {
        menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  -0.11f     ,   0.14f , 0.03f,     91  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    -0.015f    ,  -0.095f     ,   0.11f , 0.01225f,     91  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    -0.015f    ,  -0.126f     ,   0.11f , 0.01225f,     91  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    -0.11f  ,  -0.095f        ,   0.01f , 0.01f,     planet.model.INDEX_T  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    -0.08f   ,  -0.095f        ,   0.005f , 0.005f,     60  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    -0.05f   ,  -0.095f        ,   0.005f , 0.005f,     61  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    -0.02f   ,  -0.095f        ,   0.005f , 0.005f,     64  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    +0.01f   ,  -0.095f        ,   0.005f , 0.005f,     65  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    +0.04f   ,  -0.095f        ,   0.005f , 0.005f,     63  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    +0.07f   ,  -0.095f         ,   0.005f , 0.005f,     66  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    -0.08f   ,  -0.120f        ,   0.005f , 0.005f,     60  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    -0.05f   ,  -0.120f        ,   0.005f , 0.005f,     61  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    -0.02f   ,  -0.120f        ,   0.005f , 0.005f,     64  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    +0.01f   ,  -0.120f        ,   0.005f , 0.005f,     65  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    +0.04f   ,  -0.120f        ,   0.005f , 0.005f,     63  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    +0.07f   ,  -0.120f         ,   0.005f , 0.005f,     66  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    -0.08f - 0.005f   ,  -0.131f        ,   0.004f , 0.004f,     90  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    -0.05f - 0.005f   ,  -0.131f        ,   0.004f , 0.004f,     90  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    -0.02f - 0.005f   ,  -0.131f        ,   0.004f , 0.004f,     90  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    +0.01f - 0.005f   ,  -0.131f        ,   0.004f , 0.004f,     90  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    +0.04f - 0.005f   ,  -0.131f        ,   0.004f , 0.004f,     90  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    +0.07f - 0.005f   ,  -0.131f        ,   0.004f , 0.004f,     90  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    -0.08f + 0.005f   ,  -0.131f        ,   0.004f , 0.004f,     87  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    -0.05f + 0.005f   ,  -0.131f        ,   0.004f , 0.004f,     87  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    -0.02f + 0.005f   ,  -0.131f        ,   0.004f , 0.004f,     87  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    +0.01f + 0.005f   ,  -0.131f        ,   0.004f , 0.004f,     87  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    +0.04f + 0.005f   ,  -0.131f        ,   0.004f , 0.004f,     87  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    +0.07f + 0.005f   ,  -0.131f        ,   0.004f , 0.004f,     87  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    -0.11f  ,  -0.126f        ,   0.01f , 0.01f,     73 , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    +0.127f  ,  -0.0880f        ,   0.008f , 0.006f,     91  , 0);
        menuPoligons.createPoligon(curentTexture, cam ,    +0.127f  ,  -0.0880f        ,   0.007f , 0.005f,     92  , 0);
        
        quadData.textToRender.add(new TextData(camera , "Energy"  ,          - 0.05f,         - 0.0525f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
        quadData.textToRender.add(new TextData(camera , "Metal",             - 0.032f,       - 0.0525f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
        quadData.textToRender.add(new TextData(camera , "Mineral" ,          - 0.014f,        - 0.0525f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
        quadData.textToRender.add(new TextData(camera , "Carbon"  ,          + 0.004f,        - 0.0525f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
        quadData.textToRender.add(new TextData(camera , "Credit"  ,          + 0.0225f,       - 0.0525f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
        quadData.textToRender.add(new TextData(camera , "Gas"     ,          + 0.041f,        - 0.0525f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
        quadData.textToRender.add(new TextData(camera , message   ,          - 0.07f,         - 0.0665f,   - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
        quadData.textToRender.add(new TextData(camera , String.valueOf(planet.resources.cargoDoc_energy_Stock),        - 0.0425f ,       - 0.0725f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
        quadData.textToRender.add(new TextData(camera , String.valueOf(planet.resources.cargoDoc_metalOre_Stock),      - 0.0245f ,       - 0.0725f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
        quadData.textToRender.add(new TextData(camera , String.valueOf(planet.resources.cargoDoc_mineral_Stock),       - 0.0065f ,       - 0.0725f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
        quadData.textToRender.add(new TextData(camera , String.valueOf(planet.resources.cargoDoc_carbon_Stock),        + 0.0115f ,       - 0.0725f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
        quadData.textToRender.add(new TextData(camera , String.valueOf(planet.resources.cargoDoc_credit_Stock),        + 0.0295f ,       - 0.0725f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
        quadData.textToRender.add(new TextData(camera , String.valueOf(planet.resources.cargoDoc_gas_Stock),           + 0.0471f ,       - 0.0725f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));        
 
        textData = new TextData(camera , String.valueOf(planet.resources.energyMined) ,  - 0.0425f ,  - 0.057f ,  - 0.15f  , ibo , textureUtil. textChart_ON , true , 14,display,true);
        textData.planet = planet;
        quadData.textToRender.add(textData);
        textData = new TextData(camera , String.valueOf(planet.resources.metalOreMined) ,  - 0.0245f ,  - 0.057f ,  - 0.15f  , ibo , textureUtil. textChart_ON , true , 15,display,true);
        textData.planet = planet;
        quadData.textToRender.add(textData);
        textData = new TextData(camera , String.valueOf(planet.resources.mineralsMined) ,  - 0.0065f ,  - 0.057f ,  - 0.15f  , ibo , textureUtil. textChart_ON , true , 16,display,true);
        textData.planet = planet;
        quadData.textToRender.add(textData);
        textData = new TextData(camera , String.valueOf(planet.resources.carbonMined) ,  + 0.0115f ,  - 0.057f ,  - 0.15f  , ibo , textureUtil. textChart_ON , true , 17,display,true);
        textData.planet = planet;
        quadData.textToRender.add(textData);
        textData = new TextData(camera , String.valueOf(planet.resources.creditMined) ,  + 0.0295f ,  - 0.057f ,  - 0.15f  , ibo , textureUtil. textChart_ON , true , 18,display,true);
        textData.planet = planet;
        quadData.textToRender.add(textData);
        textData = new TextData(camera , String.valueOf(planet.resources.gasMined) ,  + 0.0471f,  - 0.057f ,  - 0.15f  , ibo , textureUtil. textChart_ON , true , 19,display,true);
        textData.planet = planet;
        quadData.textToRender.add(textData);        
    }
    public void createShipYardMenu(Camera cam , Planet planet,String message) {
       menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  -0.11f     ,   0.144f , 0.03f,     91  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.11f  ,  -0.11f        ,   0.025f , 0.025f,     74  , 0);       
       menuPoligons.createPoligon(curentTexture, cam ,    -0.07f  ,  -0.10f        ,   0.013f , 0.013f,     80  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.04f ,  -0.10f        ,   0.013f , 0.013f,     81  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.01f ,  -0.10f        ,   0.013f , 0.013f,     85  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.02f  ,  -0.10f        ,   0.013f , 0.013f,     82  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.05f ,  -0.10f        ,   0.013f , 0.013f,     83  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.08f  ,  -0.10f        ,   0.013f , 0.013f,     84  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.11f  ,  -0.10f        ,   0.013f , 0.013f,     86  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.07f  ,  -0.1225f        ,   0.005f , 0.005f,     90  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.04f ,  -0.1225f        ,   0.005f , 0.005f,     90  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.01f ,  -0.1225f        ,   0.005f , 0.005f,     90  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.02f  ,  -0.1225f        ,   0.005f , 0.005f,     90  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.05f ,  -0.1225f        ,   0.005f , 0.005f,     90  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.08f  ,  -0.1225f        ,   0.005f , 0.005f,     90  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.11f  ,  -0.1225f        ,   0.005f , 0.005f,     90  , 0);      
       menuPoligons.createPoligon(curentTexture, cam ,    +0.127f  ,  -0.0880f        ,   0.008f , 0.006f,     91  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.127f  ,  -0.0880f        ,   0.007f , 0.005f,     92  , 0);
       
            quadData.textToRender.add(new TextData(camera , "Cargo"        ,           - 0.046f,        - 0.068f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "Coloni"       ,          - 0.0295f,         - 0.068f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "Explorer"     ,         - 0.015f,         - 0.068f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true)); 
            quadData.textToRender.add(new TextData(camera , "Fregata"      ,       + 0.0060f,         - 0.068f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));             
            quadData.textToRender.add(new TextData(camera , "Destroier"    ,         + 0.0229f,          - 0.068f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "Carrier"      ,         + 0.0435f,          - 0.068f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));            
            quadData.textToRender.add(new TextData(camera , "Mother.Ship " ,    + 0.061f,        - 0.068f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));         
            quadData.textToRender.add(new TextData(camera , message,     - 0.047f,     - 0.0815f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));       
                 
    }
    public void createBuildingsMenu(Camera cam , Planet planet,String message)  {
       menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  -0.11f     ,   0.14f , 0.03f,     91  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.11f  ,  -0.11f        ,   0.025f , 0.025f,     75  , 0);       
       menuPoligons.createPoligon(curentTexture, cam ,    -0.07f  ,  -0.105f        ,   0.01f , 0.01f,     60  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.035f ,  -0.105f        ,   0.01f , 0.01f,     61  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.005f ,  -0.105f        ,   0.01f , 0.01f,     64  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.04f  ,  -0.105f        ,   0.01f , 0.01f,     65  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.075f ,  -0.105f        ,   0.01f , 0.01f,     63  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.11f  ,  -0.105f        ,   0.01f , 0.01f,     66  , 0);      
       menuPoligons.createPoligon(curentTexture, cam ,    -0.07f  ,  -0.126f        ,   0.005f , 0.005f,     90  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.035f ,  -0.126f        ,   0.005f , 0.005f,     90  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.005f ,  -0.126f        ,   0.005f , 0.005f,     90  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.04f  ,  -0.126f        ,   0.005f , 0.005f,     90  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.075f ,  -0.126f        ,   0.005f , 0.005f,     90  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.11f  ,  -0.126f        ,   0.005f , 0.005f,     90  , 0);       
       menuPoligons.createPoligon(curentTexture, cam ,    +0.127f  ,  -0.0880f        ,   0.008f , 0.006f,     91  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.127f  ,  -0.0880f        ,   0.007f , 0.005f,     92  , 0);  
       
            quadData.textToRender.add(new TextData(camera , "Energy ",     - 0.0475f,  - 0.055f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "Metal ",  - 0.027f,   - 0.055f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "Minerals ",   - 0.002f,   - 0.055f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true)); 
            quadData.textToRender.add(new TextData(camera , "Carbon ",     + 0.020f,   - 0.055f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));             
            quadData.textToRender.add(new TextData(camera , "Credit ",     + 0.04f,    - 0.055f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "Gas ",        + 0.0625f,        - 0.055f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));            
            quadData.textToRender.add(new TextData(camera , "Lv: "+String.valueOf(planet.resources.energyLevel),      - 0.047f,      - 0.07f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "Lv: "+String.valueOf(planet.resources.metalOreLevel),    - 0.026f,      - 0.07f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "Lv: "+String.valueOf(planet.resources.mineralsLevel),    - 0.0015f,     - 0.07f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true)); 
            quadData.textToRender.add(new TextData(camera , "Lv: "+String.valueOf(planet.resources.carbonLevel),      + 0.02f,       - 0.07f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));             
            quadData.textToRender.add(new TextData(camera , "Lv: "+String.valueOf(planet.resources.creditLevel),      + 0.0425f,     - 0.07f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "Lv: "+String.valueOf(planet.resources.gasLevel),         + 0.0625f,     - 0.07f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true)); 
            quadData.textToRender.add(new TextData(camera , message,    - 0.047f,     - 0.0815f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));        
    }
    public void createShipUpgradesMenu(Camera cam , Planet planet,String message)  {
       menuPoligons.createPoligon(curentTexture, cam ,    0.0f    ,  -0.11f     ,   0.14f , 0.03f,     91  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.11f  ,  -0.11f        ,   0.025f , 0.025f,     72  , 0);    
       menuPoligons.createPoligon(curentTexture, cam ,    -0.07f  ,  -0.105f        ,   0.01f , 0.01f,     93  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.035f ,  -0.105f        ,   0.01f , 0.01f,     94  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.005f ,  -0.105f        ,   0.01f , 0.01f,     95  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.04f  ,  -0.105f        ,   0.01f , 0.01f,     96  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.07f  ,  -0.126f        ,   0.005f , 0.005f,     90  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.035f ,  -0.126f        ,   0.005f , 0.005f,     90  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.005f ,  -0.126f        ,   0.005f , 0.005f,     90  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.04f  ,  -0.126f        ,   0.005f , 0.005f,     90  , 0);       
       menuPoligons.createPoligon(curentTexture, cam ,    +0.127f  ,  -0.0880f        ,   0.008f , 0.006f,     91  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.127f  ,  -0.0880f        ,   0.007f , 0.005f,     92  , 0);  
       
            quadData.textToRender.add(new TextData(camera , "Engine ",     - 0.0475f,   - 0.055f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "Guns "  ,     - 0.025f,    - 0.055f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "Hull "  ,     - 0.0f,      - 0.055f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true)); 
            quadData.textToRender.add(new TextData(camera , "Shield ",     + 0.020f,    - 0.055f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));             
            quadData.textToRender.add(new TextData(camera , "Lv: "+String.valueOf(planet.resources.engineUpgradeLevel),     -0.047f,     -0.07f,  -0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "Lv: "+String.valueOf(planet.resources.gunsUpgradeLevel)  ,     -0.026f,     -0.07f,  -0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "Lv: "+String.valueOf(planet.resources.hullUpgradeLevel)  ,     -0.0015f,    -0.07f,  -0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true)); 
            quadData.textToRender.add(new TextData(camera , "Lv: "+String.valueOf(planet.resources.shieldUpgradeLevel),     +0.02f,      -0.07f,  -0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));   
            quadData.textToRender.add(new TextData(camera , message,    - 0.047f,     - 0.0815f, - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));    
    } 
    public void createPlanetSelectMenu(Camera cam , Planet planet)  {
       menuPoligons.createPoligon(curentTexture, cam ,    0.0f ,  -0.111f     ,   0.144f , 0.0325f,     91  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.115f ,  -0.107f        ,   0.019f , 0.019f,     planet.model.INDEX_T  , 0);      
       menuPoligons.createPoligon(curentTexture, cam ,    -0.07f   ,  -0.10f        ,   0.0125f , 0.0125f,     75  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.02f   ,  -0.10f       ,   0.0125f , 0.0125f,     72  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.0301f ,  -0.10f       ,   0.0125f , 0.0125f,     74  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.0801f ,  -0.10f       ,   0.0125f , 0.0125f,     73  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.0925f   ,  -0.122f        ,   0.005f , 0.005f,     60  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.0525f   ,  -0.122f        ,   0.005f , 0.005f,     61  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.0125f   ,  -0.122f        ,   0.005f , 0.005f,     64  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.0325f   ,  -0.122f        ,   0.005f , 0.005f,     65  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.11f     ,  -0.122f        ,   0.005f , 0.005f,     63  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.0725f   ,  -0.122f          ,   0.005f , 0.005f,     66  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.0925f   ,  -0.13f        ,   0.003f , 0.003f,     70  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.0525f   ,  -0.13f        ,   0.003f , 0.003f,     70  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.0125f   ,  -0.13f        ,   0.003f , 0.003f,     70  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.0325f   ,  -0.13f        ,   0.003f , 0.003f,     70  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.0725f   ,  -0.13f        ,   0.003f , 0.003f,     70  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.092f   ,  -0.138f        ,   0.004f , 0.004f,     90  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.0525f   ,  -0.138f        ,   0.004f , 0.004f,     90  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.0125f   ,  -0.138f        ,   0.004f , 0.004f,     90  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.0325f   ,  -0.138f        ,   0.004f , 0.004f,     90  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.0725f   ,  -0.138f        ,   0.004f , 0.004f,     90  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.0849f   ,  -0.138f        ,   0.004f , 0.004f,     87  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.045f   ,  -0.138f        ,   0.004f , 0.004f,     87  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    -0.005f   ,  -0.138f        ,   0.004f , 0.004f,     87  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.04f   ,  -0.138f        ,   0.004f , 0.004f,     87  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.08f   ,  -0.138f        ,   0.004f , 0.004f,     87  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.11f   ,  -0.095f          ,   0.005f , 0.005f,     62  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    +0.11f   ,  -0.109f          ,   0.004f , 0.005f,     70  , 0);
       
            if(planet.isWather) {
                quadData.textToRender.add(new TextData(camera , "WhaterWorld" ,  -0.08f, - 0.0515f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                quadData.textToRender.add(new TextData(camera , "Class A"      ,  -0.08f,  - 0.08f,   - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            }
            if(planet.isArid) {
                quadData.textToRender.add(new TextData(camera , "AridWorld" ,  -0.08f,  - 0.0515f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true)); 
                quadData.textToRender.add(new TextData(camera , "Class C"    ,  -0.08f,  - 0.08f,    - 0.15f  , ibo , textureUtil. textChart_ON, false,0,display,true));
            }
            if(planet.isSemiarid) {
                quadData.textToRender.add(new TextData(camera , "SemiAridWorld" ,  -0.08f,  - 0.0515f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true)); 
                quadData.textToRender.add(new TextData(camera , "Class B"        ,  -0.08f,  - 0.08f,    - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            }
            if(planet.isVulcanic) {
                quadData.textToRender.add(new TextData(camera , "VulcanicWorld" ,  -0.08f,  - 0.0515f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                quadData.textToRender.add(new TextData(camera , "Class E"        ,  -0.08f,  - 0.08f,    - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            }
            if(planet.isIce) {
                quadData.textToRender.add(new TextData(camera , "IceWorld" ,  -0.08f,  - 0.0515f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));        
                quadData.textToRender.add(new TextData(camera , "Class D"   ,  -0.08f,  - 0.08f,    - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            }
            quadData.textToRender.add(new TextData(camera , "Buildings " ,  -0.05f,   -0.05f,    - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "Reserch "   ,  -0.02f,   -0.05f,    - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "ShipYard "  ,  +0.01f,   -0.05f,    - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "StarDoc "   ,  +0.04f,   -0.05f,    - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));            
            quadData.textToRender.add(new TextData(camera , "Energy "    ,  -0.05f,   -0.070f,   - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "Metal " ,  -0.027f,  -0.070f,   - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "Mineral " ,  -0.0012f,  -0.070f,   - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "Carbon "    ,  +0.025f,  -0.070f,   - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "Gas "       ,  +0.048f,  -0.070f,   - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "Credit "    ,  +0.065f,  -0.070f,   - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "Population ",  +0.065f,  -0.053f,   - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            quadData.textToRender.add(new TextData(camera , "Workers"    ,  +0.065f,  -0.0615f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));  
            
            quadData.textToRender.add(new TextData(camera , "["+planet.resources.maxPopulation+"]",  +0.0755f,  - 0.058f,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
            textData = new TextData(camera , String.valueOf(planet.resources.energyMined) ,  -0.05f ,  - 0.073f ,  - 0.15f  , ibo , textureUtil. textChart_ON , true , 1,display,true);
            textData.planet = planet;
            quadData.textToRender.add(textData);
            textData = new TextData(camera , String.valueOf(planet.resources.metalOreMined),  -0.027f,  - 0.073f,  - 0.15f  , ibo , textureUtil. textChart_ON , true,2,display,true);
            textData.planet = planet;
            quadData.textToRender.add(textData);
            textData = new TextData(camera , String.valueOf(planet.resources.mineralsMined),  -0.002f,  - 0.073f,  - 0.15f  , ibo , textureUtil. textChart_ON , true,3,display,true);
            textData.planet = planet;
            quadData.textToRender.add(textData);
            textData = new TextData(camera , String.valueOf(planet.resources.carbonMined),   +0.025f,  - 0.073f,  - 0.15f  , ibo , textureUtil. textChart_ON , true,4,display,true);
            textData.planet = planet;
            quadData.textToRender.add(textData);
            textData = new TextData(camera , String.valueOf(planet.resources.creditMined),  +0.07f,  - 0.073f,  - 0.15f  , ibo , textureUtil. textChart_ON , true,5,display,true);
            textData.planet = planet;
            quadData.textToRender.add(textData);
            textData = new TextData(camera , String.valueOf(planet.resources.gasMined),  +0.048f,  - 0.073f,  - 0.15f  , ibo , textureUtil. textChart_ON , true,6,display,true);
            textData.planet = planet;
            quadData.textToRender.add(textData);
            textData = new TextData(camera , String.valueOf(planet.resources.population),  +0.071f,  - 0.058f,  - 0.15f  , ibo , textureUtil. textChart_ON , true,7,display,true);
            textData.planet = planet;
            quadData.textToRender.add(textData);
            textData = new TextData(camera , String.valueOf(planet.resources.energyWorker) ,  -0.051f ,  - 0.078f , - 0.15f  , ibo , textureUtil. textChart_ON , true , 8,display,true);
            textData.planet = planet;
            quadData.textToRender.add(textData);
            textData = new TextData(camera , String.valueOf(planet.resources.metalOreWorker),  -0.027f,  - 0.078f,  - 0.15f  , ibo , textureUtil. textChart_ON , true,9,display,true);
            textData.planet = planet;
            quadData.textToRender.add(textData);
            textData = new TextData(camera , String.valueOf(planet.resources.mineralsWorker),  -0.0027f,  - 0.078f,  - 0.15f  , ibo , textureUtil. textChart_ON , true,10,display,true);
            textData.planet = planet;
            quadData.textToRender.add(textData);
            textData = new TextData(camera , String.valueOf(planet.resources.carbonWorker),   +0.024f,  - 0.078f,  - 0.15f  , ibo , textureUtil. textChart_ON , true,11,display,true);
            textData.planet = planet;
            quadData.textToRender.add(textData);
            textData = new TextData(camera , String.valueOf(planet.resources.gasWorker),  +0.048f,  - 0.078f,  - 0.15f  , ibo , textureUtil. textChart_ON , true,12,display,true);
            textData.planet = planet;
            quadData.textToRender.add(textData);
            textData = new TextData(camera , String.valueOf(planet.resources.workersAvaible),  +0.071f,  - 0.066f,  - 0.15f  , ibo , textureUtil. textChart_ON , true,13,display,true);
            textData.planet = planet;
            quadData.textToRender.add(textData);            
            
    }      
    public void createSunSelectMenu(Camera cam , Sun sun)
    {
       planet_stacer = 0;
        
       curentTexture =  textureUtil.solarSystemTex_max;
       menuPoligons.createPoligon(curentTexture, cam ,    0.09f ,  -0.040f     ,   0.05f , 0.11f ,     91  , 0);
       menuPoligons.createPoligon(curentTexture, cam ,    0.07f ,   0.03f      ,   0.025f , 0.025f ,     sun.model.INDEX_T  , 0);
      quadData.textToRender.add(new TextData(camera , "Type: "+sun.starType, 0.0275f,  0,  - 0.15f  , ibo , textureUtil. textChart_ON, false,0,display,true));
       float planetMenuSize; 
       
       for(int i=0 ; i<sun.listaPlanete.size() ; i++)   {
         planetMenuSize =   sun.listaPlanete.get(i).model.scale.x;    
         if(sun.starType.equalsIgnoreCase("whiteDwarf") || sun.starType.equalsIgnoreCase("redDwarf"))   {
            planetMenuSize = planetMenuSize * 2.5f;
         } else {
           planetMenuSize = planetMenuSize * 1.75f;
         }     
         menuPoligons.createPoligon(curentTexture, cam ,  0.12f ,  0.05f - planet_stacer  ,   planetMenuSize , planetMenuSize ,     sun.listaPlanete.get(i).model.INDEX_T  , 0);
         planet_stacer += 0.03;
       }
       
    }  
}
