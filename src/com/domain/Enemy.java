package com.domain;

import Stats.CharType;
import Stats.CollisionType;
import Stats.Direction;

import java.util.ArrayList;

public class Enemy extends Character {

    private int enemyID;

    public Enemy(int enemyID, CollisionType collisionType, int healthPoint, int health, int
            hitPoint, double speed, ArrayList<Item> inventory, int projectileCount,
                 int inventoryCapacity, ArrayList<Collidable> collisionList, CharType characterType,
                 double speedDuration, int score, Direction direction) {
        super(collisionType, healthPoint, health, hitPoint, speed, inventory, projectileCount,
                inventoryCapacity, collisionList, characterType, speedDuration, score, direction);
        this.enemyID = enemyID;
    }


    public int getEnemyID() {
        return enemyID;
    }
}
