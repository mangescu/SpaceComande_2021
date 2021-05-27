
package spacecomander;

import Planet.Planet;
import Player.Player;
import Sun.Sun;
import TextUtil.TextData;
import java.util.ArrayList;

public class QuadData {
    public ArrayList<Quad>      phaserList     ;
    public ArrayList<Quad>      phaserEmiter   ; 
    public ArrayList<Quad>      shieldList     ;
    public ArrayList<Quad>      explosionList  ;
    public ArrayList<Quad>      bulletList     ;
    public ArrayList<Sun>       sunList        ;
    public ArrayList<Planet>    planetList  ;
    public ArrayList<Player>    playerList     ;
    public ArrayList<Quad>      colonizedInfluenceRing ;
    public ArrayList<Quad>      shipSelectionRing ; 
    public ArrayList<Quad>      menuPoliList   ; 
    public ArrayList<TextData>  textToRender ;
    public ArrayList<Quad>      linePrimitive  ;
    public ArrayList<Quad>      pointPrimitive ;  
    public QuadData(){
          phaserList     = new ArrayList<Quad>();
          phaserEmiter   = new ArrayList<Quad>(); 
          shieldList     = new ArrayList<Quad>();
          explosionList  = new ArrayList<Quad>();
          bulletList     = new ArrayList<Quad>();
          sunList        = new ArrayList<Sun>();
          planetList        = new ArrayList<Planet>();
          playerList     = new ArrayList<Player>();
          colonizedInfluenceRing = new ArrayList<Quad>();
          shipSelectionRing = new ArrayList<Quad>(); 
          menuPoliList   = new ArrayList<Quad>();
          textToRender   = new ArrayList<TextData>();
          linePrimitive  = new ArrayList<Quad>();
          pointPrimitive = new ArrayList<Quad>();            
    }
}
