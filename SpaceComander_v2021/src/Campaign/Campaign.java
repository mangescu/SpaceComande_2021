
package Campaign;

import Player.Player;
import Util.RandomFloat;
import java.util.ArrayList;
import spacecomander.QuadsManagement;


public class Campaign {
  
    int max_ship_size;
    Player player;
    Color color;
    RandomFloat rand = new RandomFloat();
    QuadsManagement  quadManagement;
    ArrayList<Player>    playerList;
    
      GameSetings  gameSetings;
    
    public Campaign(QuadsManagement  quadManagement , ArrayList<Player>    playerList,  GameSetings  gameSetings,Color color){
        this.quadManagement = quadManagement;
        this.playerList = playerList;
        this.gameSetings = gameSetings;
        this.color = color;
    }
    public void initMultiplayer(int numberOfPlayer,boolean isMultiplayerMod,boolean loadFromFile)  {
        if (!loadFromFile) {
            for (int i = 0; i < numberOfPlayer; i++) {
                quadManagement.addColonizationShip(null, playerList.get(i), playerList.get(i).start_position_x, playerList.get(i).start_position_y);
                playerList.get(i).colonizationProcess = true;
            }
        }
            if(isMultiplayerMod) 
            {
                
                for(int i=0 ; i<numberOfPlayer ; i++) {
                       for(int j=0 ; j<numberOfPlayer ; j++){
                                if(playerList.get(j).index != playerList.get(i).index) {
                                    playerList.get(i).enemy[playerList.get(j).index] = true;
                                }
                       }  
               }
            } 
    }    
    public void setupPlayers(ArrayList<Player>   playerList , int playerNumber, int minRadius , int maxRadius , int race_index,boolean eraseMode)  {
            color = new Color();
            if(eraseMode)   {
                if(playerList != null){
                     for(int i=0 ; i<playerList.size() ; i++) {
                        playerList.get(i).enemy[0] = false;
                        playerList.get(i).enemy[1] = false;
                        playerList.get(i).enemy[2] = false;
                        playerList.get(i).enemy[3] = false;
                        playerList.get(i).enemy[4] = false;
                        playerList.get(i).enemy[5] = false;
                        playerList.get(i).enemy[6] = false;
                        playerList.get(i).enemy[7] = false;
                        playerList.get(i).enemy[8] = false;
                        playerList.get(i).enemy[9] = false;
                   }           
                }                
            playerList.clear();
            }       
        if(playerNumber !=0) {
             max_ship_size = (900 / playerNumber);
        }
        int minDefenceColonizeCount = 1000;
        int maximumColonizePlanet   = 1000;

        for(int i =0 ; i < playerNumber ; i++) {
            
            //________PLAYER_0______________________________//    
                    player  =  new Player();

                    player.auto_colonizePlanet = true;
                    player.auto_addShip = true;
                    player.auto_deploySquad = true;
                     
                   player.start_position_x = rand.genConstrainedIntNumber(0, gameSetings.galaxy_size);
                   player.start_position_y = rand.genConstrainedIntNumber(0, gameSetings.galaxy_size);
              
                    if(i == race_index) {
                        player.auto_deploySquad = gameSetings.auto_deploySquad;
                        player.auto_addShip = gameSetings.auto_addShip;
                        player.auto_colonizePlanet = gameSetings.auto_colonizePlanet;
                        player.auto_assign_worker = gameSetings.auto_assign_worker;
                        player.auto_deploySquad = gameSetings.auto_deploySquad;                     
                    }
                   // System.out.println("color.gameRingIndex =   "+color.gameRingIndex.size());
                    player.ring_index   =color.gameRingIndex.get(i);
                    player.selectyRing  = color.gameShipSelectRing.get(i);
                    player.cercle_index = color.gameCercleIndex.get(i);
                    player.index = i;
                    player.federation_index = i;
                    player.maxShipCount = max_ship_size;


                    player.faction = i+1;
                    if(i == race_index) {
                        player.renderPlayerElements = true;
                    }
                  
                    player.ship_tex_index    = color.gameshipIndex.get(i);
                    player.boarder_tex_index = color.gameBordetIndex.get(i);
                    player.hex_Border_index  = color.gameBordetHexIndex.get(i);
                    player.segmented_boarder_tex_index = color.gamesegmentedBordetIndex.get(i);
                    player.bullet_tex_index_type_1 = color.gameBulletIndex_1.get(i);
                    player.bullet_tex_index_type_2 = color.gameBulletIndex_2.get(i);
                    player.explosion_tex_index = color.gameExplosionIndex.get(i);
                    player.shield_tex_index = color.gameShieldIndex.get(i);
                    player.phaser_tex_index = color.gamePhaserIndex.get(i);
                    player.phaserEmiter_tex_index = color.gameEmiterIndex.get(i);
                    
                    player.minDefenceColonizeCount = minDefenceColonizeCount;       
                    player.maximumColonizePlanet = maximumColonizePlanet;

                    playerList.add(player);
            //_____________________________________________// 
         
        
        } 
          
    }
    
}
