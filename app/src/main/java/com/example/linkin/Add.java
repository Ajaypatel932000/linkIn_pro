package com.example.linkin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Add extends AppCompatActivity  implements View.OnClickListener{

    RequestQueue requestQueue;
    String URL="https://test-ajay.000webhostapp.com/insert_add.php";
    EditText job_type,seat,message,salary;
    Button submit_btn;
    ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        job_type=findViewById(R.id.job_type_id);
        seat=findViewById(R.id.seat_id);
        message=findViewById(R.id.msg_id);
        submit_btn=findViewById(R.id.leave_btn_id);
        back_btn=findViewById(R.id.cancle_btn);
        salary=findViewById(R.id.salary_id);
        requestQueue= Volley.newRequestQueue(getApplicationContext());

        back_btn.setOnClickListener(this);
        submit_btn.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.cancle_btn:
                Intent intent=new Intent(Add.this,cmp_dashboard.class);
                startActivity(intent);
                break;

             case R.id.leave_btn_id:

                 AddJobDetails();
                 break;

            default:
                Toast.makeText(Add.this,"Add page Defult case Executed ",Toast.LENGTH_LONG).show();
                break;

        }
    }
    public  void AddJobDetails()
    {
        if(checkEmpty())
        {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        String ans = object.getString("key");
                        if (ans.equalsIgnoreCase("true")) {
                            job_type.setText(null);
                            seat.setText(null);
                            message.setText(null);
                            salary.setText(null);
                            Toast.makeText(Add.this, "Add Applied ", Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                    }
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(Add.this, "Volley Response Error =" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                // send the data
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("job_type",job_type.getText().toString());
                    params.put("seat",seat.getText().toString().trim());
                    params.put("msg",message.getText().toString());
                    params.put("salary",salary.getText().toString());
                    params.put("username",login.UserName);
                    Log.i("values",login.UserName+job_type.getText().toString());
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }


    }


    public boolean checkEmpty() {

        if (job_type.getText().toString().trim().isEmpty()) {


            job_type.setError("Field Can't be empty");
            return false;

        } else if (seat.getText().toString().trim().isEmpty()) {

            seat.setError("Field Can't be empty");
            return false;

        } else if (message.getText().toString().trim().isEmpty()) {

            message.setError("Field Can't be empty");
            return false;


        } else if(salary.getText().toString().trim().isEmpty())
        {
            salary.setError("Field Can't be empty");
             return false;
        }else
        {
            return true;
        }

    }


}