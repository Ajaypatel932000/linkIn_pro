package com.example.linkin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class new_password extends AppCompatActivity implements View.OnClickListener {
    EditText et_pass,et_confirm_pass;
    Button restButton;
    ImageView backButton;
    DrawerLayout mdrawerlayout;
    String URL="https://test-ajay.000webhostapp.com/change_pwd.php";
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        requestQueue= Volley.newRequestQueue(getApplicationContext());
        et_pass=findViewById(R.id.pass);
        et_confirm_pass=findViewById(R.id.confirm_pass);
        restButton=findViewById(R.id.reset_btn_id);
        backButton=findViewById(R.id.cancle_btn);
        mdrawerlayout = findViewById(R.id.drawble);
        restButton.setOnClickListener(this);
        backButton.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.reset_btn_id:

                if(validation()) {
                    changePassword();

                }
                break;



            case R.id.cancle_btn:
                if(choose.cmp==1) {
                    Intent intent = new Intent(new_password.this, cmp_dashboard.class);
                    startActivity(intent);
                }else
                {
                    Intent intent = new Intent(new_password.this, std_dashboard.class);
                    startActivity(intent);
                }
                break;

            default:
                Toast.makeText(new_password.this,"Swich Case Defalut execute in Change_password",Toast.LENGTH_LONG).show();
                break;
        }


    }


    public boolean validation()
    {
        Pattern pattern1;
        Matcher matcher1;
        String PASSWORD_PATTERN = "^(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        // we create pattern
        pattern1 = Pattern.compile(PASSWORD_PATTERN);
        //create object of matcher
        matcher1 = pattern1.matcher(et_pass.getText().toString().trim());
        if (!matcher1.matches()) {
            et_pass.setError("Password length minimum: 8. Non-alphanumeric characters required: 1.");
            et_pass.getText().clear();

            return false;

        } else if (!et_pass.getText().toString().trim().equals(et_confirm_pass.getText().toString().trim())) {
            et_confirm_pass.setError("Invalid Password");
            et_confirm_pass.getText().clear();
            return false;

        } else {
            return true;
        }
    }

    public void changePassword()
    {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject object = new JSONObject(response);
                    String ans=object.getString("key");
                    if(ans.equalsIgnoreCase("true"))
                    {
                        Intent intent = new Intent(new_password.this, cmp_dashboard.class);
                        startActivity(intent);

                    }else
                    {
                        Toast.makeText(new_password.this,"Please try again",Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {

                    Toast.makeText(new_password.this,"ChagePassword ="+e.getMessage(),Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(new_password.this,"error ="+error.getMessage(),Toast.LENGTH_LONG).show();

            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError
            {
                Map<String,String> params=new HashMap<>();
                params.put("username",login.UserName);
                params.put("pwd",et_pass.getText().toString().trim());
                return params;
            }

        };
        requestQueue.add(stringRequest);

    }


}