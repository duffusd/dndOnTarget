package com.example.dnd;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * <H>RollResult</H>
 *
 * The purpose of this class is to store the roll results
 * @author Atsuko Takanabe
 */
public class RollResult {

    private Integer hit;
    private Integer hitResult;
    private Integer damageResult;
    private List<Integer> damages;
    private Integer totalDamage;
    private Boolean canDamage;
    private Attack attack;


    /**
     * Non-default constructor
     */
    public RollResult(Attack attack){

        this.attack = new Attack(attack);
        hit = 0;
        hitResult = 0;
        damageResult = 0;
        damages = new ArrayList<>();
        totalDamage = 0;
        canDamage = false;
    }

    /**
     * Calculates the damage result by adding the sum of all the damages and the damage modifier
     *
     * @author Atsuko Takanabe, Aaron Lee
     *
     */
    public void calculateDamageResult(int crit){

        int totalNumberOfDice = 0;

        if (crit ==20)

            totalNumberOfDice = attack.getNumOfDice() * 2;

        else

            totalNumberOfDice = attack.getNumOfDice();

        for (int i = 0; i < totalNumberOfDice; i++){

            Integer damage = attack.rollDamage();
            damages.add(damage);
            damageResult += damage;
        }

        totalDamage = Math.addExact(damageResult, attack.getModDamage());
    }


    /**
     * Calculates the hit result by adding hit and hit modifier
     *
     * @author Atsuko Takanabe
     */
    public void calculateHitResult() {

        hit = attack.rollHit();
        this.hitResult = Math.addExact(hit, attack.getModHit());
    }

    /*************Setter and Getter***********************/

    public Integer getHit(){
        return hit;
    }

    public Integer getHitResult() {
        return hitResult;
    }

    public List<Integer> getDamages() {
        return damages;
    }

    public Boolean getCanDamage(){
        return canDamage;
    }

    public Attack getAttack(){
        return attack;
    }

    public Integer getTotalDamage(){
        return totalDamage;
    }

    public void setCanDamage(Boolean x){
        this.canDamage = x;
    }
}
