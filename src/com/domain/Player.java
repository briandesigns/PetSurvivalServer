package com.domain;

import Stats.CharType;
import Stats.CollisionType;
import Stats.Direction;

import java.util.ArrayList;
public class Player extends Character {
    private int playerID;

    public Player(int playerID, CollisionType collisionType, int healthPoint, int health, int
            hitPoint, double speed, ArrayList<Item> inventory, int projectileCount,
                  int inventoryCapacity, ArrayList<Collidable> collisionList,
                  CharType characterType, double speedDuration, int score, Direction direction) {
        super(collisionType, healthPoint, health, hitPoint, speed, inventory, projectileCount,
                inventoryCapacity, collisionList, characterType, speedDuration, score, direction);
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return playerID;
    }
}
