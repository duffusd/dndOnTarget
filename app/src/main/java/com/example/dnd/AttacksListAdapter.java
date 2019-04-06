package com.example.dnd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * <h>AttackListAdapter</h>
 *
 * An Adapter class specific for displaying attacks of the chosen character in CharacterAddEdit activity
 * @author Atsuko Takanabe
 */
public class AttacksListAdapter extends ArrayAdapter<Attack> {

    private LayoutInflater mInflater;
    private List<Attack> mAttacks;
    private int mViewResourceId;

    public AttacksListAdapter(Context context, int textViewResourceId, List<Attack> attacks) {

        super(context, textViewResourceId, attacks);
        mAttacks = attacks;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View contentView, ViewGroup parents) {

        contentView = mInflater.inflate(mViewResourceId, null);
        Attack attack = mAttacks.get(position);

        TextView attackName = contentView.findViewById(R.id.attackNameText);
        TextView hitMod = contentView.findViewById(R.id.attackHitModText);
        TextView damageMod = contentView.findViewById(R.id.attackDamageModText);
        TextView dice = contentView.findViewById(R.id.attackDiceText);
        TextView numDice = contentView.findViewById(R.id.attackNumOfDiceText);

        attackName.setText(attack.getName());
        hitMod.setText(attack.getModHit().toString());
        damageMod.setText(attack.getModDamage().toString());
        dice.setText(attack.getDie().getSides().toString());
        numDice.setText(attack.getNumOfDice().toString());

        return contentView;


    }

}
