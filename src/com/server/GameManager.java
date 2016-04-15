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
        initMap();
    }

    private void initMap() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                map[i][j] = -1;
            }
        }
        map[7][2] = 0;
        map[8][2] = 0;
        map[9][2] = 0;

        map[5][3] = 0;
        map[6][3] = 0;
        map[7][3] = 0;
        map[8][3] = 0;
        map[9][3] = 0;
        map[10][3] = 0;

        map[4][4] = 0;
        map[5][4] = 0;
        map[6][4] = 0;
        map[9][4] = 0;
        map[10][4] = 0;

        map[4][5] = 0;
        map[5][5] = 0;
        map[6][5] = 0;
        map[9][5] = 0;
        map[10][5] = 0;
        map[11][5] = 0;

        map[3][6] = 0;
        map[4][6] = 0;
        map[5][6] = 0;
        map[6][6] = 0;
        map[7][6] = 0;
        map[8][6] = 0;
        map[9][6] = 0;
        map[10][6] = 0;
        map[11][6] = 0;
        map[12][6] = 0;



        map[2][7] = 0;
        map[3][7] = 0;
        map[6][7] = 0;
        map[7][7] = 0;
        map[8][7] = 0;
        map[9][7] = 0;
        map[10][7] = 0;
        map[12][7] = 0;

        map[2][8] = 0;
        map[3][8] = 0;
        map[6][8] = 0;
        map[7][8] = 0;
        map[8][8] = 0;
        map[9][8] = 0;
        map[10][8] = 0;
        map[11][8] = 0;
        map[12][8] = 0;

        map[2][9] = 0;
        map[3][9] = 0;
        map[4][9] = 0;
        map[5][9] = 0;
        map[6][9] = 0;
        map[7][9] = 0;
        map[10][9] = 0;
        map[11][9] = 0;
        map[12][9] = 0;

        map[3][10] = 0;
        map[4][10] = 0;
        map[5][10] = 0;
        map[6][10] = 0;
        map[7][10] = 0;
        map[10][10] = 0;
        map[11][10] = 0;

        map[4][11] = 0;
        map[5][11] = 0;
        map[7][11] = 0;
        map[8][11] = 0;
        map[9][11] = 0;
        map[10][11] = 0;

        map[4][12] = 0;
        map[5][12] = 0;
        map[6][12] = 0;
        map[7][12] = 0;
        map[8][12] = 0;
        map[9][12] = 0;
        map[10][12] = 0;

        map[7][13] = 0;
        map[8][13] = 0;
        map[9][13] = 0;




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
