package com.example.dnd;

import java.util.List;

/**
 * <h>Attack Class</h>
 *
 * This entity will contain the information and characteristics of an attack.
 *
 */

public class Attack {
    private long id;
    private String name;
    private List<Die> dice;

    private int modHit;
    private int modDamage;

    Attack(String name) {
        this.name = name;
    }

    Attack(String name, int modHit, int modDamage) {
        this.name = name;
        this.modHit = modHit;
        this.modDamage = modDamage;
    }

    //TODO: implement a rollHit() method somewhere here or in Activity and getHit() from here.

    /**
     * This method will roll each of the dice in {@link Attack#dice} and add the damage modifier
     * {@link Attack#modDamage} to the sum of the dice rolls.
     *
     * @return the sum of the dice rolls and the damage modifier
     */
    public int rollDamage() {
        int damage = modDamage;

        for (Die d : dice) {
            damage += d.roll();
        }

        return damage;
    }

    /********************************** GETTER AND SETTERS ***************************************/

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getModHit() {
        return modHit;
    }

    public int getModDamage() {
        return modDamage;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModHit(int modHit) {
        this.modHit = modHit;
    }

    public void setModDamage(int modDamage) {
        this.modDamage = modDamage;
    }

}
