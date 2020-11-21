package com.example.linkin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.linkin.mail.JavaMail;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class std_res extends AppCompatActivity {
    ImageView back_btn, logo;
    TextView titleText;
    Button next_btn;
    RadioGroup radioGroup;
    RequestQueue requestQueue;
    String URL = "https://test-ajay.000webhostapp.com/insert_std_res.php"; //"http://10.0.2.2:8244";
    String URL1="https://test-ajay.000webhostapp.com/check_UserName.php";
    EditText e_userName, e_pass, e_confrim_pass, e_student_no, e_email,name;
    String ans;
    RadioButton male, female;
    String gender = "";

    public String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_std_res);
        back_btn = findViewById(R.id.back_button);
        next_btn = findViewById(R.id.next_btn_id);
        titleText = findViewById(R.id.sign_up);
        logo = findViewById(R.id.logo);

        name=findViewById(R.id.Name);
        e_userName = findViewById(R.id.etuserName);
        e_email = findViewById(R.id.etEmail);
        e_pass = findViewById(R.id.etpass);
        e_confrim_pass = findViewById(R.id.etconfirmpass);
        e_student_no = findViewById(R.id.etphone);
        male = findViewById(R.id.radioButton);
        female = findViewById(R.id.radioButton2);


        radioGroup = findViewById(R.id.radio);


        requestQueue = Volley.newRequestQueue(getApplicationContext());
        e_userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //Toast.makeText(sign_up.this,"Focus",Toast.LENGTH_LONG).show();
                } else {

                    if (!e_userName.getText().toString().trim().isEmpty()) {
                        //   Toast.makeText(sign_up.this, "Lost Focus", Toast.LENGTH_LONG).show();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject object = new JSONObject(response);
                                    ans = object.getString("key");
                                    if (ans.equalsIgnoreCase("true")) {
                                        e_userName.setError("UserName already Exits");

                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(std_res.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(std_res.this, "On ErrorReponse Execute " + error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("username", e_userName.getText().toString().trim());
                                return params;
                            }

                        };
                        requestQueue.add(stringRequest);


                    }

                }

            }

        });


        // back to login screen code

        back_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(std_res.this, login.class);
                startActivity(intent);
            }
        });
        male.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "0";
                female.setError(null);
            }
        }));
        female.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "1";
                female.setError(null);
            }
        }));

    }
    public void nextSignUpScreen(View view) {
        if (checkEmpty() && validation() ) { //&& !ans

               email=e_email.getText().toString();
            // here add the logic of registration
            Log.i("USERNAME" ,e_userName.getText().toString());
            Log.i("pass",e_pass.getText().toString());
            Log.i("Phone",e_student_no.getText().toString());
            Log.i("email",e_email.getText().toString());
            StringRequest stringRequest=new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject object;
                        object = new JSONObject(response);
                        ans = object.getString("key");
                        if(ans.equalsIgnoreCase("true"))
                        {
                            sendMail();
                            Intent intent=new Intent(std_res.this,login.class);
                            startActivity(intent);
                        }else
                        {

                            Toast.makeText(std_res.this,"Registration Unsuccessful",Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        Toast.makeText(std_res.this,"Registration JSON EXCEPTION"+e.getMessage(),Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(std_res.this,"Registration Volley Error"+error.getMessage(),Toast.LENGTH_LONG).show();
                    Log.e("error", String.valueOf(error.networkResponse));

                }
            }){
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", e_userName.getText().toString().trim());
                    params.put("fullname",name.getText().toString());
                    params.put("pwd",e_pass.getText().toString().trim());
                    params.put("gender",gender);
                    params.put("num",e_student_no.getText().toString().trim());
                    params.put("email",e_email.getText().toString().trim());
                    params.put("r_id", String.valueOf(2));
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }


    public boolean checkEmpty() {

        if (e_userName.getText().toString().trim().isEmpty()) {
            e_userName.setError("Field Can't be empty");
            return false;

        }else if(name.getText().toString().trim().isEmpty()){
            name.setError("Field Can't be empty");
            return false;
        }
        else if (e_email.getText().toString().trim().isEmpty()) {

            e_email.setError("Field Can't be empty");
            return false;

        } else if (e_student_no.getText().toString().trim().isEmpty()) {

            e_student_no.setError("Field Can't be empty");
            return false;


        } else if (e_pass.getText().toString().trim().isEmpty()) {
            e_pass.setError("Field Can't be empty");
            return false;

        } else if (e_confrim_pass.getText().toString().trim().isEmpty()) {
            e_confrim_pass.setError("Field Can't be empty");
            return false;

        } else {
            return true;
        }

    }


    public boolean validation() {
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(e_email.getText().toString().trim());

        Pattern pattern1;
        Matcher matcher1;
        String PASSWORD_PATTERN = "^(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        // we create pattern
        pattern1 = Pattern.compile(PASSWORD_PATTERN);
        //create object of matcher
        matcher1 = pattern1.matcher(e_pass.getText().toString().trim());

        if (!matcher.matches()) {
            e_email.setError("Invalid Email");
            return false;

        } else if (!e_student_no.getText().toString().trim().matches("[0-9]{10}")) {
            e_student_no.setError("Invalid Number");
            return false;

        }
        if (!matcher1.matches()) {
            e_pass.setError("Password length minimum: 8. Non-alphanumeric characters required: 1.");
            e_pass.getText().clear();

            return false;

        } else if (!e_pass.getText().toString().trim().equals(e_confrim_pass.getText().toString().trim())) {
            e_confrim_pass.setError("Invalid Password");
            e_confrim_pass.getText().clear();
            return false;

        } else {
            return true;
        }
    }
    public void sendMail() {
        String subject = "UserId";
        String message = "Your UserId is " + e_userName.getText().toString();
        JavaMail javaMailAPI = new JavaMail(this, email, subject, message);
        javaMailAPI.execute();
    }


}