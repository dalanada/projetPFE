package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class update extends AppCompatActivity {
    EditText nom,prenom,cin,telephone,adresse;
    Button update;
    client clientintent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        nom = findViewById(R.id.name);
        prenom = findViewById(R.id.prenom);
        cin = findViewById(R.id.cin);
        telephone = findViewById(R.id.telephone);
        adresse = findViewById(R.id.adresse);
        update = findViewById(R.id.button_update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateclient(nom.getText().toString(),prenom.getText().toString(),cin.getText().toString(),telephone.getText().toString(),adresse.getText().toString());
            }
        });


        clientintent = (client)  getIntent().getSerializableExtra("client");
        nom.setText(clientintent.getName());
        prenom.setText(clientintent.getPrenom());
        cin.setText(clientintent.getCin());
        telephone.setText(clientintent.getTelephone());
        adresse.setText(clientintent.getAdresse());
    }

    private void updateclient(final String nom, final String prenom, final String cin, final String telephone, final String adresse) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.15/appclient/public/api/"+clientintent.getId()+"/update1"; StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() { @Override
                public void onResponse(String response) {
                    try {
                        JSONObject r = new JSONObject(response);
                        String etat = r.getString("success");
                        if (etat.equals("true")) {
                            Toast.makeText(update.this, "Client update avec succès", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(update.this,MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(update.this, "Echec de l'update", Toast.LENGTH_LONG).show(); }
                    } catch (JSONException error) {
                        Toast.makeText(update.this, "Problème d'analyse JSON: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    } }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) { Toast.makeText(update.this, "Problème d'appel HTTP: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("name", nom);
                headers.put("prenom", prenom);
                headers.put("cin", cin);
                headers.put("telephone", telephone);
                headers.put("adresse", adresse);

                return headers; }
        };
        queue.add(stringRequest);
    }

}

