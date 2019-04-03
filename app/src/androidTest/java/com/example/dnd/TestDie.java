package com.example.dnd;

import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class TestDie {

    @Test
    public void testRollDamage(){

        Die die = new Die();
        die.setSides(4);
        Integer damage = die.rollDamage();

        assertTrue(damage > 0);
        assertTrue(damage <= die.getSides());
    }

    @Test
    public void testUpdateSides(){

        Integer diceId = 3;
        Die die = new Die(InstrumentationRegistry.getTargetContext(), diceId);
        die.setSides(8);

        // update
        Integer newDiceId = 5; // for 12 sided die
        die.setDieId(newDiceId);
        die.updateSides();

        // validate
        assertTrue(die.getSides() == 12);

    }

}
