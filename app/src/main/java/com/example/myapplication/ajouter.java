package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ajouter extends AppCompatActivity {
    EditText nom,prenom,cin,telephone,adresse;
    Button ajouter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);

        nom = findViewById(R.id.name);
        prenom = findViewById(R.id.prenom);
        cin = findViewById(R.id.cin);
        telephone = findViewById(R.id.telephone);
        adresse = findViewById(R.id.adresse);
        ajouter = findViewById(R.id.button_add);
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterclient(nom.getText().toString(),prenom.getText().toString(),cin.getText().toString(),telephone.getText().toString(),adresse.getText().toString());
            }
        });



    }

    private void ajouterclient(final String nom, final String prenom, final String cin, final String telephone, final String adresse) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.15/appclient/public/api/store1"; StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() { @Override
                public void onResponse(String response) {
                    try {
                        JSONObject r = new JSONObject(response);
                        String etat = r.getString("success");
                        if (etat.equals("true")) {
                            Toast.makeText(ajouter.this, "Client ajouté avec succès", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(ajouter.this,MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(ajouter.this, "Echec de l'ajout", Toast.LENGTH_LONG).show(); }
                    } catch (JSONException error) {
                        Toast.makeText(ajouter.this, "Problème d'analyse JSON: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    } }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) { Toast.makeText(ajouter.this, "Problème d'appel HTTP: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
