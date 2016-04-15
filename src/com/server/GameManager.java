package com.server;

import java.util.ArrayList;


/**
 * game state holder. holds the true state of a game to prevent cheating
 */
public class GameManager {

    private ArrayList<Player> playerList;
    public int[][] map;


    public GameManager() {
        this.playerList = new ArrayList<Player>();
        this.map = new int[16][16];
    }


    /**
     * find empty spot on the map
     * @return
     */
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

    /**
     * remove a player from player list by ID
     * @param id
     */
    public void removePlayerByID(int id) {
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getId() == id) {
                playerList.remove(i);
            }
        }
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

    public int[][] getMap() {
        return this.map;
    }

}
