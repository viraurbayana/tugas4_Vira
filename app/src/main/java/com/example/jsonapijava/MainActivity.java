package com.example.jsonapijava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jsonapijava.model.Pemrograman;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    ArrayList<Pemrograman> pemrogramen = new ArrayList();
    protected final String url = "https://ewinsutriandi.github.io/mockapi/bahasa_pemrograman.json";
    JSONObject jsonObject;
    FloatingActionButton btnRefresh;
    ProgressBar loadingIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(view -> getRequestJsonAlgoritma());
        loadingIndicator = findViewById(R.id.loading);
        getRequestJsonAlgoritma();
    }

    void setupListview() {
        ListView listView = findViewById(R.id.listView);
        Adapter adapter = new Adapter(this, pemrogramen);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClick);
    }

    private AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Pemrograman fSELECTED = pemrogramen.get(position);
            Toast.makeText(MainActivity.this, fSELECTED.getBaca_lebih_lanjut(), Toast.LENGTH_LONG).show();
            Detail(fSELECTED);
        }
    };


    void getRequestJsonAlgoritma() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        jsonObject = response;
                        refresResponView();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("erorr", error.toString());
                        loadingIndicator.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Pastikan Anda Terhubung Dengan Interet", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    void refresResponView() {
        Iterator<String> key = jsonObject.keys();
        while(key.hasNext()) {
            String algoritma = key.next();
            try {
                JSONObject data = jsonObject.getJSONObject(algoritma);
                String deskripsi = data.getString("description");
                String logo = data.getString("logo");
                String heloWord =  data.getString("hello_world");
                String baca_lebih_lanjut = data.getString("read_more");
                pemrogramen.add(new Pemrograman(algoritma, baca_lebih_lanjut, heloWord, deskripsi, logo));

            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        loadingIndicator.setVisibility(View.GONE);
        setupListview();
    }

    void Detail(Pemrograman data) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(data.getBaca_lebih_lanjut()));
        startActivity(intent);
    }
}