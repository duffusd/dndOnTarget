package com.example.dnd;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * <H>RollResult</H>
 *
 * The purpose of this class is to store the necessary details from rolling the attack so the result
 * can be displayed easily
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
     * Default constructor
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
     * Calculates the damage result total by summing all the damages from the roll
     *
     * @author Atsuko Takanabe
     *
     */
    public void calculateDamageResult(){

        for (int i = 0; i < attack.getNumOfDice(); i++){

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
