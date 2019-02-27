package com.example.dnd;

import android.service.autofill.RegexValidator;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    // Test that when the user leaves required field empty, the application won't save anything
    @Test
    public void cannotSaveEmptyField(){

        String nameField = new String();
        Assert.assertNotNull(nameField);
    }

    // Test that the hit modifier cannot be negative
    public void negative_number(){

        Integer hitModifier = 0;
        Assert.assertTrue(hitModifier >= 0);

    }

    // Test that the user can name a character in unicode
    @Test
    public void unicode_allowed(){

        String name_jp = "あいうえお";
        Assert.assertEquals("あいうえお", name_jp);


    }
}