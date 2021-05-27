
package spacecomander;

import Border.BorderVectData;
import IBO.IBO;
import IBO.IBO_Data;
import Planet.Planet;
import Sun.Sun;
import Player.Player;
import QuadModel.Model;
import Texture.TextureUtil;
import audio.AudioMaster;
import audio.Source;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joml.Vector3f;


public class QuadsManagement {
 
    Quad ship,ring,bullet,explosion,interceptor,shield,phaser,border,emiter,shipSelectRing,lineSegment,point,quad,hex;
    Model model;
    IBO      quad_ibo , triangle_ibo;
    TextureUtil textureUtil;
    public float scale , scaleQuad;
    int race_index;   
    IBO_Data IBO_Data  ;
    QuadData  quadData;
    
    public QuadsManagement( IBO  quad_ibo , IBO  triangle_ibo , TextureUtil textureUtil , float scaleGame  ,  int race_index ,QuadData  quadData){
       this.quad_ibo = quad_ibo;
       this.triangle_ibo = triangle_ibo;
       this.textureUtil = textureUtil;
       this.scaleQuad = scaleGame;
       this.race_index = race_index;
       this.quadData = quadData;
    }
    public Quad  addBullet(int bullet_type , Quad ship  , ArrayList<Quad>  bulletList , Vector3f  fireCoordonate , float bSize) {
    
        bullet = new Quad(); 
        bullet.curentPosition.x = fireCoordonate.x;
        bullet.curentPosition.y = fireCoordonate.y;
        bullet.curentPosition.z = 0.00027f;
        bullet.bulletFiredCoordX = ship.curentPosition.x;
        bullet.bulletFiredCoordY = ship.curentPosition.y; 
        
        if(bullet_type == 0){
             model = new Model(quad_ibo, textureUtil.bulletTex, ship.player.bullet_tex_index_type_1, bullet.curentPosition, bullet.quadRotation, new Vector3f(bSize/scaleQuad, bSize/scaleQuad, bSize/scaleQuad));
        }
        if(bullet_type == 1) {
            model = new Model(quad_ibo, textureUtil.bulletTex, ship.player.bullet_tex_index_type_2, bullet.curentPosition, bullet.quadRotation, new Vector3f(bSize/scaleQuad, bSize/scaleQuad, bSize/scaleQuad));
        }
        bullet.buuletType = bullet_type;
        bullet.width = model.scale.x/scaleQuad;
        bullet.model = model; 
        bullet.player = ship.player;
        bullet.quadPlayerIndex = ship.player.index;
        bullet.isbullet = true;
        bullet.step_movement = 0.00125f;
        bullet.bulletGhideTarget = ship.targetShiep;
        bullet.bulletDamage = ship.bulletDamage;
        bulletList.add(bullet);
     return bullet;
    }    
     public Quad  addPhaser(Quad ship , ArrayList<Quad>  phaserList)
    {
        phaser = new Quad();
        phaser.curentPosition.x = 0;
        phaser.curentPosition.y = 0;
        phaser.curentPosition.z = 0.000275f;
        float delta_x = Math.abs(ship.canon_1.x - ship.target.x)/2;
        float delta_y = Math.abs(ship.canon_1.y - ship.target.y)/2;
        if(ship.canon_1.x > ship.target.x  &&  ship.canon_1.y > ship.target.y){
           phaser.curentPosition.x = ship.canon_1.x - delta_x ;
           phaser.curentPosition.y = ship.canon_1.y - delta_y ;
        }
        if(ship.canon_1.x < ship.target.x  &&  ship.canon_1.y < ship.target.y){
           phaser.curentPosition.x = ship.canon_1.x + delta_x ;
           phaser.curentPosition.y = ship.canon_1.y + delta_y ;
        }   
        if(ship.canon_1.x > ship.target.x  &&  ship.canon_1.y < ship.target.y){
           phaser.curentPosition.x = ship.canon_1.x - delta_x ;
           phaser.curentPosition.y = ship.canon_1.y + delta_y ;
        }
        if(ship.canon_1.x < ship.target.x  &&  ship.canon_1.y > ship.target.y){
           phaser.curentPosition.x = ship.canon_1.x + delta_x ;
           phaser.curentPosition.y = ship.canon_1.y - delta_y ;
        }            
        
        scale  = (float) Math.sqrt(Math.pow(ship.canon_1.x - ship.target.x, 2.) + Math.pow(ship.canon_1.y - ship.target.y, 2)) / 2;
        phaser.quadRotation.z = (float) ( - Math.toDegrees(Math.atan2(ship.target.x - ship.canon_1.x ,  ship.target.y - ship.canon_1.y)));

        if(ship.player.index != 5) {
            if (!ship.isInterceptor) {
                phaser.quad_IBO = false;
                phaser.triangle_IBO = true;

                model = new Model(quad_ibo, textureUtil.phaserTex, ship.player.phaser_tex_index, phaser.curentPosition, phaser.quadRotation, new Vector3f(0.002f / scaleQuad, scale / scaleQuad, 1));
                model.ANIMATION_RESET_V = 10;
            } else {
                    model = new Model(quad_ibo, textureUtil.phaserTex, ship.player.phaser_tex_index, phaser.curentPosition, phaser.quadRotation, new Vector3f(0.0015f / scaleQuad, scale / scaleQuad, 1));    
                    model.ANIMATION_RESET_V = 7;
            }
        } else {
            if (!ship.isInterceptor) {

                phaser.quad_IBO = false;
                phaser.triangle_IBO = true;
                if(ship.isMotherShip || ship.isCarrierShip) {
                    if(ship.targetShiep.shield > 0) {
                        model = new Model(triangle_ibo, textureUtil.phaserTex, 90, phaser.curentPosition, phaser.quadRotation, new Vector3f((ship.targetShiep.width / 2) / scaleQuad, scale / scaleQuad, 1));
                        model.ANIMATION_RESET_V = 10;
                    } else {
                        model = new Model(triangle_ibo, textureUtil.phaserTex, 90, phaser.curentPosition, phaser.quadRotation, new Vector3f((ship.targetShiep.width / 4) / scaleQuad, scale / scaleQuad, 1));
                        model.ANIMATION_RESET_V = 10;
                    }
                } else {
                    model = new Model(quad_ibo, textureUtil.phaserTex, ship.player.phaser_tex_index, phaser.curentPosition, phaser.quadRotation, new Vector3f(0.002f / scaleQuad, scale / scaleQuad, 1));
                    model.ANIMATION_RESET_V = 7;
                }
            } else {
                model = new Model(quad_ibo, textureUtil.phaserTex, ship.player.phaser_tex_index, phaser.curentPosition, phaser.quadRotation, new Vector3f(0.0015f / scaleQuad, scale / scaleQuad, 1));
                model.ANIMATION_RESET_V = 7;
            }
        }
            if(ship.player.index == 4){
               if(ship.isMotherShip || ship.isCarrierShip) {
                  model.scale.x =0.0025f; 
               }
            }
       phaser.width = model.scale.x;       
       phaser.model = model;
       phaser.phaserEmiter = ship;
       phaser.quadPlayerIndex = ship.player.index;
       phaserList.add(phaser);
    return phaser;
    }   
    public void addShipSelectRing(Quad ship)
    {
        shipSelectRing = new Quad();
        shipSelectRing.curentPosition = ship.curentPosition;
        shipSelectRing.alive = true;
        scale = (0.05f/scaleQuad);
        model = new Model(quad_ibo, textureUtil.ringTexture , quadData.playerList.get(race_index).selectyRing, shipSelectRing.curentPosition, ship.quadRotation, ship.scale);
        shipSelectRing.model = model;
        shipSelectRing.quadPlayerIndex = quadData.playerList.get(ship.quadPlayerIndex).index;
        ship.selectRing = shipSelectRing;
        quadData.shipSelectionRing.add(shipSelectRing);
    }
    public void addSunRing(Planet planet , Player player , Sun sun , float   size) {

        ring = new Quad();
        ring.curentPosition.x = sun.position.x;
        ring.curentPosition.y = sun.position.y;
        ring.curentPosition.z = 0.00026f;
        scale = (size/scaleQuad);
        model = new Model(quad_ibo, textureUtil.ringTexture , player.ring_index, ring.curentPosition, ring.quadRotation, new Vector3f(scale, scale, scale));
        ring.width = model.scale.x;
        ring.model = model;
        ring.quadPlayerIndex = player.index;

        if(sun.ring == null)    {
            sun.ring = ring;
            player.cStarList.add(sun);
            quadData.colonizedInfluenceRing.add(ring);
        } else {
            planet.player.cStarList.remove(sun);
            sun.ring = ring;
        }
    }
    public void addPlanetRind(Player player , Planet planet)  {

        if(planet.player != null  && planet.owner != player.index)    {
           planet.player.cPlanetList.remove(planet);
           if(planet.isWather) {
               planet.player.watherPList.remove(planet);
           }
           quadData.colonizedInfluenceRing.remove(planet.ring);
           planet.ring = null;
        }
  
        ring = new Quad();
        ring.curentPosition.x = planet.position.x;
        ring.curentPosition.y = planet.position.y;
        ring.curentPosition.z = 0.00026f;

        scale = (0.05f/scaleQuad);
        if(planet.isWather) {
            scale = (0.1f/scaleQuad);
            model = new Model(quad_ibo, textureUtil.ringTexture , player.ring_index, ring.curentPosition, ring.quadRotation, new Vector3f(scale, scale, scale));
        } else {
            scale = (0.05f/scaleQuad);
            model = new Model(quad_ibo, textureUtil.ringTexture , player.ring_index, ring.curentPosition, ring.quadRotation, new Vector3f(scale, scale, scale));
        }
    
        ring.width = model.scale.x;
        ring.model = model;
        ring.quadPlayerIndex = player.index;
        
        
        planet.ring = ring;
        planet.player = player;
        player.cPlanetList.add(planet);
        if(planet.isWather) {
            player.watherPList.add(planet);
        }
        planet.owner = player.index;
        
        

       quadData.colonizedInfluenceRing.add(ring);
    }
    public void addHex(Sun sun , Player player) { 
        
        
        hex = new Quad();
            hex.isHexBorder = true;
            hex.scale = new Vector3f(1,1,1);
            hex.curentPosition.x = 0;
            hex.curentPosition.y = 0;
            hex.curentPosition.z = 0;
            hex.quadRotation.x = 0;
            hex.quadRotation.y = 0;
            hex.quadRotation.z = 0;

            model = new Model(quad_ibo, textureUtil.board , player.hex_Border_index, hex.curentPosition, hex.quadRotation, hex.scale); 

            model.pointsList.get(0).x = sun.saved_position.x;
            model.pointsList.get(0).y = sun.saved_position.y;
            model.pointsList.get(0).z = sun.saved_position.z;
           
            
            model.pointsList.get(1).x = sun.intersectionPoints.get(0).borderPVector.x;
            model.pointsList.get(1).y = sun.intersectionPoints.get(0).borderPVector.y;
            model.pointsList.get(1).z = sun.intersectionPoints.get(0).borderPVector.z;  
            model.pointsList.get(2).x = sun.intersectionPoints.get(1).borderPVector.x;
            model.pointsList.get(2).y = sun.intersectionPoints.get(1).borderPVector.y;
            model.pointsList.get(2).z = sun.intersectionPoints.get(1).borderPVector.z;       
            model.pointsList.get(3).x = sun.intersectionPoints.get(2).borderPVector.x;
            model.pointsList.get(3).y = sun.intersectionPoints.get(2).borderPVector.y;
            model.pointsList.get(3).z = sun.intersectionPoints.get(2).borderPVector.z;     

            hex.model = model;
         player.bordersList.add(hex);       
//__________________________________________________________________________________________________

        hex = new Quad();
            hex.isHexBorder = true;
            hex.scale = new Vector3f(1,1,1);
            hex.curentPosition.x = 0;
            hex.curentPosition.y = 0;
            hex.curentPosition.z = 0;
            hex.quadRotation.x = 0;
            hex.quadRotation.y = 0;
            hex.quadRotation.z = 0;

            model = new Model(quad_ibo, textureUtil.board , player.hex_Border_index, hex.curentPosition, hex.quadRotation, hex.scale); 

            model.pointsList.get(0).x = sun.saved_position.x;
            model.pointsList.get(0).y = sun.saved_position.y;
            model.pointsList.get(0).z = sun.saved_position.z;
            model.pointsList.get(1).x = sun.intersectionPoints.get(2).borderPVector.x;
            model.pointsList.get(1).y = sun.intersectionPoints.get(2).borderPVector.y;
            model.pointsList.get(1).z = sun.intersectionPoints.get(2).borderPVector.z;  
            model.pointsList.get(2).x = sun.intersectionPoints.get(3).borderPVector.x;
            model.pointsList.get(2).y = sun.intersectionPoints.get(3).borderPVector.y;
            model.pointsList.get(2).z = sun.intersectionPoints.get(3).borderPVector.z;       
            model.pointsList.get(3).x = sun.intersectionPoints.get(4).borderPVector.x;
            model.pointsList.get(3).y = sun.intersectionPoints.get(4).borderPVector.y;
            model.pointsList.get(3).z = sun.intersectionPoints.get(4).borderPVector.z;     

            hex.model = model;
      player.bordersList.add(hex); 
   //________________________________________________________________________________________________________
   
        hex = new Quad();
            hex.isHexBorder = true;
            hex.scale = new Vector3f(1,1,1);
            hex.curentPosition.x = 0;
            hex.curentPosition.y = 0;
            hex.curentPosition.z = 0;
            hex.quadRotation.x = 0;
            hex.quadRotation.y = 0;
            hex.quadRotation.z = 0;

            model = new Model(quad_ibo, textureUtil.board , player.hex_Border_index, hex.curentPosition, hex.quadRotation, hex.scale); 

            model.pointsList.get(0).x = sun.saved_position.x;
            model.pointsList.get(0).y = sun.saved_position.y;
            model.pointsList.get(0).z = sun.saved_position.z;
            model.pointsList.get(1).x = sun.intersectionPoints.get(4).borderPVector.x;
            model.pointsList.get(1).y = sun.intersectionPoints.get(4).borderPVector.y;
            model.pointsList.get(1).z = sun.intersectionPoints.get(4).borderPVector.z;  
            model.pointsList.get(2).x = sun.intersectionPoints.get(5).borderPVector.x;
            model.pointsList.get(2).y = sun.intersectionPoints.get(5).borderPVector.y;
            model.pointsList.get(2).z = sun.intersectionPoints.get(5).borderPVector.z;       
            model.pointsList.get(3).x = sun.intersectionPoints.get(0).borderPVector.x;
            model.pointsList.get(3).y = sun.intersectionPoints.get(0).borderPVector.y;
            model.pointsList.get(3).z = sun.intersectionPoints.get(0).borderPVector.z;     

            hex.model = model;
       player.bordersList.add(hex);   
    }
    public void addPoint(float x,float y,float z , float rotX,float rotY,float rotZ , float w ,float h ,  int textIndex) {  
        point = new Quad();
        point.scale = new Vector3f(w,h,1);
        point.curentPosition.x = x;
        point.curentPosition.y = y;
        point.curentPosition.z = z;
        point.quadRotation.x = rotX;
        point.quadRotation.y = rotY;
        point.quadRotation.z = rotZ;
        model = new Model(quad_ibo, textureUtil.board , textIndex, point.curentPosition, point.quadRotation, point.scale);
        point.model = model;
        quadData.pointPrimitive.add(point); 
    }
    public void addSegmentLine(float   vector_1_x , float   vector_1_y , float   vector_2_x , float   vector_2_y   , float sc , int textIndex)
    {
        lineSegment = new Quad();

        lineSegment.curentPosition.x = 0;
        lineSegment.curentPosition.y = 0;
        lineSegment.curentPosition.z = 0.000275f;

        float delta_x = Math.abs(vector_1_x - vector_2_x)/2;
        float delta_y = Math.abs(vector_1_y - vector_2_y)/2;

        if(vector_1_x >= vector_2_x  &&  vector_1_y >= vector_2_y){
            lineSegment.curentPosition.x = vector_1_x - delta_x ;
            lineSegment.curentPosition.y = vector_1_y - delta_y ;
        }
        if(vector_1_x <vector_2_x  &&  vector_1_y < vector_2_y){
            lineSegment.curentPosition.x = vector_1_x + delta_x ;
            lineSegment.curentPosition.y = vector_1_y + delta_y ;
        }
        if(vector_1_x >= vector_2_x  && vector_1_y < vector_2_y){
            lineSegment.curentPosition.x = vector_1_x - delta_x ;
            lineSegment.curentPosition.y = vector_1_y + delta_y ;
        }
        if(vector_1_x < vector_2_x  &&  vector_1_y >= vector_2_y){
            lineSegment.curentPosition.x = vector_1_x + delta_x ;
            lineSegment.curentPosition.y = vector_1_y - delta_y ;
        }

        float scale  = (float) Math.sqrt(Math.pow(vector_1_x - vector_2_x, 2) + Math.pow(vector_1_y - vector_2_y, 2)) / 2;

        lineSegment.quadRotation.z = (float) ( - Math.toDegrees(Math.atan2(vector_2_x - vector_1_x ,  vector_2_y - vector_1_y)));

        model = new Model(quad_ibo, textureUtil.board , textIndex , lineSegment.curentPosition , lineSegment.quadRotation, new Vector3f((sc/scaleQuad), scale, 1));
        model.ANIMATION_RESET_V = 10;

        lineSegment.width = model.scale.x;
        lineSegment.model = model;
       
        quadData.linePrimitive.add(lineSegment);
    }
    public void addBorder(Vector3f vector_1 ,Vector3f vector_2   , Player player , float startAngle , boolean border_type){
        border = new Quad();
        border.isBorder = true;
        border.borderTranslateAngle = startAngle;
        
        border.curentPosition.x = 0;
        border.curentPosition.y = 0;
        border.curentPosition.z = 0.000275f;

        float delta_x = Math.abs(vector_1.x - vector_2.x)/2;
        float delta_y = Math.abs(vector_1.y - vector_2.y)/2;

        if(vector_1.x >= vector_2.x  &&  vector_1.y >= vector_2.y){
           border.curentPosition.x = vector_1.x - delta_x ;
           border.curentPosition.y = vector_1.y - delta_y ;
        }
        if(vector_1.x < vector_2.x  &&  vector_1.y < vector_2.y){
           border.curentPosition.x = vector_1.x + delta_x ;
           border.curentPosition.y = vector_1.y + delta_y ;
        }   
        if(vector_1.x >= vector_2.x  &&  vector_1.y < vector_2.y){
           border.curentPosition.x = vector_1.x - delta_x ;
           border.curentPosition.y = vector_1.y + delta_y ;
        }
        if(vector_1.x < vector_2.x  &&  vector_1.y >= vector_2.y){
           border.curentPosition.x = vector_1.x + delta_x ;
           border.curentPosition.y = vector_1.y - delta_y ;
        }          
          
        float scale  = (float) Math.sqrt(Math.pow(vector_1.x - vector_2.x, 2.) + Math.pow(vector_1.y - vector_2.y, 2)) / 2;     
        
        border.quadRotation.z = (float) ( - Math.toDegrees(Math.atan2(vector_2.x - vector_1.x ,  vector_2.y - vector_1.y)));      
        
       if(border_type)   {
            model = new Model(quad_ibo, textureUtil.board , player.boarder_tex_index , border.curentPosition , border.quadRotation, new Vector3f((0.0175f/scaleQuad), scale, 1));
       } else {
                   border.volatileBorder = false;
                   model = new Model(quad_ibo, textureUtil.board , player.segmented_boarder_tex_index , border.curentPosition , border.quadRotation, new Vector3f((0.0175f/scaleQuad), scale, 1));
              }
       
       model.ANIMATION_RESET_V = 10;
       
       border.width = model.scale.x;       
       border.model = model;
       border.quadPlayerIndex = player.index;
       border.border_vec_1 = vector_1;
       border.border_vec_2 = vector_2;
       
    player.bordersList.add(border);      
    }
    public void addPhaserEmiter(Quad ship ,  ArrayList<Quad>   phaserEmiter ) {

        emiter = new Quad();

        emiter.curentPosition.x = ship.curentPosition.x;
        emiter.curentPosition.y = ship.curentPosition.y;
        emiter.curentPosition.z = 0.00028f;

        float scale = (0.004f)/scaleQuad;
        emiter.quadRotation.z = ship.quadRotation.z;

         model = new Model(quad_ibo, textureUtil.phasetEmiter, ship.player.phaserEmiter_tex_index, ship.canon_1, emiter.quadRotation, new Vector3f(scale, scale, scale));
         model.ANIMATION_RESET_V = 10;

        emiter.width = model.scale.x;
        emiter.model = model;
        emiter.phaserEmiter = ship;
        emiter.quadPlayerIndex = ship.player.index;
        phaserEmiter.add(emiter);
    }
    public void addPhaserShield(Quad ship , ArrayList<Quad>  shieldList){
       shield = new Quad();
       
       shield.quadRotation.z = (float) (180 + 90 - Math.toDegrees(Math.atan2(ship.curentPosition.x - ship.target.x , ship.curentPosition.y - ship.target.y)));
       
        shield.curentPosition.x = ship.target.x;
        shield.curentPosition.y = ship.target.y;
        shield.curentPosition.z = 0.00028f;    
        
        float scale = (ship.targetShiep.width * 1.75f)/scaleQuad;
        model = new Model(quad_ibo, textureUtil.shieldTex,ship.targetShiep.player.shield_tex_index , ship.target, shield.quadRotation, new Vector3f(scale, scale, scale));
        model.ANIMATION_RESET_V = 10;
 
        shield.width = model.scale.x;
        shield.model = model;
        shield.player = ship.player;
        shield.quadPlayerIndex = ship.targetShiep.player.index;
     shieldList.add(shield);     
    }
    public void addShield(Quad ship ,Quad bullet, ArrayList<Quad>  shieldList , Vector3f position , float shieldSize) {
       shield = new Quad();
       
       shield.quadRotation.z = (float) (180 + 90  - Math.toDegrees(Math.atan2(bullet.curentPosition.x - ship.curentPosition.x, bullet.curentPosition.y - ship.curentPosition.y)));
       
        shield.curentPosition.x = ship.curentPosition.x;
        shield.curentPosition.y = ship.curentPosition.y;
        shield.curentPosition.z = 0.00028f;    
        
        scale = (ship.width * 1.75f);
      
        model = new Model(quad_ibo, textureUtil.shieldTex,ship.player.shield_tex_index , position, shield.quadRotation, new Vector3f(scale, scale, scale));
        model.ANIMATION_RESET_V = 3;
 
        shield.width = model.scale.x;
        shield.model = model;
        shield.player = ship.player;
        shield.quadPlayerIndex = ship.player.index;

     shieldList.add(shield);    
    }
    public void  addExplosion(Quad bullet , ArrayList<Quad>  explosionList , Vector3f position , float explosionSize , int explosion_index)  {
        
       explosion = new Quad();
       
        explosion.curentPosition.x = bullet.curentPosition.x;
        explosion.curentPosition.y = bullet.curentPosition.y;
        explosion.curentPosition.z = 0.00028f;       
            model = new Model(quad_ibo, textureUtil.explosionTex,explosion_index , position, explosion.quadRotation, new Vector3f(explosionSize, explosionSize, explosionSize));
            model.ANIMATION_RESET_V = 3;
        explosion.width = model.scale.x;
        explosion.model = model;
        explosion.quadPlayerIndex = bullet.player.index;
  
     explosionList.add(explosion);
    }

   public Quad  addColonizationShip(Planet planet ,  Player player  ,  float x , float y)   {
      
        ship = new Quad();
        ship.curentPosition.x = x;
        ship.curentPosition.y = y;
        ship.curentPosition.z = 0.003f;
        
        ship.endPosition.x = ship.curentPosition.x;
        ship.endPosition.y = ship.curentPosition.y;
        ship.endPosition.z = ship.curentPosition.z;
        
        ship.playerList = new ArrayList<Player>();       
        
        scale = 0.02f/scaleQuad;
        ship.shielWidth = (scale * 1.75f)/2;
        
        ship.scale.x = scale;
        ship.scale.y = scale;
        ship.scale.z = scale;

        model = new Model(quad_ibo, textureUtil.shipTex_max, player.ship_tex_index+1, ship.curentPosition, ship.quadRotation, ship.scale);
        if(planet != null)
        {
            ship.life = 5 + planet.resources.hullUpgradeLevel;
            
                    if(player.shieldTec) {
                      ship.shield = 5 + planet.resources.shieldUpgradeLevel;
                    } else {
                      ship.shield = -1;
                    }
            if(player.warpDriveTec) {
                ship.step_movement = (0.001f + (planet.resources.engineUpgradeLevel*0.001f)/2) /scaleQuad;
            } else {
                ship.step_movement = (0.001f + (planet.resources.engineUpgradeLevel*0.001f)/4)/scaleQuad;
            }

            
        } else {
                    ship.life = 5;
                    if(player.shieldTec) {
                      ship.shield = 5;
                    } else {
                      ship.shield = -1;  
                    }

                    if(player.warpDriveTec) {
                        ship.step_movement =0.001f/scaleQuad;
                    } else {
                        ship.step_movement = 0.001f/scaleQuad;
                    }
               }


        ship.canonPlasement = 0.54f;
        ship.canonStartengle = -90;
        ship.deltaAngle = 0;
        ship.shipName = "ColonizationShip";
        ship.width = model.scale.x;
        ship.model = model;
        ship.hasAtack = false;
        ship.player = player;
        ship.faction = player.faction;   
        ship.isColoniShip = true;
        ship.deployPlanet = planet;
        player.coloniShip = ship;
        ship.name = "colonization ship";
        
        player.shipList.add(ship);

       return ship;
    }
    public Quad   addCargoShip(Planet planet ,  Player player  ,  float x , float y)   {

        ship = new Quad();
        ship.curentPosition.x = x;
        ship.curentPosition.y = y;
        ship.curentPosition.z = 0.003f;

        ship.endPosition.x = ship.curentPosition.x;
        ship.endPosition.y = ship.curentPosition.y;
        ship.endPosition.z = ship.curentPosition.z;

        ship.playerList = new ArrayList<Player>();

        scale = 0.02f/scaleQuad;
        ship.shielWidth = (scale * 1.75f)/2;

        ship.scale.x = scale;
        ship.scale.y = scale;
        ship.scale.z = scale;

        model = new Model(quad_ibo, textureUtil.shipTex_max, player.ship_tex_index, ship.curentPosition, ship.quadRotation, ship.scale);
        if(planet != null)
        {
            ship.life = 5 + planet.resources.hullUpgradeLevel * 2;

            if(player.shieldTec) {
                ship.shield = 5 + planet.resources.shieldUpgradeLevel * 2;
            } else {
                ship.shield = -1;
            }

        } else {
            ship.life = 5;
            if(player.shieldTec) {
                ship.shield = 5;
            } else {
                ship.shield = -1;
            }
        }

        ship.width = model.scale.x;
        ship.model = model;
        ship.hasAtack = false;
        ship.player = player;
        if(player.warpDriveTec) {
            ship.step_movement = 0.001f + (planet.resources.engineUpgradeLevel*0.0002f)/scaleQuad;
        } else {
            ship.step_movement = 0.001f + (planet.resources.engineUpgradeLevel*0.0002f)/scaleQuad;
        }




        ship.faction = player.faction;
        ship.isCargoShip = true;
        ship.deployPlanet = planet;

         ship.shipName = "addCargoShip";

        player.shipList.add(ship);

        return ship;
    }
    public Quad addExploreShip(Planet planet ,  Player player  ,  float x , float y) 
    {
        ship = new Quad();
        ship.curentPosition.x = x;
        ship.curentPosition.y = y;
        ship.curentPosition.z = 0.003f;
        
        if(planet != null)
        {
            planet.shipCount++;
            player.shipCount++;   
        }
        
        ship.endPosition.x = ship.curentPosition.x;
        ship.endPosition.y = ship.curentPosition.y;
        ship.endPosition.z = ship.curentPosition.z;
        
        ship.playerList = new ArrayList<Player>(); 
        
        scale = 0.0125f/scaleQuad;
        ship.shielWidth = (scale * 1.75f)/2;
        
        ship.scale.x = scale;
        ship.scale.y = scale;
        ship.scale.z = scale;        
        
        model = new Model(quad_ibo, textureUtil.shipTex_max, player.ship_tex_index+3, ship.curentPosition, ship.quadRotation, ship.scale);



        ship.life = 100 + (planet.resources.hullUpgradeLevel * 10);
        
        if(player.shieldTec) {
             ship.shield = 100 + (planet.resources.shieldUpgradeLevel * 10);
         } else {
            ship.shield = -1;
        }

        ship.bulletDamage = 0.5f + planet.resources.gunsUpgradeLevel;
        ship.phaserDamage +=  planet.resources.gunsUpgradeLevel;
        ship.step_movement = (0.0001f + planet.resources.engineUpgradeLevel*0.00002f)/scaleQuad;
        ship.temp_step_movement = (0.0001f + planet.resources.engineUpgradeLevel*0.00002f)/scaleQuad;

        ship.canonPlasement = 0.2f;
        ship.canonStartengle = -90;
        ship.deltaAngle = 20;

        ship.bulet_size = 0.00075f;
        ship.width = model.scale.x;
        ship.model = model;
        ship.shipFireBehavior = 5;
        ship.bullet_attack_reset = 75;
        ship.procisionTargeting = 25;
        ship.enableSmartTargeting = true;
        ship.hasAtack = true;
        ship.player = player;

        ship.atackRange = 0.5f/scaleQuad;
        ship.faction = player.faction;   
        ship.deployPlanet = planet;
        ship.isExploreShip = true;
        player.shipList.add(ship);  
        ship.shipName = "ExploreShip";

    return ship;
    }

    public Quad addFreagateShip(Planet planet ,  Player player  ,  float x , float y) {
        ship = new Quad();
        ship.curentPosition.x = x;
        ship.curentPosition.y = y;
        ship.curentPosition.z = 0.003f;

        ship.endPosition.x = ship.curentPosition.x;
        ship.endPosition.y = ship.curentPosition.y;
        ship.endPosition.z = ship.curentPosition.z;
        
        ship.playerList = new ArrayList<Player>();         
        
        scale = 0.0175f/scaleQuad;
        ship.shielWidth = (scale * 1.75f)/2;
        
        ship.scale.x = scale;
        ship.scale.y = scale;
        ship.scale.z = scale;   
        
        if(planet != null)
        {
                planet.shipCount++;
                player.shipCount++;

                if(player.index == 0 || player.index == 1) {
                    model = new Model(quad_ibo, textureUtil.shipTex_max, player.ship_tex_index + 7, ship.curentPosition, ship.quadRotation, ship.scale);
                } else {
                    if(player.index == 7) {
                        model = new Model(quad_ibo, textureUtil.shipTex_max, player.ship_tex_index + 8, ship.curentPosition, ship.quadRotation, ship.scale);
                    } else {
                        model = new Model(quad_ibo, textureUtil.shipTex_max, player.ship_tex_index + 2, ship.curentPosition, ship.quadRotation, ship.scale);
                    }
                }


        
                ship.life = 100 + (planet.resources.hullUpgradeLevel*10);
        
                     if(player.shieldTec) {
                       ship.shield = 100 + (planet.resources.shieldUpgradeLevel*10);
                     } else {
                       ship.shield = -1;  
                     }        
        
                ship.bulletDamage = 2.5f + planet.resources.gunsUpgradeLevel;
                ship.phaserDamage +=  planet.resources.gunsUpgradeLevel;
                ship.step_movement = (0.0001f + planet.resources.engineUpgradeLevel * 0.00002f) /scaleQuad;
                ship.temp_step_movement = (0.0001f + planet.resources.engineUpgradeLevel * 0.00002f) /scaleQuad;

        }   else  {
                    ship.life =  100 ;

                        if(player.shieldTec) {
                             ship.shield = 100;
                        } else {
                            ship.shield = -1;
                        }

                    ship.bulletDamage = 2.5f ;
                    ship.step_movement = 0.0001f /scaleQuad;
                  }

        ship.canonPlasement = 0.2f;
        ship.canonStartengle = -90;
        ship.deltaAngle = 20;

        ship.shipFireBehavior = 5;
        ship.bulet_size = 0.001f;
        ship.width = model.scale.x;
        ship.model = model;
        ship.enableSmartTargeting = true;
        ship.hasAtack = true;
        ship.player = player;
        ship.procisionTargeting = 25;
        ship.bullet_attack_reset = 100;
        ship.bulletDamage = 1;
        ship.atackRange = 0.5f/scaleQuad;
        ship.faction = player.faction; 
        ship.deployPlanet = planet;
        ship.isFreagateShip = true;
        ship.shipName = "ExploreShip";
        player.shipList.add(ship);  
    return ship;
    }
    public Quad addDestroierShip(Planet planet ,  Player player  ,  float x , float y) {
        ship = new Quad();
        ship.curentPosition.x = x;
        ship.curentPosition.y = y;
        ship.curentPosition.z = 0.003f;

        if(planet != null)
        {
            planet.shipCount++;
            player.shipCount++;
        }

        ship.endPosition.x = ship.curentPosition.x;
        ship.endPosition.y = ship.curentPosition.y;
        ship.endPosition.z = ship.curentPosition.z;

        ship.playerList = new ArrayList<Player>();

        float scale = 0.02f/scaleQuad;
        ship.shielWidth = (scale * 1.75f)/2;



        ship.scale.x = scale;
        ship.scale.y = scale;
        ship.scale.z = scale;

        model = new Model(quad_ibo, textureUtil.shipTex_max, player.ship_tex_index+4, ship.curentPosition, ship.quadRotation, ship.scale);

        ship.life = 150f + (planet.resources.hullUpgradeLevel*15);
        ship.step_movement = (0.0001f + planet.resources.engineUpgradeLevel*0.00002f) /scaleQuad;
        ship.temp_step_movement = (0.0001f + planet.resources.engineUpgradeLevel*0.00002f) /scaleQuad;

        if(player.shieldTec) {
            ship.shield = 100f + (planet.resources.shieldUpgradeLevel*10);
        } else {
            ship.shield = -1;
        }

        ship.bulletDamage = 2f + planet.resources.gunsUpgradeLevel;
        ship.phaserDamage +=  planet.resources.gunsUpgradeLevel;


        ship.canonPlasement = 0.2f;
        ship.canonStartengle = -90;
        ship.deltaAngle = 20;

        ship.width = model.scale.x;
        ship.model = model;
        ship.enableSmartTargeting = true;
        ship.hasAtack = true;
        ship.procisionTargeting = 25;
        ship.shipFireBehavior = 7;
        ship.player = player;

        ship.atackRange = 0.5f/scaleQuad;
        ship.isDestroierShip = true;
        ship.deployPlanet = planet;
        ship.faction = player.faction;
        ship.shipName = "FreagateShip";
        ship.bullet_attack_reset = 300;
        player.shipList.add(ship);
      return ship;
    }
    public Quad addCarrierShip(Planet planet ,  Player player  ,  float x , float y) {
        ship = new Quad();
        ship.curentPosition.x = x;
        ship.curentPosition.y = y;
        ship.curentPosition.z = 0.003f;

        
        ship.endPosition.x = ship.curentPosition.x;
        ship.endPosition.y = ship.curentPosition.y;
        ship.endPosition.z = ship.curentPosition.z;
        
        ship.playerList = new ArrayList<Player>();       
        
        float scale;
        if(player.index == 5) {
            scale = 0.04f/scaleQuad;
            ship.shielWidth = (scale * 1.75f)/2;
        } else {
            scale = 0.03f/scaleQuad;
            ship.shielWidth = (scale * 1.75f)/2;
        }
        ship.scale.x = scale;
        ship.scale.y = scale;
        ship.scale.z = scale;

        model = new Model(quad_ibo, textureUtil.shipTex_max, player.ship_tex_index+5, ship.curentPosition, ship.quadRotation, ship.scale);
        
        
        if(planet != null)
        {
            planet.shipCount ++;
            player.shipCount ++; 
            
            
        ship.life = 100f + (planet.resources.hullUpgradeLevel*10);
      
        
                     if(player.shieldTec) {
                       ship.shield = 100 + (planet.resources.shieldUpgradeLevel*10);
                     } else {
                       ship.shield = -1;  
                     }        
        
        ship.bulletDamage = 5f + planet.resources.gunsUpgradeLevel;
        ship.phaserDamage +=  planet.resources.gunsUpgradeLevel;
        ship.phaserDamage +=  planet.resources.gunsUpgradeLevel;
        ship.step_movement = 0.0001f + (planet.resources.engineUpgradeLevel * 0.00002f)/scaleQuad;
        ship.temp_step_movement = 0.0001f + (planet.resources.engineUpgradeLevel * 0.00002f)/scaleQuad;
            
            
            
        }   else {
                     ship.life = 100f ;    
      
                     if(player.shieldTec) {
                       ship.shield = 100 ;
                     } else {
                       ship.shield = -1;  
                     }        
        
                     ship.bulletDamage = 0.1f;
        
                 }


        if(player.index == 5) {
            ship.life = 250;
            ship.shield = 250;
            ship.phaserDamage = 10;

        }

        ship.canonPlasement = 0.2f;
        ship.canonStartengle = -90;
        ship.deltaAngle = 20;
        
        ship.width = model.scale.x;
        ship.model = model;
        ship.enableSmartTargeting = true;
        ship.procisionTargeting = 25;
        ship.player = player;
        ship.bullet_attack_reset = 200;
        ship.shipFireBehavior = 6;
        ship.step_movement = 0.0001f/scaleQuad;
        ship.atackRange = 0.5f/scaleQuad;
        ship.isCarrierShip = true;
        ship.deployPlanet = planet;
        ship.shipName = "CarrierShip";
        ship.faction = player.faction;     
 
        int interceptor_count = 0;
        if(player.InterceptorTec) {
           interceptor_count = 5;
        }
        
                for(int i=0 ; i<interceptor_count ; i++)
                {
                       addInterceptorShip(planet, player, x, y , ship);
                }
       player.shipList.add(ship);   
       return ship;
    }
  public Quad addInterceptorShip(Planet planet ,  Player player  ,  float x , float y  , Quad ship) {
                    interceptor = new Quad();
                    interceptor.curentPosition.x = x;
                    interceptor.curentPosition.y = y;
                    interceptor.curentPosition.z = 0.008f;

                    if(planet != null)
                    {
                        planet.shipCount++;
                        player.shipCount++;   
                    }       

                    interceptor.endPosition.x = interceptor.curentPosition.x;
                    interceptor.endPosition.y = interceptor.curentPosition.y;
                    interceptor.endPosition.z = interceptor.curentPosition.z;

                    interceptor.playerList = new ArrayList<Player>();       

                    float scalee = 0.005f/scaleQuad;
                    interceptor.shielWidth = (scalee * 1.75f)/2;
                    
                    interceptor.scale.x = scalee;
                    interceptor.scale.y = scalee;
                    interceptor.scale.z = scalee;
                    
                    model = new Model(quad_ibo, textureUtil.shipTex_max, player.ship_tex_index+2, interceptor.curentPosition, interceptor.quadRotation, interceptor.scale);

                    interceptor.life = 10f;    

                    
                     if(player.shieldTec) {
                       interceptor.shield = 35;
                     } else {
                       interceptor.shield = -1;  
                     }                    
                    
                    interceptor.bulletDamage = 2f;


                    interceptor.interceptor_min_range = 0.02f;
                    interceptor.width = model.scale.x;
                    interceptor.model = model;
                    interceptor.parent = ship;
                    interceptor.enableSmartTargeting = true;
                    interceptor.hasAtack = true;
                    interceptor.bullet_attack_reset = 50;
                    interceptor.procisionTargeting = 300;
                    interceptor.player = player;
                    interceptor.phaser_attack_reset = 500;
                    interceptor.shipFireBehavior = 1;
                    interceptor.interceptor_is_dock = true;
                    interceptor.interceptor_bulletStopFireNumbet = 2;
                    interceptor.step_movement = 0.00075f/scaleQuad;
                    interceptor.atackRange = 0.5f/scaleQuad;
                    interceptor.isInterceptor = true;
                    interceptor.deployPlanet = planet;
                    interceptor.faction = player.faction;   
                    ship.shipName = "interceptor";
                    interceptor.rotationRadius = 5;
                    player.shipList.add(interceptor); 
                    ship.interceptors.add(interceptor);
      return interceptor;
  }
    public Quad addMotherShip(Planet planet ,  Player player  ,  float x , float y) {
         ship = new Quad();
        ship.curentPosition.x = x;
        ship.curentPosition.y = y;
        ship.curentPosition.z = 0.003f;
        
        if(planet != null)
        {
            planet.shipCount++;
            player.shipCount++;   
        }       
        
        ship.endPosition.x = ship.curentPosition.x;
        ship.endPosition.y = ship.curentPosition.y;
        ship.endPosition.z = ship.curentPosition.z;
        
        ship.playerList = new ArrayList<Player>();

        float scale;
        if(player.index == 5) {
             scale = 0.05f/scaleQuad;
            ship.shielWidth = (scale * 1.75f)/2;
        } else {
             scale = 0.04f/scaleQuad;
            ship.shielWidth = (scale * 1.75f)/2;
        }
        ship.scale.x = scale;
        ship.scale.y = scale;
        ship.scale.z = scale;

        model = new Model(quad_ibo, textureUtil.shipTex_max, player.ship_tex_index+6, ship.curentPosition, ship.quadRotation, ship.scale);
        
        ship.canonStartengle = 45;
        ship.deltaAngle = 120;
        ship.canonPlasement = 0.425f;       
        
        ship.life = 50 + (planet.resources.hullUpgradeLevel*5);
        
                     if(player.shieldTec) {
                       ship.shield = 250 + (planet.resources.shieldUpgradeLevel*20);
                     } else {
                       ship.shield = -1;  
                     }        
        
        ship.bulletDamage = 20 + (planet.resources.gunsUpgradeLevel*2);
        ship.phaserDamage +=  planet.resources.gunsUpgradeLevel;
        ship.step_movement = (0.0001f+ (planet.resources.engineUpgradeLevel*0.00002f))/scaleQuad;
        ship.temp_step_movement = (0.0001f+ (planet.resources.engineUpgradeLevel*0.00002f))/scaleQuad;

        if(player.index == 5) {
            ship.life = 250+ (planet.resources.hullUpgradeLevel*5);
            if(player.shieldTec) {
                ship.shield = 500+ (planet.resources.shieldUpgradeLevel*25);
            } else {
                ship.shield = -1;
            }
            ship.bulletDamage = 20 + (planet.resources.gunsUpgradeLevel*2);
            ship.phaserDamage = 10+(planet.resources.gunsUpgradeLevel*2);
        }

        ship.canonPlasement = 0.2f;
        ship.canonStartengle = -90;
        ship.deltaAngle = 20;

        ship.shipFireBehavior = 4;
        ship.width = model.scale.x;
        ship.model = model;
        ship.enableSmartTargeting = true;
        ship.hasAtack = true;
        ship.procisionTargeting = 25;
        ship.player = player;

        ship.atackRange = 0.5f/scaleQuad;
        ship.isMotherShip = true;
        ship.deployPlanet = planet;
        ship.faction = player.faction;  
        ship.shipName = "mother ship";
        player.shipList.add(ship);   
    return ship;
    }
  
}
