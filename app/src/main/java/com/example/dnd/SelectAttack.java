package com.example.dnd;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.dnd.data.AttackDatabaseHelper;
import com.example.dnd.data.CharacterDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class SelectAttack extends AppCompatActivity {

    CharacterDatabaseHelper myCharacterDB;
    AttackDatabaseHelper myAttackDB;

    private List<Model> mModelList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_attack);



        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new com.example.dnd.RecyclerViewAdapter(getListData());
        LinearLayoutManager manager = new LinearLayoutManager(SelectAttack.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        List<Integer> attackIds = MainActivity.getCharacter().getAttackIdsForCharacter();

        // Create an attack object for each attackIds, then add it to the attack list of the character object
        if(attackIds.size() > 0){
            for (int i = 0; i < attackIds.size(); i++){
                System.out.println("HERE: " + attackIds.get(i));
                Attack attack = new Attack(this);
                attack.setAttack(attackIds.get(i));
                MainActivity.getCharacter().addAttack(attack);
            }
        }




    }

    //recycleview multi select
    private List<Model> getListData() {
        //populate an ArrayList<String> from the database and then view it

        //Attacks
        myAttackDB = new AttackDatabaseHelper(this);
        mModelList = new ArrayList<>();
        Cursor dataAttack = myAttackDB.getAttackContents();

        if(dataAttack.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }else{
            while(dataAttack.moveToNext()){
                mModelList.add(new Model(dataAttack.getString(1)));
            }
        }


        return mModelList;
    }
}
