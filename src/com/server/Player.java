package com.server;

import java.util.ArrayList;

import io.netty.channel.Channel;

/**
 * player class that holds client information as well as the player character info on the map
 */
public class Player {

    private int health = 100;
    private Position position = new Position(0, 0);
    public String direction;
    private String userName;
    private Channel channel;
    public ArrayList<String> jsonList = new ArrayList<String>();
    private int id;
    private int registertionNum;

    public int getHealth() {
        return health;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Player(Channel _channel) {
        this.channel = _channel;
    }


    public ArrayList<String> getPlayerJsonList() {
        return this.jsonList;
    }


    public Channel getChannel() {
        return channel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setRegistertionNum(int registertionNum) {
        this.registertionNum = registertionNum;
    }


}
