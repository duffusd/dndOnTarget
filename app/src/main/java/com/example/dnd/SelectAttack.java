package com.example.dnd;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dnd.data.AttackContract;
import com.example.dnd.data.AttackDatabaseHelper;
import com.example.dnd.data.CharacterDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class SelectAttack extends AppCompatActivity {

    AttackDatabaseHelper myAttackDB;

    private List<Model> mModelList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    public static List<Integer> attackIdsForRoll = new ArrayList<>();  // new list to store selected attack's ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_attack);

        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new com.example.dnd.RecyclerViewAdapter(getListData());
        LinearLayoutManager manager = new LinearLayoutManager(SelectAttack.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        goToRoller();
    }


    /**
     * Sends user to Roller activity when clicks the Attack! button
     */
    private void goToRoller() {
        Button goToRollerButton = findViewById(R.id.attackbtn);
        goToRollerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Number of attacks selected for Roll Attack: ", String.valueOf(attackIdsForRoll.size()));

                //List<String> attackStringList = RecyclerViewAdapter.attackStringList;
                //ArrayList<String> myList = new ArrayList<String>();
                startActivity(new Intent(SelectAttack.this, Roller.class));


            }
        });

    }

    /**
     * getListData gets the list of all of the Attacks from selected Character
     *
     * @return
     */
    private List<Model> getListData() {
        List<Integer> attackIds = MainActivity.getCharacter().getAttackIdsForCharacter();
        myAttackDB = new AttackDatabaseHelper(this);
        mModelList = new ArrayList<>();
        Cursor dataAttack = (Cursor) myAttackDB.getAttackContents(attackIds);

/*        if(dataAttack.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }else{
            while(dataAttack.moveToNext()){
                mModelList.add(new Model(dataAttack.getString(1)));
*/
        if (dataAttack.getCount() == 0) {
            Toast.makeText(this, String.format("There are no attacks for %s!", MainActivity.getCharacter().getName()), Toast.LENGTH_LONG).show();
        } else {
            while (dataAttack.moveToNext()) {

                mModelList.add(new Model(dataAttack.getString(dataAttack.getColumnIndex(AttackContract.getIdColName()))));
            }
        }


        return mModelList;
    }


    public static List<Integer> getAttackIdsForRoll() {
        return attackIdsForRoll;
    }
}