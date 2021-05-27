/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Campaign;

import java.util.ArrayList;

public class Color {

public ArrayList<Integer>   gameshipIndex      =  new ArrayList<Integer>();
public ArrayList<Integer>   gameBulletIndex_1  =  new ArrayList<Integer>();
public ArrayList<Integer>   gameBulletIndex_2  =  new ArrayList<Integer>();
public ArrayList<Integer>   gameRingIndex      =  new ArrayList<Integer>();
public ArrayList<Integer>   gameCercleIndex    =  new ArrayList<Integer>();
public ArrayList<Integer>   gamePhaserIndex    =  new ArrayList<Integer>();
public ArrayList<Integer>   gameShieldIndex    =  new ArrayList<Integer>();
public ArrayList<Integer>   gameBordetIndex    =  new ArrayList<Integer>();
public ArrayList<Integer>   gamesegmentedBordetIndex    =  new ArrayList<Integer>();
public ArrayList<Integer>   gameExplosionIndex =  new ArrayList<Integer>();
public ArrayList<Integer>   gameEmiterIndex =  new ArrayList<Integer>();
public ArrayList<Integer>   gameShipSelectRing =  new ArrayList<Integer>();
public ArrayList<Integer>   gameBordetHexIndex    =  new ArrayList<Integer>();

    public int  shipTRows      = 10;
    public int  explosionTRows = 10;
    public int  phaserTRows    = 10;
    public int  shieldTRows    = 10;
    public int  emiterTRows    = 10;
    
    public void resetDataIndex(){
        gameshipIndex.clear();
        gameBulletIndex_1.clear();
        gameBulletIndex_2.clear();
        gameRingIndex.clear();
        gameCercleIndex.clear();
        gamePhaserIndex.clear();
        gameShieldIndex.clear();
        gameBordetIndex.clear();
        gamesegmentedBordetIndex.clear();
        gameExplosionIndex.clear();
        gameEmiterIndex.clear();
        gameShipSelectRing.clear();
        gameBordetHexIndex.clear();    
    }
    public Color () {
    resetDataIndex();
    //__PLAYER_0__________________________________//    
        gameshipIndex.add(shipTRows * 1);
        gameBordetIndex.add(1);
        gamesegmentedBordetIndex.add(13);
        gameBulletIndex_1.add(1);
        gameBulletIndex_2.add(6);
        gameExplosionIndex.add(explosionTRows * 0);
        gameEmiterIndex.add(emiterTRows * 1);
        gameShieldIndex.add(shieldTRows * 1);
        gamePhaserIndex.add(phaserTRows * 1);
        gameRingIndex.add(0);
        gameShipSelectRing.add(9);
        gameCercleIndex.add(12);
        gameBordetHexIndex.add(25);
     //__________________________________________//
     //__PLAYER_1__________________________________//
        gameshipIndex.add(shipTRows * 0);
        gameBordetIndex.add(0);
        gamesegmentedBordetIndex.add(12);
        gameBulletIndex_1.add(0);
        gameBulletIndex_2.add(5);
        gameExplosionIndex.add(explosionTRows * 1);
        gameEmiterIndex.add(emiterTRows * 0);
        gameShieldIndex.add(shieldTRows * 0);
        gamePhaserIndex.add(phaserTRows * 0);
        gameRingIndex.add(1);
        gameShipSelectRing.add(10);
        gameCercleIndex.add(13);
        gameBordetHexIndex.add(24);
    //__________________________________________//     
    //__PLAYER_2__________________________________//    
        gameshipIndex.add(shipTRows * 2);
        gameBordetIndex.add(2);
        gamesegmentedBordetIndex.add(14);
        gameBulletIndex_1.add(2);
        gameBulletIndex_2.add(7);
        gameExplosionIndex.add(explosionTRows * 2);
        gameEmiterIndex.add(emiterTRows * 2);
        gameShieldIndex.add(shieldTRows * 2);
        gamePhaserIndex.add(phaserTRows * 2);
        gameRingIndex.add(2);
        gameShipSelectRing.add(11);
        gameCercleIndex.add(14);
        gameBordetHexIndex.add(26);
    //__________________________________________// 
    //__PLAYER_3__________________________________//    
        gameshipIndex.add(shipTRows * 3);
        gameBordetIndex.add(3);
        gamesegmentedBordetIndex.add(15);
        gameBulletIndex_1.add(3);
        gameBulletIndex_2.add(8);
        gameExplosionIndex.add(explosionTRows * 3);
        gameEmiterIndex.add(emiterTRows * 3);
        gameShieldIndex.add(shieldTRows * 3);
        gamePhaserIndex.add(phaserTRows * 3);
        gameRingIndex.add(3);
        gameShipSelectRing.add(12);
        gameCercleIndex.add(15);
        gameBordetHexIndex.add(27);
    //__________________________________________//   
    //__PLAYER_4__________________________________//    
        gameshipIndex.add(shipTRows * 4);
        gameBordetIndex.add(4);
        gamesegmentedBordetIndex.add(16);
        gameBulletIndex_1.add(4);
        gameBulletIndex_2.add(10);
        gameExplosionIndex.add(explosionTRows * 4);
        gameEmiterIndex.add(emiterTRows * 4);
        gameShieldIndex.add(shieldTRows * 4);
        gamePhaserIndex.add(phaserTRows * 4);
        gameRingIndex.add(4);
        gameShipSelectRing.add(13);
        gameCercleIndex.add(16);
         gameBordetHexIndex.add(28);
    //__________________________________________//
    //__PLAYER_5__________________________________//    
        gameshipIndex.add(shipTRows * 5);
        gameBordetIndex.add(5);
        gamesegmentedBordetIndex.add(17);
        gameBulletIndex_1.add(9);
        gameBulletIndex_2.add(11);
        gameExplosionIndex.add(explosionTRows * 5);
        gameEmiterIndex.add(emiterTRows * 5);
        gameShieldIndex.add(shieldTRows * 5);
        gamePhaserIndex.add(phaserTRows * 5);
        gameRingIndex.add(5);
        gameShipSelectRing.add(14);
        gameCercleIndex.add(17);
          gameBordetHexIndex.add(29);
    //__________________________________________//  
    //__PLAYER_6__________________________________//    
        gameshipIndex.add(shipTRows * 6);
        gameBordetIndex.add(6);
        gamesegmentedBordetIndex.add(18);
        gameBulletIndex_1.add(14);
        gameBulletIndex_2.add(12);
        gameExplosionIndex.add(explosionTRows * 6);
        gameEmiterIndex.add(emiterTRows * 6);
        gameShieldIndex.add(shieldTRows * 6);
        gamePhaserIndex.add(phaserTRows * 6);
        gameRingIndex.add(6);
        gameShipSelectRing.add(15);
        gameCercleIndex.add(18);
         gameBordetHexIndex.add(30);
    //__________________________________________//      
    //__PLAYER_7___________________________________//    
        gameshipIndex.add(shipTRows * 7);
        gameBordetIndex.add(7);
        gamesegmentedBordetIndex.add(19);
        gameBulletIndex_1.add(19);
        gameBulletIndex_2.add(13);
        gameExplosionIndex.add(explosionTRows * 7);
        gameEmiterIndex.add(emiterTRows * 7);
        gameShieldIndex.add(shieldTRows * 7);
        gamePhaserIndex.add(phaserTRows * 7);
        gameRingIndex.add(7);
        gameShipSelectRing.add(16);
        gameCercleIndex.add(19);
        gameBordetHexIndex.add(31);
    //__________________________________________//      
    //__PLAYER_8___________________________________//    
        gameshipIndex.add(shipTRows * 8);
        gameBordetIndex.add(8);
        gamesegmentedBordetIndex.add(20);
        gameBulletIndex_1.add(24);
        gameBulletIndex_2.add(15);
        gameExplosionIndex.add(explosionTRows * 8);
        gameEmiterIndex.add(emiterTRows * 8);
        gameShieldIndex.add(shieldTRows * 8);
        gamePhaserIndex.add(phaserTRows * 8);
        gameRingIndex.add(8);
        gameShipSelectRing.add(17);
        gameCercleIndex.add(20);
        gameBordetHexIndex.add(32);
     //__________________________________________//     
    //__PLAYER_9___________________________________//    
        gameshipIndex.add(shipTRows * 9);
        gameBordetIndex.add(9);
        gamesegmentedBordetIndex.add(21);
        gameBulletIndex_1.add(18);
        gameBulletIndex_2.add(16);
        gameExplosionIndex.add(explosionTRows * 9);
        gameEmiterIndex.add(emiterTRows * 9);
        gameShieldIndex.add(shieldTRows * 9);
        gamePhaserIndex.add(phaserTRows * 0);
        gameRingIndex.add(18);
        gameShipSelectRing.add(18);
        gameCercleIndex.add(19);
        gameBordetHexIndex.add(33);
     //__________________________________________//       
     
    }

}