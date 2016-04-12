package com.domain;

import Stats.CharType;
import Stats.CollisionType;
import Stats.Direction;

import java.util.ArrayList;

public class Character implements Collidable {
    public static final int STARTING_SCORE = 0;
    private CollisionType collisionType;
    private int healthPoint;
    private int health;
    private int hitPoint;
    private double speed;
    private ArrayList<Item> inventory;
    private int projectileCount;
    private int inventoryCapacity;
    private ArrayList<Collidable> collisionList;
    private CharType characterType;
    private double speedDuration;
    private int score;
    private Direction direction;
    public Position position;

    public Character(CollisionType collisionType, int healthPoint, int health,
                     int hitPoint, double speed, ArrayList<Item> inventory, int projectileCount,
                     int inventoryCapacity, ArrayList<Collidable> collisionList, CharType characterType,
                     double speedDuration, int score, Direction direction) {
        this.collisionType = collisionType;
        this.healthPoint = healthPoint;
        this.health = health;
        this.hitPoint = hitPoint;
        this.speed = speed;
        this.inventory = inventory;
        this.projectileCount = projectileCount;
        this.inventoryCapacity = inventoryCapacity;
        this.collisionList = collisionList;
        this.characterType = characterType;
        this.speedDuration = speedDuration;
        this.score = score;
        this.direction = direction;
    }



    public CollisionType getCollisionType() {
        return collisionType;
    }

    public void setCollisionType(CollisionType collisionType) {
        this.collisionType = collisionType;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint += healthPoint;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health += health;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint += hitPoint;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed += speed;
    }

    public int getProjectileCount() {
        return projectileCount;
    }

    public void setProjectileCount(int projectileCount) {
        this.projectileCount += projectileCount;
    }

    public double getSpeedDuration() {
        return speedDuration;
    }

    public void setSpeedDuration(double speedDuration) {
        this.speedDuration += speedDuration;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }


}
