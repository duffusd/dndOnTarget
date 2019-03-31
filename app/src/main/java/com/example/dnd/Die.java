/**
 * <h>Die Class</h>
 * The {@link com.example.dnd.data.Die} contains a member variable integer containing the number of
 * sides of the "die." This is used in the {@link com.example.dnd.data.Die#roll()} method to
 * generate a random number with a maximum corresponding to the number of sides on the die.
 *
 * The {@link java.util.concurrent.ThreadLocalRandom} library is imported to generate the random
 * number.
 *
 * <p>
 * @author Justin Parry
 * @version 1.0
 * @since 02-28-2019
 * </p>
 */

package com.example.dnd;

import android.content.Context;

import com.example.dnd.data.DiceDatabaseHelper;

import java.util.concurrent.ThreadLocalRandom;

public class Die {

    Context context;
    private Integer dieId;
    private Integer sides;
    private DiceDatabaseHelper diceDbHelper;

    /**
     * Non-default constructor 
     * @param context
     * @param diceId
     */
    Die(Context context, Integer diceId){
        this.context = context;
        diceDbHelper = new DiceDatabaseHelper(context);
        this.dieId = diceId;
        sides = diceDbHelper.getDieNumber(dieId);
    }

    /**
     * Default constructor
     *
     */
    Die(){
        context = null;
        dieId = null;
        sides = null;
        diceDbHelper = null;
    }


    /**
     * Generates a random number with a range max dictated by the
     * {@link Die#sides} member. Uses
     * @return integer result of the number generation
     * @since 1.0
     * @author Justin Parry
     */
    public Integer rollDamage() {
        return ThreadLocalRandom.current().nextInt(1,sides + 1);

    }


    /**
     * Updates the number of sides on the die
     *
     * @author Atsuko Takanabe
     * @exception android.database.sqlite.SQLiteException
     */
    public void updateSides(){
        sides = diceDbHelper.getDieNumber(dieId);
    }

    /********************************** GETTER AND SETTERS ***************************************/

    public void setSides(int sides) {
        this.sides = sides;
    }

    public Integer getSides() {
        return sides;
    }
  
    public Integer getDieId() { return dieId; }

    public void setDieId(int dieId) { this.dieId = dieId; }
}
