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
    public void testAddAttack() throws Exception{

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String attackName = "attack1";
        String hitModifier = "3";
        String damageModifier = "5";
        Integer diceId = 2;
        String numOfDice = "2";
        attack.setName(attackName);
        attack.setModHit(Integer.parseInt(hitModifier));
        attack.setModDamage(Integer.parseInt(damageModifier));
        attack.setDiceId(diceId);
        attack.setNumOfDice(Integer.parseInt(numOfDice));

        // add the new attack to the database
        Integer attackId = attack.addAttack(attackName, hitModifier, damageModifier, diceId, numOfDice);
        attack.setId(attackId);
        assertTrue(attackId > 0);

        // get the attack from the database table
        AttackDatabaseHelper dbHelper = new AttackDatabaseHelper(InstrumentationRegistry.getTargetContext());
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
    public void testDeleteAttack() throws Exception{

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String attackName = "attack1";
        String hitModifier = "3";
        String damageModifier = "5";
        Integer diceId = 2;
        String numOfDice = "2";
        attack.setName(attackName);
        attack.setModHit(Integer.parseInt(hitModifier));
        attack.setModDamage(Integer.parseInt(damageModifier));
        attack.setDiceId(diceId);
        attack.setNumOfDice(Integer.parseInt(numOfDice));

        // add the new attack to the database
        Integer attackId = attack.addAttack(attackName, hitModifier, damageModifier, diceId, numOfDice);
        attack.setId(attackId);
        assertTrue(attackId > 0);

        // add the attack to CharacterAttack database
        CharacterAttacksDatabaseHelper characterAttackDbHelper = new CharacterAttacksDatabaseHelper(InstrumentationRegistry.getTargetContext());
        characterAttackDbHelper.addCharacterAttack(1, attackId);

        // delete the attack
        attack.deleteAttack(attackId);

        // check in the database table
        AttackDatabaseHelper dbHelper = new AttackDatabaseHelper(InstrumentationRegistry.getTargetContext());
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
    public void testUpdateName() throws Exception {

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String attackName = "TestAttack";
        attack.setName(attackName);

        // add the attack to the database table
        Integer attackId = attack.addAttack(attackName, "0", "0", 1, "1");
        attack.setAttack(attackId);

        // update the attack name
        String newAttackName = "NewAttack";
        attack.updateName(newAttackName);

        // check the attack name in the database table
        AttackDatabaseHelper dbHelper = new AttackDatabaseHelper(InstrumentationRegistry.getTargetContext());
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
    public void testUpdateHitModifier() throws Exception {

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String attackName = "TestAttack";
        attack.setName(attackName);

        // add the attack to the database table
        Integer attackId = attack.addAttack(attackName, "0", "0", 1, "1");
        attack.setAttack(attackId);

        // update the hit modifier
        Integer newHitModifier = 3;
        attack.updateHitModifier(newHitModifier.toString());

        // check the hit modifier in the database table
        AttackDatabaseHelper dbHelper = new AttackDatabaseHelper(InstrumentationRegistry.getTargetContext());
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
    public void testUpdateDamageModifier() throws Exception{

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String attackName = "TestAttack";
        attack.setName(attackName);

        // add the attack to the database table
        Integer attackId = attack.addAttack(attackName, "0", "0", 1, "1");
        attack.setAttack(attackId);

        // update the damage modifier
        Integer newDamageModifier = 3;
        attack.updateDamageModifier(newDamageModifier.toString());

        // check the damage modifier in the database table
        AttackDatabaseHelper dbHelper = new AttackDatabaseHelper(InstrumentationRegistry.getTargetContext());
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

    public void testUpdateDiceId() throws Exception{

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String attackName = "TestAttack";
        attack.setName(attackName);

        // add the attack to the database table
        Integer attackId = attack.addAttack(attackName, "0", "0", 1, "1");
        attack.setAttack(attackId);

        // update the diceId
        Integer newDiceId = 3;
        attack.updateDiceID(newDiceId);

        // check the diceID in the database table
        AttackDatabaseHelper dbHelper = new AttackDatabaseHelper(InstrumentationRegistry.getTargetContext());
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
    public void testUpdateNumOfDice() throws Exception {

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String attackName = "TestAttack";
        attack.setName(attackName);

        // add the attack to the database table
        Integer attackId = attack.addAttack(attackName, "0", "0", 1, "1");
        attack.setAttack(attackId);

        // update the diceId
        Integer newNumOfDice = 3;
        attack.updateNumOfDie(newNumOfDice.toString());

        // check the diceID in the database table
        AttackDatabaseHelper dbHelper = new AttackDatabaseHelper(InstrumentationRegistry.getTargetContext());
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




    @Test(expected = EmptyStringException.class)
    public void testValidateAttackName() throws Exception{

        EmptyStringException exception = new EmptyStringException();

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String attackName = "";

        attack.validateAttackName(attackName);
        assertTrue(exception.equals(EmptyStringException.class));

    }

    @Test
    public void testValidateHitModifier(){

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String hitModifierText = "";

        Integer hitModifierInt = attack.validateHitModifier(hitModifierText);

        assertTrue(hitModifierInt == 0);
    }


    @Test(expected = InvalidIntegerException.class)
    public void testValidateDamageModifier() throws Exception{

        InvalidIntegerException exception = new InvalidIntegerException();

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String damageModifierEmptyString = "";
        String damageModifierAlphabet = "12b";

        Integer damageModifierZero = attack.validateHitModifier(damageModifierEmptyString);
        attack.validateDamageModifier(damageModifierAlphabet);

        assertTrue(damageModifierZero == 0);
        assertTrue(exception.equals(InvalidIntegerException.class));
    }


    @Test(expected = InvalidIntegerException.class)
    public void testValidateNumOfDice() throws Exception{

        InvalidIntegerException exception = new InvalidIntegerException();

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String emptyString = "";
        String zeroString = "0";

        attack.validateNumOfDie(emptyString);
        attack.validateNumOfDie(zeroString);

        assertTrue(exception.equals(InvalidIntegerException.class));

    }

    @Test(expected = InvalidIntegerException.class)
    public void testValidateDiceId() throws Exception{

        InvalidIntegerException exception = new InvalidIntegerException();
        ArrayList<Integer> invalidIds = new ArrayList<>();
        invalidIds.add(0);
        invalidIds.add(7);
        invalidIds.add(-10);

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());

        for (int id : invalidIds){

            attack.validateDiceId(id);
            assertTrue(exception.equals(InvalidIntegerException.class));
        }
    }


    @Test
    public void testSetAttack() throws Exception {

        Attack attack = new Attack(InstrumentationRegistry.getTargetContext());
        String attackName = "TestAttack";
        attack.setName(attackName);

        // add the attack to the database table
        Integer attackId = attack.addAttack(attackName, "0", "0", 1, "1");
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
