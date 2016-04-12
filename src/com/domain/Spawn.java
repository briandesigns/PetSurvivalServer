package com.domain;

import Stats.CharType;
import Stats.CollisionType;
import Stats.Direction;
import Stats.EnemyStats;
import Stats.SpawnType;

import java.util.ArrayList;

public class Spawn implements Collidable {

    private int spawnID;
    private CollisionType collisionType;
    private int healthPoint;
    private int health;
    private int capacity;
    private SpawnType spawnType;
    public Position position;
    private ArrayList<Character> enemyList;

    public Spawn(int spawnID, CollisionType collisionType, int healthPoint, int health,
                 int capacity, SpawnType spawnType, ArrayList<Character> enemyList) {
        this.spawnID = spawnID;
        this.collisionType = collisionType;
        this.healthPoint = healthPoint;
        this.health = health;
        this.capacity = capacity;
        this.spawnType = spawnType;
        this.enemyList = enemyList;
        initEnemies();
    }

    private void initEnemies() {
        for (int i = 0; i < this.capacity; i++) {
            switch (this.spawnType) {
                case can:
                    this.enemyList.add(
                            new Enemy(i, CollisionType.enemy,
                                    (int) EnemyStats.baseHealthPoint.getValue(),
                                    (int) EnemyStats.baseHealth.getValue(),
                                    (int) EnemyStats.baseHitPoint.getValue(),
                                    EnemyStats.baseSpeed.getValue(),
                                    new ArrayList<Item>(), 0,
                                    (int) EnemyStats.inventoryCapacity.getValue(),
                                    new ArrayList<Collidable>(),
                                    CharType.can, EnemyStats.baseSpeedDuration.getValue(),
                                    Character.STARTING_SCORE, Direction.down
                            ));
                    break;
                case dryer:
                    break;
                case vacuum:
                    break;
                case hydrant:
                    break;
            }
        }
    }

    public int getSpawnID() {
        return spawnID;
    }

    public CollisionType getCollisionType() {
        return collisionType;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public int getHealth() {
        return health;
    }

    public int getCapacity() {
        return capacity;
    }

    public SpawnType getSpawnType() {
        return spawnType;
    }

    public void updateHealth(int health) {
        this.health += health;
    }
}
