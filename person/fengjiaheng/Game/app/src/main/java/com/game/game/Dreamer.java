package com.game.game;

import android.widget.ImageView;

public class Dreamer {
    private boolean directionLeft = false; //主角方向
    private boolean directionRight = true;
    //主角面朝那边  向左为false 向右为true
    private boolean dreamerDirection = true;
    //子弹发射
    private boolean attack = true;
    //攻击力
    private int strength=1;

    //血量
    private int hp = 3;
    //跳跃
    public boolean jump = false;

    private ImageView dreamer;
    //获取主角的图片
    public void setDreamer(ImageView dreamer){
        this.dreamer = dreamer;
    }

    public ImageView getDreamer() {
        return dreamer;
    }

    public boolean isDirectionLeft() {
        return directionLeft;
    }

    public void setDirectionLeft(boolean directionLeft) {
        this.directionLeft = directionLeft;
    }

    public boolean isDirectionRight() {
        return directionRight;
    }

    public void setDirectionRight(boolean directionRight) {
        this.directionRight = directionRight;
    }

    public boolean isDreamerDirection() {
        return dreamerDirection;
    }

    public void setDreamerDirection(boolean dreamerDirection) {
        this.dreamerDirection = dreamerDirection;
    }

    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }


}
