package com.example.linkin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class student_details extends AppCompatActivity {

    TextView msg,salary,j_t,email,num,vacant_seat;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

       msg=findViewById(R.id.m_id);
       salary=findViewById(R.id.s_id);
       j_t=findViewById(R.id.j_id);
       email=findViewById(R.id.e_id);
       num=findViewById(R.id.t_id);
       vacant_seat=findViewById(R.id.vs_id);
        back=findViewById(R.id.show_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(student_details.this,std_dashboard.class);
                startActivity(intent);
            }
        });
       salary.setText(getIntent().getStringExtra("salary"));
       j_t.setText(getIntent().getStringExtra("jt"));
       email.setText(getIntent().getStringExtra("email"));
       num.setText(getIntent().getStringExtra("num"));
       vacant_seat.setText(getIntent().getStringExtra("vs"));
       msg.setText(getIntent().getStringExtra("msg"));
    }
}