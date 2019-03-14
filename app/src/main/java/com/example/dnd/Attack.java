package com.example.dnd;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.dnd.data.AttackContract;
import com.example.dnd.data.AttackDatabaseHelper;
import com.example.dnd.data.CharacterAttacksContract;
import com.example.dnd.data.CharacterContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Integer modHit;
    private Integer modDamage;
    private String tag = "Attack";

    Attack(Context context){
        dice = new ArrayList<>();
        dbHelper = new AttackDatabaseHelper(context);
        modHit = null;
        modDamage = null;
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
          
        }catch(SQLiteException e){
            e.printStackTrace();
            Log.e(tag, "addAttack method failed");
        }
        setId(newRowId);

    }

    public void deleteAttack(Integer attackId){

        try{
            dbHelper.deleteAttack(attackId);
        }catch (SQLiteException e){
            e.printStackTrace();
            Log.e(tag, "deleteAttack method failed");
        }
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

    public void setAttack(Integer id){
        Map<String, String> attack = dbHelper.getAttackDetails(id);
        this.id = id;
        name = attack.get(AttackContract.getAttackNameColName());
        modDamage = Integer.parseInt(attack.get(AttackContract.getDamageModifierColName()));
        modHit = Integer.parseInt(attack.get(AttackContract.getHitModifierColName()));
    }


}
