package com.server;

import com.domain.Position;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Logger;

import io.netty.channel.Channel;

public class Player {
    private final static Logger LOG = LoggerManager.GetLogger(GameServerMain.class.getName());
    private int health = 100;
    private Position position = new Position(0, 0);
    public String direction;
    private String userName;
    //Session channel
    private Channel channel;
    //Player Json massage
    public ArrayList<String> jsonList = new ArrayList<String>();
    //the player which is active and has the turn
    private int id;
    private int registertionNum;
    //mark the end game the value will be the winner id

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
