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

import java.util.concurrent.ThreadLocalRandom;

public class Die {
    private int sides;

    Die() {
        sides = 0;
    }

    Die(int sides) {
        this.sides = sides;
    }

    /**
     * The roll method will generate a random number with a range max dictated by the
     * {@link Die#sides} member. Uses
     * @return integer result of the number generation
     * @since 1.0
     */
    public int roll() {
        if (sides != 0)
            return ThreadLocalRandom.current().nextInt(1,sides + 1);
        else
            return -1;
    }

    /********************************** GETTER AND SETTERS ***************************************/

    public void setSides(int sides) {
        this.sides = sides;
    }

    public int getSides() {
        return sides;
    }
}
