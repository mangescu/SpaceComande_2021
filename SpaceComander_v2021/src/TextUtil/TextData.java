
package TextUtil;

import Camera.Camera;
import Display.SetupDisplay_LWJGL_3;
import IBO.IBO;
import Planet.Planet;
import Texture.Textura;
import java.util.ArrayList;
import spacecomander.Quad;

public class TextData {
    
 IBO ibo;
 Textura text;
 String   stringData;
 public ArrayList<Quad>   textChars  = new ArrayList<Quad>();   
 Characters characters ; 
 float x,y,z ;
 public boolean enabledChart;
 SetupDisplay_LWJGL_3   display;
 
    public boolean energy_1 = false;
    public boolean metalOre_1 = false;
    public boolean minerals_1 = false;
    public boolean carbon_1 = false;
    public boolean credit_1 = false;
    public boolean gas_1 = false;
    public boolean pop_1 = false;
    public boolean energy_2 = false;
    public boolean metalOre_2 = false;
    public boolean minerals_2 = false;
    public boolean carbon_2 = false;
    public boolean credit_2 = false;
    public boolean gas_2 = false;
    public boolean pop_2 = false;
    public boolean energyW = false;
    public boolean metalOreW = false;
    public boolean mineralsW = false;
    public boolean carbonW = false;
    public boolean gasW = false;
    public boolean workersW = false;
    public boolean updateStatus = false;
    public Planet planet;
    
    public  TextData(Camera camera , String string ,float x , float y , float z , IBO ibo , Textura text , boolean updateStatus , int resTipe, SetupDisplay_LWJGL_3   display,boolean enabledChart)   {     
        this.ibo = ibo;
        this.text = text;
        this.x=camera.getPosition().x + x;
        this.y=camera.getPosition().y + y;
        this.z=camera.getPosition().z + z;
        this.stringData = string;
        this.updateStatus = updateStatus;
        this.display = display;
        this.enabledChart = enabledChart;
        
        if(resTipe == 1) {
          energy_1 = true;
        }
        if(resTipe == 2) {
          metalOre_1 = true;
        }  
        if(resTipe == 3) {
          minerals_1 = true;
        }  
        if(resTipe == 4) {
          carbon_1 = true;
        }  
        if(resTipe == 5) {
          credit_1 = true;
        }   
        if(resTipe == 6) {
          gas_1 = true;
        }
        if(resTipe == 7) {
            pop_1 = true;
        }
        if(resTipe == 8) {
            energyW = true;
        }
        if(resTipe == 9) {
            metalOreW = true;
        }
        if(resTipe == 10) {
            mineralsW = true;
        }
        if(resTipe == 11) {
            carbonW = true;
        }
        if(resTipe == 12) {
            gasW = true;
        }
        if(resTipe == 13) {
            workersW = true;
        }
        if(resTipe == 14) {
            energy_2 = true;
        }
        if(resTipe == 15) {
            metalOre_2 = true;
        }
        if(resTipe == 16) {
            minerals_2 = true;
        }
        if(resTipe == 17) {
            carbon_2 = true;
        }
        if(resTipe == 18) {
            credit_2 = true;
        }
        if(resTipe == 19) {
            gas_2 = true;
        }
        characters = new Characters(ibo,text,display);        
        generateTextQuads(stringData);
    }
    public void update(float x , float y , float z)
    {
        textChars.clear();
        if(energy_2) {
            stringData = String.valueOf(planet.resources.energyMined);
            this.x = x - 0.0425f;
            this.y = y - 0.057f;
            this.z = z - 0.15f;
        }
        if(metalOre_2) {
            stringData = String.valueOf(planet.resources.metalOreMined);
            this.x = x - 0.0245f;
            this.y = y - 0.057f;
            this.z = z - 0.15f;
        }
        if(minerals_2) {
            stringData = String.valueOf(planet.resources.mineralsMined);
            this.x = x  - 0.0065f;
            this.y = y - 0.057f;
            this.z = z - 0.15f;
        }
        if(carbon_2) {
            stringData = String.valueOf(planet.resources.carbonMined);
            this.x = x + 0.0115f;
            this.y = y - 0.057f;
            this.z = z - 0.15f;
        }
        if(credit_2) {
            stringData = String.valueOf(planet.resources.creditMined);
            this.x = x + 0.0295f;
            this.y = y - 0.057f;
            this.z = z - 0.15f;
        }
        if(gas_2) {
            stringData = String.valueOf(planet.resources.gasMined);
            this.x = x + 0.0471f;
            this.y = y - 0.057f;
            this.z = z - 0.15f;
        }
        if(energy_1) {
           stringData = String.valueOf(planet.resources.energyMined);
           this.x = x - 0.05f;
           this.y = y - 0.073f;
           this.z = z - 0.15f;
       }
       if(metalOre_1) {
           stringData = String.valueOf(planet.resources.metalOreMined);
           this.x = x - 0.027f;
           this.y = y - 0.073f;
           this.z = z - 0.15f;
       } 
       if(minerals_1) {
           stringData = String.valueOf(planet.resources.mineralsMined);  
           this.x = x -0.002f;
           this.y = y - 0.073f;
           this.z = z - 0.15f;
       }       
       if(carbon_1) {
           stringData = String.valueOf(planet.resources.carbonMined);  
           this.x = x +0.025f;
           this.y = y - 0.073f;
           this.z = z - 0.15f;
       }
       if(credit_1) {
           stringData = String.valueOf(planet.resources.creditMined);
           this.x = x + 0.07f;
           this.y = y - 0.073f;
           this.z = z - 0.15f;
       }
       if(gas_1) {
           stringData = String.valueOf(planet.resources.gasMined);
           this.x = x +0.048f;
           this.y = y - 0.073f;
           this.z = z - 0.15f;
       }
       if(pop_1) {
           stringData = String.valueOf(planet.resources.population);
           this.x = x + 0.071f;
           this.y = y - 0.058f;
           this.z = z - 0.15f;
       }
       if(energyW) {
           stringData = String.valueOf(planet.resources.energyWorker);
           this.x = x  -0.051f;
           this.y = y -  0.078f;
           this.z = z - 0.15f;
       }
       if(metalOreW) {
           stringData = String.valueOf(planet.resources.metalOreWorker);
           this.x = x  -0.027f;
           this.y = y -  0.078f;
           this.z = z - 0.15f;
       }
       if(mineralsW) {
           stringData = String.valueOf(planet.resources.mineralsWorker);
           this.x = x  -0.0027f;
           this.y = y -  0.078f;
           this.z = z - 0.15f;
       }
       if(carbonW) {
           stringData = String.valueOf(planet.resources.carbonWorker);
           this.x = x + 0.024f;
           this.y = y -  0.078f;
           this.z = z - 0.15f;
       }
       if(gasW) {
           stringData = String.valueOf(planet.resources.gasWorker);
           this.x = x +0.048f;
           this.y = y -  0.078f;
           this.z = z - 0.15f;
       }
       if(workersW) {
           stringData = String.valueOf(planet.resources.workersAvaible);
           this.x = x + 0.071f;
           this.y = y - 0.066f;
           this.z = z - 0.15f;
       }
       characters.spacer = 0;
       generateTextQuads(stringData);
    }
    public void generateTextQuads(String text) {
         for(int i=0 ; i < text.length() ; i++) {
             characters.generateCharacter(x,y,z,text.charAt(i) , textChars);
         }
    }
}
