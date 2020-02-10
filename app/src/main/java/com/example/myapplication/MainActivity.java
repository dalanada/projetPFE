package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<client> client_list = new ArrayList<>();
    RecyclerView recyclerView;
    cllient_adabter adabter;
    FloatingActionButton add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.liste);

        LinearLayoutManager linear = new GridLayoutManager(getApplicationContext(), 1);
        linear.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linear);

        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ajouter.class);
                startActivity(i);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.15/appclient/public/api/index1"; StringRequest
                stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject r = new JSONObject(response);
                    JSONArray a = r.getJSONArray("client");
                    for (int i = 0; i < a.length(); i++) {
                        JSONObject o = a.getJSONObject(i);
                        client c = new client();
                        c.setId(o.getInt("id"));
                        c.setName(o.getString("name"));
                        c.setPrenom(o.getString("prenom"));
                        c.setCin(o.getString("cin"));
                        c.setTelephone(o.getString("telephone"));
                        c.setAdresse(o.getString("adresse"));
                        client_list.add(c);


                    }


                    adabter = new cllient_adabter(getBaseContext(), client_list);
                    recyclerView.setAdapter(adabter);
                } catch (JSONException error) {
                    Toast t= Toast.makeText(MainActivity.this,"Problème d'analyse JSON:" + error.getMessage(),Toast.LENGTH_LONG);
                    t.show(); }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) { Toast.makeText(MainActivity.this,"Problème d'appel HTTP:" + e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }
}
