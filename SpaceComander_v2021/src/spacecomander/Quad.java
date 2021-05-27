
package spacecomander;

import Planet.Planet;
import Player.Player;
import QuadModel.Model;
import audio.Source;
import java.util.ArrayList;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Quad {
    
    public Source phaserSource , torpedoSource , disruptorSource;
    public int quadPlayerIndex = 0;
    public boolean shipHasCheck= false;
    int mother_ship_atack_speed = 10;
    int mother_Ship_Fire_Count_limit = 10;
    int mother_ship_fire_spacer = 400;

public boolean  quad_IBO      =  true;
public boolean  triangle_IBO  =  false;

public float bulet_size;
public float borderTranslateAngle;    
public int buuletType;      
public  Quad bulletGhideTarget;
public  int  count_randomize_angle = 0;
public  int randomize_angle_reset = 4;
public boolean shipIsInWarp = false;
public boolean warpJumpTec = false;
public boolean enable_warp  = false;
public boolean disable_warp = false;
public boolean oneTime = false;
public int distorsionAmount = 20;
public int warp_step_counter = 0;
public int warp_step_reset = 20;
public boolean ship_is_moving = false;      
public boolean enableToRender = true;
public String name;
    
public Vector3f canon_1 , canon_2 , canon_3;
    
    public Model model;
    public Player player;

    public Quad firePositionQuad;
    public Quad parent;
    public Quad phaserEmiter;
    public Quad selectRing;
    
    public  ArrayList<Player>   playerList;

    public Vector3f curentPosition;
    public Vector3f endPosition;
    public Vector2f speed;
    public Vector3f quadRotation;
    public Vector3f scale;
    
    public boolean hasAtack = true;
    public boolean releseInterceptor = false;

    public float shield = 100;
    public float life   = 100;
    public float bulletDamage = 1;
    public float phaserDamage = 2;
    
    public float bulletFiredCoordX;
    public float bulletFiredCoordY;
    
    public int faction;
    public float atackRange = 0.5f;
    public float distanceEnemyShip;
    public float minDistanceAtack = 100;  
    public boolean targetLockOn = false;
    public boolean attack = false;
    public Vector3f target;
    public Quad targetShiep;
    public boolean enableSmartTargeting = true;
    public boolean alive = true;
    public int  procisionTargeting=1;
    public boolean targetFireEnable = false;
    
    public int  count_bullet_fire = 0;
    public int  bullet_attack_reset = 200;
    public float  interceptor_min_range =0.4f;
    
    public int  count_phaser_fire = 0;
    public int  phaser_attack_reset = 4;    
    
    public int  interceptor_bulletCountFired = 0;
    public int  interceptor_bulletStopFireNumbet = 3;
    
    public boolean interceptorReturn = false;
   
    public boolean enableInterceptorAtack = true;
    
public int   quadCount = 5;
public int   quadStop  = 5;
public float minShipDistance; 

public float shielWidth;
      
public float canonPlasement = 0.85f;
public float canonStartengle = -90; 
public float deltaAngle = 25;
public float canon_1_x;
public float canon_1_y;
public float canon_2_x;
public float canon_2_y;
public float canon_3_x;
public float canon_3_y;

public Planet deployPlanet;

public int shipFireBehavior = 2;
public float step_movement = 0.0005f;
public float temp_step_movement = 0.0005f;
    
 public float interceptosReturn_x;
 public float interceptosReturn_y;
 

 public boolean interceptor_is_dock = false;

public float angleStartEnd = 0;
public float angle_rotate = 0;
public float temp_angle_rotate;
public float angleCompare = 0;
    
 public boolean enableRotationLogic = true;
 public boolean rotationEnable      = false;    
 public boolean rotate_right        = false;
 public boolean rotate_left         = false;
 
 public int rotationRadius = 5;
 public int cancelRotation = 15;
    
 public float width;
    
 public boolean isSlected;
    
 public float motherShipFireCount=3;
 public int canonAtack;
 
 public String shipName;
 public boolean isDestroierShip   = false;   
 public boolean isFreagateShip    = false;   
 public boolean isExploreShip     = false;   
 public boolean isInterceptor     = false;
 public boolean isCarrierShip     = false;
 public boolean isColoniShip      = false;
 public boolean isMotherShip      = false;
 public boolean isbullet          = false;
 public boolean isCargoShip       = false;

 public boolean  isSelectebel = true;
 public boolean autoCargoShip    = true;
 public int  energyCargo         = 0;
 public int  metalOreCargo       = 0;
 public int  mineralCargo        = 0;
 public int  carbonCargo         = 0;
 public int  creditCargo         = 0;
 public int  gasCargo            = 0;

 public boolean shipDecomision = false;

 public boolean volatileBorder = true;
 public boolean isBorder = false;
 public boolean isHexBorder = false;
 
 public Vector3f  border_vec_1;
 public Vector3f  border_vec_2;
 
 Vector2f normalizeVector = new Vector2f();
 
 ArrayList<Quad>   interceptors ;
 
    public  Quad() {  
        curentPosition = new Vector3f();
        endPosition    = new Vector3f(); 
        speed    = new Vector2f();   
        quadRotation = new Vector3f();
        scale  = new Vector3f();   
        canon_1 = new Vector3f(0, 0, 0);
        canon_2 = new Vector3f(0, 0, 0);
        canon_3 = new Vector3f(0, 0, 0); 
        interceptors = new ArrayList<Quad>();
    }
    public void genPhaserSoundSources(){
        phaserSource  = new Source();
    }
    public void deletePhaserSources(){
       phaserSource.delete();
    }
    public void genDisruptorSoundSources(){
        disruptorSource  = new Source();
    }
    public void deleteDisruptorSources(){
       disruptorSource.delete();
    }     
    public void setXYStepSpeed() { 
           speed.x =   step_movement * (float) Math.sin(Math.toRadians(angleStartEnd));
           speed.y =   step_movement * (float) Math.cos(Math.toRadians(angleStartEnd)); 
    }    
    public void translateQuad(float x , float y) {
       interceptorReturn = false;      
       endPosition.x = x;
       endPosition.y = y;   
       if(!isbullet){
           calcAngleCompare();
       } else {
                calcAngleStartEnd();
              }
        setXYStepSpeed();
        enableRotationLogic = false; 
        if(warpJumpTec) {
            if(!shipIsInWarp){
                oneTime = false;
            }                 
        }
        ship_is_moving = true;
        shipDecomision = true;
    }
    public void stop()  {
       speed.x = 0;
       speed.y = 0;  
       ship_is_moving = false;
    }
    public void reshapeQuad(){
        if(model.scale.x != model.scale.y) {
            model.scale.y = model.scale.x;
        }
        if(!isColoniShip) {
            step_movement = temp_step_movement;
        }
    }
    public void calcAngleCompare() {
        angleCompare = (float) Math.toDegrees(Math.atan2(endPosition.x - curentPosition.x , endPosition.y - curentPosition.y));
        if(angleCompare < 0) {
            angleCompare = 360+ angleCompare;
        } 
        rotateShipLogic();        
    }
     public void calcAngleStartEnd() {
        angleStartEnd = (float) Math.toDegrees(Math.atan2(endPosition.x - curentPosition.x, endPosition.y - curentPosition.y)); 
        if(angleStartEnd < 0) {
            angleStartEnd = 360 + angleStartEnd;
        }          
     }
    public void rotateShipLogic() {
        if(!enableRotationLogic){
            if(angleStartEnd >= 180) {
                if(angleCompare <= angleStartEnd - 180 || angleCompare > angleStartEnd) {      
                    enableShipRotateRight();
                } else {
                     enableShipRotateLeft();
                }
            } else {
                        if(angleCompare <=  angleStartEnd + 180 && angleCompare > angleStartEnd) {
                            enableShipRotateRight();
                        } else {
                            enableShipRotateLeft();
                        }
                   }
        }
        enableRotationLogic = true;  
    } 
    public void enableShipRotateLeft() {
                rotate_right = false;
                rotate_left = true;
                rotationEnable = true;    
    }
    public void enableShipRotateRight() {
                 rotate_right = true;
                 rotate_left = false;
                 rotationEnable = true;            
    }   

}
