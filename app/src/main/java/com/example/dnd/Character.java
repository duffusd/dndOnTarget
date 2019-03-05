package com.example.dnd;

import java.util.List;

public class Character {
    private long id;
    private String name;
    private List<Attack> attacks;

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
