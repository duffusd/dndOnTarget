package com.example.dnd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dnd.data.AttackContract;
import com.example.dnd.data.AttackDatabaseHelper;
import com.example.dnd.data.CharacterDatabaseHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SelectAttack extends AppCompatActivity {

    AttackDatabaseHelper myAttackDB;

    private List<Model> mModelList;
    private List<Attack> mAttackList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    public static List<Attack> attacksForRoll = new ArrayList<>();
    private EditText targetACText;
    public static final String targetAcSharedPreference = "TargetAcSharedPref";
    public static final String targetAC = "TargetAC";
    public static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_attack);

        mRecyclerView = findViewById(R.id.recycler_view);
        targetACText = findViewById(R.id.targetACEditText);
        mAdapter = new com.example.dnd.RecyclerViewAdapter(getListData());
        LinearLayoutManager manager = new LinearLayoutManager(SelectAttack.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        // set up shared preferences for this app
        sharedPreferences = getSharedPreferences(targetAcSharedPreference, Context.MODE_PRIVATE);


        if(MainActivity.getCharacter().getAttacks().size() == 0){

            Toast.makeText(SelectAttack.this,
                    String.format("No attacks available for %s",
                            MainActivity.getCharacter().getName()), Toast.LENGTH_LONG).show();

            MainActivity.getCharacter().clearCharacter();

        }else{

            goToRoller();
        }
    }


    /**
     * Sends user to Roller activity when clicks the Attack! button
     */
    private void goToRoller() {

        Button goToRollerButton = findViewById(R.id.attackbtn);
        goToRollerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Number of attacks selected for Roll Attack: ", String.valueOf(attacksForRoll.size()));

                Boolean validTargetAC = validateTargetAC();

                //List<String> attackStringList = RecyclerViewAdapter.attackStringList;
                //ArrayList<String> myList = new ArrayList<String>();

                if (attacksForRoll.size() == 0){
                    Toast.makeText(SelectAttack.this, "Please select at least one attack", Toast.LENGTH_LONG).show();
                } else if (!validTargetAC){
                    Toast.makeText(SelectAttack.this, "What is the target AC?", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(SelectAttack.this, Roller.class));
                }


            }
        });

    }

    private boolean validateTargetAC(){
        // set up targetAC shared preference and get the value
        Integer targetAcNum = Integer.parseInt(targetACText.getText().toString().isEmpty() ? "0" : targetACText.getText().toString());

        // first, make sure targetAC is not empty
        switch (targetAcNum){
            case 0:
                return false;
            default:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(targetAC, targetAcNum);
                editor.commit();
                return true;
        }

    }

    /**
     * getListData gets the list of all of the Attacks from selected Character
     *
     * @return
     */
    private List<Model> getListData() {

        mModelList = new ArrayList<>();

        if (MainActivity.getCharacter().getAttacks().size() > 0){

            for (Attack attack : MainActivity.getCharacter().getAttacks()){

                mModelList.add(new Model(attack.getName()));

            }
        }

        return mModelList;

        /*
        List<Integer> attackIds = MainActivity.getCharacter().getAttackIdsForCharacter();
        myAttackDB = new AttackDatabaseHelper(this);
        mModelList = new ArrayList<>();
        Cursor dataAttack = (Cursor) myAttackDB.getAttackContents(attackIds);

        if (dataAttack.getCount() == 0) {
            Toast.makeText(this, String.format("There are no attacks for %s!", MainActivity.getCharacter().getName()), Toast.LENGTH_LONG).show();
        } else {
            while (dataAttack.moveToNext()) {

                mModelList.add(new Model(dataAttack.getString(dataAttack.getColumnIndex(AttackContract.getIdColName()))));
            }
        }


        return mModelList;
    }
    */

    }



    public static List<Attack> getAttacksForRoll(){
        return attacksForRoll;
    }
}