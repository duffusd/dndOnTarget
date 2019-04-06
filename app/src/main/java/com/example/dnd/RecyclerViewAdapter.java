package com.example.dnd;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<Model> mModelList;

    public RecyclerViewAdapter(List<Model> modelList) {

        mModelList = modelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Model model = mModelList.get(position);
        final Attack attack = MainActivity.getCharacter().getAttacks().get(position);

        holder.textView.setText(attack.getName()
                + " -- Dice + Mod: "
                + attack.getNumOfDice()
                + "D" + attack.getDie().getSides() +
                " + " + attack.getModDamage()
                + " Hit Mod: " + attack.getModHit());

        holder.view.setBackgroundColor(model.isSelected() ? Color.LTGRAY : Color.DKGRAY);

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                model.setSelected(!model.isSelected());
                holder.view.setBackgroundColor(model.isSelected() ? Color.LTGRAY : Color.DKGRAY);

                if(model.isSelected() == true){

                    SelectAttack.getAttacksForRoll().add(attack);

                } else {

                    SelectAttack.getAttacksForRoll().remove(attack);

                }

                Log.e(" Adding List Item", "Item that is in the List " + SelectAttack.attacksForRoll);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView textView;

        private MyViewHolder(View itemView) {

            super(itemView);
            view = itemView;
            textView = (TextView) itemView.findViewById(R.id.text_view);

        }
    }
}