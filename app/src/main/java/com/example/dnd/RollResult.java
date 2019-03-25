package com.example.dnd;

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

    private String attackName;
    private Integer hit;
    private Integer hitModifier;
    private Integer hitResult;
    private Integer damageResult;
    private List<Integer> damages;
    private Integer damageModifier;
    private Integer totalDamage;
    private Boolean canDamage;

    /**
     * Default constructor
     */
    public RollResult(){

        attackName = null;
        hit = 0;
        hitModifier = 0;
        hitResult = 0;
        damageResult = 0;
        damages = new ArrayList<>();
        damageModifier = 0;
        totalDamage = 0;
        canDamage = false;
    }


    /**
     * Calculates the damage result total by summing all the damages from the roll
     *
     * @param damageModifier Damage modifier for the attack
     * @author Atsuko Takanabe
     *
     */
    public void calculateDamageResult(Integer damageModifier){

        this.damageModifier = damageModifier;

        for (Integer damage : damages){
            Log.i("Damage", damage.toString());
            damageResult += damage;
        }

    }

    /**
     * Calcuates the damage total by adding damage result total and damage modifier
     *
     * @return The sum of damage result and damage modifier
     * @author Atsuko Takanabe
     */
    public Integer getFinalDamage(){
        totalDamage = Math.addExact(damageResult, damageModifier);
        return totalDamage;
    }


    /**
     * Calculates the hit result by adding hit and hit modifier
     *
     * @param hit
     * @param hitModifier
     * @author Atsuko Takanabe
     */
    public void calculateHitResult(Integer hit, Integer hitModifier) {

        this.hit = hit;
        this.hitModifier = hitModifier;
        this.hitResult = Math.addExact(hit, hitModifier);
    }


    /*************Setter and Getter***********************/

    public String getAttackName() {
        return attackName;
    }

    public void setAttackName(String attackName) {
        this.attackName = attackName;
    }

    public Integer getHit(){
        return hit;
    }

    public void setHit(Integer hit){
        this.hit = hit;
    }


    public Integer getHitModifier(){
        return hitModifier;
    }

    public void setHitModifier(Integer hitModifier){
        this.hitModifier = hitModifier;
    }


    public Integer getHitResult() {
        return hitResult;
    }

    public void setHitResult(Integer hitResult) {
        this.hitResult = hitResult;
    }

    public Integer getDamageResult() {
        return damageResult;
    }

    public void setDamageResult(Integer result){
        damageResult = result;
    }


    public List<Integer> getDamages() {
        return damages;
    }

    public void setDamages(List<Integer> damages){
        this.damages = damages;
    }


    public Boolean getCanDamage(){
        return canDamage;
    }


    public void setCanDamage(Boolean x){
        this.canDamage = x;
    }


    public Integer getDamageModifier(){
        return damageModifier;
    }

    public void setDamageModifier(Integer damageModifier){
        this.damageModifier = damageModifier;
    }





}
