package com.example.dnd;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.dnd.data.AttackDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * <h>Attack Class</h>
 *
 * This entity will contain the information and characteristics of an attack.
 *
 */

public class Attack {
    private Integer id;
    private String name;
    private List<Die> dice;
    private AttackDatabaseHelper dbHelper;

    private int modHit;
    private int modDamage;

    Attack(Context context){
        dice = new ArrayList<>();
        dbHelper = new AttackDatabaseHelper(context);
    }

    Attack(Integer attackId){
        dice = new ArrayList<>();
        this.id = attackId;
    }

    Attack(String name) {
        dice = new ArrayList<>();
        this.name = name;
    }

    Attack(String name, int modHit, int modDamage, int diceId) {
        dice = new ArrayList<>();
        this.name = name;
        this.modHit = modHit;
        this.modDamage = modDamage;
    }

    //TODO: implement a rollHit() method somewhere here or in Activity and getHit() from here.

    /**
     * This method will roll each of the dice in {@link Attack#dice} and add the damage modifier
     * {@link Attack#modDamage} to the sum of the dice rolls.
     *
     * @return the sum of the dice rolls and the damage modifier
     */
    public int rollDamage() {
        int damage = modDamage;

        for (Die d : dice) {
            damage += d.roll();
        }

        return damage;
    }

    public void addAttack(String attackName, Integer hitModifier, Integer damageModifier, Integer diceId){

        Integer newRowId = null;
        setName(attackName);
        setModHit(hitModifier);
        setModDamage(damageModifier);

        try{

            newRowId = dbHelper.addAttack(attackName, hitModifier, damageModifier, diceId);
            dbHelper.close();

        }catch(SQLiteException e){
            e.printStackTrace();
            Log.e("Attack", "addAttack method failed");
        }
        setId(newRowId);



    }

    /********************************** GETTER AND SETTERS ***************************************/

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getModHit() {
        return modHit;
    }

    public int getModDamage() {
        return modDamage;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModHit(int modHit) {
        this.modHit = modHit;
    }

    public void setModDamage(int modDamage) {
        this.modDamage = modDamage;
    }

}
