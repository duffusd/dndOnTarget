package com.example.dnd;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.dnd.data.CharacterAttacksDatabaseHelper;
import com.example.dnd.data.CharacterDatabaseHelper;

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
    private Context context;


    /**
     * Character constructor
     *
     * @param context
     */
    Character(Context context){
        id = null;
        name = null;
        dbHelper = new CharacterDatabaseHelper(context);
        characterAttacksDbHelper = new CharacterAttacksDatabaseHelper(context);
        attacks = new ArrayList<>();
        this.context = context;
    }


    /**
     * This method removes attacks from the list {@link Character#attacks} without needing to access
     * the member variable.
     *
     * @param attack attack object to be removed from the list
     * @author Justin Parry
     */
    public void removeAttack(Attack attack) {
        attacks.remove(attack);
    }


    /**
     * Adds a new character to the backend database table
     *
     * @param newName The name of the new character
     * @return The new Character ID of the newly added character. If the procedure fails, it returns -1
     * @exception SQLiteException
     * @author Atsuko Takanabe
     */
    public Integer addNewCharacter(String newName){

        Integer newId = null;

        try{
            newId = dbHelper.addName(newName);

        }catch(SQLiteException e){
            e.printStackTrace();
            newId = -1;
            Log.e(tag, "Adding a new character to characterTable failed");
        }

        return newId;

    }

    /**
     * Updates a selected character's name
     *
     * @param newName The new name for a character
     * @author Atsuko Takanabe
     */
    public void updateCharacter(String newName){
    
        name = newName;

        try{
            dbHelper.updateName(name, id);
        } catch(SQLiteException e){
            e.printStackTrace();
            Log.e(tag, "UpdateCharacter failed");
        }
    }

    /**
     * Deletes the character from the backend character table.
     *
     * All the attacks associted with the character get deleted as well
     *
     * @author Atsuko Takanabe
     * @exception SQLiteException
     */
    public void deleteCharacter(){

        try{
            dbHelper.deleteCharacter(id);
            CharacterAttacksDatabaseHelper charAttackDbHelper = new CharacterAttacksDatabaseHelper(context);
            charAttackDbHelper.deleteCharacter(id);

        }catch(SQLiteException e){
            e.printStackTrace();
            Log.e(tag, "Deleting a character failed");
        }

    }

    /**
     * Gets the attack IDs associted with the character
     *
     * @return List of Attack IDs associted with the character
     * @author Atsuko Takanabe
     */
    public List<Integer> getAttackIdsForCharacter(){
        attackIds = new ArrayList<>();
        attackIds = characterAttacksDbHelper.getAttackIdsByCharacterId(id);
        return attackIds;
    }


    /**
     * This method is used for generating the attacks list.
     *
     * Using the attackIDs that belong to the character, it creates the attack object for each
     * attck ID then adds it to the attacks list
     *
     * @exception SQLiteException
     * @author Atsuko Takanabe
     */
    public void generateAttacksForCharacter(){

        attacks.clear();
        attackIds = getAttackIdsForCharacter();

        if (attackIds.size() > 0) {
            for (int i = 0; i < attackIds.size(); i++) {
                Attack attack = new Attack(context);
                attack.setAttack(attackIds.get(i));
                addAttack(attack);
            }

        }
    }


    /**
     * Sets the id and name of the character to null, and clears attacks' list
     *
     * @author Atsuko Takanabe
     */
    public void clearCharacter(){
        id = null;
        name = null;
        if(attacks.size() > 0) {
            attacks.clear();
        }
    }

    /**
     * Adds an attack to attacks' list
     *
     * @param attack attack to add to attacks' list
     * @author Atsuko Takanabe
     */
    public void addAttack(Attack attack){
        attacks.add(attack);
    }


    /***** Getters and Setters ********/

    public Integer getId() { return id; }

    public String getName() { return name; }

    public List<Attack> getAttacks() { return attacks; }

    public void setId(Integer id) { this.id = id; }

    public void setName(String name) { this.name = name; }
}
