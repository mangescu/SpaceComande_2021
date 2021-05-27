
package Planet;


public class Resources {

    int initialResources = 0;
    
    public  int energyMined , metalOreMined , mineralsMined , carbonMined , creditMined ,  gasMined;
    public  int energyLevel , metalOreLevel , mineralsLevel , carbonLevel , creditLevel ,  gasLevel;
    public  int energy      , metalOre      , minerals      , carbon      , credit      ,  gas , biomas;
    public  int energyUpgradeCost , metalOreUpgradeCost , mineralsUpgradeCost , carbonUpgradeCost , creditUpgradeCost , gasUpgradeCost ;
    public  int engineUpgradeLevel ,  gunsUpgradeLevel , hullUpgradeLevel , shieldUpgradeLevel , engineUpgradeCost , gunsUpgradeCost , hullUpgradeCost , shieldUpgradeCost;
    public  int population , maxPopulation;
    public  int energyWorker , metalOreWorker ,  mineralsWorker  , carbonWorker  , gasWorker , workersAvaible;
    public int resourcesMinedCount = 0;
    public int cargoDoc_energy_Stock , cargoDoc_metalOre_Stock , cargoDoc_mineral_Stock , cargoDoc_carbon_Stock ,cargoDoc_credit_Stock , cargoDoc_gas_Stock , stockTransferRate;
    
    public int colonyShipCost = 15;
    public int exploreShipCost = 30;
    public int freagateShipCost = 60;
    public int destroierShipCost = 120;
    public int carrierShipCost = 250;
    public int motherShipCost = 300;
    
    public Resources() {

        resourcesMinedCount = 0;

        energyWorker = 0;
        metalOreWorker = 0;
        mineralsWorker = 0;
        carbonWorker = 0;
        gasWorker = 0;

        population = 0;
        maxPopulation = 0;
        workersAvaible = 0;

        initialResources = 10000;

        energyMined = initialResources;
        metalOreMined = initialResources;
        mineralsMined = initialResources;
        carbonMined = initialResources;
        creditMined = initialResources;
        gasMined = initialResources;
       
        energyLevel = 1;
        metalOreLevel = 1;
        mineralsLevel = 1;
        carbonLevel = 1;
        creditLevel = 1;
        gasLevel = 1;
        
        energy = 0;
        metalOre =0;
        minerals = 0;
        carbon = 0;
        credit = 0;
        gas = 0;
        biomas = 0;

        energyUpgradeCost = 10;
        metalOreUpgradeCost = 10;
        mineralsUpgradeCost = 10;
        carbonUpgradeCost = 10;
        creditUpgradeCost = 10;
        gasUpgradeCost = 10;
        
        engineUpgradeLevel = 0;
        gunsUpgradeLevel = 0;
        hullUpgradeLevel = 0;
        shieldUpgradeLevel = 0;

        engineUpgradeCost = 1;
        gunsUpgradeCost = 1;
        hullUpgradeCost = 1;
        shieldUpgradeCost = 1;

        stockTransferRate = 100;


    }

 
}
