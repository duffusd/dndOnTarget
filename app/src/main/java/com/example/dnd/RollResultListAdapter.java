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

        // display the hit result
        attackName.setText(result.getAttack().getName());
        hit.setText(String.format("D20: %d + %d Mod = %d", result.getHit(), result.getAttack().getModHit(), result.getHitResult()));

        // display the damage result if hit was greater than AC
        if(result.getCanDamage() == true){


            int dieType = result.getAttack().getDie().getSides();
            int numOfDie = result.getAttack().getNumOfDice();

            System.out.println("numOfDamages: " + result.getDamages().size());


            // when only one die was used to roll for damage
            if(result.getDamages().size() == 1){

                damage.setText(String.format("Damage Roll: %dD%d (%d) + %d Mod", numOfDie, dieType, result.getDamages().get(0), result.getAttack().getModDamage()));

            } else {

                StringBuilder damagesBreakdown = new StringBuilder();
                int numOfDamages = result.getDamages().size();

                for(int i = 0; i < numOfDamages - 1; i++){
                    damagesBreakdown.append(result.getDamages().get(i).toString() + ", ");
                }

                System.out.println("damagesBreakdown: " + damagesBreakdown.length());
                damagesBreakdown.append(result.getDamages().get(numOfDamages - 1));

                damage.setText(String.format("Damage Roll: %dD%d (%s) + %d Mod", numOfDie, dieType, damagesBreakdown, result.getAttack().getModDamage()));
            }

            // display the total damage
            totalDamage.setText(String.format("Total Damage: %d", result.getTotalDamage()));

        } else { // when the AC was greater than hit
            damage.setText("Fail AC");
        }
      
        return contentView;


    }


}
