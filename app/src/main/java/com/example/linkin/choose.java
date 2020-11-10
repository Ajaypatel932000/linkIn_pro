package com.example.linkin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class choose extends AppCompatActivity {

    ImageButton studentObj,companyObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        studentObj=findViewById(R.id.std_id);
        companyObj=findViewById(R.id.cmp_id);

        studentObj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent std_intent=new Intent(choose.this,login.class);
                startActivity(std_intent);
            }
        });


        companyObj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cmp_intent=new Intent(choose.this,login.class);
                startActivity(cmp_intent);
            }
        });
    }
}