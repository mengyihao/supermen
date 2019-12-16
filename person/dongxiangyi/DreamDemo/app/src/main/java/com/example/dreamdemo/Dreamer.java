package com.example.dreamdemo;

public class Dreamer {
    private boolean right;
    private boolean left;
    private boolean fight;
    private boolean sprint; //冲刺
    private int hp;
    private int strength;
    private int attackTime;
    private int attackDistance;

    public boolean isRight() {
        return this.right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return this.left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isFight() {
        return this.fight;
    }

    public void setFight(boolean fight) {
        this.fight = fight;
    }

    public boolean isSprint() {
        return this.sprint;
    }

    public void setSprint(boolean sprint) {
        this.sprint = sprint;
    }

    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getStrength() {
        return this.strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAttackTime() {
        return this.attackTime;
    }

    public void setAttackTime(int attackTime) {
        this.attackTime = attackTime;
    }

    public int getAttackDistance() {
        return this.attackDistance;
    }

    public void setAttackDistance(int attackDistance) {
        this.attackDistance = attackDistance;
    }
    public Dreamer(int hp,int strength,int attackTime,int attackDistance){
        this.right = false;
        this.left = false;
        this.fight = false;
        this.sprint = false;
        this.hp = hp;
        this.strength = strength;
        this.attackTime = attackTime;
        this.attackDistance = attackDistance;
    }
}
