package com.server;

import com.domain.Position;

import java.util.ArrayList;


/**
 * game state holder
 */
public class GameManager {

    private ArrayList<Player> playerList;
    public int[][] map;


    public GameManager() {
        this.playerList = new ArrayList<Player>();
        this.map = new int[16][16];
    }


    public Position findFreePosition() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 0) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }


    public ArrayList<Player> getPlayerList() {
        return playerList;
    }


    public Player getPlayerByID(int playerID) {
        for (Player player : this.playerList) {
            if (player.getId() == playerID) {
                return player;
            }
        }
        return null;
    }

    public int[][] getmap() {
        return this.map;
    }

}
