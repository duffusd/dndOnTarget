package com.example.dnd;

import android.content.Context;
import android.util.Log;
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
 * @author Atsuko Takanabe
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

        boolean canDoDamage = result.getCanDamage();

        // display the hit result
        attackName.setText(result.getAttack().getName());

        if (result.getHit() == 20){

            canDoDamage = true;

            hit.setText(String.format("D20: %d *** Critical Hit ***", result.getHit()));

        }else {


            hit.setText(String.format("D20: %d + %d Mod = %d", result.getHit(), result.getAttack().getModHit(), result.getHitResult()));
        }


        // display the damage result if hit was greater than AC
        if(canDoDamage == true){


            int dieType = result.getAttack().getDie().getSides();
            int numOfDie = result.getAttack().getNumOfDice();

            // when only one die was used to roll for damage
            if(result.getDamages().size() == 1){

                damage.setText(String.format("Damage Roll: %dD%d (%d) + %d Mod", numOfDie, dieType, result.getDamages().get(0), result.getAttack().getModDamage()));

            } else {

                int numOfDamages = 0;

                if (result.getHit() == 20) {

                    numOfDamages = result.getDamages().size();
                    numOfDie = numOfDie * 2;
                }
                else{

                    numOfDamages = result.getDamages().size();
                }

                StringBuilder damagesBreakdown = new StringBuilder();
                //int numOfDamages = result.getDamages().size();

                Log.e("Number of Damages: ", "is " + numOfDamages);

                for(int i = 0; i < numOfDamages - 1; i++){
                    damagesBreakdown.append(result.getDamages().get(i).toString() + ", ");
                }


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
