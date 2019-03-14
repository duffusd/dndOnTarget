package com.example.dnd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.dnd.data.CharacterAttacksDatabaseHelper;
import com.example.dnd.data.CharacterDatabaseHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Character {

    private Integer id;
    private String name;
    private List<Integer> attackIds;
    private List<Attack> attacks;
    private CharacterDatabaseHelper dbHelper;
    private CharacterAttacksDatabaseHelper characterAttacksDbHelper;
    private String tag = "Character";

    Character(Context context){
        id = null;
        name = null;
        dbHelper = new CharacterDatabaseHelper(context);
        characterAttacksDbHelper = new CharacterAttacksDatabaseHelper(context);
        attacks = new ArrayList<>();
    }

    /**
     * This method will add attacks to the list {@link Character#attacks} without needing
     * to access the member variable, as this method will access it within the class.
     *
     * Must have an {@link Attack} class object passed in to the parameter field.
     * @param attack the attack to be added to the list of attacks
     * @return trure if operation was successful
     */
    /* public boolean addAttack(Attack attack) {
        attacks.add(attack);


        return true; //TODO: figure out if bool return is necessary
    } */

        /**
     * This method removes attacks from the list {@link Character#attacks} without needing to access
     * the member variable.
     *
     * @param attack attack object to be removed from the list
     */
    public void removeAttack(Attack attack) {
        attacks.remove(attack);
    }

    public void addNewCharacter(String newName){

        setName(newName);
        Integer newId = null;

        try{
            newId = dbHelper.addName(newName);

        }catch(SQLiteException e){
            e.printStackTrace();
            Log.e(tag, "Adding a new character to characterTable failed");
        }

        setId(newId);

    }
  
  public void updateCharacter(String newName){

        name = newName;

        try{
            dbHelper.updateName(name, id);
        } catch(SQLiteException e){
            e.printStackTrace();
            Log.e(tag, "UpdateCharacter failed");
        }
    }

  public void deleteCharacter(){

        try{
            dbHelper.deleteCharacter(id);

        }catch(SQLiteException e){
            e.printStackTrace();
            Log.e(tag, "Deleting a character failed");
        }

    }

    public List<Integer> getAttackIdsForCharacter(){
        attackIds = new ArrayList<>();
        attackIds = characterAttacksDbHelper.getAttackIdsByCharacterId(id);
        return attackIds;
    }


    public void clearCharacter(){
        id = null;
        name = null;
        if(attacks != null && !attacks.isEmpty())
            attacks.clear();
    }

    public void addAttack(Attack attack){
        attacks.add(attack);
    }

    /********************************** GETTER AND SETTERS ***************************************/

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Attack> getAttacks() { return attacks; }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
