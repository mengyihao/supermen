package com.game.game;

import android.media.Image;
import android.widget.ImageView;

public class Monster {
    private ImageView monster;
    //怪物血量
    private int monsterHp=5;
    //方向
    private boolean monsterDirection = false;
    //是否正在攻击
    private boolean monsterAttack = false;
    //攻击力
    private int monsterStrength=1;

    public int getMonsterHp() {
        return monsterHp;
    }

    public void setMonsterHp(int monsterHp) {
        this.monsterHp = monsterHp;
    }

    public boolean isMonsterDirection() {
        return monsterDirection;
    }

    public void setMonsterDirection(boolean monsterDirection) {
        this.monsterDirection = monsterDirection;
    }

    public boolean isMonsterAttack() {
        return monsterAttack;
    }

    public void setMonsterAttack(boolean monsterAttack) {
        this.monsterAttack = monsterAttack;
    }

    public int getMonsterStrength() {
        return monsterStrength;
    }

    public void setMonsterStrength(int monsterStrength) {
        this.monsterStrength = monsterStrength;
    }


    //获取怪物
    public void setMonster(ImageView v){
        this.monster = v;
    }
    public ImageView getMonster(){
        return this.monster;
    }

}
