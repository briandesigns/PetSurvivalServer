package com.server;

/**
 * the different states of communication between client and server
 */
public enum GameState {
    PLACE_HOLDER, HANDSHAKE_COMPLETE_SUCCESS, LOGIN, LOGIN_DONE, NEW_USER_LOGIN_DONE, PLAY,
    PLAY_DONE, UPDATE_HEALTH,SET_POSITION, DEATH, LOADING_DONE;
}

