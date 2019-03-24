package com.example.dnd;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RollResult {

    String attackName;
    Integer hit;
    Integer hitModifier;
    Integer hitResult;
    Integer damageResult;
    List<Integer> damages;
    Integer damageModifier;
    Integer totalDamage;
    Boolean canRollDamage;


    public RollResult(){

        attackName = null;
        hit = 0;
        hitModifier = 0;
        hitResult = 0;
        damageResult = 0;
        damages = new ArrayList<>();
        damageModifier = 0;
        totalDamage = 0;
        canRollDamage = false;
    }

    public void calculateDamageResult(Integer damageModifier){

        this.damageModifier = damageModifier;

        for (Integer damage : damages){
            Log.i("Damage", damage.toString());
            damageResult += damage;
        }

    }

    public Integer getTotalDamage(){
        totalDamage = Math.addExact(damageResult, damageModifier);
        return totalDamage;
    }

    public String getAttackName() {
        return attackName;
    }

    public void setAttackName(String attackName) {
        this.attackName = attackName;
    }

    public Integer getHitResult() {
        return hitResult;
    }

    public void setHitResult(Integer hit, Integer hitModifier) {

        this.hit = hit;
        this.hitModifier = hitModifier;
        this.hitResult = Math.addExact(hit, hitModifier);
    }

    public List<Integer> getDamages() {
        return damages;
    }

    public Integer getDamageResult() {
        return damageResult;
    }

    public Boolean getCanRollDamage(){
        return canRollDamage;
    }

    public void setCanRollDamage(Boolean can){
        this.canRollDamage = can;
    }

    public Integer getHitModifier(){
        return hitModifier;
    }

    public Integer getHit(){
        return hit;
    }

    public Integer getDamageModifier(){
        return damageModifier;
    }


}
