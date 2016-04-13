package com.server;

import Stats.GameState;

import com.json.JSONObject;

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
     * respond to the user after event is handled
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
        int activePlayerId = jsonObject.getInt("id");
        int currentPlayerID = this.gameManager.getPlayerList().get(activePlayerId).getActiveplayerid();

        return activePlayerId;
    }

    private String CardsArrayToString(String[] cardsPrev, String[] cardsCurrent) {
        String result = "";
        for (String s : cardsPrev) {
            //Do your stuff here
            result += s;
            result += "_";

        }
        for (String s : cardsCurrent) {
            //Do your stuff here
            result += s;
            result += "_";

        }
        result = result.substring(0, result.length() - 1);
        return result;
    }

    private String[] getWarCards(int playerID) {

        String prevPlayerCardId_1 = this.gameManager.getPlayerByIndex(playerID).getPlayerCards().getFirst();
        this.gameManager.getPlayerByIndex(playerID).getPlayerCards().removeFirst();
        String prevPlayerCardId_2 = this.gameManager.getPlayerByIndex(playerID).getPlayerCards().getFirst();
        this.gameManager.getPlayerByIndex(playerID).getPlayerCards().removeFirst();
        String prevPlayerCardId_3 = this.gameManager.getPlayerByIndex(playerID).getPlayerCards().getFirst();
        this.gameManager.getPlayerByIndex(playerID).getPlayerCards().removeFirst();
        //the fourth card is to play the war
        String prevPlayerCardId_4 = this.gameManager.getPlayerByIndex(playerID).getPlayerCards().getFirst();
        this.gameManager.getPlayerByIndex(playerID).getPlayerCards().removeFirst();

        return new String[]{prevPlayerCardId_1, prevPlayerCardId_2, prevPlayerCardId_3, prevPlayerCardId_4};
    }

    private int getPreviousePlayerIndex(int _currentPlayerID) {
        //find out who is the previous player
        int playerInx = this.gameManager.getPlayerIndexByKey(_currentPlayerID);
        if (playerInx == 0) {
            int playerSize = this.gameManager.getPlayers().size();
            playerInx = playerSize - 1;
        } else {
            --playerInx;
        }
        return playerInx;
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

    private void setPlayerCards(Player _player) {
        //this is only good for 2 players
        int len = this.gameManager.getCardsRandomize().length - 1;
        if (_player.getId() == 0) {
            for (int i = 0; i < (len / 2); i++) {
                _player.getPlayerCards().push(this.gameManager.getCardsRandomizeByIndex(i));
            }
        } else if (_player.getId() == 1) {
            for (int i = len; i > (len / 2); i--) {
                _player.getPlayerCards().push(this.gameManager.getCardsRandomizeByIndex(i));
            }
        }


    }


    private void setNewPlayerCardId(Player _player) {
        String cardId = _player.getPlayerCards().removeFirst();
        _player.setActivecardid(cardId);
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
