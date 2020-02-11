package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class select extends AppCompatActivity {

    TextView nom,prenom,cin,telephone,adresse;
    Button update,delet;

    client clientintent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        nom = findViewById(R.id.name);
        prenom = findViewById(R.id.prenom);
        cin = findViewById(R.id.cin);
        telephone = findViewById(R.id.telephone);
        adresse = findViewById(R.id.adresse);
        update = findViewById(R.id.button_update);
        delet = findViewById(R.id.button_delete);






        clientintent = (client)  getIntent().getSerializableExtra("client");
        nom.setText(clientintent.getName());
        prenom.setText(clientintent.getPrenom());
        cin.setText(clientintent.getCin());
        telephone.setText(clientintent.getTelephone());
        adresse.setText(clientintent.getAdresse());
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(select.this,update.class);
                i.putExtra("client",  clientintent);

                startActivity(i);
            }
        });

        delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delet();
            }
        });
    }

    private void delet() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.15/appclient/public/api/delete1/"+clientintent.getId(); StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() { @Override
                public void onResponse(String response) {
                    try {
                        JSONObject r = new JSONObject(response);
                        String etat = r.getString("success");
                        if (etat.equals("true")) {
                            Toast.makeText(select.this, "Client update avec succès", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(select.this,MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(select.this, "Echec de l'update", Toast.LENGTH_LONG).show(); }
                    } catch (JSONException error) {
                        Toast.makeText(select.this, "Problème d'analyse JSON: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    } }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) { Toast.makeText(select.this, "Problème d'appel HTTP: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();


                return headers; }
        };

        queue.add(stringRequest);
    }


}
