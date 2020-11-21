package com.example.linkin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Show extends AppCompatActivity {
RequestQueue requestQueue;
    public String  URL="https://test-ajay.000webhostapp.com/show_data.php";
  private RecyclerView recyclerView;
  private RecyclerView.Adapter adapter;
  ImageView back_btn;
  private List<Listitem_class_show> listItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

       recyclerView=findViewById(R.id.recyclerView);
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
       back_btn=findViewById(R.id.show_back);
       listItems=new ArrayList<>();
       back_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(Show.this,cmp_dashboard.class);
               startActivity(intent);

           }
       });

    requestQueue= Volley.newRequestQueue(getApplicationContext());
        loadRecyclerViewData();

    }
    private void loadRecyclerViewData() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data....");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("root");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject o = jsonArray.getJSONObject(i);
                        Listitem_class_show item = new Listitem_class_show(o.getString("u_id"), o.getString("job_type"), o.getString("message"), o.getString("vacant_seat"), o.getString("salary"));
                        listItems.add(item);
                    }
                    adapter = new MyAdapter_AddClass(listItems, getApplicationContext());
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Show.this, "Show Data Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Show.this, "Show Data Error" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", login.UserName);
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }
}