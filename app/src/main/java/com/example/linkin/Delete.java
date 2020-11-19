package com.example.linkin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

public class Delete extends AppCompatActivity {
    RequestQueue requestQueue;
    EditText id;
    Button send_btn;
    ImageView back_btn;

    String URL="https://test-ajay.000webhostapp.com/delete.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        id = findViewById(R.id.delete_c_id);
        send_btn = findViewById(R.id.send_btn_id);
        back_btn=findViewById(R.id.cancle_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Delete.this,cmp_dashboard.class);
                startActivity(intent);
            }
        });
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            String  ans = object.getString("key");
                            if (ans.equalsIgnoreCase("true")) {

                                id.setText(null);
                                Toast.makeText(Delete.this,"Delete Record ",Toast.LENGTH_LONG).show();

                            } else
                            {

                                id.setError("Invalid Id");
                                Toast.makeText(Delete.this, "User Id Not Found", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {


                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Delete.this, "Phone_number.java =" + error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                })
                {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", id.getText().toString().trim());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);

            }
        });

    }
}