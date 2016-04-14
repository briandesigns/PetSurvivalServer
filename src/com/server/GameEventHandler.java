package com.server;

import Stats.GameState;

import com.domain.Position;
import com.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Logger;

import io.netty.channel.Channel;

/**
 * this class will handle all the request / response logic and game protocol
 */
public class GameEventHandler {
    private GameManager gameManager;
    private static int playerIdCounter = 1;
    private static int playerRegistretionCounter = 0;

    public GameEventHandler(GameManager gameManager) {
        this.gameManager = gameManager;

    }

    /**
     * calls the appropriate routine when receiving a either login or play event from user
     * @param _jsonRequest
     * @param channel
     * @return
     */
    public int handleEvent(String _jsonRequest, Channel channel) {
        JSONObject jsonObject = new JSONObject(_jsonRequest);
        int event = jsonObject.getInt("event");
        int playerId = -1;
        if (event == GameState.LOGIN.ordinal()) {
            Player newPlayer = setPlayerNewAttributes("anyone", channel,
                    GameState.LOGIN_DONE.ordinal());
            setPlayerInPlayersContainer(newPlayer);
            playerId = newPlayer.getId();

            JSONObject jo1 = new JSONObject();
            jo1.put("event", GameState.LOGIN_DONE.ordinal());
            jo1.put("playerID", playerId);
            jo1.put("x", newPlayer.getPosition().x);
            jo1.put("y", newPlayer.getPosition().y);
            newPlayer.getPlayerJsonList().add(jo1.toString());


            for (Player p : this.gameManager.getPlayerList()) {
                if (p.getId() != newPlayer.getId()) {
                    JSONObject jo2 = new JSONObject();
                    jo2.put("event", GameState.SET_POSITION.ordinal());
                    jo2.put("playerID", newPlayer.getId());
                    jo2.put("x", newPlayer.getPosition().x);
                    jo2.put("y", newPlayer.getPosition().y);
                    p.getPlayerJsonList().add(jo2.toString());
                }
            }

        } else if (event == GameState.PLAY.ordinal()) {
            playerId = invokePlayEvent(jsonObject);
        } else if (event == GameState.LOADING_DONE.ordinal()) {
            Player player = this.gameManager.getPlayerByID(jsonObject.getInt("playerID"));
            playerId = player.getId();
            for (Player p : this.gameManager.getPlayerList()) {
                if (p.getId() != player.getId()) {
                    JSONObject jo = new JSONObject();
                    jo.put("event", GameState.SET_POSITION.ordinal());
                    jo.put("x", p.getPosition().x);
                    jo.put("y", p.getPosition().y);
                    jo.put("playerID", p.getId());
                    player.getPlayerJsonList().add(jo.toString());
                }

            }
        }
        return playerId;

    }

    private int invokePlayEvent(JSONObject jsonObject) {
        System.out.println("got to invokeplay");
        int activePlayerId = jsonObject.getInt("playerID");
        String move = jsonObject.getString("move");
        int[][] map = this.gameManager.getmap();
        Player activePlayer = this.gameManager.getPlayerByID(activePlayerId);
        Position activePlayerPosition = activePlayer.getPosition();

        switch (move) {
            case "up":
                if (activePlayerPosition.y == 0) {
                    //do nothing
                    activePlayer.direction = "up";
                } else if (map[activePlayerPosition.x][activePlayerPosition.y - 1] != 0) {
                    //do nothing
                    activePlayer.direction = "up";

                } else {
                    map[activePlayerPosition.x][activePlayerPosition.y - 1] = activePlayerId;
                    map[activePlayerPosition.x][activePlayerPosition.y] = 0;
                    activePlayerPosition.y -= 1;
                    activePlayer.direction = "up";
                    for (Player p : this.gameManager.getPlayerList()) {
                        JSONObject jo = new JSONObject();
                        jo.put("playerID", activePlayer.getId());
                        jo.put("event", GameState.PLAY_DONE.ordinal());
                        jo.put("move", "up");
                        p.getPlayerJsonList().add(jo.toString());
                    }
                }
                break;
            case "down":
                if (activePlayerPosition.y == 15) {
                    activePlayer.direction = "down";

                } else if (map[activePlayerPosition.x][activePlayerPosition.y + 1] != 0) {
                    activePlayer.direction = "down";

                } else {
                    map[activePlayerPosition.x][activePlayerPosition.y + 1] = activePlayerId;
                    map[activePlayerPosition.x][activePlayerPosition.y] = 0;
                    activePlayerPosition.y += 1;
                    activePlayer.direction = "down";
                    for (Player p : this.gameManager.getPlayerList()) {
                        JSONObject jo = new JSONObject();
                        jo.put("playerID", activePlayer.getId());
                        jo.put("event", GameState.PLAY_DONE.ordinal());
                        jo.put("move", "down");
                        p.getPlayerJsonList().add(jo.toString());
                        System.out.println("up is chosen");

                    }
                }
                break;
            case "left":
                if (activePlayerPosition.x == 0) {
                    activePlayer.direction = "left";
                } else if (map[activePlayerPosition.x - 1][activePlayerPosition.y] != 0) {
                    activePlayer.direction = "left";

                } else {
                    map[activePlayerPosition.x - 1][activePlayerPosition.y] = activePlayerId;
                    map[activePlayerPosition.x][activePlayerPosition.y] = 0;
                    activePlayer.direction = "left";
                    activePlayerPosition.x -= 1;
                    for (Player p : this.gameManager.getPlayerList()) {
                        JSONObject jo = new JSONObject();
                        jo.put("playerID", activePlayer.getId());
                        jo.put("event", GameState.PLAY_DONE.ordinal());
                        jo.put("move", "left");
                        p.getPlayerJsonList().add(jo.toString());
                    }

                }
                break;
            case "right":
                if (activePlayerPosition.x == 15) {
                    activePlayer.direction = "right";

                } else if (map[activePlayerPosition.x + 1][activePlayerPosition.y] != 0) {
                    activePlayer.direction = "right";

                } else {
                    map[activePlayerPosition.x + 1][activePlayerPosition.y] = activePlayerId;
                    map[activePlayerPosition.x][activePlayerPosition.y] = 0;
                    activePlayer.direction = "right";
                    activePlayerPosition.x += 1;
                    for (Player p : this.gameManager.getPlayerList()) {
                        JSONObject jo = new JSONObject();
                        jo.put("playerID", activePlayer.getId());
                        jo.put("event", GameState.PLAY_DONE.ordinal());
                        jo.put("move", "right");
                        p.getPlayerJsonList().add(jo.toString());
                    }
                }
                break;
            case "attack":
                String direction = activePlayer.direction;
                for (Player p : this.gameManager.getPlayerList()) {
                    JSONObject jo = new JSONObject();
                    jo.put("playerID", activePlayerId);
                    jo.put("event", GameState.PLAY_DONE.ordinal());
                    jo.put("move", "attack");
                    p.getPlayerJsonList().add(jo.toString());
                }
                switch (direction) {
                    case "up":
                        int opponentID = map[activePlayerPosition.x][activePlayerPosition.y - 1];
                        if (opponentID != 0) {
                            Player opponent = this.gameManager.getPlayerByID(opponentID);
                            opponent.setHealth(opponent.getHealth() - 10);
                            JSONObject jo = new JSONObject();
                            jo.put("playerID", opponentID);
                            jo.put("event", GameState.PLAY_DONE.ordinal());
                            jo.put("move", "health");
                            jo.put("health", opponent.getHealth());
                            opponent.getPlayerJsonList().add(jo.toString());
                            if (opponent.getHealth() <= 0) {
                                JSONObject jover = new JSONObject();
                                jover.put("event", GameState.DEATH.ordinal());
                                jover.put("playerID", opponentID);
                                for (Player p : this.gameManager.getPlayerList()) {
                                    p.getPlayerJsonList().add(jover.toString());
                                }
                            }
                        } else {
                            //do nothing
                        }
                        break;
                    case "down":
                        opponentID = map[activePlayerPosition.x][activePlayerPosition.y + 1];
                        if (opponentID != 0) {
                            Player opponent = this.gameManager.getPlayerByID(opponentID);
                            opponent.setHealth(opponent.getHealth() - 10);
                            JSONObject jo = new JSONObject();
                            jo.put("playerID", opponentID);
                            jo.put("event", GameState.PLAY_DONE.ordinal());
                            jo.put("move", "health");
                            jo.put("health", opponent.getHealth());
                            opponent.getPlayerJsonList().add(jo.toString());
                            if (opponent.getHealth() <= 0) {
                                JSONObject jover = new JSONObject();
                                jover.put("event", GameState.DEATH.ordinal());
                                jover.put("playerID", opponentID);
                                for (Player p : this.gameManager.getPlayerList()) {
                                    p.getPlayerJsonList().add(jover.toString());
                                }
                            }
                        } else {
                            //do nothing
                        }
                        break;
                    case "left":
                        opponentID = map[activePlayerPosition.x - 1][activePlayerPosition.y];
                        if (opponentID != 0) {
                            Player opponent = this.gameManager.getPlayerByID(opponentID);
                            opponent.setHealth(opponent.getHealth() - 10);
                            JSONObject jo = new JSONObject();
                            jo.put("playerID", opponentID);
                            jo.put("event", GameState.PLAY_DONE.ordinal());
                            jo.put("move", "health");
                            jo.put("health", opponent.getHealth());
                            opponent.getPlayerJsonList().add(jo.toString());
                            if (opponent.getHealth() <= 0) {
                                JSONObject jover = new JSONObject();
                                jover.put("event", GameState.DEATH.ordinal());
                                jover.put("playerID", opponentID);
                                for (Player p : this.gameManager.getPlayerList()) {
                                    p.getPlayerJsonList().add(jover.toString());
                                }
                            }
                        } else {
                            //do nothing
                        }
                        break;
                    case "right":
                        opponentID = map[activePlayerPosition.x + 1][activePlayerPosition.y];
                        if (opponentID != 0) {
                            Player opponent = this.gameManager.getPlayerByID(opponentID);
                            opponent.setHealth(opponent.getHealth() - 10);
                            JSONObject jo = new JSONObject();
                            jo.put("playerID", opponentID);
                            jo.put("event", GameState.PLAY_DONE.ordinal());
                            jo.put("move", "health");
                            jo.put("health", opponent.getHealth());
                            opponent.getPlayerJsonList().add(jo.toString());
                            if (opponent.getHealth() <= 0) {
                                JSONObject jover = new JSONObject();
                                jover.put("event", GameState.DEATH.ordinal());
                                jover.put("playerID", opponentID);
                                for (Player p : this.gameManager.getPlayerList()) {
                                    p.getPlayerJsonList().add(jover.toString());
                                }
                            }
                        } else {
                            //do nothing
                        }
                        break;
                }
                break;
        }
        return activePlayerId;

    }


    private Player setPlayerNewAttributes(String userName, Channel channel, int nextEvent) {
        Player newPlayer = new Player(channel);
        newPlayer.setUserName(userName);
        int id = GenerateUniqueId();
        int count = getPlayerRegistrationCounter();
        newPlayer.setRegistertionNum(count);
        newPlayer.setId(id);
        return newPlayer;
    }

    private void setPlayerInPlayersContainer(Player player) {
        player.setPosition(this.gameManager.findFreePosition());
        this.gameManager.map[player.getPosition().x][player.getPosition().y] = player.getId();
        this.gameManager.getPlayerList().add(player);
    }


    private int GenerateUniqueId() {
        int id = this.playerIdCounter;
        this.playerIdCounter++;
        return id;
    }

    private int getPlayerRegistrationCounter() {
        int count = this.playerRegistretionCounter;
        this.playerRegistretionCounter++;
        return count;
    }

}
