package spacecomander;

import AILogic.AILogic;
import AILogic.AIUtil;
import AILogic.ResValidation;
import Border.Board;
import Camera.Camera;
import Campaign.Campaign;
import Campaign.Color;
import Campaign.GameSetings;
import Campaign.GameStatusHandeling;
import Display.SetupDisplay_LWJGL_3;
import IBO.*;
import Input.Input;
import Input.KeyCallBack;
import Input.MouseClickCallback;
import Input.ScrollCallback;
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
import Util.MousePicker;
import audio.AudioMaster;
import audio.Source;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.opengl.GL11;
import shaders.ShaderProgram;
import FPS_util.SyncTimer;
import GameSaveLoad.Save;

public class SpaceComander {

    public static boolean colonyShipControlKey = false;
    
    float bulletdeleteRange  = 1;
    public static boolean enable_map = true;    
    public static float volume  = 4f;    
    
//___MENU_CONTROL_____________________________________________________//
   public static boolean  star_select_menu   = true;
   public static boolean  planet_select_menu = false;   
   public static boolean  colony_menu_1 = true;  
   public static boolean  colony_menu_2 = false;
   public static boolean  buildings_menu = false; 
   public static boolean  shipYard_menu = false;
   public static boolean  cargoBay_Menu = false;
   public static boolean  upgrades_menu = false;   
   public static boolean  tec_menu = false;
   public static boolean  ship_select_menu = false;
   public static boolean  main_menu = true;
   public static boolean  game_over__menu = true;
   public static boolean  victory_menu = true;
   public static boolean  gameSetings_menu = false;
   public static boolean  game_pause = false;
   public static boolean  game_pause_setings_menu = false;
//___TEXTURE_CONTROL____________________________________________________________________//
   public static boolean shipSelectMenuText = false;

    boolean singleDraw = true;
    boolean game_graphics_mod = true;
    public boolean enableGame = true;
    public static boolean exit_game = false;
    public static int mission_index = 1;

    public static int  race_index = 0;
    public boolean fullScreen = false;
    public float scaleQuad = 1f;
    public int minRadius = 1;
      
//______FPS___________________________________________________________//
    private double start = System.nanoTime() / 1000000000;
    private int valoare = 0;
    private double end;
//____________________________________________________________________// 
    
    QuadData  quadData , permanent_quadData;
    
    Color color;
    public MenuData menuData;
    PrimitiveColor pColor;
    MousePicker pick;
    public Model shipM;
    IBO_Data quade_IBO_Data , triangle_IBO_Data ;
    IBO      quad_ibo , triangle_ibo;
    TextureUtil textureUtil;
    SolarSystem solarSystem;
    Input  input;
    QuadUtil quadUtil;
    GameStatusHandeling  gameStatusHandeling;
    Campaign campaign;
    AILogic aILogic;
    ResValidation resValidation;
    AIUtil  aIUtil;
    QuadsManagement  quadManagement;
    public SetupDisplay_LWJGL_3   display;
    
    Maths maths ;
    
    Camera        camera;
    ShaderProgram shaderProgram;
    Renderer      renderer;
    Model galaxy , mapM;   
    Board bord;
    
    public static boolean selectQuadStatus = false;
    public static float selectQuadX  = 0 , selectQuadY  = 0; 
    public static float selectWidth  = 0f , selectHeight = 0f;

   double xPos,yPos;
   Vector3f curentRay;   
   DoubleBuffer   xBuffer , yBuffer;
   public static float selectEdgeWidth = 0.001f;

    public static ByteBuffer pixel = BufferUtils.createByteBuffer(4);    
    public static boolean enablePixelRead = false;
    public Vector3f rgb = new Vector3f();
    
//___________NEW_CODE______________________________________________________________________________//
    public  GLFWScrollCallback scrollCallback;
    public  GLFWKeyCallback    keyCallback;
    public  GLFWMouseButtonCallback mouseButtonCallback;
    public  GLFWCursorPosCallback mousePos;
    ScrollCallback scroll;
    KeyCallBack keyboard;
    public MouseClickCallback   mouseClick;
//_________________________________________________________________________________________________//  
    
    SyncTimer timer  = new SyncTimer(SyncTimer.LWJGL_GLFW);
    GameSetings  gameSetings  =new GameSetings();
    Save  saveG ;
    public static void main(String[] args) throws IOException, Exception {     
        SpaceComander   sComander  =  new  SpaceComander();
                        sComander.initDisplay();
                        sComander.initGameObjects();
                        sComander.confirmWindowSize();
    }
    public void confirmWindowSize() throws Exception{
         if(display.squareEdge >= 650 && display.squareEdge <= 1000) {
            loop();
         } 
    }
    public void initDisplay(){
        display = new SetupDisplay_LWJGL_3();
        display.initWindow();
    }
    public void initGameObjects() throws IOException 
    {
        color = new Color();
        pColor = new PrimitiveColor();
        quadData = new QuadData();
        permanent_quadData = new QuadData();
        textureUtil = new TextureUtil();
        textureUtil.loadTextures();
        quade_IBO_Data = new IBO_Data(new Vector3f(0, 0, 0), 1f, 1f, 0);
        triangle_IBO_Data = new IBO_Data(new Vector3f(0, 0, 0), 1f, 1f, 1);
        quad_ibo = new IBO(quade_IBO_Data);
        triangle_ibo = new IBO(triangle_IBO_Data);
        quadManagement = new QuadsManagement( quad_ibo, triangle_ibo, textureUtil, scaleQuad, race_index,quadData);
        campaign = new Campaign(quadManagement, quadData.playerList,gameSetings,color);
        camera = new Camera(new Vector3f(0, 0, 8.5f), 0, 0, 0);
        maths = new Maths(quadData.playerList, camera);
        mapM = new Model(quad_ibo, textureUtil.mapTex, 0, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(4.9f, 4.9f, 4.9f));
        galaxy = new Model(quad_ibo, textureUtil.galaxyTex, 0, new Vector3f(0, 0, 0.00001f), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        aIUtil = new AIUtil();
        bord = new Board(quadManagement, maths, quadData);
        solarSystem = new SolarSystem(quad_ibo, textureUtil.solarSystemTex_max, maths,bord,race_index,quadData,gameSetings,quadManagement);
        resValidation = new ResValidation();
        aILogic = new AILogic(aIUtil, quadManagement, camera,quadData, resValidation, mapM,color);
        quadUtil = new QuadUtil(quadManagement, camera, mapM, maths, race_index, bord,quadData, resValidation);
        menuData = new MenuData(quadData.menuPoliList, quad_ibo, textureUtil, quadUtil,display,quadData,camera,gameSetings);
        shaderProgram = new ShaderProgram(maths);
        renderer = new Renderer(shaderProgram, maths, camera);
        renderer.initQuad(quad_ibo);
        input = new Input(display, camera, renderer, mapM, galaxy, quadUtil, maths, quadManagement, menuData, quad_ibo, textureUtil, race_index, bord,solarSystem,campaign,quadData,aILogic);
        pick = new MousePicker(display, camera, renderer.getProjectionMatrix(),maths);
        shaderProgram.start();
        xBuffer = BufferUtils.createDoubleBuffer(1);
        yBuffer = BufferUtils.createDoubleBuffer(1);    
        scroll  = new ScrollCallback(camera , mapM ,galaxy , menuData,quadUtil,quadData);
        keyboard = new KeyCallBack();
        
        saveG = new Save(quadData);
        
        mouseClick = new MouseClickCallback(mapM,display,camera,renderer,maths,quadUtil,menuData,quad_ibo,textureUtil,race_index,solarSystem,campaign,scroll,quadData,gameSetings,galaxy,saveG,resValidation);
        scrollCallback =  glfwSetScrollCallback(display.getWindow(), scroll);
        keyCallback    =  glfwSetKeyCallback(display.getWindow(), keyboard );
        mouseButtonCallback = glfwSetMouseButtonCallback(display.getWindow(), mouseClick);   
        input.scroll = scroll;
        input.mouseClick = mouseClick;
        input.keyboard = keyboard; 
        input.mouseClick.galaxyMap = galaxy;
        mouseClick.input = input;    
        gameStatusHandeling = new GameStatusHandeling(quadData,input,mouseClick);
        quadUtil.mouseClick = mouseClick;
   
}
       
public void loop() throws Exception   {

    input.mouseClick.generateMainMenu();
    
    while (!glfwWindowShouldClose(display.window) && !exit_game) 
    {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            glfwPollEvents();
            timer.sync(200);    
            FPS();
            updateCursorPosition();
   
            shaderProgram.loadViewMatrix(camera);
            renderer.getViewMatrix();
            if (game_graphics_mod) {
                renderMap();
                renderGalaxy();
            }
 
            
            if(singleDraw)   {
                //__DRAW__PRIMITIVES___________________________________________________________________________________//
                     // drawSquare(0,0,2 , 2  , pColor.Blue);
                     //  drawFillQuad(0, 0, 0 ,   0,0,0,  1,1, pColor.Red);  
                     // drawSegment(0, 0, 0, 4.9f, race_index, selectEdgeWidth);
                     // drawSegment(0, 4.9f, 4.9f, 0, race_index, selectEdgeWidth);
                     // drawSegment(4.9f, 0, 0, 0, race_index, selectEdgeWidth); 
                //____________________________________________________________________________________________________//
            singleDraw = false;                
            }
                  
    //___INDUCE FRAME DELAY__________________________________________________//  
         //   gameStatusHandeling.gameOverConditions();
          //  gameStatusHandeling.gameVictoryConditions();
    //_________________________________________________________________________//      
  
            if (!game_graphics_mod) {
                renderPointPrimitives();
            } else {
                selectQuadPoiligons();
                renderPointPrimitives();
                renderLinePrimitives();
                renderSolarSystem();
                renderColonyRings();
                renderSpaceBorder();
                renderShips();
                renderShipSelectedRing();
                renderBullets();
                renderPhaserEmiters();
                renderExplosions();
                renderShields();
                renderPhasers();
                renderMenuPoligons();
                renderText();
                
                quadUtil.checkTargetLockOn(quadData.playerList);
                aILogic.startAI();
                input.KeyboardControl();
                          
            }
            glfwSwapBuffers(display.getWindow());
    }
    shaderProgram.stop();
    renderer.unbindQuadArray();
    cleanUP();
    glfwDestroyWindow(display.window);
    glfwTerminate();

}
public void selectQuadPoiligons() {
      if(selectQuadStatus)  {
          selectWidth  = (float) Math.abs(curentRay.x - selectQuadX);
          selectHeight = (float) Math.abs(curentRay.y - selectQuadY);
          drawSquare(Math.min(curentRay.x, selectQuadX) + selectWidth/2, Math.min(curentRay.y, selectQuadY) + selectHeight/2 , selectWidth/2 , selectHeight/2 , pColor.Blue , selectEdgeWidth);
      }
}

public void updatePhaser(Quad phaser) {
            float delta_x = Math.abs(phaser.phaserEmiter.canon_1.x - phaser.phaserEmiter.target.x)/2;
            float delta_y = Math.abs(phaser.phaserEmiter.canon_1.y - phaser.phaserEmiter.target.y)/2;
            phaser.quadRotation.z = (float) ( - Math.toDegrees(Math.atan2(phaser.phaserEmiter.target.x - phaser.phaserEmiter.canon_1.x ,  phaser.phaserEmiter.target.y - phaser.phaserEmiter.canon_1.y)));
            if(phaser.phaserEmiter.targetShiep.shield > 0)  {
                 float temp_x =   phaser.phaserEmiter.targetShiep.shielWidth  *  (float) Math.sin(Math.toRadians(phaser.quadRotation.z));
                 float temp_y =   phaser.phaserEmiter.targetShiep.shielWidth  *  (float) Math.cos(Math.toRadians(phaser.quadRotation.z));
                     if(phaser.phaserEmiter.canon_1.x >= phaser.phaserEmiter.target.x  &&  phaser.phaserEmiter.canon_1.y >= phaser.phaserEmiter.target.y){
                        phaser.curentPosition.x = (phaser.phaserEmiter.canon_1.x - delta_x) + temp_x;
                        phaser.curentPosition.y = (phaser.phaserEmiter.canon_1.y - delta_y) - temp_y;
                     }
                     if(phaser.phaserEmiter.canon_1.x < phaser.phaserEmiter.target.x  &&  phaser.phaserEmiter.canon_1.y < phaser.phaserEmiter.target.y){
                        phaser.curentPosition.x = (phaser.phaserEmiter.canon_1.x + delta_x)  + temp_x;
                        phaser.curentPosition.y = (phaser.phaserEmiter.canon_1.y + delta_y)  - temp_y;
                     }   
                     if(phaser.phaserEmiter.canon_1.x >= phaser.phaserEmiter.target.x  &&  phaser.phaserEmiter.canon_1.y < phaser.phaserEmiter.target.y){
                        phaser.curentPosition.x = (phaser.phaserEmiter.canon_1.x - delta_x) + temp_x;
                        phaser.curentPosition.y = (phaser.phaserEmiter.canon_1.y + delta_y) - temp_y;
                     }
                     if(phaser.phaserEmiter.canon_1.x < phaser.phaserEmiter.target.x  &&  phaser.phaserEmiter.canon_1.y >= phaser.phaserEmiter.target.y){
                        phaser.curentPosition.x = (phaser.phaserEmiter.canon_1.x + delta_x) + temp_x;
                        phaser.curentPosition.y = (phaser.phaserEmiter.canon_1.y - delta_y) - temp_y ;
                     }        
                     phaser.model.scale.y  = (float) (Math.sqrt(Math.pow(phaser.phaserEmiter.canon_1.x - phaser.phaserEmiter.target.x , 2.) + Math.pow(phaser.phaserEmiter.canon_1.y - phaser.phaserEmiter.target.y, 2))  / 2) - phaser.phaserEmiter.targetShiep.shielWidth;
             }   else  {
                             if(phaser.phaserEmiter.canon_1.x >= phaser.phaserEmiter.target.x  &&  phaser.phaserEmiter.canon_1.y >= phaser.phaserEmiter.target.y){
                                phaser.curentPosition.x = phaser.phaserEmiter.canon_1.x - delta_x;
                                phaser.curentPosition.y = phaser.phaserEmiter.canon_1.y - delta_y;
                             }
                             if(phaser.phaserEmiter.canon_1.x < phaser.phaserEmiter.target.x  &&  phaser.phaserEmiter.canon_1.y < phaser.phaserEmiter.target.y){
                                phaser.curentPosition.x = phaser.phaserEmiter.canon_1.x + delta_x;
                                phaser.curentPosition.y = phaser.phaserEmiter.canon_1.y + delta_y;
                             }   
                             if(phaser.phaserEmiter.canon_1.x >= phaser.phaserEmiter.target.x  &&  phaser.phaserEmiter.canon_1.y < phaser.phaserEmiter.target.y){
                                phaser.curentPosition.x = phaser.phaserEmiter.canon_1.x - delta_x;
                                phaser.curentPosition.y = phaser.phaserEmiter.canon_1.y + delta_y;
                             }
                             if(phaser.phaserEmiter.canon_1.x < phaser.phaserEmiter.target.x  &&  phaser.phaserEmiter.canon_1.y >= phaser.phaserEmiter.target.y){
                                phaser.curentPosition.x = phaser.phaserEmiter.canon_1.x + delta_x;
                                phaser.curentPosition.y = phaser.phaserEmiter.canon_1.y - delta_y;
                             }          
                             phaser.model.scale.y  = (float) (Math.sqrt(Math.pow(phaser.phaserEmiter.canon_1.x - phaser.phaserEmiter.target.x , 2.) + Math.pow(phaser.phaserEmiter.canon_1.y - phaser.phaserEmiter.target.y, 2))  / 2);   
                       }
}
public void renderSolarSystem(){
            renderer.loadNumbetOfRows(textureUtil.solarSystemTex_max.rows, shaderProgram);
            renderer.loadTexTransparenci(1.0f,shaderProgram);
            textureUtil.solarSystemTex_max.glBind();      
            for(Sun sun : quadData.sunList)  {
                if(sun.exploredList.get(0))  {
                    if (sun.enableToRender) {
                        renderer.loadQuadMatrix(sun.model, shaderProgram);
                        renderer.renderQuad(sun.model);
                    }
                    for (Planet planet : sun.listaPlanete) {

                        if (planet.enableToRender) {
                            renderer.loadQuadMatrix(planet.model, shaderProgram);
                            renderer.renderQuad(planet.model);
                        }
                    }
                }
            }
}
public void renderColonyRings() {
    renderer.loadNumbetOfRows(textureUtil.ringTexture.rows, shaderProgram);
    renderer.loadTexTransparenci(textureUtil.ringTexture.transparency, shaderProgram);
    textureUtil.ringTexture.glBind();
    for(Quad ring : quadData.colonizedInfluenceRing) {
        if(ring.enableToRender)  {
            if(quadData.playerList.get(ring.quadPlayerIndex).renderPlayerElements) {
                renderer.loadQuadMatrix(ring.model, shaderProgram);
                renderer.renderQuad(ring.model);
            }
        }
    }
}
public void renderShips() {
            renderer.loadNumbetOfRows(textureUtil.shipTex_max.rows, shaderProgram);
            renderer.loadTexTransparenci(textureUtil.shipTex_max.transparency, shaderProgram);
            textureUtil.shipTex_max.glBind();
    //______________________________________________________________________________________________//        
            for(int i=0 ; i<quadData.playerList.size() ; i++)  {
                for(int j=0 ; j<quadData.playerList.get(i).shipList.size() ; j++)   {
                    Quad ship = quadData.playerList.get(i).shipList.get(j); 
    //_______________________________________________________________________________________________//     
                    if(quadData.playerList.get(i).renderPlayerElements) {
                        if (ship.enableToRender) {
                            renderer.loadQuadMatrix(ship.model, shaderProgram);
                            renderer.renderQuad(ship.model);
                        }
                    }
                    quadUtil.move(ship , quadData.bulletList , quadData.explosionList);
                    quadUtil.shipCheck(quadData.playerList.get(i), ship , quadData.sunList , quadData.bulletList, quadData.explosionList ,quadData.phaserList ,quadData.shieldList,      quadData.phaserEmiter ,j);                    
                    if(!ship.alive)  {
                      if(ship.isCarrierShip){
                         for(Quad interceptor : ship.interceptors){
                             interceptor.parent = null;
                             interceptor.interceptor_is_dock = false;
                         }
                         ship.interceptors.clear();
                      }  
                      if(ship.isColoniShip)  {
                        quadData.playerList.get(i).colonizationProcess = false;
                        quadData.playerList.get(i).coloniShip = null;
                      } else {
                                if (ship.deployPlanet != null)  {
                                      quadData.playerList.get(i).shipCount--;
                                      ship.deployPlanet.shipCount--;
                                }
                             }
                      quadData.playerList.get(i).shipList.remove(j);
                      j--;
                    }
                }   
            }
}
public void renderShipSelectedRing() {
    renderer.loadNumbetOfRows(textureUtil.ringTexture.rows, shaderProgram);
    renderer.loadTexTransparenci(1, shaderProgram);
    textureUtil.ringTexture.glBind();
    for(int i=0 ; i < quadData.shipSelectionRing.size() ; i++) {
        Quad shipRing = quadData.shipSelectionRing.get(i);
        renderer.loadQuadMatrix(shipRing.model, shaderProgram);
        renderer.renderQuad(shipRing.model);
        if(!shipRing.alive)  {
            quadData.shipSelectionRing.remove(i);
            i--;
        }
    }
}
public void renderBullets() {
           if(quadData.bulletList.size()!= 0){
                renderer.loadNumbetOfRows(textureUtil.bulletTex.rows, shaderProgram);
                textureUtil.bulletTex.glBind();  
            }
            for(int i=0 ; i<quadData.bulletList.size() ; i++)  {
              Quad  bullet = quadData.bulletList.get(i);  
              if(bullet.enableToRender)  {
                  if(quadData.playerList.get(bullet.quadPlayerIndex).renderPlayerElements)   {
                      renderer.loadQuadMatrix(bullet.model, shaderProgram);
                      renderer.renderQuad(bullet.model);
                  }
              }
              quadUtil.moveBullet(bullet);
              quadUtil.bulletCheck(bullet, quadData.explosionList , quadData.shieldList);              
              if(!bullet.alive)  {
                 quadData.bulletList.remove(i);
                 i--;
              } else {
                  if (Math.abs(bullet.curentPosition.x - bullet.bulletFiredCoordX) > bulletdeleteRange/quadManagement.scaleQuad || Math.abs(bullet.curentPosition.y - bullet.bulletFiredCoordY) > bulletdeleteRange/quadManagement.scaleQuad)   {
                    quadData.bulletList.remove(i);
                    i--;
                  }
              }
            }
}
public void renderPhaserEmiters() {
    if(quadData.phaserEmiter.size() != 0){
        renderer.loadNumbetOfRows(textureUtil.phasetEmiter.rows, shaderProgram);
        renderer.loadTexTransparenci(textureUtil.phasetEmiter.transparency, shaderProgram);
        textureUtil.phasetEmiter.glBind();
    }
    for(int i=0 ; i<quadData.phaserEmiter.size() ; i++)  {
        Quad emiter =  quadData.phaserEmiter.get(i);
        if(quadData.playerList.get(emiter.quadPlayerIndex).renderPlayerElements)  {
            renderer.loadQuadMatrix(emiter.model, shaderProgram);
            renderer.renderQuad(emiter.model);
        }
        if(!game_pause) {
            emiter.model.ANIMATION_COUNT++;
        }
        if(emiter.model.ANIMATION_COUNT == emiter.model.ANIMATION_RESET_V)  {
            emiter.model.ANIMATION_COUNT = 0;
            emiter.model.INDEX_T++;
            emiter.model.ANIMATION_DELETE_INDEX++;
        }
        if(emiter.model.ANIMATION_DELETE_INDEX == emiter.model.ANIMATION_FRAME_NUMBER)  {
            quadData.phaserEmiter.remove(i);
            i--;
        }
    }
}
public void renderSpaceBorder() {
           renderer.loadNumbetOfRows(textureUtil.board.rows, shaderProgram);
           renderer.loadTexTransparenci(textureUtil.board.transparency, shaderProgram);
           textureUtil.board.glBind();  

            for(int i=0 ; i<quadData.playerList.size() ; i++)  {
                for(int k=0 ; k<quadData.playerList.get(i).bordersList.size() ; k++)     { 
                    Quad border = quadData.playerList.get(i).bordersList.get(k);
                    if(quadData.playerList.get(i).renderPlayerElements)   {
                         if(border.enableToRender && !border.isHexBorder)    {
                            renderer.loadQuadMatrix(border.model, shaderProgram);
                            renderer.renderQuad(border.model);
                         }
                    }
                    if(!border.alive)  {
                        quadData.playerList.get(i).bordersList.remove(k);
                        k--;
                    }
                }
            }
            renderer.loadTexTransparenci(0.1f + camera.getPosition().z/200, shaderProgram);
            for(int i=0 ; i<quadData.playerList.size() ; i++)  {
                for(int k=0 ; k<quadData.playerList.get(i).bordersList.size() ; k++)     { 
                    Quad border = quadData.playerList.get(i).bordersList.get(k);
                    if(quadData.playerList.get(i).renderPlayerElements)   {
                         if(border.enableToRender && border.isHexBorder)    {
                            renderer.loadQuadMatrix(border.model, shaderProgram);
                            renderer.renderQuad(border.model);
                         }
                    }
                    if(!border.alive)  {
                        quadData.playerList.get(i).bordersList.remove(k);
                        k--;
                    }
                }
            }            
}
public void renderExplosions() {
            if(quadData.explosionList.size() != 0){
                renderer.loadNumbetOfRows(textureUtil.explosionTex.rows, shaderProgram);
                renderer.loadTexTransparenci(textureUtil.explosionTex.transparency, shaderProgram);
                textureUtil.explosionTex.glBind();     
            }
            for(int i=0 ; i<quadData.explosionList.size() ; i++)  { 
              Quad explosion =  quadData.explosionList.get(i);
              if(quadData.playerList.get(explosion.quadPlayerIndex).renderPlayerElements)   {
                  renderer.loadQuadMatrix(explosion.model, shaderProgram);
                  renderer.renderQuad(explosion.model);
              }
              if(!game_pause) {
                  explosion.model.ANIMATION_COUNT++;
              }
              if(explosion.model.ANIMATION_COUNT == explosion.model.ANIMATION_RESET_V)    {   
                  explosion.model.ANIMATION_COUNT = 0;
                  explosion.model.INDEX_T++;
                  explosion.model.ANIMATION_DELETE_INDEX++;
              }
              if(explosion.model.ANIMATION_DELETE_INDEX == explosion.model.ANIMATION_FRAME_NUMBER)   {
                quadData.explosionList.remove(i);
                i--;
              }
            }  
}
public void renderShields() {
            if(quadData.shieldList.size() != 0){
                renderer.loadNumbetOfRows(textureUtil.shieldTex.rows, shaderProgram);
                renderer.loadTexTransparenci(textureUtil.shieldTex.transparency, shaderProgram);
                textureUtil.shieldTex.glBind();     
            }
            for(int i=0 ; i<quadData.shieldList.size() ; i++)  {
              Quad shield = quadData.shieldList.get(i);
              if(quadData.playerList.get(shield.quadPlayerIndex).renderPlayerElements)     {
                  renderer.loadQuadMatrix(shield.model, shaderProgram);
                  renderer.renderQuad(shield.model);
              } 
              if(!game_pause) {
                  shield.model.ANIMATION_COUNT++;
              }
              if(shield.model.ANIMATION_COUNT == shield.model.ANIMATION_RESET_V)  {
                  shield.model.ANIMATION_COUNT = 0;
                  shield.model.INDEX_T++;
                  shield.model.ANIMATION_DELETE_INDEX++;
              }
              if(shield.model.ANIMATION_DELETE_INDEX == shield.model.ANIMATION_FRAME_NUMBER)   {
                quadData.shieldList.remove(i);
                i--;
              }
            } 
}
public void renderPhasers(){
    if(quadData.phaserList.size() != 0){
        renderer.loadNumbetOfRows(textureUtil.phaserTex.rows, shaderProgram);
        renderer.loadTexTransparenci(textureUtil.phaserTex.transparency, shaderProgram);
        textureUtil.phaserTex.glBind();
    }
    for(int i=0 ; i<quadData.phaserList.size() ; i++)  {
            Quad phaser = quadData.phaserList.get(i);
            updatePhaser(quadData.phaserList.get(i));
            if (phaser.phaserEmiter.targetShiep.alive) {
                if(quadData.playerList.get(phaser.quadPlayerIndex).renderPlayerElements)    {
                    renderer.loadQuadMatrix(phaser.model, shaderProgram);
                    renderer.renderQuad(phaser.model);
                }
            }
            if(!game_pause) {
                phaser.model.ANIMATION_COUNT++;
            }
            if (phaser.model.ANIMATION_COUNT == phaser.model.ANIMATION_RESET_V) {
                phaser.model.ANIMATION_COUNT = 0;
                phaser.model.INDEX_T++;
                phaser.model.ANIMATION_DELETE_INDEX++;
            }
            if (phaser.model.ANIMATION_DELETE_INDEX == phaser.model.ANIMATION_FRAME_NUMBER) {
                quadData.phaserList.remove(i);
                i--;
            }
    }
}
public void renderMenuPoligons() {
    if(!shipSelectMenuText) {
        renderer.loadNumbetOfRows(textureUtil.solarSystemTex_max.rows, shaderProgram);
        renderer.loadTexTransparenci(textureUtil.solarSystemTex_max.transparency , shaderProgram);
        textureUtil.solarSystemTex_max.glBind();
    } else {
        renderer.loadNumbetOfRows(textureUtil.shipTex_max.rows, shaderProgram);
        renderer.loadTexTransparenci(textureUtil.shipTex_max.transparency , shaderProgram);
        textureUtil.shipTex_max.glBind();
    }
    for(Quad menuP : quadData.menuPoliList) {
        renderer.loadQuadMatrix(menuP.model, shaderProgram);
        renderer.renderQuad(menuP.model);
    }
}
public void renderText() {
        renderer.loadNumbetOfRows(textureUtil.textChart_ON.rows, shaderProgram);
        for(TextData textData : quadData.textToRender)  {
             if(textData.enabledChart) {
                  textureUtil.textChart_ON.glBind();
             } else {
                textureUtil.textChart_OFF.glBind();
             }
            for(Quad character : textData.textChars)  {
                renderer.loadQuadMatrix(character.model, shaderProgram);
                renderer.renderQuad(character.model);
            }
        }
}
public void renderGalaxy() {
            renderer.loadNumbetOfRows(textureUtil.galaxyTex.rows, shaderProgram);
            renderer.loadTexTransparenci(textureUtil.galaxyTex.transparency,shaderProgram);
            textureUtil.galaxyTex.glBind();
            renderer.loadQuadMatrix(galaxy, shaderProgram);
            renderer.renderQuad(galaxy);
}
public void renderMap() {
        renderer.loadNumbetOfRows(textureUtil.mapTex.rows, shaderProgram);
        renderer.loadTexTransparenci(textureUtil.mapTex.transparency, shaderProgram);
        textureUtil.mapTex.glBind();
        renderer.loadQuadMatrix(mapM, shaderProgram);
        renderer.renderQuad(mapM);
}
public void renderLinePrimitives()  {         
           renderer.loadNumbetOfRows(textureUtil.board.rows, shaderProgram);
           renderer.loadTexTransparenci(textureUtil.board.transparency, shaderProgram);
           textureUtil.board.glBind();  
                for(int k=0 ; k<quadData.linePrimitive.size() ; k++)    {  
                    Quad line = quadData.linePrimitive.get(k);
                    renderer.loadQuadMatrix(line.model, shaderProgram);
                    renderer.renderQuad(line.model); 
                } 
                quadData.linePrimitive.clear(); 
}
public void renderPointPrimitives()  { 
    
           renderer.loadNumbetOfRows(textureUtil.board.rows, shaderProgram);
           renderer.loadTexTransparenci(1f, shaderProgram);
           textureUtil.board.glBind();  
                for(int k=0 ; k<quadData.pointPrimitive.size() ; k++)    {  
                    renderer.loadQuadMatrix(quadData.pointPrimitive.get(k).model, shaderProgram);
                    renderer.renderQuad(quadData.pointPrimitive.get(k).model); 
                } 
   //            pointPrimitive.clear();
}
public void drawPoint(float x,float y ,float z ,     float rotX , float rotY , float rotZ ,    float w , float h , int textIndex) {
  quadManagement.addPoint(x, y, z ,rotX,rotY,rotZ , w,h , textIndex);
}
public void drawFillQuad(float x,float y ,float z ,     float rotX, float rotY, float rotZ ,    float w , float h , int textIndex) {
   quadManagement.addPoint(x, y, z ,rotX,rotY,rotZ , w,h , textIndex);
}
public void drawSegment(float x0 , float y0 , float x1 , float y1 , int textIndex , float selectEdgeWidth) {
    quadManagement.addSegmentLine(x0 , y0 , x1 , y1 , selectEdgeWidth , textIndex);
}
public void drawSquare(float x , float y , float w , float h   , int textIndex , float selectEdgeWidth) {
    quadManagement.addSegmentLine(x - w , y - h, x + w, y - h , selectEdgeWidth , textIndex);
    quadManagement.addSegmentLine(x - w , y + h, x + w, y + h , selectEdgeWidth , textIndex);
    quadManagement.addSegmentLine(x - w , y + h, x - w, y - h , selectEdgeWidth , textIndex);
    quadManagement.addSegmentLine(x + w , y - h, x + w, y + h , selectEdgeWidth , textIndex);  
}
public void updateCursorPosition() {
    glfwGetCursorPos(display.window, xBuffer, yBuffer);
    xPos = xBuffer.get(0);
    yPos = yBuffer.get(0); 
    curentRay = pick.update(true , xPos , yPos);
}
public void cleanUP() {
        quadUtil.soundMainClass.cleanUP();
    	shaderProgram.cleanUP();
	quad_ibo.cleanUp();
        triangle_ibo.cleanUp();
        textureUtil.solarSystemTex_max.glDispose();
        textureUtil.shipTex_max.glDispose();
        textureUtil.mapTex.glDispose();
        textureUtil.textChart_ON.glDispose();
        textureUtil.textChart_OFF.glDispose();
        input.releseCalback();
}
    public void FPS() {
        int countR = 0;
        for(Player player : quadData.playerList){
            for(Quad ship : player.shipList){
               if(ship.enableToRender){
                 countR++;
               }
            }        
        }
        
        int totalShipCount = 0;
        for (Player play : quadData.playerList) {
            totalShipCount += play.shipList.size();
        }
        end = System.nanoTime() / 1000000000;
        if (end - start > 1) {
            start = end;
            if (quadData.playerList.size() != 0) {
                    if (!quadUtil.soundMainClass.fundalSource.isPlaying()) {
                        quadUtil.soundMainClass.fundalSource.play(quadUtil.soundMainClass.fundal);
                    }
                    if (mouseClick.game_mode_type == 2) {
                        glfwSetWindowTitle(display.getWindow(), "FPS " + String.valueOf(valoare / 2) + " Ships= " + countR + " Rings= " + quadData.colonizedInfluenceRing.size() + " Bullet= " + quadData.bulletList.size() + " Pha= " + quadData.phaserList.size() + " Exp= " + quadData.explosionList.size() + " Shiel= " + quadData.shieldList.size() + " MENU= " + quadData.menuPoliList.size() + " Cam= " + camera.getPosition().z + " [T]" + galaxy.texture.transparency + " P" + quadData.pointPrimitive.size() + " E" + quadData.linePrimitive.size() + " B" + quadData.playerList.get(0).bordersList.size() + " M=" + mission_index + " VOL=" + volume);
                    } else {
                        glfwSetWindowTitle(display.getWindow(), "FPS "+String.valueOf(valoare / 2)+"    Mision =  " + mission_index);
                    }
            }
            valoare = 0;
        }
        valoare++;
    }  
    public void readXYPixel(int x , int y){
        if(enablePixelRead) {     
            GL11.glReadPixels(x, 1000-y, 1, 1, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, pixel);
             rgb.x = pixel.get(0) & 0xFF;
             rgb.y = pixel.get(1) & 0xFF;
             rgb.z = pixel.get(2) & 0xFF;        
        enablePixelRead = false;
        }
    }
}
class PrimitiveColor {
   int Red = 24;
   int Blue = 25;
   int cian = 26;
   int white = 27;
   int yelow = 28;
   int green = 29;
}
