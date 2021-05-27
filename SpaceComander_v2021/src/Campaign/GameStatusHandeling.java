package Campaign;

import Input.Input;
import Input.MouseClickCallback;
import spacecomander.QuadData;
import static spacecomander.SpaceComander.game_pause;
import static spacecomander.SpaceComander.mission_index;
import static spacecomander.SpaceComander.race_index;

public class GameStatusHandeling {

    QuadData quadData;
    Input input;
    MouseClickCallback mouseClick;

    public GameStatusHandeling(QuadData quadData, Input input, MouseClickCallback mouseClick) {
        this.quadData = quadData;
        this.input = input;
        this.mouseClick = mouseClick;
    }

    public void gameOverConditions() {
        if (!game_pause) {
            if (quadData.playerList.size() != 0) {
                if (quadData.playerList.get(race_index).cPlanetList.size() == 0 && quadData.playerList.get(race_index).coloniShip == null) {
                    if (mission_index <= 1) {
                        //input.mouseClick.generateGameOverMenu();
                    }
                }
            }
        }
    }

    public void gameVictoryConditions() {
        //__1__SINGLEPLAYER_MODE___________________//
        //__2__MULTIPLAYER_MODE____________________//
        if (mouseClick.game_mode_type == 1) {
            if (!game_pause) {
                if (quadData.playerList.size() != 0) {
                    if (quadData.playerList.get(1).cPlanetList.size() == 0 && quadData.playerList.get(1).coloniShip == null) {
                        if (mission_index <= 1) {
                            for (int i = 0; i < quadData.playerList.size(); i++) {
                                //quadData.playerList.get(i).enemyList.clear();
                            }
                            input.mouseClick.generateVictoryMenu();
                        }
                    }
                }
            }
        }
        if (mouseClick.game_mode_type == 2) {

        }
    }
}
