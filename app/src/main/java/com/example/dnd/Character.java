package com.example.dnd;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import com.example.dnd.data.CharacterAttacksDatabaseHelper;
import com.example.dnd.data.CharacterDatabaseHelper;

import org.w3c.dom.CharacterData;

import java.util.ArrayList;
import java.util.List;

/**
 * <h>Character Class</h>
 *
 * This entity contains the attributes of a character and methods for maintaining those attributes
 *
 */
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
     * Copy constructor
     *
     * @param copy Character object to copy
     */
    Character(Character copy){

        id = copy.id;
        name = copy.name;
        attackIds = copy.getAttackIdsForCharacter();
        attacks = copy.getAttacks();
        dbHelper = copy.dbHelper;
        characterAttacksDbHelper = copy.characterAttacksDbHelper;
        tag = copy.tag;
        context = copy.context;

    }


    /**
     * Non-default constructor
     *
     * @param context
     */
    Character(Context context){
        id = null;
        name = null;
        dbHelper = CharacterDatabaseHelper.getInstance(context);
        characterAttacksDbHelper = CharacterAttacksDatabaseHelper.getInstance(context);
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
     * @return The Character ID of the newly added character. If the procedure fails, it returns -1
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
     * Updates the character name
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
     * Deletes the character from the backend character table
     *
     * All the attacks associated with the character get deleted as well
     *
     * @author Atsuko Takanabe
     * @exception SQLiteException
     */
    public void deleteCharacter(){

        try{

            dbHelper.deleteCharacter(id);
            CharacterAttacksDatabaseHelper charAttackDbHelper = CharacterAttacksDatabaseHelper.getInstance(context);
            charAttackDbHelper.deleteCharacter(id);

        }catch(SQLiteException e){

            e.printStackTrace();
            Log.e(tag, "Deleting a character failed");
        }

    }


    /**
     * Gets the attack IDs associated with the character
     *
     * @return List of Attack IDs associated with the character
     * @author Atsuko Takanabe
     */
    public List<Integer> getAttackIdsForCharacter(){

        attackIds = new ArrayList<>();
        attackIds = characterAttacksDbHelper.getAttackIdsByCharacterId(id);
        return attackIds;

    }


    /**
     * This method is used for generating the attacks list
     *
     * Using the attackIDs that are linked to the character, this method creates the attack object for each
     * attack ID and adds it to the attacks list
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
     * This method validates the character name. If the name is an empty string or already exists in the database,
     * it throws the InvalidString exception
     *
     * @param name Character name to validate
     * @return Validated new character name
     * @author Atsuko Takanabe
     */
    public void validateCharacterName(String name) throws Exception{

        Boolean characterExists = dbHelper.findCharacterByName(name);

        if(characterExists){

            throw new InvalidStringException("Character name is already taken");
        }

        if(name.isEmpty() || name.length() == 0){

            throw new InvalidStringException("You must put something in the text field");

        }

        dbHelper.close();


    }


    /**
     * Sets the id and name of the character to null and clears the attacks' list
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
     * Adds an attack object to the attacks' list
     *
     * @param attack Attack object to add to the attacks' list
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
