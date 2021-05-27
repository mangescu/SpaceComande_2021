
package Input;

import AILogic.ResValidation;
import Camera.Camera;
import Campaign.Campaign;
import Campaign.GameSetings;
import Display.SetupDisplay_LWJGL_3;
import GameSaveLoad.Save;
import IBO.IBO;
import Maths.Maths;
import Menu.MenuData;
import Planet.Planet;
import Player.Player;
import QuadModel.Model;
import Render.Renderer;
import SolarSystem.SolarSystem;
import Sun.Sun;
import TextUtil.TextData;
import Texture.TextureUtil;
import Util.MouseMenuFunctionImplementation;
import Util.MousePicker;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glReadPixels;
import spacecomander.Quad;
import spacecomander.QuadData;
import spacecomander.QuadUtil;
import spacecomander.SpaceComander;
import static spacecomander.SpaceComander.race_index;

public class MouseClickCallback extends GLFWMouseButtonCallback{

   public Input input; 
   public int   game_mode_type = 0;   
   private MousePicker pick ;
   private MouseMenuFunctionImplementation   serch;
   private StringBuilder stringB = new StringBuilder();
    private float x_drag_start = 0  , y_drag_start = 0;
    private float x_drag_end = 0 , y_drag_end = 0;
    private Vector3f curentRay , menuSelectRay ;
    private SetupDisplay_LWJGL_3   display;
    private Camera camera;
    private Renderer   renderer;
    private Maths maths;
    private QuadUtil quadUtil;
    public  MenuData menuData;
    private IBO      ibo;
    private TextureUtil textureUtil;
    SolarSystem solarSystem;
    Save save;
    Campaign campaign;      
    ScrollCallback scroll;
    public Model galaxyMap;
    QuadData  quadData;  
    public  Sun sun;
    public  Planet planet; 
    private int start_coloni_index = 0;
    private int end_coloni_index = 9;
    private float coloni_delta = 0;
    private int page = 0;
    private TextData  textData;
    public  float ship_select_delta;
    int race_index;


    public boolean enabledragSelection = true;
 
    DoubleBuffer posX =  BufferUtils.createDoubleBuffer(1);
    DoubleBuffer posY =  BufferUtils.createDoubleBuffer(1);
    
    public Model  mapM;
    DecimalFormat df = new DecimalFormat("#");
    GameSetings  gameSetings;
    ResValidation resValidation;
    
    int temp_galaxySize;
    int temp_playerCount;
    
    boolean temp_auto_colonize;
    boolean temp_auto_deplay;
    boolean temp_auto_assignWorker;
    boolean temp_auto_AddShip;
    
    public MouseClickCallback(Model  mapM , SetupDisplay_LWJGL_3  display , Camera camera, Renderer renderer , Maths maths , QuadUtil quadUtil  , MenuData menuData ,  IBO ibo,TextureUtil textureUtil ,int race_index   , SolarSystem solarSystem,Campaign campaign,ScrollCallback scroll,QuadData  quadData,GameSetings  gameSetings,Model galaxyMap,Save save,ResValidation resValidation){
        this.display = display;
        this.camera = camera;
        this.renderer = renderer;
        this.maths = maths;
        this.quadUtil = quadUtil;
        this.menuData = menuData;
        this.ibo = ibo;
        this.textureUtil = textureUtil;
        this.race_index = race_index;
        this.mapM = mapM;
        this.galaxyMap = galaxyMap;
        this.solarSystem = solarSystem;
        this.campaign = campaign;
        this.scroll = scroll;
        this.quadData = quadData;
        this.gameSetings = gameSetings;
        this.save = save;
        this.resValidation = resValidation;
        temp_galaxySize = gameSetings.galaxy_size;
        temp_playerCount = gameSetings.numberOfPlayers;
        
        temp_auto_colonize = gameSetings.auto_colonizePlanet; 
        temp_auto_deplay = gameSetings.auto_deploySquad;
        temp_auto_AddShip = gameSetings.auto_addShip;
        temp_auto_assignWorker = gameSetings.auto_assign_worker;        

        pick  = new MousePicker(display , camera , renderer.getProjectionMatrix() , maths);
        serch = new MouseMenuFunctionImplementation(menuData);
        resetBooleanVar();
        spacecomander.SpaceComander.colony_menu_1 = true;
    }
    
    @Override
    public void invoke(long window, int button, int action, int mods) {

                if(button == GLFW_MOUSE_BUTTON_1)
                {
                    curentRay = pick.update(false , 0 , 0);
                    menuSelectRay = pick.getMCurentRay();
                   if(!SpaceComander.game_pause)
                   {
                    if(action == GLFW_PRESS) {

                        spacecomander.SpaceComander.enablePixelRead = true;
                        x_drag_start = curentRay.x;
                        y_drag_start = curentRay.y;
                        x_drag_end = curentRay.x;
                        y_drag_end = curentRay.y;

                           SpaceComander.selectQuadStatus = true;
                           SpaceComander.selectQuadX = curentRay.x;
                           SpaceComander.selectQuadY = curentRay.y;

                          if(SpaceComander.colonyShipControlKey) {
                             quadData.playerList.get(SpaceComander.race_index).enableColoniShipPriority = true;
                             quadData.playerList.get(SpaceComander.race_index).x_priority =  curentRay.x;
                             quadData.playerList.get(SpaceComander.race_index).y_priority =  curentRay.y; 
                             if(quadData.playerList.get(SpaceComander.race_index).coloniShip != null) {
                                quadData.playerList.get(SpaceComander.race_index).coloniShip.translateQuad(quadData.playerList.get(SpaceComander.race_index).x_priority, quadData.playerList.get(SpaceComander.race_index).y_priority);
                             }
                         }                 

                        //__PLANET_MENU____________________________________________________________________//
                        if (spacecomander.SpaceComander.planet_select_menu) {
                            int index = serch.selectplanetMenuOption(menuSelectRay.x, menuSelectRay.y);
                            
                            if(index == 1) {
                              generateColoniMenu_2(0,0,false);  
                            }
                            if (index == 2) {
                                createBuildingsMenu("");
                            }
                            if (index == 3) {
                                createShipUpgradesMenu("");
                            }
                            if (index == 4) {
                                createShipYardMenu("");
                            }
                            if (index == 5) {
                                createCargoBayMenu(planet, "Message....");
                            }
                            if (index == 17) {
                                if (planet.resources.workersAvaible > 0) {
                                    planet.resources.energyWorker++;
                                    planet.resources.workersAvaible--;
                                    generatePlanetMenu(planet);
                                }
                            }
                            if (index == 22) {
                                if (planet.resources.energyWorker > 0) {
                                    planet.resources.energyWorker--;
                                    planet.resources.workersAvaible++;
                                    generatePlanetMenu(planet);
                                }
                            }
                            if (index == 18) {
                                if (planet.resources.workersAvaible > 0) {
                                    planet.resources.metalOreWorker++;
                                    planet.resources.workersAvaible--;
                                    generatePlanetMenu(planet);
                                }
                            }
                            if (index == 23) {
                                if (planet.resources.metalOreWorker > 0) {
                                    planet.resources.metalOreWorker--;
                                    planet.resources.workersAvaible++;
                                    generatePlanetMenu(planet);
                                }
                            }
                            if (index == 19) {
                                if (planet.resources.workersAvaible > 0) {
                                    planet.resources.mineralsWorker++;
                                    planet.resources.workersAvaible--;
                                    generatePlanetMenu(planet);
                                }
                            }
                            if (index == 24) {
                                if (planet.resources.mineralsWorker > 0) {
                                    planet.resources.mineralsWorker--;
                                    planet.resources.workersAvaible++;
                                    generatePlanetMenu(planet);
                                }
                            }
                            if (index == 20) {
                                if (planet.resources.workersAvaible > 0) {
                                    planet.resources.carbonWorker++;
                                    planet.resources.workersAvaible--;
                                    generatePlanetMenu(planet);
                                }
                            }
                            if (index == 25) {
                                if (planet.resources.carbonWorker > 0) {
                                    planet.resources.carbonWorker--;
                                    planet.resources.workersAvaible++;
                                    generatePlanetMenu(planet);
                                }
                            }
                            if (index == 21) {
                                if (planet.resources.workersAvaible > 0) {
                                    planet.resources.gasWorker++;
                                    planet.resources.workersAvaible--;
                                    generatePlanetMenu(planet);
                                }
                            }
                            if (index == 26) {
                                if (planet.resources.gasWorker > 0) {
                                    planet.resources.gasWorker--;
                                    planet.resources.workersAvaible++;
                                    generatePlanetMenu(planet);
                                }
                            }
                        }
                        //____________________________________________________________________________________//
                        if (SpaceComander.ship_select_menu) {
                            int index = serch.selectedShipOption(menuSelectRay.x, menuSelectRay.y);
                            if (index == -1){
                                SpaceComander.ship_select_menu = false;
                                resetBooleanVar();
                                clearMENU();   
                                enableColoniMenu_1();
                                enabledragSelection  = true;
                            }
                            ;
                            if (index == 0) {
                                quadData.shipSelectionRing.clear();
                                for (Quad ship : quadData.playerList.get(race_index).shipList) {
                                    if (!ship.isCargoShip) {
                                        ship.isSlected = false;
                                    } else {
                                        if(ship.isSlected) {
                                            quadUtil.quadManagement.addShipSelectRing(ship);
                                        }
                                    }
                                }
                                quadUtil.coloni_ship_count = 0;
                                quadUtil.explorer_ship_count = 0;
                                quadUtil.fregate_ship_count =0;
                                quadUtil.destroier_ship_count = 0;
                                quadUtil.carrier_ship_count =0;
                                quadUtil.interceptor_ship_count  =0;
                                quadUtil.mother_ship_count = 0;

                                clearMENU();   
                                menuData.createShipSelectMenu(camera, quadData.playerList.get(race_index));
                                shipSelectCountPrint();
                            }
                            if (index == 1) {
                                quadData.shipSelectionRing.clear();
                                for (Quad ship : quadData.playerList.get(race_index).shipList) {
                                    if (!ship.isColoniShip) {
                                        ship.isSlected = false;
                                    } else {
                                        if(ship.isSlected) {
                                            quadUtil.quadManagement.addShipSelectRing(ship);
                                        }
                                    }
                                }
                                quadUtil.cargo_ship_count = 0;
                                quadUtil.explorer_ship_count = 0;
                                quadUtil.fregate_ship_count =0;
                                quadUtil.destroier_ship_count = 0;
                                quadUtil.carrier_ship_count =0;
                                quadUtil.interceptor_ship_count  =0;
                                quadUtil.mother_ship_count = 0;

                                clearMENU();   
                                menuData.createShipSelectMenu(camera, quadData.playerList.get(race_index));
                                shipSelectCountPrint();
                            }
                            if (index == 2) {
                                quadData.shipSelectionRing.clear();
                                for (Quad ship : quadData.playerList.get(race_index).shipList) {
                                    if (!ship.isInterceptor) {
                                        ship.isSlected = false;
                                    } else {
                                        if(ship.isSlected) {
                                            quadUtil.quadManagement.addShipSelectRing(ship);
                                        }
                                    }
                                }
                                quadUtil.cargo_ship_count = 0;
                                quadUtil.coloni_ship_count = 0;
                                quadUtil.explorer_ship_count = 0;
                                quadUtil.fregate_ship_count =0;
                                quadUtil.destroier_ship_count = 0;
                                quadUtil.carrier_ship_count =0;
                                quadUtil.mother_ship_count = 0;

                                clearMENU();   
                                menuData.createShipSelectMenu(camera, quadData.playerList.get(race_index));
                                shipSelectCountPrint();
                            }
                            if (index == 3) {
                                quadData.shipSelectionRing.clear();
                                for (Quad ship : quadData.playerList.get(race_index).shipList) {
                                    if (!ship.isExploreShip) {
                                        ship.isSlected = false;
                                    } else {
                                        if(ship.isSlected) {
                                            quadUtil.quadManagement.addShipSelectRing(ship);
                                        }
                                    }
                                }
                                quadUtil.cargo_ship_count = 0;
                                quadUtil.coloni_ship_count = 0;
                                quadUtil.fregate_ship_count =0;
                                quadUtil.destroier_ship_count = 0;
                                quadUtil.carrier_ship_count =0;
                                quadUtil.interceptor_ship_count  =0;
                                quadUtil.mother_ship_count = 0;
                                quadUtil.selected_ship_count = quadUtil.explorer_ship_count;

                                clearMENU();   
                                menuData.createShipSelectMenu(camera, quadData.playerList.get(race_index));
                                shipSelectCountPrint();
                            }
                            if (index == 4) {
                                quadData.shipSelectionRing.clear();
                                for (Quad ship : quadData.playerList.get(race_index).shipList) {
                                    if (!ship.isDestroierShip) {
                                        ship.isSlected = false;
                                    } else {
                                        if(ship.isSlected) {
                                            quadUtil.quadManagement.addShipSelectRing(ship);
                                        }
                                    }
                                }
                                quadUtil.cargo_ship_count = 0;
                                quadUtil.coloni_ship_count = 0;
                                quadUtil.explorer_ship_count = 0;
                                quadUtil.fregate_ship_count =0;
                                quadUtil.carrier_ship_count =0;
                                quadUtil.interceptor_ship_count  =0;
                                quadUtil.mother_ship_count = 0;
                                quadUtil.selected_ship_count = quadUtil.destroier_ship_count;

                                clearMENU();   
                                menuData.createShipSelectMenu(camera, quadData.playerList.get(race_index));
                                shipSelectCountPrint();
                            }
                            if (index == 5) {
                                quadData.shipSelectionRing.clear();
                                for (Quad ship : quadData.playerList.get(race_index).shipList) {
                                    if (!ship.isCarrierShip) {
                                        ship.isSlected = false;
                                    } else {
                                        if(ship.isSlected) {
                                            quadUtil.quadManagement.addShipSelectRing(ship);
                                        }
                                    }
                                }
                                quadUtil.cargo_ship_count = 0;
                                quadUtil.coloni_ship_count = 0;
                                quadUtil.explorer_ship_count = 0;
                                quadUtil.fregate_ship_count =0;
                                quadUtil.destroier_ship_count = 0;
                                quadUtil.interceptor_ship_count  =0;
                                quadUtil.mother_ship_count = 0;
                                quadUtil.selected_ship_count = quadUtil.carrier_ship_count;

                                clearMENU();   
                                menuData.createShipSelectMenu(camera, quadData.playerList.get(race_index));
                                shipSelectCountPrint();
                            }
                            if (index == 6) {
                                quadData.shipSelectionRing.clear();
                                for (Quad ship : quadData.playerList.get(race_index).shipList) {
                                    if (!ship.isMotherShip) {
                                        ship.isSlected = false;
                                    } else {
                                        if(ship.isSlected) {
                                            quadUtil.quadManagement.addShipSelectRing(ship);
                                        }
                                    }
                                }
                                quadUtil.cargo_ship_count = 0;
                                quadUtil.coloni_ship_count = 0;
                                quadUtil.explorer_ship_count = 0;
                                quadUtil.fregate_ship_count =0;
                                quadUtil.destroier_ship_count = 0;
                                quadUtil.carrier_ship_count =0;
                                quadUtil.interceptor_ship_count  =0;
                                quadUtil.selected_ship_count = quadUtil.mother_ship_count;

                                clearMENU();   
                                menuData.createShipSelectMenu(camera, quadData.playerList.get(race_index));
                                shipSelectCountPrint();
                            }
                            if (index == 7) {
                                quadData.shipSelectionRing.clear();
                                for (Quad ship : quadData.playerList.get(race_index).shipList) {
                                    if (!ship.isFreagateShip) {
                                        ship.isSlected = false;
                                    } else {
                                        if(ship.isSlected) {
                                            quadUtil.quadManagement.addShipSelectRing(ship);
                                        }
                                    }
                                }
                                quadUtil.cargo_ship_count = 0;
                                quadUtil.coloni_ship_count = 0;
                                quadUtil.explorer_ship_count = 0;
                                quadUtil.destroier_ship_count = 0;
                                quadUtil.carrier_ship_count =0;
                                quadUtil.interceptor_ship_count  =0;
                                quadUtil.mother_ship_count = 0;
                                quadUtil.selected_ship_count = quadUtil.fregate_ship_count;

                                clearMENU();   
                                menuData.createShipSelectMenu(camera, quadData.playerList.get(race_index));
                                shipSelectCountPrint();
                            }
                            if (index == 8) {
                                quadData.shipSelectionRing.clear();
                                for (Quad ship : quadData.playerList.get(race_index).shipList) {
                                    if (!ship.isFreagateShip) {
                                        ship.isSlected = false;
                                    } else {
                                        quadUtil.quadManagement.addShipSelectRing(ship);
                                    }
                                }
                                quadUtil.cargo_ship_count = 0;
                                quadUtil.coloni_ship_count = 0;
                                quadUtil.explorer_ship_count = 0;
                                quadUtil.destroier_ship_count = 0;
                                quadUtil.carrier_ship_count =0;
                                quadUtil.interceptor_ship_count  =0;
                                quadUtil.mother_ship_count = 0;
                                quadUtil.selected_ship_count = quadUtil.fregate_ship_count;

                               clearMENU();   

                                menuData.createShipSelectMenu(camera, quadData.playerList.get(race_index));
                                shipSelectCountPrint();
                            }
                         }
                        if(!SpaceComander.ship_select_menu) {
                            quadUtil.checkShipSelection(quadData.playerList, curentRay.x, curentRay.y);
                            if(quadUtil.selected_ship_count != 0)    {
                                clearMENU();   
                                resetBooleanVar();
                                menuData.createShipSelectMenu(camera, quadData.playerList.get(race_index));
                                shipSelectCountPrint();
                                SpaceComander.ship_select_menu = true;
                                SpaceComander.shipSelectMenuText = true;
                            }
                        }
                        //___BUILDING_________________________________________________________________________//
                        if(spacecomander.SpaceComander.buildings_menu)   {

                            int index = serch.buildingMenuOption(menuSelectRay.x, menuSelectRay.y);
                            if(index == 15){
                                generatePlanetMenu(planet);
                            }
                            if(index == 8){
                                  if(resValidation.upgraderMiningF(planet)) {
                                    planet.resources.energyLevel++;
                                    createBuildingsMenu("Message: done.....");
                                    resValidation.incrementUpgratesCost(planet, 0);
                                } else {
                                    stringB.setLength(0);
                                    stringB.append("Message: need more resources....."+planet.resources.energyUpgradeCost+" "+planet.resources.metalOreUpgradeCost+" "+planet.resources.mineralsUpgradeCost+" "+planet.resources.carbonUpgradeCost+" "+planet.resources.creditUpgradeCost+" "+planet.resources.gasUpgradeCost);
                                    createBuildingsMenu(stringB.toString());
                                }
                            }
                            if(index == 9) {
                                if(resValidation.upgraderMiningF(planet)) {
                                    planet.resources.metalOreLevel++;
                                    createBuildingsMenu("Message: done.....");
                                    resValidation.incrementUpgratesCost(planet, 1);
                                } else {
                                    stringB.setLength(0);
                                    stringB.append("Message: need more resources....."+planet.resources.energyUpgradeCost+" "+planet.resources.metalOreUpgradeCost+" "+planet.resources.mineralsUpgradeCost+" "+planet.resources.carbonUpgradeCost+" "+planet.resources.creditUpgradeCost+" "+planet.resources.gasUpgradeCost);
                                    createBuildingsMenu(stringB.toString());
                                }
                            }
                            if(index == 10){
                                if(resValidation.upgraderMiningF(planet)) {
                                    planet.resources.mineralsLevel++;
                                    createBuildingsMenu("Message: done.....");
                                    resValidation.incrementUpgratesCost(planet, 2);
                                } else {
                                    stringB.setLength(0);
                                    stringB.append("Message: need more resources....."+planet.resources.energyUpgradeCost+" "+planet.resources.metalOreUpgradeCost+" "+planet.resources.mineralsUpgradeCost+" "+planet.resources.carbonUpgradeCost+" "+planet.resources.creditUpgradeCost+" "+planet.resources.gasUpgradeCost);
                                    createBuildingsMenu(stringB.toString());
                                }
                            }
                            if(index == 11){
                                if(resValidation.upgraderMiningF(planet)) {
                                    planet.resources.carbonLevel++;
                                    createBuildingsMenu("Message: done.....");
                                    resValidation.incrementUpgratesCost(planet, 3);
                                } else {
                                    stringB.setLength(0);
                                    stringB.append("Message: need more resources....."+planet.resources.energyUpgradeCost+" "+planet.resources.metalOreUpgradeCost+" "+planet.resources.mineralsUpgradeCost+" "+planet.resources.carbonUpgradeCost+" "+planet.resources.creditUpgradeCost+" "+planet.resources.gasUpgradeCost);
                                    createBuildingsMenu(stringB.toString());
                                }
                            }
                            if(index == 12){
                                 if(resValidation.upgraderMiningF(planet)) {
                                    planet.resources.creditLevel++;
                                    createBuildingsMenu("Message: done.....");
                                    resValidation.incrementUpgratesCost(planet, 4);
                                } else {
                                    stringB.setLength(0);
                                    stringB.append("Message: need more resources....."+planet.resources.energyUpgradeCost+" "+planet.resources.metalOreUpgradeCost+" "+planet.resources.mineralsUpgradeCost+" "+planet.resources.carbonUpgradeCost+" "+planet.resources.creditUpgradeCost+" "+planet.resources.gasUpgradeCost);
                                    createBuildingsMenu(stringB.toString());
                                }
                            }
                            if(index == 13){
                                 if(resValidation.upgraderMiningF(planet)) {
                                    planet.resources.gasLevel++;
                                    createBuildingsMenu("Message: done.....");
                                    resValidation.incrementUpgratesCost(planet, 5);
                                } else {
                                    stringB.setLength(0);
                                    stringB.append("Message: need more resources....."+planet.resources.energyUpgradeCost+" "+planet.resources.metalOreUpgradeCost+" "+planet.resources.mineralsUpgradeCost+" "+planet.resources.carbonUpgradeCost+" "+planet.resources.creditUpgradeCost+" "+planet.resources.gasUpgradeCost);
                                    createBuildingsMenu(stringB.toString());
                                }
                            }
                        }
                        //____________________________________________________________________________________//
                        //__CARGO_BAY_________________________________________________________________________//
                        if(SpaceComander.cargoBay_Menu)
                        {
                            int index = serch.shipYardMenuOption(menuSelectRay.x, menuSelectRay.y);
                            if(index == 30){
                                generatePlanetMenu(planet);
                            }
                            if(index == 16){
                                if(planet.resources.energyMined >= planet.resources.stockTransferRate)   {
                                    planet.resources.cargoDoc_energy_Stock += planet.resources.stockTransferRate;
                                    planet.resources.energyMined -= planet.resources.stockTransferRate;
                                    createCargoBayMenu(planet, "Message....");
                                } else {
                                    createCargoBayMenu(planet, "Message....  Need more resources");
                                }
                            }
                            if(index == 22){
                                if(planet.resources.cargoDoc_energy_Stock >= planet.resources.stockTransferRate)   {
                                    planet.resources.energyMined += planet.resources.stockTransferRate;
                                    planet.resources.cargoDoc_energy_Stock -= planet.resources.stockTransferRate;
                                    createCargoBayMenu(planet, "Message....");
                                } else {
                                    createCargoBayMenu(planet, "Message....  Need more resources");
                                }
                            }
                            if(index == 17){
                                if(planet.resources.metalOreMined >= planet.resources.stockTransferRate)   {
                                    planet.resources.cargoDoc_metalOre_Stock += planet.resources.stockTransferRate;
                                    planet.resources.metalOreMined -= planet.resources.stockTransferRate;
                                    createCargoBayMenu(planet, "Message....");
                                } else {
                                    createCargoBayMenu(planet, "Message....  Need more resources");
                                }
                            }
                            if(index == 23){
                                if(planet.resources.cargoDoc_metalOre_Stock >= planet.resources.stockTransferRate)   {
                                    planet.resources.metalOreMined += planet.resources.stockTransferRate;
                                    planet.resources.cargoDoc_metalOre_Stock -= planet.resources.stockTransferRate;
                                    createCargoBayMenu(planet, "Message....");
                                } else {
                                    createCargoBayMenu(planet, "Message....  Need more resources");
                                }
                            }
                            if(index == 18){
                                if(planet.resources.mineralsMined >= planet.resources.stockTransferRate)   {
                                    planet.resources.cargoDoc_mineral_Stock += planet.resources.stockTransferRate;
                                    planet.resources.mineralsMined -= planet.resources.stockTransferRate;
                                    createCargoBayMenu(planet, "Message....");
                                } else {
                                    createCargoBayMenu(planet, "Message....  Need more resources");
                                }
                            }
                            if(index == 24){
                                if(planet.resources.cargoDoc_mineral_Stock >= planet.resources.stockTransferRate) {
                                    planet.resources.mineralsMined += planet.resources.stockTransferRate;
                                    planet.resources.cargoDoc_mineral_Stock -= planet.resources.stockTransferRate;
                                    createCargoBayMenu(planet, "Message....");
                                } else {
                                    createCargoBayMenu(planet, "Message....  Need more resources");
                                }
                            }
                            if(index == 19){
                                if(planet.resources.carbonMined >= planet.resources.stockTransferRate)   {
                                    planet.resources.cargoDoc_carbon_Stock += planet.resources.stockTransferRate;
                                    planet.resources.carbonMined -= planet.resources.stockTransferRate;
                                    createCargoBayMenu(planet, "Message....");
                                } else {
                                    createCargoBayMenu(planet, "Message....  Need more resources");
                                }
                            }
                            if(index == 25){
                                if(planet.resources.cargoDoc_carbon_Stock >= planet.resources.stockTransferRate) {
                                    planet.resources.carbonMined += planet.resources.stockTransferRate;
                                    planet.resources.cargoDoc_carbon_Stock -= planet.resources.stockTransferRate;
                                    createCargoBayMenu(planet, "Message....");
                                } else {
                                    createCargoBayMenu(planet, "Message....  Need more resources");
                                }
                            }
                            if(index == 20){
                                if(planet.resources.creditMined >= planet.resources.stockTransferRate) {
                                    planet.resources.cargoDoc_credit_Stock += planet.resources.stockTransferRate;
                                    planet.resources.creditMined -= planet.resources.stockTransferRate;
                                    createCargoBayMenu(planet, "Message....");
                                } else {
                                    createCargoBayMenu(planet, "Message....  Need more resources");
                                }
                            }
                            if(index == 26){
                                if(planet.resources.cargoDoc_credit_Stock >= planet.resources.stockTransferRate)   {
                                    planet.resources.creditMined += planet.resources.stockTransferRate;
                                    planet.resources.cargoDoc_credit_Stock -= planet.resources.stockTransferRate;
                                    createCargoBayMenu(planet, "Message....");
                                } else {
                                    createCargoBayMenu(planet, "Message....  Need more resources");
                                }
                            }
                            if(index == 21){
                                if(planet.resources.gasMined >= planet.resources.stockTransferRate)   {
                                    planet.resources.cargoDoc_gas_Stock += planet.resources.stockTransferRate;
                                    planet.resources.gasMined -= planet.resources.stockTransferRate;
                                    createCargoBayMenu(planet, "Message....");
                                } else {
                                    createCargoBayMenu(planet, "Message....  Need more resources");
                                }
                            }
                            if(index == 27){
                                if(planet.resources.cargoDoc_gas_Stock >= planet.resources.stockTransferRate)   {
                                    planet.resources.gasMined += planet.resources.stockTransferRate;
                                    planet.resources.cargoDoc_gas_Stock -= planet.resources.stockTransferRate;
                                    createCargoBayMenu(planet, "Message....");
                                }
                            }
                        }
                        //__SHIP_YARD_________________________________________________________________________//
                        if(spacecomander.SpaceComander.shipYard_menu)
                        {
                            int index = serch.shipYardMenuOption(menuSelectRay.x, menuSelectRay.y);
                            if(index == 17){
                                generatePlanetMenu(planet);
                            }
                            if(index == 9){
                                if(planet.resources.cargoDoc_energy_Stock >0 || planet.resources.cargoDoc_metalOre_Stock >0 || planet.resources.cargoDoc_mineral_Stock >0 || planet.resources.cargoDoc_carbon_Stock >0 || planet.resources.cargoDoc_credit_Stock >0 || planet.resources.cargoDoc_gas_Stock >0) {
                                   Quad  ship =  quadUtil.quadManagement.addCargoShip(planet, quadData.playerList.get(race_index), planet.position.x + 0.1f / quadUtil.quadManagement.scaleQuad, planet.position.y + 0.1f / quadUtil.quadManagement.scaleQuad);
                                         ship.autoCargoShip = false;
                                         ship.energyCargo   = planet.resources.cargoDoc_energy_Stock;
                                         ship.metalOreCargo = planet.resources.cargoDoc_metalOre_Stock;
                                         ship.mineralCargo  = planet.resources.cargoDoc_mineral_Stock;
                                         ship.carbonCargo   = planet.resources.cargoDoc_carbon_Stock;
                                         ship.creditCargo   = planet.resources.cargoDoc_credit_Stock;
                                         ship.gasCargo      = planet.resources.cargoDoc_gas_Stock;
                                    planet.resources.cargoDoc_energy_Stock = 0;
                                    planet.resources.cargoDoc_metalOre_Stock = 0;
                                    planet.resources.cargoDoc_mineral_Stock = 0;
                                    planet.resources.cargoDoc_carbon_Stock = 0;
                                    planet.resources.cargoDoc_credit_Stock = 0;
                                    planet.resources.cargoDoc_gas_Stock = 0;
                                } else {
                                    createShipYardMenu("message: cargo bay is empty.....");
                                }
                            }
                            if(index == 10){
                                if(resValidation.checkShipProductionResources(planet, planet.resources.colonyShipCost))  {
                                    if(quadData.playerList.get(0).coloniShip == null) {
                                        quadUtil.quadManagement.addColonizationShip(planet, quadData.playerList.get(race_index), planet.position.x+0.04f/quadUtil.quadManagement.scaleQuad, planet.position.y+0.04f/quadUtil.quadManagement.scaleQuad);
                                        createShipYardMenu("message: done...");
                                    } else {
                                        createShipYardMenu("message: colony ships count exceeded.....");
                                    }
                                } else {
                                    createShipYardMenu("message: need more resources...");
                                }
                            }
                            if(index == 11){
                                    if(planet.shipCount < planet.planetMaxShipCount) {
                                       if(resValidation.checkShipProductionResources(planet, planet.resources.exploreShipCost))  {
                                            quadUtil.quadManagement.addExploreShip(planet, quadData.playerList.get(race_index), planet.position.x + 0.04f / quadUtil.quadManagement.scaleQuad, planet.position.y + 0.04f / quadUtil.quadManagement.scaleQuad);
                                            createShipYardMenu("message: done...");
                                        } else {
                                            createShipYardMenu("message: need more resources...");
                                        }
                                    } else {
                                        createShipYardMenu("message: maxim planet ship count... "+String.valueOf(planet.shipCount)+" "+String.valueOf(planet.planetMaxShipCount));
                                    }
                            }
                            if(index == 12){
                                    if(planet.shipCount < planet.planetMaxShipCount) {
                                        if(resValidation.checkShipProductionResources(planet, planet.resources.freagateShipCost))  {
                                            quadUtil.quadManagement.addFreagateShip(planet, quadData.playerList.get(race_index), planet.position.x + 0.04f / quadUtil.quadManagement.scaleQuad, planet.position.y + 0.04f / quadUtil.quadManagement.scaleQuad);
                                            createShipYardMenu("message: done...");
                                        } else {
                                            createShipYardMenu("message: need more resources...");
                                        }
                                    } else {
                                        createShipYardMenu("message: maxim planet ship count...");
                                    }
                            }
                            if(index == 13){

                                    if(planet.shipCount < planet.planetMaxShipCount) {
                                         if(resValidation.checkShipProductionResources(planet, planet.resources.destroierShipCost))  {
                                            quadUtil.quadManagement.addDestroierShip(planet, quadData.playerList.get(race_index), planet.position.x + 0.04f / quadUtil.quadManagement.scaleQuad, planet.position.y + 0.04f / quadUtil.quadManagement.scaleQuad);
                                            createShipYardMenu("message: done...");
                                        } else {
                                            createShipYardMenu("message: need more resources...");
                                        }
                                    } else {
                                        createShipYardMenu("message: maxim planet ship count...");
                                    }
                            }
                            if(index == 14){

                                    if(planet.shipCount < planet.planetMaxShipCount) {
                                         if(resValidation.checkShipProductionResources(planet, planet.resources.carrierShipCost))  {
                                            quadUtil.quadManagement.addCarrierShip(planet, quadData.playerList.get(race_index), planet.position.x + 0.04f / quadUtil.quadManagement.scaleQuad, planet.position.y + 0.04f / quadUtil.quadManagement.scaleQuad);
                                            createShipYardMenu("message: done...");
                                        } else {
                                            createShipYardMenu("message: need more resources...");
                                        }
                                    } else {
                                        createShipYardMenu("message: maxim planet ship count...");
                                    }
                            }
                            if(index == 15){

                                    if(planet.shipCount < planet.planetMaxShipCount) {
                                        if(resValidation.checkShipProductionResources(planet, planet.resources.motherShipCost))  {
                                            quadUtil.quadManagement.addMotherShip(planet, quadData.playerList.get(race_index), planet.position.x + 0.04f / quadUtil.quadManagement.scaleQuad, planet.position.y + 0.04f / quadUtil.quadManagement.scaleQuad);
                                            createShipYardMenu("message: done...");
                                        } else {
                                            createShipYardMenu("message: need more resources...");
                                        }
                                    } else {
                                        createShipYardMenu("message: maxim planet ship count...");
                                    }
                            }
                        }
                        //__SHIP_UPGRADE_MENU_________________________________________________________________//
                        if(spacecomander.SpaceComander.upgrades_menu)
                        {
                            int index = serch.shipYardMenuOption(menuSelectRay.x, menuSelectRay.y);
                            if(index == 11){
                                generatePlanetMenu(planet);
                            }
                            if(index == 6) {
                                if (planet.resources.engineUpgradeLevel < 3) {  
                                    if(resValidation.shipsUpgrades(planet,0)){
                                        createShipUpgradesMenu("Done...");
                                    } else {
                                        createShipUpgradesMenu("Need more resources....");
                                    }
                                } else {
                                    createShipUpgradesMenu("message: Engine  upgrade is at maximum....");
                                }
                            }
                            if(index == 7 ) {
                                if(planet.resources.gunsUpgradeLevel < 3) {
                                    if(resValidation.shipsUpgrades(planet,1)){  
                                        createShipUpgradesMenu("Done...");
                                    } else {
                                        createShipUpgradesMenu("Need more resources....");
                                    }
                                } else {
                                    createShipUpgradesMenu("message: Guns  upgrades are at maximum....");
                                }
                            }
                            if(index == 8 ) {
                                if(planet.resources.hullUpgradeLevel < 3) {
                                    if(resValidation.shipsUpgrades(planet,2)){
                                        createShipUpgradesMenu("Done...");
                                    } else {
                                        createShipUpgradesMenu("Need more resources....");
                                    }
                                } else {
                                    createShipUpgradesMenu("message: Hull  upgrade is at maximum....");
                                }
                            }
                            if(index == 9 ) {
                                if(quadData.playerList.get(0).shieldTec) {
                                    if(planet.resources.shieldUpgradeLevel < 3){
                                        if(resValidation.shipsUpgrades(planet,3)){ 
                                            createShipUpgradesMenu("Done...");
                                        } else {
                                            createShipUpgradesMenu("Need more resources....");
                                        }
                                    } else {
                                        createShipUpgradesMenu("message: Shield  upgrade is at maximum....");
                                    }
                                } else {
                                    createShipUpgradesMenu("message: Shield Technologi not reserch....");
                                }
                            }
                        }
                        //_____SELECT_PLANET_FROM _SUN_MENU_______________________________________________________________________________________________//
                        if(spacecomander.SpaceComander.star_select_menu)
                        {
                            planet = serch.serchSunPlanetSelected(menuSelectRay.x, menuSelectRay.y , sun);
                            if(planet != null) {
                                if(planet.owner == spacecomander.SpaceComander.race_index)  {
                                     if(planet.player != null) {
                                         if (planet.player.index == race_index) {
                                             generatePlanetMenu(planet);
                                         }
                                     } else {
                                         generatePlanetMenu(planet);
                                     }
                                }
                            }
                        }
                       //______SELECT__SUN_____________________________________________________________________________________________________________________________________________________________________________________________________________//
                       if(serch.serchSunSelected(curentRay.x, curentRay.y , quadData.sunList) != null && !SpaceComander.ship_select_menu && !spacecomander.SpaceComander.star_select_menu)
                       {
                             sun =  serch.serchSunSelected(curentRay.x, curentRay.y , quadData.sunList);
                             if(sun != null) 
                             {
                                spacecomander.SpaceComander.colony_menu_1 = false;
                                     clearMENU();   
                                     menuData.createSunSelectMenu(camera,sun);
                                     resetBooleanVar();  
                                     spacecomander.SpaceComander.star_select_menu = true;
                             }
                       }            
                       //___SELECT_PLANETS____________________________________________________________________//
                       if(spacecomander.SpaceComander.colony_menu_1)
                       {
                             planet = serch.serchPlanetSelected(curentRay.x, curentRay.y, quadData.sunList);
                             if(planet != null){
                                if(planet.owner == spacecomander.SpaceComander.race_index)
                                {
                                     if(planet.player != null) {
                                         if (planet.player.index == race_index) {
                                             generatePlanetMenu(planet);
                                         }
                                     } else {
                                         generatePlanetMenu(planet);
                                     }
                                }
                             }
                       }
                       //__SELECT_MENU_1____________________________________________________________________//
                         if(spacecomander.SpaceComander.colony_menu_1){
                             generateColoniMenu_2(start_coloni_index , end_coloni_index,true);
                         }
                       //___RESERCH_TEC_____________________________________________________________________//
                         if(spacecomander.SpaceComander.tec_menu){
                             createTecReserchMenu(quadData.playerList.get(0));
                         }
                       //__SELECT_MENU_2____________________________________________________________________//
                         if(spacecomander.SpaceComander.colony_menu_2){
                            int  index = serch.selectMenu_2(menuSelectRay.x, menuSelectRay.y , quadData.playerList.get(0));
                            if(index  > 0 && index < 10)
                            {
                                     clearMENU();   
                                     resetBooleanVar();
                                     planet = quadData.playerList.get(0).watherPList.get((index - 1)+(page*9));
                                     generatePlanetMenu(planet);        
                                     spacecomander.SpaceComander.planet_select_menu = true;
                            } else {
                                     if(index == -1) {
                                         if(start_coloni_index - 9 >=0)    {
                                             start_coloni_index -= 9;
                                             end_coloni_index   -= 9; 
                                             page--;
                                             generateColoniMenu_2(start_coloni_index , end_coloni_index,true);
                                         }
                                     } 
                                     if(index == 0) {
                                         if(quadData.playerList.get(0).watherPList.size() > start_coloni_index + 9)  {
                                             start_coloni_index += 9;
                                             end_coloni_index   += 9;  
                                             page++;
                                             generateColoniMenu_2(start_coloni_index , end_coloni_index,true);
                                         }
                                     }
                                   }
                         }
                       }
                    } else {
                                if(action == GLFW_PRESS) {  
                                    if(SpaceComander.gameSetings_menu) {
                                        int index = serch.selectMainMenu(menuSelectRay.x, menuSelectRay.y);
                                        if(index == 1){
                                           resetBooleanVar();
                                           clearMENU();    
                                           menuData.createGameMainMenu(camera);
                                           SpaceComander.main_menu = true; 
                                           temp_galaxySize = gameSetings.galaxy_size;    
                                           temp_playerCount = gameSetings.numberOfPlayers;
                                           temp_auto_AddShip = gameSetings.auto_addShip;
                                           temp_auto_colonize = gameSetings.auto_colonizePlanet;
                                           temp_auto_deplay = gameSetings.auto_deploySquad;      
                                        }
                                        if(index == 2) {
                                           gameSetings.galaxy_size = temp_galaxySize;
                                           gameSetings.numberOfPlayers = temp_playerCount;
                                           gameSetings.auto_addShip = temp_auto_AddShip;
                                           gameSetings.auto_assign_worker = temp_auto_assignWorker;
                                           gameSetings.auto_colonizePlanet = temp_auto_colonize;
                                           gameSetings.auto_deploySquad = temp_auto_deplay;
                                           resetBooleanVar();
                                           clearMENU();   
                                           menuData.createGameMainMenu(camera);
                                           SpaceComander.main_menu = true;
                                        }
                                        if(index >=3 && index <=12) {
                                           gameSetings.galaxy_size = index-2;
                                           resetBooleanVar();
                                           clearScreen();
                                           clearMENU();   
                                           menuData.createSetingsMenu(camera,true);
                                           SpaceComander.gameSetings_menu = true;
                                        }
                                        if(index >= 13 && index <= 22) {
                                          gameSetings.numberOfPlayers = (index - 12);
                                          resetBooleanVar();
                                          clearScreen();
                                          clearMENU();   
                                          menuData.createSetingsMenu(camera,true);
                                            
                                          SpaceComander.gameSetings_menu = true;     
                                        }
                                        if(index == 23) {
                                          if(gameSetings.auto_colonizePlanet){
                                             gameSetings.auto_colonizePlanet = false;
                                          } else {
                                            gameSetings.auto_colonizePlanet = true;
                                          }

                                          campaign.setupPlayers(quadData.playerList, gameSetings.numberOfPlayers, 1, gameSetings.galaxy_size, race_index,true);
                                          campaign.initMultiplayer(gameSetings.numberOfPlayers,true,false);
                                          quadData.playerList.get(race_index).auto_colonizePlanet = gameSetings.auto_colonizePlanet;
                                          quadData.playerList.get(race_index).auto_assign_worker = gameSetings.auto_assign_worker;
                                          quadData.playerList.get(race_index).auto_deploySquad = gameSetings.auto_deploySquad;
                                          quadData.playerList.get(race_index).auto_addShip = gameSetings.auto_addShip;
                                          resetBooleanVar();
                                          clearScreen();
                                          clearMENU();   
                                          menuData.createSetingsMenu(camera,true);
                                          SpaceComander.gameSetings_menu = true;                                           
                                        }
                                        if(index == 24) {
                                          if(gameSetings.auto_addShip){
                                             gameSetings.auto_addShip = false;
                                          } else {
                                            gameSetings.auto_addShip = true;
                                          }
                                          campaign.setupPlayers(quadData.playerList, gameSetings.numberOfPlayers, 1, gameSetings.galaxy_size, race_index,true);
                                          campaign.initMultiplayer(gameSetings.numberOfPlayers,true,false);
                                          
                                          quadData.playerList.get(race_index).auto_colonizePlanet = gameSetings.auto_colonizePlanet;
                                          resetBooleanVar();
                                          clearScreen();
                                          clearMENU();   
                                          menuData.createSetingsMenu(camera,true);
                                          SpaceComander.gameSetings_menu = true;                                           
                                        }    
                                        if(index == 25) {
                                          if(gameSetings.auto_deploySquad){
                                             gameSetings.auto_deploySquad = false;
                                          } else {
                                            gameSetings.auto_deploySquad = true;
                                          }
                                          campaign.setupPlayers(quadData.playerList, gameSetings.numberOfPlayers, 1, gameSetings.galaxy_size, race_index,true);
                                          campaign.initMultiplayer(gameSetings.numberOfPlayers,true,false);
                                          
                                          quadData.playerList.get(race_index).auto_colonizePlanet = gameSetings.auto_colonizePlanet;
                                          resetBooleanVar();
                                          clearScreen();
                                          clearMENU();   
                                          menuData.createSetingsMenu(camera,true);
                                          SpaceComander.gameSetings_menu = true;                                           
                                        } 
                                        if(index == 26) {
                                          if(gameSetings.auto_assign_worker){
                                             gameSetings.auto_assign_worker = false;
                                          } else {
                                            gameSetings.auto_assign_worker = true;
                                          }
                                          campaign.setupPlayers(quadData.playerList, gameSetings.numberOfPlayers, 1, gameSetings.galaxy_size, race_index,true);
                                          campaign.initMultiplayer(gameSetings.numberOfPlayers,true,false);
                                          
                                          quadData.playerList.get(race_index).auto_colonizePlanet = gameSetings.auto_colonizePlanet;
                                          resetBooleanVar();
                                          clearScreen();
                                          clearMENU();   
                                          menuData.createSetingsMenu(camera,true);
                                          SpaceComander.gameSetings_menu = true;                                        
                                        
                                        }

                                    }
                                    if(SpaceComander.main_menu)
                                    {
                                         int index = serch.selectMainMenu(menuSelectRay.x, menuSelectRay.y);
                                         if(index == 1)
                                         {
                                            SpaceComander.mission_index = 1;
                                            solarSystem.load_from_file = false;  
                                            clearScreen();
                                            resetBooleanVar();
                                            loadCurentMision();
                                            clearMENU();   
                                            SpaceComander.game_pause = false;
                                            game_mode_type = 1;
                                         }                                     
                                         if(index == 2)
                                         {
                                            solarSystem.load_from_file = false;  
                                            clearScreen();
                                            resetBooleanVar();
                                            loadCurentMision();
                                            clearMENU();   
                                            SpaceComander.game_pause = false;
                                         }                                        
                                         if(index == 4)
                                         {   
                                              //_______MULTIPLAYER________________________________________//
                                             galaxyMap.scale.x = (gameSetings.galaxy_size+4)*1.5f;
                                             galaxyMap.scale.y = (gameSetings.galaxy_size+4)*1.5f;
                                             solarSystem.load_from_file = false;  
                                             SpaceComander.mission_index = 0;
                                             startMultiplayerGame(false);
                                             if(quadData.playerList.size() != 0) {
                                                input.setupCameraLocation(quadData.playerList.get(0).coloniShip.curentPosition.x , quadData.playerList.get(0).coloniShip.curentPosition.y,true);
                                             }
                                             game_mode_type = 2;
                                             SpaceComander.mission_index = 0;
                                             SpaceComander.game_pause = false;  
                                         }                                       
                                         if(index == 3)  {
                                             //_______LOAD________________________________________//
                                             solarSystem.load_from_file = true;
                                             galaxyMap.scale.x = (gameSetings.galaxy_size+4)*1.5f;
                                             galaxyMap.scale.y = (gameSetings.galaxy_size+4)*1.5f; 
                                             startMultiplayerGame(true);
                                             if(quadData.playerList.size() != 0) {
                                               if(!solarSystem.load_from_file){
                                                input.setupCameraLocation(quadData.playerList.get(0).coloniShip.curentPosition.x , quadData.playerList.get(0).coloniShip.curentPosition.y,true);
                                               }
                                             }
                                             for(Player play:quadData.playerList) {
                                                 play.colonizationProcess = false;
                                             }
                                             game_mode_type = 2;
                                             SpaceComander.game_pause = false;
                                         }                                      
                                         if(index == 5)   {
                                              System.out.println("Save Gameee_main menu");
                                         }                                     
                                         if(index == 6)    {
                                              clearMENU();   
                                              resetBooleanVar();       
                                              SpaceComander.game_pause = true;
                                              menuData.createSetingsMenu(camera,true);
                                              SpaceComander.gameSetings_menu = true;                                                                
                                         }                                     
                                         if(index == 7)    {
                                              SpaceComander.exit_game = true;
                                         } 
                                    }    
                                    if(SpaceComander.game_over__menu)
                                    {
                                        int index = serch.selectMainMenu(menuSelectRay.x, menuSelectRay.y);
                                        if(index == 2) {
                                            if(game_mode_type == 1)
                                            {
                                                clearScreen();
                                                resetBooleanVar();
                                                loadCurentMision();
                                                clearMENU();   
                                                SpaceComander.game_pause = false;
                                            } else {
                                                clearScreen();
                                                resetBooleanVar();   
                                               // startMultiplayerGame(false);
                                                input.setupCameraLocation(quadData.playerList.get(0).coloniShip.curentPosition.x , quadData.playerList.get(0).coloniShip.curentPosition.y,true);
                                            }
                                        }
                                        if(index == 1)    {
                                            clearScreen();
                                            resetBooleanVar();
                                            generateMainMenu();
                                        }
                                    }

                                    /*
                                    if(SpaceComander.victory_menu)
                                    {
                                        int index = serch.selectMainMenu(menuSelectRay.x, menuSelectRay.y);
                                        if(index == 1)
                                        {
                                            clearScreen();
                                            resetBooleanVar();
                                            loadCurentMision();
                                            menuData.menuPoliList.clear();
                                            quadData.textToRender.clear();
                                            SpaceComander.game_pause = false;      
                                        }
                                        if(index == 2) {
                                            clearScreen();
                                            resetBooleanVar();
                                            SpaceComander.mission_index++;
                                            loadCurentMision();
                                            menuData.menuPoliList.clear();
                                            quadData.textToRender.clear();
                                            SpaceComander.game_pause = false; 
                                        }
                                        if(index == 3) {
                                            clearScreen();
                                            resetBooleanVar();
                                            generateMainMenu();
                                        }
                                    }
                                    */
                                    if(SpaceComander.game_pause &&  !SpaceComander.game_pause_setings_menu && !SpaceComander.gameSetings_menu)
                                    {
                                       int index = serch.selectMainMenu(menuSelectRay.x, menuSelectRay.y);
                                       
                                       if(index == 5)
                                       { 
                                           try {
                                               //_____SAVE________________________________________//
                                               clearFile("solarSystem.txt");
                                               saveGame();
                                               clearMENU();   
                                               resetBooleanVar();  
                                               SpaceComander.game_pause = false;
                                           } catch (IOException ex) {}
                                       }
                                       if(index == 6)
                                       {  
                                              temp_auto_colonize = gameSetings.auto_colonizePlanet; 
                                              temp_auto_deplay = gameSetings.auto_deploySquad;
                                              temp_auto_AddShip = gameSetings.auto_addShip;
                                              temp_auto_assignWorker = gameSetings.auto_assign_worker;
                                              
                                              clearMENU();   
                                              resetBooleanVar();       
                                              SpaceComander.game_pause = true;
                                              menuData.createSetingsMenu(camera,false);
                                              SpaceComander.game_pause_setings_menu = true;                                                 
                                       }
                                       if(index == 7){                              
                                            clearScreen();
                                            resetBooleanVar();
                                            generateMainMenu();
                                       }
                                    }
                                    if(SpaceComander.game_pause_setings_menu)
                                    {
                                      int index = serch.selectMainMenu(menuSelectRay.x, menuSelectRay.y);
                                      if(index == 1){
                                            clearMENU();   
                                            resetBooleanVar();  
                                            SpaceComander.game_pause = false;
                                      }
                                       
                                      if(index == 2){
                                            gameSetings.auto_colonizePlanet =  temp_auto_colonize ; 
                                            gameSetings.auto_deploySquad = temp_auto_deplay ;
                                            gameSetings.auto_addShip = temp_auto_AddShip ;
                                            gameSetings.auto_assign_worker = temp_auto_assignWorker;                                                                                   
                                            quadData.playerList.get(race_index).auto_colonizePlanet = temp_auto_colonize;
                                            quadData.playerList.get(race_index).auto_addShip = temp_auto_AddShip;
                                            quadData.playerList.get(race_index).auto_assign_worker = temp_auto_assignWorker;
                                            quadData.playerList.get(race_index).auto_deploySquad = temp_auto_deplay;
                                            clearMENU();   
                                            resetBooleanVar();  
                                            SpaceComander.game_pause = false;                                           
                                      }
                                        if(index == 23) {
                                          if(gameSetings.auto_colonizePlanet){
                                             gameSetings.auto_colonizePlanet = false;
                                          } else {
                                            gameSetings.auto_colonizePlanet = true;
                                          }
                                          quadData.playerList.get(race_index).auto_colonizePlanet = gameSetings.auto_colonizePlanet;
                                          resetBooleanVar();
                                          clearMENU();   
                                          menuData.createSetingsMenu(camera,false);
                                          SpaceComander.game_pause_setings_menu = true;                                           
                                        }
                                        if(index == 24) {
                                          if(gameSetings.auto_addShip){
                                             gameSetings.auto_addShip = false;
                                          } else {
                                            gameSetings.auto_addShip = true;
                                          }
                                          quadData.playerList.get(race_index).auto_addShip = gameSetings.auto_addShip;
                                          quadData.playerList.get(race_index).auto_colonizePlanet = gameSetings.auto_colonizePlanet;
                                          resetBooleanVar();
                                          clearMENU();   
                                          menuData.createSetingsMenu(camera,false);
                                          SpaceComander.game_pause_setings_menu = true;                                         
                                        }    
                                        if(index == 25) {
                                          if(gameSetings.auto_deploySquad){
                                             gameSetings.auto_deploySquad = false;
                                          } else {
                                            gameSetings.auto_deploySquad = true;
                                          }
                                          quadData.playerList.get(race_index).auto_deploySquad = gameSetings.auto_deploySquad;
                                          resetBooleanVar();
                                          clearMENU();   
                                          menuData.createSetingsMenu(camera,false);
                                          SpaceComander.game_pause_setings_menu = true;                                       
                                        } 
                                        if(index == 26) {
                                          if(gameSetings.auto_assign_worker){
                                             gameSetings.auto_assign_worker = false;
                                          } else {
                                            gameSetings.auto_assign_worker = true;
                                          }
                                          quadData.playerList.get(race_index).auto_assign_worker = gameSetings.auto_assign_worker;
                                          resetBooleanVar();
                                          clearMENU();   
                                          menuData.createSetingsMenu(camera,false);
                                          SpaceComander.game_pause_setings_menu = true;                                        
                                        
                                        }                                      
                                    }                                    
                                }
                           }
                   
                    if(!SpaceComander.game_pause)
                    {
                            if(action == GLFW_RELEASE){

                               curentRay =  pick.update(false , 0 , 0);
                               x_drag_end = curentRay.x;
                               y_drag_end = curentRay.y;
                               SpaceComander.selectQuadStatus = false;

                               if(Math.abs(x_drag_start - x_drag_end) > 0.01f && Math.abs(y_drag_start - y_drag_end) > 0.01f) {

                                   if (enabledragSelection)
                                   {
                                       quadUtil.getDragAndDropSelectedQuads(quadData.playerList, x_drag_start, y_drag_start, x_drag_end, y_drag_end);
                                       if (quadUtil.selected_ship_count != 0)
                                       {
                                           enabledragSelection = false;
                                           shipSelectCountPrint();
                                           SpaceComander.ship_select_menu = true;
                                           SpaceComander.shipSelectMenuText = true;
                                       }
                                   }
                               }
                            }
                    }
                }
 
                 if(button == GLFW_MOUSE_BUTTON_2) {
                    if(!SpaceComander.game_pause)   
                    {
                        if(action == GLFW_PRESS) {
                          curentRay = pick.update(false , 0 , 0);
                          quadUtil.translateSelectedShips(quadData.playerList, curentRay.x, curentRay.y);
                          if(!SpaceComander.ship_select_menu){
                             clearMENU();   
                             resetBooleanVar();
                             enableColoniMenu_1();
                          }
                          sun = null;
                          planet = null;
                          page = 0;
                          start_coloni_index = 0;
                          end_coloni_index = 9;             
                        }
                        
                    } else {
                               if(action == GLFW_PRESS) {}
                           }
                    if(action == GLFW_RELEASE){}
                 }    
    }
     public void clearFile(String file) throws IOException {
        FileWriter writer  = null;
        BufferedWriter out = null;
        writer = new FileWriter(file, false);  
        out = new BufferedWriter(writer);  
        out.write(""); 
        out.close();
     }     
    public void saveGame() throws IOException {
        FileWriter writer  = null;
        BufferedWriter out = null;
        writer = new FileWriter("solarSystem.txt", true);            
        out = new BufferedWriter(writer);     
          out.write(String.valueOf(SpaceComander.mission_index)+" ");
          out.write(String.valueOf(gameSetings.numberOfPlayers)+" ");
          out.write(String.valueOf(solarSystem.minRadius)+" ");
          out.write(String.valueOf(solarSystem.maxRadius)+" ");
          out.write(String.valueOf(quadData.planetList.size())+" ");
          out.write(String.valueOf(quadData.sunList.size())+" ");
          out.newLine();
          for(Sun sun:quadData.sunList) {
                out.write("sun ");
                out.write(sun.saved_position.x+" ");
                out.write(sun.saved_position.y+" ");
                out.write(sun.model.scale.x+" ");
                out.write(sun.radius+" ");
                out.write(sun.listaPlanete.size()+" ");
                out.write(sun.playerIndex+" ");
                out.newLine();
                for(Planet planet:sun.listaPlanete){
                    out.write("planet ");
                    out.write(planet.model.scale.x+" ");
                    out.write(planet.angleOrbit+" ");
                    out.write(planet.starDistance+" ");
                    out.write(planet.position.x+" ");
                    out.write(planet.position.y+" ");
                    out.write(planet.position.z+" ");
                    out.write(planet.starDistance+" ");
                    out.write(planet.starSpacer+" ");
                    out.write(planet.startPP+" ");
                    out.write(planet.planetsSpacerr+" ");
                    out.write(planet.model.INDEX_T+" ");
                    out.write(planet.owner+" ");                    
                    out.write(planet.resources.energyMined+" ");
                    out.write(planet.resources.metalOreMined+" ");
                    out.write(planet.resources.mineralsMined+" ");
                    out.write(planet.resources.carbonMined+" ");
                    out.write(planet.resources.gasMined+" ");
                    out.write(planet.resources.creditMined+" ");                   
                    out.write(planet.resources.energyLevel+" ");
                    out.write(planet.resources.metalOreLevel+" ");
                    out.write(planet.resources.mineralsLevel+" ");
                    out.write(planet.resources.carbonLevel+" ");
                    out.write(planet.resources.gasLevel+" ");
                    out.write(planet.resources.creditLevel+" ");                   
                    out.write(planet.resources.engineUpgradeLevel+" ");
                    out.write(planet.resources.gunsUpgradeLevel+" ");
                    out.write(planet.resources.hullUpgradeLevel+" ");
                     out.write(planet.resources.shieldUpgradeLevel+" ");
                    
                    out.newLine();
                }
          }      
        out.close();
        
        clearFile("otherData.txt");
        writer = new FileWriter("otherData.txt", true);            
        out = new BufferedWriter(writer);        
        for(int i=0 ; i<quadData.playerList.size() ; i++) {
            for(int j=0 ; j<quadData.playerList.get(i).shipList.size() ; j++) {
                if(!quadData.playerList.get(i).shipList.get(j).isCargoShip &&  !quadData.playerList.get(i).shipList.get(j).isColoniShip  &&  !quadData.playerList.get(i).shipList.get(j).isInterceptor){                    
                    out.write(quadData.playerList.get(i).index+" ");   //___0__//
                    out.write(quadData.playerList.get(i).shipList.get(j).deployPlanet.index+" ");  //___1__//
                    out.write(String.valueOf(quadData.playerList.get(i).shipList.get(j).curentPosition.x)+" ");    //___2__//
                    out.write(String.valueOf(quadData.playerList.get(i).shipList.get(j).curentPosition.y)+" ");   //___3__//                       
                    out.write(String.valueOf(quadData.playerList.get(i).shipList.get(j).isCarrierShip)+" ");     //___4__//
                    out.write(String.valueOf(quadData.playerList.get(i).shipList.get(j).isDestroierShip)+" ");     //___5__//
                    out.write(String.valueOf(quadData.playerList.get(i).shipList.get(j).isExploreShip)+" ");     //___6__//
                    out.write(String.valueOf(quadData.playerList.get(i).shipList.get(j).isFreagateShip)+" ");     //__7__//
                    out.write(String.valueOf(quadData.playerList.get(i).shipList.get(j).isMotherShip)+" ");     //___8__//                    
                    out.write(String.valueOf(quadData.playerList.get(i).shipList.get(j).endPosition.x)+" ");     //___9__//
                    out.write(String.valueOf(quadData.playerList.get(i).shipList.get(j).endPosition.y)+" ");     //___10__//
                    out.write(String.valueOf(quadData.playerList.get(i).shipList.get(j).ship_is_moving)+" ");     //___11__//
                    out.write(String.valueOf(quadData.playerList.get(i).shipList.get(j).shipIsInWarp)+" ");     //___12__//
                    out.write(String.valueOf(quadData.playerList.get(i).shipList.get(j).life)+" ");     //___13__//
                    out.write(String.valueOf(quadData.playerList.get(i).shipList.get(j).shield)+" ");     //___14__//                    
                    out.newLine();
                }
            }        
        } 
        out.close();
    }
    public void game_pause_menu(){
        clearMENU();   
        resetBooleanVar();
        menuData.create_game_pause_Menu(camera);
    }
    public void shipSelectCountPrint(){
        clearMENU();   
        resetBooleanVar();
        quadUtil.getSelectedShips(quadData.playerList);
        if(quadUtil.selected_ship_count != 0)
        {
            menuData.createShipSelectMenu(camera, quadData.playerList.get(race_index));        
            ship_select_delta = 0;
            if (quadUtil.coloni_ship_count != 0) {
                stringB.setLength(0);
                stringB.append(quadUtil.coloni_ship_count);
                quadData.textToRender.add(new TextData(camera , stringB.toString(),  - 0.072f + ship_select_delta,  - 0.081f,  - 0.15f, ibo, textureUtil.textChart_ON, false, 0,display,true));
                ship_select_delta += 0.015f;
            }
            if (quadUtil.explorer_ship_count != 0) {
                stringB.setLength(0);
                stringB.append(quadUtil.explorer_ship_count);
                quadData.textToRender.add(new TextData(camera , stringB.toString(),  - 0.072f + ship_select_delta,  - 0.081f,  - 0.15f, ibo, textureUtil.textChart_ON, false, 0,display,true));
                ship_select_delta += 0.015f;
            }
            if (quadUtil.fregate_ship_count != 0) {
                stringB.setLength(0);
                stringB.append(quadUtil.fregate_ship_count);
                quadData.textToRender.add(new TextData(camera , stringB.toString(),  - 0.072f + ship_select_delta,  - 0.081f,  - 0.15f, ibo, textureUtil.textChart_ON, false, 0, display,true));
                ship_select_delta += 0.015f;
            }
            if (quadUtil.destroier_ship_count != 0) {
                stringB.setLength(0);
                stringB.append(quadUtil.destroier_ship_count);
                quadData.textToRender.add(new TextData(camera , stringB.toString(),  - 0.072f + ship_select_delta,  - 0.081f,  - 0.15f, ibo, textureUtil.textChart_ON, false, 0,display,true));
                ship_select_delta += 0.015f;
            }
            if (quadUtil.carrier_ship_count != 0) {
                stringB.setLength(0);
                stringB.append(quadUtil.carrier_ship_count);
                quadData.textToRender.add(new TextData(camera , stringB.toString(),  - 0.072f + ship_select_delta,  - 0.081f,  - 0.15f, ibo, textureUtil.textChart_ON, false, 0,display,true));
                ship_select_delta += 0.015f;
            }
            if (quadUtil.interceptor_ship_count != 0) {
                stringB.setLength(0);
                stringB.append(quadUtil.interceptor_ship_count);
                quadData.textToRender.add(new TextData(camera , stringB.toString(),  - 0.072f + ship_select_delta,  - 0.081f,  - 0.15f, ibo, textureUtil.textChart_ON, false, 0,display,true));
                ship_select_delta += 0.015f;
            }
            if (quadUtil.mother_ship_count != 0) {
                stringB.setLength(0);
                stringB.append(quadUtil.mother_ship_count);
                quadData.textToRender.add(new TextData(camera , stringB.toString(),  - 0.072f + ship_select_delta,  - 0.081f,  - 0.15f, ibo, textureUtil.textChart_ON, false, 0,display,true));
                ship_select_delta += 0.015f;
            }
           SpaceComander.ship_select_menu = true;
           SpaceComander.shipSelectMenuText = true;
        }
    }

    public void createTecReserchMenu(Player player)  {
        resetBooleanVar();        
        quadData.textToRender.clear();
        int  data = serch.selectMenu_1(menuSelectRay.x, menuSelectRay.y);
        if(data == 1) {
          player.laserTec = true;
        }
        if(data == 2) {
          player.torpedoTec = true;
        }
        if(data == 3) {
          player.shieldTec = true;
          for(Quad ship : player.shipList){
                ship.shield = 100;
          }
        }       
        if(data == 4) {
          player.warpDriveTec = true; 
          for(Quad ship : player.shipList){
              ship.player.warpDriveTec = true;
          }
        }
        if(data == 5) {
          player.InterceptorTec = true;
        }           
        menuData.menuPoliList.clear();
        menuData.createTecMenu(camera , quadData.playerList.get(0));
        spacecomander.SpaceComander.tec_menu = true;
    }

    public void loadCurentMision(){
        if(SpaceComander.mission_index == 1) {
            gameSetings.galaxy_size = 7;
            galaxyMap.scale.x = (gameSetings.galaxy_size+4)*1.5f;
            galaxyMap.scale.y = (gameSetings.galaxy_size+4)*1.5f;
            campaign.setupPlayers(quadData.playerList, 2, 2, 10, race_index,false);
            generatePlayersStartPosition();
            campaign.initMultiplayer(2, false,false);
            try {       
                solarSystem.generateMission_1Map();
            } catch (IOException ex) {}
            quadData.playerList.get(0).enemy[quadData.playerList.get(1).index] = true;
            quadData.playerList.get(1).enemy[quadData.playerList.get(0).index] = true;
        }
        if(SpaceComander.mission_index == 2) {
            gameSetings.galaxy_size = 7;
            galaxyMap.scale.x = (gameSetings.galaxy_size+4)*1.5f;
            galaxyMap.scale.y = (gameSetings.galaxy_size+4)*1.5f;
            campaign.setupPlayers(quadData.playerList, 2, 2, 10, race_index,false);
            generatePlayersStartPosition();
            campaign.initMultiplayer(2, false,false);
            try {
                solarSystem.generateMission_2Map();
            } catch (IOException ex) {}
            quadData.playerList.get(0).enemy[quadData.playerList.get(1).index] = true;
            quadData.playerList.get(1).enemy[quadData.playerList.get(0).index] = true;            
        }            
    }
    public void loadFGameParam() {
       try {
           Scanner   scan = new Scanner(new File("solarSystem.txt"));
           String[] param = scan.nextLine().split(" ");
           SpaceComander.mission_index = Integer.valueOf(param[0]);
           gameSetings.numberOfPlayers = Integer.valueOf(param[1]);
       } catch (FileNotFoundException ex) {}
    }
    public void startMultiplayerGame(boolean loadFromFile){
        if(solarSystem.load_from_file) {
            loadFGameParam();
        }
        if(SpaceComander.mission_index == 0) {
            campaign.setupPlayers(quadData.playerList, gameSetings.numberOfPlayers, 2, 10, race_index,false);
            campaign.initMultiplayer(gameSetings.numberOfPlayers,true,loadFromFile);    
            SpaceComander.game_pause = false;
                  try {    
                     solarSystem.generateMultiplayerMap(1, 10);
                  } catch (IOException ex) {}
        } else {
                if(SpaceComander.mission_index == 1) {
                       gameSetings.galaxy_size = 7;
                       galaxyMap.scale.x = (gameSetings.galaxy_size+4)*1.5f;
                       galaxyMap.scale.y = (gameSetings.galaxy_size+4)*1.5f;
                       campaign.setupPlayers(quadData.playerList, 2, 2, 10, race_index,false);
                       campaign.initMultiplayer(2, false,false);
                       try {    
                          solarSystem.generateMultiplayerMap(1, 10);
                       } catch (IOException ex) {}                       
                        quadData.playerList.get(0).enemy[quadData.playerList.get(1).index] = true;
                        quadData.playerList.get(1).enemy[quadData.playerList.get(0).index] = true;                       
                   }
                   if(SpaceComander.mission_index == 2) {
                       gameSetings.galaxy_size = 7;
                       galaxyMap.scale.x = (gameSetings.galaxy_size+4)*1.5f;
                       galaxyMap.scale.y = (gameSetings.galaxy_size+4)*1.5f;
                       campaign.setupPlayers(quadData.playerList, 2, 2, 10, race_index,false);
                       campaign.initMultiplayer(2, false,false);
                        try {    
                          solarSystem.generateMultiplayerMap(1, 10);
                        } catch (IOException ex) {}                       
                        quadData.playerList.get(0).enemy[quadData.playerList.get(1).index] = true;
                        quadData.playerList.get(1).enemy[quadData.playerList.get(0).index] = true;                       
                   }                    
        }
        clearMENU();   
        resetBooleanVar();    
    }
    public void generateMainMenu(){
        clearMENU();   
        resetBooleanVar();       
        SpaceComander.game_pause = true;
        SpaceComander.main_menu = true;        
        menuData.createGameMainMenu(camera);   
    }   
    public void generateVictoryMenu(){
          clearMENU();   
          resetBooleanVar();
          quadUtil.selected_ship_count = 0;
          SpaceComander.game_pause = true;
          SpaceComander.victory_menu = true;          
          menuData.createVictoryMenu(camera);         
    }
    public void generateGameOverMenu(){
          clearMENU();   
          menuData.createGameOverMenu(camera);
          resetBooleanVar();
          quadUtil.selected_ship_count = 0;
          SpaceComander.game_pause = true;
          SpaceComander.game_over__menu = true;          
    }    
    public void createShipUpgradesMenu(String message)   {
        resetBooleanVar();        
        clearMENU();   
        menuData.createShipUpgradesMenu(camera, planet,message);                   
        spacecomander.SpaceComander.upgrades_menu = true;
    }
    public void createCargoBayMenu(Planet planet , String message)  {
        resetBooleanVar();
        clearMENU();   
        menuData.createCargoBayMenu(camera , planet,message);       
        SpaceComander.cargoBay_Menu = true;
    }
    public void createShipYardMenu(String message)
    {
        resetBooleanVar();        
        clearMENU();   
        menuData.createShipYardMenu(camera, planet,message); 
        SpaceComander.shipYard_menu = true;
    }
    public void createBuildingsMenu(String message) {
        resetBooleanVar();        
        clearMENU();   
        menuData.createBuildingsMenu(camera, planet,message);                  
        spacecomander.SpaceComander.buildings_menu = true;
    }
    public void generateColoniMenu_2(int start , int end,boolean bool)    {
                   if(bool){
                        int  data = serch.selectMenu_2(menuSelectRay.x, menuSelectRay.y);
                        if(data == 0 || data == 8 || data == 9)
                        {
                                 resetBooleanVar();
                                 clearMENU();                       
                                 menuData.createColonyMenu_2(start , end ,camera , quadData.playerList.get(0),null);
                                 if(quadData.playerList.get(0).watherPList.size() > end)  {
                                                coloni_delta = 0;
                                     for(int i=start ; i < end ; i++) {
                                                quadData.textToRender.add(new TextData(camera , String.valueOf(quadData.playerList.get(0).watherPList.get(i).resources.energy),   -0.0475f , + 0.06f - coloni_delta,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                                                quadData.textToRender.add(new TextData(camera , String.valueOf(quadData.playerList.get(0).watherPList.get(i).resources.metalOre), -0.03f ,   + 0.06f - coloni_delta,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                                                quadData.textToRender.add(new TextData(camera , String.valueOf(quadData.playerList.get(0).watherPList.get(i).resources.minerals), -0.0120f , + 0.06f - coloni_delta,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                                                quadData.textToRender.add(new TextData(camera , String.valueOf(quadData.playerList.get(0).watherPList.get(i).resources.carbon),   +0.006f ,  + 0.06f - coloni_delta,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                                                quadData.textToRender.add(new TextData(camera , String.valueOf(quadData.playerList.get(0).watherPList.get(i).resources.gas),      +0.024f ,  + 0.06f - coloni_delta,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                                                quadData.textToRender.add(new TextData(camera , String.valueOf(quadData.playerList.get(0).watherPList.get(i).resources.biomas),  + 0.042f ,  + 0.06f - coloni_delta,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                                                quadData.textToRender.add(new TextData(camera , String.valueOf(quadData.playerList.get(0).watherPList.get(i).resources.maxPopulation+" M"),  + 0.06f ,  + 0.06f - coloni_delta,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                                                quadData.textToRender.add(new TextData(camera , String.valueOf(page+1),  - 0.0f ,  - 0.068f ,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                                                coloni_delta += 0.015f;   
                                     } 
                                 } else {                             
                                             coloni_delta = 0;
                                             for(int i=start ; i < quadData.playerList.get(0).watherPList.size() ; i++) {
                                                quadData.textToRender.add(new TextData(camera , String.valueOf(quadData.playerList.get(0).watherPList.get(i).resources.energy),  - 0.0475f , + 0.06f - coloni_delta,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                                                quadData.textToRender.add(new TextData(camera , String.valueOf(quadData.playerList.get(0).watherPList.get(i).resources.metalOre), - 0.03f ,  + 0.06f - coloni_delta,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                                                quadData.textToRender.add(new TextData(camera , String.valueOf(quadData.playerList.get(0).watherPList.get(i).resources.minerals), - 0.0120f , + 0.06f - coloni_delta,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                                                quadData.textToRender.add(new TextData(camera , String.valueOf(quadData.playerList.get(0).watherPList.get(i).resources.carbon),  + 0.006f ,  + 0.06f - coloni_delta,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                                                quadData.textToRender.add(new TextData(camera , String.valueOf(quadData.playerList.get(0).watherPList.get(i).resources.gas),  + 0.024f , + 0.06f - coloni_delta,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                                                quadData.textToRender.add(new TextData(camera , String.valueOf(quadData.playerList.get(0).watherPList.get(i).resources.biomas),  + 0.042f ,  + 0.06f - coloni_delta,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                                                quadData.textToRender.add(new TextData(camera , String.valueOf(quadData.playerList.get(0).watherPList.get(i).resources.maxPopulation+" M"),  + 0.06f ,  + 0.06f - coloni_delta,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                                                quadData.textToRender.add(new TextData(camera , String.valueOf(page+1), 0.0f ,  - 0.068f ,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));                                                                                          
                                                coloni_delta += 0.015f;
                                             }                                       
                                         }

                            spacecomander.SpaceComander.colony_menu_2 = true;
                        }
                        if(data == 1)  {
                                 resetBooleanVar();
                                 clearMENU();
                                 menuData.createTecMenu(camera , quadData.playerList.get(0));                      
                                 spacecomander.SpaceComander.tec_menu = true;
                        }
                   } else {
                                 resetBooleanVar();
                                 clearMENU();                    
                                 menuData.createColonyMenu_2(start , end ,camera , quadData.playerList.get(0),planet);  
                                 quadData.textToRender.add(new TextData(camera , String.valueOf(planet.resources.energy),  - 0.0475f , + 0.06f ,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                                 quadData.textToRender.add(new TextData(camera , String.valueOf(planet.resources.metalOre), - 0.03f ,  + 0.06f ,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                                 quadData.textToRender.add(new TextData(camera , String.valueOf(planet.resources.minerals), - 0.0120f , + 0.06f ,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                                 quadData.textToRender.add(new TextData(camera , String.valueOf(planet.resources.carbon),  + 0.006f ,  + 0.06f ,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                                 quadData.textToRender.add(new TextData(camera , String.valueOf(planet.resources.gas),  + 0.024f , + 0.06f ,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                                 quadData.textToRender.add(new TextData(camera , String.valueOf(planet.resources.biomas),  + 0.042f ,  + 0.06f ,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));
                                 quadData.textToRender.add(new TextData(camera , String.valueOf(planet.resources.maxPopulation+" M"),  + 0.06f ,  + 0.06f ,  - 0.15f  , ibo , textureUtil. textChart_ON , false,0,display,true));                                                                                                                                       
                          }
    }
    public void generatePlanetMenu(Planet planet) {
            resetBooleanVar();             
            clearMENU();
            menuData.createPlanetSelectMenu(camera, planet);
            spacecomander.SpaceComander.planet_select_menu = true;
    }
    public void clearMENU(){
            quadData.textToRender.clear();
            menuData.menuPoliList.clear();     
    }
public void clearScreen()  {
        quadData.bulletList.clear();
        quadData.phaserList.clear();
        quadData.phaserEmiter.clear();
        quadData.shieldList.clear();
        quadData.explosionList.clear();
        quadData.shipSelectionRing.clear();
        for(Player player : quadData.playerList){
            player.bordersList.clear();
            player.cPlanetList.clear();
            player.cStarList.clear();
            player.watherPList.clear();
            player.shipList.clear();
        }
        quadData.sunList.clear();
        quadData.planetList.clear();
        quadData.playerList.clear();
        quadData.colonizedInfluenceRing.clear();   
}     
    public void resetBooleanVar()   {       
        SpaceComander.star_select_menu = false;
        SpaceComander.planet_select_menu = false;
        SpaceComander.colony_menu_1 = false;
        SpaceComander.colony_menu_2 = false;
        SpaceComander.buildings_menu = false;
        SpaceComander.shipYard_menu = false;
        SpaceComander.upgrades_menu = false;
        SpaceComander.tec_menu = false;
        SpaceComander.gameSetings_menu = false;
        SpaceComander.shipSelectMenuText = false;
        SpaceComander.cargoBay_Menu = false;
        SpaceComander.main_menu = false;
        SpaceComander.game_over__menu = false;
        SpaceComander.victory_menu = false;
        SpaceComander.game_pause_setings_menu = false;  
    }
    public void enableColoniMenu_1() {
      menuData.createColonyMenu_1(camera);  
      spacecomander.SpaceComander.colony_menu_1 = true;        
    }
    public void unproject(float x , float y , float z)  {
        Vector3f pos =  new Vector3f();
        Matrix4f mat = new Matrix4f();
        Matrix4f test = new Matrix4f();        
        renderer.projectionMatrix.mul(renderer.viewMatrix,mat);
        mat.unproject(x, y, z,  new int[] { 0, 0, 900, 900 },pos);
    }
    public void project(float x , float y , float z){
        Vector3f pos =  new Vector3f();
        Matrix4f mat = new Matrix4f();
        renderer.projectionMatrix.mul(renderer.viewMatrix,mat);
        mat.project(x, y, z,  new int[] {0,0,1000,1000},pos);
    }
    public void generatePlayersStartPosition(){
       if(SpaceComander.mission_index  == 1) {
            quadData.playerList.get(0).start_position_x = 1;
            quadData.playerList.get(0).start_position_y = 1;
            quadData.playerList.get(1).start_position_x = -1;
            quadData.playerList.get(1).start_position_y = 1;          
       }
       if(SpaceComander.mission_index  == 2) {
            quadData.playerList.get(0).start_position_x = 1;
            quadData.playerList.get(0).start_position_y = 1;
            quadData.playerList.get(1).start_position_x = -1;
            quadData.playerList.get(1).start_position_y = 1;          
       }       
    }
}
