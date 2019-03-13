package com.example.dnd;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.dnd.data.CharacterDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class Character {

    private Integer id;
    private String name;
    private List<Attack> attacks;
    private CharacterDatabaseHelper dbHelper;


    Character(Context context){
        List<Attack> attacks = new ArrayList<>();
        id = null;
        name = null;
        dbHelper = new CharacterDatabaseHelper(context);
    }

    /**
     * This method will add attacks to the list {@link Character#attacks} without needing
     * to access the member variable, as this method will access it within the class.
     *
     * Must have an {@link Attack} class object passed in to the parameter field.
     * @param attack the attack to be added to the list of attacks
     * @return trure if operation was successful
     */
    public boolean addAttack(Attack attack) {
        attacks.add(attack);


        return true; //TODO: figure out if bool return is necessary
    }

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
            Log.e("Character", "Adding a new character to characterTable failed");

        }

        setId(newId);

    }

    public void deleteCharacter(){

        try{
            dbHelper.deleteCharacter(id);

        }catch(SQLiteException e){
            e.printStackTrace();
            Log.e("Character", "Deleting a character failed");
        }

    }
    /********************************** GETTER AND SETTERS ***************************************/

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void clearCharacter(){
        id = null;
        name = null;
        if(attacks != null && !attacks.isEmpty())
            attacks.clear();
    }
}
