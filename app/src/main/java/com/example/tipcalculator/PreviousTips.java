package com.example.tipcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PreviousTips extends AppCompatActivity {

    private static final String TAG = "PreviousTip";
    private RecyclerView rvPreviousTips;
    private ArrayList<TipsList> tipsList = new ArrayList<>();
    private TipsDatabaseHelper tipsDb;
    private ImageView ivEmptyFolder;
    private TextView tvEmptyFolder;

    private TipsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_tips);


        rvPreviousTips = findViewById(R.id.rvPreviousTips);
//        ivEmptyFolder = findViewById(R.id.ivEmptyFolder);
        tvEmptyFolder = findViewById(R.id.tvEmptyFolder);
        tipsDb = new TipsDatabaseHelper(PreviousTips.this);

        storeDataInArrays();

        adapter = new TipsAdapter(PreviousTips.this, tipsList);
        adapter.setOnItemClickListener(new TipsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Toast.makeText(PreviousTips.this, "item clicked" + position, Toast.LENGTH_SHORT).show();
            }
        });
        rvPreviousTips.setAdapter(adapter);

        rvPreviousTips.setLayoutManager(new LinearLayoutManager(this));

    }

    private void storeDataInArrays() {
        Cursor cursor = null;
        if (tipsDb != null) {
            cursor = tipsDb.readAllData();
        }

        if (cursor.getCount() == 0) {
//            ivEmptyFolder.setVisibility(View.VISIBLE);
            tvEmptyFolder.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                tipsList.add(new TipsList(
                                cursor.getInt(1),
                                cursor.getDouble(2),
                                cursor.getDouble(3),
                                cursor.getInt(4)
                        )


                );
            }
//            ivEmptyFolder.setVisibility(View.GONE);
            tvEmptyFolder.setVisibility(View.GONE);
        }

    }

    public void createTips(int tips) {


//        for(int i=0;i<tips;i++){
//            tipsList.add(new TipsList((int)(Math.random()*100),(int)(Math.random()*10),(int)(Math.random()*25)));
//        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.previous_tips_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {
            confirmDialog();
        }

        return super.onOptionsItemSelected(item);
    }

//    private void uploadToFirebase() {
//
//        CollectionReference cr = firestore.collection("Tips");
//        Map<String, Object> tips = new HashMap<>();
//        Cursor cursor = tipsDb.readAllData();
//        if(cursor.getCount() != 0){
//            while (cursor.moveToNext()){
//
//            }
//        }
//
//        tips.put("tip", cursor.getInt(1));
//        tips.put("tip_total", cursor.getDouble(2));
//        tips.put("tip_per_person", cursor.getDouble(3));
//        tips.put("split_no", cursor.getInt(4));
//        cr.add(tips).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Log.d(TAG, "Your data has been added to Firebase Firestore" + documentReference.getId());
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.w(TAG, "some error occured" + e);
//            }
//        });
//    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete a Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TipsDatabaseHelper myDB = new TipsDatabaseHelper(PreviousTips.this);
                myDB.deleteAllData();
                Intent intent = new Intent(PreviousTips.this, MainActivity.class);
                startActivity(intent);
                finish();

                Toast.makeText(PreviousTips.this,"data deleted", Toast.LENGTH_SHORT).show();

                finish();

//                Intent intent = new Intent(PreviousTips.this, MainActivity.class);
//                startActivity(intent);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }
}