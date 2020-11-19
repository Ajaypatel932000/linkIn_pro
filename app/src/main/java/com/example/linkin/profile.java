package com.example.linkin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class profile extends AppCompatActivity {
    //CircularImageView img_btn;
    ImageView select_img,img_btn;
     Bitmap bitmap;
     final int Image_Request=1;
    EditText et_name,et_email,et_number;
    Button update_btn;
      final int IMG_REQUEST=1;
ImageView back;

    String URL="https://test-ajay.000webhostapp.com/profile_show.php";
    String img_path="https://test-ajay.000webhostapp.com/Image/";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        update_btn=findViewById(R.id.update_btn_id);
        et_name=findViewById(R.id.profileetuserName);
        et_email=findViewById(R.id.profileetEmail);
        et_number=findViewById(R.id.profileetphone);
        img_btn=findViewById(R.id.profile_img);
        select_img=findViewById(R.id.select_img_id);
        back=findViewById(R.id.showback);
        img_btn.setImageDrawable(null);
        requestQueue= Volley.newRequestQueue(getApplicationContext());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(choose.std==0) {
                    Intent intent = new Intent(profile.this, cmp_dashboard.class);
                    startActivity(intent);
                }else
                {
                    Intent intent = new Intent(profile.this, std_dashboard.class);
                    startActivity(intent);
                }
            }
        });

        select_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();
            }
        });
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading Data....");
        progressDialog.show();
        load();
        progressDialog.dismiss();

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           // Toast.makeText(profile.this,"UpdateButton click ",Toast.LENGTH_LONG).show();
                    Update();



            }

        });


    }

    private void load()
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject object=new JSONObject(response);
                    JSONArray jsonArray=object.getJSONArray("root");

                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        et_name.setText(jsonObject.getString("n"));
                        Log.d("name",jsonObject.getString("n"));
                        et_email.setText(jsonObject.getString("e"));
                        et_number.setText(jsonObject.getString("m"));
                        Log.d("m",jsonObject.getString("m"));
                        String img_ = jsonObject.getString("i");

                        if (img_ != null)
                            Picasso.with(getApplicationContext()).load(img_path+img_+".jpg").into(img_btn);
                        else
                            Picasso.with(getApplicationContext()).load(R.drawable.ic_person);
                    }
                } catch (JSONException e) {

                    Log.d("json",e.getMessage());
                    Toast.makeText(profile.this,"profile json exception img"+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(profile.this,"profile Error Response= "+error.getMessage(),Toast.LENGTH_LONG).show();
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
    private void Update() {
        //Toast.makeText(profile.this,"Update Function ",Toast.LENGTH_LONG).show();

        if (checkEmpty() && validation()) {
            Log.i("done","done");

            Toast.makeText(profile.this,"Update Function 1",Toast.LENGTH_LONG).show();


            final String img=ImageToString(bitmap);
          //  Toast.makeText(profile.this,"Update Function 2",Toast.LENGTH_LONG).show();

            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, "https://test-ajay.000webhostapp.com/profile_update.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("std", String.valueOf(choose.std));
                    if(choose.std==0) {
                        Intent intent = new Intent(profile.this, cmp_dashboard.class);
                        startActivity(intent);
                    }else
                    {

                        Intent intent = new Intent(profile.this, std_dashboard.class);
                        startActivity(intent);

                    }
                    //load();

//                    try {
//                        Toast.makeText(profile.this,"Update Function 3",Toast.LENGTH_LONG).show();
//
//                        //           progressDialog.dismiss();
//                        JSONObject object=new JSONObject(response);
//                        JSONArray jsonArray=object.getJSONArray("root");
//                        //JSONObject object1=object.getJSONObject("Success");
//                        for(int i=0;i<jsonArray.length();i++) {
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            et_name.setText(jsonObject.getString("n"));
//                            Log.d("name", jsonObject.getString("n"));
//                            et_email.setText(jsonObject.getString("e"));
//                            et_number.setText(jsonObject.getString("m"));
//                            Log.d("m", jsonObject.getString("m"));
//                            String img_ = jsonObject.getString("i");
//                            Picasso.with(getApplicationContext()).load(img_path + img_ + ".jpg").into(img_btn);
//                        }
//
//                    } catch (JSONException e) {
//                        Toast.makeText(profile.this, "profile json exception 1 ", Toast.LENGTH_LONG).show();
//                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(profile.this, "profile Error Response= " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", login.UserName);
                    params.put("number", et_number.getText().toString().trim());
                    params.put("name", et_name.getText().toString().trim());
                    params.put("email", et_email.getText().toString().trim());
                    params.put("img",img);

                    return params;
                }


            };
            requestQueue.add(stringRequest1);
        }
    }



    private void selectImage()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null)
        {
            try {

                Uri path = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                img_btn.setImageBitmap(bitmap);
                //Toast.makeText(profile.this,"Path ="+path+" Bitmap  ="+bitmap,Toast.LENGTH_LONG).show();

            }catch(IOException e)
            {
                Toast.makeText(profile.this,"Error "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }

    private String ImageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgeByte=byteArrayOutputStream.toByteArray();

      //  Toast.makeText(profile.this," String ="+imgeByte,Toast.LENGTH_LONG).show();

        return Base64.encodeToString(imgeByte,Base64.DEFAULT);

    }

    public boolean checkEmpty() {

        if (et_name.getText().toString().trim().isEmpty()) {
            et_name.setError("Field Can't be empty");
            return false;

        } else if (et_email.getText().toString().trim().isEmpty()) {

            et_email.setError("Field Can't be empty");
            return false;

        } else if (et_number.getText().toString().trim().isEmpty()) {

            et_number.setError("Field Can't be empty");
            return false;

        } else
        {
            return true;
        }

    }
    public boolean validation()
    {
        Pattern pattern;
        Matcher matcher;

        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(et_email.getText().toString().trim());



        if(!et_name.getText().toString().trim().matches("[a-zA-Z ]+"))
        {
            et_name.setError("Invalid Name");
            return false;

        }else if(!et_number.getText().toString().trim().matches("[0-9]{10}"))
        {
            et_number.setError("Invalid Number");
            return false;
        }else if(!matcher.matches())
        {
            et_email.setError("Invalid Email ");
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        ///super.onBackPressed();
    }
}