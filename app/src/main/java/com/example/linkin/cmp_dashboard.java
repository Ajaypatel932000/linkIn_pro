package com.example.linkin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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

import java.util.HashMap;
import java.util.Map;

public class cmp_dashboard extends AppCompatActivity implements  View.OnClickListener{

    ImageView navigation_btn,profile;
    TextView titleText,name;
    String URL="http://10.0.2.2:8244/PROJECT2020/aayesha.asmx/getProfile";
    String img_path="http://10.0.2.2:8244/PROJECT2020/App_Themes/Theme1/assets/images/";
    String URL1="https://test-ajay.000webhostapp.com/profile_show.php";
    public RequestQueue requestQueue;


    DrawerLayout mdrawerlayout;
    CardView AddCard,UpdateCard,DeleteCard,ShowCard,LeaveCard,AttendanceCard;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmp_dashboard);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        requestQueue = Volley.newRequestQueue(getApplicationContext());
         choose.cmp=1;
        mdrawerlayout = findViewById(R.id.drawble);
        navigation_btn = findViewById(R.id.menu_id);
        navigationView = findViewById(R.id.navView);
        AddCard = findViewById(R.id.cardAdd);
        UpdateCard = findViewById(R.id.CardUpdate);
        DeleteCard = findViewById(R.id.CardDelete);
        ShowCard = findViewById(R.id.CardShow);


//    this line code  effect to set icon color as it is  . your selected color .
        navigationView.setItemIconTintList(null);
        AddCard.setOnClickListener(this);
        UpdateCard.setOnClickListener(this);
        DeleteCard.setOnClickListener(this);
        ShowCard.setOnClickListener(this);


        // This memory assign in navigation drawer
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
                        Intent intent = new Intent(cmp_dashboard.this, new_password.class);
                        startActivity(intent);
                        break;
                    case R.id.profile:
                        Intent intent2 = new Intent(cmp_dashboard.this, profile.class);
                        startActivity(intent2);

                        break;


                    case R.id.logout:
                        Intent intent4 = new Intent(cmp_dashboard.this, login.class);
                        choose.cmp=0;
                        startActivity(intent4);
                        break;


                    default:
                        Toast.makeText(cmp_dashboard.this, "Dashboard Toast Executed", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

      load();
    }

    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.cardAdd:
                Intent intent=new Intent(cmp_dashboard.this,Add.class);
                startActivity(intent);
                break;
            case R.id.CardUpdate:
                Intent intent1=new Intent(cmp_dashboard.this,Update.class);
                startActivity(intent1);
                break;

            case R.id.CardDelete:
                Intent intent2=new Intent(cmp_dashboard.this,Delete.class);
                startActivity(intent2);
                break;
            case R.id.CardShow:
                Intent intent3=new Intent(cmp_dashboard.this,Show.class);
                startActivity(intent3);
                break;


            default:
                Toast.makeText(cmp_dashboard.this,"CardView  Click Default Case Execute",Toast.LENGTH_LONG).show();
                break;
        }
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

                        if (img_ != null)
                            Picasso.with(getApplicationContext()).load(img_path+img_+".jpg").into(profile);
                        else
                            Picasso.with(getApplicationContext()).load(R.drawable.ic_close_white).into(profile);
                    }
                } catch (JSONException e) {

                    Log.d("json",e.getMessage());
                    Toast.makeText(cmp_dashboard.this,"profile json exception img"+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(cmp_dashboard.this,"profile Error Response= "+error.getMessage(),Toast.LENGTH_LONG).show();
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