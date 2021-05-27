
package Player;

import Border.BorderVectData;
import Planet.Planet;
import Sun.Sun;
import java.util.ArrayList;
import spacecomander.Quad;
import Border.BoarderList;

public class Player {

    public Planet  planetColector = null;

    public boolean enableColoniShipPriority = false;
    public float   x_priority;
    public float   y_priority;

   public boolean  renderPlayerElements = true;
    
   public ArrayList<Quad>      bordersList   = new ArrayList<Quad>();    
   public ArrayList<Quad>      shipList;  
    
   public int boarder_tex_index;
   public int hex_Border_index;
   public int segmented_boarder_tex_index;
   
   
   public int ship_tex_index; 
   public int bullet_tex_index_type_1;
   public int bullet_tex_index_type_2;
   public int explosion_tex_index;
   public int shield_tex_index;
   public int phaser_tex_index;
   public int phaserEmiter_tex_index;
    
    
   public boolean auto_addShip        = true;
   public boolean auto_colonizePlanet = true;
   public boolean auto_deploySquad    = true;
   public boolean auto_assign_worker = true;
   public boolean auto_colect_resources = true;
   public boolean auto_resUpgrade       = true;
   
   public boolean laserTec = true;
   public boolean torpedoTec = true;
   public boolean shieldTec = true;
   public boolean warpDriveTec = true;
   public boolean InterceptorTec = true;
    
   public float start_position_x;
   public float start_position_y;    

    public Player  masterPlayer = null;
    public  ArrayList<Player>     conquestPlayerList     = new ArrayList<Player>();
    
    public  ArrayList<Planet>    watherPList   = new ArrayList<Planet>();
    public  ArrayList<Planet>    cPlanetList   = new ArrayList<Planet>();
    public  ArrayList<Sun>       cStarList   = new ArrayList<Sun>();

  public boolean[]  enemy = new boolean[10];


  public int faction;    
  public int minDefenceColonizeCount; 
  public int maxShipCount; 
  public int index; 
  public int federation_index = -1;
  public int shipCount;
  public int maximumColonizePlanet; 

   public int  ring_index;
   public int  selectyRing;
   public int  cercle_index;

   public boolean colonizationProcess = true;  
   public Quad coloniShip;   
   
   public int colonizationClass = 0;
   
   public byte[] playerSharedBorderLenght = new byte[10];
   
   public Player() {
     this.shipList = new ArrayList<Quad>();
   }
    
}
