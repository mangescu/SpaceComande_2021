
package AILogic;

import Planet.Planet;
import spacecomander.Quad;


public class ResValidation {
    boolean done;
    public ResValidation(){} 
    public boolean addShipIfExistResources(int cost, Planet planet) {
        boolean done = false;
        if (planet.resources.energyMined >= cost && planet.resources.metalOreMined >= cost && planet.resources.mineralsMined >= cost && planet.resources.carbonMined >= cost && planet.resources.creditMined >= cost && planet.resources.gasMined >= cost) {
            setNewResourcesStorege(cost, planet);
            done = true;
        }
        return done;
    }
    public void setNewResourcesStorege(int cost , Planet p)  {
                p.resources.energyMined   -= cost;
                p.resources.metalOreMined -= cost;
                p.resources.mineralsMined -= cost;
                p.resources.carbonMined   -= cost;
                p.resources.creditMined   -= cost;
                p.resources.gasMined      -= cost;       
    }    
    public boolean checkShipProductionResources(Planet planet , int cost) {
       boolean ok = false;
          if(planet.resources.energyMined >= cost && planet.resources.metalOreMined >= cost && planet.resources.mineralsMined >= cost && planet.resources.carbonMined >= cost && planet.resources.creditMined >= cost && planet.resources.gasMined >= cost) {
             planet.resources.energyMined    -= cost;
             planet.resources.metalOreMined  -= cost;
             planet.resources.mineralsMined  -= cost;
             planet.resources.carbonMined    -= cost;
             planet.resources.creditMined    -= cost;
             planet.resources.gasMined       -= cost;
             ok = true;
          }       
       return ok;
    } 
    public void incrementUpgratesCost(Planet planet, int i) {
        if (i == 0) {
            planet.resources.energyUpgradeCost = planet.resources.energyUpgradeCost + 10;
        }
        if (i == 1) {
            planet.resources.metalOreUpgradeCost = planet.resources.metalOreUpgradeCost + 10;
        }
        if (i == 2) {
            planet.resources.mineralsUpgradeCost = planet.resources.mineralsUpgradeCost + 10;
        }
        if (i == 3) {
            planet.resources.carbonUpgradeCost = planet.resources.carbonUpgradeCost + 10;
        }
        if (i == 4) {
            planet.resources.creditUpgradeCost = planet.resources.creditUpgradeCost + 10;
        }
        if (i == 5) {
            planet.resources.gasUpgradeCost = planet.resources.gasUpgradeCost + 10;
        }
    }
    public void autoShipsUpgrades(Planet planet){
       
        if(planet.resources.engineUpgradeLevel <= planet.resources.gunsUpgradeLevel && planet.resources.engineUpgradeLevel <= planet.resources.hullUpgradeLevel && planet.resources.engineUpgradeLevel <= planet.resources.shieldUpgradeLevel) {
            shipsUpgrades(planet, 0);
            done = true;
        }
        if(planet.resources.gunsUpgradeLevel <= planet.resources.engineUpgradeLevel && planet.resources.gunsUpgradeLevel <= planet.resources.hullUpgradeLevel && planet.resources.gunsUpgradeLevel <= planet.resources.shieldUpgradeLevel) {
            shipsUpgrades(planet, 1);
             done = true;
        }   
        if(planet.resources.hullUpgradeLevel <= planet.resources.engineUpgradeLevel && planet.resources.hullUpgradeLevel <= planet.resources.gunsUpgradeLevel && planet.resources.hullUpgradeLevel <= planet.resources.shieldUpgradeLevel) {
            shipsUpgrades(planet, 2);
             done = true;
        } 
        if(planet.resources.shieldUpgradeLevel <= planet.resources.engineUpgradeLevel && planet.resources.shieldUpgradeLevel <= planet.resources.gunsUpgradeLevel && planet.resources.shieldUpgradeLevel <= planet.resources.hullUpgradeLevel) {
            shipsUpgrades(planet, 3);
             done = true;
        } 
     
    }
    public boolean shipsUpgrades(Planet planet , int type){
        int cost = 0;
        if(type == 0){
            cost = planet.resources.engineUpgradeCost;
        }
        if(type == 1){
            cost = planet.resources.gunsUpgradeCost;
        }  
        if(type == 2){
            cost = planet.resources.hullUpgradeCost;
        }  
        if(type == 3){
            cost = planet.resources.shieldUpgradeCost;
        }          
        if (planet.resources.energyMined >= (1000*cost) && planet.resources.metalOreMined >= (1000*cost) && planet.resources.mineralsMined >= (1000*cost) && planet.resources.carbonMined >= (1000*cost) && planet.resources.creditMined >= (1000*cost) && planet.resources.gasMined >= (1000*cost)) {
            if(type == 0){
                planet.resources.engineUpgradeLevel++;
                planet.resources.engineUpgradeCost++; 
                resetUpgradeResource(planet,(1000*cost));  
            }
            if(type == 1){
                planet.resources.gunsUpgradeLevel++;
                planet.resources.gunsUpgradeCost++; 
                resetUpgradeResource(planet,(1000*cost)); 
            } 
            if(type == 2){
                planet.resources.hullUpgradeLevel++;
                planet.resources.hullUpgradeCost++;    
                resetUpgradeResource(planet,(1000*cost)); 
            }  
            if(type == 3){
                planet.resources.shieldUpgradeLevel++;
                planet.resources.shieldUpgradeCost++;   
                resetUpgradeResource(planet,(1000*cost)); 
            }             
            return true;
        }
        return false;
    }
    public void resetUpgradeResource(Planet planet , int cost) {
            planet.resources.energyMined   -= cost;
            planet.resources.metalOreMined -= cost;
            planet.resources.mineralsMined -= cost;
            planet.resources.carbonMined   -= cost;
            planet.resources.creditMined   -= cost;
            planet.resources.gasMined      -= cost;
    }    
    public boolean ungradeResLevel(Planet planet){
        done = false; 
        if(planet.resources.energyLevel <= planet.resources.metalOreLevel && planet.resources.energyLevel <= planet.resources.mineralsLevel && planet.resources.energyLevel <= planet.resources.carbonLevel && planet.resources.energyLevel <= planet.resources.gasLevel && planet.resources.energyLevel <= planet.resources.creditLevel) {
                if(resetPlanetResources(planet)) {
                    if(planet.resources.energyLevel < 3) {
                        planet.resources.energyLevel++;
                        planet.resources.energyUpgradeCost   += 10 ;
                        resetPlanetResources(planet);    
                    }
                }
        }
        if(planet.resources.metalOreLevel <= planet.resources.energyLevel && planet.resources.metalOreLevel <= planet.resources.mineralsLevel && planet.resources.metalOreLevel <= planet.resources.carbonLevel && planet.resources.metalOreLevel <= planet.resources.gasLevel && planet.resources.metalOreLevel <= planet.resources.creditLevel) {
                if (resetPlanetResources(planet)) {
                   if(planet.resources.metalOreLevel < 3) {
                      planet.resources.metalOreLevel++;
                      planet.resources.metalOreUpgradeCost  +=  10;
                      resetPlanetResources(planet);
                   }
                }
        }
        if(planet.resources.mineralsLevel <= planet.resources.energyLevel && planet.resources.mineralsLevel <= planet.resources.metalOreLevel && planet.resources.mineralsLevel <= planet.resources.carbonLevel && planet.resources.mineralsLevel <= planet.resources.gasLevel && planet.resources.mineralsLevel <= planet.resources.creditLevel)   {
                if (resetPlanetResources(planet)) {
                     if(planet.resources.mineralsLevel < 3) {
                        planet.resources.mineralsLevel++;
                        planet.resources.mineralsUpgradeCost    +=  10;
                        resetPlanetResources(planet);
                     }
                }
        }
        if(planet.resources.carbonLevel <= planet.resources.energyLevel && planet.resources.carbonLevel <= planet.resources.metalOreLevel && planet.resources.carbonLevel <= planet.resources.mineralsLevel && planet.resources.carbonLevel <= planet.resources.gasLevel && planet.resources.carbonLevel <= planet.resources.creditLevel) {          
                if (resetPlanetResources(planet)) { 
                    if(planet.resources.carbonLevel < 3) {
                        planet.resources.carbonLevel++;
                        planet.resources.carbonUpgradeCost   += 10;
                        resetPlanetResources(planet);
                    }
                }            
        }
        if(planet.resources.gasLevel <= planet.resources.energyLevel && planet.resources.gasLevel <= planet.resources.metalOreLevel && planet.resources.gasLevel <= planet.resources.mineralsLevel && planet.resources.gasLevel <= planet.resources.carbonLevel && planet.resources.gasLevel <= planet.resources.creditLevel)   {
                if (resetPlanetResources(planet)) {
                     if(planet.resources.gasLevel < 3) {
                        planet.resources.gasLevel++;
                        planet.resources.gasUpgradeCost    += 10;
                        resetPlanetResources(planet);
                     }
                }    
        }     
        if(planet.resources.creditLevel <= planet.resources.energyLevel && planet.resources.creditLevel <= planet.resources.metalOreLevel && planet.resources.creditLevel <= planet.resources.mineralsLevel && planet.resources.creditLevel <= planet.resources.carbonLevel && planet.resources.creditLevel <= planet.resources.gasLevel)  {
                if (resetPlanetResources(planet)) {
                     if(planet.resources.creditLevel < 3) {
                        planet.resources.creditLevel++;
                        planet.resources.creditUpgradeCost   += 10;
                        resetPlanetResources(planet);
                     }
                }   
        }
        return done;
    }  
    public boolean  resetPlanetResources(Planet planet){
         boolean  check = false;
         if(planet.resources.energyMined >= planet.resources.energyUpgradeCost && planet.resources.metalOreMined >= planet.resources.metalOreUpgradeCost && planet.resources.mineralsMined >= planet.resources.mineralsUpgradeCost && planet.resources.carbonMined >= planet.resources.carbonUpgradeCost&& planet.resources.gasMined >= planet.resources.gasUpgradeCost && planet.resources.creditMined >= planet.resources.creditUpgradeCost) {
                planet.resources.energyMined -= planet.resources.energyUpgradeCost;
                planet.resources.metalOreMined -= planet.resources.metalOreUpgradeCost;
                planet.resources.mineralsMined -= planet.resources.mineralsUpgradeCost;
                planet.resources.carbonMined -= planet.resources.carbonUpgradeCost;
                planet.resources.creditMined -= planet.resources.creditUpgradeCost; 
                planet.resources.gasMined -= planet.resources.gasUpgradeCost;
                check = true;
                done = true;
         }
         return check;
    }    
    public boolean  upgraderMiningF(Planet planet)   { 
        if(planet.resources.energyMined >= planet.resources.energyUpgradeCost && planet.resources.metalOreMined >= planet.resources.metalOreUpgradeCost && planet.resources.mineralsMined >= planet.resources.mineralsUpgradeCost){
             if(planet.resources.carbonMined >= planet.resources.carbonUpgradeCost && planet.resources.creditMined >= planet.resources.creditUpgradeCost && planet.resources.gasMined >= planet.resources.gasUpgradeCost){
                    planet.resources.energyMined   -= planet.resources.energyUpgradeCost;
                    planet.resources.metalOreMined -= planet.resources.metalOreUpgradeCost;
                    planet.resources.mineralsMined -= planet.resources.mineralsUpgradeCost;
                    planet.resources.carbonMined   -= planet.resources.carbonUpgradeCost;
                    planet.resources.creditMined   -= planet.resources.creditUpgradeCost;
                    planet.resources.gasMined      -= planet.resources.gasUpgradeCost;                 
                 return true;              
             } else {
                       return false;
                    }
        } else {
                 return false;
               }
    } 
    public void cargoShip_Planet_Transfer(Planet planet , Quad ship , int divider) {
        planet.resources.energyMined += ship.energyCargo / divider;
        planet.resources.metalOreMined += ship.metalOreCargo / divider;
        planet.resources.mineralsMined += ship.mineralCargo / divider;
        planet.resources.carbonMined += ship.carbonCargo / divider;
        planet.resources.creditMined += ship.creditCargo / divider;
        planet.resources.gasMined += ship.gasCargo / divider;
    }
    public void planet_CargoShip_Transfer(Planet planet , Quad ship ) {
        planet.resources.energyMined += ship.energyCargo;
        planet.resources.metalOreMined += ship.metalOreCargo;
        planet.resources.mineralsMined += ship.mineralCargo;
        planet.resources.carbonMined += ship.carbonCargo;
        planet.resources.creditMined += ship.creditCargo;
        planet.resources.gasMined += ship.gasCargo;
    }
}
