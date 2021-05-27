
package SolarSystem;

import Border.Board;
import Campaign.GameSetings;
import IBO.IBO;
import Maths.Maths;
import Planet.Planet;
import Planet.Resources;
import Player.Player;
import QuadModel.Model;
import Sun.Sun;
import Texture.Textura;
import Util.RandomFloat;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.joml.Vector2f;
import org.joml.Vector3f;
import spacecomander.Quad;
import spacecomander.QuadData;
import spacecomander.QuadsManagement;
import spacecomander.SpaceComander;

public class SolarSystem {
    
    IBO ibo;
    Textura tex;
    Model model;
    Sun sun;
    Planet planet;
    ArrayList<Planet>  pList;         
    DecimalFormat df2 = new DecimalFormat("#.##"); 
    DecimalFormat df3 = new DecimalFormat("#.###"); 
    RandomFloat rand = new RandomFloat();
    Random random = new Random();
    public ArrayList<Integer>  lavaPlanet   =  new ArrayList<Integer>();
    public ArrayList<Integer>  aridPlanet   =  new ArrayList<Integer>();
    public ArrayList<Integer>  SAridPlanet   =  new ArrayList<Integer>();
    public ArrayList<Integer>  watherPlanet   =  new ArrayList<Integer>();        
    public ArrayList<Integer>  icePlanet   =  new ArrayList<Integer>();           
    public ArrayList<Vector2f>  starsCoordonateList   =  new ArrayList<Vector2f>();
    public ArrayList<Integer>  planetRandAngle   =  new ArrayList<Integer>();
    public int planetAngleSpacer;
    Maths math;     
    float sunZ = 0.0001f;
    public float planetZ = 0.0002f;
    float deltaSun = 0.000001f; 
    int star_index = 0;
    int planet_index = 0;
    int watherMaxShipCount = 10;
    float delta_x = 0.0f;
    public int minRadius , maxRadius , race_index;  
    int mision;
    public Board bord;
    QuadData quadData;
    GameSetings  gameSetings;
    QuadsManagement quadsManagement;
    
    Scanner   scan;
    public boolean load_from_file;
    String sun_string_line[];
    String planet_string_line[];
    int s_index = 1;
    int p_index = 1;
    float distance ;
    Quad ship = null;
     
    public SolarSystem(IBO  ibo , Textura tex , Maths math , Board bord ,int race_index,QuadData quadData,GameSetings  gameSetings,QuadsManagement quadsManagement){
        this.race_index = race_index;
        this.ibo = ibo;
        this.tex = tex;
        this.math = math;
        this.bord = bord;
        this.gameSetings = gameSetings;
        star_index = 0;
        this.quadData =quadData;
        this.quadsManagement = quadsManagement; 
    }
    public void loadFromFile(){
        try {
            scan = new Scanner(new File("solarSystem.txt"));
        } catch (FileNotFoundException ex) {}
    }
    public void  generateMultiplayerMap(int minRadius , int maxRadius) throws IOException  {
       quadData.planetList.clear();
       quadData.sunList.clear();
       loadFromFile();
       star_index = 0;
       planet_index = 0;
       this.minRadius = minRadius;
       this.maxRadius = maxRadius;          
       generateMMap();
       bord.setupSunChildren();
       for(Player player: quadData.playerList){           
           bord.buildBorder(player);        
       }
         if(load_from_file){
            loadShips();
         }
         
         
       for(int i=0 ; i < quadData.sunList.size() ; i++){
            for(int j=0 ; j < quadData.sunList.size() ; j++){
                if(quadData.sunList.get(i).star_index != quadData.sunList.get(j).star_index) {
                   if(Math.sqrt(Math.pow(quadData.sunList.get(i).saved_position.x - quadData.sunList.get(j).saved_position.x, 2) + Math.pow(quadData.sunList.get(i).saved_position.y - quadData.sunList.get(j).saved_position.y, 2)) < 2.6f){
                      quadData.sunList.get(i).neighborhood.add(quadData.sunList.get(j));
                   }
                }
            }           
       }  

    }
    public void loadShips(){
        try {
            scan = new Scanner(new File("otherData.txt"));
        } catch (FileNotFoundException ex) {}
        int index = 0;
        while(scan.hasNext()){
              String[] line  = scan.nextLine().split(" ");
              Player player = quadData.playerList.get(Integer.valueOf(line[0]));
              Planet planet = quadData.planetList.get(Integer.valueOf(line[1]));            
              if(line[4].equalsIgnoreCase("true")){
                ship = quadsManagement.addCarrierShip(planet, player, Float.valueOf(line[2]), Float.valueOf(line[3]));
                planet.shipCount+=6;
                quadData.playerList.get(Integer.valueOf(line[0])).shipCount+=6;
              }
              if(line[5].equalsIgnoreCase("true")){
                ship = quadsManagement.addDestroierShip(planet, player, Float.valueOf(line[2]), Float.valueOf(line[3]));
                 planet.shipCount++;
                 quadData.playerList.get(Integer.valueOf(line[0])).shipCount++;
              }              
              if(line[6].equalsIgnoreCase("true")){
                ship = quadsManagement.addExploreShip(planet, player, Float.valueOf(line[2]), Float.valueOf(line[3]));
                 planet.shipCount++;
                 quadData.playerList.get(Integer.valueOf(line[0])).shipCount++;
              }   
              if(line[7].equalsIgnoreCase("true")){
                ship = quadsManagement.addFreagateShip(planet, player, Float.valueOf(line[2]), Float.valueOf(line[3]));
                 planet.shipCount++;
                 quadData.playerList.get(Integer.valueOf(line[0])).shipCount++;
              }             
              if(line[8].equalsIgnoreCase("true")){
                ship = quadsManagement.addMotherShip(planet, player, Float.valueOf(line[2]), Float.valueOf(line[3]));
                planet.shipCount++;
                quadData.playerList.get(Integer.valueOf(line[0])).shipCount++;
              }               
            ship.curentPosition.x = Float.valueOf(line[2]);
            ship.curentPosition.y = Float.valueOf(line[3]);

            ship.endPosition.x = Float.valueOf(line[9]);
            ship.endPosition.y = Float.valueOf(line[10]);
            
            if(line[12].equalsIgnoreCase("true")) {
               ship.warpJumpTec = true;
               ship.shipIsInWarp = true;
            }
            if(line[11].equalsIgnoreCase("true")) {
               ship.ship_is_moving = true;
               ship.translateQuad(ship.endPosition.x, ship.endPosition.y);
            }
            ship.life = Float.valueOf(line[13]);
            ship.shield = Float.valueOf(line[14]);            
        }   
    }
    public void generateMission_1Map() throws IOException {
        planet_index = 0;
        star_index = 0;
        delta_x = 0;
        minRadius = 0;
        maxRadius = 50;
        generateStar(-2, 1, "yellow");
            planet = generatePlanet(sun, 5, 25);
            sun.listaPlanete.add(planet);
        generateStar(2, 1, "yellow");
            planet = generatePlanet(sun, 5, 25);
            sun.listaPlanete.add(planet);
            
        bord.setupSunChildren();
    }
    public void generateMission_2Map() throws IOException {
        planet_index = 0;
        star_index = 0;
        delta_x = 0;
        minRadius = 0;
        maxRadius = 50;
        generateStar(-1f, 1, "yellow");
            planet = generatePlanet(sun, 5, 25);
            sun.listaPlanete.add(planet);
            planet = generatePlanet(sun, 6, 45);
            sun.listaPlanete.add(planet);
            planet = generatePlanet(sun, 7, 100);
            sun.listaPlanete.add(planet);
        generateStar(1f, 1, "yellow");
            planet = generatePlanet(sun, 1, 25);
            sun.listaPlanete.add(planet);
            planet = generatePlanet(sun, 2, 60);
            sun.listaPlanete.add(planet);
            planet = generatePlanet(sun, 5, 15);
            sun.listaPlanete.add(planet);

        bord.setupSunChildren();
    }

    public void generateMMap() throws IOException{
        int size =  gameSetings.galaxy_size +4;
        maxRadius = gameSetings.galaxy_size +4;
        String[] gameseting = null;
            if(load_from_file){   
                gameseting = scan.nextLine().split(" ");
                maxRadius = Integer.valueOf(gameseting[3]);
                minRadius = Integer.valueOf(gameseting[2]);
            }
        if (!load_from_file) 
        {
            int count = 0;
            for (float i = size; i > -size; i = i - 1.5f) {
                for (float j = -size; j < size; j = j + 1.5f) {
                    float distance = (float) Math.sqrt(Math.pow(i, 2) + Math.pow(j, 2));
                    if (distance < maxRadius && distance > minRadius) {
                       generateStar(j, i, "");
                    }
                }
                if (count == 0) {
                    delta_x = 0.75f;
                }
                if (count == 1) {
                    delta_x = 0;
                }
                count++;
                if (count == 2) {
                    count = 0;
                }
            }
        } else {
                   while (scan.hasNext()) {
                        generateStar(0, 0, "");
                   }
               }
    }
    public void generateStar(float  i , float  j ,String starType) {
                s_index = 1;
                if(load_from_file){                    
                    String string = scan.nextLine();
                    sun_string_line = string.split(" ");
                }
                sun = new Sun(gameSetings.numberOfPlayers);
                sun.position = new Vector3f(i+delta_x , j  , sunZ);
               
                if(load_from_file) {  
                    sun.position.x = Float.valueOf(sun_string_line[s_index]);
                    sun.position.y = Float.valueOf(sun_string_line[s_index+1]);
                    sun.position.z = sunZ;
                 
                  s_index = s_index + 2;
                }    
                sun.savePosition();
                sunZ += deltaSun;
                float scale = Float.valueOf(df3.format(rand.getFloatRange(0.1f, 0.99f))) / 15;     
                //________________________________________________________________________________________________________________________//
                    if(SpaceComander.mission_index != 0)    {
                       if(starType.equalsIgnoreCase("yellow")) {
                          scale = Float.valueOf(df3.format(rand.getFloatRange(0.3f, 0.6f))) / 15;
                       }
                    }
                //_________________________________________________________________________________________________________________________//   
                if(load_from_file ){
                   scale = Float.valueOf(sun_string_line[s_index]);
                   s_index++;
                }
                
                model = new Model(ibo, tex, 0, sun.position, new Vector3f(0, 0, 0), new Vector3f(scale, scale, scale));

                sun.radius = model.scale.x;
                 if(load_from_file){
                   sun.radius = Float.valueOf(sun_string_line[s_index]);
                   s_index++;
                 }
                rand.floats.clear();
                rand.starSpacer = Float.valueOf(df3.format(sun.radius * 2));
                if (sun.radius >= (0.1f / 15) && sun.radius < (0.3f / 15)) {
                    int h = random.nextInt(3);
                    if (h == 0) {
                        sun.starType = "whiteDwarf";
                        model.INDEX_T = 3;
                    } else {
                        sun.starType = "redDwarf";
                        model.INDEX_T = 1;
                    }
                    rand.planetsSpacer = 0.015f;
                }
                if (sun.radius >= (0.3f / 15) && sun.radius < (0.6f / 15)) {
                    sun.starType = "yellow";
                    model.INDEX_T = 0;
                    rand.planetsSpacer = 0.0225f;
                }
                if (sun.radius >= (0.6f / 15) && sun.radius < (0.8f / 15)) {
                    sun.starType = "blueGiant";
                    model.INDEX_T = 2;
                    rand.planetsSpacer = 0.0325f;
                }
                if (sun.radius >= (0.8f / 15)) {
                    sun.starType = "redGiant";
                    model.INDEX_T = 1;
                    rand.planetsSpacer = 0.035f;
                }
                sun.model = model;
                sun.listaPlanete = new ArrayList<Planet>();
                lavaPlanet.clear();
                aridPlanet.clear();
                SAridPlanet.clear();
                watherPlanet.clear();
                icePlanet.clear();
                       
                generateSunPlanets();

                sun.star_index = star_index;
                quadData.sunList.add(sun);
                star_index++; 
               
    }
    public void generateSunPlanets() {
        if(SpaceComander.mission_index == 0) {
                int count = 0;
                while (count == 0) {
                    count = random.nextInt(7);
                }
                if(load_from_file){
                   count = Integer.valueOf(sun_string_line[s_index]);
                   s_index++;
                }
    //_______________________________________________________________________________________// 
                if(load_from_file){
                        sun.playerIndex = Integer.valueOf(sun_string_line[s_index]);
                        s_index++;                       
                        if(sun.playerIndex != -1){
                            sun.border_type = true;
                            sun.player = quadData.playerList.get(sun.playerIndex);
                            quadData.playerList.get(sun.playerIndex).cStarList.add(sun); 
                        }
                }             
    //________________________________________________________________________________________            

                for (int p = 0; p < count; p++) {
                    planet = generatePlanet(sun  ,0,0);
                    if(load_from_file) {
                        planet.resources.energyMined = Integer.valueOf(planet_string_line[p_index]);
                        p_index++;
                        planet.resources.metalOreMined = Integer.valueOf(planet_string_line[p_index]);
                        p_index++;     
                        planet.resources.mineralsMined = Integer.valueOf(planet_string_line[p_index]);
                        p_index++; 
                        planet.resources.carbonMined = Integer.valueOf(planet_string_line[p_index]);
                        p_index++;     
                        planet.resources.gasMined = Integer.valueOf(planet_string_line[p_index]);
                        p_index++; 
                        planet.resources.creditMined = Integer.valueOf(planet_string_line[p_index]);
                        p_index++;   

                        planet.resources.energyLevel = Integer.valueOf(planet_string_line[p_index]);
                        p_index++;
                        planet.resources.metalOreLevel = Integer.valueOf(planet_string_line[p_index]);
                        p_index++;     
                        planet.resources.mineralsLevel = Integer.valueOf(planet_string_line[p_index]);
                        p_index++; 
                        planet.resources.carbonLevel = Integer.valueOf(planet_string_line[p_index]);
                        p_index++;     
                        planet.resources.gasLevel = Integer.valueOf(planet_string_line[p_index]);
                        p_index++; 
                        planet.resources.creditLevel = Integer.valueOf(planet_string_line[p_index]);
                        p_index++;                         
                    }
                    sun.listaPlanete.add(planet);
                    quadData.planetList.add(planet);
                } 
        } 

    }
    public Planet  generatePlanet(Sun sun ,int distance , int angle)
    {
        p_index = 1; 
        if(load_from_file) {
            String string = scan.nextLine();
            planet_string_line = string.split(" "); 
        } 

        planet = new Planet();
        planet.parentStar = sun;        
        planet.position = new Vector3f();       
        planet.spacer = sun.radius;
        planet.startP = rand.startP;
        planet.planetsSpacer = rand.planetsSpacer;                
        model = new Model(ibo, tex, 0, planet.position , new Vector3f(0,0,0) , new Vector3f(1,1,1));
        float scale = 0;       
        if(sun.starType.equalsIgnoreCase("redDwarf"))    {
            scale = rand.getFloatRange(0.1f, 0.3f)/100;
        }        
        if(sun.starType.equalsIgnoreCase("whiteDwarf"))    {
            scale = rand.getFloatRange(0.1f, 0.3f)/100;
        }        
        if(sun.starType.equalsIgnoreCase("yellow"))    {
            scale = rand.getFloatRange(0.2f, 0.4f)/100;
        }        
        if(sun.starType.equalsIgnoreCase("blueGiant"))    {
            scale = rand.getFloatRange(0.2f, 0.6f)/100;
        }
        if(sun.starType.equalsIgnoreCase("redGiant"))    {
            scale = rand.getFloatRange(0.3f, 0.7f)/100;
        }
        if(load_from_file) {
          scale = Float.valueOf(planet_string_line[p_index]);
          p_index++;
        }
        model.INDEX_T  = 7;
        model.scale.x = scale;
        model.scale.y = scale;
        model.scale.z = scale;
        planet.radius = model.scale.x;
        
    boolean ok = true;
    boolean done = true;
    if(SpaceComander.mission_index == 0) {
        while(ok) {
            done = true;
            planet.angleOrbit = random.nextInt(360);
            if(load_from_file){
              planet.angleOrbit = Integer.valueOf(planet_string_line[p_index]);
              p_index++;
            }
            
            for (int i = 0; i < planetRandAngle.size(); i++) {
                if (Math.abs(planet.angleOrbit - planetRandAngle.get(i)) < (planetAngleSpacer*0.01f)){
                     done = false;
                }
            }
            if(done) {
                ok = false;
            }
        }
        
        planetRandAngle.add(planet.angleOrbit);
        planet.angleRotate =  planet.angleOrbit;
        planet.starDistance = rand.generateRandomDistance(true , 0);
        
        if(load_from_file){
              planet.starDistance = Float.valueOf(planet_string_line[p_index]);
              p_index++;         
        }
        
    } else {
          planet.angleOrbit = angle;
          planet.starDistance = rand.generateRandomDistance(false , distance);;
    }
        float x = sun.position.x + (float)  (planet.starDistance  *  Math.cos(Math.toRadians(planet.angleOrbit)));
        float y = sun.position.y + (float)  (planet.starDistance  *  Math.sin(Math.toRadians(planet.angleOrbit)));
        planet.position.x = x;
        planet.position.y = y;
        planet.position.z = planetZ;
        
        if(load_from_file){
          planet.position.x = Float.valueOf(planet_string_line[p_index]);
          planet.position.y = Float.valueOf(planet_string_line[p_index+1]);
          planet.position.z = Float.valueOf(planet_string_line[p_index+2]);
          p_index += 3;
        }
        float distanta = planet.starDistance;
        float spacer        = rand.starSpacer;
        float startP        = rand.startP;
        float planetsSpacer = rand.planetsSpacer;
        planet.starSpacer = spacer;
        planet.startPP = startP;
        planet.planetsSpacerr = planetsSpacer;
        if(load_from_file) {
          distanta = Float.valueOf(planet_string_line[p_index]);
          spacer = Float.valueOf(planet_string_line[p_index+1]);
          startP = Float.valueOf(planet_string_line[p_index+2]);
          planetsSpacer = Float.valueOf(planet_string_line[p_index+3]);
          p_index += 4;
        }
      
        if(sun.starType.equalsIgnoreCase("redDwarf"))  {
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 0) +spacer)))   {    
                    genAridPlanetTex(model,planet);
                }
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 1) +spacer)))   {    
                    genSAridPlanetTex(model,planet);
                }   
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 2) +spacer)))   {    
                    genWatherPlanet(model,planet);
                }  
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 3) +spacer)))   {    
                    genIcePlanet(model,planet);
                } 
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 4) +spacer)))   {    
                    genIcePlanet(model,planet);
                }  
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 5) +spacer)))   {    
                    genIcePlanet(model,planet);
                } 
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 6) +spacer)))   {    
                    genIcePlanet(model,planet);
                } 
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 7) +spacer)))  {    
                    genIcePlanet(model,planet);
                } 
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 8) +spacer)))   {    
                    genIcePlanet(model,planet);
                }
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 9) +spacer)))  {    
                    genIcePlanet(model,planet);     
                }    
        }
        if(sun.starType.equalsIgnoreCase("whiteDwarf"))  {
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 0) +spacer)))   {    
                    genAridPlanetTex(model,planet);
                }
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 1) +spacer)))   {    
                    genAridPlanetTex(model,planet);
                }   
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 2) +spacer)))   {    
                    genAridPlanetTex(model,planet);
                }  
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 3) +spacer)))   {    
                    genAridPlanetTex(model,planet);
                } 
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 4) +spacer)))    {    
                    genIcePlanet(model,planet);
                }  
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 5) +spacer)))   {    
                    genIcePlanet(model,planet);
                } 
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 6) +spacer)))    {    
                    genIcePlanet(model,planet);
                } 
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 7) +spacer)))    {    
                    genIcePlanet(model,planet);
                } 
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 8) +spacer)))    {    
                    genIcePlanet(model,planet);
                }
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 9) +spacer)))    {    
                    genIcePlanet(model,planet);     
                }    
        }   
        if(sun.starType.equalsIgnoreCase("yellow"))  {
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 0) +spacer)))    {    
                    genLavaWorldTex(model,planet);
                }
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 1) +spacer)))   {    
                    genAridPlanetTex(model,planet);
                }   
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 2) +spacer)))   {    
                    genAridPlanetTex(model,planet);
                }  
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 3) +spacer)))   {    
                    genSAridPlanetTex(model,planet);
                } 
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 4) +spacer)))    {    
                    genSAridPlanetTex(model,planet);
                }  
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 5) +spacer)))   {    
                    genWatherPlanet(model,planet);
                } 
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 6) +spacer)))     {    
                    genWatherPlanet(model,planet);
                } 
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 7) +spacer)))     {
                    genIcePlanet(model,planet);
                } 
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 8) +spacer)))    {    
                    genIcePlanet(model,planet);
                }
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 9) +spacer)))    {    
                    genIcePlanet(model,planet);     
                }    
        } 
        if(sun.starType.equalsIgnoreCase("blueGiant"))  {
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 0) +spacer)))    {    
                    genLavaWorldTex(model,planet);
                }
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 1) +spacer)))    {    
                    genAridPlanetTex(model,planet);
                }   
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 2) +spacer)))    {    
                    genAridPlanetTex(model,planet);
                }  
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 3) +spacer)))   {    
                    genSAridPlanetTex(model,planet);
                } 
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 4) +spacer)))    {    
                    genSAridPlanetTex(model,planet);
                }  
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 5) +spacer)))    {    
                    genSAridPlanetTex(model,planet);
                } 
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 6) +spacer)))    {    
                    genSAridPlanetTex(model,planet);
                } 
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 7) +spacer)))    {    
                    genSAridPlanetTex(model,planet);
                } 
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 8) +spacer)))    {    
                    genWatherPlanet(model,planet);
                }
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 9) +spacer)))   {    
                    genIcePlanet(model,planet);     
                }    
        }  
        if(sun.starType.equalsIgnoreCase("redGiant")) {
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 0) +spacer)))     {    
                    genLavaWorldTex(model,planet);
                }
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 1) +spacer)))    {    
                    genLavaWorldTex(model,planet);
                }   
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 2) +spacer)))    {    
                    genLavaWorldTex(model,planet);
                }  
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 3) +spacer)))   {    
                    genAridPlanetTex(model,planet);
                } 
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 4) +spacer)))    {    
                    genAridPlanetTex(model,planet);
                }  
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 5) +spacer)))    {    
                    genAridPlanetTex(model,planet);
                } 
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 6) +spacer)))   {    
                    genAridPlanetTex(model,planet);
                } 
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 7) +spacer)))    {    
                    genAridPlanetTex(model,planet);
                } 
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 8) +spacer)))   {    
                    genSAridPlanetTex(model,planet);
                }
                if(distanta ==Float.valueOf(df3.format(startP + (planetsSpacer * 9) +spacer)))   {
                    genWatherPlanet(model,planet);
                }    
        }        
        planet.model = model;
     
        if(load_from_file) {
          planet.owner = Integer.valueOf(planet_string_line[p_index]);
          if(planet.owner != -1) {
            quadsManagement.addPlanetRind(quadData.playerList.get(planet.owner),planet);
            sun.player_planets_own[quadData.playerList.get(planet.owner).index]++;
            p_index ++;
          }
        }        
     planet.index = planet_index; 
     planet_index++;
     return planet;   
    }
    
    public void genLavaWorldTex(Model quadP , Planet planet)  {
           boolean done = false;
           planet.isVulcanic = true;
           planet.classPlanet = 1;
           generateResources(planet , 0 , 5);
           while(!done)   {           
                done = false;
                int randNumber = random.nextInt(6);
                if(!lavaPlanet.contains(randNumber) && randNumber == 0)    {
                   quadP.INDEX_T = 4;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   lavaPlanet.add(randNumber);
                   done = true;
                }
                if(!lavaPlanet.contains(randNumber) && randNumber == 1)    {
                   quadP.INDEX_T = 5;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   lavaPlanet.add(randNumber);
                   done = true;
                }
                if(!lavaPlanet.contains(randNumber) && randNumber == 2)    {
                   quadP.INDEX_T = 6;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   lavaPlanet.add(randNumber);
                   done = true;
                } 
                if(!lavaPlanet.contains(randNumber) && randNumber == 3)    {
                   quadP.INDEX_T = 7;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   lavaPlanet.add(randNumber);
                   done = true;
                }
                if(!lavaPlanet.contains(randNumber) && randNumber == 4)    {
                   quadP.INDEX_T = 10;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   lavaPlanet.add(randNumber);
                   done = true;
                }                
                if(!lavaPlanet.contains(randNumber) && randNumber == 5)    {
                   quadP.INDEX_T = 11;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   lavaPlanet.add(randNumber);
                   done = true;
                }                   
           }    
    }
    public void genAridPlanetTex(Model quadP ,Planet planet)  {
           boolean done = false;
           planet.isArid = true;
           planet.classPlanet = 2;
           generateResources(planet , 0 , 5);
           while(!done)   {           
                done = false;
                int randNumber = random.nextInt(9);
                if(!aridPlanet.contains(randNumber) && randNumber == 0)    {
                   quadP.INDEX_T = 12;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   aridPlanet.add(randNumber);
                   done = true;
                }
                if(!aridPlanet.contains(randNumber) && randNumber == 1)    {
                   quadP.INDEX_T = 13;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   aridPlanet.add(randNumber);
                   done = true;
                }
                if(!aridPlanet.contains(randNumber) && randNumber == 2)    {
                   quadP.INDEX_T = 14;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   aridPlanet.add(randNumber);
                   done = true;
                } 
                if(!aridPlanet.contains(randNumber) && randNumber == 3)    {
                   quadP.INDEX_T = 15;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   aridPlanet.add(randNumber);
                   done = true;
                }                
                if(!aridPlanet.contains(randNumber) && randNumber == 4)    {
                   quadP.INDEX_T = 16;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   aridPlanet.add(randNumber);
                   done = true;
                } 
                if(!aridPlanet.contains(randNumber) && randNumber == 5)    {
                   quadP.INDEX_T = 17;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   aridPlanet.add(randNumber);
                   done = true;
                } 
                if(!aridPlanet.contains(randNumber) && randNumber == 6)    {
                   quadP.INDEX_T = 20;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   aridPlanet.add(randNumber);
                   done = true;
                }  
                if(!aridPlanet.contains(randNumber) && randNumber == 7)    {
                   quadP.INDEX_T = 21;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   aridPlanet.add(randNumber);
                   done = true;
                }  
                if(!aridPlanet.contains(randNumber) && randNumber == 8)    {
                   quadP.INDEX_T = 22;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   aridPlanet.add(randNumber);
                   done = true;
                }
           }    
    } 
    public void genSAridPlanetTex(Model quadP,Planet planet)  {
           boolean done = false;
           planet.isSemiarid = true;
           planet.classPlanet = 1;
           generateResources(planet , 0 , 5);
           while(!done)   {           
                done = false;
                int randNumber = random.nextInt(11);
                if(!SAridPlanet.contains(randNumber) && randNumber == 0)    {
                   quadP.INDEX_T = 23;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   SAridPlanet.add(randNumber);
                   done = true;
                }
                if(!SAridPlanet.contains(randNumber) && randNumber == 1)    {
                   quadP.INDEX_T = 24;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   SAridPlanet.add(randNumber);
                   done = true;
                }
                if(!SAridPlanet.contains(randNumber) && randNumber == 2)    {
                   quadP.INDEX_T = 25;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   SAridPlanet.add(randNumber);
                   done = true;
                } 
                if(!SAridPlanet.contains(randNumber) && randNumber == 3)    {
                   quadP.INDEX_T = 26;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   SAridPlanet.add(randNumber);
                   done = true;
                }                
                if(!SAridPlanet.contains(randNumber) && randNumber == 4)    {
                   quadP.INDEX_T = 27;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   SAridPlanet.add(randNumber);
                   done = true;
                } 
                if(!SAridPlanet.contains(randNumber) && randNumber == 5)    {
                   quadP.INDEX_T = 30;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   SAridPlanet.add(randNumber);
                   done = true;
                } 
                if(!SAridPlanet.contains(randNumber) && randNumber == 6)    {
                   quadP.INDEX_T = 31;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   SAridPlanet.add(randNumber);
                   done = true;
                }
               if(!SAridPlanet.contains(randNumber) && randNumber == 7)   {
                   quadP.INDEX_T = 55;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   SAridPlanet.add(randNumber);
                   done = true;
               }
               if(!SAridPlanet.contains(randNumber) && randNumber == 8)   {
                   quadP.INDEX_T = 56;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   SAridPlanet.add(randNumber);
                   done = true;
               }
               if(!SAridPlanet.contains(randNumber) && randNumber == 9)   {
                   quadP.INDEX_T = 28;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   SAridPlanet.add(randNumber);
                   done = true;
               }
               if(!SAridPlanet.contains(randNumber) && randNumber == 10)   {
                   quadP.INDEX_T = 18;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   SAridPlanet.add(randNumber);
                   done = true;
               }
           }    
    }  
    public void genWatherPlanet(Model quadP,Planet planet) {
           boolean done = false;
           planet.isWather = true;
           planet.classPlanet = 0;
           planet.planetMaxShipCount = watherMaxShipCount;
           generateResources(planet , 1 , 3);
           while(!done)   {           
                done = false;
                int randNumber = random.nextInt(12);
                if(!SAridPlanet.contains(randNumber) && randNumber == 0)    {
                   quadP.INDEX_T = 32;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   SAridPlanet.add(randNumber);
                   done = true;
                }                  
                if(!watherPlanet.contains(randNumber) && randNumber == 1)    {
                   quadP.INDEX_T = 33;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   watherPlanet.add(randNumber);
                   done = true;
                }
                if(!watherPlanet.contains(randNumber) && randNumber == 2)    {
                   quadP.INDEX_T = 34;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   watherPlanet.add(randNumber);
                   done = true;
                }
                if(!watherPlanet.contains(randNumber) && randNumber == 3)    {
                   quadP.INDEX_T = 35;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   watherPlanet.add(randNumber);
                   done = true;
                } 
                if(!watherPlanet.contains(randNumber) && randNumber == 4)    {
                   quadP.INDEX_T = 36;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   watherPlanet.add(randNumber);
                   done = true;
                }                
                if(!watherPlanet.contains(randNumber) && randNumber == 5)    {
                   quadP.INDEX_T = 37;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   watherPlanet.add(randNumber);
                   done = true;
                } 
                if(!watherPlanet.contains(randNumber) && randNumber == 6)    {
                   quadP.INDEX_T = 40;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   watherPlanet.add(randNumber);
                   done = true;
                } 
                if(!watherPlanet.contains(randNumber) && randNumber == 7)    {
                   quadP.INDEX_T = 41;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   watherPlanet.add(randNumber);
                   done = true;
                }  
                if(!watherPlanet.contains(randNumber) && randNumber == 8)    {
                   quadP.INDEX_T = 42;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   watherPlanet.add(randNumber);
                   done = true;
                }
               if(!watherPlanet.contains(randNumber) && randNumber == 9)    {
                   quadP.INDEX_T = 52;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   watherPlanet.add(randNumber);
                   done = true;
               }
               if(!watherPlanet.contains(randNumber) && randNumber == 10)   {
                   quadP.INDEX_T = 53;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   watherPlanet.add(randNumber);
                   done = true;
               }
               if(!watherPlanet.contains(randNumber) && randNumber == 11)    {
                   quadP.INDEX_T = 54;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   watherPlanet.add(randNumber);
                   done = true;
               }
           }    
    }    
    public void genIcePlanet(Model quadP , Planet planet)  {
           boolean done = false;
           planet.isIce = true;
           planet.classPlanet = 3;
           generateResources(planet , 0 , 2);
           while(!done)   {           
                done = false;
                int randNumber = random.nextInt(7);
                if(!icePlanet.contains(randNumber) && randNumber == 0)   {
                   quadP.INDEX_T = 44;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   icePlanet.add(randNumber);
                   done = true;
                }
                if(!icePlanet.contains(randNumber) && randNumber == 1)
                {
                   quadP.INDEX_T = 45;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   icePlanet.add(randNumber);
                   done = true;
                }
                if(!icePlanet.contains(randNumber) && randNumber == 2)    {
                   quadP.INDEX_T = 46;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   float scale = rand.getFloatRange(0.6f, 0.8f)/600;;
                   model.scale.x = scale; 
                   model.scale.y = scale;
                   model.scale.z = scale;
                   icePlanet.add(randNumber);
                   done = true;
                } 
                if(!icePlanet.contains(randNumber) && randNumber == 3)    {
                   quadP.INDEX_T = 47;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   icePlanet.add(randNumber);
                   done = true;
                }                
                if(!icePlanet.contains(randNumber) && randNumber == 4)    {
                   quadP.INDEX_T = 50;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   icePlanet.add(randNumber);
                   done = true;
                } 
                if(!icePlanet.contains(randNumber) && randNumber == 5)   {
                   quadP.INDEX_T = 51;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   icePlanet.add(randNumber);
                   done = true;
                } 
                if(!icePlanet.contains(randNumber) && randNumber == 6) {
                   quadP.INDEX_T = 43;
                   if(load_from_file) {
                     quadP.INDEX_T = Integer.valueOf(planet_string_line[p_index]);
                     p_index++;
                   }
                   icePlanet.add(randNumber);
                   done = true;
                }  
           }    
    }
    public void generateResources(Planet planet , int start , int delta)  {
        int  energy = 0;
        int  metalOre = 0;
        int  mineral = 0;
        int  carbon = 0;
        int  gas = 0;
        if(planet.isVulcanic) {
            planet.resources.biomas = 10;
            metalOre = 5;
        }
        if(planet.isArid) {
            planet.resources.biomas = 1+ random.nextInt(2);
        }
        if(planet.isSemiarid) {
            planet.resources.biomas = 1+ random.nextInt(3);
            mineral = 5;
        }
        if(planet.isWather) {
            planet.resources.biomas =  5 +random.nextInt(6);
            carbon = 5;
        }
        if(planet.isIce){
            planet.resources.biomas =  1 +random.nextInt(2);
            gas = 5;
        }
        planet.resources.maxPopulation = planet.resources.biomas * 10;
        planet.resources.energy   = start + random.nextInt(delta+energy);
        planet.resources.metalOre = start + random.nextInt(delta+metalOre);
        planet.resources.minerals = start + random.nextInt(delta+mineral);
        planet.resources.carbon   = start + random.nextInt(delta+carbon);
        planet.resources.gas      = start + random.nextInt(delta+gas);
    }
}
