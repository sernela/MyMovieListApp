package com.example.bingewatchversion3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddMovieActivity extends AppCompatActivity {

    EditText name, lastseen, turl;
    Button btnAdd, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        name = (EditText)findViewById(R.id.txtName);
        lastseen = (EditText)findViewById(R.id.txtlastSeen);
        turl = (EditText)findViewById(R.id.txtImageUrl);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnBack = (Button)findViewById(R.id.btnBack);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                clearAll();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void insertData() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name.getText().toString());
        map.put("lastseen", lastseen.getText().toString());
        map.put("turl", turl.getText().toString().replaceAll("\\s+", ""));

        //Put url code here s.replaceAll("\\s+", "");
        if (turl.length() > 0) {

        FirebaseDatabase.getInstance().getReference().child("Movies").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddMovieActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddMovieActivity.this, "Error while Insertion", Toast.LENGTH_SHORT).show();
                    }
                });
        }else{
            Toast.makeText(AddMovieActivity.this, "Correct Url not Entered, Try Again", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearAll()
    {
        name.setText("");
        lastseen.setText("");
        turl.setText("");
    }
}