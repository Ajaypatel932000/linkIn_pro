package com.example.linkin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class std_dashboard extends AppCompatActivity  {
    RequestQueue requestQueue;
    ImageView navigation_btn,profile;
    TextView name;
    public String  URL="https://test-ajay.000webhostapp.com/show_std.php";
    String URL1="https://test-ajay.000webhostapp.com/profile_show.php";
    String img_path="https://test-ajay.000webhostapp.com/Image/";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    DrawerLayout mdrawerlayout;
    NavigationView navigationView;
    private List<ListItem_std_show> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_std_dashboard);
       choose.std=1;
       // when drawer navigation choose.std=0
        // dot't miss this statement;
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        navigation_btn = findViewById(R.id.menu_id);
        navigationView = findViewById(R.id.navView);
        mdrawerlayout = findViewById(R.id.drawble);
        recyclerView=findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        navigationView.setItemIconTintList(null);

        View headView = navigationView.getHeaderView(0);
        profile = headView.findViewById(R.id.profile_id);
        //profile.setImageResource(R.drawable.home);

        name = headView.findViewById(R.id.name_id);

        navigation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdrawerlayout.openDrawer(GravityCompat.START);


            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.chang_pass:
                        Intent intent = new Intent(std_dashboard.this, new_password.class);
                        startActivity(intent);
                        break;
                    case R.id.profile:
                        Intent intent2 = new Intent(std_dashboard.this, profile.class);
                        startActivity(intent2);

                        break;


                    case R.id.logout:
                        Intent intent4 = new Intent(std_dashboard.this, login.class);
                        choose.std=0;
                        startActivity(intent4);
                        break;


                    default:
                        Toast.makeText(std_dashboard.this, "Dashboard Toast Executed", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
        list=new ArrayList<>();
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        loadRecyclerViewData();
        load();
    }

   @Override
    public void onBackPressed() {
         AlertDialog diaBox = AskOption();
        diaBox.show();
    }
   private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setIcon(R.drawable.ic_person)
                .setPositiveButton("  Yes  " , new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                })
                .setNegativeButton("  No  ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;

    }

    private void loadRecyclerViewData()
    {
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading Data....");
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("root");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject  o=jsonArray.getJSONObject(i);
                       // Listitem_class_show item=new Listitem_class_show(o.getString("u_id"),o.getString("job_type"),o.getString("message"),o.getString("vacant_seat"))
                        ListItem_std_show listItem=new ListItem_std_show(o.getString("email"),o.getString("num"),o.getString("job_type"),o.getString("message"),o.getString("vacant_seat"),o.getString("salary"));
                        list.add(listItem);
                    }
                    adapter=new MyAdapter_std_show(list,getApplicationContext());
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(std_dashboard.this,"Show Data Error"+e.getMessage(),Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(std_dashboard.this,"Show Data Error"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){

        };
        requestQueue.add(stringRequest);
    }
    public void load()
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject object=new JSONObject(response);
                    JSONArray jsonArray=object.getJSONArray("root");

                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        name.setText(jsonObject.getString("n"));
                        Log.d("name",jsonObject.getString("n"));

                        String img_ = jsonObject.getString("i");
                         //profile.setImageDrawable(null);

                        if (img_ != null)
                            Picasso.with(getApplicationContext()).load(img_path+img_+".jpg").into(profile);
                        else
                            Picasso.with(getApplicationContext()).load(R.drawable.ic_person).into(profile);
                    }
                } catch (JSONException e) {

                    Log.d("json",e.getMessage());
                    Toast.makeText(std_dashboard.this,"profile json exception img"+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(std_dashboard.this,"profile Error Response= "+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();

                params.put("username",login.UserName);
                return params;
            }



        };
        requestQueue.add(stringRequest);


    }

}