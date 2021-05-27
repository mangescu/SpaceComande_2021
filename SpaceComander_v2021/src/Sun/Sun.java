
package Sun;

import Border.BorderVectData;
import Border.Ecuation;
import Border.InvertEcuation;
import Planet.Planet;
import QuadModel.Model;
import java.util.ArrayList;
import org.joml.Vector3f;
import spacecomander.Quad;
import Player.Player;import spacecomander.SpaceComander;
;

public class Sun {

     public ArrayList<Sun>        neighborhood       = new ArrayList<Sun>();

    public ArrayList<Boolean>        exploredList       = new ArrayList<Boolean>();
    public ArrayList<Vector3f>       sunNeighborsList   = new ArrayList<Vector3f>();
    public ArrayList<Ecuation>             ecuationList   =  new ArrayList<Ecuation>();
    public ArrayList<InvertEcuation>       invertEList    =  new ArrayList<InvertEcuation>();
    public ArrayList<BorderVectData>       intersectionPoints   = new ArrayList<BorderVectData>();
    public boolean enableToRender = true;    
    public Model model;    
    public Vector3f position;
    public Vector3f saved_position;
    public String name;
    public String starType;
    public float radius;   
    public ArrayList<Planet> listaPlanete;
    public Quad ring;
    public Player player;
    public int playerIndex;
    public float borderAngle;
    public float tempBorderAngle;
    public float firstBorderAngle;
    public Vector3f tempVec3;
    int[]  planets;
    public int[] player_planets_own ;
    public boolean border_type = true;    
    public int star_index;
    
    public Sun(int numberOfPlayers){
        playerIndex = -1;
        player_planets_own = new int[numberOfPlayers+1];
        planets = new int[numberOfPlayers+1];
        exploredList.add(true);
        exploredList.add(true);
        exploredList.add(true);
        exploredList.add(true);
        exploredList.add(true);
        exploredList.add(true);
        exploredList.add(true);
        exploredList.add(true);
        exploredList.add(true);
        exploredList.add(true);
        
        saved_position = new Vector3f();
    }
    public void savePosition(){
       saved_position.x = position.x;
       saved_position.y = position.y;
       saved_position.z = position.z;
    }
    public Sun(Vector3f position ,  String name , float radius){
        this.position = position;
        this.name = name;
        this.radius = radius;  
        star_index = 0;
    }
    public boolean checkSystemEntity(int player_id)  {
        boolean ok = true;
        for(int i=0 ;i< player_planets_own.length ; i++)   {
            if(player_id != i)    {
              if(player_planets_own[i] != 0)  {
                   ok = false;
              }
            }
        }
        return ok;
    }
    public boolean checkBigestOwner(int player_id) {
        boolean ok = true;  
        for(int i=0 ;i< player_planets_own.length ; i++)   {
            if(player_id != i) {
              if(player_planets_own[i] >= player_planets_own[player_id])  {
                   ok = false;
              }
            }
        } 
      return ok;
    }
    public void  setupDefaultNeighbors() {
        float delta = 1.5f;
        tempVec3 = new Vector3f();
        tempVec3.x = position.x - (0.5f * delta);
        tempVec3.y = position.y - (1f * delta);
        sunNeighborsList.add(tempVec3);
        tempVec3 = new Vector3f();
        tempVec3.x = position.x - (1f * delta);
        tempVec3.y = position.y;
        sunNeighborsList.add(tempVec3);
        tempVec3 = new Vector3f();
        tempVec3.x = position.x - (0.5f * delta);
        tempVec3.y = position.y + (1f * delta);
        sunNeighborsList.add(tempVec3);
        tempVec3 = new Vector3f();
        tempVec3.x = position.x + (0.5f * delta);
        tempVec3.y = position.y + (1f * delta);
        sunNeighborsList.add(tempVec3);
        tempVec3 = new Vector3f();
        tempVec3.x = position.x + (1f * delta);
        tempVec3.y = position.y;
        sunNeighborsList.add(tempVec3);
        tempVec3 = new Vector3f();
        tempVec3.x = position.x + (0.5f * delta);
        tempVec3.y = position.y - (1f * delta);
        sunNeighborsList.add(tempVec3);
    }
}
