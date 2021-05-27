
package spacecomander;

import AILogic.ResValidation;
import Border.Board;
import Camera.Camera;
import Input.MouseClickCallback;
import Maths.Maths;
import Planet.Planet;
import Player.Player;
import QuadModel.Model;
import Sun.Sun;
import Util.RandomFloat;
import audio.AudioMaster;
import audio.SoundMainClass;
import audio.Source;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuadUtil {
   
public int  targetLock_count;
public int  targetLock_resetValue = 10;
RandomFloat rand = new RandomFloat();
Random random = new Random();
Camera        camera;
Planet planet;
Sun sun;
Model mapM;
int index;
float isShieldOn;
boolean check;
MouseClickCallback  mouseClick;

    float distance_x;
    float distance_y;
    float inter_distance_x;
    float inter_distance_y;
    
    public QuadsManagement  quadManagement;
    Board bord3;
    Maths maths;
    float   absDistanceX , absDistanceY;
    float  width;
    public int selected_ship_count;
    public int coloni_ship_count;
    public int explorer_ship_count;
    public int fregate_ship_count;
    public int destroier_ship_count;
    public int carrier_ship_count;
    public int interceptor_ship_count;
    public int mother_ship_count;
    public int cargo_ship_count;
    public int  race_index ;

    QuadData quadData;
    SoundMainClass  soundMainClass;
    public Source      forceFieldSource ,  torpedoSource , shieldImpactSource_1 , shieldImpactSource_2  , shieldImpactSource_3;
    int    shieldImpactSound    , forceFieldSound  , torpedoSound    ;
    ResValidation resValidation;
     
    int reset = 10;
    int count = 0;
    
    
    public QuadUtil(QuadsManagement  quadManagement , Camera camera  , Model mapM  , Maths maths,int  race_index ,Board bord3,QuadData quadData,ResValidation resValidation) throws IOException {
        this.quadManagement = quadManagement;
        this.camera = camera;
        this.mapM = mapM;
        this.maths = maths;
        this.race_index=race_index;
        this.bord3 = bord3;
        this.quadData = quadData;
        soundMainClass = new SoundMainClass(camera);
        this.resValidation = resValidation;
    }
    public void getSelectedShips(ArrayList<Player> playerList ){
        resetShipSelection();
        for(Player player : playerList)   {
           for (Quad ship :player.shipList) { 
                if(ship.isSlected)  {
                    IncrementShipSelected(ship);
                }
           }
        }   
    }
    public void getDragAndDropSelectedQuads(ArrayList<Player> playerList , float x_drag_start , float y_drag_start , float  x_drag_end , float y_drag_end){
        quadData.shipSelectionRing.clear();
        resetShipSelection();
        for(Player player : playerList)   {
           for (Quad ship :player.shipList) { 
                if(player.index == race_index)
                if (ship.curentPosition.x > x_drag_start && ship.curentPosition.x < x_drag_end || ship.curentPosition.x < x_drag_start && ship.curentPosition.x > x_drag_end) {
                    if (ship.curentPosition.y > y_drag_start && ship.curentPosition.y < y_drag_end || ship.curentPosition.y < y_drag_start && ship.curentPosition.y > y_drag_end) {
                        if(ship.isSelectebel) {
                            if (ship.isInterceptor) {
                                if (!ship.interceptor_is_dock) {
                                    ship.isSlected = true;
                                    quadManagement.addShipSelectRing(ship);
                                    selected_ship_count++;
                                    IncrementShipSelected(ship);                                 
                                }
                            } else {
                                ship.isSlected = true;
                                quadManagement.addShipSelectRing(ship); 
                                IncrementShipSelected(ship);
                            }
                        }
                    } else {
                        ship.isSlected = false;
                    }
                }     else {
                              ship.isSlected = false;
                           }
           }
        }
    }
    public void resetShipSelection(){
        selected_ship_count = 0;
        coloni_ship_count = 0;
        explorer_ship_count = 0;
        fregate_ship_count = 0;
        destroier_ship_count = 0;
        carrier_ship_count =0;
        interceptor_ship_count = 0;
        mother_ship_count = 0;
        cargo_ship_count = 0;    
    }
    public void IncrementShipSelected(Quad ship) {
        selected_ship_count++;
        if (ship.isInterceptor) {
            interceptor_ship_count++;
        }
        if (ship.isColoniShip) {
            coloni_ship_count++;
        }
        if (ship.isExploreShip) {
            explorer_ship_count++;
        }
        if (ship.isFreagateShip) {
            fregate_ship_count++;
        }
        if (ship.isDestroierShip) {
            destroier_ship_count++;
        }
        if (ship.isCarrierShip) {
            carrier_ship_count++;
        }
        if (ship.isMotherShip) {
            mother_ship_count++;
        }
        if (ship.isCargoShip) {
            cargo_ship_count++;
        }
    }
    public void checkShipSelection(ArrayList<Player> playerList , float  x , float y)  {
        quadData.shipSelectionRing.clear();
        selected_ship_count = 0;
        coloni_ship_count = 0;
        explorer_ship_count = 0;
        fregate_ship_count = 0;
        destroier_ship_count = 0;
        carrier_ship_count =0;
        interceptor_ship_count = 0;
        mother_ship_count = 0;
        cargo_ship_count = 0;
        for(Player player : playerList) {
            for (Quad ship : player.shipList) {
                if(ship.isSelectebel)
                if(ship.player.index == race_index) {
                    if (x < ship.curentPosition.x + ship.width && x > ship.curentPosition.x - ship.width) {
                        if (y < ship.curentPosition.y + ship.width && y > ship.curentPosition.y - ship.width) {
                            if(!ship.interceptor_is_dock){
                                ship.isSlected = true;
                                quadManagement.addShipSelectRing(ship);
                                selected_ship_count++;
                                if (ship.isColoniShip) {
                                    coloni_ship_count++;
                                }
                                if (ship.isExploreShip) {
                                    explorer_ship_count++;
                                }
                                if (ship.isFreagateShip) {
                                    fregate_ship_count++;
                                }
                                if (ship.isDestroierShip) {
                                    destroier_ship_count++;
                                }
                                if (ship.isCarrierShip) {
                                    carrier_ship_count++;
                                }
                                if (ship.isMotherShip) {
                                    mother_ship_count++;
                                }
                                if (ship.isCargoShip) {
                                    cargo_ship_count++;
                                }
                            }
                        } else {
                            ship.isSlected = false;
                        }
                    } else {
                        ship.isSlected = false;
                    }
                }
            }
        }
    }
    public void translateSelectedShips(ArrayList<Player> playerList , float  x ,float y) {
            for (Player player : playerList)  {
                for(Quad ship : player.shipList)   {
                    if (ship.isSlected)    {
                                    if(ship.player.warpDriveTec) {
                                        if(!ship.isInterceptor)     {
                                            if(maths.calcPointsPointDistance(x, y, ship.curentPosition.x, ship.curentPosition.y) > 0.12f)    {
                                                ship.warpJumpTec = true;
                                            } else {
                                                        if(!ship.shipIsInWarp) {
                                                               ship.warpJumpTec = false;
                                                        }
                                                   }
                                        }   
                                    }
                                    if(!ship.isColoniShip && !ship.isCargoShip) {
                                        if(selected_ship_count >1) {
                                            ship.translateQuad(x + (rand.genFloatNumber(0, 0.1f, 0.2f) / 5) / quadManagement.scaleQuad, y + (rand.genFloatNumber(0, 0.1f, 0.2f) / 5) / quadManagement.scaleQuad);
                                        } else {
                                            ship.translateQuad(x, y);
                                        }
                                    } else {
                                             ship.translateQuad(x, y);
                                    }
    
                            ship.interceptosReturn_x = x;
                            ship.interceptosReturn_y = y;
                    }
                }
            }
    }
    public void fire(Quad ship , ArrayList<Quad>  bulletList ,ArrayList<Quad>  explosionList , ArrayList<Quad>  phaserList  ,  ArrayList<Quad>  shieldList , ArrayList<Quad>      phaserEmiter){

        if(!ship.isInterceptor)
        {
            ship.count_bullet_fire++;
            if (ship.count_bullet_fire == ship.bullet_attack_reset) {
                ship.count_bullet_fire = 0;
                if (ship.attack && !ship.isInterceptor) {
                    if (ship.isMotherShip) {
                        if (ship.motherShipFireCount > ship.mother_Ship_Fire_Count_limit) {
                            ship.bullet_attack_reset = ship.mother_ship_fire_spacer;
                            ship.motherShipFireCount = 0;
                            ship.canonAtack = 4;
                            fireTorpedo(ship, bulletList, explosionList);
                            if(ship.enableToRender){
                                soundMainClass.playTorpedoSound();
                            }
                            if (ship.player.laserTec) {
                                firePhaser(ship, shieldList, phaserList, explosionList , bulletList,     phaserEmiter);
                            }
                            ship.canonAtack = random.nextInt(3);

                        } else {
                            ship.bullet_attack_reset = ship.mother_ship_atack_speed;
                        }
                        ship.motherShipFireCount++;
                        fireTorpedo(ship, bulletList, explosionList);
                        if(ship.enableToRender){
                             soundMainClass.playRapidFireDisruptorSound();
                        }  
                    } else {
                        fireTorpedo(ship, bulletList, explosionList);
                        if(ship.enableToRender){
                          soundMainClass.playStandarDisrupterFire();
                        }
                        ship.count_phaser_fire++;
                        if (ship.count_phaser_fire == ship.phaser_attack_reset) {
                            ship.count_phaser_fire = 0;
                            if (ship.player.laserTec) {
                                firePhaser(ship, shieldList, phaserList, explosionList , bulletList,    phaserEmiter);
                            }
                        }
                    }
                } else {
                    if (ship.isInterceptor) {
                        ship.count_phaser_fire++;
                        if (ship.count_phaser_fire == ship.phaser_attack_reset) {
                            ship.count_phaser_fire = 0;
                            if (ship.player.laserTec) {
                                firePhaser(ship, shieldList, phaserList, explosionList , bulletList ,    phaserEmiter);
                            }
                        }
                    }
                }
            }
        } else {
            if(ship.attack) {
                ship.count_phaser_fire++;
                if (ship.count_phaser_fire == ship.phaser_attack_reset) {
                    ship.count_phaser_fire = 0;
                    if (ship.player.laserTec) {
                        firePhaser(ship, shieldList, phaserList, explosionList , bulletList ,       phaserEmiter);
                    }
                }
            }
        }
    }
    public void firePhaser(Quad ship , ArrayList<Quad> shieldList , ArrayList<Quad> phaserList , ArrayList<Quad>  explosionList ,ArrayList<Quad>  bulletList , ArrayList<Quad>      phaserEmiter){
        firePhaser(ship, phaserList , bulletList ,    phaserEmiter);
     
        if(ship.targetShiep.shield > 0 && ship.targetShiep.enableToRender){
           quadManagement.addPhaserShield(ship , shieldList);
        } 
        if(ship.phaserDamage > ship.targetShiep.life+ship.targetShiep.shield){
            ship.targetShiep.alive = false;
            if(ship.targetShiep.isSlected)
            {
                decreseSelectedShipCount(ship.targetShiep);
            }
           if(ship.targetShiep.enableToRender)  {
                soundMainClass.playExplosionSource();
                quadManagement.addExplosion(ship.targetShiep, explosionList, ship.targetShiep.curentPosition, ship.targetShiep.model.scale.x * 1.5f , 10);
           }
        } else {
                    if(ship.targetShiep.shield > 0) {
                        if(!ship.targetShiep.isColoniShip)    {
                           ship.targetShiep.shield -= ship.phaserDamage;
                        } else {
                                  ship.targetShiep.shield -= (ship.phaserDamage*3);
                               }
                    } else {
                        if(!ship.targetShiep.isColoniShip)    {
                            ship.targetShiep.life -= ship.phaserDamage;
                        } else {
                                  ship.targetShiep.life -= (ship.phaserDamage*3);
                               }
                    }
               } 
    }
    public void  bulletCheck(Quad bullet, ArrayList<Quad> explosionList , ArrayList<Quad> shieldList) {  
        width = 0;
        Quad ship =null;
        Player player_B;
        
            if (bullet.quadCount == bullet.quadStop) {

                bullet.minShipDistance = 1000;          
                for (int j = 0; j < bullet.playerList.size(); j++) {
                    player_B = bullet.playerList.get(j);
                     
                    for (int k = 0; k < bullet.playerList.get(j).shipList.size(); k++)
                    {
                        ship = bullet.playerList.get(j).shipList.get(k);
                                                                           
                            absDistanceX = Math.abs(bullet.curentPosition.x - ship.curentPosition.x);
                            absDistanceY = Math.abs(bullet.curentPosition.y - ship.curentPosition.y);
                            width = ship.width * 2.42f;

                            if ((absDistanceX + absDistanceY) < bullet.minShipDistance) {
                                bullet.minShipDistance = (absDistanceX + absDistanceY);
                            }
                            if (absDistanceX < width && absDistanceY < width)
                            {
                                float distance = (float) Math.sqrt(Math.pow(bullet.curentPosition.x - ship.curentPosition.x, 2.) + Math.pow(bullet.curentPosition.y - ship.curentPosition.y, 2));
                                isShieldOn = 1;
                                if (ship.shield > 0) {
                                    isShieldOn = 2.0f;
                                }
                                if (distance < ship.width * isShieldOn) {
                                    if (bullet.bulletDamage > ship.life + ship.shield)
                                    {
                                        ship.alive = false;                                
                                        if(ship.isSlected) {
                                            decreseSelectedShipCount(ship);
                                        }
                                        if(ship.enableToRender) {
                                            quadManagement.addExplosion(ship, explosionList, ship.curentPosition, ship.model.scale.x * 1.5f , 10);
                                            soundMainClass.playExplosionSource();
                                        }
                                    } else {
                                        if (ship.shield < 0) {
                                            ship.life -= bullet.bulletDamage;
                                        } else {
                                            ship.shield -= bullet.bulletDamage;
                                            if(ship.enableToRender) {
                                                quadManagement.addShield(ship,bullet, shieldList , ship.curentPosition, ship.width*1.25f);     
                                                if(!ship.isInterceptor) {
                                                    soundMainClass.playShieldImpactSource();
                                                }
                                            }
                                        }
                                    }
                                    if(bullet.enableToRender) {
                                        if(camera.getPosition().z < 3.5f) {
                                            quadManagement.addExplosion(bullet, explosionList, bullet.curentPosition, bullet.model.scale.x * 1.5f , bullet.player.explosion_tex_index);
                                        }
                                    }
                                    stop(bullet);
                                    bullet.alive=false;
                                }
                            }

                    }
                }
                if (bullet.minShipDistance > 0.05f) {
                    if (bullet.quadCount == bullet.quadStop) {
                        bullet.quadCount = 0;
                    }
                } else {
                    bullet.quadCount = bullet.quadStop;
                }
            } else {
                bullet.quadCount++;
            }
        if(checkConstrainsQ(bullet)){
            bullet.enableToRender = true;
        } else {
            bullet.enableToRender = false;
        }
    }
    public void decreseSelectedShipCount(Quad ship) {
        ship.selectRing.alive = false;
        ship.isSlected = false;
        selected_ship_count--;
        if (ship.isColoniShip) {
            coloni_ship_count--;
        }
        if (ship.isCargoShip) {
            cargo_ship_count--;
        }
        if (ship.isExploreShip) {
            explorer_ship_count--;
        }
        if (ship.isFreagateShip) {
            fregate_ship_count--;
        }
        if (ship.isDestroierShip) {
            destroier_ship_count--;
        }
        if (ship.isCarrierShip) {
            carrier_ship_count--;
        }
        if (ship.isMotherShip) {
            mother_ship_count--;
        }
        mouseClick.shipSelectCountPrint();
    }
    public void shipCheck(Player player , Quad ship ,  ArrayList<Sun>   sunList  , ArrayList<Quad>  bulletList , ArrayList<Quad>  explosionList , ArrayList<Quad>  phaserList , ArrayList<Quad>  shieldList ,ArrayList<Quad>   phaserEmiter , int i)
    {
        planet = null;
        distance_x       = Math.abs(ship.endPosition.x - ship.curentPosition.x);
        distance_y       = Math.abs(ship.endPosition.y - ship.curentPosition.y);
        inter_distance_x = Math.abs(ship.interceptosReturn_x - ship.curentPosition.x);
        inter_distance_y = Math.abs(ship.interceptosReturn_y - ship.curentPosition.x);
       
        if(distance_x < 0.01f && distance_y < 0.01f) {
            stop(ship);
            if(!ship.isInterceptor) {
                    if (ship.isColoniShip)
                    {
                        planet = checkPlanetColonizationColizion(ship, sunList);
                        if (planet != null)
                        {
                            if (planet.player == null)
                            {    
                                quadManagement.addPlanetRind(ship.player, planet);                               
                                 
                                player.coloniShip = null;
                                ship.alive = false;
                                if(ship.selectRing != null) {
                                    decreseSelectedShipCount(ship);
                                    ship.selectRing.alive = false;
                                    coloni_ship_count = 0;
                                    selected_ship_count--;  
                                }

                                Sun sun = sunList.get(planet.parentStar.star_index);
                                sun.player_planets_own[ship.player.index]++;
                              
                                if(sun.player == null)
                                {
                                    ship.player.cStarList.add(sun);
                                    sun.player = ship.player;
                                    sun.playerIndex = ship.player.index;
 
                                } else {   
                                               
                                                Player shipPlayer   =  quadData.playerList.get(ship.player.index);
                                                Player sunPlayer    =  quadData.playerList.get(sun.player.index);
                                                    
                                                if(resetStarBorder(sun , shipPlayer.index)) 
                                                {
                                                    quadData.playerList.get(sun.player.index).cStarList.remove(sun);
                                                    shipPlayer.cStarList.add(sun);
                                                    sun.player = quadData.playerList.get(ship.player.index);
                                                    sun.playerIndex = shipPlayer.index;
                                                    bord3.buildBorder(sunPlayer);
                                                    bord3.buildBorder(shipPlayer);
                                                    checkNeiborn();
                                                } 
                                       }

                                    bord3.buildBorder(ship.player);
                                    checkNeiborn();
                            } else {  
                          
                                      if (quadData.playerList.get(ship.player.index).enemy[quadData.playerList.get(planet.player.index).index]) {  
                                        
                                        Player planetPlayer  = quadData.playerList.get(planet.player.index);
                                        Player shipPlayer   =  quadData.playerList.get(ship.player.index);
                                        Sun sun = sunList.get(planet.parentStar.star_index);
                                        
                                        sun.player_planets_own[shipPlayer.index]++;
                                        sun.player_planets_own[planetPlayer.index]--;
                                        
                                        quadManagement.addPlanetRind(player, planet);                                        
                                        
                                            if(!shipPlayer.cStarList.contains(sun))
                                            {  
                                                if(resetStarBorder(sun , shipPlayer.index)) 
                                                {
                                                    quadData.playerList.get(sun.player.index).cStarList.remove(sun);
                                                    shipPlayer.cStarList.add(sun);
                                                    sun.playerIndex = shipPlayer.index;
                                                    sun.player = quadData.playerList.get(ship.player.index);
                                                } 
                                            }
                                        checkNeiborn();
                                        bord3.buildBorder(planetPlayer);
                                        bord3.buildBorder(shipPlayer);

                                        player.coloniShip = null;
                                        ship.alive = false;
                                        if(ship.selectRing != null) {
                                            ship.selectRing.alive = false;
                                            coloni_ship_count = 0;
                                            selected_ship_count--;
                                        }

                                    } else {
                                        player.coloniShip = null;
                                        ship.alive = false;
                                        if(ship.selectRing != null) {
                                            ship.selectRing.alive = false;
                                            coloni_ship_count = 0;
                                            selected_ship_count--;
                                        }
                                    }
                            }
                            if (planet.player.cPlanetList.size() == 1) {
                                planet.resources.population = 20;
                                planet.resources.workersAvaible = 2;
                                planet.resources.energyWorker = 0;
                                planet.resources.metalOreWorker = 0;
                                planet.resources.mineralsWorker = 0;
                                planet.resources.carbonWorker = 0;
                                planet.resources.gasWorker = 0;
                                planet.resources.resourcesMinedCount = 0;
                            } else {
                                planet.resources.population = 10;
                                planet.resources.workersAvaible = 1;
                                planet.resources.energyWorker = 0;
                                planet.resources.metalOreWorker = 0;
                                planet.resources.mineralsWorker = 0;
                                planet.resources.carbonWorker = 0;
                                planet.resources.gasWorker = 0;
                                planet.resources.resourcesMinedCount = 0;
                            }
      
                        } else {

                            Sun star = checkSunColision(ship, sunList);
                            if (star != null) {
                                star.exploredList.set(player.index, true);
                            }
                            player.colonizationProcess = false;
                        }
                    }
                    if (ship.isCargoShip)
                    {
                        planet = checkPlanetColonizationColizion(ship, sunList);
                        if (planet != null) {
                            if(ship.autoCargoShip)    {
                                if(!ship.deployPlanet.isWather) {
                                    resValidation.cargoShip_Planet_Transfer(planet, ship, 50);
                                } else {
                                    resValidation.cargoShip_Planet_Transfer(planet, ship, 1);
                                }
                            } else {  
                                resValidation.planet_CargoShip_Transfer(planet, ship);
                            }
                            
                            ship.alive = false;
                          
                            if(ship.selectRing != null) {
                                ship.selectRing.alive = false;
                                cargo_ship_count = 0;
                                selected_ship_count--;
                                decreseSelectedShipCount(ship);
                            }

                        } else {
                            if(ship.shipDecomision) {
                                ship.alive = false;
                                if(ship.selectRing != null) {
                                    ship.selectRing.alive = false;
                                    cargo_ship_count = 0;
                                    selected_ship_count--;
                                    decreseSelectedShipCount(ship);
                                }
                            }
                        }
                    }

            } else {
                interceptorStopCase(ship);
            }
        }
        setupCanon(ship);
        if(SpaceComander.game_pause == false) {
           fire(ship, bulletList, explosionList, phaserList, shieldList ,       phaserEmiter);
        }
            if (count == reset) {
                if (Math.abs(ship.curentPosition.x) > 15 || Math.abs(ship.curentPosition.y) > 15) {
                    ship.alive = false;
                    if (ship.isSlected) {
                        ship.selectRing.alive = false;
                        resetShipSelection();
                    }
                    ship.deployPlanet.shipCount--;
                }
                count = 0;
            }
            count++;
    }
    public void interceptorStopCase(Quad ship)
    {
        if (!ship.targetLockOn)    {
            if (ship.parent != null)    {
                if (!ship.parent.releseInterceptor)  
                {
                    if (Math.abs(ship.endPosition.x - ship.parent.curentPosition.x) > 0.003f ||  Math.abs(ship.endPosition.y - ship.parent.curentPosition.y) > 0.003f)
                    {
                        ship.translateQuad(ship.parent.curentPosition.x, ship.parent.curentPosition.y);
                        ship.interceptor_bulletCountFired = ship.interceptor_bulletStopFireNumbet;
                        ship.interceptorReturn = true;
                        ship.interceptor_is_dock = false;
                    } else {
                                ship.curentPosition.x = ship.parent.curentPosition.x;
                                ship.curentPosition.y = ship.parent.curentPosition.y;
                                ship.interceptor_is_dock = true;
                                if(ship.isSlected) {
                                    decreseSelectedShipCount(ship);
                                }
                           }
                } else {
                    ship.translateQuad(ship.interceptosReturn_x, ship.interceptosReturn_y);
                    ship.interceptor_bulletCountFired = ship.interceptor_bulletStopFireNumbet;
                    ship.interceptor_is_dock = false;
                }
            } else {
                        if(inter_distance_x > 0.02f && inter_distance_x > 0.02f) {
                            ship.translateQuad(ship.interceptosReturn_x, ship.interceptosReturn_y);
                        }             
                        ship.interceptor_bulletCountFired = ship.interceptor_bulletStopFireNumbet;
                        ship.interceptor_is_dock = false;
                   }
        } else {
            if (Math.abs(ship.curentPosition.x - ship.target.x) > 0.02f / quadManagement.scaleQuad || Math.abs(ship.curentPosition.y - ship.target.y) > 0.02f / quadManagement.scaleQuad)
            {
                ship.translateQuad(ship.target.x, ship.target.y);
                ship.targetFireEnable = true;
                ship.interceptor_bulletCountFired = 0;
                ship.interceptor_is_dock = false;

                if(ship.targetShiep.isInterceptor){
                    ship.interceptor_min_range = 0.02f;
                } else {
                    ship.interceptor_min_range = 0.02f;
                }
            } else {
                ship.translateQuad(ship.target.x + rand.genShipRandFloat(0.9f, 6) / quadManagement.scaleQuad, ship.target.y + rand.genShipRandFloat(0.9f, 6) / quadManagement.scaleQuad);
                ship.targetFireEnable = false;
                ship.interceptor_is_dock = false;
            }
        }
    }
    public void checkTargetLockOn(ArrayList<Player> playerList) {
        Player player_1 = null,player_2 = null;
        boolean inRange = false;
        targetLock_count++;
        if (targetLock_count == targetLock_resetValue) 
        {
            targetLock_count = 0;
            for (int i = 0; i < playerList.size(); i++) {
                player_1 = playerList.get(i);
                for (int j = 0; j < player_1.shipList.size(); j++) 
                {   Quad ship_1 = player_1.shipList.get(j);
                    if(ship_1.hasAtack) 
                    {        
                        check = false;
                        ship_1.minDistanceAtack = 1000;
                        ship_1.playerList.clear();

                        for (int p = 0; p < playerList.size(); p++){
                            player_2 = playerList.get(p);
                            if (player_1.enemy[player_2.index]) 
                            {
                                for (int k = 0; k < player_2.shipList.size(); k++) 
                                {     Quad ship_2 = player_2.shipList.get(k);

                                            if (Math.abs(ship_1.curentPosition.x - ship_2.curentPosition.x) < ship_1.atackRange * 2f && Math.abs(ship_1.curentPosition.y - ship_2.curentPosition.y) < ship_1.atackRange * 2f) {
                                                if(i == SpaceComander.race_index) {
                                                    player_2.renderPlayerElements = true;
                                                }
                                                if (!ship_1.playerList.contains(player_2)) {
                                                    ship_1.playerList.add(player_2);
                                                }
                                                ship_1.distanceEnemyShip = (float) Math.sqrt(Math.pow(ship_1.curentPosition.x - ship_2.curentPosition.x, 2) + Math.pow(ship_1.curentPosition.y - ship_2.curentPosition.y, 2));

                                                if (ship_1.distanceEnemyShip < ship_1.atackRange) {
                                                      lockOnLogic(ship_1,ship_2,player_1,player_2);
                                                      check = true;
                                                }
                                            }
                                }

                            }
                        }
                        if (!check) 
                        {
                            ship_1.targetLockOn = false;
                            ship_1.attack = false;
                        } else {
                                  if(ship_1.targetShiep != null)
                                  {
                                      if (!ship_1.targetShiep.alive) {
                                          ship_1.targetLockOn = false;
                                          ship_1.attack = false;
                                      }
                                  }
                               }

                    }
                    if (checkConstrainsQ(ship_1)) {
                        ship_1.enableToRender = true;
                    } else {
                        ship_1.enableToRender = false;
                    }
                }
            }
        }
    } 

    public void lockOnLogic(Quad ship_1,Quad ship_2 ,Player player_1 ,Player player_2) {
        if (!ship_1.targetLockOn) {
              if(player_1.enemy[player_2.index]){
                ship_1.attack = true;
                ship_1.targetLockOn = true;
                ship_1.target = ship_2.curentPosition;
                ship_1.targetShiep = ship_2;
            }
        } else {
            if (ship_1.enableSmartTargeting) {
                if (ship_1.distanceEnemyShip < ship_1.minDistanceAtack) {
                    ship_1.target = ship_2.curentPosition;
                    ship_1.targetShiep = ship_2;
                    ship_1.minDistanceAtack = ship_1.distanceEnemyShip;
                }
            }
        }
    }
    public Sun checkSunColision(Quad ship , ArrayList<Sun> sunList)  {
        sun = null;
        for(Sun  star : sunList){
            if(Math.abs(ship.curentPosition.x - star.position.x) < 0.4f  &&  Math.abs(ship.curentPosition.y - star.position.y) < 0.4f) {
                sun  = star;
            }
        }
        return sun;
    }
    public Planet checkPlanetColonizationColizion(Quad ship , ArrayList<Sun> sunList) {
        planet = null;
        for(Sun  sun : sunList){
                for(Planet planet : sun.listaPlanete){
                      if(Math.abs(ship.curentPosition.x - planet.position.x) < 0.02f  &&  Math.abs(ship.curentPosition.y - planet.position.y) < 0.02f){
                          if(ship.isColoniShip) {
                              if (planet.owner != ship.player.index) {
                                  this.planet = planet;
                              }
                          }
                          if(ship.isCargoShip) {
                              if (planet.owner == ship.player.index) {
                                  this.planet = planet;
                              }
                          }
                      }
                }
        }
        return planet;
    }  
    public void moveBullet(Quad bullet){
       if(!SpaceComander.game_pause) 
       {
            bullet.curentPosition.x += bullet.speed.x;
            bullet.curentPosition.y += bullet.speed.y;          
            if(bullet.buuletType == 1)
            {
               bullet.count_randomize_angle++;
               if(bullet.count_randomize_angle == bullet.randomize_angle_reset)
               {  bullet.count_randomize_angle = 0;
                  bullet.quadRotation.z = random.nextInt(360);
               }
            }
       }
    }
    public void stop(Quad ship)  {  
        if(ship.warpJumpTec)
        {
            if(ship.shipIsInWarp){
                   ship.disable_warp = true;
            } else {
                      ship.stop();
                      ship.reshapeQuad();
                   }
        } else {
                    ship.stop();
               }
    }    
    public void warp(Quad ship) 
    {
             if(ship.enable_warp)  {   
                if(ship.distorsionAmount < 20) 
                { 
                     ship.warp_step_counter++; 
                     if(ship.warp_step_counter <= ship.warp_step_reset)
                     {
                        ship.warp_step_counter = 0;
                        ship.model.scale.y +=0.001;
                        ship.distorsionAmount++;
                        ship.step_movement += 0.0001f;
                        ship.setXYStepSpeed();
                     }
                } else { 
                          ship.warp_step_counter = 0;
                          ship.enable_warp = false;
                          ship.shipIsInWarp = true;
                       }
            }
        
            if(ship.disable_warp)    {          
                 if(ship.distorsionAmount > 0) 
                 { 
                      ship.warp_step_counter++; 
                      if(ship.warp_step_counter <= ship.warp_step_reset)
                      {
                            ship.warp_step_counter = 0;
                            ship.model.scale.y -=0.001;
                            ship.distorsionAmount--;
                            ship.step_movement -= 0.0001f;
                            ship.setXYStepSpeed();
                      }
                 } else {
                           ship.warp_step_counter = 0;
                           if(maths.calcPointsPointDistance(ship.endPosition.x, ship.endPosition.y, ship.curentPosition.x, ship.curentPosition.y) > 0.12f) {
                               ship.enable_warp = true;
                               ship.oneTime = true;
                           }
                           ship.disable_warp = false;
                           ship.shipIsInWarp = false;
                           ship.stop();
                           ship.reshapeQuad();
                        }
            }   
    }
    public void move(Quad ship ,  ArrayList<Quad> bulletList , ArrayList<Quad> explosionList)   {  
       if(ship.warpJumpTec && !ship.isInterceptor) {
            if(ship.hasAtack) {
                warp(ship);
            }
       }
       ship.calcAngleCompare();
       ship.angle_rotate = 360 - ship.angleStartEnd;
       ship.quadRotation.z = ship.angle_rotate;
        
       if(ship.rotationEnable)
       {
            if(ship.rotate_left)  {
               rotateShipLeft(ship);
            } 
            if(ship.rotate_right) {
                rotateShipRight(ship);
            }
       }
       if(!SpaceComander.game_pause) {
            ship.curentPosition.x += ship.speed.x;
            ship.curentPosition.y += ship.speed.y;
            ship.canon_1.x += ship.speed.x;
            ship.canon_1.y += ship.speed.y;
            ship.canon_2.x += ship.speed.x;
            ship.canon_2.y += ship.speed.y;
            ship.canon_3.x += ship.speed.x;
            ship.canon_3.y += ship.speed.y;
       }
      interceptorFireCase(ship,bulletList,explosionList);
    }
    public void interceptorFireCase(Quad ship , ArrayList<Quad> bulletList , ArrayList<Quad> explosionList)
    {
        if(ship.isInterceptor && ship.attack)
        {
            ship.count_bullet_fire++;
            if(ship.count_bullet_fire > ship.bullet_attack_reset)
            {
                ship.count_bullet_fire = 0;

                if(Math.abs(ship.curentPosition.x - ship.target.x) > ship.interceptor_min_range || Math.abs(ship.curentPosition.y - ship.target.y) > ship.interceptor_min_range)    {
                    if(ship.interceptor_bulletCountFired < ship.interceptor_bulletStopFireNumbet)
                    {
                        if(ship.enableInterceptorAtack)
                        {
                            ship.translateQuad(ship.target.x, ship.target.y);
                            fireTorpedo(ship, bulletList, explosionList);
                            ship.interceptor_bulletCountFired++;
                        }
                        if (ship.interceptor_bulletCountFired == ship.interceptor_bulletStopFireNumbet)
                        {
                            ship.translateQuad(ship.target.x + rand.genShipRandFloat(0.9f, 6) / quadManagement.scaleQuad, ship.target.y + rand.genShipRandFloat(0.9f, 6) / quadManagement.scaleQuad);
                            ship.enableInterceptorAtack = false;
                        }
                    }
                }
            }
        }
    }
    public void rotateShipLeft(Quad quad) {
            if(quad.rotationEnable) {   
                if(quad.angleStartEnd  <= 0) {
                    quad.angleStartEnd = 360;
                }
                quad.angleStartEnd -= quad.rotationRadius;
                quad.setXYStepSpeed();
            }
            if((Math.abs(quad.angleCompare - quad.angleStartEnd))  < quad.cancelRotation) {
              
               quad.calcAngleStartEnd();
               quad.setXYStepSpeed();
               quad.angle_rotate = 360 - quad.angleStartEnd;
               quad.quadRotation.z =  quad.angle_rotate;
               
               if(quad.targetFireEnable && quad.isInterceptor)   { 
                   quad.targetFireEnable = false;
                   quad.interceptor_bulletCountFired = 0;
               }
               if(!quad.oneTime)   {
                  quad.distorsionAmount = 0;
                  quad.enable_warp = true;
                  quad.oneTime = true;
               }
               quad.rotationEnable = false;
               quad.rotate_left = false;
               quad.enableInterceptorAtack = true;
            }      
    }
    public void rotateShipRight(Quad quad) {
            if(quad.rotationEnable) {
                if(quad.angleStartEnd  >=360) {
                    quad.angleStartEnd = 0;
                }
                quad.angleStartEnd += quad.rotationRadius;
                quad.setXYStepSpeed();
            }      
            if((Math.abs(quad.angleCompare - quad.angleStartEnd))  <= quad.cancelRotation)   {
             
               quad.calcAngleStartEnd();
               quad.setXYStepSpeed();
               quad.angle_rotate = 360 - quad.angleStartEnd;
               quad.quadRotation.z =  quad.angle_rotate;
               
               if(quad.targetFireEnable && quad.isInterceptor)  { 
                   quad.targetFireEnable = false;
                   quad.interceptor_bulletCountFired = 0;
               }  
               if(!quad.oneTime)   {
                  quad.distorsionAmount = 0;
                  quad.enable_warp = true;
                  quad.oneTime = true;
               } 
               quad.rotationEnable = false;
               quad.rotate_right = false;
               quad.enableInterceptorAtack = true;
            } 
    }    
    
    public void firePhaser(Quad ship , ArrayList<Quad>  phaserList , ArrayList<Quad>  bulletList ,ArrayList<Quad>        phaserEmiter)  {
        if(ship.curentPosition.x !=  ship.target.x  && ship.curentPosition.y != ship.target.y){
            if(ship.enableToRender || ship.targetShiep.enableToRender) {
                Quad phaser = quadManagement.addPhaser(ship , phaserList);
                soundMainClass.playPhaserSource(ship);        
                if(ship.player.index != 5) {
                    quadManagement.addPhaserEmiter(ship, phaserEmiter);
                } else {
                    if(!ship.isCarrierShip && !ship.isMotherShip) {
                        quadManagement.addPhaserEmiter(ship, phaserEmiter);
                    }
                }
            }
        }
    }
    public void fireTorpedo(Quad ship  , ArrayList<Quad>  bulletList , ArrayList<Quad>  explosionList) 
    {
       Quad bullet;
       float angle;
       if(ship.shipFireBehavior == 4) {

                   if (ship.canonAtack == 0) {

                       ship.bulet_size = 0.0005f;
                       bullet = quadManagement.addBullet(0, ship, bulletList, ship.canon_2, ship.bulet_size);
                       angle  = (float) Math.toDegrees(Math.atan2(ship.targetShiep.curentPosition.x - bullet.curentPosition.x , ship.targetShiep.curentPosition.y - bullet.curentPosition.y));
                       bullet.quadRotation.z = 180-angle;
                       bullet.model.scale.y = bullet.model.scale.x * 8;
                       bullet.step_movement = 0.0018f / quadManagement.scaleQuad;
                       bullet.buuletType = 0;
                       bullet.bulletDamage = 1 + ship.deployPlanet.resources.gunsUpgradeLevel;
                       bullet.playerList = ship.playerList;
                       bullet.translateQuad(ship.target.x + 0.002f, ship.target.y + 0.002f);

                       bullet = quadManagement.addBullet(0, ship, bulletList, ship.canon_2, ship.bulet_size);
                       angle  = (float) Math.toDegrees(Math.atan2(ship.targetShiep.curentPosition.x - bullet.curentPosition.x , ship.targetShiep.curentPosition.y - bullet.curentPosition.y));
                       bullet.quadRotation.z = 180-angle;
                       bullet.model.scale.y = bullet.model.scale.x * 8;
                       bullet.step_movement = 0.0018f / quadManagement.scaleQuad;
                       bullet.buuletType = 0;
                       bullet.bulletDamage = 1 + ship.deployPlanet.resources.gunsUpgradeLevel;
                       bullet.playerList = ship.playerList;
                       bullet.translateQuad(ship.target.x - 0.002f, ship.target.y - 0.002f); 
                   }
                   if (ship.canonAtack == 1) {

                       ship.bulet_size = 0.0005f;
                       bullet = quadManagement.addBullet(0, ship, bulletList, ship.canon_2, ship.bulet_size);
                       angle  = (float) Math.toDegrees(Math.atan2(ship.targetShiep.curentPosition.x - bullet.curentPosition.x , ship.targetShiep.curentPosition.y - bullet.curentPosition.y));
                       bullet.quadRotation.z = 180-angle;
                       bullet.model.scale.y = bullet.model.scale.x * 5;
                       bullet.step_movement = 0.0018f / quadManagement.scaleQuad;
                       bullet.buuletType = 0;
                       bullet.bulletDamage = 1 + ship.deployPlanet.resources.gunsUpgradeLevel;
                       bullet.playerList = ship.playerList;
                       bullet.translateQuad(ship.target.x + 0.002f, ship.target.y + 0.002f);

                       bullet = quadManagement.addBullet(0, ship, bulletList, ship.canon_2, ship.bulet_size);
                       angle  = (float) Math.toDegrees(Math.atan2(ship.targetShiep.curentPosition.x - bullet.curentPosition.x , ship.targetShiep.curentPosition.y - bullet.curentPosition.y));
                       bullet.quadRotation.z = 180-angle;
                       bullet.model.scale.y = bullet.model.scale.x * 5;
                       bullet.step_movement = 0.0018f / quadManagement.scaleQuad;
                       bullet.buuletType = 0;
                       bullet.bulletDamage = 1 + ship.deployPlanet.resources.gunsUpgradeLevel;
                       bullet.playerList = ship.playerList;
                       bullet.translateQuad(ship.target.x - 0.002f, ship.target.y - 0.002f);
                   }
                   if (ship.canonAtack == 2) {
                       ship.bulet_size = 0.0005f;
                       bullet = quadManagement.addBullet(0, ship, bulletList, ship.canon_3, ship.bulet_size);
                       angle  = (float) Math.toDegrees(Math.atan2(ship.targetShiep.curentPosition.x - bullet.curentPosition.x , ship.targetShiep.curentPosition.y - bullet.curentPosition.y));
                       bullet.quadRotation.z = 180-angle;
                       bullet.model.scale.y = bullet.model.scale.x * 5;
                       bullet.step_movement = 0.0018f / quadManagement.scaleQuad;
                       bullet.buuletType = 0;
                       bullet.bulletDamage = 1 + ship.deployPlanet.resources.gunsUpgradeLevel;
                       bullet.playerList = ship.playerList;
                       bullet.translateQuad(ship.target.x + 0.002f, ship.target.y + 0.002f);

                       bullet = quadManagement.addBullet(0, ship, bulletList, ship.canon_3, ship.bulet_size);
                       angle  = (float) Math.toDegrees(Math.atan2(ship.targetShiep.curentPosition.x - bullet.curentPosition.x , ship.targetShiep.curentPosition.y - bullet.curentPosition.y));
                       bullet.quadRotation.z = 180-angle;
                       bullet.model.scale.y = bullet.model.scale.x * 8;
                       bullet.step_movement = 0.0018f / quadManagement.scaleQuad;
                       bullet.buuletType = 0;
                       bullet.bulletDamage = 1 + ship.deployPlanet.resources.gunsUpgradeLevel;
                       bullet.playerList = ship.playerList;
                       bullet.translateQuad(ship.target.x - 0.002f, ship.target.y - 0.002f);
                   }
                   if (ship.canonAtack == 4) {
                       ship.bulet_size = 0.0045f;
                       if(ship.player.torpedoTec) {
                           bullet = quadManagement.addBullet(1, ship, bulletList, ship.curentPosition, ship.bulet_size);
                       } else {
                           ship.bulet_size = 0.003f;
                           bullet = quadManagement.addBullet(0, ship, bulletList, ship.curentPosition, ship.bulet_size);
                       }
                       bullet.step_movement = 0.00125f / quadManagement.scaleQuad;
                       if (ship.isFreagateShip) {
                           bullet.step_movement = 0.001f / quadManagement.scaleQuad;
                       }
                       bullet.bulletDamage = bullet.bulletDamage + ship.deployPlanet.resources.gunsUpgradeLevel;
                       bullet.buuletType = 1;
                       bullet.playerList = ship.playerList;
                       bullet.translateQuad(ship.target.x + 0.002f, ship.target.y + 0.002f);
                   }

       } else {
           
                    if(ship.shipFireBehavior == 1)
                    {
                        ship.bulet_size = 0.001f;
                        bullet =  quadManagement.addBullet(0 ,ship, bulletList,ship.canon_1 , ship.bulet_size);
                            bullet.step_movement = 0.0018f/quadManagement.scaleQuad;
                            bullet.bulletDamage = bullet.bulletDamage + ship.deployPlanet.resources.gunsUpgradeLevel;
                            bullet.playerList = ship.playerList;    
                            bullet.translateQuad(ship.target.x  + rand.genFloatNumber(0 ,0.1f , 0.99f)/ship.procisionTargeting   ,   ship.target.y + rand.genFloatNumber(0 ,0.1f , 0.99f)/ship.procisionTargeting);
                            if(ship.enableToRender) {
                                 quadManagement.addExplosion(ship, explosionList, ship.canon_1 , bullet.width , bullet.player.explosion_tex_index);
                            }

                        bullet =  quadManagement.addBullet(0 ,ship, bulletList,ship.canon_2 , ship.bulet_size);
                            bullet.step_movement = 0.0018f/quadManagement.scaleQuad;
                            bullet.bulletDamage = bullet.bulletDamage + ship.deployPlanet.resources.gunsUpgradeLevel;
                            bullet.playerList = ship.playerList;
                            bullet.translateQuad(ship.target.x  + rand.genFloatNumber(0 ,0.1f , 0.99f)/ship.procisionTargeting   ,   ship.target.y + rand.genFloatNumber(0 ,0.1f , 0.99f)/ship.procisionTargeting);
                            if(ship.enableToRender) {
                                 quadManagement.addExplosion(ship, explosionList, ship.canon_2 , bullet.width , bullet.player.explosion_tex_index); 
                            }
                            
                    }  else {

                                if(ship.shipFireBehavior == 2)
                                {
                                    ship.bulet_size = 0.0025f;
                                    bullet =  quadManagement.addBullet(0 ,ship, bulletList,ship.curentPosition ,  ship.bulet_size);
                                    bullet.step_movement = 0.0015f/quadManagement.scaleQuad;
                                        bullet.bulletDamage = bullet.bulletDamage + ship.deployPlanet.resources.gunsUpgradeLevel;
                                        bullet.playerList = ship.playerList;
                                        bullet.translateQuad(ship.target.x  + rand.genFloatNumber(0 ,0.1f , 0.99f)/ship.procisionTargeting   ,   ship.target.y + rand.genFloatNumber(0 ,0.1f , 0.99f)/ship.procisionTargeting);
                                } else {

                                    if(ship.shipFireBehavior == 3)
                                    {
                                        ship.bulet_size = 0.004f;
                                        bullet =  quadManagement.addBullet(0 ,ship, bulletList,ship.curentPosition ,  ship.bulet_size);
                                        bullet.step_movement = 0.0015f/quadManagement.scaleQuad;
                                        bullet.bulletDamage = bullet.bulletDamage + ship.deployPlanet.resources.gunsUpgradeLevel;
                                        bullet.playerList = ship.playerList;
                                        bullet.translateQuad(ship.target.x  + rand.genFloatNumber(0 ,0.1f , 0.99f)/ship.procisionTargeting   ,   ship.target.y + rand.genFloatNumber(0 ,0.1f , 0.99f)/ship.procisionTargeting);
                                    } else {

                                        if(ship.shipFireBehavior == 5)
                                        {
                                            bullet =  quadManagement.addBullet(0 ,ship, bulletList,ship.curentPosition , ship.bulet_size);
                                            bullet.step_movement = 0.003f/quadManagement.scaleQuad;
                                            bullet.bulletDamage = 1f;
                                            bullet.playerList = ship.playerList;
                                            bullet.translateQuad(ship.target.x ,   ship.target.y);
                                            angle  = (float) Math.toDegrees(Math.atan2(ship.targetShiep.curentPosition.x - bullet.curentPosition.x , ship.targetShiep.curentPosition.y - bullet.curentPosition.y));
                                            bullet.quadRotation.z = 180-angle;
                                            bullet.model.scale.y = bullet.model.scale.x * 8;

                                        } else {

                                            if(ship.shipFireBehavior == 6)
                                            {
                                                ship.bulet_size = 0.004f;
                                                bullet =  quadManagement.addBullet(0 ,ship, bulletList,ship.curentPosition , ship.bulet_size );
                                                bullet.step_movement = 0.00125f/quadManagement.scaleQuad;
                                                bullet.bulletDamage = 5f+ship.deployPlanet.resources.gunsUpgradeLevel;
                                                bullet.playerList = ship.playerList;
                                                bullet.translateQuad(ship.target.x  ,   ship.target.y);

                                            } else {
                                                if(ship.shipFireBehavior == 7){
                                                    ship.bulet_size = 0.004f;
                                                    bullet =  quadManagement.addBullet(0 ,ship, bulletList,ship.curentPosition , ship.bulet_size);
                                                    bullet.step_movement = 0.0015f/quadManagement.scaleQuad;
                                                    bullet.bulletDamage = 5f+ship.deployPlanet.resources.gunsUpgradeLevel;
                                                    bullet.playerList = ship.playerList;
                                                    bullet.translateQuad(ship.target.x  ,  ship.target.y);
                                                }
                                            }
                                        }

                                    }

                                }
                            }
              }       
    } 
public void setupCanon(Quad quad){
         
    if(quad.angle_rotate != quad.temp_angle_rotate)
    {
       float x0 = (float) ((quad.width*quad.canonPlasement)  *  Math.cos(Math.toRadians(quad.angle_rotate-quad.canonStartengle + quad.deltaAngle)));
       float y0 = (float) ((quad.width*quad.canonPlasement)  *  Math.sin(Math.toRadians(quad.angle_rotate-quad.canonStartengle + quad.deltaAngle)));
       float x1 = (float) ((quad.width*quad.canonPlasement)  *  Math.cos(Math.toRadians(quad.angle_rotate-quad.canonStartengle - quad.deltaAngle)));
       float y1 = (float) ((quad.width*quad.canonPlasement)  *  Math.sin(Math.toRadians(quad.angle_rotate-quad.canonStartengle - quad.deltaAngle)));
       float x2 = (float) ((quad.width*quad.canonPlasement)  *  Math.cos(Math.toRadians(quad.angle_rotate-quad.canonStartengle)));
       float y2 = (float) ((quad.width*quad.canonPlasement)  *  Math.sin(Math.toRadians(quad.angle_rotate-quad.canonStartengle)));
        
        quad.canon_1.x = (float) (quad.curentPosition.x+x0);
        quad.canon_1.y = (float) (quad.curentPosition.y+y0); 
        quad.canon_2.x = (float) (quad.curentPosition.x+x1);
        quad.canon_2.y = (float) (quad.curentPosition.y+y1); 
        quad.canon_3.x = (float) (quad.curentPosition.x+x2);
        quad.canon_3.y = (float) (quad.curentPosition.y+y2); 
        
        quad.canon_1_x = x0;
        quad.canon_1_y = y0;
        quad.canon_2_x = x1;
        quad.canon_2_y = y1;        
        quad.canon_3_x = x2;
        quad.canon_3_y = y2;
        
        quad.temp_angle_rotate = quad.angle_rotate;
        
    } else {
                quad.canon_1.x = (float) (quad.curentPosition.x+quad.canon_1_x);
                quad.canon_1.y = (float) (quad.curentPosition.y+quad.canon_1_y); 
                quad.canon_2.x = (float) (quad.curentPosition.x+quad.canon_2_x);
                quad.canon_2.y = (float) (quad.curentPosition.y+quad.canon_2_y); 
                quad.canon_3.x = (float) (quad.curentPosition.x+quad.canon_3_x);
                quad.canon_3.y = (float) (quad.curentPosition.y+quad.canon_3_y);  
           }
} 
    public boolean  resetStarBorder(Sun sun  , int id) 
    {
       boolean done  = false;
       boolean equal = false; 
       int max = -1;
       int player_index =-1;
       
       for(int i=0 ; i<sun.player_planets_own.length ; i++)   {
            for(int j=0 ; j<sun.player_planets_own.length ; j++)    {
                 if(i != j)     {
                      if(sun.player_planets_own[i] != 0 && sun.player_planets_own[j] != 0)    {
                         if(sun.player_planets_own[i] == sun.player_planets_own[j])     {
                             equal = true;
                         }
                      }
                 }
            }
       }
       
       if(!equal)    {
          for(int i=0 ; i<sun.player_planets_own.length ; i++)  {
              if(sun.player_planets_own[i] > max)   {
                 max = sun.player_planets_own[i];
                 player_index = i;
              }
          }
       } else {
                if(sun.player_planets_own[sun.player.index] == 0) {
                    player_index = id;
                }
              }
       
       if(max != -1)   {
           if(id == player_index) {
                done = true;
           }
       }
       
       return done; 
    } 
    public boolean checkConstrainsQ(Quad  quad) {
    boolean check = false;
        if(quad.curentPosition.x > camera.getPosition().x - mapM.scale.x   &&  quad.curentPosition.x < camera.getPosition().x + mapM.scale.x){
            if(quad.curentPosition.y > camera.getPosition().y - mapM.scale.y   &&  quad.curentPosition.y < camera.getPosition().y + mapM.scale.y){
               check = true;
            }        
        }
        return check;
    }
    public void checkNeiborn() {
        for (int i = 0; i < quadData.playerList.size(); i++) {
            Player player = quadData.playerList.get(i);
            resetSheardBorder(player);
            for (int j = 0; j < player.cStarList.size(); j++) {
                Sun sun = player.cStarList.get(j);
                for(Sun star : sun.neighborhood){
                    if(star.player != null) {
                       if(star.player.index != player.index) {
                          player.playerSharedBorderLenght[star.player.index]++;
                       }
                    }
                }
            }
        }
    }
    public void resetSheardBorder(Player player){
        for (int i = 0; i < quadData.playerList.size(); i++) {
             player.playerSharedBorderLenght[quadData.playerList.get(i).index] = 0;
        }
    }
}
