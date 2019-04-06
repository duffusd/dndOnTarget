package com.example.dnd;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.dnd.data.CharacterAttacksDatabaseHelper;
import com.example.dnd.data.CharacterContract;
import com.example.dnd.data.CharacterDatabaseHelper;
import com.example.dnd.data.DatabaseContract;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.w3c.dom.CharacterData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TestCharacter {

    @Test
    public void testAddNewCharacter(){

        Character character = new Character(InstrumentationRegistry.getTargetContext());
        String name = "TestCharacter";
        Integer characterId = character.addNewCharacter(name);
        character.setId(characterId);

        // Get the character's name by characterId from the database table
        CharacterDatabaseHelper dbHelper = CharacterDatabaseHelper.getInstance(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor result = db.rawQuery("SELECT "
                + CharacterContract.getNameColName()
                + " FROM "
                + CharacterContract.getTableName()
                + " WHERE "
                + CharacterContract.getIdColName()
                + "="
                + characterId, null);

        String characterNameInDB = null;

        while(result.moveToNext()){
            characterNameInDB = result.getString(0);
        }

        // assert
        assertTrue(characterId > 0);
        assertTrue(characterNameInDB.equals(name));


        // delete the character and close the database helper
        dbHelper.close();
        character.deleteCharacter();
    }


    @Test
    public void testUpdateName(){

        Character character = new Character(InstrumentationRegistry.getTargetContext());
        String originalName = "Bear";
        String newName = "Mr.Bear";

        Integer characterId = character.addNewCharacter(originalName);
        character.setId(characterId);
        character.setName(originalName);

        // Update the character name
        character.updateCharacter(newName);

        // assert
        assertTrue(character.getName() == newName);

        // delete the character
        character.deleteCharacter();

    }



    @Test
    public void testValidateCharacterName(){

        String message_already_taken = "Character name is already taken";
        String message_empty_name = "You must put something in the text field";

        String expectedMessage_already_taken = null;
        String expectedMessage_empty_name = null;


        Character character = new Character(InstrumentationRegistry.getTargetContext());
        String name = "TestCharacter";
        Integer id = character.addNewCharacter(name);
        character.setId(id);

        // validate the character name that already exists in the database table
        try{

            character.validateCharacterName(name);

        }catch (Exception e){

            expectedMessage_already_taken = e.getMessage();
        }

        assertTrue(expectedMessage_already_taken.equals(message_already_taken));


        // validate the empty character name
        try{

            character.validateCharacterName("");

        }catch (Exception e){

            expectedMessage_empty_name = e.getMessage();
        }

        assertTrue(expectedMessage_empty_name.equals(message_empty_name));

        // delete the character
        character.deleteCharacter();

    }

    @Test
    public void testGetAttackIdsForCharacter() throws Exception{

        Character character = new Character(InstrumentationRegistry.getTargetContext());
        Integer characterId = character.addNewCharacter("Lion");
        assertTrue(characterId > 0);
        character.setId(characterId);

        // create attacks
        Attack attack1 = new Attack(InstrumentationRegistry.getTargetContext());
        String attack1_name = "Attack1";
        Integer attack1_hitModifier = 0;
        Integer attack1_damageModifier = 3;
        Integer attack1_dieType = 3;
        Integer attack1_numOfDice = 3;
        Integer attack1_id = attack1.addAttack(attack1_name, attack1_hitModifier, attack1_damageModifier, attack1_dieType, attack1_numOfDice);

        Attack attack2 = new Attack(InstrumentationRegistry.getTargetContext());
        String attack2_name = "Attack2";
        Integer attack2_hitModifier = 0;
        Integer attack2_damageModifier = 3;
        Integer attack2_dieType = 3;
        Integer attack2_numOfDice = 3;
        Integer attack2_id = attack2.addAttack(attack2_name, attack2_hitModifier, attack2_damageModifier, attack2_dieType, attack2_numOfDice);

        // add a character and attack to CharacterAttacks Database
        CharacterAttacksDatabaseHelper dbHelper = CharacterAttacksDatabaseHelper.getInstance(InstrumentationRegistry.getTargetContext());
        dbHelper.addCharacterAttack(characterId, attack1_id);
        dbHelper.addCharacterAttack(characterId, attack2_id);

        // test
        List<Integer> attackIds = new ArrayList<>();
        attackIds = character.getAttackIdsForCharacter();
        assertTrue(attackIds.size() == 2);
        assertTrue(attackIds.get(0) == attack1_id || attackIds.get(0) == attack2_id);
        assertTrue(attackIds.get(1) == attack1_id || attackIds.get(1) == attack2_id);

        // delete character and attacks and close the database helper
        character.deleteCharacter();
        attack1.deleteAttack(attack1_id);
        attack2.deleteAttack(attack2_id);
        dbHelper.close();
    }

    @Test
    public void TestGenerateAttacksForCharacter() throws Exception{

        Character character = new Character(InstrumentationRegistry.getTargetContext());
        Integer characterId = character.addNewCharacter("LionXX");
        assertTrue(characterId > 0);
        character.setId(characterId);

        // create attack
        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String attack_name = "attackXX";
        Integer attack_hitModifier = 0;
        Integer attack_damageModifier = 3;
        Integer attack_dieType = 3;
        Integer attack_numOfDice = 3;
        Integer attack_id = attack.addAttack(attack_name, attack_hitModifier, attack_damageModifier, attack_dieType, attack_numOfDice);
        attack.setId(attack_id);
        attack.setName(attack_name);
        attack.setModHit(attack_hitModifier);
        attack.setModDamage(attack_damageModifier);
        attack.setDiceId(attack_dieType);
        attack.setNumOfDice(attack_numOfDice);

        // add a character and attack to CharacterAttacks Database
        CharacterAttacksDatabaseHelper dbHelper = CharacterAttacksDatabaseHelper.getInstance(InstrumentationRegistry.getTargetContext());
        dbHelper.addCharacterAttack(characterId, attack_id);

        // test
        character.generateAttacksForCharacter();
        List<Attack> testAttacksList = character.getAttacks();
        Attack testAttack = testAttacksList.get(0);

        assertEquals(attack.getId(), testAttack.getId());
        assertEquals(attack.getName(), testAttack.getName());
        assertEquals(attack.getModHit(), testAttack.getModHit());
        assertEquals(attack.getDiceId(), testAttack.getDiceId());
        assertEquals(attack.getNumOfDice(), testAttack.getNumOfDice());

        // delete character and attack and close the database helper
        character.deleteCharacter();
        attack.deleteAttack(attack_id);
        dbHelper.close();

    }

    @Test
    public void testDeleteCharacter(){

        // create a new character
        Character character = new Character(InstrumentationRegistry.getTargetContext());
        String name = "TestTest";
        Integer characterId = character.addNewCharacter(name);
        character.setId(characterId);

        // delete the character
        character.deleteCharacter();

        // check if the character is removed from the database table
        CharacterDatabaseHelper dbHelper = CharacterDatabaseHelper.getInstance(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " +
                CharacterContract.getTableName() +
                " WHERE " +
                CharacterContract.getIdColName() +
                "="
                + characterId, null);

        assertTrue(result.getCount() == 0);


        // delete the character and close the database helper
        character.deleteCharacter();
        dbHelper.close();

    }


}
