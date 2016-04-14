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
    private final static Logger LOG = LoggerManager.GetLogger(GameEventHandler.class.getName());
    private GameManager gameManager;
    private static int playerIdCounter = 0;
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
        String userName = jsonObject.getString("username");
        if (event == GameState.LOGIN.ordinal()) {
            Player newPlayer = setPlayerNewAttributes(userName, channel,
                    GameState.LOGIN_DONE.ordinal());
            setPlayerInPlayersContainer(newPlayer);
            playerId = newPlayer.getId();
        } else if (event == GameState.PLAY.ordinal()) {
            playerId = invokePlayEvent(jsonObject);
        }
        return playerId;

    }


    /**
     * just sets a response string for the player
     * @param _playerId
     * @param _jsonRequest
     * @return
     */
    public boolean ResponseDispatcher(int _playerId, String _jsonRequest) {
        JSONObject jsonObject = new JSONObject(_jsonRequest);
        int Event = jsonObject.getInt("event");
        boolean bDone = false;
        if (Event == GameState.LOGIN.ordinal()) {
            bDone = this.gameManager.getGameResponseDispatcher().ResponseDispatcheLoginDone(_playerId);
        } else if (Event == GameState.PLAY.ordinal()) {
            bDone = this.gameManager.getGameResponseDispatcher().ResponseDispatchePlayDone(_playerId);
        }
        return bDone;
    }


    private int invokePlayEvent(JSONObject jsonObject) {
        int activePlayerId = jsonObject.getInt("playerID");
        String move = jsonObject.getString("move");
        int[][] map = this.gameManager.getmap();
        Player activePlayer = this.gameManager.getPlayerByID(activePlayerId);
        Position activePlayerPosition = activePlayer.getPosition();

        synchronized (this.gameManager.map) {
            switch (move) {
                case "up":
                    if (activePlayerPosition.y == 0) {
                        //do nothing
                    } else if (map[activePlayerPosition.x][activePlayerPosition.y - 1] != 0) {
                        //do nothing
                    } else {
                        map[activePlayerPosition.x][activePlayerPosition.y - 1] = activePlayerId;
                        map[activePlayerPosition.x][activePlayerPosition.y] = 0;
                        activePlayer.direction = "up";
                        for (Player p : this.gameManager.getPlayerList()) {
                            JSONObject jo = new JSONObject();
                            jo.put("playerID", activePlayer);
                            jo.put("event", GameState.PLAY);
                            jo.put("move", "up");
                            p.getPlayerJsonList().add(jo.toString());
                        }
                    }
                    break;
                case "down":
                    if (activePlayerPosition.y == 15) {
                        //do nothing
                    } else if (map[activePlayerPosition.x][activePlayerPosition.y + 1] != 0) {
                        //do nothing
                    } else {
                        map[activePlayerPosition.x][activePlayerPosition.y + 1] = activePlayerId;
                        map[activePlayerPosition.x][activePlayerPosition.y] = 0;
                        activePlayer.direction = "down";
                        for (Player p : this.gameManager.getPlayerList()) {
                            JSONObject jo = new JSONObject();
                            jo.put("playerID", activePlayer);
                            jo.put("event", GameState.PLAY);
                            jo.put("move", "down");
                            p.getPlayerJsonList().add(jo.toString());
                        }
                    }
                    break;
                case "left":
                    if (activePlayerPosition.x == 0) {
                        //do nothing
                    } else if (map[activePlayerPosition.x - 1][activePlayerPosition.y] != 0) {
                        //do nothing
                    } else {
                        map[activePlayerPosition.x - 1][activePlayerPosition.y] = activePlayerId;
                        map[activePlayerPosition.x][activePlayerPosition.y] = 0;
                        activePlayer.direction = "left";
                        for (Player p : this.gameManager.getPlayerList()) {
                            JSONObject jo = new JSONObject();
                            jo.put("playerID", activePlayer);
                            jo.put("event", GameState.PLAY);
                            jo.put("move", "left");
                            p.getPlayerJsonList().add(jo.toString());
                        }

                    }
                    break;
                case "right":
                    if (activePlayerPosition.x == 15) {
                        //do nothing
                    } else if (map[activePlayerPosition.x + 1][activePlayerPosition.y] != 0) {
                        //do nothing
                    } else {
                        map[activePlayerPosition.x + 1][activePlayerPosition.y] = activePlayerId;
                        map[activePlayerPosition.x][activePlayerPosition.y] = 0;
                        activePlayer.direction = "right";
                        for (Player p : this.gameManager.getPlayerList()) {
                            JSONObject jo = new JSONObject();
                            jo.put("playerID", activePlayer);
                            jo.put("event", GameState.PLAY);
                            jo.put("move", "right");
                            p.getPlayerJsonList().add(jo.toString());
                        }
                    }
                    break;
                case "attack":
                    String direction = activePlayer.direction;
                    for(Player p: this.gameManager.getPlayerList()) {
                        JSONObject jo = new JSONObject();
                        jo.put("playerID", activePlayerId);
                        jo.put("event", GameState.PLAY);
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
                                jo.put("event", GameState.PLAY);
                                jo.put("move", "health");
                                jo.put("health", opponent.getHealth());
                                opponent.getPlayerJsonList().add(jo.toString());

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
                                jo.put("event", GameState.PLAY);
                                jo.put("move", "health");
                                jo.put("health", opponent.getHealth());
                                opponent.getPlayerJsonList().add(jo.toString());
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
                                jo.put("event", GameState.PLAY);
                                jo.put("move", "health");
                                jo.put("health", opponent.getHealth());
                                opponent.getPlayerJsonList().add(jo.toString());
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
                                jo.put("event", GameState.PLAY);
                                jo.put("move", "health");
                                jo.put("health", opponent.getHealth());
                                opponent.getPlayerJsonList().add(jo.toString());
                            } else {
                                //do nothing
                            }
                            break;
                    }
                    break;
            }
            return activePlayerId;
        }

    }


    private Player setPlayerNewAttributes(String userName, Channel channel, int nextEvent) {
        Player newPlayer = new Player(channel);
        newPlayer.setUserName(userName);
        int id = GenerateUniqueId();
        int count = getPlayerRegistrationCounter();
        newPlayer.setRegistertionNum(count);
        newPlayer.setId(id);
        newPlayer.setEvent(nextEvent);
        return newPlayer;
    }

    private void setPlayerInPlayersContainer(Player player) {
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
