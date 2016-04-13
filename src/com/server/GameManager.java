package com.server;

import com.domain.Position;
import com.domain.Spawn;

import java.util.ArrayList;
import java.util.logging.Logger;


/**
 * game state holder
 */
public class GameManager {

    private final static Logger LOG = LoggerManager.GetLogger(GameServerMain.class.getName());
    private final GameResponseDispatcher gameResponseDispatcher;
    private ArrayList<Player> playerList;
    private int[][] map;



    public GameManager() {
        gameResponseDispatcher = new GameResponseDispatcher(this);
        this.playerList = new ArrayList<Player>();
        this.map = new int[16][16];
    }


    public Position findFreePosition() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 0) {
                    return new Position(i,j);
                }
            }
        }
        return null;
    }

    public GameResponseDispatcher getGameResponseDispatcher() {
        return gameResponseDispatcher;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }


}
