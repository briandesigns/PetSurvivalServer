package com.server;

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
