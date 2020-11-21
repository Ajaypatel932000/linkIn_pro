package com.example.linkin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class login extends AppCompatActivity {

    TextView pass_link;
    Button login_btn, registration;
    EditText et_name, et_password;
    RequestQueue requestQueue;
    String pass, userName, URL = "https://test-ajay.000webhostapp.com/login_script.php";
    String ans;
   public static String UserName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        pass_link = findViewById(R.id.link_id);
        login_btn = findViewById(R.id.login_id);
        et_name = findViewById(R.id.etEmail);
        et_password = findViewById(R.id.etpass);
        registration = findViewById(R.id.reg_id);


        requestQueue = Volley.newRequestQueue(getApplicationContext());
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    login_fun();

            }
        });
        pass_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent=new Intent(login.this,forgot_password.class);
            startActivity(intent);
            }
        });

    }
    public void SignUp(View view) {
        if(choose.isCompany) {

            Intent intent = new Intent(login.this, cmp_res.class);
            startActivity(intent);
        }else
        {
            Intent intent = new Intent(login.this,std_res.class);
            startActivity(intent);
        }
    }
    private boolean validateUserName()
    {
        if(userName.isEmpty())
        {
            et_name.setError(" UserName Requried ",getDrawable(R.drawable.ic_email));
            return false;
        }else if(pass.isEmpty())
        {
            et_password.setError("Password Can't  Empty");
            return false;
        }else
        {
            return true;
        }

    }

   public void login_fun() {

        userName = et_name.getText().toString().trim();
        pass = et_password.getText().toString().trim();

        if (validateUserName()) {
            Toast.makeText(this, "name =" + userName + " password= " + pass, Toast.LENGTH_LONG).show();
            ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Wait a While....");
            progressDialog.show();
            StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
                 JSONObject object;
                 try {
                     object = new JSONObject(response);

                 ans = object.getString("key");
                 if(ans.equalsIgnoreCase("company"))
                 {

                     Intent intent=new Intent(login.this,cmp_dashboard.class);
                     UserName=userName;
                     progressDialog.dismiss();
                     startActivity(intent);
                 }else if(ans.equalsIgnoreCase("student"))
                 {

                     Intent intent=new Intent(login.this,std_dashboard.class);
                     UserName=userName;
                     progressDialog.dismiss();
                    startActivity(intent);

                    // Toast.makeText(login.this,"Student Page",Toast.LENGTH_LONG).show();

                 }else if(ans.equalsIgnoreCase("invalid"))
                 {
                     progressDialog.dismiss();
                     Toast.makeText(login.this,"Login Unsuccessful",Toast.LENGTH_LONG).show();

                 }else
                 {
                     progressDialog.dismiss();
                     Toast.makeText(login.this,"Login else Part",Toast.LENGTH_LONG).show();

                 }

             } catch (JSONException e) {
                     progressDialog.dismiss();
               //  Toast.makeText(login.this,"Login JSON EXCEPTION"+e.getMessage(),Toast.LENGTH_LONG).show();
                 e.printStackTrace();
             }

         }
        }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               progressDialog.dismiss();
               Toast.makeText(login.this,"Login Volley Error"+error.getMessage(),Toast.LENGTH_LONG).show();
               Log.e("error", String.valueOf(error.networkResponse));

           }
         }){
             // send the data
             @Override
             protected Map<String, String> getParams() throws AuthFailureError {
                 Map<String, String> params = new HashMap<>();
                 params.put("u_name", userName);
                 params.put("pwd", pass);

                 return params;
             }

         };
            requestQueue.add(stringRequest);

        }

    }
}