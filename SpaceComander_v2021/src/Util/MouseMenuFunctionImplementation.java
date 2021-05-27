
package Util;

import Menu.MenuData;
import Planet.Planet;
import Player.Player;
import Sun.Sun;
import java.util.ArrayList;


public class MouseMenuFunctionImplementation {
    
MenuData menuData;       
Sun sun;
Planet planet;

public   MouseMenuFunctionImplementation(MenuData menuData){
     this.menuData = menuData;
}  
    public Sun serchSunSelected(float x , float y ,  ArrayList<Sun>   sunList) {
        sun = null;
        for(Sun s : sunList) {
            if(x > s.position.x - s.model.scale.x && x < s.position.x + s.model.scale.x) {
                if(y > s.position.y - s.model.scale.y && y < s.position.y + s.model.scale.y) {
                   sun = s;
                }            
            }
        }
    return sun;
    }
    public Planet serchPlanetSelected(float x , float y ,  ArrayList<Sun>   sunList) {
        planet  = null;
        for(Sun s : sunList) {
            for(Planet p : s.listaPlanete){            
                if(x > p.position.x - p.model.scale.x && x < p.position.x + p.model.scale.x) {
                    if(y > p.position.y - p.model.scale.y && y < p.position.y + p.model.scale.y) {
                       planet = p;
                    }            
                }
            }
        }
    return planet;
    }    
    public Planet serchSunPlanetSelected(float x , float y ,  Sun sun) {
        planet  = null;
        for(int i=2 ; i < menuData.menuPoliList.size() ; i++)   {
            if(x > menuData.menuPoliList.get(i).curentPosition.x - menuData.menuPoliList.get(i).model.scale.x && x < menuData.menuPoliList.get(i).curentPosition.x + menuData.menuPoliList.get(i).model.scale.x) {
                if(y > menuData.menuPoliList.get(i).curentPosition.y - menuData.menuPoliList.get(i).model.scale.y && y < menuData.menuPoliList.get(i).curentPosition.y + menuData.menuPoliList.get(i).model.scale.y) {
                     planet = sun.listaPlanete.get(i-2);
                }                      
            } 
        }
    return planet;
    }
    public int selectMenu_1(float x , float y) {
        int  ok  = -1;
        for(int i=0 ; i < menuData.menuPoliList.size() ; i++)  {
            if(x > menuData.menuPoliList.get(i).curentPosition.x - menuData.menuPoliList.get(i).model.scale.x && x < menuData.menuPoliList.get(i).curentPosition.x + menuData.menuPoliList.get(i).model.scale.x) {
                if(y > menuData.menuPoliList.get(i).curentPosition.y - menuData.menuPoliList.get(i).model.scale.y && y < menuData.menuPoliList.get(i).curentPosition.y + menuData.menuPoliList.get(i).model.scale.y) {   
                    ok = i;
                }                      
            } 
        }
    return ok;
    }
    public int selectMenu_2(float x , float y) {
        int  ok  = -1;
        for(int i=0 ; i < menuData.menuPoliList.size() ; i++)  {
            if(x > menuData.menuPoliList.get(i).curentPosition.x - menuData.menuPoliList.get(i).model.scale.x && x < menuData.menuPoliList.get(i).curentPosition.x + menuData.menuPoliList.get(i).model.scale.x) {
                if(y > menuData.menuPoliList.get(i).curentPosition.y - menuData.menuPoliList.get(i).model.scale.y && y < menuData.menuPoliList.get(i).curentPosition.y + menuData.menuPoliList.get(i).model.scale.y) {   
                    ok = i;
                }                      
            } 
        }
    return ok;
    }   
    public int selectMainMenu(float x , float y)  {
        int index = -10;
        for(int i=1 ; i < menuData.menuPoliList.size() ; i++){
            if(x > menuData.menuPoliList.get(i).curentPosition.x - menuData.menuPoliList.get(i).model.scale.x && x < menuData.menuPoliList.get(i).curentPosition.x + menuData.menuPoliList.get(i).model.scale.x) {
                if(y > menuData.menuPoliList.get(i).curentPosition.y - menuData.menuPoliList.get(i).model.scale.y && y < menuData.menuPoliList.get(i).curentPosition.y + menuData.menuPoliList.get(i).model.scale.y) {
                     index = i;   
                }                      
            } 
        }
    return index;    
    }    
    public int selectplanetMenuOption(float x , float y)  {
        int index = -10;
        for(int i=1 ; i < menuData.menuPoliList.size() ; i++){
            if(x > menuData.menuPoliList.get(i).curentPosition.x - menuData.menuPoliList.get(i).model.scale.x && x < menuData.menuPoliList.get(i).curentPosition.x + menuData.menuPoliList.get(i).model.scale.x) {
                if(y > menuData.menuPoliList.get(i).curentPosition.y - menuData.menuPoliList.get(i).model.scale.y && y < menuData.menuPoliList.get(i).curentPosition.y + menuData.menuPoliList.get(i).model.scale.y) {
                     index = i;   
                }                      
            } 
        }
    return index;    
    }
    public int  selectedShipOption(float x , float y)
    {
        int ind = -1;
        int index = -1;
        for(int i=0 ; i < menuData.menuPoliList.size() ; i++){
            if(x > menuData.menuPoliList.get(i).curentPosition.x - menuData.menuPoliList.get(i).model.scale.x && x < menuData.menuPoliList.get(i).curentPosition.x + menuData.menuPoliList.get(i).model.scale.x) {
                if(y > menuData.menuPoliList.get(i).curentPosition.y - menuData.menuPoliList.get(i).model.scale.y && y < menuData.menuPoliList.get(i).curentPosition.y + menuData.menuPoliList.get(i).model.scale.y) {
                    ind = i;
                }
            }
        }
        if(ind >= 0) {
            if (menuData.menuPoliList.get(ind).model.INDEX_T % 10 == 0) {
                index = 0;
            }
            if (menuData.menuPoliList.get(ind).model.INDEX_T % 10 == 1) {
                index = 1;
            }
            if (menuData.menuPoliList.get(ind).model.INDEX_T % 10 == 2) {
                index = 2;
            }
            if (menuData.menuPoliList.get(ind).model.INDEX_T % 10 == 3) {
                index = 3;
            }
            if (menuData.menuPoliList.get(ind).model.INDEX_T % 10 == 4) {
                index = 4;
            }
            if (menuData.menuPoliList.get(ind).model.INDEX_T % 10 == 5) {
                index = 5;
            }
            if (menuData.menuPoliList.get(ind).model.INDEX_T % 10 == 6) {
                index = 6;
            }
            if (menuData.menuPoliList.get(ind).model.INDEX_T % 10 == 7) {
                index = 7;
            }
            if (menuData.menuPoliList.get(ind).model.INDEX_T % 10 == 8) {
                index = 8;
            }
        }
        if(ind == 0) {
            index = 10;
        }
        return index;
    }
    public int buildingMenuOption(float x , float y)  {
        int index = -10;
        for(int i=1 ; i < menuData.menuPoliList.size() ; i++){
            if(x > menuData.menuPoliList.get(i).curentPosition.x - menuData.menuPoliList.get(i).model.scale.x && x < menuData.menuPoliList.get(i).curentPosition.x + menuData.menuPoliList.get(i).model.scale.x) {
                if(y > menuData.menuPoliList.get(i).curentPosition.y - menuData.menuPoliList.get(i).model.scale.y && y < menuData.menuPoliList.get(i).curentPosition.y + menuData.menuPoliList.get(i).model.scale.y) {
                     index = i;   
                }                      
            } 
        }
    return index;    
    }
public int shipYardMenuOption(float x , float y) {
        int index = -10;
        for(int i=1 ; i < menuData.menuPoliList.size() ; i++){
            if(x > menuData.menuPoliList.get(i).curentPosition.x - menuData.menuPoliList.get(i).model.scale.x && x < menuData.menuPoliList.get(i).curentPosition.x + menuData.menuPoliList.get(i).model.scale.x) {
                if(y > menuData.menuPoliList.get(i).curentPosition.y - menuData.menuPoliList.get(i).model.scale.y && y < menuData.menuPoliList.get(i).curentPosition.y + menuData.menuPoliList.get(i).model.scale.y) {
                     index = i; 
                }                      
            } 
        }
    return index; 
}    
    public int selectMenu_2(float x , float y , Player player) {
        int index = -10;
        for(int i=8 ; i < menuData.menuPoliList.size() ; i++) {
            if(x > menuData.menuPoliList.get(i).curentPosition.x - menuData.menuPoliList.get(i).model.scale.x && x < menuData.menuPoliList.get(i).curentPosition.x + menuData.menuPoliList.get(i).model.scale.x) {
                if(y > menuData.menuPoliList.get(i).curentPosition.y - menuData.menuPoliList.get(i).model.scale.y && y < menuData.menuPoliList.get(i).curentPosition.y + menuData.menuPoliList.get(i).model.scale.y) {
                     index = i - 9;   
                }                      
            } 
        }
    return index;
    }     
}
