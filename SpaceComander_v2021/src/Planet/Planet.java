
package Planet;

import Player.Player;
import QuadModel.Model;
import org.joml.Vector3f;
import spacecomander.Quad;
import Sun.Sun;

public class Planet {

    public int planetMaxShipCount = 0;
    public int index;
    public boolean isVulcanic = false;
    public boolean isArid = false;
    public boolean isSemiarid = false;
    public boolean isWather = false;
    public boolean isIce = false;
    
    public int classPlanet;
    
    public boolean enableToRender = true;    
    
    public Model model;
    public Quad ring;
    public Player player;
    
    public int  owner = -1;  
    public  boolean isColonized;
    public int   angleOrbit;
    public int   angleRotate;    

    public float spacer;
    public float startP;
    public float planetsSpacer;
    
    public int shipCount = 0;
    
    public String name;
    public Vector3f position;
    public float starDistance;
    public float starSpacer;
    public float startPP;
     public float planetsSpacerr;
    public float radius;

    public Sun parentStar;
    
    //__BORDER_COLONY___________________________________//
        //public float borderAngle;
        //public boolean isBorderColony;
        //public boolean pinBorder = false;
    //__________________________________________________//


    public Resources resources;
    public boolean planet_ownership = false;
    
    public Planet() {
        resources = new Resources();
    }
    
}
