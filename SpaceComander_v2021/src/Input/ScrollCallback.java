
package Input;

import Camera.Camera;
import Menu.MenuData;
import Planet.Planet;
import Player.Player;
import QuadModel.Model;
import Sun.Sun;
import TextUtil.TextData;
import java.util.ArrayList;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWScrollCallback;
import spacecomander.Quad;
import spacecomander.QuadData;
import spacecomander.QuadUtil;
import spacecomander.SpaceComander;

public class ScrollCallback extends  GLFWScrollCallback{

 Camera camera;   
 Model map , galaxyMap;
 MenuData menuData;
 int zoomSpeed = 1;
 QuadUtil  quadUtil;
 QuadData quadData;
//____MAP__SCALE______________________________________________________//
   float deltaScaleMap = 0.14412f;
//____________________________________________________________________//  
    public ScrollCallback(Camera camera , Model map ,Model galaxyMap ,MenuData menuData  , QuadUtil  quadUtil,QuadData quadData) {
        this.camera = camera;
        this.map = map;
        this.galaxyMap = galaxyMap;
        this.menuData = menuData;
        this.quadUtil = quadUtil;
        this.quadData =quadData;
    }
    public void ZoomInLow(){
        while(camera.getPosition().z >= 0.75f) {
            zoomCameraIn(1);
        }
    }
    public void ZoomInHeight(){
        while(camera.getPosition().z <= 7f) {
            zoomCameraOut(1);
        }
    }    
    @Override
    public void invoke(long win, double dx, double dy) {
        if(!SpaceComander.game_pause)
        {
             if (dy > 0 && camera.getPosition().z >= 0.60) {
                 if(camera.getPosition().z <= 5) {
                     zoomSpeed = 1;
                 }
                 if(camera.getPosition().z <=10) {
                     zoomSpeed = 2;
                 }
                 zoomCameraIn(zoomSpeed);
                 SpaceComander.selectEdgeWidth = camera.getPosition().z/1000;
            }
            if (dy < 0 && camera.getPosition().z < 15) {
                if(camera.getPosition().z > 5) {
                    zoomSpeed = 2;
                }
                if(camera.getPosition().z > 10) {
                    zoomSpeed = 8;
                }
                zoomCameraOut(zoomSpeed);
                SpaceComander.selectEdgeWidth = camera.getPosition().z/1000;
            }
        }
    }
    public void zoomCameraOut(int count)   {
        for(int i=0 ; i < count ; i++)   {
            if(camera.getPosition().z < 15)  {
                if(galaxyMap.texture.transparency < 0.7f) {
                    galaxyMap.texture.transparency += 0.015f;
                    map.texture.transparency       -= 0.01f;;
                }
                camera.getPosition().z += 0.25f;
                updateMap(deltaScaleMap);
                update_TextPoli_menuPoli(+0.25f);                
                checkCameraBox();
            }
        }
    }
    public void zoomCameraIn(int count)   {
        for(int i=0 ; i < count ; i++)     {
            if(camera.getPosition().z >= 0.75f){
                if(galaxyMap.texture.transparency >= 0.01) {
                    galaxyMap.texture.transparency -= 0.015f;
                    map.texture.transparency       += 0.01f;;
                }
                camera.getPosition().z += -0.25f;
                updateMap(-deltaScaleMap); 
                update_TextPoli_menuPoli(-0.25f);            
                checkCameraBox();
            }
        }
    }
    public void updateMap(float delta) {
                map.scale.x += delta;
                map.scale.y += delta;    
    }
    public void update_TextPoli_menuPoli(float delta) {
                for (TextData text : quadData.textToRender) {
                    for (Quad quad : text.textChars) {
                        quad.curentPosition.z += delta;
                    }
                }
                for (Quad quad : quadData.menuPoliList) {
                    quad.model.position.z += delta;
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
                if(camera.getPosition().z > 2.5)
                {
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
}
