
package AILogic;

import Camera.Camera;
import Campaign.Color;
import Menu.MenuData;
import Planet.Planet;
import Player.Player;
import QuadModel.Model;
import Sun.Sun;
import TextUtil.TextData;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;
import spacecomander.Quad;
import spacecomander.QuadData;
import spacecomander.QuadsManagement;
import spacecomander.SpaceComander;


public class AILogic {
    
    int count_1 , count_2 , count_3 , count_4 , count_5 , count_6 , count_7 , count_8 , count_9 , count_10;
    AIUtil aIUtil;
    QuadsManagement quadManagement;
    Random random = new Random();
    Camera  camera;
    int cost;
    Model mapM;
    QuadData quadData;
    public ResValidation resValidation;
    int max_cargo = 200;
    Quad ship = null;
    Color color;
     
    
    public AILogic(AIUtil aIUtil   , QuadsManagement quadManagement   , Camera  camera , QuadData quadData , ResValidation resValidation , Model mapM , Color color)
    {
        this.aIUtil = aIUtil;
        this.quadManagement = quadManagement;
        this.camera = camera;
        this.mapM = mapM;
        count_1 = 45;
        this.quadData = quadData;
        this.resValidation = resValidation;
        this.color = color;
    }
    
    public void startAI()  
    {
        if(!SpaceComander.game_pause)
        if(count_1 < 25)
        {
           count_1++;
        } else {
            count_1 = 0;

                    if(count_2 < 2){
                       count_2++;
                    } else {    if(!SpaceComander.game_pause)
                                {
                                    colonizePlanet(quadData.playerList);
                                    count_2 = 0;
                                    if(count_3 < 1){
                                       count_3++;
                                    } else {

                                             addAIShip();
                                             updateText();
                                             count_3 = 0;
                                        if(count_4 <= 2){
                                            incresePopulation();
                                            count_4++;
                                        } else {
                                           incorporatePlayer();
                                           mineResources();
                                           deplaySquad();
                                           deployCorgoShip(quadData.playerList);                                      
                                           count_4 = 0;
                                        }
                                    }
                                }
 
                           }            
               }
        
    }
    public void asignateAIWorkers(Planet planet) {
        if(planet.player.auto_assign_worker)
        {   if(planet.isWather)
            {
                if (planet.resources.workersAvaible > 0) {
                    if (planet.resources.energyWorker <= planet.resources.metalOreWorker && planet.resources.energyWorker <= planet.resources.mineralsWorker && planet.resources.energyWorker <= planet.resources.carbonWorker && planet.resources.energyWorker <= planet.resources.gasWorker) {
                        planet.resources.energyWorker++;
                        planet.resources.workersAvaible--;
                    } else {
                        if (planet.resources.metalOreWorker <= planet.resources.energyWorker && planet.resources.metalOreWorker <= planet.resources.mineralsWorker && planet.resources.metalOreWorker <= planet.resources.carbonWorker && planet.resources.metalOreWorker <= planet.resources.gasWorker) {
                            planet.resources.metalOreWorker++;
                            planet.resources.resourcesMinedCount++;
                            planet.resources.workersAvaible--;
                        } else {
                            if (planet.resources.mineralsWorker <= planet.resources.energyWorker && planet.resources.mineralsWorker <= planet.resources.metalOreWorker && planet.resources.mineralsWorker <= planet.resources.carbonWorker && planet.resources.mineralsWorker <= planet.resources.gasWorker) {
                                planet.resources.mineralsWorker++;
                                planet.resources.resourcesMinedCount++;
                                planet.resources.workersAvaible--;
                            } else {
                                if (planet.resources.carbonWorker <= planet.resources.energyWorker && planet.resources.carbonWorker <= planet.resources.metalOreWorker && planet.resources.carbonWorker <= planet.resources.mineralsWorker && planet.resources.carbonWorker <= planet.resources.gasWorker) {
                                    planet.resources.carbonWorker++;
                                    planet.resources.resourcesMinedCount++;
                                    planet.resources.workersAvaible--;
                                } else {
                                    if (planet.resources.gasWorker <= planet.resources.energyWorker && planet.resources.gasWorker <= planet.resources.metalOreWorker && planet.resources.gasWorker <= planet.resources.mineralsWorker && planet.resources.gasWorker <= planet.resources.carbonWorker) {
                                        planet.resources.gasWorker++;
                                        planet.resources.resourcesMinedCount++;
                                        planet.resources.workersAvaible--;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                        if (planet.resources.workersAvaible > 0)
                        {
                            if(planet.resources.metalOre >= planet.resources.minerals && planet.resources.metalOre >= planet.resources.carbon && planet.resources.metalOre >= planet.resources.gas) {
                                    planet.resources.metalOreWorker++;
                                    planet.resources.resourcesMinedCount++;
                                    planet.resources.workersAvaible--;
                            } else {
                                if(planet.resources.minerals >= planet.resources.metalOre && planet.resources.minerals >= planet.resources.carbon && planet.resources.minerals >= planet.resources.gas) {
                                    planet.resources.mineralsWorker++;
                                    planet.resources.resourcesMinedCount++;
                                    planet.resources.workersAvaible--;
                                } else {
                                    if(planet.resources.carbon >= planet.resources.minerals && planet.resources.carbon >= planet.resources.metalOre && planet.resources.carbon >= planet.resources.gas) {
                                        planet.resources.carbonWorker++;
                                        planet.resources.resourcesMinedCount++;
                                        planet.resources.workersAvaible--;
                                    } else {
                                        if (planet.resources.gas >= planet.resources.minerals && planet.resources.gas >= planet.resources.metalOre && planet.resources.gas >= planet.resources.carbon) {
                                            planet.resources.gasWorker++;
                                            planet.resources.resourcesMinedCount++;
                                            planet.resources.workersAvaible--;
                                        }
                                    }
                                }

                            }

                        }

                   }
        }
    }
    public void incresePopulation(){
        for(Player player : quadData.playerList) {
            for(Planet planet : player.cPlanetList) {
                if(planet.resources.maxPopulation > 0)
                {
                    if (planet.resources.population < planet.resources.maxPopulation) {
                        if (!planet.isWather) {
                            if (planet.isSemiarid) {
                                planet.resources.population += 2;
                                planet.resources.workersAvaible  = planet.resources.population / 10;
                                planet.resources.workersAvaible = planet.resources.workersAvaible - planet.resources.energyWorker - planet.resources.metalOreWorker - planet.resources.mineralsWorker - planet.resources.carbonWorker - planet.resources.gasWorker;

                            } else {
                                planet.resources.population++;
                                planet.resources.workersAvaible  = planet.resources.population / 10;
                                planet.resources.workersAvaible = planet.resources.workersAvaible - planet.resources.energyWorker - planet.resources.metalOreWorker - planet.resources.mineralsWorker - planet.resources.carbonWorker - planet.resources.gasWorker;

                            }
                        } else {
                            planet.resources.population += 5;
                            planet.resources.workersAvaible  = planet.resources.population / 10;
                            planet.resources.workersAvaible = planet.resources.workersAvaible - planet.resources.energyWorker - planet.resources.metalOreWorker - planet.resources.mineralsWorker - planet.resources.carbonWorker - planet.resources.gasWorker;
                        }
                    } else {
                        planet.resources.workersAvaible  = planet.resources.population / 10;
                        planet.resources.workersAvaible = planet.resources.workersAvaible - planet.resources.energyWorker - planet.resources.metalOreWorker - planet.resources.mineralsWorker - planet.resources.carbonWorker - planet.resources.gasWorker;

                    }
               } else {
                    if (planet.resources.population > 0){
                        planet.resources.population -= 1;
                        planet.resources.workersAvaible  = planet.resources.population / 10;
                    }
                }
                asignateAIWorkers(planet);
            }
        }
    }
    public void updateText(){
        for(TextData data : quadData.textToRender) {
             if(data.updateStatus){
                data.update(camera.getPosition().x , camera.getPosition().y , camera.getPosition().z);
             }
        }
    }    
    public void mineResources()
    {
      int pop;
      for(Player player : quadData.playerList)
      {
         for(Planet planet : player.cPlanetList)
         {
            pop = planet.resources.population / 10;
            if(planet.resources.energy > 0) {
                planet.resources.energyMined += (planet.resources.energyLevel + planet.resources.energy) * planet.resources.energyWorker;
            }
            if(planet.resources.metalOre > 0) {
                planet.resources.metalOreMined += (planet.resources.metalOreLevel + planet.resources.metalOre) * planet.resources.metalOreWorker;
            }
            if(planet.resources.minerals > 0) {
                planet.resources.mineralsMined += (planet.resources.mineralsLevel + planet.resources.minerals) * planet.resources.mineralsWorker;
            }
            if(planet.resources.carbon > 0) {
                planet.resources.carbonMined += (planet.resources.carbonLevel + planet.resources.carbon) * planet.resources.carbonWorker;
            }

            planet.resources.creditMined   += planet.resources.creditLevel+ pop;

            if(planet.resources.gas > 0) {
                planet.resources.gasMined += (planet.resources.gasLevel + planet.resources.gas) * planet.resources.gasWorker;
            }  
            
             if(planet.player.auto_resUpgrade && planet.isWather) {
               
                 if(count_10 < 20) {
                     count_10++;
                 } else {
                           count_10 = 0;
                           resValidation.ungradeResLevel(planet);
                           if(count_8 < 2) {
                              count_8++;
                           } else {
                                    count_8 = 0;
                                    resValidation.autoShipsUpgrades(planet);
                                    
                                   
                                  }                          
                        }
             }
             
         }
      }
    
    }
    public void deplaySquad()
    {
       for(Player player : quadData.playerList) 
       {
           if(player.auto_deploySquad)
           {
              if(player.cPlanetList.size() != 0)
              {
                  int p = random.nextInt(player.cPlanetList.size());
                  Planet planet = player.cPlanetList.get(p);
                  Planet tPlanet = aIUtil.getClosestEnemyPlanet(planet, quadData.sunList , quadData.playerList);
                   if(tPlanet != null)
                   {
                      if(player.cPlanetList.get(p).shipCount >= planet.planetMaxShipCount)
                      {
                          aIUtil.deplaySquad(planet, player, tPlanet);
                      }
                   }
              }
           }
       }
    
    
    }
    public void addAIShip() 
    {
        for(Player player : quadData.playerList)
        {
            if(player.auto_addShip)
            {
                 if(player.watherPList.size() != 0 && player.shipCount < player.maxShipCount)
                 {
                     int r= random.nextInt(5);
                     int p = random.nextInt(player.cPlanetList.size());
                     Planet planetR = player.cPlanetList.get(p); 
                     if(planetR.shipCount < planetR.planetMaxShipCount)
                     { 
                            ship = null;
                            if(r == 0){
                                if(resValidation.addShipIfExistResources(planetR.resources.exploreShipCost,planetR)) {
                                    ship = quadManagement.addExploreShip(planetR, player, planetR.position.x+(random.nextFloat()/10)/quadManagement.scaleQuad, planetR.position.y+(random.nextFloat()/10)/quadManagement.scaleQuad);
                                }
                            }
                            if(r == 1){
                                if(resValidation.addShipIfExistResources(planetR.resources.freagateShipCost,planetR)) {
                                   ship = quadManagement.addFreagateShip(planetR, player, planetR.position.x+(random.nextFloat()/10)/quadManagement.scaleQuad, planetR.position.y+(random.nextFloat()/10)/quadManagement.scaleQuad);
                                }
                            }
                            if(r == 2){
                                if(resValidation.addShipIfExistResources(planetR.resources.destroierShipCost,planetR)) {
                                    ship = quadManagement.addDestroierShip(planetR, player, planetR.position.x+(random.nextFloat()/10)/quadManagement.scaleQuad, planetR.position.y+(random.nextFloat()/10)/quadManagement.scaleQuad);
                                }
                            }
                            if(r == 3) {
                                if(resValidation.addShipIfExistResources(planetR.resources.carrierShipCost,planetR))  {
                                    ship = quadManagement.addCarrierShip(planetR, player, planetR.position.x+(random.nextFloat()/10)/quadManagement.scaleQuad, planetR.position.y+(random.nextFloat()/10)/quadManagement.scaleQuad);
                                }
                            }  
                            if(r == 4){
                                if(resValidation.addShipIfExistResources(planetR.resources.motherShipCost,planetR)) {
                                    ship = quadManagement.addMotherShip(planetR, player, planetR.position.x+(random.nextFloat()/10)/quadManagement.scaleQuad, planetR.position.y+(random.nextFloat()/10)/quadManagement.scaleQuad);
                                }
                            }
                            checkIFShipEnableToRender(ship);  
                     }                    
                 }
            }
        }
    }

    public void  deployCorgoShip(ArrayList<Player>  playerList)
    { 
        for(Player player : playerList)
        {
             if(player.auto_colect_resources)
             {
                 for(Planet planet : player.cPlanetList)
                 {
                    if(player.planetColector == null)
                    {
                        if (!planet.isWather) {

                            Planet planetT = aIUtil.getClosestWatherColoniPlanet(planet, player);
                            if (planetT != null) {

                                if (planet.resources.metalOreMined >= max_cargo || planet.resources.mineralsMined >= max_cargo || planet.resources.carbonMined >= max_cargo || planet.resources.gasMined >= max_cargo) {
                                     ship = quadManagement.addCargoShip(planet, player, planet.position.x, planet.position.y);
                                     ship.isSelectebel  = false;
                                     ship.metalOreCargo = planet.resources.metalOreMined;
                                     ship.mineralCargo  = planet.resources.mineralsMined;
                                     ship.carbonCargo   = planet.resources.carbonMined;
                                     ship.gasCargo      = planet.resources.gasMined;

                                     planet.resources.metalOreMined = 0;
                                     planet.resources.mineralsMined = 0;
                                     planet.resources.carbonMined   = 0;
                                     planet.resources.gasMined      = 0;

                                     ship.translateQuad(planetT.position.x, planetT.position.y);
                                     
                                     checkIFShipEnableToRender(ship);                                      
                                }
                            }
                        }
                    } else {
                            if(planet != player.planetColector)
                            {
                                if (planet.resources.metalOreMined >= max_cargo || planet.resources.mineralsMined >= max_cargo || planet.resources.carbonMined >= max_cargo || planet.resources.gasMined >= max_cargo) {
                                     ship = quadManagement.addCargoShip(planet, player, planet.position.x, planet.position.y);

                                     ship.isSelectebel = false;
                                     ship.metalOreCargo = planet.resources.metalOreMined;
                                     ship.mineralCargo = planet.resources.mineralsMined;
                                     ship.carbonCargo = planet.resources.carbonMined;
                                     ship.gasCargo = planet.resources.gasMined;

                                     planet.resources.metalOreMined = 0;
                                     planet.resources.mineralsMined = 0;
                                     planet.resources.carbonMined = 0;
                                     planet.resources.gasMined = 0;

                                     ship.translateQuad(player.planetColector.position.x, player.planetColector.position.y);
                                     checkIFShipEnableToRender(ship);                              
                                }
                            }
                     }
                 }
             }
        }
    }
    public void  colonizePlanet(ArrayList<Player>  playerList)     {
        for(Player player : playerList)
        {
            if(player.auto_colonizePlanet)
            {
                if(player.cPlanetList.size() < player.maximumColonizePlanet)
                {
                    if(player.coloniShip == null && player.colonizationProcess == false && player.watherPList.size() != 0) {

                         int p = random.nextInt(player.watherPList.size());
                         Planet planetP = player.watherPList.get(p);
                         if(checkResources(planetP , planetP.resources.colonyShipCost))
                         {
                            ship =  quadManagement.addColonizationShip(planetP, player, planetP.position.x, planetP.position.y);
                            Planet planetT = null;
                            if (player.cPlanetList.size() < player.minDefenceColonizeCount) {
                                planetT = aIUtil.getClosestColonizebelPlanet(player.coloniShip, quadData.sunList,false,playerList);
                                if(planetT == null)
                                {
                                   Sun sun = aIUtil.getClosestUnexploredStar(player.coloniShip , quadData.sunList);
                                   if(sun != null) {
                                       if(player.enableColoniShipPriority) {
                                           player.coloniShip.translateQuad(player.x_priority, player.y_priority);
                                       } else {
                                           player.coloniShip.translateQuad(sun.position.x, sun.position.y);
                                       }
                                   }
                                }
                            } else {
                                planetT = aIUtil.getClosestEnemyPlanet(planetP, quadData.sunList, playerList);
                                if(planetT == null)
                                {
                                    Sun sun = aIUtil.getClosestUnexploredStar(player.coloniShip , quadData.sunList);
                                    if(sun != null) {
                                        if(player.enableColoniShipPriority) {
                                            player.coloniShip.translateQuad(player.x_priority, player.y_priority);
                                        } else {
                                           player.coloniShip.translateQuad(sun.position.x, sun.position.y);
                                        }
                                    }
                                }
                            }
                            if (planetT != null) {
                                  if(player.enableColoniShipPriority) {
                                      player.coloniShip.translateQuad(player.x_priority, player.y_priority);
                                  } else {
                                      player.coloniShip.translateQuad(planetT.position.x, planetT.position.y);
                                  }
                                player.colonizationProcess = true;
                            }
                            checkIFShipEnableToRender(ship);                           
                        }        
                    }
                    if(player.coloniShip != null && player.colonizationProcess == false ) 
                    {
                        Planet p = null;
                        if(player.cPlanetList.size() == 0){
                            p = aIUtil.getClosestColonizebelPlanet(player.coloniShip, quadData.sunList,true,playerList);
                            if(p == null) {
                                Sun sun = aIUtil.getClosestUnexploredStar(player.coloniShip , quadData.sunList);
                                if(sun != null) {
                                    player.coloniShip.translateQuad(sun.position.x, sun.position.y);
                                }
                            }
                        } else {
                            p = aIUtil.getClosestColonizebelPlanet(player.coloniShip, quadData.sunList,false,playerList);
                            if(p == null) {
                                Sun sun = aIUtil.getClosestUnexploredStar(player.coloniShip , quadData.sunList);
                                if(sun != null) {
                                    player.coloniShip.translateQuad(sun.position.x, sun.position.y);
                                }
                            }
                        }
                        if(p != null)
                        {
                            player.coloniShip.translateQuad(p.position.x,p.position.y);
                        }
                            player.colonizationProcess = true;   
                    }
                }
            }
        }
    }
    public void checkIFShipEnableToRender(Quad ship) {
        if (ship != null) {
            if (checkConstrainsQ(ship)) {
                ship.enableToRender = true;
            } else {
                ship.enableToRender = false;
            }
        }
    }
    public boolean  checkResources(Planet planetP , int res) {
        if(planetP.resources.energyMined >=res && planetP.resources.metalOreMined >=res && planetP.resources.mineralsMined >=res && planetP.resources.carbonMined >=res && planetP.resources.creditMined >=res && planetP.resources.gasMined >=res) {
            planetP.resources.energyMined   -= res;
            planetP.resources.metalOreMined -= res;
            planetP.resources.mineralsMined -= res;
            planetP.resources.carbonMined   -= res;
            planetP.resources.creditMined   -= res;
            planetP.resources.gasMined      -= res;
            return  true;
        } else {
            return false;
        }
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
    public void incorporatePlayer() {
        for (Player player_0 : quadData.playerList) {
            for (Player player_1 : quadData.playerList) {
                 if(player_0.enemy[player_1.index] && player_0.playerSharedBorderLenght[player_1.index] > 3){
                     generateRequest(player_0, player_1);
                 }
            }
        }
    }
    public void generateRequest(Player player_master, Player player) {
        int ansver = -1;
        if (player.masterPlayer == null) {
            if (player.cPlanetList.size() < player_master.cPlanetList.size() * 0.5f) {
                ansver = random.nextInt(1);
                if (ansver == 0) {
                        if(player_master.masterPlayer == null) 
                        {
                            player_master.federation_index = player_master.index;
                            player.boarder_tex_index = color.gameBordetIndex.get(player_master.index);
                            player.segmented_boarder_tex_index = color.gamesegmentedBordetIndex.get(player_master.index);
                            player.hex_Border_index = color.gameBordetHexIndex.get(player_master.index);
                            player.federation_index = player_master.federation_index;
                        } else {
                            player.boarder_tex_index = color.gameBordetIndex.get(player_master.federation_index);
                            player.segmented_boarder_tex_index = color.gamesegmentedBordetIndex.get(player_master.federation_index);
                            player.hex_Border_index = color.gameBordetHexIndex.get(player_master.federation_index);
                            player.federation_index = player_master.federation_index;                                
                        }
                        player.masterPlayer = player_master;
                        makePeace(player_master, player);
                                  
                }
            }
        }
    }
    public void makePeace(Player masterPlayer , Player player) {
       player.enemy[masterPlayer.index] = false;
       masterPlayer.enemy[player.index] = false;        
       for(int i=0 ; i< 9 ; i++) {
         if(masterPlayer.enemy[i] == false){
            player.enemy[i] = false;
         }
       }
    }
}
