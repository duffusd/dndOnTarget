package com.example.dnd;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.dnd.data.AttackContract;
import com.example.dnd.data.AttackDatabaseHelper;
import com.example.dnd.data.CharacterAttacksContract;
import com.example.dnd.data.CharacterAttacksDatabaseHelper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TestAttack {


    @Test
    public void testAddAttack(){

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String attackName = "attack1";
        Integer hitModifier = 3;
        Integer damageModifier = 5;
        Integer diceId = 2;
        Integer numOfDice = 2;

        attack.setName(attackName);
        attack.setModHit(hitModifier);
        attack.setModDamage(damageModifier);
        attack.setDiceId(diceId);
        attack.setNumOfDice(numOfDice);

        // add the new attack to the database
        Integer attackId = attack.addAttack(attackName, hitModifier, damageModifier, diceId, numOfDice);
        attack.setId(attackId);
        assertTrue(attackId > 0);

        // get the attack from the database table
        AttackDatabaseHelper dbHelper = AttackDatabaseHelper.getInstance(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "
                + AttackContract.getTableName()
                + " WHERE "
                + AttackContract.getIdColName()
                + "=" + attackId, null);

        assertTrue(result.getCount() == 1);

        String dbAttackName = null;
        Integer dbHitModifier = null;
        Integer dbDamageModifier = null;
        Integer dbDiceId = null;
        Integer dbNumOfDice = null;

        while (result.moveToNext()){

            dbAttackName = result.getString(result.getColumnIndex(AttackContract.getAttackNameColName()));
            dbHitModifier = result.getInt(result.getColumnIndex(AttackContract.getHitModifierColName()));
            dbDamageModifier = result.getInt(result.getColumnIndex(AttackContract.getDamageModifierColName()));
            dbDiceId = result.getInt(result.getColumnIndex(AttackContract.getDiceIdColName()));
            dbNumOfDice = result.getInt(result.getColumnIndex(AttackContract.getNumOfDieColName()));

        }

        // validate
        Assert.assertEquals(attack.getName(), dbAttackName);
        Assert.assertEquals(attack.getModHit(), dbHitModifier);
        Assert.assertEquals(attack.getModDamage(), dbDamageModifier);
        Assert.assertEquals(attack.getDiceId(), dbDiceId);
        Assert.assertEquals(attack.getNumOfDice(), dbNumOfDice);


        // delete the attack and close the database helper
        attack.deleteAttack(attackId);
        dbHelper.close();

    }

    @Test
    public void testDeleteAttack(){

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String attackName = "attack1";
        Integer hitModifier = 3;
        Integer damageModifier = 5;
        Integer diceId = 2;
        Integer numOfDice = 2;
        attack.setName(attackName);
        attack.setModHit(hitModifier);
        attack.setModDamage(damageModifier);
        attack.setDiceId(diceId);
        attack.setNumOfDice(numOfDice);

        // add the new attack to the database
        Integer attackId = attack.addAttack(attackName, hitModifier, damageModifier, diceId, numOfDice);
        attack.setId(attackId);
        assertTrue(attackId > 0);

        // add the attack to CharacterAttack database
        CharacterAttacksDatabaseHelper characterAttackDbHelper = CharacterAttacksDatabaseHelper.getInstance(InstrumentationRegistry.getTargetContext());
        characterAttackDbHelper.addCharacterAttack(1, attackId);

        // delete the attack
        attack.deleteAttack(attackId);

        // check in the database table
        AttackDatabaseHelper dbHelper = AttackDatabaseHelper.getInstance(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "
                + AttackContract.getTableName()
                + " WHERE "
                + AttackContract.getIdColName()
                + "="
                + attackId, null);

        assertTrue(result.getCount() == 0);

        // check in the CharacterAttack database table
        SQLiteDatabase characterAttackDb = characterAttackDbHelper.getReadableDatabase();
        Cursor characterAttackResult = characterAttackDb.rawQuery("SELECT * FROM "
                                            + CharacterAttacksContract.getTableName()
                                            + " WHERE "
                                            + CharacterAttacksContract.getAttackIdColName()
                                            + "="
                                            + attackId, null);

        assertTrue(characterAttackResult.getCount() == 0);

        // close the database helpers
        dbHelper.close();
        characterAttackDbHelper.close();

    }


    @Test
    public void testUpdateName(){

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String attackName = "TestAttack";
        attack.setName(attackName);

        // add the attack to the database table
        Integer attackId = attack.addAttack(attackName, 0, 0, 1, 1);
        attack.setAttack(attackId);

        // update the attack name
        String newAttackName = "NewAttack";
        attack.updateName(newAttackName);

        // check the attack name in the database table
        AttackDatabaseHelper dbHelper = AttackDatabaseHelper.getInstance(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT "
                                    + AttackContract.getAttackNameColName()
                                    + " FROM "
                                    + AttackContract.getTableName()
                                    + " WHERE "
                                    + AttackContract.getIdColName()
                                    + "="
                                    + attackId, null);

        assertTrue(result.getCount() == 1);

        String dbAttackName = null;
        while(result.moveToNext()){

            dbAttackName = result.getString(result.getColumnIndex(AttackContract.getAttackNameColName()));

        }

        assertEquals(newAttackName, dbAttackName);
        assertEquals(newAttackName, attack.getName());

        // delete the attack and database helper
        attack.deleteAttack(attackId);
        dbHelper.close();

    }


    @Test
    public void testUpdateHitModifier(){

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String attackName = "TestAttack";
        attack.setName(attackName);

        // add the attack to the database table
        Integer attackId = attack.addAttack(attackName, 0, 0, 1, 1);
        attack.setAttack(attackId);

        // update the hit modifier
        Integer newHitModifier = 3;
        attack.updateHitModifier(newHitModifier);

        // check the hit modifier in the database table
        AttackDatabaseHelper dbHelper = AttackDatabaseHelper.getInstance(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT "
                + AttackContract.getHitModifierColName()
                + " FROM "
                + AttackContract.getTableName()
                + " WHERE "
                + AttackContract.getIdColName()
                + "="
                + attackId, null);

        assertTrue(result.getCount() == 1);

        Integer dbHitModifier = null;
        while(result.moveToNext()){

            dbHitModifier = result.getInt(result.getColumnIndex(AttackContract.getHitModifierColName()));

        }

        assertEquals(newHitModifier, dbHitModifier);
        assertEquals(newHitModifier, attack.getModHit());

        // delete the attack and database helper
        attack.deleteAttack(attackId);
        dbHelper.close();

    }


    @Test
    public void testUpdateDamageModifier(){

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String attackName = "TestAttack";
        attack.setName(attackName);

        // add the attack to the database table
        Integer attackId = attack.addAttack(attackName, 0, 0, 1, 1);
        attack.setAttack(attackId);

        // update the damage modifier
        Integer newDamageModifier = 3;
        attack.updateDamageModifier(newDamageModifier);

        // check the damage modifier in the database table
        AttackDatabaseHelper dbHelper = AttackDatabaseHelper.getInstance(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT "
                + AttackContract.getDamageModifierColName()
                + " FROM "
                + AttackContract.getTableName()
                + " WHERE "
                + AttackContract.getIdColName()
                + "="
                + attackId, null);

        assertTrue(result.getCount() == 1);

        Integer dbDamageModifier = null;
        while(result.moveToNext()){

            dbDamageModifier = result.getInt(result.getColumnIndex(AttackContract.getDamageModifierColName()));

        }

        assertEquals(newDamageModifier, dbDamageModifier);
        assertEquals(newDamageModifier, attack.getModDamage());

        // delete the attack and database helper
        attack.deleteAttack(attackId);
        dbHelper.close();

    }

    public void testUpdateDiceId(){

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String attackName = "TestAttack";
        attack.setName(attackName);

        // add the attack to the database table
        Integer attackId = attack.addAttack(attackName, 0, 0, 1, 1);
        attack.setAttack(attackId);

        // update the diceId
        Integer newDiceId = 3;
        attack.updateDiceID(newDiceId);

        // check the diceID in the database table
        AttackDatabaseHelper dbHelper = AttackDatabaseHelper.getInstance(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT "
                + AttackContract.getDiceIdColName()
                + " FROM "
                + AttackContract.getTableName()
                + " WHERE "
                + AttackContract.getIdColName()
                + "="
                + attackId, null);

        assertTrue(result.getCount() == 1);

        Integer dbDiceId = null;
        while(result.moveToNext()){

            dbDiceId = result.getInt(result.getColumnIndex(AttackContract.getDiceIdColName()));

        }

        assertEquals(newDiceId, dbDiceId);
        assertEquals(newDiceId, attack.getDiceId());

        // delete the attack and database helper
        attack.deleteAttack(attackId);
        dbHelper.close();

    }

    @Test
    public void testUpdateNumOfDice(){

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String attackName = "TestAttack";
        attack.setName(attackName);

        // add the attack to the database table
        Integer attackId = attack.addAttack(attackName, 0, 0, 1, 1);
        attack.setAttack(attackId);

        // update the diceId
        Integer newNumOfDice = 3;
        attack.updateNumOfDie(newNumOfDice);

        // check the diceID in the database table
        AttackDatabaseHelper dbHelper = AttackDatabaseHelper.getInstance(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT "
                + AttackContract.getNumOfDieColName()
                + " FROM "
                + AttackContract.getTableName()
                + " WHERE "
                + AttackContract.getIdColName()
                + "="
                + attackId, null);

        assertTrue(result.getCount() == 1);

        Integer dbNumOfDice = null;
        while(result.moveToNext()){

            dbNumOfDice = result.getInt(result.getColumnIndex(AttackContract.getNumOfDieColName()));

        }

        assertEquals(newNumOfDice, dbNumOfDice);
        assertEquals(newNumOfDice, attack.getNumOfDice());

        // delete the attack and database helper
        attack.deleteAttack(attackId);
        dbHelper.close();

    }


    @Test
    public void testValidateAttackName(){

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String attackName = "";
        String message = "What's the name of the attack?";
        String actual_message = null;

        try{

            attack.validateAttackName(attackName);

        }catch(Exception e){

            actual_message = e.getMessage();

        }

        assertTrue(actual_message.equals(message));

    }

    @Test
    public void testValidateHitModifier(){

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String hitModifierText = "";

        Integer hitModifierInt = attack.validateHitModifier(hitModifierText);

        assertTrue(hitModifierInt == 0);
    }


    @Test
    public void testValidateDamageModifier(){

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String damageModifierEmptyString = "";
        String damageModifierAlphabet = "12b";

        // if the empty string is passed for damage modifier, it returns 0
        Integer damageModifierZero = attack.validateHitModifier(damageModifierEmptyString);
        assertTrue(damageModifierZero == 0);


        // if the damage modifier contains the alphabet, the error message is thrown
        String message_alphabet = "Damage Modifier can't have any alphabet";
        String actual_message = null;

        try{

            attack.validateDamageModifier(damageModifierAlphabet);

        }catch(Exception e){

            actual_message = e.getMessage();
        }

        assertTrue(actual_message.equals(message_alphabet));


    }


    @Test
    public void testValidateNumOfDice(){

        String message = "At least one die is required";
        String actual_message = null;

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String emptyString = "";
        String zeroString = "0";

        // validate the empty number of dice
        try{

            attack.validateNumOfDie(emptyString);

        }catch(Exception e){

            actual_message = null;
            actual_message = e.getMessage();
        }

        assertTrue(actual_message.equals(message));


        // validate 0 for number of dice
        try{

            attack.validateNumOfDie(zeroString);

        }catch(Exception e){

            actual_message = null;
            actual_message = e.getMessage();

        }

        assertTrue(actual_message.equals(message));

    }

    @Test
    public void testValidateDiceId(){

        ArrayList<Integer> invalidIds = new ArrayList<>();
        invalidIds.add(0);
        invalidIds.add(7);
        invalidIds.add(-10);

        String message = "Select the correct die type";
        String actual_message = null;

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());

        for (int id : invalidIds){

            try{

                attack.validateDiceId(id);

            }catch(Exception e){

                actual_message = e.getMessage();
                assertTrue(actual_message.equals(message));
                actual_message = null;

            }
        }
    }


    @Test
    public void testSetAttack(){

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String attackName = "TestAttack";
        attack.setName(attackName);

        // add the attack to the database table
        Integer attackId = attack.addAttack(attackName, 0, 0, 1, 1);
        attack.setAttack(attackId);
        Die die = new Die(InstrumentationRegistry.getTargetContext(), attack.getDiceId());
        attack.setDie(die);

        // call setAttack
        Attack testAttack = new Attack(InstrumentationRegistry.getTargetContext());
        testAttack.setAttack(attackId);

        // validate
        assertEquals(attack.getName(), testAttack.getName());
        assertEquals(attack.getModHit(), testAttack.getModHit());
        assertEquals(attack.getModDamage(), testAttack.getModDamage());
        assertEquals(attack.getDiceId(), testAttack.getDiceId());
        assertEquals(attack.getDie().getDieId(), testAttack.getDie().getDieId());
        assertEquals(attack.getDie().getSides(), testAttack.getDie().getSides());

        // delete
        attack.deleteAttack(attack.getId());
        attack.deleteAttack(testAttack.getId());

    }

    @Test
    public void testRollHit(){
        Attack attack = new Attack();
        Integer hit = attack.rollHit();
        assertTrue(hit > 0);
        assertTrue(hit <= 20);
    }

}
