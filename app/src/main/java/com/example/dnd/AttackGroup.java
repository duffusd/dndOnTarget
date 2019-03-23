package com.example.dnd;

import android.util.Pair;

import java.util.List;

/**
 * <h>AttackGroup</h>
 *
 * THis is a class for holding a group of {@link com.example.dnd.Attack}s and allowing them to
 * be rolled in an instant.
 *
 */


public class AttackGroup {
    private long id; // If we do end up saving them
    private String name;
    private List<Attack> attacks;
    //private Die d20 = new Die(20);

    /**
     * This method will add attacks to the list {@link Character#attacks} without needing
     * to access the member variable, as this method will access it within the class.
     *
     * Must have an {@link Attack} class object passed in to the parameter field.
     * @param attack
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
     * @param attack
     */
    public void removeAttack(Attack attack) {
        attacks.remove(attack);
    }

    //TODO: Figure out implentation for roll w/o targetAC

    /*
    public int roll(int targetAC) {
        int damageTotal = 0;
        for (Attack atk : attacks) {
            int hitRoll = atk.getModHit() + d20.roll();

            if (hitRoll >= targetAC) {
                int damage = atk.rollDamage() + atk.getModDamage();
                damageTotal += damage;
            }
        }

        return damageTotal;
    }
    */

    /********************************** GETTER AND SETTERS ***************************************/

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
