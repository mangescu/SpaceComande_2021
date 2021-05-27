
package AILogic;

import Planet.Planet;
import Player.Player;
import Sun.Sun;
import java.util.ArrayList;
import java.util.Random;
import spacecomander.Quad;


public class AIUtil {
    
    Planet planet ;
    Sun sun;
    float distance;
    float   minDistance;
    Random rand = new Random();
    
    public Planet getClosestColonizebelPlanet(Quad ship ,ArrayList<Sun> sunList , boolean isWatherPlanet , ArrayList<Player>    playerList)
    {
        planet = null;
        minDistance = 1000;

         for(Sun sun : sunList)
         {
             if (sun.exploredList.get(ship.quadPlayerIndex))
             {
                 for (Planet planet : sun.listaPlanete) {

                     distance = (float) Math.sqrt(Math.pow(planet.position.x - ship.curentPosition.x, 2) + Math.pow(planet.position.y - ship.curentPosition.y, 2));

                     if (distance < minDistance && planet.owner != ship.player.index) 
                     {
                         if (planet.player == null) {
                             if (isWatherPlanet) {
                                 if (planet.isWather) {
                                         this.planet = planet;
                                         minDistance = distance;
                                 }
                             } else {
                                     this.planet = planet;
                                     minDistance = distance;
                             }
                         } else {
                          
                               if(playerList.get(ship.player.index).enemy[playerList.get(planet.player.index).index]) {
                                 if (isWatherPlanet) {
                                     if (planet.isWather) {
                                             this.planet = planet;
                                             minDistance = distance;
                                     }
                                 } else {
                                         this.planet = planet;
                                         minDistance = distance;
                                 }
                             }

                         }

                     }

                 }
             }

         }

    
        return planet;
    }
    public Sun getClosestUnexploredStar(Quad ship ,ArrayList<Sun> sunList)
    {
        sun = null;
        minDistance = 1000;
        for(Sun star : sunList)
        {
            float distance = (float) Math.sqrt(Math.pow(ship.curentPosition.x - star.position.x, 2) + Math.pow(ship.curentPosition.y - star.position.y, 2));
            if (distance < minDistance)
            {
                if(!star.exploredList.get(ship.player.index)) {
                    sun = star;
                    minDistance = distance;
                }
            }
        }
        return sun;
    }
    public Planet getClosestWatherColoniPlanet(Planet sourcePlanet , Player player)
    {
        planet = null;
        minDistance = 1000;
        for(Planet planet : player.cPlanetList)
        {
                float distance = (float) Math.sqrt(Math.pow(planet.position.x - sourcePlanet.position.x, 2) + Math.pow(planet.position.y - sourcePlanet.position.y, 2));
                if (distance < minDistance) {
                    if (planet.isWather) {
                        this.planet = planet;
                        minDistance = distance;
                    }
                }
        }
     return planet;
    }

    public Planet getClosestEnemyPlanet(Planet sourcePlanet , ArrayList<Sun> sunList , ArrayList<Player>   playerList) 
    {
        planet = null;
        minDistance = 1000;
            for(Sun sun : sunList)
            {
               for(Planet planet : sun.listaPlanete) 
               {
                  float distance = (float) Math.sqrt(Math.pow(planet.position.x - sourcePlanet.position.x, 2) + Math.pow(planet.position.y - sourcePlanet.position.y, 2));
                  if(distance < minDistance && planet.owner != sourcePlanet.owner && planet.owner != -1) 
                  {
                      if(sourcePlanet.player.enemy[playerList.get(planet.player.index).index]) {
                             this.planet = planet;
                             minDistance = distance; 
                      } 
                  }
               }
            }    
       
       return planet;
    }
    public void deplaySquad(Planet planet, Player play, Planet TPlanet) {
        for (Quad ship : play.shipList) {

             if(!ship.isInterceptor)     {
            //______SHIP_WARP_____________________________________________________________________________________________________________//
                if (ship.player.warpDriveTec) {
                    if (calcPointsPointDistance(TPlanet.position.x, TPlanet.position.y, ship.curentPosition.x, ship.curentPosition.y) > 0.5f) {
                        ship.warpJumpTec = true;
                    } else {
                        if (!ship.shipIsInWarp) {
                            ship.warpJumpTec = false;
                        }
                    }
                }
            //____________________________________________________________________________________________________________________________//
                if (planet == ship.deployPlanet && ship.hasAtack && !ship.attack) {
                    if (ship.player.index == 0) {
                        ship.translateQuad(TPlanet.position.x - rand.nextFloat() / 10, TPlanet.position.y + rand.nextFloat() / 10);
                    } else {
                        if (ship.player.index == 1) {
                            ship.translateQuad(TPlanet.position.x + rand.nextFloat() / 10, TPlanet.position.y - rand.nextFloat() / 10);
                        } else {
                            if (ship.player.index == 2) {
                                ship.translateQuad(TPlanet.position.x - rand.nextFloat() / 10, TPlanet.position.y - rand.nextFloat() / 10);
                            } else {
                                if (ship.player.index == 3) {
                                    ship.translateQuad(TPlanet.position.x + rand.nextFloat() / 5, TPlanet.position.y + rand.nextFloat() / 5);
                                } else {
                                    if (ship.player.index == 4) {
                                        ship.translateQuad(TPlanet.position.x + rand.nextFloat() / 6, TPlanet.position.y + rand.nextFloat() / 6);
                                    } else {
                                        if (ship.player.index == 5) {
                                            ship.translateQuad(TPlanet.position.x + rand.nextFloat() / 6, TPlanet.position.y - rand.nextFloat() / 3);
                                        } else {
                                            if (ship.player.index == 6) {
                                                ship.translateQuad(TPlanet.position.x + rand.nextFloat() / 6, TPlanet.position.y + rand.nextFloat() / 3);
                                            } else {
                                                if (ship.player.index == 7) {
                                                    ship.translateQuad(TPlanet.position.x + rand.nextFloat() / 6, TPlanet.position.y + rand.nextFloat() / 3);
                                                } else {
                                                    if (ship.player.index == 8) {
                                                        ship.translateQuad(TPlanet.position.x - rand.nextFloat() / 6, TPlanet.position.y - rand.nextFloat() / 3);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public float calcPointsPointDistance(float x1 , float y1 , float x2 , float y2) {
        return  (float) (Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2)));
    }  
    
    
}
