
package Input;

import AILogic.AILogic;
import Border.Board;
import Camera.Camera;
import Campaign.Campaign;
import Display.SetupDisplay_LWJGL_3;
import IBO.IBO;
import static Input.KeyCallBack.keys;
import Maths.Maths;
import Menu.MenuData;
import Planet.Planet;
import Player.Player;
import QuadModel.Model;
import Render.Renderer;
import SolarSystem.SolarSystem;
import Sun.Sun;
import TextUtil.TextData;
import Texture.TextureUtil;
import Util.MousePicker;
import java.util.ArrayList;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import spacecomander.Quad;
import spacecomander.QuadData;
import spacecomander.QuadUtil;
import spacecomander.QuadsManagement;
import spacecomander.SpaceComander;

public class Input  {
//____MAP__SCALE______________________________________________________//
   float deltaScaleMap = 0.14412f;
//____________________________________________________________________//   
    Camera camera;
    Renderer      renderer;
    Model map,galaxyMap;
    QuadUtil quadUtil;
    float moveSpeed;
    Vector3f curentRay;

    float x_percent , y_percent;
     
    MousePicker pick ;
    Maths maths;
    QuadsManagement  quadManagement;
  
    boolean mouseIsDown;
    ArrayList<Quad>      bordersList;
    SetupDisplay_LWJGL_3   display;
    
    public  GLFWScrollCallback scrollCallback;
    public  GLFWKeyCallback    keyCallback;
    public  GLFWMouseButtonCallback mouseButtonCallback;
    public  GLFWCursorPosCallback mousePos;
    
    boolean space = false;
    boolean mkey = false;
    
    public MenuData menuData;
    IBO      ibo;
    TextureUtil textureUtil;
    int race_index;
    public ScrollCallback scroll;
    public KeyCallBack keyboard;
    public MouseClickCallback   mouseClick;
    AILogic aiLogic;
   
    Board bord3;
    
   SolarSystem solarSystem;
   Campaign campaign;
   QuadData  quadData;
       
    public Input(SetupDisplay_LWJGL_3 display ,Camera camera ,Renderer  renderer, Model map ,Model galaxyMap ,QuadUtil quadUtil, Maths maths ,QuadsManagement  quadManagement   ,MenuData menuData, IBO  ibo,TextureUtil textureUtil ,int race_index ,Board bord3  , SolarSystem solarSystem,Campaign campaign,QuadData  quadData,AILogic aiLogic){
        this.display = display;
        this.camera = camera;
        this.renderer = renderer;
        this.map = map;
        this.galaxyMap = galaxyMap;
        this.maths = maths;
        this.quadUtil = quadUtil;
        this.quadManagement  =  quadManagement;
        this.bordersList = bordersList;
        this.menuData = menuData;
        this.ibo = ibo;
        this.textureUtil = textureUtil;
        this.race_index = race_index;
        this.solarSystem = solarSystem;
        this.campaign = campaign;
        this.quadData = quadData;
        pick = new MousePicker(display , camera , renderer.getProjectionMatrix() , maths);  
        int Xpos , Ypos;
        this.bord3 = bord3;
        this.aiLogic =  aiLogic;
    }
    public void setupCameraLocation(float x , float y , boolean type)
    {
        if(type) {
            scroll.ZoomInLow();
        } else {
                 scroll.ZoomInHeight();
               }
        setupXYKeyboardLocation(x , y);
        map.texture.transparency = 1.0f;
        galaxyMap.texture.transparency = 0.01f;
        SpaceComander.selectEdgeWidth = camera.getPosition().z/1000;
    }
    public void setupXYKeyboardLocation(float x , float y)
    {
        if(y > camera.getPosition().y)  {
            while(Math.abs(y - camera.getPosition().y) >=0.01f)    {
                moveUpY();
            }
        }
        if(y < camera.getPosition().y)    {
            while(Math.abs(camera.getPosition().y - y) >=0.01f)    {
                moveDownY();
            }
        }
        if(x > camera.getPosition().x)    {
            while(Math.abs(x - camera.getPosition().x) >=0.01f)    {
                moveRightX();
            }
        }
        if(x < camera.getPosition().x)    {
            while(Math.abs(camera.getPosition().x - x) >=0.01f)    {
                moveLeftX();
            }
        }
    }
    public void moveUpY(){
        moveSpeed = 0.005f * Math.abs(camera.getPosition().z);
        camera.getPosition().y += moveSpeed;
        updatetext(moveSpeed,false);
        updateMenuPoligons(moveSpeed,false);
        map.position.y += moveSpeed;
        checkCameraBox();
    }
    public void moveDownY()
    {
        moveSpeed = 0.005f * Math.abs(camera.getPosition().z);
        camera.getPosition().y -= moveSpeed;
        updatetext(-moveSpeed,false);
        updateMenuPoligons(-moveSpeed,false);
        map.position.y -=moveSpeed;
        checkCameraBox();
    }
    public void moveRightX()
    {
        moveSpeed = 0.005f * Math.abs(camera.getPosition().z);
        camera.getPosition().x += moveSpeed;
        updatetext(moveSpeed,true);
        updateMenuPoligons(moveSpeed,true);
        map.position.x +=moveSpeed;
        checkCameraBox();
    }
    public void moveLeftX(){
        moveSpeed = 0.005f * Math.abs(camera.getPosition().z);
        camera.getPosition().x -= moveSpeed;
        updatetext(-moveSpeed,true);
        updateMenuPoligons(-moveSpeed,true);
        map.position.x -=moveSpeed;
        checkCameraBox();
    }
    public void KeyboardControl(){
        
         if(keys[GLFW.GLFW_KEY_H]) {
             
             for(Player play : quadData.playerList){
                 System.out.println("play cStarList ======= "+play.cStarList.size());
                 for(int j=0 ; j<10 ; j++) {
                    System.out.println("play neiburns = "+play.playerSharedBorderLenght[j]);
                 }
                 System.out.println();
             }
         
         }        
        
        if(keys[GLFW.GLFW_KEY_Q]) {
            if(!SpaceComander.colonyShipControlKey) {
                SpaceComander.colonyShipControlKey = true;
                quadData.textToRender.add(new TextData(camera , "[Q] set [ x y ] coordonate from where to serch closest planet [mouse left click]",   - 0.085f   ,   + 0.085f    ,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
             }
        } else {
            if(SpaceComander.colonyShipControlKey) {
                 SpaceComander.colonyShipControlKey = false;
                 if(quadData.textToRender.size() != 0) {
                    quadData.textToRender.remove(quadData.textToRender.size() - 1);
                 }
            }
        }
        if(keys[GLFW.GLFW_KEY_Y]) {
             for(Planet p : quadData.playerList.get(SpaceComander.race_index).cPlanetList){
                if(p.isWather) {
                   aiLogic.resValidation.autoShipsUpgrades(p);
                }
             }
        }
         if(keys[GLFW.GLFW_KEY_W]) {
             moveUpY();
         }
         if(keys[GLFW.GLFW_KEY_S]) {
             moveDownY();
         }     
         if(keys[GLFW.GLFW_KEY_A]) {
             moveLeftX();
         }         
         if(keys[GLFW.GLFW_KEY_D]) {
             moveRightX();
         }
        if(keys[GLFW.GLFW_KEY_C]) {
            quadData.playerList.get(race_index).planetColector = mouseClick.planet;
        }
        if(keys[GLFW.GLFW_KEY_ESCAPE]) {
              glfwSetWindowShouldClose(display.getWindow(), true);  
              space = true;
        }                 
        if(keys[GLFW.GLFW_KEY_M]) {
             if(mkey)  {

                mkey = false;
             }
        }
        if(keys[GLFW.GLFW_KEY_N]) {
             if(!mkey)   {

                mkey = true;
             }
        }         
        if(keys[GLFW.GLFW_KEY_U]) {
            if(!mkey) {
                mkey = true;
            }
        }
        if(keys[GLFW.GLFW_KEY_Y]) {
            if(mkey)   {
                //textureUtil.mapTex.glInit();
                mkey = false;
            }
        }
         if(keys[GLFW.GLFW_KEY_X]) {
          mkey = false;
             if(!SpaceComander.game_pause) {
                 mouseClick.game_pause_menu();
                 SpaceComander.game_pause = true;
                 //space = true;
             } 
         }          
        if(keys[GLFW.GLFW_KEY_Z]) {
            if(SpaceComander.game_pause)  {          
                mouseClick.menuData.menuPoliList.clear();
                mouseClick.quadData.textToRender.clear();
                mouseClick.resetBooleanVar();
                SpaceComander.game_pause = false;
        
            }
            if(keys[GLFW.GLFW_KEY_B]) { 
               quadData. playerList.get(0).shipList.clear();
            }           
         }         
    }
    public void updateMenuPoligons(float delta , boolean axis) {
        for(Quad menuP : quadData.menuPoliList) {
            if(axis) {
                menuP.curentPosition.x += delta;
            } else {
                menuP.curentPosition.y += delta;
            }
        }    
    } 
    public void updatetext(float delta , boolean axis){
        for(TextData text : quadData.textToRender) {
            for(Quad quad : text.textChars) {
                if(axis) {
                   quad.curentPosition.x += delta;
                } else {
                   quad.curentPosition.y += delta;
                }
            }
        }     
    }
    public void checkCameraBox()   {
       for(Sun sun : quadData.sunList) {
              if(checkConstrainsS(sun)) {
                 sun.enableToRender = true;
              } else {
                       sun.enableToRender = false;
                     }           
           for(Planet planet : sun.listaPlanete){
              if(checkConstrainsP(planet)){
                 planet.enableToRender = true;
                 if(planet.ring != null) {
                     planet.ring.enableToRender = true;
                 }
              } else {
                       planet.enableToRender = false;
                        if(planet.ring != null) {
                            planet.ring.enableToRender = false;
                        }                       
                     }
            if(camera.getPosition().z > 2.5)    {
                planet.enableToRender = false;
            } else {
                        planet.enableToRender = true;
                   }  
           }   
       }
       checkPlayerBorderConstrains();
    }
    public boolean checkConstrainsS(Sun sun) {
        if(sun.position.x > camera.getPosition().x - map.scale.x   &&  sun.position.x < camera.getPosition().x + map.scale.x){
            if(sun.position.y > camera.getPosition().y - map.scale.y   &&  sun.position.y < camera.getPosition().y + map.scale.y){
               return true;
            } else {
                     return false;
                   }        
        } else {
                 return false;
               }
    }    
    public boolean checkConstrainsP(Planet planet) {
        if(planet.position.x > camera.getPosition().x - map.scale.x   &&  planet.position.x < camera.getPosition().x + map.scale.x){
            if(planet.position.y > camera.getPosition().y - map.scale.y   &&  planet.position.y < camera.getPosition().y + map.scale.y){
               return  true;
            } else {
                      return false;
                   }        
        } else {
                  return false;
               }
    }
     public void checkPlayerBorderConstrains() {
        for(int i=0 ; i<quadData.playerList.size() ; i++) {
            for(int j=0 ; j<quadData.playerList.get(i).bordersList.size() ; j++) {
                Quad border = quadData.playerList.get(i).bordersList.get(j);
                if(border.isBorder) {
                    if(!checkBorderPointConstains(border.border_vec_1)  &&  !checkBorderPointConstains(border.border_vec_2)) {
                        border.enableToRender = false;
                    } else {
                       border.enableToRender = true;
                    }
                } else {
                   if(checkHexBorderConstrains(border)) {
                      border.enableToRender = true;
                   } else {
                     border.enableToRender = false;
                   }
                }
            }
        }
    }
    public boolean checkHexBorderConstrains(Quad border) {
        boolean check = false;
        for(int i=0 ; i<border.model.pointsList.size() ; i++) {
             Vector3f pointB = border.model.pointsList.get(i);
             if(checkBorderPointConstains(pointB)) {
                check = true;
             }
        }
        return check;
    } 
    public boolean  checkBorderPointConstains(Vector3f pointB) {
        if(pointB.x > camera.getPosition().x - (map.scale.x + map.scale.x/4)   && pointB.x < camera.getPosition().x + (map.scale.x + map.scale.x/4)){
            if(pointB.y > camera.getPosition().y - (map.scale.y + map.scale.y/4)   &&  pointB.y < camera.getPosition().y + (map.scale.y + map.scale.y/4)){
               return  true;
            } else {
                      return false;
                   }        
        } else {
                  return false;
               }    
    }   
 public void releseCalback(){
   // keyCallback.free();
   // scrollCallback.free();
 }
 
}
