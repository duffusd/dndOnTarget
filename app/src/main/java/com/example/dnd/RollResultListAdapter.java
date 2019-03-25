package com.example.dnd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;


/**
 * <h>RollResultListAdapter</h>
 *
 * An adapter class used to display the result from rolling the attack(s)
 */
public class RollResultListAdapter extends ArrayAdapter<RollResult> {

    private LayoutInflater mInflater;
    private List<RollResult> results;
    private int mViewResourceId;


    public RollResultListAdapter(Context context, int textViewResourceId, List<RollResult> results) {
        super(context, textViewResourceId, results);
        this.results = results;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }


    public View getView(int position, View contentView, ViewGroup parents) {

        contentView = mInflater.inflate(mViewResourceId, null);

        RollResult result = results.get(position);

        TextView attackName = contentView.findViewById(R.id.rolled_attack_name);
        TextView hit = contentView.findViewById(R.id.roll_hit);
        TextView damage = contentView.findViewById(R.id.roll_damage);
        TextView totalDamage = contentView.findViewById(R.id.roll_total_damage);

        attackName.setText(result.getAttackName());
        hit.setText(String.format("D20: %d + %d Mod = %d", result.getHit(), result.getHitModifier(), result.getHitResult()));

        if(result.getCanDamage() == true){
            damage.setText(String.format("Damage Roll: (%d) + %d Mod", result.getDamageResult(), result.getDamageModifier()));
            totalDamage.setText(String.format("Total Damage: %d", result.getFinalDamage()));
        } else {
            damage.setText("Fail AC");
        }

        return contentView;


    }


}
