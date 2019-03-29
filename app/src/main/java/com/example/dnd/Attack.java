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
import com.example.dnd.data.CharacterAttacksDatabaseHelper;
import com.example.dnd.data.CharacterContract;
import com.example.dnd.data.DiceDatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <h>Attack Class</h>
 *
 * This entity will contain the information and characteristics of an attack.
 *
 */
public class Attack {

    private Integer id;
    private String name;
    private Die die;
    private AttackDatabaseHelper dbHelper;
    private Integer modHit;
    private Integer modDamage;
    private String tag = "Attack";
    private Context context;
    private Integer diceId;
    private Integer numOfDice;


    /**
     * Non-Default constructor
     * @param context
     */
    Attack(Context context){
        dbHelper = new AttackDatabaseHelper(context);
        this.context = context;
        modHit = null;
        modDamage = null;
        diceId = null;
        numOfDice = 0;
        die = null;
    }

    /**
     * Copy constructor
     *
     * @param attack
     */
    Attack(Attack attack){
        id = attack.id;
        name = attack.name;
        context = attack.context;
        dbHelper = attack.dbHelper;
        modHit = attack.modHit;
        modDamage = attack.modDamage;
        diceId = attack.diceId;
        numOfDice = attack.numOfDice;
        die = attack.die;


    }

    /**
     * Generates a random number with a range between 1 and 20
     *
     * @return Random number between 1 and 20
     */
    public Integer rollHit(){

        Integer hit = ThreadLocalRandom.current().nextInt(1,21);
        Log.i(tag, String.format("Hit: %d", hit));

        return hit;
    }


    /**
     * This method will roll each of the dice for damage
     *
     * @return The result from rolling for damage
     * @author Justin Parry, Atsuko Takanabe
     */
    public int rollDamage() {
                 
        Integer damage = die.rollDamage();
        Log.i(tag, String.format("Damage: %d", damage));

        return damage;
    }



    /**
     * Adds a new attack to the backend database
     *
     * @param attackName New attack name
     * @param hitModifier Hit modifier value of the new attack
     * @param damageModifier Damage modifier value of the new attack
     * @param diceId Dice ID for the new attack
     * @param numOfDice Number of Dice for the new attack
     * @return The Attack ID of the newly created attack
     * @exception SQLiteException
     * @author Atsuko Takanabe
     */
    public Integer addAttack(String attackName, Integer hitModifier, Integer damageModifier, Integer diceId, Integer numOfDice){
      
      Integer newRowId = null;

        try{

            newRowId = dbHelper.addAttack(attackName, hitModifier, damageModifier, diceId, numOfDice);
          
        }catch(SQLiteException e){
            e.printStackTrace();
            Log.e(tag, "addAttack method failed");
        }

        return newRowId;
    }

    /**
     * Updates the name of the existing attack
     *
     * @param newName The new name to update with
     * @exception SQLiteException
     * @author Atsuko Takanabe
     */
    public void updateName(String newName){

        try{
            dbHelper.updateName(id, newName);
        }catch (SQLiteException e){
            e.printStackTrace();
            Log.e(tag, "Updating an attack name failed");
        }

        name = newName;
    }

    /**
     * Updates the value of hit modifier of the existing attack
     *
     * @param hit New hitModifier value
     */
    public void updateHitModifier(Integer hit){

        try{
            dbHelper.updateHitModifier(id, hit);
        } catch(SQLiteException e){
            e.printStackTrace();
            Log.e(tag, "Updating hit modifier failed");
        }

        modHit = hit;
    }

    /**
     * Updates the value of damage modifier of the existing attack
     *
     * @param damage New value for damage
     * @exception SQLiteException
     * @author Atsuko Takanabe
     */

    public void updateDamageModifier(Integer damage){

        try{
            dbHelper.updateDamageModifier(id, damage);
        }catch (SQLiteException e){
            e.printStackTrace();
            Log.e(tag, "Updating damage modifier failed");
        }

        modDamage = damage;
    }


    /**
     * Updates the diceID of the existing attack
     *
     * @param diceId New dieId
     * @exception SQLiteException
     * @author Atsuko Takanabe
     */
    public void updateDiceID(Integer diceId){

        try{
            dbHelper.updateDiceId(id, diceId);
        }catch (SQLiteException e){
            e.printStackTrace();
            Log.e(tag, "Updating diceID failed");
        }

        this.diceId = diceId;
        die.setDieId(diceId);
    }

    /**
     * Updates the number of dice of the existing attack
     *
     * @param numOfDie New number of dice
     * @exception SQLiteException
     * @author Atsuko Takanabe
     */
    public void updateNumOfDie(Integer numOfDie){

        try{
            dbHelper.updateNumOfDie(id, numOfDie);
        }catch (SQLiteException e){
            e.printStackTrace();
            Log.e(tag, "Updating the number of die/dice failed");
        }

        this.numOfDice = numOfDie;
    }

    /**
     * Deletes an attack from the attack table and character-attack table
     *
     * @param attackId Attack ID to delete
     * @exception SQLiteException
     * @author Atsuko Takanabe
     */
    public void deleteAttack(Integer attackId){

        try{
            dbHelper.deleteAttack(attackId);
            CharacterAttacksDatabaseHelper charAttackDbHelper = new CharacterAttacksDatabaseHelper(context);
            charAttackDbHelper.deleteAttack(attackId);
        }catch (SQLiteException e){
            e.printStackTrace();
            Log.e(tag, "deleteAttack method failed");
        }
    }

    /**
     * Sets the attributes of attack object to null
     *
     * @author Atsuko Takanabe
     */
    public void clear(){
        setId(null);
        setName(null);
        setModDamage(null);
        setModHit(null);
        setDiceId(null);
        setNumOfDice(null);
        die = null;

    }

    /********************************** GETTER AND SETTERS ***************************************/

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getModHit() { return modHit; }

    public Integer getModDamage() { return modDamage; }

    public Integer getDiceId() { return diceId; }

    public Integer getNumOfDice() { return numOfDice; }

    public Die getDie() { return die; }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModHit(Integer modHit) {
        this.modHit = modHit;
    }

    public void setModDamage(Integer modDamage) {
        this.modDamage = modDamage;
    }

    public void setNumOfDice(Integer numOfDice) { this.numOfDice = numOfDice; }

    public void setDiceId(Integer diceId) { this.diceId = diceId; }

    /**
     * Using the attack ID, it inquires the database and gets the rest of the attack's attribute,
     * then sets them in the object
     *
     * @param id Attack ID
     * @author Atsuko Takanabe
     */
    public void setAttack(Integer id){
        Map<String, String> attack = dbHelper.getAttackDetails(id);
        this.id = id;
        name = attack.get(AttackContract.getAttackNameColName());
        modDamage = Integer.parseInt(attack.get(AttackContract.getDamageModifierColName()));
        modHit = Integer.parseInt(attack.get(AttackContract.getHitModifierColName()));
        diceId = Integer.parseInt(attack.get(AttackContract.getDiceIdColName()));
        numOfDice = Integer.parseInt(attack.get(AttackContract.getNumOfDieColName()));
        die = new Die(context, diceId);
    }


}
